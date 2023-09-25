package com.example.colanportfolio.data.model.projectdetails

data class ProjectDetailsResponse(
    val client_location: String,
    val client_name: String,
    val highlighted_content: String,
    val highlighted_image: String,
    val id: String,
    val overview_content: String,
    val overview_image: String,
    val project_Webapp: String,
    val project_Website: String,
    val project_androidapp: String,
    val project_domain: List<ProjectDomain>,
    val project_domain_id: String,
    val project_image_logo: String,
    val project_iosapp: String,
    val project_name: String,
    val project_othercontent: String,
    val project_technology: List<ProjectTechnology>,
    val related_keyword: String,
    val screen: List<String>,
    val technology_frameworks: List<TechnologyFramework>
)