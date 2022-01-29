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
import com.example.coronatrackersamstag.databinding.FragmentReportListBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ReportList.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReportList : Fragment() {

    val coronaReportAppModel : CoronaReportAppModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding : FragmentReportListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_report_list, container, false
        )

        binding.btHome.setOnClickListener { view ->
            view?.findNavController()?.navigate(R.id.action_reportList_to_welcomeFragment)
        }

        var stringBuilder = StringBuilder()
        var id = ""

        coronaReportAppModel.reportList.value?.forEach {
            if(it.id.length <= 5){
                id = "OÖ1234"
            }else {
                id = it.id
            }

            stringBuilder.append(
                String.format(
                    "%s\n       %s\n         %s\n       %s\n\n",
                    "ID: " + id.substring(0,2) + "..." + id.substring(id.length-4, id.length),
                    "Datum und Uhrzeit: " + it.dateAndTime.toLocalDate() + " / " + it.dateAndTime.toLocalTime(),
                    "Ergebnis: " + convertPositive(it.isPositive),
                    "Standort: " + it.office

                )
            )
        }

        binding.tvReportList.text = stringBuilder.toString()

        return binding.root
    }

    fun convertPositive(isPositive : Boolean) : String{
        if(isPositive){
            return "Positiv"
        }else {
            return "Negativ"
        }
    }


}