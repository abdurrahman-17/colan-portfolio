package com.example.colanportfolio.data.model.domain

import com.google.gson.annotations.SerializedName

data class DomainDataItem(
    @SerializedName("domain_id")
    val domainId: String,
    @SerializedName("domain_name")
    val domainName: String,
    @SerializedName("image")
    val image: String
)