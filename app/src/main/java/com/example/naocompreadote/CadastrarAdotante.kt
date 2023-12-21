package com.example.naocompreadote

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                    email = binding.editTextCadastrarEmailAdotante.text.toString())
                lifecycleScope.launch(Dispatchers.Default) {
                    var login = ApiClient.getProjectService().criarAdotante(adotante)
                }
                findNavController().navigate(R.id.homeFragment)
            }
        }
    }
}