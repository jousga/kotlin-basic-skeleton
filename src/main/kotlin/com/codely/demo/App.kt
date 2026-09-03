package com.codely.demo

import java.time.LocalDate
import java.time.Period
import kotlin.system.exitProcess

/**
 * Al pedir la introducción de la fecha lo hacemos por la linea de commandos, se han creado estos tres métodos para facilitar el testeo del cóidigo, ya que de esta manera podemos simular la entrada y salida de datos sin necesidad de usar la consola.
 */
open class Reader {
    open fun read() = readlnOrNull()
}

open class Writer {
    open fun write(message: String) = println(message)
}

open class Clock {
    open fun now(): LocalDate = LocalDate.now()
}

/**
 *  Por defecto en Kotlin las clases son FINAL, al usar open, hacemos que esta pueda ser extendida.
 */
open class App(private val reader: Reader, private val writer: Writer, private val clock: Clock) {
    fun execute() {
        writer.write("Please enter a date with the format <yyyy-MM-dd>")
        val line = reader.read()
        line.takeUnless {
            !it.isNullOrEmpty() && !it.isNullOrBlank()
        }?.let {
            writer.write("The introduced date <$it> is not valid")
            return
        }
        line.takeUnless {
            it.isNullOrEmpty()
        }?.let {
            LocalDate.parse(it)
        }.apply {
            if (this == null) {
                writer.write("The introduced date <$this> is not valid")
                exitProcess(1)
            }
        }.also {
            writer.write("You wrote the date $it")
        }?.run {
            calculateDifferenceUntilToday()
        }

        writer.write("Bye!")
    }

    protected open fun currentDate(): LocalDate = clock.now()

    private fun LocalDate.calculateDifferenceUntilToday() = with(Period.between(this, clock.now())) {
        when {
            years > 0 -> writer.write("The difference between the date you wrote an today is $years years")
            months > 0 -> writer.write("The difference between the date you wrote an today is $months months")
            days > 0 -> writer.write("The difference between the date you wrote an today is $days days")
        }
    }
}