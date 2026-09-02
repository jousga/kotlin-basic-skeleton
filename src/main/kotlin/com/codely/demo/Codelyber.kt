package com.codely.demo

import java.time.LocalDate

fun main() {
    println("Introduce a date 'yyyy-MM-dd'")
    val readline = readLine()
    val input = LocalDate.parse(readline)
    println("You introduced: $input")
}
