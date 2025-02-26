package com.jose.mi_bocadillo_final.Fragments.pedidos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jose.mi_bocadillo_final.Fragments.historialPedidos.PedidoAdapter
import com.jose.mi_bocadillo_final.Models.Pedido
import com.jose.mi_bocadillo_final.R
import com.jose.mi_bocadillo_final.databinding.FragmentPedidosBinding

class PedidosFragment : Fragment() {

    private var _binding: FragmentPedidosBinding? = null
    private val binding get() = _binding!!
    private val pedidoViewModel: PedidosViewModel by activityViewModels()
    private lateinit var pedidoAdapter: PedidoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPedidosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el RecyclerView
        binding.recyclerViewPedidos.layoutManager = LinearLayoutManager(context)
        pedidoAdapter = PedidoAdapter()
        binding.recyclerViewPedidos.adapter = pedidoAdapter

        // Observar cambios en los pedidos
        pedidoViewModel.pedidos.observe(viewLifecycleOwner, Observer { pedidos ->
            if (pedidos != null) {
                pedidoAdapter.submitList(pedidos)
            }
        })

        // Cargar los pedidos
        pedidoViewModel.cargarPedidos()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}