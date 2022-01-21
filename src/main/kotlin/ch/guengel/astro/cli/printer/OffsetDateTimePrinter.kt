package ch.guengel.astro.cli.printer

import ch.guengel.astro.cli.printer.cell.TextCell
import ch.guengel.astro.cli.printer.cell.print
import ch.guengel.astro.cli.printer.cell.println
import ch.guengel.astro.time.toUT
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX")

class OffsetDateTimePrinter : Printer<OffsetDateTime> {
    private val utLabel = TextCell("UT", labelSize).apply { extraPaddingRight = 1 }
    private val lctLabel = TextCell("LCT", labelSize).apply { extraPaddingRight = 1 }

    override fun printTitle() {
        titleCell.println()
    }

    override fun print(item: OffsetDateTime) {
        utLabel.print()
        TextCell(
            item.toUT().format(dateTimeFormatter).toString(), valueSize
        ).println()


        lctLabel.print()
        TextCell(item.format(dateTimeFormatter).toString(), valueSize).println()
    }
}
