package com.ddevs.linkit.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ddevs.linkit.R
import com.ddevs.linkit.adapter.LinkRVAdapter
import com.ddevs.linkit.databinding.FragmentLinkBinding
import com.ddevs.linkit.model.Link
import com.ddevs.linkit.viewmodel.LinkViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior


class LinkFragment : Fragment() {
    lateinit var binding:FragmentLinkBinding
    val viewmodel by lazy{
        ViewModelProvider(this).get(LinkViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLinkBinding.inflate(inflater,container,false)
        val view=binding.root
        val linkRVAdapter=LinkRVAdapter(viewmodel)
        linkRVAdapter.setLinks(listOf())
        binding.rvLinks.adapter=linkRVAdapter
        viewmodel.allLinks.observe(viewLifecycleOwner,{
                if(it!=null)
                    linkRVAdapter.setLinks(it)
            })
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_linkFragment_to_addLinkFragment)
        }
        binding.shareBtn.setOnClickListener {
            startActivity(Intent(activity,ShareActivity::class.java))
        }
        return view
    }

}