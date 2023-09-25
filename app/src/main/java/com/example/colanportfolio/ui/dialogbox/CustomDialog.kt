package com.example.colanportfolio.ui.dialogbox

import android.R.attr
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.colanportfolio.BR
import com.example.colanportfolio.R
import com.example.colanportfolio.adapter.BaseRecyclerViewAdapter
import com.example.colanportfolio.adapter.OnDataBindCallback
import com.example.colanportfolio.data.model.domaindetailitem.DomainDetailItem
import com.example.colanportfolio.data.model.domaindetailitem.ProjectScreenShotsModel
import com.example.colanportfolio.databinding.DomainDetailItemBinding
import com.example.colanportfolio.databinding.ScreenshotImageBinding
import android.R.attr.defaultValue

import android.R.attr.key
import android.R.attr.defaultValue

import android.R.attr.key
import android.util.Log
import androidx.core.view.get


class CustomDialog:DialogFragment() {

    private var customDialogAdapter: BaseRecyclerViewAdapter<ProjectScreenShotsModel, ScreenshotImageBinding>? =
        null


    var screenShotList = ArrayList<ProjectScreenShotsModel>()
    private var close: ImageView? = null
    private var recyclerView: RecyclerView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.custom_dialog, container, false)

        dialog?.show()
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        Log.d("list",screenShotList.let { it.toString() })
        close = v.findViewById(R.id.dialogClose)
        close?.setOnClickListener {
            dialog?.dismiss()
        }

        customDialogAdapter = BaseRecyclerViewAdapter(R.layout.screenshot_image,
            BR.ScreenShotItem, screenShotList, null,
            object : OnDataBindCallback<ScreenshotImageBinding> {
                override fun onItemClick(view: View, position: Int, v: ScreenshotImageBinding) {}

                override fun onDataBind(
                    v: ScreenshotImageBinding,
                    onClickListener: View.OnClickListener
                ) {
                }
            })

        recyclerView?.adapter = customDialogAdapter

        return v

    }

}