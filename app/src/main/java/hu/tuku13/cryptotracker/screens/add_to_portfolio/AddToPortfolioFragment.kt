package hu.tuku13.cryptotracker.screens.add_to_portfolio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import hu.tuku13.cryptotracker.LogoSize
import hu.tuku13.cryptotracker.R
import hu.tuku13.cryptotracker.Util
import hu.tuku13.cryptotracker.databinding.FragmentAddToPortfolioBinding
import hu.tuku13.cryptotracker.domain.Coin

class AddToPortfolioFragment : Fragment() {
    private lateinit var binding: FragmentAddToPortfolioBinding
    private lateinit var viewModel: AddToPortfolioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddToPortfolioBinding.inflate(layoutInflater)

        val coin = AddToPortfolioFragmentArgs.fromBundle(requireArguments()).coin
        val application = requireNotNull(this.activity).application

        val viewModelFactory = AddToPortfolioViewModel.Factory(application, coin)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddToPortfolioViewModel::class.java)
        binding.viewModel = viewModel

        bind(coin)

        binding.btnAdd.setOnClickListener {
            val amount = binding.etAmount.text.toString().toDouble()
            val price = binding.etPrice.text.toString().toDouble()
            val date = binding.calendarView.date
        }

        return binding.root
    }

    private fun bind(coin: Coin) {
        val imgCoinLogo = binding.imgLogo
        Util.setImage(coin.id, imgCoinLogo, LogoSize.SMALL)
        binding.tvName.text = coin.name
    }

}