package com.umirov.myapplication.view.rv_adapters

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umirov.myapplication.data.ApiConstants
import com.umirov.myapplication.databinding.FilmItemBinding
import com.umirov.myapplication.data.Entity.Film
import com.umirov.myapplication.view.customviews.RatingDonutView

class FilmListRecyclerAdapter(private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<FilmListRecyclerAdapter.FilmViewHolder>() {


     val items = mutableListOf<Film>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        when (holder) {
            is FilmViewHolder -> {
                holder.bind(items[position])
                holder.itemView.setOnClickListener {
                    clickListener.click(items[position])
                }

            }

        }
    }

    fun addItems(list: List<Film>) {
        val startPosition = items.size
        items.addAll(list)
        notifyItemRangeInserted(startPosition, list.size)
        notifyDataSetChanged()


    }

    inner class FilmViewHolder(private val binding: FilmItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            Glide.with(itemView)
                .load(ApiConstants.IMAGES_URL + "w342" + film.poster)
                .centerCrop()
                .into(binding.poster)

            binding.ratingDonut.setProgress((film.rating * 10).toInt())
            binding.title.text = film.title
            binding.description.text = film.description
            animateRating(binding.ratingDonut, film.rating)
        }

        private fun animateRating(view: RatingDonutView, rating: Double) {
            val animator = ValueAnimator.ofInt(0, (rating * 10).toInt())
            animator.duration = 1000
            animator.addUpdateListener { animation ->
                val animatedValue = animation.animatedValue as Int
                view.setProgress(animatedValue)
            }
            animator.start()

        }
    }

    interface OnItemClickListener {
        fun click(film: Film)
    }
}
