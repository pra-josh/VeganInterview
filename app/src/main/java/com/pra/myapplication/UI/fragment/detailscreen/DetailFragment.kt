package com.pra.myapplication.UI.fragment.detailscreen

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import com.pra.myapplication.databinding.FragmentDetailBinding
import android.content.Intent
import android.net.Uri
import com.pra.myapplication.UI.fragment.BaseFragment
import com.pra.myapplication.data.model.Content
import java.util.*


class DetailFragment : BaseFragment() {

    private lateinit var mBinding: FragmentDetailBinding

    private var mContent: Content? = null

    override fun getLayoutResourceId(inflater: LayoutInflater): View {
        mBinding = FragmentDetailBinding.inflate(inflater)
        return mBinding.root
    }

    override fun initComponents(view: View?) {
    }

    override fun prepareView() {
        getData()
        mBinding.tvUrl.setMovementMethod(LinkMovementMethod.getInstance());
        mBinding.tvTitle.text = mContent?.mediaTitleCustom
        mBinding.tvDate.text = mContent?.mediaDate?.dateString
        mBinding.tvUrl.text = mContent?.mediaUrl

        if (mContent?.mediaUrl?.isNotEmpty()!!) {
            mBinding.btnPdf.visibility = View.VISIBLE
        } else {
            mBinding.btnPdf.visibility = View.GONE
        }
    }

    override fun setActionListeners() {
        mBinding.btnPdf.setOnClickListener {
            val format = "https://drive.google.com/viewerng/viewer?embedded=true&url="+mContent?.mediaUrl
            val fullPath: String = java.lang.String.format(Locale.ENGLISH, format, "PDF_URL_HERE")
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(fullPath))
            startActivity(browserIntent)
        }
    }

    override fun setToolBar() {
    }


    private fun getData() {
        val bundle = arguments
        if (bundle != null) {
            if (bundle.containsKey("object")) {
                mContent = bundle.getParcelable("object")
            }
        }
    }
}