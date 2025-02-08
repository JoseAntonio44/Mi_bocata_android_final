package com.jose.mi_bocadillo_final.Fragments.pedirBocadillo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jose.mi_bocadillo_final.databinding.FragmentPedirBocadilloBinding

class PedirBocadilloFragment : Fragment() {

    private var _binding: FragmentPedirBocadilloBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(this).get(PedirBocadilloViewModel::class.java)

        _binding = FragmentPedirBocadilloBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
