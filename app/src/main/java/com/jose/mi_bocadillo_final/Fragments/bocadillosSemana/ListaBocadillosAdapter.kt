package com.jose.mi_bocadillo_final.Fragments.bocadillosSemana

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jose.mi_bocadillo_final.Models.Bocadillo
import com.jose.mi_bocadillo_final.R

class ListaBocadillosAdapter : RecyclerView.Adapter<ListaBocadillosAdapter.BocadilloViewHolder>() {
    private var bocadillosList = listOf<Bocadillo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BocadilloViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bocadillo, parent, false)
        return BocadilloViewHolder(view)
    }

    override fun onBindViewHolder(holder: BocadilloViewHolder, position: Int) {
        val bocadillo = bocadillosList[position]
        holder.nombre.text = "Bocadillo: ${bocadillo.descripcion}"
        holder.dia.text = bocadillo.dia
        holder.precio.text = "Precio: ${bocadillo.coste}€"
        holder.tipo.text = bocadillo.tipo
    }

    override fun getItemCount(): Int = bocadillosList.size

    fun submitList(bocadillos: List<Bocadillo>) {
        bocadillosList = bocadillos
        notifyDataSetChanged()
    }

    class BocadilloViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.NombreBocadillo)
        val dia: TextView = view.findViewById(R.id.Dia)
        val precio: TextView = view.findViewById(R.id.PrecioBocadillo)
        val tipo: TextView = view.findViewById(R.id.tipo)
    }
}