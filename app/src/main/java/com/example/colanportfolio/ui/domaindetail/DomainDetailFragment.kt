package com.example.colanportfolio.ui.domaindetail

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
import com.example.colanportfolio.data.model.domaindetailitem.DomainDetailItem
import com.example.colanportfolio.data.model.domaindetailitem.ProjectListResponseItem
import com.example.colanportfolio.data.model.domaindetailitem.TechnologyListResponseItem
import com.example.colanportfolio.databinding.DomainDetailItemBinding
import com.example.colanportfolio.databinding.FragmentDomainDetailBinding
import com.example.colanportfolio.ui.dialogbox.CustomDialog
import com.example.colanportfolio.ui.domaindetailroot.DomainDetailRootFragment
import com.example.colanportfolio.utils.Constants
import kotlinx.android.synthetic.main.fragment_domain_detail.*

class DomainDetailFragment : BaseFragment<FragmentDomainDetailBinding,DomainDetailViewModel>() {

    private var domainDetailAdapter : BaseRecyclerViewAdapter<ProjectListResponseItem, DomainDetailItemBinding>? = null

    private val DomainDetailVM:DomainDetailViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.DomainDetailVM
    override val layoutId: Int
        get() = R.layout.fragment_domain_detail
    override val viewModel: DomainDetailViewModel
        get() {

            return DomainDetailVM
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel.type.set(arguments?.getString("type"))
        if (viewModel.type.get() == "domain") {
            viewModel.domainId.set(arguments?.getString("domain_id"))
            viewModel.domainName.set(arguments?.getString("title"))
            viewModel.domainImage.set(arguments?.getString("image"))
            Log.d("domain_id",viewModel.domainId.get().toString())
            viewModel.domainDetailListApi()
        } else if (viewModel.type.get() == "technology") {
            viewModel.technologyId.set(arguments?.getString("technology_id"))
            viewModel.domainName.set(arguments?.getString("title"))
            viewModel.domainImage.set(arguments?.getString("image"))
            viewModel.technologyDetailListApi()
        }


        viewModel.backNavigation.observeEvent(viewLifecycleOwner, Observer {

            it.let {
                activity?.supportFragmentManager?.popBackStack()
            }
        })



        domainDetailAdapter = BaseRecyclerViewAdapter(R.layout.domain_detail_item,BR.DomainDetailAdapter,
        viewModel.domainDetailList,null,object :OnDataBindCallback<DomainDetailItemBinding>{
                override fun onItemClick(view: View, position: Int, v: DomainDetailItemBinding) {
                    when(view.id){
                        R.id.domain_detail_cv -> {
                            /*if (viewModel.type.get() == "technology"){
                                val technology = ArrayList<TechnologyListResponseItem>()
                                if (viewModel.domainDetailList[position].technology.isNotEmpty()){
                                    Constants.technology.clear()
                                    viewModel.domainDetailList[position].technology.forEach {
                                        technology.addAll(listOf(TechnologyListResponseItem(technology_image = it.technology_image,project_technology = it.project_technology,project_technology_id = it.project_technology_id)))
                                        Constants.technology.addAll(technology)
                                    }
                                }
                            }*/


                            val bundle = Bundle()
                            bundle.putString("id",viewModel.domainDetailList[position].id)
                            bundle.putString("name",viewModel.domainDetailList[position].project_name)
                            bundle.putString("image",viewModel.domainDetailList[position].project_image_logo)
                            replaceFragmentBundle(R.id.bottom_container, DomainDetailRootFragment(),"domain","domain",bundle)
                        }
                    }
                }

                override fun onDataBind(
                    v: DomainDetailItemBinding,
                    onClickListener: View.OnClickListener
                ) {
                    v.domainDetailCv
                        .setOnClickListener(onClickListener)
                }
            })

        viewModel.mShowProgressBar.observeEvent(viewLifecycleOwner, Observer {
            it.let {
                showProgressBar(domain_detail_layout)
            }
        })

        viewDataBinding?.domainProjectRv?.adapter= domainDetailAdapter

        viewModel.domainDetailListData.observe(viewLifecycleOwner, Observer {
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){
                    true -> {
                        it.data.response.let {
                            domainDetailAdapter?.clearDataSet()
                            domainDetailAdapter?.addDataSet(it)
                            domainDetailAdapter?.notifyDataSetChanged()
                        }
                    }
                    false -> {
                        putToast(it.data.message)
                    }
                    else -> putToast(resources.getString(R.string.internet_connection))
                }
            }
        })
        return viewDataBinding?.root
    }
}