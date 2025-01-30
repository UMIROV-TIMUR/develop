package com.umirov.myapplication.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var isLoading = false
    private val visibleThreshold = 2
    private var currentPage = 1
    private var totalPages = 1

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
        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.homeFragmentRoot,
            requireActivity(),
            1
        )

        initSearchView()
        initPullToRefresh()
        initRecycler()

        viewModel.filmsListLiveData.observe(viewLifecycleOwner, Observer<List<Film>> {

            filmsDataBase = it
            filmsAdapter.addItems(it)
        })





        // Add Scroll Listener to RecyclerView пагинация*
        binding.mainRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    loadMoreItems()
                    isLoading = true
                }
            }
        })


    }
        private fun initPullToRefresh() {
            binding.pullToRefresh.setOnRefreshListener {
                filmsAdapter.items.clear()
                viewModel.getFilms()
                binding.pullToRefresh.isRefreshing = false
            }

    }




    private fun initSearchView() {
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

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

    private fun loadMoreItems() {
        currentPage++
        viewModel.getFilmsFromApi(currentPage, object : HomeFragmentViewModel.ApiCallback {
            override fun onSuccess(films: List<Film>) {
                filmsAdapter.addItems(films)
                isLoading = false
            }

            override fun onFailure() {



                isLoading = false
            }
        })


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




