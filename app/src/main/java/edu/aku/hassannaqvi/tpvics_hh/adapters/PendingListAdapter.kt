package edu.aku.hassannaqvi.tpvics_hh.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import edu.aku.hassannaqvi.tpvics_hh.R
import edu.aku.hassannaqvi.tpvics_hh.databinding.PendingformLayoutBinding
import edu.aku.hassannaqvi.tpvics_hh.models.FormsContract

class PendingListAdapter(private val mContext: Context, private var mList: List<FormsContract>) : RecyclerView.Adapter<PendingListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val bi: PendingformLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.pendingform_layout, viewGroup, false)
        return ViewHolder(bi)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.bi.parentLayout.tag = i
        holder.bi.cluster.text = "CLUSTER: ".plus(mList[i].clusterCode)
        holder.bi.hhno.text = "HHNO: ".plus(mList[i].hhno)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setMList(members: List<FormsContract>) {
        mList = members
        notifyDataSetChanged()
    }

    class ViewHolder(val bi: PendingformLayoutBinding) : RecyclerView.ViewHolder(bi.root)

}