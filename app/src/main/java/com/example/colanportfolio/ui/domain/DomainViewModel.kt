package com.example.colanportfolio.ui.domain

import android.app.Application
import androidx.databinding.Observable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.colanportfolio.base.BaseViewModel
import com.example.colanportfolio.data.local.SharedPreferenceImp
import com.example.colanportfolio.data.model.domain.DomainDataItem
import com.example.colanportfolio.data.repository.BaseResponse
import com.example.colanportfolio.data.repository.LoginControllerRepository
import com.example.colanportfolio.utils.Resource
import com.example.colanportfolio.utils.SingleLiveData
import javax.inject.Inject

class DomainViewModel@Inject constructor(application: Application) : BaseViewModel(application)  {

    val mShowProgressBar = SingleLiveData<Boolean>()
    val apiCallStatus = SingleLiveData<Boolean>()
    var sharedPreferences : SharedPreferenceImp ?= null
    val domainListData = MutableLiveData<Resource<BaseResponse<List<DomainDataItem>>>>()
    var domainList = ArrayList<DomainDataItem>()
    var searchET = ObservableField<String>("")

    init {
        sharedPreferences = SharedPreferenceImp()
        domainListApi()
    }

    fun domainListApi(){
        apiCallStatus.value = true
        mShowProgressBar.value = true
        LoginControllerRepository.getInstance().domainListApi(domainListData)
    }

    fun onSearchChange(s: CharSequence, start: Int, before: Int, count: Int){
        if (apiCallStatus.value == false){
            searchET.set(s.toString())
            domainSearchApi()
        }
    }

     fun domainSearchApi() {
        apiCallStatus.value = true
        mShowProgressBar.value=true
        LoginControllerRepository.getInstance().domainSearchListApi(domainListData, search = searchET.get().toString())
    }

    private fun domainFilter(search : String){

    }

}