package com.example.colanportfolio.ui.testimonials

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.colanportfolio.BR
import com.example.colanportfolio.R
import com.example.colanportfolio.adapter.BaseRecyclerViewAdapter
import com.example.colanportfolio.adapter.OnDataBindCallback
import com.example.colanportfolio.base.BaseFragment
import com.example.colanportfolio.data.model.testimonial.TestimonialItem
import com.example.colanportfolio.databinding.FragmentTestimonialBinding
import com.example.colanportfolio.databinding.TestimonialItemBinding
import com.example.colanportfolio.ui.indicator.CirclePagerIndicatorDecoration
import kotlinx.android.synthetic.main.fragment_domain.*
import kotlinx.android.synthetic.main.fragment_testimonial.*

class TestimonialFragment :BaseFragment<FragmentTestimonialBinding,TestimonialViewModel>() {

    private var testimonialAdapter : BaseRecyclerViewAdapter<TestimonialItem, TestimonialItemBinding>? = null
    private val testimonialVM:TestimonialViewModel by viewModels()

    override val bindingVariable: Int
        get() =BR.TestimonialVM
    override val layoutId: Int
        get() =R.layout.fragment_testimonial
    override val viewModel: TestimonialViewModel
        get() {
            return testimonialVM
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        testimonialAdapter= BaseRecyclerViewAdapter(R.layout.testimonial_item,BR.testimonialAdapter,
        viewModel.testimonialList,null,object :OnDataBindCallback<TestimonialItemBinding>{
                override fun onItemClick(view: View, position: Int, v: TestimonialItemBinding) {
                    when(view.id){
                        R.id.technology_cv -> {
                        }
                    }
                }

                override fun onDataBind(
                    v: TestimonialItemBinding,
                    onClickListener: View.OnClickListener
                ) {
                }
            })
        viewDataBinding?.testimonialRv?.adapter=testimonialAdapter
        viewDataBinding?.testimonialRv?.addItemDecoration(CirclePagerIndicatorDecoration())

        viewModel.mShowProgressBar.observeEvent(viewLifecycleOwner, Observer {
            it.let {
                showProgressBar(testimonial_layout)
            }
        })


        viewModel.testimonialListData.observe(viewLifecycleOwner, Observer{
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){

                    true -> {
                        it.data.response.let {
                            testimonialAdapter?.clearDataSet()
                            viewModel.testimonialList.clear()
                            /*viewModel.testimonialList.addAll(it)
                            Log.d("techList",viewModel.testimonialList.let { it.toString() })*/
                            testimonialAdapter?.addDataSet(it)
                            testimonialAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
        })


        return viewDataBinding?.root
    }




}