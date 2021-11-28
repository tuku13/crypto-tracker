package hu.tuku13.cryptotracker.screens.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.transition.Visibility
import dagger.hilt.android.AndroidEntryPoint
import hu.tuku13.cryptotracker.R
import hu.tuku13.cryptotracker.adapters.CoinAdapter
import hu.tuku13.cryptotracker.databinding.FragmentFavouritesBinding
import hu.tuku13.cryptotracker.repository.CoinRepository
import hu.tuku13.cryptotracker.screens.overview.OverviewFragmentDirections
import javax.inject.Inject

@AndroidEntryPoint
class FavouritesFragment : Fragment() {
    private lateinit var viewModel: FavouriteViewModel
    private lateinit var adapter: CoinAdapter
    @Inject
    lateinit var repository: CoinRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFavouritesBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

        val viewModelFactory = FavouriteViewModel.Factory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(FavouriteViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.navigateToCoinDetails.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                this.findNavController().navigate(
                    FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(it)
                )
                viewModel.doneNavigating()
            }
        })

        adapter = CoinAdapter(
            CoinAdapter.OnClickListener {
                viewModel.selectCoin(it)
            }
        )

//        binding.viewModel.isEmpty.observe(viewLifecycleOwner, Observer {
//            if(it) {
//                binding.tvFavouritesEmpty.visibility = View.GONE
//            } else {
//                binding.tvFavouritesEmpty.visibility = View.VISIBLE
//            }
//        })

        binding.favouriteCoinList.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.favouriteCoins.observe(viewLifecycleOwner, Observer {
            it?.apply {
                adapter.coinsList = it
            }
        })
    }
}