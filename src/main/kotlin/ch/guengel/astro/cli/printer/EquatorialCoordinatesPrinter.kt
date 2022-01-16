package ch.guengel.astro.cli.printer

import ch.guengel.astro.cli.printer.cell.TextCell
import ch.guengel.astro.cli.printer.cell.printAnsi
import ch.guengel.astro.cli.printer.cell.printlnAnsi
import ch.guengel.astro.coordinates.EquatorialCoordinates

class EquatorialCoordinatesPrinter : Printer<EquatorialCoordinates> {
    private val raLabel = TextCell("RA", labelSize).apply { extraPaddingRight = 1 }
    private val decLabel = TextCell("Dec", labelSize).apply { extraPaddingRight = 1 }

    override fun printTitle() {
        titleCell.printlnAnsi()
    }

    override fun print(item: EquatorialCoordinates) {
        raLabel.printAnsi()
        TextCell(item.rightAscension.toString(), valueSize).printlnAnsi()

        decLabel.printAnsi()
        TextCell(item.declination.toString(), valueSize).printlnAnsi()
    }
}
