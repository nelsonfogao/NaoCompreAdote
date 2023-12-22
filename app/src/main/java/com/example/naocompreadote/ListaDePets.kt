package com.example.naocompreadote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.naocompreadote.databinding.FragmentListaDePetsBinding

class ListaDePets : Fragment() {
    private lateinit var mainViewModel: MainViewModel
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
            binding.listaPets.adapter = it.pets?.let { it1 -> PetsRecyclerAdapter(it1){
                    mainViewModel.getPetById(it.petId!!)
                    if (!it.adocoes!!.isEmpty())
                        mainViewModel.getAdotanteByPetId(it.petId!!)
                    else
                        mainViewModel.clearAdotante()
                findNavController().navigate(R.id.listaDeAdotantes)
            } }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.cadastrarPetFragment)
        }
    }

}