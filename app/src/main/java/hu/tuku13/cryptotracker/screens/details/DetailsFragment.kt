package hu.tuku13.cryptotracker.screens.details

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.tuku13.cryptotracker.LogoSize
import hu.tuku13.cryptotracker.R
import hu.tuku13.cryptotracker.Util
import hu.tuku13.cryptotracker.databinding.FragmentDetailsBinding
import hu.tuku13.cryptotracker.domain.Coin
import hu.tuku13.cryptotracker.repository.CoinRepository
import hu.tuku13.cryptotracker.screens.overview.OverviewFragmentDirections
import java.text.DecimalFormat
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel
    @Inject
    lateinit var repository: CoinRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        val selectedCoin = DetailsFragmentArgs.fromBundle(requireArguments()).coin

        val viewModelFactory = DetailsViewModel.Factory(repository, selectedCoin)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(DetailsViewModel::class.java)
        binding.viewModel = viewModel

        bind(selectedCoin)

        return binding.root
    }

    private fun bind(selectedCoin: Coin) {
        val imgCoinLogo = binding.imgLogo
        Util.setImage(selectedCoin.id, imgCoinLogo, LogoSize.MEDIUM)

        // bind data
        binding.tvName.text = selectedCoin.name
        binding.tvSymbol.text = selectedCoin.symbol
        binding.tvPlatform.text = selectedCoin.platform

        val priceText = requireContext().getString(R.string.coin_price, selectedCoin.price)
        binding.tvPrice.text = priceText

        val marketCapText = requireContext()
            .getString(R.string.coin_price, selectedCoin.marketCap)
        binding.tvMarketCap.text = marketCapText

        val decimalFormat = DecimalFormat("#.####")
        val formattedSupply = decimalFormat.format(selectedCoin.totalSupply)
        val supplyText = "$formattedSupply ${selectedCoin.symbol}"
        binding.tvSupply.text = supplyText

        val marketRankText = requireContext()
            .getString(R.string.market_rank_format, selectedCoin.cmcRank)
        binding.tvRank.text = marketRankText

        val dateAddedText = selectedCoin.dateAdded.substring(IntRange(0, 3)) + "." +
                selectedCoin.dateAdded.substring(IntRange(5, 6)) + "." +
                selectedCoin.dateAdded.substring(IntRange(8, 9))
        binding.tvDate.text = dateAddedText

        val priceChange1hText = requireContext()
            .getString(R.string.change_percentage_details, selectedCoin.percentChange1h)
        val colorPriceChange1h = if (selectedCoin.percentChange1h >= 0) {
            ContextCompat.getColor(requireContext(), R.color.positive_change)
        } else {
            ContextCompat.getColor(requireContext(), R.color.negative_change)
        }
        binding.tvChange1h.setTextColor(colorPriceChange1h)
        binding.tvChange1h.text = priceChange1hText

        val priceChange24hText = requireContext()
            .getString(R.string.change_percentage_details, selectedCoin.percentChange24h)
        val colorPriceChange24h = if (selectedCoin.percentChange24h >= 0) {
            ContextCompat.getColor(requireContext(), R.color.positive_change)
        } else {
            ContextCompat.getColor(requireContext(), R.color.negative_change)
        }
        binding.tvChange24h.setTextColor(colorPriceChange24h)
        binding.tvChange24h.text = priceChange24hText

        // price change 7d
        val priceChange7dText = requireContext()
            .getString(R.string.change_percentage_details, selectedCoin.percentChange7d)
        val colorPriceChange7d = if (selectedCoin.percentChange7d >= 0) {
            ContextCompat.getColor(requireContext(), R.color.positive_change)
        } else {
            ContextCompat.getColor(requireContext(), R.color.negative_change)
        }
        binding.tvChange24h.setTextColor(colorPriceChange7d)
        binding.tvChange7d.text = priceChange7dText

        val volume24hText = "$" + decimalFormat.format(selectedCoin.volume24h)
        binding.tvVolume24h.text = volume24hText

        val volumeChangeText = requireContext()
            .getString(R.string.change_percentage_details, selectedCoin.volumeChange24h)
        val colorVolumeChange24h = if (selectedCoin.volumeChange24h >= 0) {
            ContextCompat.getColor(requireContext(), R.color.positive_change)
        } else {
            ContextCompat.getColor(requireContext(), R.color.negative_change)
        }
        binding.tvVolumeChange.setTextColor(colorVolumeChange24h)
        binding.tvVolumeChange.text = volumeChangeText

        binding.imgFavourite.setOnClickListener {
            viewModel.toggleFavourite()
        }

        viewModel.isCoinFavourite.observe(viewLifecycleOwner, Observer {
            if(it) {
                binding.imgFavourite.setImageResource(R.drawable.ic_remove_from_favourites)
                val tooltipText: CharSequence = "Remove from favourites"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    binding.imgFavourite.tooltipText = tooltipText
                }
            } else {
                binding.imgFavourite.setImageResource(R.drawable.ic_add_to_favourites)
                val tooltipText: CharSequence = "Add to favourites"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    binding.imgFavourite.tooltipText = tooltipText
                }
            }
        })

        viewModel.navigateAddToPortfolio.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                this.findNavController().navigate(
                    DetailsFragmentDirections.actionDetailsFragmentToAddToPortfolioFragment(it)
                )
                viewModel.doneNavigating()
            }
        })
    }
}