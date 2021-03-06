package ch.guengel.astro.cli.printer

import ch.guengel.astro.cli.printer.cell.TextCell
import ch.guengel.astro.cli.printer.cell.print
import ch.guengel.astro.cli.printer.cell.println
import ch.guengel.astro.coordinates.HorizontalCoordinates
import org.fusesource.jansi.Ansi

class HorizontalCoordinatesPrinter(val negativeAltRed: Boolean = true) : Printer<HorizontalCoordinates> {
    private val azLabel = TextCell("Az", labelSize).apply { extraPaddingRight = 1 }

    override fun printTitle() {
        titleCell.println()
    }

    override fun print(item: HorizontalCoordinates) {
        val foregroundColor = if (item.altitude.asDecimal() < 0.0) {
            if (negativeAltRed) Ansi.Color.RED else Ansi.Color.GREEN
        } else {
            if (negativeAltRed) Ansi.Color.GREEN else Ansi.Color.RED
        }

        TextCell("Alt", labelSize).apply {
            color = foregroundColor
            extraPaddingRight = 1
        }.print()

        TextCell(item.altitude.toString(), valueSize).apply {
            color = foregroundColor
        }.println()

        azLabel.print()
        TextCell(item.azimuth.toString(), valueSize).println()
    }
}
