package com.wq.freeze.kotlinweibo.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Administrator on 2016/2/27.
 */
class WeiboListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {

    }

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): RecyclerView.ViewHolder {
        return WeiboHolder(TextView(p0?.context))
    }

    override fun getItemCount(): Int {
        return 2
    }

    inner class WeiboHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}