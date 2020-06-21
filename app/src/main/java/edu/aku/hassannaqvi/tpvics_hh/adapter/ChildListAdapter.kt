package edu.aku.hassannaqvi.tpvics_hh.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.aku.hassannaqvi.tpvics_hh.R
import edu.aku.hassannaqvi.tpvics_hh.contracts.ChildContract
import edu.aku.hassannaqvi.tpvics_hh.databinding.ItemChildListBinding
import edu.aku.hassannaqvi.tpvics_hh.utils.getMemberIcon
import edu.aku.hassannaqvi.tpvics_hh.viewmodel.MainVModel

class ChildListAdapter(private val mContext: Context, private var mList: List<ChildContract>, private val vModel: MainVModel) : RecyclerView.Adapter<ChildListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val bi: ItemChildListBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_child_list, viewGroup, false)
        return ViewHolder(bi)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.bi.parentLayout.tag = i
        holder.bi.name.text = mList[i].childName
        if (mList[i].cstatus != "1")
            holder.bi.containeridcard.setBackgroundColor(ContextCompat.getColor(mContext, R.color.shifted))
        Glide.with(mContext)
                .asBitmap()
                .load(getMemberIcon(mList[i].getgender().toInt()))
                .into(holder.bi.childImage)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setMList(members: List<ChildContract>) {
        mList = members
        notifyDataSetChanged()
    }

    class ViewHolder(val bi: ItemChildListBinding) : RecyclerView.ViewHolder(bi.root)

}