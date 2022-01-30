package com.example.coronatrackersamstag2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.database.DatabaseUtils
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController

import com.example.coronatrackersamstag2.Model.CoronaReportAppModell
import com.example.coronatrackersamstag2.Model.Report
import com.example.coronatrackersamstag2.databinding.FragmentNewReportBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewReport.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewReport : Fragment() {
   val coronaReportAppModell : CoronaReportAppModell by activityViewModels()

    var datePickerDialog : DatePickerDialog? = null
    var timePickerDialog : TimePickerDialog? = null

    var date : LocalDate = LocalDate.now()
    var time : LocalTime = LocalTime.now()

    private lateinit var binding : FragmentNewReportBinding
    private var idValid = true;
    private var idUnique = true;

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

        var offices = resources.getStringArray(R.array.offices)
        var arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, offices)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOffice.adapter = arrayAdapter

        binding.etInputDate.inputType = 0
        binding.etInputTime.inputType = 0

        binding.etInputDate.setOnClickListener { view ->
            showDatePickerDialog(view)
        }

        binding.etInputDate.setOnClickListener { view ->
            showTimePickerDialog(view)
        }

        binding.btSave.setOnClickListener { view ->
            validate(view)
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
            binding.etInputId.toString(),
            LocalDateTime.of(date, time),
            binding.switchPositive.isChecked,
            binding.spinnerOffice.selectedItem.toString()
        )

        if(binding.etInputId.text.toString() == ""){
            idValid = false
            binding.etInputId.text.clear()
        }else if(binding.etInputId.text.toString().length < 6){
            idValid = false
            binding.etInputId.text.clear()
        }

        if(idValid){
            if(coronaReportAppModell.reportlist.value!!.isEmpty()){
                coronaReportAppModell.addReport(report);
                view?.findNavController()?.navigate(R.id.action_newReport_to_reportList)
            }
            coronaReportAppModell.reportlist.value?.forEach {
                if(it.id == binding.etInputId.toString()){
                    idUnique = false
                    idValid = false

                }else {
                    idValid = true
                    idUnique = true
                    coronaReportAppModell.addReport(report)

                    view?.findNavController()?.navigate(R.id.action_newReport_to_reportList)
                }
            }
        }
    }

}