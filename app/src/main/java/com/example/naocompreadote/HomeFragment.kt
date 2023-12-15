package com.example.naocompreadote

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.naocompreadote.api.ApiClient
import com.example.naocompreadote.api.model.Credenciais
import com.example.naocompreadote.api.model.Doador
import com.example.naocompreadote.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call

class HomeFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var login:Doador = Doador()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        mainViewModel = ViewModelProvider(requireActivity(), ViewModelFactory()).get(MainViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonEntrar.setOnClickListener{
            if(binding.editTextEmail.text.isNullOrEmpty()){
                Snackbar.make(it, "Digite seu email!!", Snackbar.LENGTH_SHORT).setTextColor(Color.WHITE)
                    .setBackgroundTint(Color.RED)
                    .show()
            }else if(binding.editTextPassword.text.isNullOrEmpty()) {
                Snackbar.make(it, "Digite sua senha!!", Snackbar.LENGTH_SHORT)
                    .setTextColor(Color.WHITE)
                    .setBackgroundTint(Color.RED)
                    .show()
            }else {
                var credenciais= Credenciais(
                email = binding.editTextEmail.text.toString(),
                senha = binding.editTextPassword.text.toString())
                if ( binding.switchLogin.isChecked){
                    GlobalScope.launch(Dispatchers.Default) {
                        //login = ApiClient.getProjectService().loginAdotante(credenciais)

                    }
                    findNavController().navigate(R.id.listaAdotantes)
                }else{
                    GlobalScope.launch(Dispatchers.Default) {
                        login = ApiClient.getProjectService().loginDoador(credenciais)
                    }
                    mainViewModel._doador.observe(viewLifecycleOwner, Observer {
                        mainViewModel.updateDoador(login)

                    })
                    mainViewModel._doador.observe(viewLifecycleOwner, Observer { newData ->
                        var id = newData.doadorId
                        GlobalScope.launch(Dispatchers.Default) {
                            login = ApiClient.getProjectService().getDoadorById(id!!)
                        }
                    })
                    mainViewModel._doador.observe(viewLifecycleOwner, Observer {
                        mainViewModel.updateDoador(login)
                    })
                    findNavController().navigate(R.id.listaDePets)

                }

            }
        }


        binding.buttonCriarConta.setOnClickListener{
            findNavController().navigate(R.id.newUser)
        }

    }
}