package ch.guengel.astro.cli.printer.cell

interface Cell {
    fun getFormattedText(): String
    fun getAnsiText(): String
}
