package com.example.movies.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.model.Film
import com.example.movies.model.Films
import com.example.movies.network.FilmApi
import kotlinx.coroutines.launch

enum class FilmApiStatus { LOADING, ERROR, DONE }

private const val TAG_ERROR = "Error"

class FilmViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<FilmApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<FilmApiStatus> = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of Film
    // with new values
    private val _films = MutableLiveData<Films>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val films: LiveData<Films> = _films

    //  Create properties to represent MutableLiveData and LiveData for a single Film object.
    //  This will be used to display the details of an Film when a list item is clicked
    private val _film = MutableLiveData<Film>()
    val film: LiveData<Film> = _film

    /**
     * Call getFilmsList() on init so we can display status immediately.
     */
    init {
        getFilmsList()
    }

    /**
     * Gets Films information from the Film API Retrofit service and updates the
     * [FilmApi] [List] [LiveData].
     */
    fun getFilmsList() {
        viewModelScope.launch {
            _status.value = FilmApiStatus.LOADING
            try {
                _films.value = FilmApi.retrofitService.getFilms()
                _status.value = FilmApiStatus.DONE
            } catch (e: Exception) {
                _status.value = FilmApiStatus.ERROR
                _films.value = Films()
                Log.e(TAG_ERROR, e.toString())
            }
        }
    }

    fun getFilmDetails(id: Int) {
        viewModelScope.launch {
            _status.value = FilmApiStatus.LOADING
            try {
                _film.value = FilmApi.retrofitService.getFilm(id)
                _status.value = FilmApiStatus.DONE
            } catch (e: Exception) {
                _status.value = FilmApiStatus.ERROR
                Log.e(TAG_ERROR, e.toString())
            }
        }
    }
}