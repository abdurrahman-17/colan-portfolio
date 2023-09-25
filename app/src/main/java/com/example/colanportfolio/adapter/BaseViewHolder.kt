package com.example.colanportfolio.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<V : ViewDataBinding>(val viewDataBinding: V,
                                          private val onDataBindCallback: OnDataBindCallback<V>
) : RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener {

    init {
        onDataBindCallback.onDataBind(viewDataBinding, this)
        viewDataBinding.root.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        onDataBindCallback.onItemClick(view, adapterPosition,viewDataBinding)
    }

}