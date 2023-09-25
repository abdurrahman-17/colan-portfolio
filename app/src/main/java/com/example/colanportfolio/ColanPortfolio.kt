package com.example.colanportfolio

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.example.colanportfolio.component.BaseDataBindingComponent

class ColanPortfolio : Application() {

    companion object{
        private var mInstance : ColanPortfolio? = null

        fun getInstance() : ColanPortfolio? {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        DataBindingUtil.setDefaultComponent(BaseDataBindingComponent())
    }

}