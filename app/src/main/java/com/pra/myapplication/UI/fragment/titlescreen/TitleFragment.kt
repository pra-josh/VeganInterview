package com.pra.myapplication.UI.fragment.titlescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.pra.myapplication.R
import com.pra.myapplication.UI.Listener.OnItemClickListener
import com.pra.myapplication.UI.Util.ViewModelFactory
import com.pra.myapplication.UI.adapter.TitleAdapter
import com.pra.myapplication.UI.fragment.BaseFragment
import com.pra.myapplication.data.model.Content
import com.pra.myapplication.databinding.FragmentTitleBinding

class TitleFragment : BaseFragment(), OnRefreshListener, OnItemClickListener {

    private lateinit var mBinding: FragmentTitleBinding
    private var mAdapter: TitleAdapter? = null
    private var mTitleList: MutableList<Content> = ArrayList()

    private lateinit var mViewModel: TitleViewModel

    override fun getLayoutResourceId(inflater: LayoutInflater): View {
        mBinding = FragmentTitleBinding.inflate(inflater)
        return mBinding.root
    }

    override fun initComponents(view: View?) {
        val factory = ViewModelFactory(mActivity)
        mViewModel = ViewModelProvider(this, factory)[TitleViewModel::class.java]

    }

    override fun prepareView() {
        val linearLayoutManager = LinearLayoutManager(mActivity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mBinding.rvtitleList.layoutManager = linearLayoutManager
        observableViewModel()

        mViewModel.fetchtitle(true)
       // fetchCountry(true)
    }

    override fun setActionListeners() {
        mBinding.swipeRefresh.setOnRefreshListener(this)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun setToolBar() {
    }

    private fun observableViewModel() {
        mViewModel.titleObserver.observe(this, Observer {
            it.let {
                mTitleList = it as MutableList<Content>
                setadapter()
            }
        })

        mViewModel.countryLoadError.observe(this, Observer {
            it?.let {
                if (it) {
                    mBinding.tvError.visibility = View.VISIBLE
                    mBinding.rvtitleList.visibility = View.GONE
                } else {
                    mBinding.tvError.visibility = View.GONE
                    mBinding.rvtitleList.visibility = View.VISIBLE
                }
            }
        })

        mViewModel.loading.observe(this, Observer {
            it?.let {
                if (it) {
                    mBinding.progressBar.visibility = View.VISIBLE
                    //  _binding.rvUser.visibility = View.GONE
                } else {
                    mBinding.progressBar.visibility = View.GONE
                    mBinding.rvtitleList.visibility = View.VISIBLE
                    mBinding.swipeRefresh.isRefreshing = false
                }
            }
        })
    }

    private fun setadapter() {
        if (mAdapter == null) {
            mAdapter = TitleAdapter(mActivity, mTitleList as ArrayList<Content>, this)
            mBinding.rvtitleList.adapter = mAdapter
        } else {
            mAdapter?.UpdateCountry(mTitleList)
        }
    }


    /**
     * @description: pull refresh the shipment order list
     * call API for shipment order list
     */
    override fun onRefresh() {
        mViewModel.fetchtitle(false)
    }

    override fun onDelete(position: Int) {
        mTitleList.removeAt(position)
        mAdapter?.notifyDataSetChanged()
        Toast.makeText(mActivity, "Data Deleted successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putParcelable("object", mTitleList[position])
        val navController = Navigation.findNavController(mBinding.root)
        navController.navigate(R.id.action_titleFragment_to_detailFragment, bundle)
    }

}