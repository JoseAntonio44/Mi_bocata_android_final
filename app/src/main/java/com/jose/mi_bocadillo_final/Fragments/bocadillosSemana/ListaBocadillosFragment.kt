package com.jose.mi_bocadillo_final.Fragments.listaBocadillos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jose.mi_bocadillo_final.databinding.FragmentListaBocadillosBinding

class ListaBocadillosFragment : Fragment() {

    private var _binding: FragmentListaBocadillosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(this).get(ListaBocadillosViewModel::class.java)

        _binding = FragmentListaBocadillosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
