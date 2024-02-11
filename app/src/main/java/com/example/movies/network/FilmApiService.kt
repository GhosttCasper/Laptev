package com.example.movies.network

import com.example.movies.model.Film
import com.example.movies.model.Films
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
private const val API_KEY =
    "a4ef3405-dfb2-4120-a62b-365e8538bf65" // e30ffed0-76ab-4dd6-b41f-4c9da2b2735b
//a4ef3405-dfb2-4120-a62b-365e8538bf65

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getFilms] method
 */
interface FilmApiService {
    /**
     * Returns a [List] of [Film] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "films" endpoint will be requested with the GET
     * HTTP method
     */
    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("api/v2.2/films/top?type=TOP_100_POPULAR_FILMS") //?type=TOP_100_POPULAR_FILMS
    suspend fun getFilms(): Films

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")//https://kinopoiskapiunofficial.tech/api/v2.2/films/top
    @GET("/api/v2.2/films/{kinopoiskId}")
    suspend fun getFilm(@Path("kinopoiskId") id: Int): Film
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object FilmApi {
    val retrofitService: FilmApiService by lazy { retrofit.create(FilmApiService::class.java) }
}
