package ch.guengel.astro.cli.printer

interface Printer<T> {
    fun printTitle()
    fun print(item: T)
}
