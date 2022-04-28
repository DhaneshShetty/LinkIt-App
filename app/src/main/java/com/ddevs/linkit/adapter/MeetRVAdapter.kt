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
import androidx.work.WorkManager
import com.ddevs.linkit.R
import com.ddevs.linkit.model.MeetLink
import com.ddevs.linkit.viewmodel.MeetViewModel
import java.text.DateFormat

class MeetRVAdapter(private val viewModel: MeetViewModel): RecyclerView.Adapter<MeetRVAdapter.ViewHolder>() {
    private lateinit var mLinks: List<MeetLink>

    fun setLinks(links: List<MeetLink>){
        mLinks=links
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mLinks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meet_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text=mLinks[position].title
        holder.desc.text=mLinks[position].notes
        holder.link.text=mLinks[position].meetUrl
        holder.time.text= mLinks[position].startTime
        val dFormat: DateFormat = DateFormat.getDateInstance()
        val date= dFormat.format(mLinks[position].date)
        holder.date.text= date
        if(holder.desc.text.isEmpty())
            holder.desc.visibility=View.GONE
        holder.deleteBtn.setOnClickListener {
            viewModel.deleteCurrentLink(mLinks[position])
        }
        holder.copyBtn.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, mLinks[position].meetUrl)
            holder.itemView.context.startActivity(Intent.createChooser(intent, "Share via"))
            viewModel.copyToClipBoard(mLinks[position].meetUrl)
        }
        holder.link.setOnLongClickListener {
            viewModel.copyToClipBoard(mLinks[position].meetUrl)
            Toast.makeText(holder.itemView.context,"Link Copied", Toast.LENGTH_SHORT).show()
            true
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val desc: TextView = itemView.findViewById(R.id.desc)
        val link: TextView = itemView.findViewById(R.id.link)
        val time: TextView = itemView.findViewById(R.id.time)
        val date: TextView = itemView.findViewById(R.id.date)
        val deleteBtn: ImageButton =itemView.findViewById(R.id.delete_link)
        val copyBtn: ImageButton =itemView.findViewById(R.id.copy_link)
    }
}