package hu.tuku13.cryptotracker.screens.portfolio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import hu.tuku13.cryptotracker.R
import hu.tuku13.cryptotracker.databinding.FragmentPortfolioBinding

class PortfolioFragment : Fragment() {
    private lateinit var viewModel: PortfolioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPortfolioBinding.inflate(layoutInflater)
        //binding.setLifecycleOwner(this)

        val viewModelFactory = PortfolioViewModel.Factory()
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PortfolioViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root
    }

}