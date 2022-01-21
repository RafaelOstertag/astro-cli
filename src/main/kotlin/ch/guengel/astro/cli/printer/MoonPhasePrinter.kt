package ch.guengel.astro.cli.printer

import ch.guengel.astro.cli.printer.cell.*
import org.fusesource.jansi.Ansi

class MoonPhasePrinter : Printer<Double> {
    override fun printTitle() {
        titleCell.println()
    }

    override fun print(item: Double) {
        val foregroundColor = if (item >= 0.5) {
            Ansi.Color.RED
        } else if (item >= 0.25) {
            Ansi.Color.YELLOW
        } else {
            Ansi.Color.GREEN
        }

        TextCell("Phase", labelSize).apply {
            extraPaddingRight = 1
            color = foregroundColor
        }.print()

        DecimalCell(item, valueSize).apply {
            alignment = Alignment.LEFT
            decimalPlaces = 2
            color = foregroundColor
        }.println()
    }
}
