package com.example.colanportfolio.data.model.technology

import com.google.gson.annotations.SerializedName

data class TechnologyItem(

    @SerializedName("technology_id")
    val technology_id :String,
    @SerializedName("technology_name")
    val technology_name:String,
    @SerializedName("technology_type")
    val technology_type:String,
    @SerializedName("keywords")
    val keywords:String,
    @SerializedName("image")
    val image:String
)