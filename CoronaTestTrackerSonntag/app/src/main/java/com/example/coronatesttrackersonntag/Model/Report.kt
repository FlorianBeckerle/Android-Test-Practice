package com.example.coronatesttrackersonntag.Model

import java.time.LocalDateTime

data class Report (
    var id : String,
    var dateAndTime : LocalDateTime,
    var isPositive : Boolean,
    var office : String
        ) {

    override fun toString(): String {
        return "ID:'${id.substring(0,2)}'...'${id.substring(id.length-4, id.length)}', dateAndTime=$dateAndTime, isPositive=$isPositive, office='$office')"
    }
}