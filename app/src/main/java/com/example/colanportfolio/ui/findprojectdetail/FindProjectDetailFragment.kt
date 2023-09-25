package com.example.colanportfolio.ui.findprojectdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.colanportfolio.BR
import com.example.colanportfolio.R
import com.example.colanportfolio.base.BaseFragment
import com.example.colanportfolio.databinding.FragmentFindProjectDetailBinding
import com.example.colanportfolio.ui.findproject.FindProjectViewModel


class FindProjectDetailFragment : BaseFragment<FragmentFindProjectDetailBinding,FindProjectDetailViewModel>() {

    private val findProjectDetailVM: FindProjectDetailViewModel by viewModels()


    override val bindingVariable: Int
        get() = BR.FindProjectDetailVM
    override val layoutId: Int
        get() = R.layout.fragment_find_project_detail
    override val viewModel: FindProjectDetailViewModel
        get() {

            return findProjectDetailVM
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)

        return viewDataBinding?.root
    }




}