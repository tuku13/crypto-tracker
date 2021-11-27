package hu.tuku13.cryptotracker.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.tuku13.cryptotracker.LogoSize
import hu.tuku13.cryptotracker.R
import hu.tuku13.cryptotracker.Util
import hu.tuku13.cryptotracker.domain.Coin
import hu.tuku13.cryptotracker.domain.PortfolioTransaction
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class PortfolioAdapter(
    private val onClickListener: OnClickListener
    ): RecyclerView.Adapter<PortfolioAdapter.ViewHolder>() {

    private lateinit var parentContext: Context

    var portfolio : List<PortfolioTransaction> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
            Log.d("SET portfolio", "meret: ${value.size}")
        }

    var coins : List<Coin> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
            Log.d("SET coins", "coins: ${coins.size}")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.portfolio_list_item, parent, false)
        parentContext = parent.context

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = portfolio[position]
        val coin = getCoin(item.coinId)
        holder.item = item
        Util.setImage(item.coinId, holder.logo, LogoSize.SMALL)
        holder.symbol.text = coin.symbol

        val decimalFormat = DecimalFormat("#.######")
        holder.amount.text = decimalFormat.format(item.amount)

        holder.name.text = coin.name
        holder.price.text = parentContext
            .getString(R.string.coin_price, item.price)

        holder.date.text = convertLongToTime(item.date) //TODO szepen formazni

        holder.btnDelete.setOnClickListener {
            onClickListener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return portfolio.size
    }

    private fun getCoin(id: Int) : Coin {
        return coins.first {
            it.id == id
        }
    }
    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.format(date)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var item: PortfolioTransaction
        val logo: ImageView = itemView.findViewById(R.id.logo)
        val symbol: TextView = itemView.findViewById(R.id.symbol)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val date: TextView = itemView.findViewById(R.id.date)
        val btnDelete: ImageView = itemView.findViewById(R.id.delete)
    }

    class OnClickListener(val clickListener: (transaction: PortfolioTransaction) ->Unit) {
        fun onClick(transaction: PortfolioTransaction) = clickListener(transaction)
    }
}