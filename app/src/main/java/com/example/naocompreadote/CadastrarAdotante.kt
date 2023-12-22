package com.example.naocompreadote

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.naocompreadote.api.ApiClient
import com.example.naocompreadote.api.model.Adotante
import com.example.naocompreadote.api.model.Doador
import com.example.naocompreadote.databinding.FragmentCadastrarAdotanteBinding
import com.example.naocompreadote.databinding.FragmentCadastrarDoadorBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CadastrarAdotante : Fragment() {
    private lateinit var mainViewModel: MainViewModel

    private var _binding: FragmentCadastrarAdotanteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCadastrarAdotanteBinding.inflate(inflater, container, false)

        mainViewModel = ViewModelProvider(requireActivity(), ViewModelFactory()).get(MainViewModel::class.java)
        mainViewModel.imagemAdotante.observe(viewLifecycleOwner, Observer {})
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.CadastrarFotoAdotante.setOnClickListener{
            selecionarImagem()
        }

        binding.buttonCriarContaAdotante.setOnClickListener{
            if(binding.editTextCadastrarNomeAdotante.text.isNullOrEmpty()){
                Snackbar.make(it, "Digite seu nome!!", Snackbar.LENGTH_SHORT).setTextColor(Color.WHITE)
                    .setBackgroundTint(Color.RED)
                    .show()
            }else if(binding.editTextCadastrarSenhaAdotante.text.isNullOrEmpty()) {
                Snackbar.make(it, "Digite sua senha!!", Snackbar.LENGTH_SHORT)
                    .setTextColor(Color.WHITE)
                    .setBackgroundTint(Color.RED)
                    .show()
            }else if(binding.editTextEnderecoAdotante.text.isNullOrEmpty()) {
                Snackbar.make(it, "Digite seu endereço!!", Snackbar.LENGTH_SHORT)
                    .setTextColor(Color.WHITE)
                    .setBackgroundTint(Color.RED)
                    .show()
            }else if(binding.editTextCadastrarTelefoneAdotante.text.isNullOrEmpty()) {
                Snackbar.make(it, "Digite seu telefone!!", Snackbar.LENGTH_SHORT)
                    .setTextColor(Color.WHITE)
                    .setBackgroundTint(Color.RED)
                    .show()
            }else if(binding.editTextCadastrarEmailAdotante.text.isNullOrEmpty()) {
                Snackbar.make(it, "Digite seu email!!", Snackbar.LENGTH_SHORT)
                    .setTextColor(Color.WHITE)
                    .setBackgroundTint(Color.RED)
                    .show()
            }else {
                var adotante = Adotante(nome= binding.editTextCadastrarNomeAdotante.text.toString(),
                    senha = binding.editTextCadastrarSenhaAdotante.text.toString(),
                    endereco = binding.editTextEnderecoAdotante.text.toString(),
                    telefone = binding.editTextCadastrarTelefoneAdotante.text.toString(),
                    cpf = binding.editTextCadastrarCpfAdotante.text.toString(),
                    fotoUrl = mainViewModel.imagemAdotante.value,
                    email = binding.editTextCadastrarEmailAdotante.text.toString())
                mainViewModel.criarAdotante(adotante)
                findNavController().navigate(R.id.homeFragment)
            }
        }
    }
    private val someActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let { uri ->
                handleSelectedImage(uri)
            }
        }
    }

    private fun selecionarImagem() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        someActivityResultLauncher.launch(intent)
    }
    private fun handleSelectedImage(uri: Uri) {
        binding.CadastrarFotoAdotante.setImageURI(uri)
        mainViewModel.uploadImageAdotante(uri)
    }
}