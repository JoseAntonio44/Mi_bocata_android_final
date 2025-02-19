package com.jose.mi_bocadillo_final.Fragments.perfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jose.mi_bocadillo_final.AuthManager
import com.jose.mi_bocadillo_final.databinding.FragmentPerfilBinding


class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val PerfilFragmentViewModel =
            ViewModelProvider(this).get(PerfilViewModel::class.java)

        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val root: View = binding.root


        /*val textView: TextView = binding.textDashboard
        PerfilFragmentViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nombreTextView = binding.Nombre
        val apellidosTextView = binding.Apellidos
        val correoTextView = binding.correo
        val cursoTextView = binding.curso


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}