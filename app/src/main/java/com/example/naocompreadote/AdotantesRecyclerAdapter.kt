package com.example.naocompreadote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.naocompreadote.api.model.Adotante
import com.squareup.picasso.Picasso


class AdotantesRecyclerAdapter (
    val adotantesList: List<Adotante>
): RecyclerView.Adapter<AdotantesRecyclerAdapter.AdotantesViewHolder>(){


    class AdotantesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textViewNome: TextView = itemView.findViewById(R.id.textViewNomeCard)
        val textViewTelefone: TextView = itemView.findViewById(R.id.textViewTelefoneCard)
        val imgViewAdotantes: ImageView = itemView.findViewById(R.id.imageListaAdotantes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdotantesViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.adotantes_recycler_adapter,
                parent,
                false
            )
        return AdotantesViewHolder(view)
    }

    override fun onBindViewHolder( holder: AdotantesViewHolder, position: Int) {
        val adotantes = adotantesList[position]
        holder.textViewNome.text = adotantes.nome
        holder.textViewTelefone.text = adotantes.telefone
        Picasso.get().load(adotantes.fotoUrl)
            .into(holder.imgViewAdotantes)
    }

    override fun getItemCount(): Int = adotantesList.size
}