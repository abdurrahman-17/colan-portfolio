package com.example.colanportfolio.base

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import com.example.colanportfolio.utils.snackBar

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val mApplication : Application = application
    var isLoading = ObservableBoolean()
        private set
   // val compositeDisposable: CompositeDisposable


    override fun onCleared() {
       // compositeDisposable.dispose()
        super.onCleared()
    }

    fun setIsLoading(isLoadingNew: ObservableBoolean) {
        isLoading = isLoadingNew
    }

    fun showSnackBar(message : String,view : View){
        mApplication.snackBar(message,view)
    }

    fun putToast(message : String){
        Toast.makeText(mApplication,message, Toast.LENGTH_SHORT).show()
    }


    init {
        //compositeDisposable = CompositeDisposable()
    }
}