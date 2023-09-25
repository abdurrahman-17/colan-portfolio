package com.example.colanportfolio.ui.domaindetailroot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.colanportfolio.BR
import com.example.colanportfolio.R
import com.example.colanportfolio.adapter.BaseRecyclerViewAdapter
import com.example.colanportfolio.base.BaseFragment
import com.example.colanportfolio.data.model.domaindetailitem.ProjectScreenShotsModel
import com.example.colanportfolio.databinding.FragmentDomainDetailRootBinding
import com.example.colanportfolio.databinding.ScreenshotImageBinding
import com.example.colanportfolio.ui.liveurl.LiveUrlFragment
import com.example.colanportfolio.ui.projectdeatil.ProjectDetailFragment
import com.example.colanportfolio.ui.projectscreenshot.ProjectScreenShotFragment
import com.example.colanportfolio.ui.technolgy_keywords.TechnologyKeywordsFragment
import com.example.colanportfolio.utils.Constants
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_domain_detail_root.*


class DomainDetailRootFragment : BaseFragment<FragmentDomainDetailRootBinding,DomainDetailRootViewModel>() {

    private val domainDetailRootVM:DomainDetailRootViewModel by viewModels()

    private var domainDetailAdapter : BaseRecyclerViewAdapter<ProjectScreenShotsModel, ScreenshotImageBinding>? = null




    override val bindingVariable: Int
        get() = BR.DomainDetailRootVM
    override val layoutId: Int
        get() = R.layout.fragment_domain_detail_root
    override val viewModel: DomainDetailRootViewModel
        get() {
            return domainDetailRootVM
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        //viewModel.id.set(arguments?.getString("id"))
        //viewModel.getProjectDetails()

        viewModel.mShowProgressBar.observeEvent(viewLifecycleOwner, Observer {
            it.let {
                showProgressBar(domain_details_fl)
            }
        })

        viewModel.projectDetails.observe(viewLifecycleOwner, Observer {
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){
                    true -> {
                        viewModel.projectScreenshots.clear()
                        viewModel.technology.clear()
                        viewModel.framework.clear()
                        viewModel.highlightedContent.set(it.data.response.highlighted_content)
                        viewModel.highlightedImage.set(it.data.response.highlighted_image)
                        viewModel.overviewContent.set(it.data.response.overview_content)
                        viewModel.overviewImage.set(it.data.response.overview_image)
                        viewModel.projectName.set(it.data.response.project_name)
                        viewModel.projectImageLogo.set(it.data.response.project_image_logo)
                        viewModel.relatedKeyword.set(it.data.response.related_keyword)
                        viewModel.technology.addAll(it.data.response.project_technology )
                        viewModel.framework.addAll(it.data.response.technology_frameworks)
                        viewModel.projectScreenshots.addAll(it.data.response.screen)
                        viewModel.projectWebsite.set(it.data.response.project_Website)
                        viewModel.projectWebapp.set(it.data.response.project_Webapp)
                        viewModel.projectAndroidapp.set(it.data.response.project_androidapp)
                        viewModel.projectIosapp.set(it.data.response.project_iosapp)
                        if (viewModel.technology.isNotEmpty()){
                            Constants.technology.clear()
                            Constants.technology.addAll(viewModel.technology)
                        }
                        if (viewModel.framework.isNotEmpty()){
                            Constants.framework.clear()
                            Constants.framework.addAll(viewModel.framework)
                        }
                        viewDataBinding?.titleTl?.removeAllTabs()
                        viewDataBinding?.titleTl?.addTab(viewDataBinding?.titleTl?.newTab()!!.setText(resources.getString(R.string.overview)))
                        viewDataBinding?.titleTl?.addTab(viewDataBinding?.titleTl?.newTab()!!.setText(resources.getString(R.string.highlighted_features)))
                        viewDataBinding?.titleTl?.addTab(viewDataBinding?.titleTl?.newTab()!!.setText(resources.getString(R.string.technology)))
                        viewDataBinding?.titleTl?.addTab(viewDataBinding?.titleTl?.newTab()!!.setText(resources.getString(R.string.live_url)))
                        viewDataBinding?.titleTl?.addTab(viewDataBinding?.titleTl?.newTab()!!.setText(resources.getString(R.string.screenshot)))
                        viewDataBinding?.titleTl?.tabGravity =TabLayout.GRAVITY_FILL
                        viewDataBinding?.titleTl?.tabMode = TabLayout.MODE_SCROLLABLE
                        viewDataBinding?.titleTl?.selectedTabPosition
                        val adapter =ProjectDetailAdapter(activity?.supportFragmentManager,viewDataBinding?.titleTl?.tabCount!!)
                        viewDataBinding?.detailFragmentVp?.adapter = adapter

                        viewDataBinding?.detailFragmentVp?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(viewDataBinding?.titleTl))
                        viewDataBinding?.titleTl?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                            override fun onTabSelected(tab: TabLayout.Tab?) {
                                viewDataBinding?.detailFragmentVp?.currentItem = tab!!.position
                            }

                            override fun onTabUnselected(tab: TabLayout.Tab?) {}

                            override fun onTabReselected(tab: TabLayout.Tab?) {}

                        })
                    }
                    false -> {
                        putToast(it.data.message)
                    }
                    else ->{
                        putToast(resources.getString(R.string.internet_connection))
                    }

                }
            }
        })


