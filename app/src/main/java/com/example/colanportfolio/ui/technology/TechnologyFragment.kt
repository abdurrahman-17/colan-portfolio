package com.example.colanportfolio.ui.technology

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
import com.example.colanportfolio.data.model.technology.TechnologyItem
import com.example.colanportfolio.databinding.FragmentTechnologyBinding
import com.example.colanportfolio.databinding.TechItemBinding
import com.example.colanportfolio.ui.domaindetail.DomainDetailFragment
import kotlinx.android.synthetic.main.fragment_technology.*

class TechnologyFragment : BaseFragment<FragmentTechnologyBinding,TechnologyViewModel>() {

    private var technologyAdapter : BaseRecyclerViewAdapter<TechnologyItem, TechItemBinding>? = null
    private val technologyVM: TechnologyViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.TechnologyVM
    override val layoutId: Int
        get() = R.layout.fragment_technology
    override val viewModel: TechnologyViewModel
        get() {

            return technologyVM
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        technologyAdapter= BaseRecyclerViewAdapter(R.layout.tech_item,BR.technologyAdapter,
        viewModel.techList,null,object:OnDataBindCallback<TechItemBinding>{
                override fun onItemClick(view: View, position: Int, v: TechItemBinding) {
                    when(view.id){
                        R.id.technology_cv -> {
                            val bundle = Bundle()
                            bundle.putString("type","technology")
                            bundle.putString("technology_id",viewModel.techList[position].technology_id)
                            bundle.putString("title",viewModel.techList[position].technology_name)
                            bundle.putString("image",viewModel.techList[position].image)
                            replaceFragmentBundle(R.id.bottom_container, DomainDetailFragment(),"tech","tech",bundle)

                        }
                    }
                }

                override fun onDataBind(v: TechItemBinding, onClickListener: View.OnClickListener) {
                    v.technologyCv.setOnClickListener(onClickListener)
                }

            })
        viewDataBinding?.techRv?.adapter=technologyAdapter

        viewModel.mShowProgressBar.observeEvent(viewLifecycleOwner, Observer {
            it.let {
                showProgressBar(technology_layout)
            }
        })

        viewDataBinding?.TechSearchEditText?.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                viewModel.technologySearchApi()
                return@OnEditorActionListener true
            }
            false
        })

        viewModel.technologyListData.observe(viewLifecycleOwner,Observer{
            it.let {

                dismissProgressBar()
                viewModel.apiCallStatus.value = false
                when(it.data?.isSuccess){

                    true -> {
                        it.data.response.let {
                            /*viewModel.techList.clear()
                            viewModel.techList.addAll(it)
                            Log.d("techList",viewModel.techList.let { it.toString() })*/
                            technologyAdapter?.clearDataSet()
                            technologyAdapter?.addDataSet(it)
                            technologyAdapter?.notifyDataSetChanged()
                        }
                    }false->{
                    if (it.data.message.equals("No Project Data Found")){
                        technologyAdapter?.clearDataSet()
                    }
                    if(it.data.message.equals("No Data Found")){
                        technologyAdapter?.clearDataSet()
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