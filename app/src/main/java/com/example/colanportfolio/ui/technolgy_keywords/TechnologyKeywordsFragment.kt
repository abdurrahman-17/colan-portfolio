package com.example.colanportfolio.ui.technolgy_keywords

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.colanportfolio.BR
import com.example.colanportfolio.R
import com.example.colanportfolio.adapter.BaseRecyclerViewAdapter
import com.example.colanportfolio.adapter.OnDataBindCallback
import com.example.colanportfolio.base.BaseFragment
import com.example.colanportfolio.data.model.domaindetailitem.TechnologyListResponseItem
import com.example.colanportfolio.data.model.projectdetails.ProjectTechnology
import com.example.colanportfolio.data.model.projectdetails.TechnologyFramework
import com.example.colanportfolio.data.model.technology.TechnologyItem
import com.example.colanportfolio.databinding.FragmentTechnologyKeywordsBinding
import com.example.colanportfolio.databinding.FrameworkItemBinding
import com.example.colanportfolio.databinding.TechKeywordsItemBinding
import com.example.colanportfolio.ui.screenshotimageoverview.ScreenShotImageOverviewViewModel
import com.example.colanportfolio.utils.Constants

class TechnologyKeywordsFragment : BaseFragment<FragmentTechnologyKeywordsBinding, TechnologyKeywordViewModel>() {

    private val techKeyword: TechnologyKeywordViewModel by viewModels()

    private var technologyAdapter : BaseRecyclerViewAdapter<ProjectTechnology,TechKeywordsItemBinding>? = null
    private var frameworkAdapter : BaseRecyclerViewAdapter<TechnologyFramework, FrameworkItemBinding>? = null

    override val bindingVariable: Int
        get() = BR.technologyKeywordVM
    override val layoutId: Int
        get() = R.layout.fragment_technology_keywords
    override val viewModel: TechnologyKeywordViewModel
        get(){
            return techKeyword
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel.name.set(arguments?.getString("name"))
        viewModel.projectName.set(arguments?.getString("project_name"))
        viewModel.projectImage.set(arguments?.getString("project_image"))
        viewModel.keywords.set(arguments?.getString("content"))
        viewModel.sub_title.set(arguments?.getString("sub"))
        viewModel.framework.set(arguments?.getString("framework"))
        if(Constants.framework.isEmpty()){
            viewDataBinding?.frameworkRv?.visibility = View.GONE

        }
        if (viewModel.keywords.get().isNullOrEmpty()){
            viewDataBinding?.projectCv?.visibility = View.GONE
        }
        //viewModel.technologyListData.addAll(arguments.getParcelableArrayList<ProjectTechnology>("technology"))

        if (viewModel.keywords.get() != ""){
            viewDataBinding?.keywordTv?.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(viewModel.keywords.get(), Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(viewModel.keywords.get())
            }
        }
        technologyAdapter = BaseRecyclerViewAdapter(R.layout.tech_keywords_item,BR.technologyListAdapter,
        viewModel.technologyListData, null, object : OnDataBindCallback<TechKeywordsItemBinding>{
                override fun onItemClick(view: View, position: Int, v: TechKeywordsItemBinding) {
                }

                override fun onDataBind(
                    v: TechKeywordsItemBinding,
                    onClickListener: View.OnClickListener
                ) {
                }

            })
        viewDataBinding?.technologyKeywordRv?.adapter = technologyAdapter
        technologyAdapter?.addDataSet(Constants.technology)
        frameworkAdapter = BaseRecyclerViewAdapter(R.layout.framework_item,BR.frameworkAdapter,
        viewModel.frameworkList, null, object : OnDataBindCallback<FrameworkItemBinding>{
                override fun onItemClick(view: View, position: Int, v: FrameworkItemBinding) {}

                override fun onDataBind(
                    v: FrameworkItemBinding,
                    onClickListener: View.OnClickListener
                ) {}

            })
        viewDataBinding?.frameworkRv?.adapter = frameworkAdapter
        frameworkAdapter?.addDataSet(Constants.framework)

        return viewDataBinding?.root
    }

}