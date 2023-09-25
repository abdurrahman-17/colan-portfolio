package com.example.colanportfolio.ui.contact

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.colanportfolio.base.BaseViewModel
import com.example.colanportfolio.data.model.contact.ContactResponse
import com.example.colanportfolio.data.model.register.RegisterRequest
import com.example.colanportfolio.data.repository.BaseResponse
import com.example.colanportfolio.data.repository.LoginControllerRepository
import com.example.colanportfolio.utils.Resource
import com.example.colanportfolio.utils.SingleLiveData
import javax.inject.Inject

class ContactViewModel@Inject constructor(application: Application) : BaseViewModel(application)  {


    val mShowProgressBar = SingleLiveData<Boolean>()
    val apiClear = ObservableField<Boolean>()
    val registerDataResponse = MutableLiveData<Resource<BaseResponse<ContactResponse>>>()


    val firstName = ObservableField<String>("")
    val LastName = ObservableField<String>("")
    val email = ObservableField<String>("")
    val phone = ObservableField<String>("")
    val company = ObservableField<String>("")
    val location = ObservableField<String>("")
    val expectation = ObservableField<String>("")
    val phoneCode = ObservableField<String>("")

    fun registerApi(){
        mShowProgressBar.value = true
        apiClear.set(true)
        LoginControllerRepository.getInstance().registerUser(registerDataResponse,firstName.get().toString(),LastName.get().toString(),
            email.get().toString(),phone.get().toString(),company.get().toString(),location.get().toString(),expectation.get().toString(),phoneCode.get().toString())
    }


}