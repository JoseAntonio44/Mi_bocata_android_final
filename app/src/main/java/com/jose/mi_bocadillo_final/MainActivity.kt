package com.jose.mi_bocadillo_final

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.jose.mi_bocadillo_final.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonLogin.setOnClickListener {
            val email = binding.usuario.text.toString()
            val password = binding.contrasena.text.toString()
            val intent = Intent(this, PantallaAlumno::class.java)
            startActivity(intent)
        }



    }
}