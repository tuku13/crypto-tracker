package hu.tuku13.cryptotracker.screens.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.tuku13.cryptotracker.adapters.CoinAdapter
import hu.tuku13.cryptotracker.databinding.FragmentOverviewBinding
import hu.tuku13.cryptotracker.repository.CoinRepository
import javax.inject.Inject

@AndroidEntryPoint
class OverviewFragment() : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var viewModel: OverviewViewModel
    private lateinit var adapter: CoinAdapter
    @Inject
    lateinit var repository: CoinRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModelFactory = OverviewViewModel.Factory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(OverviewViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.navigateToCoinDetails.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                this.findNavController().navigate(
                        OverviewFragmentDirections.actionOverviewFragmentToDetailsFragment(it)
                )
                viewModel.doneNavigating()
            }
        })

        adapter = CoinAdapter(
            CoinAdapter.OnClickListener {
                viewModel.selectCoin(it)
            }
        )

        binding.searchBar.setOnQueryTextListener(this)

        viewModel.filter.observe(viewLifecycleOwner, Observer { f ->
            val list = viewModel.coins.value ?: emptyList()
            adapter.coinsList = list.filter { c ->
                c.name.contains(f, true) || c.symbol.contains(f, true)
            }
        })

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

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        viewModel.filter.value = p0 ?: ""
        return true
    }

}