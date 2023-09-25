package com.example.colanportfolio.component

import androidx.databinding.DataBindingComponent
import com.example.colanportfolio.component.modules.BaseImageViewBinding

class BaseDataBindingComponent : DataBindingComponent {

    override fun getIImageViewBinding(): IImageViewBinding {
        return BaseImageViewBinding()
    }
}