package ch.guengel.astro.cli.printer

import ch.guengel.astro.cli.printer.cell.*
import ch.guengel.astro.openngc.ExtendedEntry
import ch.guengel.astro.openngc.ObjectType
import org.fusesource.jansi.Ansi

class ExtendedEntryPrinter : Printer<ExtendedEntry> {
    private val title = listOf(
        TextCell("Objects", 8).apply {
            extraPaddingRight = 1
            attribute = Ansi.Attribute.UNDERLINE
        },
        TextCell("Type", 22).apply {
            extraPaddingRight = 1
            attribute = Ansi.Attribute.UNDERLINE
        },
        TextCell("V-Mag", 5).apply {
            extraPaddingRight = 1
            attribute = Ansi.Attribute.UNDERLINE
        },
        TextCell("Mes", 3).apply {
            extraPaddingRight = 2
            attribute = Ansi.Attribute.UNDERLINE
        },
        TextCell("RA", 11).apply {
            extraPaddingRight = 1
            attribute = Ansi.Attribute.UNDERLINE
        },
        TextCell("Dec", 14).apply {
            extraPaddingRight = 2
            attribute = Ansi.Attribute.UNDERLINE
        },
        TextCell("Alt", 14).apply {
            extraPaddingRight = 1
            attribute = Ansi.Attribute.UNDERLINE
        },
        TextCell("Az", 14).apply { attribute = Ansi.Attribute.UNDERLINE }
    )

    override fun printTitle() {
        title.forEach { it.printAnsi() }
        println()
    }

    private fun objectTypeTextCell(objectType: ObjectType): TextCell =
        TextCell(objectType.description, 22).apply {
            extraPaddingRight = 1
            when (objectType) {
                ObjectType.GALAXY_TRIPLET,
                ObjectType.GALAXY_PAIR,
                ObjectType.GALAXY,
                ObjectType.GALAXY_GROUP,
                -> color = Ansi.Color.YELLOW
                ObjectType.NON_EXISTENT,
                ObjectType.OTHER,
                ObjectType.DUP,
                -> colorIntensity = Intensity.BRIGHT
                ObjectType.DOUBLE_STAR -> color = Ansi.Color.BLUE
                ObjectType.G_CLUSTER -> color = Ansi.Color.MAGENTA
                ObjectType.O_CLUSTER -> color = Ansi.Color.CYAN
                ObjectType.HII,
                ObjectType.DARK_NEBULA,
                ObjectType.EM_NEBULA,
                ObjectType.NEBULA,
                ObjectType.REF_NEBULA,
                ObjectType.P_NEBULA,
                -> {
                    backgroundColor = Ansi.Color.YELLOW
                    backgroundColorIntensity = Intensity.DIM
                }
                else -> {
                    // leave default
                }
            }
        }

    override fun print(item: ExtendedEntry) {
        listOf(
            TextCell(item.entry.name, 8).apply {
                extraPaddingRight = 1
            },
            objectTypeTextCell(item.entry.objectType),
            DecimalCell(item.entry.vMag ?: Double.NaN, 5).apply {
                extraPaddingRight = 1
                alignment = Alignment.RIGHT
                decimalPlaces = 2
                item.entry.vMag ?: run {
                    color = Ansi.Color.WHITE
                    colorIntensity = Intensity.DIM
                }
            },
            TextCell(item.entry.messier?.toString() ?: "", 3).apply {
                extraPaddingRight = 2
                alignment = Alignment.RIGHT
                if (item.entry.messier != null) {
                    color = Ansi.Color.GREEN
                }
            },
            TextCell(item.entry.equatorialCoordinates?.rightAscension.toString(), 11).apply {
                extraPaddingRight = 1
                alignment = Alignment.RIGHT
            },
            TextCell(item.entry.equatorialCoordinates?.declination.toString(), 14).apply {
                extraPaddingRight = 2
                alignment = Alignment.RIGHT
            },
            TextCell(item.horizontalCoordinates.altitude.toString(), 14).apply {
                extraPaddingRight = 1
                alignment = Alignment.RIGHT
                if (item.horizontalCoordinates.altitude.asDecimal() < 0.0) color = Ansi.Color.RED else color =
                    Ansi.Color.GREEN
            },
            TextCell(item.horizontalCoordinates.azimuth.toString(), 14).apply {
                alignment = Alignment.RIGHT
            }
        ).forEach { it.printAnsi() }
        println()
    }
}
