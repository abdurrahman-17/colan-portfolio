package com.example.colanportfolio.utils

import com.example.colanportfolio.data.model.domaindetailitem.TechnologyListResponseItem
import com.example.colanportfolio.data.model.projectdetails.ProjectTechnology
import com.example.colanportfolio.data.model.projectdetails.TechnologyFramework

object Constants {
    var isNetworkConnected : Boolean = false

    const val  APPLICATION_JSON="application/json"
    const val  ACCEPT_KEY = "Accept"
    const val  DOMAIN_ID = "DOMAIN_ID"
    val technology = ArrayList<ProjectTechnology>()
    val framework = ArrayList<TechnologyFramework>()
}