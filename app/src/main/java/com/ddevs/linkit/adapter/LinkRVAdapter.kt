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
import com.ddevs.linkit.databinding.ActivityMainBinding.inflate
import com.ddevs.linkit.model.Link
import com.ddevs.linkit.model.ShareLink
import com.ddevs.linkit.viewmodel.LinkViewModel
import com.google.android.material.chip.Chip

class LinkRVAdapter(private val viewModel: LinkViewModel): RecyclerView.Adapter<LinkRVAdapter.ViewHolder>() {
    lateinit var mLinks: List<Link>

    fun setLinks(links:List<Link>){
        mLinks=links
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mLinks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.link_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text=mLinks[position].title
        holder.desc.text=mLinks[position].notes
        holder.link.text=mLinks[position].url
        holder.category.text=mLinks[position].category
        if(holder.desc.text.isEmpty())
            holder.desc.visibility=View.GONE
        holder.deleteBtn.setOnClickListener {
            viewModel.deleteCurrentLink(mLinks[position])
        }
        holder.copyBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,mLinks[position].url)
            holder.itemView.context.startActivity(Intent.createChooser(intent,"Share via"))

        }
        holder.link.setOnLongClickListener {
            viewModel.copyToClipBoard(mLinks[position].url)
            Toast.makeText(holder.itemView.context,"Link Copied", Toast.LENGTH_SHORT).show()
            true
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val desc: TextView = itemView.findViewById(R.id.desc)
        val link: TextView = itemView.findViewById(R.id.link)
        val deleteBtn: ImageButton =itemView.findViewById(R.id.delete_link)
        val copyBtn: ImageButton =itemView.findViewById(R.id.copy_link)
        val category: Chip = itemView.findViewById(R.id.category)
    }
}