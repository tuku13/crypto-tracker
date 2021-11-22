package hu.tuku13.cryptotracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import hu.tuku13.cryptotracker.R
import hu.tuku13.cryptotracker.Util
import hu.tuku13.cryptotracker.domain.Coin
import hu.tuku13.cryptotracker.domain.testBTC
import hu.tuku13.cryptotracker.domain.testETH

class CoinAdapter: RecyclerView.Adapter<CoinAdapter.ViewHolder>() {
    //listOf(testBTC, testETH, testETH, testBTC, testBTC, testBTC, testETH, testETH, testETH, testETH, testETH, testBTC)
    var coinsList : List<Coin> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private lateinit var parentContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.coin_list_item,parent,false)
        parentContext = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = coinsList[position]
        holder.coinRank.text = "${position + 1}"
        holder.name.text = item.name
        holder.symbol.text = item.symbol

        val priceText = parentContext.getString(R.string.coin_price, item.price)
        holder.price.text = priceText

        val percentChangeText = parentContext
            .getString(R.string.change_percentage, item.percentChange24h)
        holder.percentChange24h.text = percentChangeText

        if(item.percentChange24h >= 0) {
            val color = ContextCompat.getColor(parentContext, R.color.positive_change)
            holder.percentChange24h.setTextColor(color)
        } else {
            val color = ContextCompat.getColor(parentContext, R.color.negative_change)
            holder.percentChange24h.setTextColor(color)
        }

        //holder.marketCap.text = "$${item.market_cap / 1000000}m"
        Util.setImage(item.id, holder.coinLogo)
    }

    override fun getItemCount(): Int {
        return coinsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val coinRank: TextView = itemView.findViewById(R.id.coin_rank)
        val name: TextView = itemView.findViewById(R.id.name)
        val symbol: TextView = itemView.findViewById(R.id.symbol)
        val price: TextView = itemView.findViewById(R.id.price)
        val percentChange24h: TextView = itemView.findViewById(R.id.percent_change_24h)
        //val marketCap: TextView = itemView.findViewById(R.id.market_cap)
        val coinLogo: ImageView = itemView.findViewById(R.id.coin_logo)
    }
}