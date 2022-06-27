package com.abdoul.videoassessment.common

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateConversion {

    fun String.toLongDate(): Long? {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        return df.parse(this)?.time
    }

    private fun Long.customFormat(): String {
        val df = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = Date(this)
        return df.format(date)
    }

    private fun Long.formattedDate(): String {
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date(this)
        return df.format(date)
    }

    fun Long.toFormatDate(): String {
        val chosenDay = LocalDate.parse(this.formattedDate()).dayOfWeek
        val tomorrow = LocalDate.now().plusDays(1).dayOfWeek
        val yesterday = LocalDate.now().minusDays(1).dayOfWeek
        val today = LocalDate.now().dayOfWeek
        val day = when {
            tomorrow == chosenDay -> "Tomorrow, ${getTime(this)}"
            yesterday == chosenDay -> "Yesterday, ${getTime(this)}"
            today == chosenDay -> "Today, ${getTime(this)}"
            else -> this.customFormat()
        }
        return day
    }

    fun Long.isTomorrow(): Boolean {
        val tomorrow = LocalDate.now().plusDays(1)
        val chosenDay = LocalDate.ofEpochDay(this / TimeUnit.DAYS.toMillis(1))
        return tomorrow == chosenDay
    }

    private fun getTime(startTime: Long) =
        DateTimeFormatter.ofPattern("HH:mm").format(
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(startTime),
                ZoneId.systemDefault()
            )
        )
}