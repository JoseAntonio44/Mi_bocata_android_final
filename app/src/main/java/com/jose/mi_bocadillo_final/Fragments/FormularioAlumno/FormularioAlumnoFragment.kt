package com.jose.mi_bocadillo_final.Fragments.FormularioAlumno

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.jose.mi_bocadillo_final.Models.Usuario
import com.jose.mi_bocadillo_final.PantallaAdmin.PantallaAdminViewModel
import com.jose.mi_bocadillo_final.databinding.FragmentFormularioAlumnoBinding


class FormularioAlumnoFragment : Fragment() {

    private lateinit var nombreEditText: EditText
    private lateinit var apellidosEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var cursoEditText: EditText

    private val usuarioViewModel: PantallaAdminViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFormularioAlumnoBinding.inflate(inflater, container, false)

        var  emailAnterior = arguments?.getString("email") ?: "";

        nombreEditText = binding.nombreEditText
        apellidosEditText = binding.apellidosEditText
        emailEditText = binding.emailEditText
        passwordEditText = binding.passwordEditText
        cursoEditText = binding.cursoEditText

        val nombre = arguments?.getString("nombre") ?: ""
        val apellidos = arguments?.getString("apellidos") ?: ""
        val email = arguments?.getString("email") ?: ""
        val password = arguments?.getString("password") ?: ""
        val curso = arguments?.getString("curso") ?: ""

        if (nombre.isNotEmpty() && apellidos.isNotEmpty() && email.isNotEmpty()) {
            nombreEditText.setText(nombre)
            apellidosEditText.setText(apellidos)
            emailEditText.setText(email)
            passwordEditText.setText(password)
            cursoEditText.setText(curso)
        }

        binding.botonGuardar.setOnClickListener {
            guardarAlumno(emailAnterior)
            if (nombre.isNotEmpty() && apellidos.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && curso.isNotEmpty()) {
                binding.root.visibility = View.GONE
            }

        }
        binding.botonCancelar.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return binding.root
    }

    private fun guardarAlumno(emailAnterior: String) {
        val nombre = nombreEditText.text.toString()
        val apellidos = apellidosEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val curso = cursoEditText.text.toString()

        if (nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty() || password.isEmpty() || curso.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Aquí llamas a Retrofit para guardar el alumno
        // Ejemplo: apiService.crearAlumno(Usuario(email, "", nombre, apellidos, "curso", "alumno"))


        val alumno = Usuario(email, password, nombre, apellidos, curso, "alumno")

        usuarioViewModel.modificarUsuario(alumno,emailAnterior)


        requireActivity().supportFragmentManager.popBackStack()
    }
}