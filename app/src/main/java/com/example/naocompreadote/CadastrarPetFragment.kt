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
import androidx.navigation.fragment.findNavController
import com.example.naocompreadote.api.model.Adotante
import com.example.naocompreadote.api.model.Credenciais
import com.example.naocompreadote.api.model.Doador
import com.example.naocompreadote.api.model.Pet
import com.example.naocompreadote.databinding.FragmentCadastrarPetBinding
import com.example.naocompreadote.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class CadastrarPetFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentCadastrarPetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCadastrarPetBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity(), ViewModelFactory()).get(MainViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.CadastrarFotoPet.setOnClickListener{
            selecionarImagem()
        }

        binding.buttonCriarPet.setOnClickListener{
            if(binding.editTextCadastrarNomePet.text.isNullOrEmpty()){
                Snackbar.make(it, "Digite o nome!!", Snackbar.LENGTH_SHORT).setTextColor(Color.WHITE)
                    .setBackgroundTint(Color.RED)
                    .show()
            }else if(binding.editTextDate.text.isNullOrEmpty()) {
                Snackbar.make(it, "Preencha a data!!", Snackbar.LENGTH_SHORT)
                    .setTextColor(Color.WHITE)
                    .setBackgroundTint(Color.RED)
                    .show()
            } else {
                var ehDog = false
                if ( binding.switchCadastrarPet.isChecked){
                    ehDog = true
                }
                var pet = Pet(nome= binding.editTextCadastrarNomePet.text.toString(),
                    dataNascimento = converterData(binding.editTextDate.text.toString()),
                    disponivel = true,
                    ehDog = ehDog,
                    fotoUrl = mainViewModel.imagemPet.value)
                mainViewModel.postCadastarPet(pet, mainViewModel.doador.value?.doadorId!!)
                mainViewModel.doador.observe(viewLifecycleOwner, Observer {
                    mainViewModel.getDoadorById(it.doadorId!!)
                })
                findNavController().navigate(R.id.listaDePets)
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
        binding.CadastrarFotoPet.setImageURI(uri)
        mainViewModel.uploadImagePet(uri)
    }

    fun converterData(inputDate: String): String {
        val formatoEntrada = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formatoSaida = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val data = formatoEntrada.parse(inputDate)
        return formatoSaida.format(data!!)
    }
}