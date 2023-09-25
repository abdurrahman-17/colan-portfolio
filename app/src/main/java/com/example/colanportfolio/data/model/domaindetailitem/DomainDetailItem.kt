package com.example.colanportfolio.data.model.domaindetailitem

import com.google.gson.annotations.SerializedName

data class DomainDetailItem(

    @SerializedName("client_location")
    val clientLocation: String,
    @SerializedName("client_name")
    val clientName: String,
    @SerializedName("highlighted_content")
    val highlightedContent: String,
    @SerializedName("highlighted_image")
    val highlightedImage: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("overview_content")
    val overviewContent: String,
    @SerializedName("overview_image")
    val overviewImage: String,
    @SerializedName("platform_language")
    val platformLanguage: String,
    @SerializedName("project_androidapp")
    val projectAndroidapp: String,
    @SerializedName("project_database")
    val projectDatabase: String,
    @SerializedName("project_domain")
    val projectDomain: String,
    @SerializedName("project_framework")
    val projectFramework: String,
    @SerializedName("project_frontend")
    val projectFrontend: String,
    @SerializedName("project_image_logo")
    val projectImageLogo: String,
    @SerializedName("project_iosapp")
    val projectIosapp: String,
    @SerializedName("project_name")
    val projectName: String,
    @SerializedName("project_othercontent")
    val projectOthercontent: String,
    @SerializedName("screen")
    val projectScreenshots: ArrayList<String>,
    @SerializedName("project_servertool")
    val projectServertool: String,
    @SerializedName("project_technology")
    val projectTechnology: String,
    @SerializedName("project_uiux")
    val projectUiux: String,
    @SerializedName("project_Webapp")
    val projectWebapp: String,
    @SerializedName("project_Website")
    val projectWebsite: String,
    @SerializedName("related_keyword")
    val relatedKeyword: String,
    @SerializedName("technology")
    val technology: ArrayList<TechnologyListResponseItem>
)