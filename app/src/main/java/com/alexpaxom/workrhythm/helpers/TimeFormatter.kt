package com.alexpaxom.workrhythm.helpers

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object TimeFormatter {
    private const val convertMillisecondsToDays = 86400000
    fun intervalFromTime(elapsedTime: Long): String {
        val df: DateFormat = SimpleDateFormat(" HH:mm:ss")
        df.timeZone = TimeZone.getTimeZone("GMT+0")
        val elapsed_days = (elapsedTime / convertMillisecondsToDays).toInt()
        return ("Days: " + elapsed_days.toString()
                + " " +
                "Hours: " + df.format(elapsedTime % convertMillisecondsToDays))
    }

    fun intervalGetDays(elapsedTime: Long): String {
        val elapsed_days = (elapsedTime / convertMillisecondsToDays).toInt()
        return elapsed_days.toString()
    }

    fun intervalGetHours(elapsedTime: Long): String {
        val df: DateFormat = SimpleDateFormat(" HH:mm:ss")
        df.timeZone = TimeZone.getTimeZone("GMT+0")
        return df.format(elapsedTime % convertMillisecondsToDays)
    }
}