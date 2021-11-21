package hu.tuku13.cryptotracker.screens.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hu.tuku13.cryptotracker.R
import hu.tuku13.cryptotracker.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailsBinding.inflate(layoutInflater)
        //binding.setLifecycleOwner(this)

        val viewModelFactory = DetailsViewModel.Factory()
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(DetailsViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root
    }
}