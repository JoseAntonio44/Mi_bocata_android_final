package com.jose.mi_bocadillo_final.Fragments.bocadillosSemana

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.jose.mi_bocadillo_final.Adapters.BocadillosAdapter
import com.jose.mi_bocadillo_final.databinding.FragmentListaBocadillosBinding

class ListaBocadillosFragment : Fragment() {

    private val listaBocadillosViewModel : ListaBocadillosViewModel by viewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listaBocadillosViewModel.fetchBocatas()

        var listaBocatas = binding.listView

        listaBocadillosViewModel.bocatas.observe(viewLifecycleOwner) {
            bocatas -> if (bocatas != null) {

                val adapter = BocadillosAdapter(bocatas)


            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
