package ch.guengel.astro.cli.actions

import ch.guengel.astro.cli.arguments.SunCommand
import ch.guengel.astro.cli.printer.*
import ch.guengel.astro.coordinates.Angle
import ch.guengel.astro.coordinates.GeographicCoordinates
import ch.guengel.astro.coordinates.toHorizonCoordinates
import ch.guengel.astro.sun.sunPosition
import ch.guengel.astro.time.gstToLST
import ch.guengel.astro.time.toGST

fun sunAction(arguments: SunCommand) {
    val observerDateTime = parseTime(arguments.time)
    val observerCoordinates = GeographicCoordinates(Angle.of(arguments.latitude), Angle.of(arguments.longitude))
    val sunPositionEquatorialCoordinates = sunPosition(observerDateTime)
    val sunPositionHorizontalCoordinates =
        sunPositionEquatorialCoordinates.toHorizonCoordinates(observerCoordinates, observerDateTime)

    val geographicCoordinatesPrinter = GeographicCoordinatesPrinter()
    geographicCoordinatesPrinter.printTitle()
    geographicCoordinatesPrinter.print(GeographicCoordinatesWithTime(observerCoordinates, observerDateTime))

    val gst = observerDateTime.toGST()
    val lst = gstToLST(gst, observerCoordinates)
    val siderealTimePrinter = SiderealTimePrinter()
    siderealTimePrinter.printTitle()
    siderealTimePrinter.print(SiderealTime(gst, lst))

    val equatorialCoordinatesPrinter = EquatorialCoordinatesPrinter()
    equatorialCoordinatesPrinter.printTitle()
    equatorialCoordinatesPrinter.print(sunPositionEquatorialCoordinates)

    val horizontalCoordinatesPrinter = HorizontalCoordinatesPrinter()
    horizontalCoordinatesPrinter.printTitle()
    horizontalCoordinatesPrinter.print(HorizontalCoordinatesWithTime(sunPositionHorizontalCoordinates,
        observerDateTime))
}
