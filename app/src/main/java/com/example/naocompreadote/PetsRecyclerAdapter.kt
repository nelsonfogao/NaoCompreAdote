package com.example.naocompreadote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.naocompreadote.api.model.Pet
import com.example.naocompreadote.databinding.FragmentHomeBinding


class PetsRecyclerAdapter (
    val petsList: List<Pet>
): RecyclerView.Adapter<PetsRecyclerAdapter.PetsViewHolder>(){


    class PetsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textViewNome: TextView = itemView.findViewById(R.id.textViewNomeCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetsViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.pets_recycler_adapter,
                parent,
                false
            )
        return PetsViewHolder(view)
    }

    override fun onBindViewHolder( holder: PetsViewHolder, position: Int) {
        val pets = petsList[position]
        holder.textViewNome.text = pets.nome


    }

    override fun getItemCount(): Int = petsList.size
}