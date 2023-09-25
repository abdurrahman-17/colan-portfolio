package com.example.colanportfolio.base

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.colanportfolio.R
import java.text.NumberFormat
import java.util.*

abstract class BaseFragment<T : ViewDataBinding?, V : BaseViewModel?> : Fragment(){
    var baseActivity: BaseActivity<*, *>? = null
        private set
    private var mRootView: View? = null

    var viewDataBinding: T? = null
        private set

    private var mViewModel: V? = null
    private var progressBar: ProgressBar? = null

    abstract val bindingVariable: Int

    @get:LayoutRes
    abstract val layoutId: Int

    abstract val viewModel: V

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context
            baseActivity = activity
            activity.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate<T>(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.root
        return mRootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.setVariable(bindingVariable, mViewModel)
        viewDataBinding?.lifecycleOwner = this
        viewDataBinding?.executePendingBindings()
    }

    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

   /* val isNetworkConnected: Boolean
        get() = baseActivity != null && baseActivity!!.isNetworkConnected*/

    fun openActivityOnTokenExpire() {
        if (baseActivity != null) {
            baseActivity!!.openActivityOnTokenExpire()
        }
    }

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String?)
    }

    protected fun replaceFragment(
        @IdRes containerViewId: Int,
        @NonNull fragment: Fragment,
        @NonNull fragmentTag: String,
        @Nullable backStackStateName: String,
    ) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit,R.anim.anim_pop_enter,R.anim.anim_pop_exit)
            ?.replace(containerViewId, fragment, fragmentTag)
            ?.addToBackStack(backStackStateName)
            ?.commit()
    }

    protected fun replaceFragmentBundle(
            @IdRes containerViewId: Int,
            @NonNull fragment: Fragment,
            @NonNull fragmentTag: String,
            @Nullable backStackStateName: String,
            @Nullable bundle: Bundle?
    ) {
        fragment.arguments = bundle
        activity?.supportFragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit,R.anim.anim_pop_enter,R.anim.anim_pop_exit)
                ?.replace(containerViewId, fragment, fragmentTag)
                ?.addToBackStack(backStackStateName)
                ?.commit()
    }

    protected fun replaceFragmentBack(
            @IdRes containerViewId: Int,
            @NonNull fragment: Fragment,
            @NonNull fragmentTag: String,
            @Nullable backStackStateName: String
    ) {
        activity?.supportFragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(R.anim.anim_exit, R.anim.anim_exit)
                ?.replace(containerViewId, fragment, fragmentTag)
                ?.addToBackStack(backStackStateName)
                ?.commit()
    }

    /**
     * common show progress bar for all screens
     *
     * @param parent layout of screen
     */
    fun showProgressBar(parent: ViewGroup) {
        baseActivity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        progressBar = ProgressBar(baseActivity, null, android.R.attr.progressBarStyleLarge)
        progressBar?.indeterminateDrawable?.setColorFilter(
                ContextCompat.getColor(baseActivity!!, R.color.blue),
                android.graphics.PorterDuff.Mode.SRC_ATOP
        )
        when (parent) {
            is RelativeLayout -> {
                val params = RelativeLayout.LayoutParams(130, 130)
                params.addRule(RelativeLayout.CENTER_IN_PARENT)
                parent.addView(progressBar, params)
            }
            is FrameLayout -> {
                val params = FrameLayout.LayoutParams(80, 80)
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
            baseActivity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    fun putToast(message : String){
        Toast.makeText(baseActivity,message, Toast.LENGTH_SHORT).show()
    }

    fun showAlertDialog(message : String) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(baseActivity)
        //alertDialog.setTitle("AlertDialog")
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(
            "Ok"
        ) { _, _ ->
            activity?.supportFragmentManager?.popBackStack()
        }

        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    fun alertDialog(message : String) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(baseActivity)
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

    /*fun myQuoteDialog(message : String) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(baseActivity)
        //alertDialog.setTitle("AlertDialog")
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(
                "yes"
        ) { _, _ ->
            activity?.supportFragmentManager?.popBackStack()
        }

        alertDialog.setNegativeButton(
                "No"
        ){ _,_->

        }

        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }*/

    fun rupeesComma(amount : Double) : String{
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
        val moneyString: String = formatter.format(amount)
        return moneyString
    }

}