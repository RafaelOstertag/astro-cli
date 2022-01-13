package ch.guengel.astro.cli.actions

import ch.guengel.astro.coordinates.Angle
import ch.guengel.astro.openngc.ParserError
import java.time.OffsetDateTime


private val declinationRegex = Regex("(?<degrees>[+-]?[0-9]{1,3}):(?<minutes>[0-9]{1,2}):(?<seconds>[0-9.]+)")

fun parseTime(str: String): OffsetDateTime {
    if (str == "now") {
        return OffsetDateTime.now()
    }

    return OffsetDateTime.parse(str)
}

fun parseDeclination(str: String): Angle {
    val result =
        declinationRegex.matchEntire(str) ?: throw ParserError("Cannot extract declination from '$str'")
    val matchGroups = result.groups

    val degrees = matchGroups["degrees"]!!.value.toInt()
    val minutes = matchGroups["minutes"]!!.value.toInt()
    val seconds = matchGroups["seconds"]!!.value.toDouble()
    return Angle(degrees, minutes, seconds)
}
