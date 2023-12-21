package com.example.naocompreadote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.naocompreadote.databinding.FragmentTelaPrincipalUsuarioBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TelaPrincipalUsuario : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentTelaPrincipalUsuarioBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTelaPrincipalUsuarioBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity(), ViewModelFactory()).get(MainViewModel::class.java)

        mainViewModel.adotanteLogado.observe(viewLifecycleOwner, Observer {
            it.adotanteId?.let { it1 -> mainViewModel.getPetPrincipal(it1) }
        })
        mainViewModel.petPrincipal.observe(viewLifecycleOwner, Observer {
            Picasso.get().load(it.fotoUrl).into(binding.imagePetUser)
            binding.nomeDoPet.text = it.nome
        })


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}