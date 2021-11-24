package hu.tuku13.cryptotracker

import android.widget.ImageView
import com.bumptech.glide.Glide

// https://s2.coinmarketcap.com/static/img/coins/64x64/{id}.png
private val IMAGE_BASE_URL = "https://s2.coinmarketcap.com/static/img/coins/"

private val SMALL = 64
private val LARGE = 200

enum class LogoSize(val size: Int) {
    SMALL(64),
    MEDIUM(128),
    LARGE(200)
}

object Util {
    fun setImage(coinId: Int, imageView: ImageView, logoSize: LogoSize = LogoSize.SMALL) {
        Glide
            .with(imageView)
            .load("$IMAGE_BASE_URL${logoSize.size}x${logoSize.size}/${coinId}.png")
            .placeholder(R.drawable.ic_coin_placeholder)
            .into(imageView)
    }
}