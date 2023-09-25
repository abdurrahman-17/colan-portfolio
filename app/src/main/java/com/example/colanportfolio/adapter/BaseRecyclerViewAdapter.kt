package com.example.colanportfolio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseRecyclerViewAdapter<T, V : ViewDataBinding>(
        @LayoutRes
        private val layoutResourceId: Int,
        private val bindVariableId: Int,
        private var items: ArrayList<T>,
        private var dataVariables: Map<Int, Any>?,
        private val onDataBindCallback: OnDataBindCallback<V>
) : RecyclerView.Adapter<BaseViewHolder<V>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        return BaseViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        layoutResourceId,
                        parent,
                        false
                ), onDataBindCallback
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
        if (dataVariables != null) {
            for (data in dataVariables!!.entries) {
                holder.viewDataBinding.setVariable(data.key, data.value)
            }
        }

        holder.viewDataBinding.setVariable(bindVariableId, getItem(position))
        holder.viewDataBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun getItem(position: Int): T {
        return items[position]
    }

    fun addDataSet(data: List<T>) {
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun getItems(): List<T> {
        return items
    }

    fun clearDataSet() {
        items.clear()
        notifyDataSetChanged()
    }

    fun addUpdateSet(position: Int, data: T) {
        items.set(position, data)
        notifyDataSetChanged()
    }

    fun removeList(position: Int) {
        items.removeAt(position)
        notifyDataSetChanged()
    }

    fun add(r: T) {
        items.add(r)
        notifyItemInserted(items.size)
    }

    fun addData(data: List<T>) {
        for (reactDatum in data) {
            add(reactDatum)
        }
    }

}