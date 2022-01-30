package com.example.coronatesttrackersonntag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.coronaReporttrackersonntag.Model.CoronaReportAppModell
import com.example.coronatesttrackersonntag.databinding.*
import java.time.LocalTime



class Welcome : Fragment() {

    val coronaReportAppModell : CoronaReportAppModell by activityViewModels()
    val now : LocalTime = LocalTime.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding : FragmentWelcomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_welcome, container, false
        )

        binding.btToNewReport.setOnClickListener { view ->
            view?.findNavController()?.navigate(R.id.action_welcome_to_newReport)
        }

        binding.btToReportList.setOnClickListener { view ->
            view?.findNavController()?.navigate(R.id.action_welcome_to_reportList)
        }






        return binding.root
    }


}