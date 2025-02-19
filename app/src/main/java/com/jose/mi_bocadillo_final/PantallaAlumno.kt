package com.jose.mi_bocadillo_final

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jose.mi_bocadillo_final.databinding.ActivityPantallaAlumnoBinding
class PantallaAlumno : AppCompatActivity() {


    private lateinit var binding: ActivityPantallaAlumnoBinding;
    private lateinit var authManager: AuthManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityPantallaAlumnoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authManager = AuthManager()

        val user = binding.user

        user.text = authManager.obtenerUsuarioActual()?.email ?: "Usuario no autenticado"


        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val logoutButton = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.botonCerrarSesion)

        logoutButton.setOnClickListener {
            authManager.cerrarSesion()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val navView: BottomNavigationView = binding.bottomNavigationView

        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_alumno)?.let {
            (it as NavHostFragment).navController
        } ?: throw IllegalStateException("NavController not found")


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_pedir,
                R.id.nav_pedidos,
                R.id.nav_bocatas,
                R.id.nav_perfil
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }
}