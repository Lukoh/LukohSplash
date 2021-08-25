package com.goforer.base.utility

import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    private const val YMD = "yyyy-MM-dd"
    private const val EDMY = "EEEE, dd MMM yyyy"

    fun convertDateFormat(
        inputDate: String
    ): String {
        var outputDate = ""

        val input = SimpleDateFormat(YMD, Locale.US)
        val output = SimpleDateFormat(EDMY, Locale.US)

        try {
            val parsed = input.parse(inputDate)

            parsed?.let {
                outputDate = output.format(parsed)
            }
        } catch (e: ParseException) {
            Timber.d(e.stackTrace.toString())
        }

        return outputDate
    }
}
