package com.hold.rich.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun unixTimeToString(millsStr: String): String {
    try {
        val mills = millsStr.toLong()
        if (mills < 0) return "error"
        val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        return format.format(Date(mills))
    } catch (e: NumberFormatException) {
        return "error"
    }
}

fun String.readable(): String {
    return try {
        val f = this.toFloat()
        val format = DecimalFormat("0.####")
        format.roundingMode = RoundingMode.FLOOR
        format.format(f)
    } catch (e: NumberFormatException) {
        ""
    }
}

fun joinParams(vararg params: String): String {
    var result = ""
    for (param in params) {
        result += "$param,"
    }
    if (result.length > 1) result = result.dropLast(1)
    return result
}

fun String.toSpotInst(): String {
    return "$this-USDT"
}

fun String.toMarginInst(): String {
    return "$this-USDT"
}

fun String.toSwapInst(): String {
    return "$this-USDT-SWAP"
}