 package com.example.coronatrackersamstag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.coronatrackersamstag.Model.CoronaReportAppModel
import com.example.coronatrackersamstag.databinding.FragmentWelcomeBinding
import java.time.LocalDateTime

/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {
    val coronaReportAppModel : CoronaReportAppModel by activityViewModels()
    val now: LocalDateTime = LocalDateTime.now();

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentWelcomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_welcome, container, false
        )

        binding.btEntryList.setOnClickListener { view ->
            view.findNavController()?.navigate(R.id.action_welcomeFragment_to_reportList)
        }

        binding.btNewEntry.setOnClickListener { view ->
            view.findNavController()?.navigate(R.id.action_welcomeFragment_to_newReport)
        }


        var anzNeg = 0
        var anzPos = 0
        var anzLinzHauptplatz = 0
        var anzLeondingMeixner = 0
        var anzParkplatzHaid = 0


        coronaReportAppModel.reportList.value?.filter {
            true
        }?.forEach {
            if(it.isPositive){
                anzPos++
            }else {
                anzNeg++
            }

            when(it.office) {
                "Linz-Stadtplatz" -> anzLinzHauptplatz++
                "Leonding-Meixnerkreuzung" -> anzLeondingMeixner++
                "Parkplatz-Haidcenter" -> anzParkplatzHaid++
            }


        }

        binding.tvQuantityLeondingMeixnerkruezung.text = anzLeondingMeixner.toString()
        binding.tvQuantityLinzStadtplatz.text = anzLinzHauptplatz.toString()
        binding.tvQuantityParkplatzHaid.text = anzParkplatzHaid.toString()

        binding.pbPositivNegativ.max = anzPos + anzNeg
        binding.pbPositivNegativ.min = 0
        binding.pbPositivNegativ.progress = anzPos

        return binding.root
        //return super.onCreateView(inflater, container, savedInstanceState)
    }
}