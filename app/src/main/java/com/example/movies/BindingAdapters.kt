package com.example.movies

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movies.model.Film
import com.example.movies.ui.adapter.FilmListAdapter
import com.example.movies.ui.viewmodel.FilmApiStatus

/**
 * Uses the Coil library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

/**
 * Updates the data shown in the [RecyclerView].
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Film>?) {
    val adapter = recyclerView.adapter as FilmListAdapter
    adapter.submitList(data)
}

/**
 * This binding adapter displays the [FilmApiStatus] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@BindingAdapter("filmApiStatus")
fun bindStatus(statusImageView: ImageView, status: FilmApiStatus) {
    when (status) {
        FilmApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        FilmApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        FilmApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("filmApiStatus")
fun bindStatus(statusTextView: TextView, status: FilmApiStatus) {
    when (status) {
        FilmApiStatus.ERROR -> {
            statusTextView.visibility = View.VISIBLE
        }
        else -> {
            statusTextView.visibility = View.GONE
        }
    }
}

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter(value = ["htmlText"])
fun TextView.setHtmlText(string: String?) {
    text =
        if (string == null) "" else HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_MODE_COMPACT)
}