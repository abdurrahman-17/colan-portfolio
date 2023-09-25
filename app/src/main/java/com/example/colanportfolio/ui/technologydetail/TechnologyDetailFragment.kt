package com.example.colanportfolio.ui.technologydetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.colanportfolio.BR
import com.example.colanportfolio.R
import com.example.colanportfolio.base.BaseFragment
import com.example.colanportfolio.databinding.FragmentTechnologyDetailBinding

class TechnologyDetailFragment : BaseFragment<FragmentTechnologyDetailBinding,TechnologyDetailViewModel>() {

    private val technologyDetailVM:TechnologyDetailViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.TechnologyDetailVM
    override val layoutId: Int
        get() = R.layout.fragment_technology_detail
    override val viewModel: TechnologyDetailViewModel
        get() {

            return technologyDetailVM
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_technology_detail, container, false)
    }



}