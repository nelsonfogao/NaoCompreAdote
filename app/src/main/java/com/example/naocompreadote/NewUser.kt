package com.example.naocompreadote

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.naocompreadote.api.ApiClient
import com.example.naocompreadote.api.model.Credenciais
import com.example.naocompreadote.databinding.FragmentHomeBinding
import com.example.naocompreadote.databinding.FragmentNewUserBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewUser : Fragment() {
    private lateinit var mainViewModel: MainViewModel

    private var _binding: FragmentNewUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewUserBinding.inflate(inflater, container, false)

        mainViewModel = ViewModelProvider(requireActivity(), ViewModelFactory()).get(MainViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonDoador.setOnClickListener {
            findNavController().navigate(R.id.cadastrarDoador)
        }


        binding.buttonCriarConta.setOnClickListener{
            findNavController().navigate(R.id.cadastrarAdotante)
        }

    }
}