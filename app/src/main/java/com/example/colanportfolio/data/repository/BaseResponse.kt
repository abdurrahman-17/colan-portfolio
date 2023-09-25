package com.example.colanportfolio.data.repository

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("status")
    val isSuccess : Boolean,
    @SerializedName("message")
    val message : String,
    @SerializedName("data")
    val response : T
)