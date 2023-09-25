package com.example.colanportfolio.ui.projectdeatil

import android.app.Application
import androidx.databinding.ObservableField
import com.example.colanportfolio.base.BaseViewModel
import com.example.colanportfolio.utils.SingleLiveData
import javax.inject.Inject

class ProjectDetailViewModel@Inject constructor(application: Application) : BaseViewModel(application)  {



    var name= ObservableField<String>("")
    var project_name= ObservableField<String>("")
    var project_image= ObservableField<String>("")
    var content= ObservableField<String>("")
    var image= ObservableField<String>("")

}