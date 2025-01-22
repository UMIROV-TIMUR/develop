package com.umirov.myapplication.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.umirov.myapplication.databinding.FragmentHomeBinding
import com.umirov.myapplication.domain.Film
import com.umirov.myapplication.utils.AnimationHelper
import com.umirov.myapplication.view.rv_adapters.FilmListRecyclerAdapter
import com.umirov.myapplication.view.rv_adapters.TopSpacingItemDecoration
import com.umirov.myapplication.view.rv_viewholders.MainActivity
import com.umirov.myapplication.viewmodel.HomeFragmentViewModel

class HomeFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    private var filmsDataBase = listOf<Film>()

        set(value) {

            if (field == value) return

            field = value

            filmsAdapter.addItems(field)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(binding.homeFragmentRoot, requireActivity(), 1)

        initSearchView()
        initRecycler()

        viewModel.filmsListLiveData.observe(viewLifecycleOwner, Observer<List<Film>>{
            filmsDataBase = it
        })




    }


    private fun initSearchView() {
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("SearchView", "Query submitted: $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SearchView", "Query changed: $newText")
                if (newText.isNullOrEmpty()) {
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }
                val result = filmsDataBase.filter {
                    it.title.contains(newText.toString(), ignoreCase = true)
                }
                filmsAdapter.addItems(result)
                return true
            }
        })
    }

    private fun initRecycler() {




            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })
        binding.mainRecycler.apply {

            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
