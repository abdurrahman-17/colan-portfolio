package com.example.colanportfolio.data.api
import com.example.colanportfolio.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class ApiInterceptor :
    Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // try the request
        val newRequest = request.newBuilder()
            .header(Constants.ACCEPT_KEY, Constants.APPLICATION_JSON)
            .build()
        return chain.proceed(newRequest)
    }


}