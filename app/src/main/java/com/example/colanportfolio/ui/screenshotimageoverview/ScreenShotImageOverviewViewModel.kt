package com.example.colanportfolio.ui.screenshotimageoverview

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.example.colanportfolio.base.BaseViewModel
import com.example.colanportfolio.data.local.SharedPreferenceImp
import com.example.colanportfolio.data.model.domaindetailitem.ProjectScreenShotsModel
import com.example.colanportfolio.data.model.domaindetailitem.ScreenShotImageItem
import com.example.colanportfolio.utils.SingleLiveData
import javax.inject.Inject

class ScreenShotImageOverviewViewModel@Inject constructor(application: Application) : BaseViewModel(application) {

    val backNavigation = SingleLiveData<Boolean>()
    var image = ObservableField<String>("")
    var projectScreenShot = ObservableArrayList<String>()
    var screenshot = ArrayList<ScreenShotImageItem>()
    var sharedPreferences : SharedPreferenceImp?= null

    init {
        sharedPreferences = SharedPreferenceImp()
    }

    fun onClickBack(){
        backNavigation.value = true
    }

}

