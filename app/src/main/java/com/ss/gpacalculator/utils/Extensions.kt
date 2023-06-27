package com.ss.gpacalculator.utils

fun String.addDot(): String {
    if (this.isBlank()) return this

    val string = if (this.length >= 2 && !this.contains(".")) buildString {
        this.append(this@addDot.first())
        this.append(".")
        this.append(this@addDot.substring(1))
    } else this

    return string.getLittersInRange(0..3)
}

fun String.getLittersInRange(range: IntRange): String {
    return if (this.length > range.last) this.substring(range = range) else this
}