package com.jose.mi_bocadillo_final.Fragments.pedirBocadillo

import PedirBocadilloViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.jose.mi_bocadillo_final.databinding.FragmentPedirBocadilloBinding

class PedirBocadilloFragment : Fragment() {
    private val pedirBocadilloViewModel: PedirBocadilloViewModel by viewModels()
    private var _binding: FragmentPedirBocadilloBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPedirBocadilloBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pedirBocadilloViewModel.fetchBocadillos()

        val listadoBocadillos = binding.textView

        pedirBocadilloViewModel.bocadillos.observe(viewLifecycleOwner, Observer { bocadillos ->
            if (bocadillos != null && bocadillos.isNotEmpty()) {
                val primerBocadillo = bocadillos[0]
                val descripcion = primerBocadillo.descripcion
                listadoBocadillos.text = "Descripción del primer bocadillo: $descripcion"
                Log.d("App", "Bocadillos observados: ${bocadillos.size}")
            } else {
                listadoBocadillos.text = "No hay bocadillos disponibles"
            }
        })

        pedirBocadilloViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                listadoBocadillos.text = errorMessage
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}