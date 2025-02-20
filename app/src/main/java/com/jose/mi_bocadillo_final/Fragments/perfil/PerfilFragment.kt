package com.jose.mi_bocadillo_final.Fragments.perfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jose.mi_bocadillo_final.databinding.FragmentPerfilBinding

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    private val perfilViewModel: PerfilViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        perfilViewModel.cargarUsuarioLogueado()

        perfilViewModel.usuario.observe(viewLifecycleOwner) { usuario ->
            if (usuario != null) {
                binding.Nombre.text = usuario.nombre
                binding.Apellidos.text = usuario.apellidos
                binding.correo.text = usuario.email
                binding.curso.text = usuario.curso
            } else {
                binding.Nombre.text = "Usuario no encontrado"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}