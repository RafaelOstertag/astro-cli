package ch.guengel.astro.cli.printer

import ch.guengel.astro.cli.printer.cell.TextCell
import ch.guengel.astro.cli.printer.cell.print
import ch.guengel.astro.cli.printer.cell.println
import ch.guengel.astro.coordinates.EquatorialCoordinates

class EquatorialCoordinatesPrinter : Printer<EquatorialCoordinates> {
    private val raLabel = TextCell("RA", labelSize).apply { extraPaddingRight = 1 }
    private val decLabel = TextCell("Dec", labelSize).apply { extraPaddingRight = 1 }

    override fun printTitle() {
        titleCell.println()
    }

    override fun print(item: EquatorialCoordinates) {
        raLabel.print()
        TextCell(item.rightAscension.toString(), valueSize).println()

        decLabel.print()
        TextCell(item.declination.toString(), valueSize).println()
    }
}
