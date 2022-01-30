package com.example.coronatrackersamstag2.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class CoronaReportAppModell : ViewModel(){

    var _reportlist : MutableLiveData<MutableList<Report>> =
        MutableLiveData(LinkedList<Report>())

    private var testString : String = ""


    //TODO Funktionen
    val reportlist : LiveData<MutableList<Report>>
        get() = _reportlist

    public fun addReport(report : Report) {
        _reportlist.value?.add(report)
    }

    public fun getReport(place: String){
        testString = reportlist.value.toString()
    }

    public fun removeReport(report: Report){
        _reportlist.value?.forEach {
            if(it.id == report.id){
                _reportlist.value?.remove(it)
            }
        }
    }
}