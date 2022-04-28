package com.ddevs.linkit.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ddevs.linkit.R
import com.ddevs.linkit.adapter.LinkRVAdapter
import com.ddevs.linkit.adapter.MeetRVAdapter
import com.ddevs.linkit.databinding.FragmentLinkBinding
import com.ddevs.linkit.databinding.FragmentMeetBinding
import com.ddevs.linkit.viewmodel.LinkViewModel
import com.ddevs.linkit.viewmodel.MeetViewModel


class MeetFragment : Fragment() {
    lateinit var binding: FragmentMeetBinding
    val viewmodel by lazy{
        ViewModelProvider(this).get(MeetViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMeetBinding.inflate(inflater,container,false)
        val view=binding.root
        val meetRVAdapter= MeetRVAdapter(viewmodel)
        meetRVAdapter.setLinks(listOf())
        binding.rvMeet.adapter=meetRVAdapter
        viewmodel.allLinks.observe(viewLifecycleOwner,{
            if(it!=null)
                meetRVAdapter.setLinks(it)
        })
        binding.shareBtn.setOnClickListener {
            startActivity(Intent(activity,ShareActivity::class.java))
        }
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_meetFragment_to_addLinkFragment)
        }
        return view
    }
}