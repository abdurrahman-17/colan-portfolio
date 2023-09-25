package com.example.colanportfolio.data.api

import com.example.colanportfolio.BuildConfig
import com.example.colanportfolio.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PortfolioClientBuilder private constructor(){

    companion object{

        private const val API_BASE_URL = BuildConfig.HOST
        private var okHttpClient: OkHttpClient? = null
        private const val CONNECT_TIMEOUT = 15
        private const val READ_TIMEOUT = 5
        private const val WRITE_TIMEOUT = 5

        private var clientBuilder: PortfolioClientBuilder? = null
        var internetConnectionListener: InternetConnectionListener? = null

        private val httpLoggingInterceptor = HttpLoggingInterceptor()
        private val apiInterceptor = ApiInterceptor()

        private var retrofit: Retrofit? = null

        private val builder = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())

        init {
            if (BuildConfig.DEBUG) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            }
        }

        fun getInstance(): PortfolioClientBuilder {

            if (clientBuilder == null) {

                clientBuilder = PortfolioClientBuilder()

                val httpClient = OkHttpClient.Builder()

                if (BuildConfig.DEBUG) {
                    httpClient.addInterceptor(httpLoggingInterceptor)
                    // httpClient.addInterceptor(apiInterceptor)
                    //httpClient.hostnameVerifier { _, _ -> true }
                    //httpClient.addNetworkInterceptor(StethoInterceptor())
                }
                val builder = Retrofit.Builder().baseUrl(BuildConfig.HOST)
                        .addConverterFactory(
                                GsonConverterFactory
                                        .create(GsonBuilder().setLenient().create())
                        )
                retrofit = builder.client(httpClient.build()).build()

            }

            return clientBuilder!!
        }

        fun <A> createApi(apiClass: Class<A>): A? {

            okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.MINUTES)
                    .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.MINUTES)
                    .addInterceptor(object : NetworkConnectionInterceptor() {
                        override fun isInternetAvailable(): Boolean {
                            internetConnectionListener?.onInternetConnection(Constants.isNetworkConnected)
                            return Constants.isNetworkConnected
                        }

                        override fun onInternetUnavailable() {
                            if (internetConnectionListener != null) {
                                internetConnectionListener?.onInternetConnection(false)
                            }
                        }

                    })
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(apiInterceptor)
                    .cookieJar(UvCookieJar())
                    .build()
            retrofit = builder.client(okHttpClient!!).build()
            return retrofit?.create(apiClass)
        }

        private class UvCookieJar : CookieJar {

            private val cookies = mutableListOf<Cookie>()

            override fun saveFromResponse(url: HttpUrl, cookieList: List<Cookie>) {
                cookies.clear()
                cookies.addAll(cookieList)
            }

            override fun loadForRequest(url: HttpUrl): List<Cookie> =
                    cookies
        }

        interface ServicesApiInterface {
            companion object {
                fun loginUser(): ILoginController? = createApi(ILoginController::class.java)
                /*fun shipperDashboard() : IShipperDashboard? = createApi(IShipperDashboard::class.java)
                fun carrierDashboard() : ICarrierDashboard? = createApi(ICarrierDashboard::class.java)*/
            }
        }

    }
}