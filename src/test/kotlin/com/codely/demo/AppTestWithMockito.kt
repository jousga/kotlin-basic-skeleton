package com.codely.demo

import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.time.LocalDate

/**
 * Antes teníamos la clase AppTest que extendia a la clase App, para poder personalizar la fecha que devuelve el método currentDate()
 * y así podíamos usarla en los test.
 *
 * Ahora esto no es necesario ya que hemos creado la clase Clock que es la que nos devolverá la fecha actual y lo que haremos en los test es
 * moquear lo que devuelve para forzar a que devuelva lo que nosotros queremos y así poder probar fechas distintas.
 */
class AppTestWithMockito {
    @Test
    fun `should calculate difference and return 31 years`() {
        val reader = mock<Reader>()
        val writer = mock<Writer>()
        val clock = mock<Clock>()
        val app = App(reader, writer, clock)

        whenever(clock.now()).thenReturn(LocalDate.of(2021, 8, 31))
        whenever(reader.read()).thenReturn("1990-08-31")
        doNothing().`when`(writer).write(any())

        app.execute()

        verify(writer).write("The difference between the date you wrote an today is 31 years")
    }

    @Test
    fun `should calculate difference and return 11 months`() {
        val reader = mock<Reader>()
        val writer = mock<Writer>()
        val clock = mock<Clock>()
        val app = App(reader, writer, clock)

        whenever(clock.now()).thenReturn(LocalDate.of(2021, 8, 31))
        whenever(reader.read()).thenReturn("2020-09-01")
        doNothing().`when`(writer).write(any())

        app.execute()

        verify(writer).write("The difference between the date you wrote an today is 11 months")
    }

    @Test
    fun `should calculate difference and return 2 days`() {
        val reader = mock<Reader>()
        val writer = mock<Writer>()
        val clock = mock<Clock>()
        val app = App(reader, writer, clock)

        whenever(clock.now()).thenReturn(LocalDate.of(2021, 8, 31))
        whenever(reader.read()).thenReturn("2021-08-29")
        doNothing().`when`(writer).write(any())

        app.execute()

        verify(writer).write("The difference between the date you wrote an today is 2 days")
    }

    @Test
    fun `fail when the introduced date is empty`() {
        val reader = mock<Reader>()
        val writer = mock<Writer>()
        val clock = mock<Clock>()
        val app = App(reader, writer, clock)

        whenever(clock.now()).thenReturn(LocalDate.of(2021, 8, 31))
        whenever(reader.read()).thenReturn("")
        doNothing().`when`(writer).write(any())

        app.execute()

        verify(writer).write("The introduced date <> is not valid")
    }

    @Test
    fun `fail when the introduced date is blank`() {
        val reader = mock<Reader>()
        val writer = mock<Writer>()
        val clock = mock<Clock>()
        val app = App(reader, writer, clock)

        whenever(clock.now()).thenReturn(LocalDate.of(2021, 8, 31))
        whenever(reader.read()).thenReturn(" ")
        doNothing().`when`(writer).write(any())

        app.execute()

        verify(writer).write("The introduced date < > is not valid")
    }
}