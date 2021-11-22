package hu.tuku13.cryptotracker

import android.widget.ImageView
import com.bumptech.glide.Glide

// https://s2.coinmarketcap.com/static/img/coins/64x64/{id}.png
private val IMAGE_BASE_URL = "https://s2.coinmarketcap.com/static/img/coins/64x64/"

object Util {
    fun setImage(id: Int, imageView: ImageView ) {
        Glide
            .with(imageView)
            .load("$IMAGE_BASE_URL$id.png")
            .placeholder(R.drawable.ic_coin_placeholder)
            .into(imageView)
    }
}