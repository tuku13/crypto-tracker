package hu.tuku13.cryptotracker.screens.portfolio.info

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import hu.tuku13.cryptotracker.databinding.FragmentPortfolioInfoBinding
import hu.tuku13.cryptotracker.repository.CoinRepository
import javax.inject.Inject

@AndroidEntryPoint
class PortfolioInfoFragment : Fragment() {
    private lateinit var binding : FragmentPortfolioInfoBinding
    private lateinit var viewModel : PortfolioInfoViewModel
    @Inject
    lateinit var repository: CoinRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPortfolioInfoBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        val viewModelFactory = PortfolioInfoViewModel.Factory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PortfolioInfoViewModel::class.java)

        var id = 0

        viewModel.coinWithPortfolioTransactions.observe(viewLifecycleOwner, Observer { it ->
            val pieEntries = mutableListOf<PieEntry>()
            val barEntries = mutableListOf<BarEntry>()
            it.forEach {
                val value = it.value.sumOf { t ->
                    t.amount * t.price
                }
                val pieEntry = PieEntry(value.toFloat(), it.key.symbol)
                pieEntries.add(pieEntry)

                val barEntry = BarEntry((++id).toFloat(), value.toFloat())
                barEntries.add(barEntry)
            }
            val pieDataSet = PieDataSet(pieEntries, "Coins")
            pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

            val pieData = PieData(pieDataSet)
            binding.pieChart.data = pieData

            binding.pieChart.holeRadius = 0F
            binding.pieChart.setTransparentCircleAlpha(0)
            binding.pieChart.setUsePercentValues(false)
            binding.pieChart.invalidate()

            val barDataSet = BarDataSet(barEntries, "Coins")
            barDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
            barDataSet.valueTextColor = Color.BLACK

            val barData = BarData(barDataSet)
            binding.barChart.data = barData
            binding.barChart.invalidate()

        })

        return binding.root
    }

}