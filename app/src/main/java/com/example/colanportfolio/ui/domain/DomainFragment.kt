package com.example.colanportfolio.ui.domain

import android.content.pm.ActivityInfo
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.colanportfolio.BR
import com.example.colanportfolio.R
import com.example.colanportfolio.adapter.BaseRecyclerViewAdapter
import com.example.colanportfolio.adapter.OnDataBindCallback
import com.example.colanportfolio.base.BaseFragment
import com.example.colanportfolio.data.local.SharedPreferenceImp
import com.example.colanportfolio.data.model.domain.DomainDataItem
import com.example.colanportfolio.databinding.DomainItemBinding
import com.example.colanportfolio.databinding.FragmentDomainBinding
import com.example.colanportfolio.ui.domaindetail.DomainDetailFragment
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_domain.*


class DomainFragment : BaseFragment<FragmentDomainBinding,DomainViewModel>() {

    private var domainAdapter : BaseRecyclerViewAdapter<DomainDataItem, DomainItemBinding>? = null
    private val DomainVM:DomainViewModel by viewModels()
    private var dialog: SpotsDialog? = null

    override val bindingVariable: Int
        get() = BR.DomainVM
    override val layoutId: Int
        get() = R.layout.fragment_domain
    override val viewModel: DomainViewModel
        get() {

            return DomainVM
        }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)



        dialog = SpotsDialog(activity, "Please Wait...")
        domainAdapter = BaseRecyclerViewAdapter(R.layout.domain_item,BR.domainAdapter,
        viewModel.domainList, null,object : OnDataBindCallback<DomainItemBinding>{
                override fun onItemClick(view: View, position: Int, v: DomainItemBinding) {
                    when(view.id){
                        R.id.domain_cv -> {
                            val bundle = Bundle()
                            bundle.putString("type","domain")
                            bundle.putString("domain_id",viewModel.domainList[position].domainId)
                            bundle.putString("title",viewModel.domainList[position].domainName)
                            bundle.putString("image",viewModel.domainList[position].image)
                            replaceFragmentBundle(R.id.bottom_container,DomainDetailFragment(),"domain","domain",bundle)
                        }
                    }
                }

                override fun onDataBind(
                    v: DomainItemBinding,
                    onClickListener: View.OnClickListener
                ) {
                    v.domainCv.setOnClickListener(onClickListener)
                }
            })
        viewDataBinding?.domainRv?.adapter = domainAdapter

        viewModel.mShowProgressBar.observeEvent(viewLifecycleOwner, Observer {
            it.let {
                showProgressBar(domain_layout)
            }
        })

        viewDataBinding?.DomainSearchEditText?.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.domainSearchApi()
                return@OnEditorActionListener true
            }
            false
        })

        viewModel.domainListData.observe(viewLifecycleOwner, Observer {
            it.let {
                dismissProgressBar()
                viewModel.apiCallStatus.value = false
                when(it.data?.isSuccess){
                    true -> {
                       /* it.data.response.forEach{
                            viewModel.domainList.clear()
                            viewModel.domainList.add(it)
                            domainAdapter?.addDataSet(viewModel.domainList)
                            //domainAdapter?.notifyDataSetChanged()
                        }*/
                        it.data.response.let {
                        domainAdapter?.clearDataSet()
                            //viewModel.domainList.clear()
                            //viewModel.domainList.addAll(it)
                            //Log.d("domainList",viewModel.domainList.let { it.toString() })
                            domainAdapter?.addDataSet(it)
                            domainAdapter?.notifyDataSetChanged()
                        }
                    }false->{
                    if (it.data.message.equals("Data Not Found")){
                        domainAdapter?.clearDataSet()
                    }
                    putToast(it.data.message)
                    }
                    else->{
                        putToast(resources.getString(R.string.internet_connection))
                    }
                }
            }
        })

        return viewDataBinding?.root
    }

}