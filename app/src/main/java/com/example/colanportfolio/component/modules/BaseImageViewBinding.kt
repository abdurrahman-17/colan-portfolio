package com.example.colanportfolio.component.modules

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.colanportfolio.R
import com.example.colanportfolio.component.IImageViewBinding

class BaseImageViewBinding : IImageViewBinding {

    override fun setImageFromDrawable(imageView: ImageView, filePath: Int?) {
        Glide.with(imageView.context).load(filePath).into(imageView)
    }

    override fun setImageFromUrl(imageView: ImageView, filePath: String?) {
        val option: RequestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.no_img_available)
                .fitCenter()

        Glide.with(imageView.context)
                .load(filePath)
                .apply(option)
            .fitCenter()
                .into(imageView)
    }
}