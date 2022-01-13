package ch.guengel.astro.cli.printer

import ch.guengel.astro.coordinates.HorizontalCoordinates
import java.time.OffsetDateTime

data class HorizontalCoordinatesWithTime(
    val horizontalCoordinates: HorizontalCoordinates,
    val observerDateTime: OffsetDateTime,
)

class HorizontalCoordinatesPrinter : Printer<HorizontalCoordinatesWithTime> {
    override fun printTitle() {
        println("-".repeat(maxLength))
    }

    override fun print(item: HorizontalCoordinatesWithTime) {
        println("Alt: ${item.horizontalCoordinates.altitude}")
        println("Az:  ${item.horizontalCoordinates.azimuth}")
        println("T:   ${item.observerDateTime}")
    }
}
