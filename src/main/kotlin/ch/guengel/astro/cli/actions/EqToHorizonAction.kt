package ch.guengel.astro.cli.actions

import ch.guengel.astro.cli.arguments.EqToHorizonCommand
import ch.guengel.astro.cli.printer.EquatorialCoordinatesPrinter
import ch.guengel.astro.cli.printer.GeographicCoordinatesPrinter
import ch.guengel.astro.cli.printer.HorizontalCoordinatesPrinter
import ch.guengel.astro.cli.printer.SiderealTime
import ch.guengel.astro.cli.printer.SiderealTimePrinter
import ch.guengel.astro.coordinates.Angle
import ch.guengel.astro.coordinates.EquatorialCoordinates
import ch.guengel.astro.coordinates.GeographicCoordinates
import ch.guengel.astro.coordinates.toHorizontalCoordinates
import ch.guengel.astro.openngc.parser.RightAscensionParser
import ch.guengel.astro.time.gstToLST
import ch.guengel.astro.time.toGST

private const val maxLength = 37

fun eqToHorizonAction(eqToHorizonCommand: EqToHorizonCommand) {
    val longitude = Angle.of(eqToHorizonCommand.longitude)
    val latitude = Angle.of(eqToHorizonCommand.latitude)
    val geographicCoordinates = GeographicCoordinates(latitude, longitude)
    val observerDateTime = parseTime(eqToHorizonCommand.time)

    val rightAscension = RightAscensionParser.parse(eqToHorizonCommand.rightAscension)
    val declination = parseDeclination(eqToHorizonCommand.declination)
    val equatorialCoordinates = EquatorialCoordinates(rightAscension, declination)

    val horizontalCoordinates =
        EquatorialCoordinates(rightAscension, declination).toHorizontalCoordinates(geographicCoordinates,
            observerDateTime)

    if (eqToHorizonCommand.terse) {
        println("Alt,Az: ${horizontalCoordinates.altitude},${horizontalCoordinates.azimuth}")
    } else {
        val equatorialCoordinatesPrinter = EquatorialCoordinatesPrinter()
        equatorialCoordinatesPrinter.printTitle()
        equatorialCoordinatesPrinter.print(equatorialCoordinates)

        val geographicCoordinatesPrinter = GeographicCoordinatesPrinter()
        geographicCoordinatesPrinter.printTitle()
        geographicCoordinatesPrinter.print(geographicCoordinates)

        val siderealTimePrinter = SiderealTimePrinter()
        siderealTimePrinter.printTitle()
        val gst = observerDateTime.toGST()
        siderealTimePrinter.print(SiderealTime(gst, gstToLST(gst, geographicCoordinates)))

        val horizontalCoordinatesPrinter = HorizontalCoordinatesPrinter()
        horizontalCoordinatesPrinter.printTitle()
        horizontalCoordinatesPrinter.print(horizontalCoordinates)
    }
}
