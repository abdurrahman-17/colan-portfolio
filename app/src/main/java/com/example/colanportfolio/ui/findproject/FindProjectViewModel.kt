package com.example.colanportfolio.ui.findproject

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.colanportfolio.base.BaseViewModel
import com.example.colanportfolio.data.local.SharedPreferenceImp
import com.example.colanportfolio.data.model.domaindetailitem.ProjectListResponseItem
import com.example.colanportfolio.data.model.search.FindProjectItem
import com.example.colanportfolio.data.model.search.SearchRequest
import com.example.colanportfolio.data.repository.BaseResponse
import com.example.colanportfolio.data.repository.LoginControllerRepository
import com.example.colanportfolio.utils.Resource
import com.example.colanportfolio.utils.SingleLiveData
import javax.inject.Inject

class FindProjectViewModel@Inject constructor(application: Application) : BaseViewModel(application) {

    val mShowProgressBar = SingleLiveData<Boolean>()
    val apiCallStatus = SingleLiveData<Boolean>()
    var sharedPreferences : SharedPreferenceImp?= null
    val findProjectListData = MutableLiveData<Resource<BaseResponse<List<ProjectListResponseItem>>>>()
    var findProjectList = ObservableArrayList<ProjectListResponseItem>()
    val searchET = ObservableField<String>("")

    init {
        sharedPreferences = SharedPreferenceImp()
    }

    fun onSearchChange(s: CharSequence, start: Int, before: Int, count: Int) {
        searchET.set(s.toString())
        if (searchET.get() == ""){
            findProjectApi()
        }else if (searchET.get() != ""){
            apiCallStatus.value = true
        }
    }

    fun findProjectApi(){
        mShowProgressBar.value=true
        LoginControllerRepository.getInstance().findProjectListApi(findProjectListData, search = searchET.get().toString())
    }
}