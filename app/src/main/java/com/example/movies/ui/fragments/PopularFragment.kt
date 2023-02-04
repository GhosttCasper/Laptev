package com.example.movies.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movies.databinding.FragmentPopularBinding
import com.example.movies.ui.adapter.FilmListAdapter
import com.example.movies.ui.viewmodel.FilmViewModel

class PopularFragment : Fragment() {

    private val viewModel: FilmViewModel by viewModels()

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the FilmViewModel
        binding.viewModel = viewModel

        val adapter = FilmListAdapter {
            val action =
                it.filmId?.let { it1 ->
                    PopularFragmentDirections.actionPopularFragmentToFilmDetailFragment(
                        it1
                    )
                }
            if (action != null) {
                this.findNavController().navigate(action)
            }
        }
        // Sets the adapter of the recyclerView RecyclerView
        binding.recyclerView.adapter = adapter
        return binding.root
    }
}