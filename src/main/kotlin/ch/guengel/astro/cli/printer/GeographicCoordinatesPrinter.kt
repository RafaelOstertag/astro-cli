package ch.guengel.astro.cli.printer

import ch.guengel.astro.cli.printer.cell.TextCell
import ch.guengel.astro.cli.printer.cell.print
import ch.guengel.astro.cli.printer.cell.println
import ch.guengel.astro.coordinates.GeographicCoordinates

class GeographicCoordinatesPrinter : Printer<GeographicCoordinates> {
    private val lonLabel = TextCell("Lon", labelSize).apply { extraPaddingRight = 1 }
    private val latLabel = TextCell("Lat", labelSize).apply { extraPaddingRight = 1 }

    override fun printTitle() {
        titleCell.println()
    }

    override fun print(item: GeographicCoordinates) {
        lonLabel.print()
        TextCell(item.longitude.toString(), valueSize).println()

        latLabel.print()
        TextCell(item.latitude.toString(), valueSize).println()
    }
}
