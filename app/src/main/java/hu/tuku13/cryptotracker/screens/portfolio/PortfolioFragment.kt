package hu.tuku13.cryptotracker.screens.portfolio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hu.tuku13.cryptotracker.R
import hu.tuku13.cryptotracker.adapters.PortfolioPageStateAdapter
import hu.tuku13.cryptotracker.databinding.FragmentPortfolioBinding

class PortfolioFragment : Fragment() {
    private lateinit var viewModel: PortfolioViewModel
    private lateinit var binding: FragmentPortfolioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPortfolioBinding.inflate(layoutInflater)
        //binding.setLifecycleOwner(this)
        val application = requireNotNull(activity).application

        val viewModelFactory = PortfolioViewModel.Factory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PortfolioViewModel::class.java)
        binding.viewModel = viewModel

        binding.viewPager.adapter = PortfolioPageStateAdapter(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Transactions"
                1 -> "Info"
                else -> "Info"
            }
        }.attach()
    }

}