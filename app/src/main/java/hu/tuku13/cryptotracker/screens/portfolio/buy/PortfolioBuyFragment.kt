package hu.tuku13.cryptotracker.screens.portfolio.buy

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.tuku13.cryptotracker.databinding.FragmentPortfolioBuyBinding
import hu.tuku13.cryptotracker.screens.portfolio.PortfolioViewModel

class PortfolioBuyFragment : Fragment() {
    private lateinit var binding: FragmentPortfolioBuyBinding
    private lateinit var viewModel: PortfolioBuyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPortfolioBuyBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

        val viewModelFactory = PortfolioBuyViewModel.Factory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PortfolioBuyViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root
    }

}