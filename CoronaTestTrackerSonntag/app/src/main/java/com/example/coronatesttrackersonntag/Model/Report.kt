package com.example.coronatesttrackersonntag.Model

import java.time.LocalDateTime

data class Report (
    var id : String,
    var dateAndTime : LocalDateTime,
    var isPositive : Boolean,
    var office : String
        ) {
}