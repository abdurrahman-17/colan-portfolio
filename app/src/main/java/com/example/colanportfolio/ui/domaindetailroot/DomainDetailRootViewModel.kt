package com.example.colanportfolio.ui.domaindetailroot

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.colanportfolio.base.BaseViewModel
import com.example.colanportfolio.data.local.SharedPreferenceImp
import com.example.colanportfolio.data.model.domain.DomainDataItem
import com.example.colanportfolio.data.model.domaindetailitem.ProjectScreenShotsModel
import com.example.colanportfolio.data.model.projectdetails.ProjectDetailsResponse
import com.example.colanportfolio.data.model.projectdetails.ProjectTechnology
import com.example.colanportfolio.data.model.projectdetails.TechnologyFramework
import com.example.colanportfolio.data.model.technology.TechnologyItem
import com.example.colanportfolio.data.repository.BaseResponse
import com.example.colanportfolio.data.repository.LoginControllerRepository
import com.example.colanportfolio.ui.dialogbox.CustomDialog
import com.example.colanportfolio.utils.Resource
import com.example.colanportfolio.utils.SingleLiveData
import javax.inject.Inject


class DomainDetailRootViewModel@Inject constructor(application: Application) : BaseViewModel(application)  {

    val mShowProgressBar = SingleLiveData<Boolean>()
    val backNavigation = SingleLiveData<Boolean>()
    val projectDetails = MutableLiveData<Resource<BaseResponse<ProjectDetailsResponse>>>()

    var sharedPreferences : SharedPreferenceImp?= null
    var name= ObservableField<String>("")
    var clientLocation= ObservableField<String>("")
    var clientName= ObservableField<String>("")
    var highlightedContent= ObservableField<String>("")
    var highlightedImage= ObservableField<String>("")
    var id= ObservableField<String>("")
    var overviewContent= ObservableField<String>("")
    var overviewImage= ObservableField<String>("")
    var platformLanguage= ObservableField<String>("")
    var projectAndroidapp= ObservableField<String>("")
    var projectDatabase= ObservableField<String>("")
    var projectDomain= ObservableField<String>("")
    var projectFramework= ObservableField<String>("")
    var projectFrontend= ObservableField<String>("")
    var projectImageLogo= ObservableField<String>("")
    var projectIosapp= ObservableField<String>("")
    var projectName= ObservableField<String>("")
    var projectOthercontent= ObservableField<String>("")
    var projectScreenshots= ObservableArrayList<String>()
    var technology= ObservableArrayList<ProjectTechnology>()
    var framework = ObservableArrayList<TechnologyFramework>()
    var projectServertool= ObservableField<String>("")
    var projectTechnology= ObservableField<String>("")
    var projectUiux= ObservableField<String>("")
    var projectWebapp= ObservableField<String>("")
    var projectWebsite= ObservableField<String>("")
    var relatedKeyword= ObservableField<String>("")
    //var screenShotList= ObservableArrayList<ProjectScreenShotsModel>()

    fun onClickBack(){
        backNavigation.value = true
    }

    init {
        sharedPreferences = SharedPreferenceImp()
    }

    fun customDialog(){

    /*    val customDialog = CustomDialog(getApplication())
        customDialog.show()
        customDialog.setCanceledOnTouchOutside(true)*/

    }

    fun getProjectDetails(){
        mShowProgressBar.value = true
        LoginControllerRepository.getInstance().projectDetailsApi(projectDetails,id.get().toString())
    }


}