package com.example.colanportfolio.base

import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.colanportfolio.ColanPortfolio
import com.example.colanportfolio.R
import com.example.colanportfolio.data.local.SharedPreferenceImp
import com.example.colanportfolio.utils.Alert
import com.example.colanportfolio.utils.Constants
import com.example.colanportfolio.utils.NetworkUtils

abstract class BaseActivity<T : ViewDataBinding?, V : ViewModel?> : AppCompatActivity() {

    private var mProgressDialog: ProgressDialog? = null
    var viewDataBinding: T? = null

    private var mViewModel: V? = null
    private var progressBar: ProgressBar? = null
    private var isNoNetAlertVisible = false
    var networkChangeReceiver: NetworkChangeReceiver? = null
    private var sharedPreference : SharedPreferenceImp? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V

    fun onFragmentAttached() {}
    fun onFragmentDetached(tag: String?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = SharedPreferenceImp()
        performDataBinding()

    }


    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String?): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }

    val isNetworkConnected: Boolean
        get() = NetworkUtils.isNetworkConnected(applicationContext)

    inner class NetworkChangeReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            showNetworkError()
        }
    }

    override fun onResume() {
        super.onResume()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(
            networkChangeReceiver,
            IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        )
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChangeReceiver)
    }

    fun openActivityOnTokenExpire() { //        startActivity(LoginActivity.newIntent(this));
        finish()
    }

//    fun performDependencyInjection() {
//        AndroidInjection.inject(this)
//    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String?>?, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions!!, requestCode)
        }
    }

    /*fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(this)
    }*/

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView<T>(this, layoutId)
        mViewModel = if (mViewModel == null) viewModel else mViewModel
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.bottom_container, fragment)
                .commit()
    }

    fun showBottomFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.bottom_container, fragment)
                .commit()
    }

    protected fun replaceFragment(
        @IdRes containerViewId: Int,
        @NonNull fragment: Fragment,
        @NonNull fragmentTag: String,
        @Nullable backStackStateName: String
    ) {
        this.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit,R.anim.anim_pop_enter,R.anim.anim_pop_exit)
            .replace(containerViewId, fragment, fragmentTag)
            .addToBackStack(backStackStateName)
            .commit()
    }

    /**
     * common show progress bar for all screens
     *
     * @param parent layout of screen
     */
    fun showProgressBar(parent: ViewGroup) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleLarge)
        progressBar?.indeterminateDrawable?.setColorFilter(
            ContextCompat.getColor(this, R.color.color_yellow),
            android.graphics.PorterDuff.Mode.SRC_ATOP
        )
        when (parent) {
            is RelativeLayout -> {
                val params = RelativeLayout.LayoutParams(130, 130)
                params.addRule(RelativeLayout.CENTER_IN_PARENT)
                parent.addView(progressBar, params)
            }
            is FrameLayout -> {
                val params = FrameLayout.LayoutParams(130, 130)
                params.gravity = Gravity.CENTER
                parent.addView(progressBar, params)
            }
            is CoordinatorLayout -> {
                val params = CoordinatorLayout.LayoutParams(130, 130)
                params.gravity = Gravity.CENTER
                parent.addView(progressBar, params)
            }
            is ConstraintLayout -> {
                val params = ConstraintLayout.LayoutParams(80, 80)
                params.topToTop = ConstraintSet.PARENT_ID
                params.startToStart = ConstraintSet.PARENT_ID
                params.endToEnd = ConstraintSet.PARENT_ID
                params.bottomToBottom = ConstraintSet.PARENT_ID
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    parent.elevation = 6f
                }
                parent.addView(progressBar, params)
            }
        }
        progressBar?.visibility = View.VISIBLE  //To show ProgressBar
    }

    /**
     * common dismiss progress bar for all screens
     *
     */
    fun dismissProgressBar() {
        if (progressBar?.visibility == View.VISIBLE) {
            progressBar?.visibility = View.GONE     // To Hide ProgressBar
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

     fun hideKeyboard(v: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.applicationWindowToken, 0)
    }

    fun showNetworkError(): Boolean {
        // Show popup when network is no available
        val isNetwork = NetworkUtils.isNetworkConnected(this)
        Constants.isNetworkConnected = NetworkUtils.isNetworkConnected(this)
        if (!isNoNetAlertVisible && !NetworkUtils.isNetworkConnected(this)) {
            isNoNetAlertVisible = true
            Alert.createOkAlert(this@BaseActivity,
                "Alert",
                "No internet connection or network failure",
                object : Alert.OnAlertClickListener {
                    override fun onPositive(dialog: DialogInterface) {
                        dialog.dismiss()
                        isNoNetAlertVisible = false
                    }

                    override fun onNegative(dialog: DialogInterface) {
                        dialog.dismiss()
                    }

                }).show()
        }
        return isNetwork
    }

    fun putToast(message : String){
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }

    fun alertDialog(message : String) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        //alertDialog.setTitle("AlertDialog")
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(
            "Ok"
        ) { _, _ ->

        }

        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

}