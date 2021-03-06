package hu.tuku13.cryptotracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import hu.tuku13.cryptotracker.R
import hu.tuku13.cryptotracker.Util
import hu.tuku13.cryptotracker.domain.Coin
import hu.tuku13.cryptotracker.screens.overview.OverviewViewModel

class CoinAdapter(
    private val onClickListener: OnClickListener
    ): RecyclerView.Adapter<CoinAdapter.ViewHolder>() {
    var coinsList : List<Coin> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private lateinit var parentContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.coin_list_item, parent, false)
        parentContext = parent.context

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = coinsList[position]
        holder.item = item
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

        Util.setImage(item.id, holder.coinLogo)
        holder.parentLayout.setOnClickListener{
            onClickListener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return coinsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var item: Coin
        val coinRank: TextView = itemView.findViewById(R.id.coin_rank)
        val name: TextView = itemView.findViewById(R.id.name)
        val symbol: TextView = itemView.findViewById(R.id.symbol)
        val price: TextView = itemView.findViewById(R.id.price)
        val percentChange24h: TextView = itemView.findViewById(R.id.percent_change_24h)
        val coinLogo: ImageView = itemView.findViewById(R.id.coin_logo)
        val parentLayout: LinearLayout = itemView.findViewById(R.id.parent_layout)
    }

    class OnClickListener(val clickListener: (coin: Coin) -> Unit ) {
        fun onClick(coin: Coin) = clickListener(coin)
    }
}