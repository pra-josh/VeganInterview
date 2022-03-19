package com.pra.myapplication.UI.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pra.myapplication.UI.Listener.OnItemClickListener
import com.pra.myapplication.data.model.Content
import com.pra.myapplication.databinding.RowTitleBinding

class TitleAdapter(
    private val context: Context,
    private val userList: ArrayList<Content>,
    private val mOnItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<TitleAdapter.MyViewHolder>() {


    fun UpdateCountry(newCountry: List<Content>) {
        userList.clear()
        userList.addAll(newCountry)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding =
            RowTitleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemBinding.tvTitle.text = userList[position].mediaTitleCustom

        holder.itemBinding.llMain.setOnClickListener {

         mOnItemClickListener.onItemClick(position)
        }

        holder.itemBinding.btnDelete.setOnClickListener {
            mOnItemClickListener.onDelete(position)
        }
    }

    override fun getItemCount(): Int = userList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    class MyViewHolder(public val itemBinding: RowTitleBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

    }
}