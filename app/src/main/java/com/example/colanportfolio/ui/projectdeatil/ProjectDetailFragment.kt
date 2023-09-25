package com.example.colanportfolio.ui.projectdeatil

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.colanportfolio.BR
import com.example.colanportfolio.R
import com.example.colanportfolio.base.BaseFragment
import com.example.colanportfolio.databinding.FragmentProjectDetailBinding

class ProjectDetailFragment : BaseFragment<FragmentProjectDetailBinding,ProjectDetailViewModel>() {


    private val projectDetailVM: ProjectDetailViewModel by viewModels()
    override val bindingVariable: Int
        get() = BR.projectDeatilVM
    override val layoutId: Int
        get() = R.layout.fragment_project_detail
    override val viewModel: ProjectDetailViewModel
        get() {
            return projectDetailVM
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel.project_name.set(arguments?.getString("project_name"))
        viewModel.project_image.set(arguments?.getString("project_image"))
        viewModel.name.set(arguments?.getString("name"))
        viewModel.content.set(arguments?.getString("content"))
        viewModel.image.set(arguments?.getString("image"))
        show()
        if (viewModel.content.get() != ""){
            viewDataBinding?.detailsTv?.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(viewModel.content.get(), Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(viewModel.content.get())
            }
        }


        /*viewModel.backNavigation.observeEvent(viewLifecycleOwner, Observer {

            it.let {
                activity?.supportFragmentManager?.popBackStack()
            }
        })*/


        return viewDataBinding?.root
    }

    fun show(){
        if (viewModel.image.get().isNullOrEmpty()){
            viewDataBinding?.detailIv?.visibility = View.GONE
        }
    }



}