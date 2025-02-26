package com.jose.mi_bocadillo_final.Fragments.historialPedidos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jose.mi_bocadillo_final.Models.Pedido
import com.jose.mi_bocadillo_final.R

class PedidoAdapter :
    ListAdapter<Pedido, PedidoAdapter.PedidoViewHolder>(PedidoDiffCallback()) {

    class PedidoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val descripcion: TextView = view.findViewById(R.id.Descripcion)
        val fecha: TextView = view.findViewById(R.id.Fecha)
        val precio: TextView = view.findViewById(R.id.Precio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pedido, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = getItem(position)
        holder.descripcion.text = pedido.descripcion
        holder.fecha.text = "Fecha: ${pedido.fecha}"
        holder.precio.text = "Precio: ${pedido.precio}€"
    }

    class PedidoDiffCallback : DiffUtil.ItemCallback<Pedido>() {
        override fun areItemsTheSame(oldItem: Pedido, newItem: Pedido): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pedido, newItem: Pedido): Boolean {
            return oldItem == newItem
        }
    }
}