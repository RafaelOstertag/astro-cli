package ch.guengel.astro.cli.printer

import ch.guengel.astro.coordinates.Time

data class SiderealTime(val gst: Time, val lst: Time)

class SiderealTimePrinter : Printer<SiderealTime> {
    override fun printTitle() {
        println("-".repeat(maxLength))
    }

    override fun print(item: SiderealTime) {
        println("GST: ${item.gst}")
        println("LST: ${item.lst}")
    }
}
