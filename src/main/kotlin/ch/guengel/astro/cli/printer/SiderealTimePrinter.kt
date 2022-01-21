package ch.guengel.astro.cli.printer

import ch.guengel.astro.cli.printer.cell.TextCell
import ch.guengel.astro.cli.printer.cell.print
import ch.guengel.astro.cli.printer.cell.println
import ch.guengel.astro.coordinates.Time

data class SiderealTime(val gst: Time, val lst: Time)

class SiderealTimePrinter : Printer<SiderealTime> {
    private val gstLabel = TextCell("GST", labelSize).apply { extraPaddingRight = 1 }
    private val lstLabel = TextCell("LST", labelSize).apply { extraPaddingRight = 1 }

    override fun printTitle() {
        titleCell.println()
    }

    override fun print(item: SiderealTime) {
        gstLabel.print()
        TextCell(item.gst.toString(), valueSize).println()

        lstLabel.print()
        TextCell(item.lst.toString(), valueSize).println()
    }
}
