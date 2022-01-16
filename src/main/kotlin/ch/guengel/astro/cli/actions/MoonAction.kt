package ch.guengel.astro.cli.actions

import ch.guengel.astro.cli.arguments.MoonCommand
import ch.guengel.astro.cli.printer.*
import ch.guengel.astro.coordinates.Angle
import ch.guengel.astro.coordinates.GeographicCoordinates
import ch.guengel.astro.coordinates.toHorizonCoordinates
import ch.guengel.astro.moon.MoonPhase
import ch.guengel.astro.moon.MoonPosition
import ch.guengel.astro.time.gstToLST
import ch.guengel.astro.time.toGST

fun moonAction(arguments: MoonCommand) {
    val observerDateTime = parseTime(arguments.time)
    val observerCoordinates = GeographicCoordinates(Angle.of(arguments.latitude), Angle.of(arguments.longitude))
    val moonPositionEquatorialCoordinates = MoonPosition.position(observerDateTime)
    val moonPositionHorizontalCoordinates =
        moonPositionEquatorialCoordinates.toHorizonCoordinates(observerCoordinates, observerDateTime)

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
    equatorialCoordinatesPrinter.print(moonPositionEquatorialCoordinates)

    val horizontalCoordinatesPrinter = HorizontalCoordinatesPrinter()
    horizontalCoordinatesPrinter.printTitle()
    horizontalCoordinatesPrinter.print(moonPositionHorizontalCoordinates)

    val offsetDateTimePrinter = OffsetDateTimePrinter()
    offsetDateTimePrinter.printTitle()
    offsetDateTimePrinter.print(observerDateTime)

    val moonPhasePrinter = MoonPhasePrinter()
    moonPhasePrinter.printTitle()
    moonPhasePrinter.print(MoonPhase.percentage(observerDateTime))
}
