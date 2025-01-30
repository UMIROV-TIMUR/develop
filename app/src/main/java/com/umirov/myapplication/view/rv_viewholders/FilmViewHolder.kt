package com.umirov.myapplication.view.rv_viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umirov.myapplication.databinding.FilmItemBinding
import com.umirov.myapplication.data.Entity.Film

// In the constructor of the class, we pass the layout we created (film_item.xml)
class FilmViewHolder(private val binding: FilmItemBinding) : RecyclerView.ViewHolder(binding.root) {

    // Bind views from layout to variables
    fun bind(film: Film) {
        // Set the title
        binding.title.text = film.title
        // Set the poster
        Glide.with(itemView)
            .load(film.poster)
            .centerCrop()
            .into(binding.poster)
        // Set the description
        binding.description.text = film.description
        // Set the rating
        binding.ratingDonut.setProgress((film.rating * 10).toInt())
    }

    companion object {
        fun from(parent: ViewGroup): FilmViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = FilmItemBinding.inflate(layoutInflater, parent, false)
            return FilmViewHolder(binding)
        }
    }
}
