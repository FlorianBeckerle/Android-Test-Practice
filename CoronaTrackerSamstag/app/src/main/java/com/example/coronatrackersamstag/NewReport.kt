package com.example.coronatrackersamstag

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.coronatrackersamstag.Model.CoronaReportAppModel
import com.example.coronatrackersamstag.Model.Report
import com.example.coronatrackersamstag.databinding.FragmentNewReportBinding
import java.time.LocalDate
import java.time.LocalTime
import java.time.LocalDateTime
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

    val coronaReportAppModel : CoronaReportAppModel by activityViewModels()

    var datePickerDialog : DatePickerDialog? = null

    var timePickerDialog : TimePickerDialog? = null

    var date: LocalDate = LocalDate.now()
    var time: LocalTime = LocalTime.now()

    private lateinit var binding: FragmentNewReportBinding
    private var idValid: Boolean = true;
    private var idUnique: Boolean = true;
    var testString: String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_new_report, container, false
        )

        binding.etDatePicker.inputType = 0
        binding.etTimePicker.inputType = 0

        val offices = resources.getStringArray(R.array.offices)
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, offices)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOffice.adapter = arrayAdapter

        binding.etDatePicker.setOnClickListener { view ->
            showDatePickerDialog(view)
        }

        binding.etTimePicker.setOnClickListener { view ->
            showTimePickerDialog(view)
        }

        binding.btSave.setOnClickListener { view ->
            validate(view)
        }

        return binding.root;

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
                            binding.etDatePicker.setText(
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
                binding.etTimePicker.setText("$sHour:$sMinute")
                time = LocalTime.of(sHour,sMinute)
            }
            },
            hour,
            minutes,
            true
        )
        timePickerDialog!!.show()
    }

    private fun validate(v: View){
        val report : Report = Report(
            binding.etInputId.text.toString(),
            LocalDateTime.of(date,time),
            binding.switchInputPositiv.isChecked,
            binding.spinnerOffice.selectedItem.toString()
        )

        coronaReportAppModel.addReport(report)
        if(binding.etInputId.text.toString() == "") {
            idValid = false
            coronaReportAppModel.removeReport(report)
            binding.etInputId.text.clear()
        }else if(binding.etInputId.text.toString().length < 6){
            binding.etInputId.text.clear()
            idValid = false
            coronaReportAppModel.removeReport(report)
        }
        coronaReportAppModel.removeReport(report)

        if(idValid) {
            if(coronaReportAppModel.reportList.value!!.isEmpty()) {
                coronaReportAppModel.addReport(report)
                //TODO Navigate to ReportList
                view?.findNavController()?.navigate(R.id.action_newReport_to_reportList)

            }else {
                coronaReportAppModel.reportList.value?.forEach() {
                    if(binding.etInputId.text.toString() == it.id) {
                        binding.etInputId.text.clear()
                        idValid = false
                        idUnique = false
                        coronaReportAppModel.removeReport(it)
                    } else {
                        idValid = true
                        idUnique = true
                        coronaReportAppModel.addReport(report)
                        //TODO Navigate to ReportList
                        view?.findNavController()?.navigate(R.id.action_newReport_to_reportList)
                    }
                }
            }
        }
    }
}