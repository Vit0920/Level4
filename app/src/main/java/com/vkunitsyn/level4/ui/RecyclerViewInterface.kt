package com.vkunitsyn.level4.ui

import android.widget.ImageView

interface RecyclerViewInterface {
    fun onItemClick(imageView: ImageView, position: Int)
}