package com.jose.mi_bocadillo_final.Fragments.bocadillosSemana

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jose.mi_bocadillo_final.databinding.FragmentListaBocadillosBinding

class ListaBocadillosFragment : Fragment() {

    private var _binding: FragmentListaBocadillosBinding? = null
    private val binding get() = _binding!!
    private val bocadilloViewModel: ListaBocadillosViewModel by activityViewModels()
    private lateinit var bocadilloAdapter: ListaBocadillosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaBocadillosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewBocadillos.layoutManager = LinearLayoutManager(context)
        bocadilloAdapter = ListaBocadillosAdapter()
        binding.recyclerViewBocadillos.adapter = bocadilloAdapter

        bocadilloViewModel.bocatas.observe(viewLifecycleOwner, Observer { bocadillos ->
            bocadillos?.let {
                bocadilloAdapter.submitList(bocadillos)
                println("BOCADILLOS: " + bocadillos)
            }

        })

        bocadilloViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, "Error: $it", Toast.LENGTH_LONG).show()
            }
        })

        bocadilloViewModel.fetchBocadillos()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}