package com.example.colanportfolio.ui.testimonials

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.example.colanportfolio.base.BaseViewModel
import com.example.colanportfolio.data.local.SharedPreferenceImp
import com.example.colanportfolio.data.model.testimonial.TestimonialItem
import com.example.colanportfolio.data.repository.BaseResponse
import com.example.colanportfolio.data.repository.LoginControllerRepository
import com.example.colanportfolio.utils.Resource
import com.example.colanportfolio.utils.SingleLiveData
import javax.inject.Inject

class TestimonialViewModel@Inject constructor(application: Application) : BaseViewModel(application) {

    val mShowProgressBar = SingleLiveData<Boolean>()
    var sharedPreferences : SharedPreferenceImp?= null
    val testimonialListData = MutableLiveData<Resource<BaseResponse<List<TestimonialItem>>>>()
    var testimonialList = ObservableArrayList<TestimonialItem>()

    init {
        sharedPreferences = SharedPreferenceImp()
        testimonialListApi()
    }

    private fun testimonialListApi() {
        mShowProgressBar.value = true
        LoginControllerRepository.getInstance().testimonialListApi(testimonialListData)


    }


}