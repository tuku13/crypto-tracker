package hu.tuku13.cryptotracker.screens.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import hu.tuku13.cryptotracker.databinding.FragmentOverviewBinding

class OverviewFragment() : Fragment() {
    private lateinit var viewModel: OverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
        //binding.setLifecycleOwner(this)

        val viewModelFactory = OverviewViewModel.Factory("Ez legyen a szoveg")
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(OverviewViewModel::class.java)
        binding.viewModel =viewModel

        return binding.root
    }

}