package com.example.movies.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.example.movies.databinding.FragmentPopularBinding
import com.example.movies.ui.adapter.FilmListAdapter
import com.example.movies.ui.viewmodel.FilmViewModel

class PopularFragment : Fragment() {

    private val viewModel: FilmViewModel by activityViewModels()

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

        val adapter = FilmListAdapter({
            it.filmId?.let { it1 -> viewModel.getFilmDetails(it1) }
            binding.slidingPaneLayout.openPane()
        }, {
            it.favorites = !it.favorites
            true
        })
        // Sets the adapter of the recyclerView RecyclerView
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val slidingPaneLayout = binding.slidingPaneLayout
        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED

        // Connect the SlidingPaneLayout to the system back button.
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            FilmsListOnBackPressedCallback(slidingPaneLayout)
        )
    }
}

class FilmsListOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout
) : OnBackPressedCallback(slidingPaneLayout.isSlideable || slidingPaneLayout.isOpen),
    SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(this)
    }

    /**
     * Callback for handling the [OnBackPressedDispatcher.onBackPressed] event.
     */
    override fun handleOnBackPressed() {
        slidingPaneLayout.closePane()
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {

    }

    override fun onPanelOpened(panel: View) {
        isEnabled = true
    }

    override fun onPanelClosed(panel: View) {
        isEnabled = false
    }
}