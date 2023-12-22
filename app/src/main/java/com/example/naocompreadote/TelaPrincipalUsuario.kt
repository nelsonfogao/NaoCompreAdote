package com.example.naocompreadote

import android.os.Bundle
import android.os.Handler
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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
            binding.idadeDoPet.text = calcularIdade(it.dataNascimento!!)
        })


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageViewLike.setOnClickListener{
            mainViewModel.postAdocaoAtual()
            binding.imageViewLike.handler.postDelayed({
                binding.imageViewLike.isEnabled = true
            }, 1000)
        }
    }


    fun calcularIdade(dataNascimento: String): String {
        val formato = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        val dataNasc = formato.parse(dataNascimento)
        val hoje = Calendar.getInstance().time

        val diferenca = hoje.time - dataNasc.time
        val anos = diferenca / (365.25 * 24 * 60 * 60 * 1000).toLong()

        val meses = ((diferenca % (365.25 * 24 * 60 * 60 * 1000)) / (30.44 * 24 * 60 * 60 * 1000)).toLong()

        return if (anos > 0) {
            if (meses >= 12) {
                "$anos anos"
            } else {
                "$anos anos, $meses meses"
            }
        } else {
            "$meses meses"
        }
    }
}