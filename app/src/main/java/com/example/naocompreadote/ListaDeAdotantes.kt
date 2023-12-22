package com.example.naocompreadote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.naocompreadote.databinding.FragmentListaDeAdotantesBinding

class ListaDeAdotantes : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentListaDeAdotantesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListaDeAdotantesBinding.inflate(inflater, container, false)

        mainViewModel = ViewModelProvider(requireActivity(), ViewModelFactory()).get(MainViewModel::class.java)
        mainViewModel.adotante.observe(viewLifecycleOwner, Observer {
            binding.listaAdotantes.adapter = it?.let { it1 -> AdotantesRecyclerAdapter(it1) }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}