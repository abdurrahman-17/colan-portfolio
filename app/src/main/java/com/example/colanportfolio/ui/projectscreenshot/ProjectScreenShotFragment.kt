package com.example.colanportfolio.ui.projectscreenshot

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.colanportfolio.BR
import com.example.colanportfolio.R
import com.example.colanportfolio.adapter.BaseRecyclerViewAdapter
import com.example.colanportfolio.adapter.OnDataBindCallback
import com.example.colanportfolio.base.BaseFragment
import com.example.colanportfolio.data.model.domaindetailitem.ProjectScreenShotsModel
import com.example.colanportfolio.databinding.FragmentProjectScreenShotBinding
import com.example.colanportfolio.databinding.ScreenshotImageBinding
import com.example.colanportfolio.ui.dialogbox.CustomDialog
import com.example.colanportfolio.ui.screenshotimageoverview.ScreenShotImageOverviewFragment
import com.example.colanportfolio.ui.viewpager.LinePagerIndicatorDecoration
import com.zolad.zoominimageview.ZoomInImageView
import kotlinx.android.synthetic.main.fragment_splash_screen.*
import java.text.FieldPosition


class ProjectScreenShotFragment : BaseFragment<FragmentProjectScreenShotBinding,ProjectScreenShotViewModel>() {

    private val projectScreenShotVM: ProjectScreenShotViewModel by viewModels()
    val screen = ArrayList<ProjectScreenShotsModel>()
    var dialog : CustomDialog ?= null
    private var screenshotAdapter :BaseRecyclerViewAdapter<ProjectScreenShotsModel,ScreenshotImageBinding>? = null


    override val bindingVariable: Int
        get() = BR.projectScreenShotVM
    override val layoutId: Int
        get() = R.layout.fragment_project_screen_shot
    override val viewModel: ProjectScreenShotViewModel
        get() {
            return projectScreenShotVM
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel.project_name.set(arguments?.getString("project_name"))
        viewModel.project_image.set(arguments?.getString("project_image"))
        viewModel.name.set(arguments?.getString("name"))



        screen.clear()
        viewModel.project_name.set(arguments?.getString("project_name"))
        viewModel.project_image.set(arguments?.getString("project_image"))
        viewModel.name.set(arguments?.getString("name"))
        viewModel.projectScreenShot.clear()
        arguments?.getStringArrayList("screenshot")?.let { viewModel.projectScreenShot.addAll(it) }
        viewModel.projectScreenShot.forEach {
            screen.addAll(listOf(ProjectScreenShotsModel(image = it)))
        }

        screenshotAdapter = BaseRecyclerViewAdapter(R.layout.screenshot_image,BR.screenshotAdapter,viewModel.screenshot,
        null, object : OnDataBindCallback<ScreenshotImageBinding>{
                override fun onItemClick(view: View, position: Int, v: ScreenshotImageBinding) {

                    when(view.id){

                        R.id.screenshot_iv ->{
                            val bundle = Bundle()
                            bundle.putStringArrayList("screenshot",viewModel.projectScreenShot)
                            replaceFragmentBundle(R.id.bottom_container, ScreenShotImageOverviewFragment(),"image","image",bundle)

                        }
                    }

                }

                override fun onDataBind(
                    v: ScreenshotImageBinding,
                    onClickListener: View.OnClickListener
                ) {

                    v.screenshotIv.setOnClickListener(onClickListener)
                }

            })
        viewDataBinding?.screenshotRv?.adapter = screenshotAdapter
        screenshotAdapter?.clearDataSet()
        screenshotAdapter?.addDataSet(screen)
        screenshotAdapter?.notifyDataSetChanged()
      //  viewDataBinding?.screenshotRv?.addItemDecoration(LinePagerIndicatorDecoration())

        return viewDataBinding?.root
    }

    private fun showDialogAlert(position: Int) {
        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.screen_shot_dialog_item, null)
        val dialogView: ZoomInImageView = mDialogView.findViewById(R.id.screenshot_ziv)
        val mBuilder = AlertDialog.Builder(activity)
        mBuilder.setView(mDialogView)
        Glide.with(this).load(viewModel.screenshot.get(position).image).into(dialogView)
        val closeView: ImageView = mDialogView.findViewById(R.id.close_iv)
        mBuilder.setCancelable(false)
        val rr = mBuilder.show()
        closeView.setOnClickListener(View.OnClickListener {
            // Dismiss the popup
            rr.dismiss()
        })
    }

    override fun onResume() {
        viewModel.projectScreenShot.clear()
        arguments?.getStringArrayList("screenshot")?.let { viewModel.projectScreenShot.addAll(it) }
        super.onResume()
    }


}