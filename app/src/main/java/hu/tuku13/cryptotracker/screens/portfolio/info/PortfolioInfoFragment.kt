package hu.tuku13.cryptotracker.screens.portfolio.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import hu.tuku13.cryptotracker.R
import hu.tuku13.cryptotracker.database.getDatabase
import hu.tuku13.cryptotracker.databinding.FragmentPortfolioInfoBinding
import hu.tuku13.cryptotracker.domain.Coin
import hu.tuku13.cryptotracker.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class PortfolioInfoFragment : Fragment() {
    private lateinit var binding : FragmentPortfolioInfoBinding
    private lateinit var viewModel : PortfolioInfoViewModel
    @Inject
    lateinit var repository: CoinRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPortfolioInfoBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(activity).application
        val viewModelFactory = PortfolioInfoViewModel.Factory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PortfolioInfoViewModel::class.java)

        viewModel.coinWithPortfolioTransactions.observe(viewLifecycleOwner, Observer {

        })

        return binding.root
    }

}