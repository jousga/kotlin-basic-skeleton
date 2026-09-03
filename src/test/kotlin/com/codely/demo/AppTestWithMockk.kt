package com.codely.demo

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import java.time.LocalDate

/**
 * Aquí en lugar de testear la clase App, lo que hacemos es testear la clase AppTest, que hereda de App y sobreescribe el método currentDate para que devuelva una fecha fija, de esta manera podemos testear el código sin depender de la fecha actual.
 */
class AppTestWithmockkk {
    @Test
    fun `should calculate difference and return 31 years`() {
        val reader = mockk<Reader>()
        //Este relaxed, nos evita tener que añadir la línea de código indicando que al llamar al método write devuelve algo o no hace nada
        val writer = mockk<Writer>(relaxed = true)
        val clock = mockk<Clock>()
        val app = App(reader, writer, clock)

        every { reader.read() } returns "1990-08-31"
        every { clock.now() } returns LocalDate.parse("2021-08-31")

        app.execute()

        verify { writer.write("The difference between the date you wrote an today is 31 years") }
    }

    @Test
    fun `should calculate difference and return 11 months`() {
        val reader = mockk<Reader>()
        //Este relaxed, nos evita tener que añadir la línea de código indicando que al llamar al método write devuelve algo o no hace nada
        val writer = mockk<Writer>(relaxed = true)
        val clock = mockk<Clock>()
        val app = App(reader, writer, clock)

        every { reader.read() } returns "2020-09-01"
        every { clock.now() } returns LocalDate.of(2021, 8, 31)

        app.execute()

        verify { writer.write("The difference between the date you wrote an today is 11 months") }
    }

    @Test
    fun `should calculate difference and return 2 days`() {
        val reader = mockk<Reader>()
        //Este relaxed, nos evita tener que añadir la línea de código indicando que al llamar al método write devuelve algo o no hace nada
        val writer = mockk<Writer>(relaxed = true)
        val clock = mockk<Clock>()
        val app = App(reader, writer, clock)

        every { reader.read() } returns "2021-08-29"
        every { clock.now() } returns LocalDate.of(2021, 8, 31)

        app.execute()

        verify { writer.write("The difference between the date you wrote an today is 2 days") }
    }

    @Test
    fun `fail when the introduced date is empty`() {
        val reader = mockk<Reader>()
        //Este relaxed, nos evita tener que añadir la línea de código indicando que al llamar al método write devuelve algo o no hace nada
        val writer = mockk<Writer>(relaxed = true)
        val clock = mockk<Clock>()
        val app = App(reader, writer, clock)

        every { reader.read() } returns ""
        every { clock.now() } returns LocalDate.of(2021, 8, 31)

        app.execute()

        verify { writer.write("The introduced date <> is not valid") }
    }

    @Test
    fun `fail when the introduced date is blank`() {
        val reader = mockk<Reader>()
        //Este relaxed, nos evita tener que añadir la línea de código indicando que al llamar al método write devuelve algo o no hace nada
        val writer = mockk<Writer>(relaxed = true)
        val clock = mockk<Clock>()
        val app = App(reader, writer, clock)

        every { reader.read() } returns " "
        every { clock.now() } returns LocalDate.of(2021, 8, 31)

        app.execute()

        verify { writer.write("The introduced date < > is not valid") }
    }
}