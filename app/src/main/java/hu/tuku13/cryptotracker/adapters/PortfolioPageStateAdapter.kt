package hu.tuku13.cryptotracker.adapters

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.tuku13.cryptotracker.screens.favourites.FavouritesFragment
import hu.tuku13.cryptotracker.screens.portfolio.info.PortfolioInfoFragment
import hu.tuku13.cryptotracker.screens.portfolio.transactions.PortfolioTransactionsFragment

class PortfolioPageStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int) = when(position) {
        0 -> PortfolioTransactionsFragment()
        1 -> PortfolioInfoFragment()
        else -> PortfolioTransactionsFragment()
    }

}