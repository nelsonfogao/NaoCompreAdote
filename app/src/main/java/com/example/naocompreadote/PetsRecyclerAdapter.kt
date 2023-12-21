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
import com.squareup.picasso.Picasso


class PetsRecyclerAdapter (
    val petsList: List<Pet>,
    val actionClick: (Pet)->Unit
): RecyclerView.Adapter<PetsRecyclerAdapter.PetsViewHolder>(){


    class PetsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textViewNome: TextView = itemView.findViewById(R.id.textViewNomeCard)
        val imgViewPets: ImageView = itemView.findViewById(R.id.imageListaPets)
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
        Picasso.get().load(pets.fotoUrl)
            .into(holder.imgViewPets)

        holder.itemView.setOnClickListener{
            actionClick(pets)
        }


    }

    override fun getItemCount(): Int = petsList.size
}