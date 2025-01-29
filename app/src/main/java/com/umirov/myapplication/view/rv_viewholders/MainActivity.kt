package com.umirov.myapplication.view.rv_viewholders

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.umirov.myapplication.R
import com.umirov.myapplication.databinding.ActivityMainBinding
import com.umirov.myapplication.domain.Film
import com.umirov.myapplication.view.fragments.DetailsFragment
import com.umirov.myapplication.view.fragments.FavoritesFragment
import com.umirov.myapplication.view.fragments.HomeFragment
import com.umirov.myapplication.view.fragments.SelectionsFragment
import com.umirov.myapplication.view.fragments.SplashFragment
import com.umirov.myapplication.view.fragments.WatchLaterFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SplashFragment())
                .commit()
        }

        initNavigation()
    }


    fun launchDetailsFragment(film: Film) {
        // Create a bundle with the film
        val bundle = Bundle()
        bundle.putParcelable("film", film)

        // Create the details fragment and set arguments
        val fragment = DetailsFragment()
        fragment.arguments = bundle

        // Launch the fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun initNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.fav -> {
                    val tag = "favorites"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: FavoritesFragment(), tag)
                    true
                }
                R.id.watchlater -> {
                    val tag = "watchlater"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: WatchLaterFragment(), tag)
                    true
                }
                R.id.selections -> {
                    val tag = "selections"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: SelectionsFragment(), tag)
                    true
                }
                R.id.home -> {
                    val tag = "home"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: HomeFragment(), tag)
                    true
                }
                else -> false
            }
        }
    }

    private fun checkFragmentExistence(tag: String): Fragment? =
        supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment, tag)
            .addToBackStack(null)
            .commit()
    }
}
