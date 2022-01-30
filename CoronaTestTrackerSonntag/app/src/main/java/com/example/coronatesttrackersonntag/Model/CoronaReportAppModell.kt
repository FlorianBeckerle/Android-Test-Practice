package com.example.coronaReporttrackersonntag.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coronatesttrackersonntag.Model.Report

class CoronaReportAppModell : ViewModel() {

    private val _ReportList : MutableLiveData<MutableList<Report>> =
        MutableLiveData<MutableList<Report>>()
    val ReportList: LiveData<MutableList<Report>>
        get() = _ReportList
    
    
    fun addReportToList(Report: Report){
        val value = this._ReportList.value ?: arrayListOf()
        value.add(Report)
        this._ReportList.value = value
    }

}