package hu.tuku13.cryptotracker.screens.portfolio.transactions

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import hu.tuku13.cryptotracker.adapters.PortfolioAdapter
import hu.tuku13.cryptotracker.databinding.FragmentPortfolioTransactionsBinding

class PortfolioTransactionsFragment : Fragment() {
    private lateinit var binding: FragmentPortfolioTransactionsBinding
    private lateinit var viewModel: PortfolioTransactionsViewModel
    private lateinit var adapter: PortfolioAdapter

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

        adapter = PortfolioAdapter(
            PortfolioAdapter.OnClickListener {
                Log.d("OnClick", it.id.toString())
                viewModel.deleteTransaction(it)
            }
        )
        binding.recycleView.adapter = adapter

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.coins.observe(viewLifecycleOwner, Observer {
            it?.apply {
                adapter.coins = it
            }
        })
        viewModel.transactions.observe(viewLifecycleOwner, Observer {
            it?.apply {
                Log.d("OBSERVER", "portfolio observer log")
                adapter.portfolio = it
            }
        })
    }

}