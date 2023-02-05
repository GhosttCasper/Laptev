package com.example.movies.model

data class Films(
    val films: List<Film>? = null,
)

/**
 * This data class defines a Film which includes an ID, and the image URL.
 * The property names of this data class are used by Moshi to match the names of values in JSON.
 */
data class Film(
    val filmId: Int?,
    val nameRu: String,
    // used to map posterUrl from the JSON to posterUrl in our class
    val posterUrl: String,
    val posterUrlPreview: String,
    val description: String? = "",
    val countries: List<Country>,
    val genres: List<Genre>,
    val year: Int,
    var favorites: Boolean = false,
)

data class Country(
    val country: String
)

data class Genre(
    val genre: String
)