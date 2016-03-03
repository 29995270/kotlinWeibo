package com.wq.freeze.kotlinweibo.ui.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.wq.freeze.kotlinweibo.R
import com.wq.freeze.kotlinweibo.extension.aaaLogv
import com.wq.freeze.kotlinweibo.extension.lazyFind
import com.wq.freeze.kotlinweibo.extension.setWeiboContent
import com.wq.freeze.kotlinweibo.model.data.Weibo
import com.wq.freeze.kotlinweibo.ui.view.VDraweeView
import org.jetbrains.anko.find
import java.util.regex.Pattern
import kotlin.properties.Delegates

/**
 * Created by Administrator on 2016/2/27.
 */
class WeiboListAdapter(val dataSrc: MutableList<Weibo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_ITEM: Int = 0
    val TYPE_FOOTER: Int = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is WeiboHolder && getItemViewType(position) == TYPE_ITEM) {
            val weibo = dataSrc[position]
            if (weibo.user.verified) {
                holder.avatar.setTip(R.mipmap.avatar_vip)
            } else {
                holder.avatar.setTip(null)
            }
            holder.avatar.setImageURI(Uri.parse(weibo.user.profile_image_url), null)
            holder.name.text = weibo.user.name


            holder.from.text = weibo.created_at + "  来自" + weibo.getSourceString()
            holder.content.setWeiboContent(weibo.text)

            if (weibo.retweeted_status != null) {
                holder.retweeted.visibility = View.VISIBLE
                holder.retweetedContent.setWeiboContent("@${weibo.retweeted_status.user.name}:${weibo.retweeted_status.text}")
            } else{
                holder.retweeted.visibility = View.GONE
            }

        } else if (holder is LoadHolder && getItemViewType(position) == TYPE_FOOTER) {
            if (itemCount != 1)
                holder.progressBar.visibility = View.VISIBLE
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup?, type: Int): RecyclerView.ViewHolder {
        return if (type == TYPE_ITEM) {
            val view = LayoutInflater.from(viewGroup?.context).inflate(R.layout.item_weibo_list, viewGroup, false)
            WeiboHolder(view)
        } else {
            LoadHolder(LayoutInflater.from(viewGroup?.context).inflate(R.layout.widget_load_footer, viewGroup, false))
        }
    }

    override fun getItemCount(): Int {
        return dataSrc.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) TYPE_FOOTER else TYPE_ITEM
    }

    inner class WeiboHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar by lazyFind<VDraweeView>(R.id.avatar)
        val name by lazyFind<TextView>(R.id.name)
        val from by lazyFind<TextView>(R.id.from)
        val content by lazyFind<TextView>(R.id.content)
        val retweeted by lazyFind<ViewGroup>(R.id.retweeted_weibo)
        val retweetedContent by lazyFind<TextView>(R.id.retweeted_content)

        init {
            retweeted.visibility = View.GONE
        }
    }

    inner class LoadHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar by lazyFind<ProgressBar>(R.id.progress_bar)
        val loadMessage by lazyFind<TextView>(R.id.message)

        init {
            progressBar.visibility = View.INVISIBLE
            loadMessage.visibility = View.INVISIBLE
        }
    }

}