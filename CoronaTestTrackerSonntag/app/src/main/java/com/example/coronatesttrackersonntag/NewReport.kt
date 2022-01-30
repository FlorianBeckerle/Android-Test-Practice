package com.example.coronatesttrackersonntag

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.coronaReporttrackersonntag.Model.CoronaReportAppModell
import com.example.coronatesttrackersonntag.Model.Report
import com.example.coronatesttrackersonntag.databinding.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*


class NewReport : Fragment() {

    val coronaReportAppModell : CoronaReportAppModell by activityViewModels()

    var datePickerDialog : DatePickerDialog? = null
    var timePickerDialog : TimePickerDialog? = null

    var date : LocalDate = LocalDate.now()
    var time : LocalTime = LocalTime.now()

    private var idValid = true
    private var idUnique = true

    private lateinit var binding: FragmentNewReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_new_report, container, false
        )


        binding.btSave.setOnClickListener { view ->
            validate(view)
        }

        binding.etInputDate.inputType = 0
        binding.etInputTime.inputType = 0

        binding.etInputDate.setOnClickListener { view ->
            showDatePickerDialog(view)
        }

        binding.etInputTime.setOnClickListener { view ->
            showTimePickerDialog(view)
        }



        return binding.root
    }

    private fun showDatePickerDialog(v: View) {
        val cldr: Calendar = Calendar.getInstance()
        val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
        val month: Int = cldr.get(Calendar.MONTH)
        val year: Int = cldr.get(Calendar.YEAR)
        datePickerDialog =
            context?.let {
                DatePickerDialog(
                    it,
                    { _, year, monthOfYear, dayOfMonth ->
                        run {
                            binding.etInputDate.setText(
                                dayOfMonth.toString() + "/" + (monthOfYear + 1) +
                                        "/" + year
                            )
                            date = LocalDate.of(year,monthOfYear+1,dayOfMonth)
                        }
                    },
                    year,
                    month,
                    day
                )
            }
        datePickerDialog!!.show()
    }

    private fun showTimePickerDialog(v: View) {
        val cldr = Calendar.getInstance()
        val hour = cldr[Calendar.HOUR_OF_DAY]
        val minutes = cldr[Calendar.MINUTE]
        timePickerDialog = TimePickerDialog(
            context,
            { _, sHour, sMinute -> run {
                binding.etInputTime.setText("$sHour:$sMinute")
                time = LocalTime.of(sHour,sMinute)
            }
            },
            hour,
            minutes,
            true
        )
        timePickerDialog!!.show()
    }

    fun validate(v: View){

        var report = Report(
            binding.etInputID.text.toString(),
            LocalDateTime.of(date,time),
            binding.switchIsPos.isChecked,
            binding.spinnerOffice.selectedItem.toString()
        )

        coronaReportAppModell.addReportToList(report)
        if(binding.etInputID.text.toString() == ""){
            idValid = false
            binding.etInputID.text.clear()
        }else if (binding.etInputID.text.length < 5){
            idValid = false
            binding.etInputID.text.clear()
        }
        coronaReportAppModell.ReportList.value?.remove(report)

        if(idValid){
            if(coronaReportAppModell.ReportList.value!!.isEmpty()){
                coronaReportAppModell.addReportToList(report)
                view?.findNavController()?.navigate(R.id.action_newReport_to_reportList)
            } else {
                coronaReportAppModell.ReportList.value?.forEach {
                    if(it.id == binding.etInputID.text.toString()){
                        idUnique = false
                        idValid = false

                    }else {
                        idValid = true
                        idUnique = true
                        coronaReportAppModell.addReportToList(report)
                        view?.findNavController()?.navigate(R.id.action_newReport_to_reportList)
                    }
            }



            }
        }

    }
}