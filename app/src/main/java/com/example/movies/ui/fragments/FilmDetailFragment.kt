package com.example.movies.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.movies.R
import com.example.movies.bindImage
import com.example.movies.databinding.FragmentFilmDetailBinding
import com.example.movies.model.Film
import com.example.movies.setHtmlText
import com.example.movies.ui.viewmodel.FilmViewModel

class FilmDetailFragment : Fragment() {
    private val viewModel: FilmViewModel by activityViewModels()

    private var _binding: FragmentFilmDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmDetailBinding.inflate(inflater)

        return binding.root
    }

    /**
     * Binds views with the passed in film data.
     */
    private fun bind(film: Film) {
        binding.apply {
            filmPosterImage.bindImage(film.posterUrl)
            filmName.text = film.nameRu
            filmDescription.text = film.description

            filmGenres.setHtmlText(
                getString(
                    R.string.film_genres,
                    film.genres.joinToString { it.genre }
                )
            )
            filmCountries.setHtmlText(
                getString(
                    R.string.film_countries,
                    film.countries.joinToString { it.country }
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Attach an observer on the data (instead of polling for changes) and only update the
        // the UI when the data actually changes.
        viewModel.film.observe(this.viewLifecycleOwner) { selectedFilm ->
            bind(selectedFilm)
        }

    }
}