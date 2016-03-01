package com.wq.freeze.kotlinweibo.ui.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wq.freeze.kotlinweibo.R
import com.wq.freeze.kotlinweibo.extension.lazyFind
import com.wq.freeze.kotlinweibo.model.data.Weibo
import com.wq.freeze.kotlinweibo.ui.view.VDraweeView
import org.jetbrains.anko.find
import kotlin.properties.Delegates

/**
 * Created by Administrator on 2016/2/27.
 */
class WeiboListAdapter(val dataSrc: MutableList<Weibo>): RecyclerView.Adapter<WeiboListAdapter.WeiboHolder>() {


    override fun onBindViewHolder(holder: WeiboListAdapter.WeiboHolder, position: Int) {
        val weibo = dataSrc[position]
        if (weibo.user.verified) {
            holder.avatar.setTip(R.mipmap.avatar_vip)
        } else {
            holder.avatar.setTip(null)
        }
        holder.avatar.setImageURI(Uri.parse(weibo.user.profile_image_url), null)
        holder.name.text = weibo.user.name
        holder.from.text = weibo.created_at + "  " + weibo.source
        holder.content.text = weibo.text
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup?, position: Int): WeiboListAdapter.WeiboHolder {
        val view = LayoutInflater.from(viewGroup?.context).inflate(R.layout.item_weibo_list, viewGroup, false)
        return WeiboHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSrc.size
    }

    inner class WeiboHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val avatar by lazyFind<VDraweeView>(R.id.avatar)
        val name by lazyFind<TextView>(R.id.name)
        val from by lazyFind<TextView>(R.id.from)
        val content by lazyFind<TextView>(R.id.content)
    }

}