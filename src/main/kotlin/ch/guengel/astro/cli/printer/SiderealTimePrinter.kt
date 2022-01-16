package ch.guengel.astro.cli.printer

import ch.guengel.astro.cli.printer.cell.TextCell
import ch.guengel.astro.cli.printer.cell.printAnsi
import ch.guengel.astro.cli.printer.cell.printlnAnsi
import ch.guengel.astro.coordinates.Time

data class SiderealTime(val gst: Time, val lst: Time)

class SiderealTimePrinter : Printer<SiderealTime> {
    private val gstLabel = TextCell("GST", labelSize).apply { extraPaddingRight = 1 }
    private val lstLabel = TextCell("LST", labelSize).apply { extraPaddingRight = 1 }

    override fun printTitle() {
        titleCell.printlnAnsi()
    }

    override fun print(item: SiderealTime) {
        gstLabel.printAnsi()
        TextCell(item.gst.toString(), valueSize).printlnAnsi()

        lstLabel.printAnsi()
        TextCell(item.lst.toString(), valueSize).printlnAnsi()
    }
}
