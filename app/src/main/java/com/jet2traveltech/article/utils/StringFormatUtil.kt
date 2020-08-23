package com.jet2traveltech.article.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

/**
 * Utility string for matting class(es).
 */
object StringFormatUtil {

    /**
     * Format given long number into Roman string format.
     *
     * @param number long number for be formatted.
     * @return String containing the formatted value.
     */
    fun formatCount(number: Long): String? {
        val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
        val value = floor(log10(number.toDouble())).toInt()
        val base = value / 3
        return if (value >= 3 && base < suffix.size) {
            DecimalFormat("#0.0").format(number / 10.0.pow(base * 3.toDouble())) + suffix[base]
        } else {
            DecimalFormat("#,##0").format(number)
        }
    }

    /**
     * Format T-Time (ISO time) and calculate the elapsed time in formatted way.
     *
     * @param time String denoting the ISO time.
     * @return String denote total time elapsed since the given time in required format.
     */
    fun getElapsedTime(time: String): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        val calendar: Calendar = Calendar.getInstance()

        try {
            calendar.time = sdf.parse(time)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        val diff = System.currentTimeMillis() - calendar.timeInMillis

        val years = diff / (365 * 24 * 60 * 60 * 1000)
        val weeks = diff / (7 * 24 * 60 * 60 * 1000)
        val days = diff / (24 * 60 * 60 * 1000)
        val hrs = diff / (60 * 60 * 1000)
        val min = diff / (60 * 1000)


        return when {
            years != 0L -> "$years year ago"
            weeks != 0L -> "$weeks week ago"
            days != 0L -> "$days day"
            hrs != 0L -> "$hrs hr"
            min != 0L -> "$min min"
            else -> "New"
        }
    }
}