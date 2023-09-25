package com.example.colanportfolio.data.api

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
import com.example.colanportfolio.data.repository.BaseResponse
import retrofit2.Call
import retrofit2.http.*

interface ILoginController {

    @GET("colan_portfolio/api/login/get_domains_api.php")
    fun domainListApi() : Call<BaseResponse<List<DomainDataItem>>>

    @GET("colan_portfolio/api/login/get_domains_api.php")
    fun domainSearchListApi(@Query("search") search: String) : Call<BaseResponse<List<DomainDataItem>>>

    @GET("colan_portfolio/api/login/get_technology_api.php")
    fun technologyListApi():Call<BaseResponse<List<TechnologyItem>>>

    @GET("colan_portfolio/api/login/get_technology_api.php")
    fun technologySearchListApi(@Query("search") search: String):Call<BaseResponse<List<TechnologyItem>>>

    @GET("colan_portfolio/api/login/get_testimonial_api.php")
    fun testimonialListApi():Call<BaseResponse<List<TestimonialItem>>>

    @FormUrlEncoded
    @POST("colan_portfolio/api/login/search_projects_api.php")
    fun findProjectListApi( @Field("search") search : String ):Call<BaseResponse<List<ProjectListResponseItem>>>

    @FormUrlEncoded
    @POST("colan_portfolio/api/login/get_domain_projects_list_api.php")
    fun domainDetailListApi( @Field("domain_id") domain_id : String ):Call<BaseResponse<List<ProjectListResponseItem>>>

    @FormUrlEncoded
    @POST("colan_portfolio/api/login/get_technology_projects_list_api.php")
    fun technologyDetailListApi( @Field("technology_id") technology_id : String ):Call<BaseResponse<List<ProjectListResponseItem>>>

    @FormUrlEncoded
    @POST("contact_app/insertapi.php")
    fun registerUser(
        @Field("first_name") first_name:String,
        @Field("email") email:String,
        @Field("phone") phone:String,
        @Field("company") company:String,
        @Field("location") location:String,
        @Field("last_name") last_name:String,
        @Field("expectation") expectation:String,
        @Field("isd_code") isd_code:String
    ):Call<BaseResponse<ContactResponse>>

    @FormUrlEncoded
    @POST("colan_portfolio/api/login/get_projects_details_api.php")
    fun projectDetailsApi(@Field("id") id : String ):Call<BaseResponse<ProjectDetailsResponse>>

}