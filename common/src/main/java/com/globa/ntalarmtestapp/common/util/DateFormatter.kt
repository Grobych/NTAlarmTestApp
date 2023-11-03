package com.globa.ntalarmtestapp.common.util

import java.text.SimpleDateFormat
import java.util.Locale

object DateFormatter {
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
    private val extendDateFormat = SimpleDateFormat("dd.MM.yyyy, HH:mm:ss", Locale.ENGLISH)

    fun getSimpleDate(date: Long): String = dateFormat.format(date * 1000L)
    fun getExtendDate(date: Long): String = extendDateFormat.format(date * 1000L)
}