package ch.guengel.astro.cli.printer

import ch.guengel.astro.coordinates.GeographicCoordinates
import java.time.OffsetDateTime

data class GeographicCoordinatesWithTime(
    val geographicCoordinates: GeographicCoordinates,
    val observerDateTime: OffsetDateTime,
)

class GeographicCoordinatesPrinter : Printer<GeographicCoordinatesWithTime> {
    override fun printTitle() {
        println("-".repeat(maxLength))
    }

    override fun print(item: GeographicCoordinatesWithTime) {
        println("Lon: ${item.geographicCoordinates.longitude}")
        println("Lat: ${item.geographicCoordinates.latitude}")
        println("T:   ${item.observerDateTime}")
    }
}
