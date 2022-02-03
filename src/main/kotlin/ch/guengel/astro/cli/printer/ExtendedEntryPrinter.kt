package ch.guengel.astro.cli.printer

import ch.guengel.astro.cli.printer.cell.Alignment
import ch.guengel.astro.cli.printer.cell.DecimalCell
import ch.guengel.astro.cli.printer.cell.Intensity
import ch.guengel.astro.cli.printer.cell.TextCell
import ch.guengel.astro.cli.printer.cell.print
import ch.guengel.astro.openngc.ExtendedNgcEntry
import ch.guengel.astro.openngc.ObjectType
import org.fusesource.jansi.Ansi

class ExtendedEntryPrinter : Printer<ExtendedNgcEntry> {


    private val title = listOf(
        TextCell("Objects", objectCellWidth).apply {
            extraPaddingRight = 1
        },
        TextCell("Type", typeCellWidth).apply {
            extraPaddingRight = 1
        },
        TextCell("V-Mag", vMagCellWidth).apply {
            extraPaddingRight = 1
        },
        TextCell("Mes", mesCellWidth).apply {
            extraPaddingRight = 2
        },
        TextCell("RA", raCellWidth).apply {
            extraPaddingRight = 1
        },
        TextCell("Dec", decCellWidth).apply {
            extraPaddingRight = 2
        },
        TextCell("Alt", altCellWidth).apply {
            extraPaddingRight = 1
        },
        TextCell("Az", azCellWidth).apply {
            extraPaddingRight = 1
        },
        TextCell("Common Names", commonNamesCellWidth)
    ).also { list ->
        list.forEach { it.attribute = Ansi.Attribute.UNDERLINE }
    }

    override fun printTitle() {
        title.forEach { it.print() }
        println()
    }

    private fun objectTypeTextCell(objectType: ObjectType): TextCell =
        TextCell(objectType.description, typeCellWidth).apply {
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

    override fun print(item: ExtendedNgcEntry) {
        listOf(
            TextCell(item.ngcEntry.name, objectCellWidth).apply {
                extraPaddingRight = 1
            },
            objectTypeTextCell(item.ngcEntry.objectType),
            DecimalCell(item.ngcEntry.vMag ?: Double.NaN, vMagCellWidth).apply {
                extraPaddingRight = 1
                alignment = Alignment.RIGHT
                decimalPlaces = 2
                item.ngcEntry.vMag ?: run {
                    color = Ansi.Color.WHITE
                    colorIntensity = Intensity.DIM
                }
            },
            TextCell(item.ngcEntry.messier?.toString() ?: "", mesCellWidth).apply {
                extraPaddingRight = 2
                alignment = Alignment.RIGHT
                if (item.ngcEntry.messier != null) {
                    color = Ansi.Color.GREEN
                }
            },
            TextCell(item.ngcEntry.equatorialCoordinates?.rightAscension.toString(), raCellWidth).apply {
                extraPaddingRight = 1
                alignment = Alignment.RIGHT
            },
            TextCell(item.ngcEntry.equatorialCoordinates?.declination.toString(), decCellWidth).apply {
                extraPaddingRight = 2
                alignment = Alignment.RIGHT
            },
            TextCell(item.horizontalCoordinates.altitude.toString(), altCellWidth).apply {
                extraPaddingRight = 1
                alignment = Alignment.RIGHT
                if (item.horizontalCoordinates.altitude.asDecimal() < 0.0) color = Ansi.Color.RED else color =
                    Ansi.Color.GREEN
            },
            TextCell(item.horizontalCoordinates.azimuth.toString(), azCellWidth).apply {
                alignment = Alignment.RIGHT
                extraPaddingRight = 1
            },
            TextCell(item.ngcEntry.commonNames?.joinToString(", ") ?: "", commonNamesCellWidth)
        ).forEach { it.print() }
        println()
    }

    private companion object {
        private const val objectCellWidth = 13
        private const val typeCellWidth = 22
        private const val vMagCellWidth = 5
        private const val mesCellWidth = 3
        private const val raCellWidth = 11
        private const val decCellWidth = 14
        private const val altCellWidth = 14
        private const val azCellWidth = 14
        private const val commonNamesCellWidth = 100
    }
}
