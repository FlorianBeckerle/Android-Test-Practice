package com.example.coronatesttrackersonntag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.coronaReporttrackersonntag.Model.CoronaReportAppModell
import com.example.coronatesttrackersonntag.Model.Report
import com.example.coronatesttrackersonntag.databinding.*
import java.util.Observer


class ReportList : Fragment() {
   val coronaReportAppModell : CoronaReportAppModell by activityViewModels()



    private lateinit var binding : FragmentReportListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_report_list, container, false
        )

        binding.btToWelcome.setOnClickListener { view ->
            view?.findNavController()?.navigate(R.id.action_reportList_to_welcome)
        }

        coronaReportAppModell.ReportList.observe(viewLifecycleOwner, androidx.lifecycle.Observer { reports ->
            updateReportList(reports)
        })


        return binding.root
    }

    fun updateReportList(entries:MutableList<Report>){
        val adapter: ArrayAdapter<Report>? = context?.let {
            ArrayAdapter<Report>(
                it,
                android.R.layout.simple_list_item_1, android.R.id.text1, entries
            )
        }
        binding.listView.adapter = adapter
    }

}