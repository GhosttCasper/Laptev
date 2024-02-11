package com.example.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.ListViewItemBinding
import com.example.movies.model.Film

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class FilmListAdapter(
    private val onFilmClicked: (Film) -> Unit,
    private val onFilmLongClicked: (Film) -> Boolean
) :
    ListAdapter<Film, FilmListAdapter.FilmViewHolder>(
        DiffCallback
    ) {

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FilmViewHolder(
            ListViewItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)
        holder.itemView.setOnClickListener {
            onFilmClicked(film)
        }
        holder.itemView.setOnLongClickListener {
            val longClickWasHandled = onFilmLongClicked(film)
            notifyItemChanged(position)
            longClickWasHandled
        }
        holder.bind(film)
    }

    /**
     * The FilmViewHolder constructor takes the binding variable from the associated
     * ListViewItem, which nicely gives it access to the full [Film] information.
     */
    class FilmViewHolder(
        private var binding: ListViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            binding.film = film
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [Film] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.nameRu == newItem.nameRu
        }

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.description == newItem.description
        }
    }
}