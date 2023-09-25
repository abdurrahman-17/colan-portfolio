package com.example.colanportfolio.ui.screenshotimageoverview

import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.example.colanportfolio.data.model.domaindetailitem.ProjectScreenShotsModel
import com.example.colanportfolio.data.model.domaindetailitem.ScreenShotImageItem
import com.example.colanportfolio.databinding.FragmentScreenShotImageOverviewBinding
import com.example.colanportfolio.databinding.ScreenShotImageItemBinding
import com.example.colanportfolio.databinding.ScreenshotImageBinding
import com.example.colanportfolio.ui.indicator.CirclePagerIndicatorDecoration
import com.example.colanportfolio.ui.projectscreenshot.ProjectScreenShotViewModel
import com.example.colanportfolio.ui.viewpager.LinePagerIndicatorDecoration
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*


class ScreenShotImageOverviewFragment :BaseFragment<FragmentScreenShotImageOverviewBinding, ScreenShotImageOverviewViewModel>() {

    private var screenshotAdapter : BaseRecyclerViewAdapter<ScreenShotImageItem, ScreenShotImageItemBinding>? = null
    val screen = ArrayList<ScreenShotImageItem>()


    private val imageVM: ScreenShotImageOverviewViewModel by viewModels()


    private var bottomNavigation :BottomNavigationView?=null

    override val bindingVariable: Int
        get() = BR.imageVm
    override val layoutId: Int
        get() =R.layout.fragment_screen_shot_image_overview
    override val viewModel: ScreenShotImageOverviewViewModel
        get(){
            return imageVM
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
    //    viewModel.image.set(arguments?.getString("image"))


        screen.clear()
        arguments?.getStringArrayList("screenshot")?.let { viewModel.projectScreenShot.addAll(it) }
        viewModel.projectScreenShot.forEach {
            screen.addAll(listOf(ScreenShotImageItem(image = it)))
        }

        screenshotAdapter = BaseRecyclerViewAdapter(R.layout.screen_shot_image_item,BR.screenshotItemAdapter,viewModel.screenshot,
        null,object : OnDataBindCallback<ScreenShotImageItemBinding>{
                override fun onItemClick(view: View, position: Int, v: ScreenShotImageItemBinding) {}

                override fun onDataBind(
                    v: ScreenShotImageItemBinding,
                    onClickListener: View.OnClickListener
                ) {}

            })

        viewModel.backNavigation.observeEvent(viewLifecycleOwner, Observer {

            it.let {
                activity?.supportFragmentManager?.popBackStack()
            }
        })

        viewDataBinding?.screenshotImageRv?.adapter = screenshotAdapter
        viewDataBinding?.screenshotImageRv?.addItemDecoration(CirclePagerIndicatorDecoration())


        screenshotAdapter?.clearDataSet()
        screenshotAdapter?.addDataSet(screen)
        screenshotAdapter?.notifyDataSetChanged()
     //   viewDataBinding?.screenshotImageRv?.addItemDecoration(LinePagerIndicatorDecoration())


        return viewDataBinding?.root
    }


}