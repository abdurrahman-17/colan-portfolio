package com.example.colanportfolio.data.model.testimonial

import com.google.gson.annotations.SerializedName

data class TestimonialItem(

    @SerializedName("id")
    val id :String,
    @SerializedName("project_name")
    val project_name:String,
    @SerializedName("client_name")
    val client_name:String,
    @SerializedName("client_location")
    val client_location:String,
    @SerializedName("client_comment")
    val client_comment:String,
    @SerializedName("video_pre")
    val video_pre:String,
    @SerializedName("user_imag")
    val user_imag:String,
    @SerializedName("created_on")
    val created_on:String

)