package ch.guengel.astro.cli.actions

import ch.guengel.astro.cli.arguments.SunCommand
import ch.guengel.astro.cli.printer.EquatorialCoordinatesPrinter
import ch.guengel.astro.cli.printer.GeographicCoordinatesPrinter
import ch.guengel.astro.cli.printer.HorizontalCoordinatesPrinter
import ch.guengel.astro.cli.printer.OffsetDateTimePrinter
import ch.guengel.astro.cli.printer.SiderealTime
import ch.guengel.astro.cli.printer.SiderealTimePrinter
import ch.guengel.astro.coordinates.Angle
import ch.guengel.astro.coordinates.GeographicCoordinates
import ch.guengel.astro.coordinates.toHorizontalCoordinates
import ch.guengel.astro.sun.SunPosition
import ch.guengel.astro.time.gstToLST
import ch.guengel.astro.time.toGST

fun sunAction(arguments: SunCommand) {
    val observerDateTime = parseTime(arguments.time)
    val observerCoordinates = GeographicCoordinates(Angle.of(arguments.latitude), Angle.of(arguments.longitude))
    val sunPositionEquatorialCoordinates = SunPosition.position(observerDateTime)
    val sunPositionHorizontalCoordinates =
        sunPositionEquatorialCoordinates.toHorizontalCoordinates(observerCoordinates, observerDateTime)

    val geographicCoordinatesPrinter = GeographicCoordinatesPrinter()
    geographicCoordinatesPrinter.printTitle()
    geographicCoordinatesPrinter.print(observerCoordinates)

    val gst = observerDateTime.toGST()
    val lst = gstToLST(gst, observerCoordinates)
    val siderealTimePrinter = SiderealTimePrinter()
    siderealTimePrinter.printTitle()
    siderealTimePrinter.print(SiderealTime(gst, lst))

    val equatorialCoordinatesPrinter = EquatorialCoordinatesPrinter()
    equatorialCoordinatesPrinter.printTitle()
    equatorialCoordinatesPrinter.print(sunPositionEquatorialCoordinates)

    val horizontalCoordinatesPrinter = HorizontalCoordinatesPrinter(negativeAltRed = false)
    horizontalCoordinatesPrinter.printTitle()
    horizontalCoordinatesPrinter.print(sunPositionHorizontalCoordinates)

    val offsetDateTimePrinter = OffsetDateTimePrinter()
    offsetDateTimePrinter.printTitle()
    offsetDateTimePrinter.print(observerDateTime)
}
