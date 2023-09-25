package com.example.colanportfolio.data.api

import com.example.colanportfolio.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response



abstract class NetworkConnectionInterceptor: Interceptor {

    abstract fun isInternetAvailable(): Boolean

    abstract fun onInternetUnavailable()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!Constants.isNetworkConnected) {
            onInternetUnavailable()
        }

        return chain.proceed(request)
    }
}