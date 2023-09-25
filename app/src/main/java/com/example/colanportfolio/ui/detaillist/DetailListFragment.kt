package com.example.colanportfolio.ui.detaillist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.colanportfolio.BR
import com.example.colanportfolio.R
import com.example.colanportfolio.base.BaseFragment
import com.example.colanportfolio.databinding.FragmentDetailListBinding

class DetailListFragment : BaseFragment<FragmentDetailListBinding,DetailListViewModel>() {

    private val detailListVM:DetailListViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.DetailListVM
    override val layoutId: Int
        get() = R.layout.fragment_detail_list
    override val viewModel: DetailListViewModel
        get() {

            return detailListVM
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_list, container, false)
    }



}