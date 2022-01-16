package ch.guengel.astro.cli.printer

import ch.guengel.astro.cli.printer.cell.TextCell
import ch.guengel.astro.cli.printer.cell.printAnsi
import ch.guengel.astro.cli.printer.cell.printlnAnsi
import ch.guengel.astro.coordinates.HorizontalCoordinates
import org.fusesource.jansi.Ansi

class HorizontalCoordinatesPrinter : Printer<HorizontalCoordinates> {
    private val azLabel = TextCell("Az", labelSize).apply { extraPaddingRight = 1 }

    override fun printTitle() {
        titleCell.printlnAnsi()
    }

    override fun print(item: HorizontalCoordinates) {
        val foregroundColor = if (item.altitude.asDecimal() < 0.0) {
            Ansi.Color.RED
        } else {
            Ansi.Color.DEFAULT
        }

        TextCell("Alt", labelSize).apply {
            color = foregroundColor
            extraPaddingRight = 1
        }.printAnsi()

        TextCell(item.altitude.toString(), valueSize).apply {
            color = foregroundColor
        }.printlnAnsi()

        azLabel.printAnsi()
        TextCell(item.azimuth.toString(), valueSize).printlnAnsi()
    }
}
