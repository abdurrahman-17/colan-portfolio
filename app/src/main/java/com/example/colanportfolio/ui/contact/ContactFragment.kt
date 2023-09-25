package com.example.colanportfolio.ui.contact

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.colanportfolio.BR
import com.example.colanportfolio.R
import com.example.colanportfolio.base.BaseFragment
import com.example.colanportfolio.databinding.FragmentContactBinding
import com.example.colanportfolio.ui.contactdetails.ContactDetailsFragment
import com.hbb20.CountryCodePicker
import kotlinx.android.synthetic.main.fragment_contact.*

class ContactFragment : BaseFragment<FragmentContactBinding, ContactViewModel>(), CountryCodePicker.OnCountryChangeListener {

    private val contactVM: ContactViewModel by viewModels()
    private var ccp: CountryCodePicker?=null

    override val bindingVariable: Int
        get() = BR.contactVM
    override val layoutId: Int
        get() = R.layout.fragment_contact
    override val viewModel: ContactViewModel
        get() {
            return contactVM
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        ccp = viewDataBinding?.countryCodePicker
        ccp?.setOnCountryChangeListener(this)
        viewDataBinding?.emailET?.clearFocus()
        viewDataBinding?.emailET?.error = null
        clearDetail()

        contactVM.mShowProgressBar.observeEvent(viewLifecycleOwner, Observer {
            it.let {
                showProgressBar(contact_layout)
            }
        })

        viewDataBinding?.SubmitButton?.setOnClickListener {
            registerValidation()
        }
        contactVM.registerDataResponse.observe(viewLifecycleOwner, Observer {
            it.let {
                dismissProgressBar()
                if (viewModel.apiClear.get() == true){
                    when (it.data?.isSuccess) {
                        true -> {
                            if (it.data.response.url.isNotEmpty()){
                                val bundle = Bundle()
                                bundle.putString("url",it.data.response.url)
                                replaceFragmentBundle(R.id.bottom_container,ContactDetailsFragment(),"Contact Details","Contact Details",bundle)
                                clearDetail()
                            }
                        }
                        false -> {
                            putToast(it.data.message)
                        }
                        else -> {
                            putToast("Internet Issue")
                        }
                    }
                }
            }

        })

        return viewDataBinding?.root
    }

    fun dialogExit(message: String?) {
        val alertDialog = AlertDialog.Builder(this.requireContext()).create()
        alertDialog.setCancelable(false)
        alertDialog.setTitle("Colan Portfolio")
        alertDialog.setIcon(resources.getDrawable(R.drawable.colan))
        alertDialog.setMessage(message)
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, which ->
            clearDetail()
         }
        alertDialog.show()
    }

    fun clearDetail(){
        viewModel.apiClear.set(false)
        viewDataBinding?.firstNameET?.setText("")
        viewDataBinding?.firstNameET?.isSelected = false
        viewDataBinding?.lastNameET?.setText("")
        viewDataBinding?.lastNameET?.isSelected = false
        viewDataBinding?.emailET?.setText("")
        viewDataBinding?.emailET?.isSelected = false
        viewDataBinding?.phoneET?.setText("")
        viewDataBinding?.phoneET?.isSelected = false
        viewDataBinding?.locationET?.setText("")
        viewDataBinding?.locationET?.isSelected = false
        viewDataBinding?.companyET?.setText("")
        viewDataBinding?.companyET?.isSelected = false
        viewDataBinding?.expectationET?.setText("")
        viewDataBinding?.expectationET?.isSelected = false
    }
    fun registerValidation() {

        if (TextUtils.isEmpty(viewDataBinding?.emailET?.text.toString().trim() { it <= ' ' })) {
            viewDataBinding?.emailET?.error = "please enter  email"
            viewDataBinding?.emailET?.requestFocus()
        } else if (!TextUtils.isEmpty(
                viewDataBinding?.emailET?.text.toString().trim() { it <= ' ' }) &&
            !Patterns.EMAIL_ADDRESS.matcher(viewDataBinding?.emailET?.text.toString()).matches()
        ) {
            viewDataBinding?.emailET?.error = "please enter valid email"
            viewDataBinding?.emailET?.requestFocus()
        } else {
            contactVM.registerApi()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCountrySelected() {
        viewModel.phoneCode.set(ccp?.selectedCountryCode)
    }

}