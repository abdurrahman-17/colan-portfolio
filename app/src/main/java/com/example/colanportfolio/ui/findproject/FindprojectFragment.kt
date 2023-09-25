package com.example.colanportfolio.ui.findproject

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
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
import com.example.colanportfolio.data.model.domaindetailitem.ProjectListResponseItem
import com.example.colanportfolio.data.model.search.FindProjectItem
import com.example.colanportfolio.databinding.FindProjectItemBinding
import com.example.colanportfolio.databinding.FragmentFindprojectBinding
import com.example.colanportfolio.ui.domaindetailroot.DomainDetailRootFragment
import kotlinx.android.synthetic.main.fragment_findproject.*
import android.view.inputmethod.EditorInfo

import android.widget.TextView
import android.widget.TextView.OnEditorActionListener


class FindprojectFragment : BaseFragment<FragmentFindprojectBinding,FindProjectViewModel>(){

    private var findProjectAdapter : BaseRecyclerViewAdapter<ProjectListResponseItem, FindProjectItemBinding>? = null

    private val findVM: FindProjectViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.FindProjectVM
    override val layoutId: Int
        get() = R.layout.fragment_findproject
    override val viewModel: FindProjectViewModel
        get() {

            return findVM
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)


        findProjectAdapter = BaseRecyclerViewAdapter(R.layout.find_project_item,BR.FindProjectAdapter,
        viewModel.findProjectList,null,object :OnDataBindCallback<FindProjectItemBinding>{
                override fun onItemClick(view: View, position: Int, v: FindProjectItemBinding) {
                    when(view.id){
                        R.id.findProject_cv -> {
                            val bundle = Bundle()
                            bundle.putString("id",viewModel.findProjectList[position].id)
                            replaceFragmentBundle(R.id.bottom_container, DomainDetailRootFragment(),"domain","domain",bundle)
                        }
                    }
                }

                override fun onDataBind(
                    v: FindProjectItemBinding,
                    onClickListener: View.OnClickListener
                ) {
                    v.findProjectCv.setOnClickListener(onClickListener)
                }
            })

        viewDataBinding?.findProjectRv?.adapter=findProjectAdapter

        viewDataBinding?.button?.setOnClickListener(View.OnClickListener {
            viewModel.findProjectApi()
        })


        viewDataBinding?.searchEditText?.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.findProjectApi()
                return@OnEditorActionListener true
            }
            false
        })

        viewModel.findProjectListData.observe(viewLifecycleOwner, Observer {
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){
                    true -> {
                        it.data.response.let {
                            findProjectAdapter?.clearDataSet()
                           /* viewModel.findProjectList.clear()
                            viewModel.findProjectList.addAll(it)*/
                            findProjectAdapter?.addDataSet(it)
                            findProjectAdapter?.notifyDataSetChanged()
                        }
                    }
                    false ->{
                        when {
                            it.data.message.equals("Data Not Found") -> {
                                findProjectAdapter?.clearDataSet()
                                putToast(it.data.message)
                            }
                            it.data.message.equals("Search Project Name") -> {
                                findProjectAdapter?.clearDataSet()
                            }
                            it.data.message.equals("No Project Data Found") -> {
                                findProjectAdapter?.clearDataSet()
                            }
                            else -> {
                                putToast(it.data.message)
                            }
                        }
                    }
                    else ->{
                        putToast(resources.getString(R.string.internet_connection))
                    }
                }

            }
        })

        viewModel.mShowProgressBar.observeEvent(viewLifecycleOwner, Observer {
            it.let {
                showProgressBar(find_project_layout)
            }
        })


        return viewDataBinding?.root
    }


}