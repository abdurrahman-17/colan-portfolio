package com.example.colanportfolio.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.colanportfolio.data.api.PortfolioClientBuilder
import com.example.colanportfolio.data.model.contact.ContactResponse
import com.example.colanportfolio.data.model.domain.DomainDataItem
import com.example.colanportfolio.data.model.domaindetailitem.DomainDetailItem
import com.example.colanportfolio.data.model.domaindetailitem.DomainDetailRequest
import com.example.colanportfolio.data.model.domaindetailitem.ProjectListResponseItem
import com.example.colanportfolio.data.model.projectdetails.ProjectDetailsResponse
import com.example.colanportfolio.data.model.register.RegisterRequest
import com.example.colanportfolio.data.model.search.FindProjectItem
import com.example.colanportfolio.data.model.search.SearchRequest
import com.example.colanportfolio.data.model.technology.TechnologyItem
import com.example.colanportfolio.data.model.testimonial.TestimonialItem
import com.example.colanportfolio.utils.Resource

class LoginControllerRepository : BaseRepository() {
    private var apiService = PortfolioClientBuilder.Companion.ServicesApiInterface

    companion object {
        @Volatile
        private var instance: LoginControllerRepository? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: LoginControllerRepository().also { instance = it }
        }
    }

    fun domainListApi(responseData : MutableLiveData<Resource<BaseResponse<List<DomainDataItem>>>>) {
        apiService.loginUser()?.domainListApi()?.enqueue(getCallbackList(responseData))
    }

    fun domainSearchListApi(responseData : MutableLiveData<Resource<BaseResponse<List<DomainDataItem>>>>,search:String) {
        apiService.loginUser()?.domainSearchListApi(search)?.enqueue(getCallbackList(responseData))
    }

    fun technologyListApi(responseData : MutableLiveData<Resource<BaseResponse<List<TechnologyItem>>>>){
        apiService.loginUser()?.technologyListApi()?.enqueue(getCallbackList(responseData))
    }

    fun technologySearchListApi(responseData : MutableLiveData<Resource<BaseResponse<List<TechnologyItem>>>>,search: String){
        apiService.loginUser()?.technologySearchListApi(search)?.enqueue(getCallbackList(responseData))
    }

    fun testimonialListApi(responseData : MutableLiveData<Resource<BaseResponse<List<TestimonialItem>>>>){
        apiService.loginUser()?.testimonialListApi()?.enqueue(getCallbackList(responseData))
    }

    fun findProjectListApi(responseData : MutableLiveData<Resource<BaseResponse<List<ProjectListResponseItem>>>>,search: String){
        apiService.loginUser()?.findProjectListApi(search)?.enqueue(getCallbackList(responseData))
    }

    fun domainDetailListApi(responseData : MutableLiveData<Resource<BaseResponse<List<ProjectListResponseItem>>>>,domain_id : String){
        apiService.loginUser()?.domainDetailListApi(domain_id)?.enqueue(getCallbackList(responseData))
    }

    fun technologyDetailListApi(responseData : MutableLiveData<Resource<BaseResponse<List<ProjectListResponseItem>>>>,technology_id :String){
        apiService.loginUser()?.technologyDetailListApi(technology_id)?.enqueue(getCallbackList(responseData))
    }

    fun registerUser(responseData: MutableLiveData<Resource<BaseResponse<ContactResponse>>>,first_name:String,last_name:String,email:String,phone:String,company:String,location:String,expectation:String,isd_code:String){
        apiService.loginUser()?.registerUser(first_name, email, phone, company, location, last_name, expectation,isd_code)?.enqueue(getCallback(responseData))
    }

    fun projectDetailsApi(responseData: MutableLiveData<Resource<BaseResponse<ProjectDetailsResponse>>>, id : String) {
        apiService.loginUser()?.projectDetailsApi(id)?.enqueue(getCallback(responseData))
    }
}