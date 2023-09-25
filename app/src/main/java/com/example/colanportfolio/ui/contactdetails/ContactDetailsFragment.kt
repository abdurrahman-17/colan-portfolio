package com.example.colanportfolio.ui.contactdetails

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.ObservableField
import androidx.fragment.app.viewModels
import com.example.colanportfolio.BR
import com.example.colanportfolio.R
import com.example.colanportfolio.base.BaseFragment
import com.example.colanportfolio.databinding.FragmentContactDetailsBinding
import dmax.dialog.SpotsDialog

class ContactDetailsFragment : BaseFragment<FragmentContactDetailsBinding,ContactDetailsViewModel>() {

    private val contactDetailsVM:ContactDetailsViewModel by viewModels()
    private var webView: WebView? = null
    var url : String? = null
    var urlTwo = ObservableField<String>("")
    private var dialog: SpotsDialog? = null

    override val bindingVariable: Int
        get() = BR.contactDetailsVM
    override val layoutId: Int
        get() = R.layout.fragment_contact_details
    override val viewModel: ContactDetailsViewModel
        get() {
            return contactDetailsVM
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        url = arguments?.getString("url")
        webView = viewDataBinding?.contactWv
        webView!!.setWebViewClient(MyBrowser())
        webView!!.getSettings().setLoadsImagesAutomatically(true)
        webView!!.getSettings().setJavaScriptEnabled(true)
        webView!!.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
        webView!!.loadUrl(url!!)
        urlTwo.set(url)

        return viewDataBinding?.root
    }

    private inner class MyBrowser : WebViewClient(){
        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean{
            if (url != null){
                if (url == urlTwo.get()){
                    activity?.supportFragmentManager?.popBackStack()
                }else{
                    urlTwo.set(url)
                    view.loadUrl(url)
                }
            }
            return true
        }
        /*override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            dialog?.show()
            super.onPageStarted(view, url, favicon)
        }
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            dialog?.show()
            super.onPageStarted(view, url, favicon)
        }*/

    }

}