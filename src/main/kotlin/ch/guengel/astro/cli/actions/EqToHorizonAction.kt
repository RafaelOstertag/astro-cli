package ch.guengel.astro.cli.actions

import ch.guengel.astro.cli.arguments.EqToHorizonCommand
import ch.guengel.astro.coordinates.Angle
import ch.guengel.astro.coordinates.EquatorialCoordinates
import ch.guengel.astro.coordinates.GeographicCoordinates
import ch.guengel.astro.coordinates.toHorizonCoordinates
import ch.guengel.astro.openngc.parser.DeclinationParser
import ch.guengel.astro.openngc.parser.RightAscensionParser
import ch.guengel.astro.time.gstToLST
import ch.guengel.astro.time.toGST

private const val maxLength = 37

fun eqToHorizonAction(eqToHorizonCommand: EqToHorizonCommand) {
    val longitude = Angle.of(eqToHorizonCommand.longitude)
    val latitude = Angle.of(eqToHorizonCommand.latitude)
    val geographicCoordinates = GeographicCoordinates(latitude, longitude)
    val time = parseTime(eqToHorizonCommand.time)

    val rightAscension = RightAscensionParser.parse(eqToHorizonCommand.rightAscension)
    val declination = DeclinationParser.parse(eqToHorizonCommand.declination)

    val horizontalCoordinates =
        EquatorialCoordinates(rightAscension, declination).toHorizonCoordinates(geographicCoordinates, time)

    if (eqToHorizonCommand.terse) {
        println("Alt,Az: ${horizontalCoordinates.altitude},${horizontalCoordinates.azimuth}")
    } else {
        println("-".repeat(maxLength))
        println("RA:   $rightAscension")
        println("Dec: $declination")
        println("-".repeat(maxLength))
        println("Lon: $longitude")
        println("Lat: $latitude")
        println("T:   $time")
        println("-".repeat(maxLength))
        val gst = time.toGST()
        println("GST: $gst")
        val lst = gstToLST(gst, geographicCoordinates)
        println("LST: $lst")
        println("-".repeat(maxLength))
        println("Alt: ${horizontalCoordinates.altitude}")
        println("Az:  ${horizontalCoordinates.azimuth}")
        println("-".repeat(maxLength))
    }
}
