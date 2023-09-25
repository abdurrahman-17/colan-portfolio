package com.example.colanportfolio.aboutadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.colanportfolio.R
import com.example.colanportfolio.data.model.aboutus.About
import kotlinx.android.synthetic.main.about_technology.view.*

class AboutUsAdapter(private var DomainList: ArrayList<About>, val context: Context): RecyclerView.Adapter<AboutUsAdapter.viewHolder>() {

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: About) {
            itemView.about_title.text = model.title
            itemView.about_desc.text = model.desc
            itemView.about_pic.setImageResource(model.image.toInt())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.about_technology, parent, false)
        return viewHolder(v)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(DomainList[position])
    }

    override fun getItemCount(): Int {
        return DomainList.size
    }
}