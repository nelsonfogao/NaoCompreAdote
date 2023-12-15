package com.example.naocompreadote

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.naocompreadote.api.ApiClient
import com.example.naocompreadote.api.model.Credenciais
import com.example.naocompreadote.api.model.Doador
import com.example.naocompreadote.api.model.Pet
import com.example.naocompreadote.databinding.FragmentHomeBinding
import com.example.naocompreadote.databinding.FragmentListaDePetsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListaDePets : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    var login:Doador = Doador()
    private var _binding: FragmentListaDePetsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListaDePetsBinding.inflate(inflater, container, false)

        mainViewModel = ViewModelProvider(requireActivity(), ViewModelFactory()).get(MainViewModel::class.java)
        mainViewModel.doador.observe(viewLifecycleOwner, Observer {
            it.pets
            binding.listaPets.adapter = PetsRecyclerAdapter(it.pets!!)

        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
    private fun adaptarListView(listaDePets: List<Pet>) {
        binding.listaPets.adapter =
            PetsRecyclerAdapter(listaDePets)
        binding.listaPets.layoutManager = LinearLayoutManager(requireContext())
    }
}