package hu.tuku13.cryptotracker.screens.portfolio.add_to_portfolio

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import hu.tuku13.cryptotracker.LogoSize
import hu.tuku13.cryptotracker.Util
import hu.tuku13.cryptotracker.databinding.FragmentAddToPortfolioBinding
import hu.tuku13.cryptotracker.domain.Coin
import java.util.*

class AddToPortfolioFragment : Fragment() {
    private lateinit var binding: FragmentAddToPortfolioBinding
    private lateinit var viewModel: AddToPortfolioViewModel
    var date: Long = Calendar.getInstance().timeInMillis

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddToPortfolioBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        val coin = AddToPortfolioFragmentArgs.fromBundle(requireArguments()).coin
        val application = requireNotNull(this.activity).application

        val viewModelFactory = AddToPortfolioViewModel.Factory(application, coin)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddToPortfolioViewModel::class.java)
        binding.viewModel = viewModel

        bind(coin)

        binding.calendarView.setOnDateChangeListener { _, y, m, d ->
            val calendar = Calendar.getInstance()
            calendar.set(y, m, d)
            date = calendar.timeInMillis
        }

        binding.btnAdd.setOnClickListener {
            val amount = binding.etAmount.text.toString().toDouble()
            val price = binding.etPrice.text.toString().toDouble()
            val date = date
            val isBuyTransaction = true//binding.switchBuyOrSell.isActivated
            viewModel.addToPortfolio(coin, amount, price, date, isBuyTransaction)
        }

        viewModel.addedToPortfolio.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                Snackbar.make(binding.btnAdd, "Added", Snackbar.LENGTH_SHORT).show()
                viewModel.addToPortfolioCompleted()
                findNavController().navigate(AddToPortfolioFragmentDirections
                    .actionAddToPortfolioFragmentToPortfolioFragment()
                )
            }
        })

        return binding.root
    }

    private fun bind(coin: Coin) {
        val imgCoinLogo = binding.imgLogo
        Util.setImage(coin.id, imgCoinLogo, LogoSize.SMALL)
        binding.tvName.text = coin.name

    }

}