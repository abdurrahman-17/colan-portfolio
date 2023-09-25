package com.example.colanportfolio.ui.technology

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.colanportfolio.base.BaseViewModel
import com.example.colanportfolio.data.local.SharedPreferenceImp
import com.example.colanportfolio.data.model.technology.TechnologyItem
import com.example.colanportfolio.data.repository.BaseResponse
import com.example.colanportfolio.data.repository.LoginControllerRepository
import com.example.colanportfolio.utils.Resource
import com.example.colanportfolio.utils.SingleLiveData
import javax.inject.Inject

class TechnologyViewModel@Inject constructor(application: Application) : BaseViewModel(application) {

    val mShowProgressBar = SingleLiveData<Boolean>()
    val apiCallStatus = SingleLiveData<Boolean>()
    var sharedPreferences : SharedPreferenceImp?= null
    val technologyListData = MutableLiveData<Resource<BaseResponse<List<TechnologyItem>>>>()
    var techList = ObservableArrayList<TechnologyItem>()
    var searchET = ObservableField<String>("")


    init {
        sharedPreferences = SharedPreferenceImp()
        technologyListApi()
    }

    private fun technologyListApi() {
        apiCallStatus.value = true
        mShowProgressBar.value = true
        LoginControllerRepository.getInstance().technologyListApi(technologyListData)
    }

    fun onSearchChange(s: CharSequence, start: Int, before: Int, count: Int){
        if (apiCallStatus.value == false){
            searchET.set(s.toString())
            technologySearchApi()
        }
    }

     fun technologySearchApi() {
        apiCallStatus.value = true
        mShowProgressBar.value=true
        LoginControllerRepository.getInstance().technologySearchListApi(technologyListData, search = searchET.get().toString())

    }


}