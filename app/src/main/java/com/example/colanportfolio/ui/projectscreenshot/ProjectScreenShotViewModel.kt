package com.example.colanportfolio.ui.projectscreenshot

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.example.colanportfolio.base.BaseViewModel
import com.example.colanportfolio.data.local.SharedPreferenceImp
import com.example.colanportfolio.data.model.domaindetailitem.ProjectScreenShotsModel
import com.example.colanportfolio.utils.SingleLiveData
import javax.inject.Inject

class ProjectScreenShotViewModel@Inject constructor(application: Application) : BaseViewModel(application) {

    val backNavigation = SingleLiveData<Boolean>()
    var name = ObservableField<String>("")
    var project_image = ObservableField<String>("")
    var project_name = ObservableField<String>("")
    var projectScreenShot = ObservableArrayList<String>()
    var screenshot = ArrayList<ProjectScreenShotsModel>()
    var sharedPreferences : SharedPreferenceImp?= null


    init {
        sharedPreferences = SharedPreferenceImp()
    }




}