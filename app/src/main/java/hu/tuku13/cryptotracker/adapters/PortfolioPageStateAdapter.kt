package hu.tuku13.cryptotracker.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.tuku13.cryptotracker.screens.favourites.FavouritesFragment
import hu.tuku13.cryptotracker.screens.overview.OverviewFragment
import hu.tuku13.cryptotracker.screens.portfolio.buy.PortfolioBuyFragment

class PortfolioPageStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int) = when(position) {
        0 -> PortfolioBuyFragment()
        1 -> FavouritesFragment()
        2 -> FavouritesFragment()
        else -> FavouritesFragment()
    }

}