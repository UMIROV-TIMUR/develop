package com.umirov.myapplication.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.umirov.myapplication.databinding.FragmentSettingsBinding
import com.umirov.myapplication.utils.AnimationHelper
import com.umirov.myapplication.viewmodel.SettingsFragmentViewModel

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(SettingsFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Подключаем анимацию, и передаем номер позиции у кнопки в нижнем меню
        AnimationHelper.performFragmentCircularRevealAnimation(binding.settingsFragmentRoot, requireActivity(), 5)
        // Слушаем какой у нас сейчас выбран вариант в настройках
        viewModel.categoryPropertyLifeData.observe(viewLifecycleOwner, Observer<String> {
            when(it) {
                POPULAR_CATEGORY -> binding.radioGroup.check(binding.radioPopular.id)
                TOP_RATED_CATEGORY -> binding.radioGroup.check(binding.radioTopRated.id)
                UPCOMING_CATEGORY -> binding.radioGroup.check(binding.radioUpcoming.id)
                NOW_PLAYING_CATEGORY -> binding.radioGroup.check(binding.radioNowPlaying.id)
            }
        })
        // Слушатель для отправки нового состояния в настройки
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                binding.radioPopular.id -> viewModel.putCategoryProperty(POPULAR_CATEGORY)
                binding.radioTopRated.id -> viewModel.putCategoryProperty(TOP_RATED_CATEGORY)
                binding.radioUpcoming.id -> viewModel.putCategoryProperty(UPCOMING_CATEGORY)
                binding.radioNowPlaying.id -> viewModel.putCategoryProperty(NOW_PLAYING_CATEGORY)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val POPULAR_CATEGORY = "popular"
        private const val TOP_RATED_CATEGORY = "top_rated"
        private const val UPCOMING_CATEGORY = "upcoming"
        private const val NOW_PLAYING_CATEGORY = "now_playing"
    }
}
