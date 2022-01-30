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

        //Progress Bar Values
        var anzPos = 0
        var anzNeg = 0

        //Statistik Values
        var anzLinz = 0
        var anzLeonding = 0
        var anzHaid = 0


        coronaReportAppModell.ReportList.value?.forEach {
            if(it.isPositive){
                anzPos++
            }else {
                anzNeg++
            }

            when(it.office){
                "Linz-Stadtplatz" -> anzLinz++
                "Leonding-Meixnerkreuzung" -> anzLeonding++
                "Parkplatz-Haidcenter" -> anzHaid++
            }
        }

        binding.progressBar.max = anzNeg + anzPos
        binding.progressBar.min = 0
        binding.progressBar.progress = anzPos

        binding.tvAnzLinzStadtplatz.text = anzLinz.toString()
        binding.tvLeondingMeixner.text = anzLeonding.toString()
        binding.tvAnzParkplatzHaid.text = anzHaid.toString()

        return binding.root
    }


}