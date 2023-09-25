package com.example.colanportfolio.ui.liveurl

import android.app.Application
import androidx.databinding.ObservableField
import com.example.colanportfolio.base.BaseViewModel
import com.example.colanportfolio.utils.SingleLiveData
import javax.inject.Inject

class LiveUrlViewmodel@Inject constructor(application: Application) : BaseViewModel(application) {

    val backNavigation = SingleLiveData<Boolean>()


    var name= ObservableField<String>("")
    var image= ObservableField<String>("")
    var project_name= ObservableField<String>("")
    var webite_link= ObservableField<String>("")
    var webApp_link= ObservableField<String>("")
    var android= ObservableField<String>("")
    var ios= ObservableField<String>("")

    fun onClickBack(){
        backNavigation.value = true
    }
}