package com.ddevs.linkit.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ddevs.linkit.R
import com.ddevs.linkit.model.ShareLink
import com.ddevs.linkit.viewmodel.ShareViewModel

class ShareRVAdapter(val viewModel: ShareViewModel): RecyclerView.Adapter<ShareRVAdapter.ViewHolder>() {
    private lateinit var mSharedLinks:List<ShareLink>

    fun setSharedLinks(links:List<ShareLink>){
        mSharedLinks=links
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val link: TextView = itemView.findViewById(R.id.link)
        val deleteBtn:ImageButton =itemView.findViewById(R.id.delete_link)
        val copyBtn:ImageButton=itemView.findViewById(R.id.copy_link)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view= LayoutInflater.from(parent.context).inflate(R.layout.share_link_item,parent,false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.link.text= mSharedLinks[position].url
        holder.deleteBtn.setOnClickListener {
            viewModel.deleteCurrentLink(mSharedLinks[position])
        }
        holder.copyBtn.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,mSharedLinks[position].url)
            holder.itemView.context.startActivity(Intent.createChooser(intent,"Share via"))
        }
        holder.link.setOnLongClickListener {
            viewModel.copyToClipBoard(mSharedLinks[position].url)
            Toast.makeText(holder.itemView.context,"Link Copied",Toast.LENGTH_SHORT).show()
            true
        }
    }

    override fun getItemCount(): Int {
        return mSharedLinks.size
    }

}