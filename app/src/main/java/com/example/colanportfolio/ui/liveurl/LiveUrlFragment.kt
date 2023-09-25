package com.example.colanportfolio.ui.liveurl

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.colanportfolio.BR
import com.example.colanportfolio.R
import com.example.colanportfolio.base.BaseFragment
import com.example.colanportfolio.databinding.FragmentLiveUrlBinding
import com.example.colanportfolio.ui.projectdeatil.ProjectDetailViewModel


class LiveUrlFragment : BaseFragment<FragmentLiveUrlBinding,LiveUrlViewmodel>() {

    private val liveUrlVM: LiveUrlViewmodel by viewModels()

    override val bindingVariable: Int
        get() = BR.liveUrlVM
    override val layoutId: Int
        get() = R.layout.fragment_live_url
    override val viewModel: LiveUrlViewmodel
        get(){

            return liveUrlVM
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel.project_name.set(arguments?.getString("project_name"))
        viewModel.image.set(arguments?.getString("project_image"))
        viewModel.name.set(arguments?.getString("name"))
        viewModel.webite_link.set(arguments?.getString("website"))
        viewModel.webApp_link.set(arguments?.getString("webapp"))
        viewModel.android.set(arguments?.getString("android"))
        viewModel.ios.set(arguments?.getString("ios"))
        show()


        viewDataBinding?.androidIb?.setOnClickListener {
            if (URLUtil.isValidUrl(viewModel.android.get().toString())){
                val url =viewModel.android.get().toString()
                val uri = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context?.startActivity(intent)
            }

        }

        viewDataBinding?.iosIb?.setOnClickListener {
            if (URLUtil.isValidUrl(viewModel.ios.get().toString())){
                val url2 =viewModel.ios.get().toString()
                val uri = Uri.parse(url2)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context?.startActivity(intent)
            }else{
                putToast("This is not valid url")
            }

        }

        viewDataBinding?.webAppLinkTv?.setOnClickListener {
            if (URLUtil.isValidUrl(viewModel.webApp_link.get().toString())){
                val url3 =viewModel.webApp_link.get().toString()
                val uri = Uri.parse(url3)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context?.startActivity(intent)
            }else{
                putToast("This is not valid url")
            }

        }


        viewDataBinding?.websiteLinkTv?.setOnClickListener {
            if (URLUtil.isValidUrl(viewModel.webite_link.get().toString())){
                val url3 =viewModel.webite_link.get().toString()
                val uri = Uri.parse(url3)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context?.startActivity(intent)
            }else{
                putToast("This is not valid url")
            }

        }

        return viewDataBinding?.root

    }

    private fun show() {

        if (viewModel.webite_link.get()==""){
            viewDataBinding?.websiteLinkTv?.visibility= View.GONE
            viewDataBinding?.websiteTv?.visibility= View.GONE
        }
        if (viewModel.webApp_link.get()==""){
            viewDataBinding?.webappTv?.visibility= View.GONE
            viewDataBinding?.webAppLinkTv?.visibility= View.GONE
        }

        if (viewModel.android.get()==""){
            viewDataBinding?.androidIb?.visibility= View.GONE
        }

        if (viewModel.ios.get()==""){
            viewDataBinding?.iosIb?.visibility= View.GONE
        }

        if (viewModel.ios.get()=="" || viewModel.android.get()==""){
            viewDataBinding?.mobileAppTv?.visibility= View.GONE

        }
    }

}