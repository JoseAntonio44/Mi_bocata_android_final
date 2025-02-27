package com.jose.mi_bocadillo_final.Fragments.FormularioAlumno

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.jose.mi_bocadillo_final.Models.Usuario
import com.jose.mi_bocadillo_final.databinding.FragmentFormularioAlumnoBinding

class FormularioAlumnoFragment : Fragment() {

    private lateinit var nombreEditText: EditText
    private lateinit var apellidosEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var cursoEditText: EditText
    private lateinit var rolEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFormularioAlumnoBinding.inflate(inflater, container, false)

        nombreEditText = binding.nombreEditText
        apellidosEditText = binding.apellidosEditText
        emailEditText = binding.emailEditText

        // Recibir los datos del alumno desde arguments
        val nombre = arguments?.getString("nombre") ?: ""
        val apellidos = arguments?.getString("apellidos") ?: ""
        val email = arguments?.getString("email") ?: ""

        // Si estamos editando, rellenamos los campos
        if (nombre.isNotEmpty() && apellidos.isNotEmpty() && email.isNotEmpty()) {
            nombreEditText.setText(nombre)
            apellidosEditText.setText(apellidos)
            emailEditText.setText(email)
        }

        // Acción del botón guardar
        binding.botonGuardar.setOnClickListener {
            guardarAlumno()
            binding.root.visibility = View.GONE

        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //view.visibility = View.VISIBLE
    }

    private fun guardarAlumno() {
        val nombre = nombreEditText.text.toString()
        val apellidos = apellidosEditText.text.toString()
        val email = emailEditText.text.toString()

        if (nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Aquí llamas a Retrofit para guardar el alumno
        // Ejemplo: apiService.crearAlumno(Usuario(email, "", nombre, apellidos, "curso", "alumno"))

        Toast.makeText(requireContext(), "Alumno guardado", Toast.LENGTH_SHORT).show()

        requireActivity().supportFragmentManager.popBackStack()
    }
}