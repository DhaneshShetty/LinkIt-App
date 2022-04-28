package com.ddevs.linkit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ddevs.linkit.R
import com.ddevs.linkit.databinding.FragmentAddLinkBinding
import com.ddevs.linkit.model.Link
import com.ddevs.linkit.model.MeetLink
import com.ddevs.linkit.viewmodel.AddLinkViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import java.lang.System.currentTimeMillis

class AddLinkFragment : Fragment() {
    lateinit var binding: FragmentAddLinkBinding
    private var newHour: Int = 12
    private var newMinute: Int = 0
    val viewmodel by lazy {
        ViewModelProvider(this).get(AddLinkViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddLinkBinding.inflate(inflater, container, false)
        binding.meetCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.materialCardView.visibility = View.VISIBLE
            } else {
                binding.materialCardView.visibility = View.GONE
            }
        }
        binding.closeSheet.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.calendarView.minDate = currentTimeMillis()
        binding.addBtn.setOnClickListener {
            var error=0
            if (binding.title.text.toString() == "") {
                binding.title.error = "Required"
                error=1
            }
            if (binding.link.text.toString() == ""){
                binding.link.error = "Required"
                error=1
            }
            if (binding.category.text.toString() == ""){
                binding.category.error = "Required"
                error=1
            }
            if(!URLUtil.isValidUrl(binding.link.text.toString())){
                binding.link.error = "Enter a proper URL"
                error=1;
            }
            if(error==0) {
                if (binding.meetCheckbox.isChecked) {
                    viewmodel.addMeetLink(
                        MeetLink(
                            title = binding.title.text.toString(),
                            meetUrl = binding.link.text.toString(),
                            notes = binding.desc.text.toString(),
                            startTime = binding.timePicker.text.toString(),
                            date = binding.calendarView.date
                        ), newHour, newMinute
                    )

                } else {
                    viewmodel.addLink(
                        Link(
                            title = binding.title.text.toString(),
                            url = binding.link.text.toString(),
                            notes = binding.desc.text.toString(),
                            category = binding.category.text.toString()
                        )
                    )
                }
                findNavController().navigateUp()
            }
        }
        binding.timePicker.setOnClickListener {
            val materialTimePicker = MaterialTimePicker.Builder()
                .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                .setHour(9)
                .setMinute(0)
                .build()
            materialTimePicker.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TimePicker)
            materialTimePicker.show(requireFragmentManager(), "fragment_tag")
            materialTimePicker.addOnPositiveButtonClickListener {
                newHour = materialTimePicker.hour
                newMinute = materialTimePicker.minute
                val hour: String
                val am_pm: String
                if (newHour > 12) {
                    hour = (newHour % 12).toString()
                    am_pm = "PM"
                } else if (newHour == 12) {
                    hour = 12.toString()
                    am_pm = "PM"
                } else {
                    hour = if (newHour == 0) {
                        12.toString()
                    } else {
                        newHour.toString()
                    }
                    am_pm = "AM"
                }
                val minute: String = if (newMinute in 0..9) {
                    "0$newMinute"
                } else {
                    newMinute.toString()
                }
                binding.timePicker.text = "$hour:$minute $am_pm"
            }

        }
        return binding.root
    }

}