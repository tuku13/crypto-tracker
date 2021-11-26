package hu.tuku13.cryptotracker.screens.portfolio.transactions

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.tuku13.cryptotracker.databinding.FragmentPortfolioTransactionsBinding

class PortfolioTransactionsFragment : Fragment() {
    private lateinit var binding: FragmentPortfolioTransactionsBinding
    private lateinit var viewModel: PortfolioTransactionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPortfolioTransactionsBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

        val viewModelFactory = PortfolioTransactionsViewModel.Factory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PortfolioTransactionsViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root
    }

}