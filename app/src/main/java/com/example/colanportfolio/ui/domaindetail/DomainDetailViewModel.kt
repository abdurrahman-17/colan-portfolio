package com.example.colanportfolio.ui.domaindetail

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.colanportfolio.base.BaseViewModel
import com.example.colanportfolio.data.local.SharedPreferenceImp
import com.example.colanportfolio.data.model.domaindetailitem.DomainDetailItem
import com.example.colanportfolio.data.model.domaindetailitem.ProjectListResponseItem
import com.example.colanportfolio.data.repository.BaseResponse
import com.example.colanportfolio.data.repository.LoginControllerRepository
import com.example.colanportfolio.utils.Resource
import com.example.colanportfolio.utils.SingleLiveData
import javax.inject.Inject

class DomainDetailViewModel@Inject constructor(application: Application) : BaseViewModel(application) {

    val mShowProgressBar = SingleLiveData<Boolean>()
    val backNavigation = SingleLiveData<Boolean>()
    var sharedPreferences : SharedPreferenceImp?= null
    val domainDetailListData = MutableLiveData<Resource<BaseResponse<List<ProjectListResponseItem>>>>()
    var domainDetailList = ObservableArrayList<ProjectListResponseItem>()
    var domainId= ObservableField<String>("")
    var type= ObservableField<String>("")
    var domainName= ObservableField<String>("")
    var domainImage= ObservableField<String>("")
    var technologyId= ObservableField<String>("")


    init {
        sharedPreferences = SharedPreferenceImp()
    }

    fun domainDetailListApi() {
        mShowProgressBar.value = true
        Log.d("domain_id",domainId.get().toString())
        LoginControllerRepository.getInstance().domainDetailListApi(domainDetailListData,domainId.get().toString())
    }

    fun technologyDetailListApi() {
        mShowProgressBar.value = true
        LoginControllerRepository.getInstance().technologyDetailListApi(domainDetailListData,technologyId.get().toString())
    }

    fun onClickBack(){
        backNavigation.value = true
    }


}