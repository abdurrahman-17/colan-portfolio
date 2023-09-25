package com.example.colanportfolio.ui.technolgy_keywords

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.example.colanportfolio.base.BaseViewModel
import com.example.colanportfolio.data.model.domaindetailitem.TechnologyListResponseItem
import com.example.colanportfolio.data.model.projectdetails.ProjectTechnology
import com.example.colanportfolio.data.model.projectdetails.TechnologyFramework
import javax.inject.Inject

class TechnologyKeywordViewModel@Inject constructor(application: Application) : BaseViewModel(application) {
    var technologyListData = ObservableArrayList<ProjectTechnology>()
    var frameworkList = ObservableArrayList<TechnologyFramework>()
    var name = ObservableField<String>("")
    var projectName = ObservableField<String>("")
    var projectImage = ObservableField<String>("")
    var keywords = ObservableField<String>("")
    var sub_title = ObservableField<String>("")
    var framework = ObservableField<String>("")
}