package hu.tuku13.cryptotracker.screens.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import hu.tuku13.cryptotracker.adapters.CoinAdapter
import hu.tuku13.cryptotracker.databinding.FragmentOverviewBinding

class OverviewFragment() : Fragment() {
    private lateinit var viewModel: OverviewViewModel
    private lateinit var adapter: CoinAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
        //binding.setLifecycleOwner(this)

        val viewModelFactory = OverviewViewModel.Factory("Ez legyen a szoveg")
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(OverviewViewModel::class.java)
        binding.viewModel = viewModel

        adapter = CoinAdapter()
        binding.coinList.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.coins.observe(viewLifecycleOwner, Observer {
            it?.apply {
                adapter.coinsList = it
            }
        })
    }

}