/*
        viewDataBinding?.overviewTV?.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(viewModel.overviewContent.get(), Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(viewModel.overviewContent.get())
        }

        viewDataBinding?.highlightTV?.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(viewModel.highlightedContent.get(), Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(viewModel.highlightedContent.get())
        }


        viewModel.backNavigation.observeEvent(viewLifecycleOwner, Observer {

            it.let {
                activity?.supportFragmentManager?.popBackStack()
            }
        })

        viewDataBinding?.button?.setOnClickListener {

        //    putToast(viewModel.projectAndroidapp.get().toString())
            val url =viewModel.projectAndroidapp.get().toString()
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context?.startActivity(intent)

        }

        viewDataBinding?.button2?.setOnClickListener {

            val url2 =viewModel.projectIosapp.get().toString()
            val uri = Uri.parse(url2)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context?.startActivity(intent)

        }

        viewDataBinding?.textView20?.setOnClickListener {
            val url3 =viewModel.projectWebsite.get().toString()
            val uri = Uri.parse(url3)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context?.startActivity(intent)
        }

        viewDataBinding?.btnScreenshot?.setOnClickListener {


            arguments?.getStringArrayList("projectScreenshots")?.let {dialogFragment?.screenShotList?.addAll(viewModel.screenShotList)}
            dialogFragment = CustomDialog()
            dialogFragment?.show(requireFragmentManager(),"show")

        }

*/

        viewModel.backNavigation.observeEvent(viewLifecycleOwner, Observer {

            it.let {
                activity?.supportFragmentManager?.popBackStack()
            }
        })

        /*viewDataBinding?.overviewCv?.setOnClickListener{
            val bundle = Bundle()
            bundle.clear()
            bundle.putString("project_name",viewModel.name.get())
            bundle.putString("project_image",viewModel.projectImageLogo.get())
            bundle.putString("name","OVERVIEW")
            bundle.putString("content",viewModel.overviewContent.get())
            bundle.putString("image",viewModel.overviewImage.get())
            replaceFragmentBundle(R.id.bottom_container,ProjectDetailFragment(),"project","project",bundle)
        }

        viewDataBinding?.highlightedCv?.setOnClickListener {
            val bundle = Bundle()
            bundle.clear()
            bundle.putString("project_name",viewModel.name.get())
            bundle.putString("project_image",viewModel.projectImageLogo.get())
            bundle.putString("name","HIGHLIGHTED FEATURES")
            bundle.putString("content",viewModel.highlightedContent.get())
            bundle.putString("image",viewModel.highlightedImage.get())
            replaceFragmentBundle(R.id.bottom_container,ProjectDetailFragment(),"project","project",bundle)
        }

        viewDataBinding?.urlCv?.setOnClickListener {
            val bundle = Bundle()
            bundle.clear()
            bundle.putString("project_name",viewModel.name.get())
            bundle.putString("project_image",viewModel.projectImageLogo.get())
            bundle.putString("name","LIVE URLS")
            bundle.putString("website",viewModel.projectWebsite.get())
            bundle.putString("webapp",viewModel.projectWebapp.get())
            bundle.putString("android",viewModel.projectAndroidapp.get())
            bundle.putString("ios",viewModel.projectIosapp.get())
            replaceFragmentBundle(R.id.bottom_container,LiveUrlFragment(),"project","project",bundle)
        }

        viewDataBinding?.screenshotCv?.setOnClickListener {
            val bundle = Bundle()
            bundle.clear()
            bundle.putString("project_name",viewModel.name.get())
            bundle.putString("project_image",viewModel.projectImageLogo.get())
            bundle.putString("name","PROJECT SCREENSHOTS")
            bundle.putStringArrayList("screenshot",viewModel.projectScreenshots)
            replaceFragmentBundle(R.id.bottom_container,ProjectScreenShotFragment(),"project","project",bundle)
        }

        viewDataBinding?.technologyCv?.setOnClickListener {
           *//* val bundle = Bundle()
            bundle.clear()
            bundle.putString("project_name",viewModel.name.get())
            bundle.putString("project_image",viewModel.projectImageLogo.get())
            bundle.putString("name","TECHNOLOGY")
            bundle.putString("sub","RELATED KEYWORDS")
            bundle.putString("content",viewModel.relatedKeyword.get())
            replaceFragmentBundle(R.id.bottom_container,TechnologyKeywordsFragment(),"project","project",bundle)*//*
            putToast("In Development ")
        }*/


        return viewDataBinding?.root
    }

    inner class ProjectDetailAdapter(fragmentManager: FragmentManager?, position: Int) : FragmentStatePagerAdapter(fragmentManager!!,position) {

        var numOfTabs = 0
        init {
            this.numOfTabs = position
        }

        override fun getItem(position: Int): Fragment {
            when(position){
                0 -> {
                    val projectOverviewFirst = ProjectDetailFragment()
                    val bundle = Bundle()
                    bundle.putString("project_name",viewModel.projectName.get())
                    bundle.putString("project_image",viewModel.projectImageLogo.get())
                    bundle.putString("name","OVERVIEW")
                    bundle.putString("content",viewModel.overviewContent.get())
                    bundle.putString("image",viewModel.overviewImage.get())
                    projectOverviewFirst.arguments = bundle
                    return projectOverviewFirst
                }
                1 -> {
                    val projectOverviewFirst = ProjectDetailFragment()
                    val bundle = Bundle()
                    bundle.clear()
                    bundle.putString("project_name",viewModel.projectName.get())
                    bundle.putString("project_image",viewModel.projectImageLogo.get())
                    bundle.putString("name","HIGHLIGHTED FEATURES")
                    bundle.putString("content",viewModel.highlightedContent.get())
                    bundle.putString("image",viewModel.highlightedImage.get())
                    projectOverviewFirst.arguments = bundle
                    return projectOverviewFirst
                }
                2 -> {
                    val technologyKeywordsFragment = TechnologyKeywordsFragment()
                    val bundle = Bundle()
                    bundle.clear()
                    bundle.putString("project_name",viewModel.projectName.get())
                    bundle.putString("project_image",viewModel.projectImageLogo.get())
                    bundle.putString("name","TECHNOLOGY")
                    bundle.putString("sub","RELATED KEYWORDS")
                    bundle.putString("framework","FRAMEWORK")
                    bundle.putString("content",viewModel.relatedKeyword.get())
                    technologyKeywordsFragment.arguments = bundle
                    return technologyKeywordsFragment
                }
                3 -> {
                    val liveUrlFragment = LiveUrlFragment()
                    val bundle = Bundle()
                    bundle.clear()
                    bundle.putString("project_name",viewModel.projectName.get())
                    bundle.putString("project_image",viewModel.projectImageLogo.get())
                    bundle.putString("name","LIVE URLS")
                    bundle.putString("website",viewModel.projectWebsite.get())
                    bundle.putString("webapp",viewModel.projectWebapp.get())
                    bundle.putString("android",viewModel.projectAndroidapp.get())
                    bundle.putString("ios",viewModel.projectIosapp.get())
                    liveUrlFragment.arguments = bundle
                    return liveUrlFragment
                }
                4 -> {
                    val projectScreenShot = ProjectScreenShotFragment()
                    val bundle = Bundle()
                    bundle.clear()
                    bundle.putString("project_name",viewModel.projectName.get())
                    bundle.putString("project_image",viewModel.projectImageLogo.get())
                    bundle.putString("name","PROJECT SCREENSHOTS")
                    bundle.putStringArrayList("screenshot",viewModel.projectScreenshots)
                    projectScreenShot.arguments = bundle
                    return projectScreenShot
                }
                else -> {
                    val projectOverviewFirst = ProjectDetailFragment()
                    val bundle = Bundle()
                    bundle.putString("project_name",viewModel.name.get())
                    bundle.putString("project_image",viewModel.projectImageLogo.get())
                    bundle.putString("name","OVERVIEW")
                    bundle.putString("content",viewModel.overviewContent.get())
                    bundle.putString("image",viewModel.overviewImage.get())
                    projectOverviewFirst.arguments = bundle
                    return projectOverviewFirst
                }
            }
        }

        override fun getCount(): Int {
            return numOfTabs
        }

    }

    override fun onResume() {
        viewModel.id.set(arguments?.getString("id"))
        viewModel.getProjectDetails()
        super.onResume()
    }

}