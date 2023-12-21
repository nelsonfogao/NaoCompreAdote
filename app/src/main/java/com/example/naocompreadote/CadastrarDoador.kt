package com.example.naocompreadote

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.naocompreadote.api.ApiClient
import com.example.naocompreadote.api.model.Credenciais
import com.example.naocompreadote.api.model.Doador
import com.example.naocompreadote.databinding.FragmentCadastrarDoadorBinding
import com.example.naocompreadote.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CadastrarDoador : Fragment() {
    private lateinit var mainViewModel: MainViewModel

    private var _binding: FragmentCadastrarDoadorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCadastrarDoadorBinding.inflate(inflater, container, false)

        mainViewModel = ViewModelProvider(requireActivity(), ViewModelFactory()).get(MainViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCriarContaDoador.setOnClickListener{
            if(binding.editTextCadastrarNomeDoador.text.isNullOrEmpty()){
                Snackbar.make(it, "Digite seu nome!!", Snackbar.LENGTH_SHORT).setTextColor(Color.WHITE)
                    .setBackgroundTint(Color.RED)
                    .show()
            }else if(binding.editTextCadastrarSenhaDoador.text.isNullOrEmpty()) {
                Snackbar.make(it, "Digite sua senha!!", Snackbar.LENGTH_SHORT)
                    .setTextColor(Color.WHITE)
                    .setBackgroundTint(Color.RED)
                    .show()
            }else if(binding.editTextEnderecoDoador.text.isNullOrEmpty()) {
                Snackbar.make(it, "Digite seu endereço!!", Snackbar.LENGTH_SHORT)
                    .setTextColor(Color.WHITE)
                    .setBackgroundTint(Color.RED)
                    .show()
            }else if(binding.editTextCadastrarTelefoneDoador.text.isNullOrEmpty()) {
                Snackbar.make(it, "Digite seu telefone!!", Snackbar.LENGTH_SHORT)
                    .setTextColor(Color.WHITE)
                    .setBackgroundTint(Color.RED)
                    .show()
            }else if(binding.editTextCadastrarEmailDoador.text.isNullOrEmpty()) {
                Snackbar.make(it, "Digite seu email!!", Snackbar.LENGTH_SHORT)
                    .setTextColor(Color.WHITE)
                    .setBackgroundTint(Color.RED)
                    .show()
            }else {
                var doador = Doador(nome= binding.editTextCadastrarNomeDoador.text.toString(),
                senha = binding.editTextCadastrarSenhaDoador.text.toString(),
                endereco = binding.editTextEnderecoDoador.text.toString(),
                telefone = binding.editTextCadastrarTelefoneDoador.text.toString(),
                email = binding.editTextCadastrarEmailDoador.text.toString())
                lifecycleScope.launch(Dispatchers.Default) {
                    var login = ApiClient.getProjectService().criarDoador(doador)
                    Log.d("TAG", login.toString())
                }
                findNavController().navigate(R.id.homeFragment)
            }
        }
    }
}