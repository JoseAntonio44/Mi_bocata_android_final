package com.jose.mi_bocadillo_final.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jose.mi_bocadillo_final.Models.Bocadillo
import com.jose.mi_bocadillo_final.R

class BocadillosAdapter(private val bocadilloList: List<Bocadillo>) :
    RecyclerView.Adapter<BocadillosAdapter.PostViewHolder>() {

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.NombreBocadillo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bocata_list, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = bocadilloList[position]
        holder.title.text = post.descripcion
    }

    override fun getItemCount() = bocadilloList.size
}