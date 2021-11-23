package hu.tuku13.cryptotracker.screens.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import hu.tuku13.cryptotracker.adapters.CoinAdapter
import hu.tuku13.cryptotracker.databinding.FragmentOverviewBinding
import hu.tuku13.cryptotracker.repository.CoinRepository

class OverviewFragment() : Fragment() {
    private lateinit var viewModel: OverviewViewModel
    private lateinit var adapter: CoinAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
        //binding.setLifecycleOwner(this)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = OverviewViewModel.Factory(application)
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