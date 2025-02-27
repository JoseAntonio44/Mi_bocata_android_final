package com.jose.mi_bocadillo_final.Fragments.pedirBocadillo

import PedirBocadilloViewModel
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.jose.mi_bocadillo_final.AuthManager
import com.jose.mi_bocadillo_final.MainActivity
import com.jose.mi_bocadillo_final.databinding.FragmentPedirBocadilloBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        val authManager = AuthManager()

        val nombreBocadilloFrio = binding.nombreFrio
        val precioBocadilloFrio = binding.precioFrio
        val botonPedirFrio = binding.botonPedirFrio

        val nombreBocadilloCaliente = binding.nombreCaliente
        val precioBocadilloCaliente = binding.precioCaliente
        val botonPedirCaliente = binding.botonPedirCaliente
        val mensaje = binding.mensajePedido
        val botonBorrarMensaje = binding.botonBorrarMensaje

        val botonCerrarSesion = binding.botonCerrarSesion

        val diaActual = SimpleDateFormat("EEEE", Locale("es", "ES")).format(Date()).lowercase()


        botonCerrarSesion.setOnClickListener {
            authManager.cerrarSesion()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        pedirBocadilloViewModel.bocadillos.observe(viewLifecycleOwner, Observer { bocadillos ->
            if (bocadillos != null && bocadillos.isNotEmpty()) {
                val bocadilloFrio = bocadillos.find { it.tipo == "frio" && it.dia == diaActual }
                val bocadilloCaliente =
                    bocadillos.find { it.tipo == "caliente" && it.dia == diaActual }

                if (bocadilloFrio != null) {
                    nombreBocadilloFrio.text = bocadilloFrio.descripcion
                    precioBocadilloFrio.text = "Precio: ${bocadilloFrio.coste}€"
                } else {
                    nombreBocadilloFrio.text = "No disponible"
                    precioBocadilloFrio.text = ""
                }

                if (bocadilloCaliente != null) {
                    nombreBocadilloCaliente.text = bocadilloCaliente.descripcion
                    precioBocadilloCaliente.text = "Precio: ${bocadilloCaliente.coste}€"
                } else {
                    nombreBocadilloCaliente.text = "No disponible"
                    precioBocadilloCaliente.text = ""
                }
                botonPedirFrio.setOnClickListener {
                    bocadilloFrio?.let { bocadillo ->
                        pedirBocadilloViewModel.hacerPedido(bocadilloFrio)
                    }
                }

                botonPedirCaliente.setOnClickListener {
                    bocadilloCaliente?.let { bocadillo ->
                        pedirBocadilloViewModel.hacerPedido(bocadilloCaliente)

                    }
                }
                botonBorrarMensaje.setOnClickListener{
                    mensaje.visibility = View.GONE
                    botonBorrarMensaje.visibility = View.GONE
                }
            }


        })
        pedirBocadilloViewModel.pedidoExitoso.observe(viewLifecycleOwner, Observer { pedidoExitoso ->
            if (pedidoExitoso) {
                mensaje.text = "Pedido realizado con éxito"
                mensaje.visibility = View.VISIBLE
                botonBorrarMensaje.visibility = View.VISIBLE
            }
            else {
                mensaje.text = "Error al realizar el pedido"
                mensaje.visibility = View.VISIBLE
                botonBorrarMensaje.visibility = View.VISIBLE
            }
        })

        pedirBocadilloViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                mensaje.text = errorMessage
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}