package ch.guengel.astro.cli.printer

import ch.guengel.astro.coordinates.EquatorialCoordinates

class EquatorialCoordinatesPrinter : Printer<EquatorialCoordinates> {
    override fun printTitle() {
        println("-".repeat(maxLength))
    }

    override fun print(item: EquatorialCoordinates) {
        println("RA:    ${item.rightAscension}")
        println("Dec: ${item.declination}")
    }
}
