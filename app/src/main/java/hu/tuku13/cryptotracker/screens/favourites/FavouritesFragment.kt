package hu.tuku13.cryptotracker.screens.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import hu.tuku13.cryptotracker.R
import hu.tuku13.cryptotracker.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment() {
    private lateinit var viewModel: FavouriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFavouritesBinding.inflate(layoutInflater)
        //binding.setLifecycleOwner(this)

        val viewModelFactory = FavouriteViewModel.Factory()
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(FavouriteViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root
    }
}