package com.example.colanportfolio.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.colanportfolio.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

open class BaseRepository {
    private val noNetworkMsg = "No internet connection or network failure"

    fun <T> getCallback(responseData: MutableLiveData<Resource<BaseResponse<T>>>): Callback<BaseResponse<T>> {
        return object : Callback<BaseResponse<T>> {
            override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {
                val msg = if (t is IOException) noNetworkMsg else t.message!!
                responseData.value = Resource.error(msg, null)
                System.out.println("getCallBackaaa"+"response.code()")
            }

            override fun onResponse(call: Call<BaseResponse<T>>, response: Response<BaseResponse<T>>) {
                if (response.isSuccessful && response.body() != null) {
                    responseData.value = Resource.success(response.body()!!)
                    System.out.println("getCallBack::::"+response.code())
                } else if (response.errorBody() != null) {
                    //val errorResponse = getErrorResponse<T>(response.errorBody()!!)
                    System.out.println("getCallBack"+response.code())
                     responseData.value = Resource.error(response.code().toString(), null)
                    if (response.code() == 204){
                        responseData.value = Resource.error(response.code().toString(), null)
                    }
                }

            }

        }
    }

    fun <T> getCallbackList(responseData: MutableLiveData<Resource<BaseResponse<List<T>>>>): Callback<BaseResponse<List<T>>> {
        return object : Callback<BaseResponse<List<T>>> {
            override fun onFailure(call: Call<BaseResponse<List<T>>>, t: Throwable) {
                val msg = if (t is IOException) noNetworkMsg else t.message!!
                responseData.value = Resource.error(msg, null)
            }

            override fun onResponse(call: Call<BaseResponse<List<T>>>, response: Response<BaseResponse<List<T>>>) {

                if (response.isSuccessful && response.body() != null) {
                    responseData.value = Resource.success(response.body()!!)
                } /*else if (response.errorBody() != null) {
                    val errorResponse = getErrorResponse<List<T>>(response.errorBody()!!)
                    System.out.println("getCallBackList"+response.code())
                    responseData.value = Resource.error(errorResponse.message, errorResponse)
                }*/
            }
        }
    }

}