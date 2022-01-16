package ch.guengel.astro.cli.printer

import ch.guengel.astro.cli.printer.cell.TextCell
import ch.guengel.astro.cli.printer.cell.printAnsi
import ch.guengel.astro.cli.printer.cell.printlnAnsi
import ch.guengel.astro.coordinates.GeographicCoordinates

class GeographicCoordinatesPrinter : Printer<GeographicCoordinates> {
    private val lonLabel = TextCell("Lon", labelSize).apply { extraPaddingRight = 1 }
    private val latLabel = TextCell("Lat", labelSize).apply { extraPaddingRight = 1 }

    override fun printTitle() {
        titleCell.printlnAnsi()
    }

    override fun print(item: GeographicCoordinates) {
        lonLabel.printAnsi()
        TextCell(item.longitude.toString(), valueSize).printlnAnsi()

        latLabel.printAnsi()
        TextCell(item.latitude.toString(), valueSize).printlnAnsi()
    }
}
