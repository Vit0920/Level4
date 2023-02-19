package com.vkunitsyn.level4.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.addPictureGlide(pic: Any?) {
    Glide.with(this)
        .load(pic)
        .circleCrop()
        .override(400, 400)
        .into(this)
}


fun Context.dpToPx(dp: Float):Float{
    return dp * this.resources.displayMetrics.density
}