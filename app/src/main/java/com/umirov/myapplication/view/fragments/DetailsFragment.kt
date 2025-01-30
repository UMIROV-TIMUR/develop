package com.umirov.myapplication.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.umirov.myapplication.R
import com.umirov.myapplication.data.ApiConstants
import com.umirov.myapplication.databinding.FragmentDetailsBinding
import com.umirov.myapplication.data.Entity.Film

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var film: Film

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        setFilmsDetails()

        binding.detailsFabFavorites.setOnClickListener {
                if (!film.isInFavorites) {
                    binding.detailsFabFavorites.setImageResource(R.drawable.baseline_favorite_24)
                    film.isInFavorites = true
                } else {
                    binding.detailsFabFavorites.setImageResource(R.drawable.baseline_favorite_border_24)
                    film.isInFavorites = false
                }
            }

        binding.detailsFabShare.setOnClickListener {
            //Создаем интент
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Check out this film: ${film.title} \n\n ${film.description}"
                )
                type = "text/plain"
            }
            //Запускаем наше активити
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
    }

    private fun setFilmsDetails() {
        //Получаем наш фильм из переданного бандла
        film = requireArguments().getParcelable("film") ?: return

        //Устанавливаем заголовок
        binding.detailsToolbar.title = film.title
        //Устанавливаем картинку
        Glide.with(this)
            .load(ApiConstants.IMAGES_URL + "w780" + film.poster)
            .centerCrop()
            .into(binding.detailsPoster)

        //Устанавливаем описание
        binding.detailsDescription.text = film.description

        binding.detailsFabFavorites.setImageResource(
            if (film.isInFavorites) R.drawable.baseline_favorite_24
            else R.drawable.baseline_favorite_border_24
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
