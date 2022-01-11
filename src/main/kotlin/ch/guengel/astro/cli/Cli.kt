package ch.guengel.astro.cli

import ch.guengel.astro.cli.configuration.AppDirectories
import ch.guengel.astro.cli.configuration.XdgDirectories
import ch.guengel.astro.cli.printer.ExtendedEntryPrinterImpl
import ch.guengel.astro.coordinates.Angle
import ch.guengel.astro.coordinates.GeographicCoordinates
import ch.guengel.astro.openngc.Catalog
import ch.guengel.astro.openngc.ExtendedEntry
import ch.guengel.astro.openngc.parser.CSVParser
import java.time.OffsetDateTime

fun main(args: Array<String>) {
    val arguments = Arguments()
    arguments.parse(args)

    XdgDirectories.createDirectories()
    AppDirectories.createDirectories()

    if (arguments.update) {
        OpenNGCFile.update()
    } else {
        OpenNGCFile.updateWhenNotExists()
    }

    val catalog = CSVParser.parse(OpenNGCFile.catalog)
    listOperation(arguments, catalog)
}


private fun listOperation(arguments: Arguments, catalog: Catalog) {
    val extendedEntryPrinter = ExtendedEntryPrinterImpl()
    extendedEntryPrinter.printTitle()

    val dateTime = parseTime(arguments.time)
    val longitude = Angle.of(arguments.longitude)
    val latitude = Angle.of(arguments.latitude)

    val filters = mutableListOf<(ExtendedEntry) -> Boolean>()

    if (arguments.subCatalog != null) {
        filters.add { extendedEntry -> extendedEntry.entry.catalogName == arguments.subCatalog }
    }

    if (arguments.messierOnly != null) {
        filters.add { extendedEntry -> extendedEntry.entry.isMessier() }
    }

    if (arguments.nonMessier != null) {
        filters.add { extendedEntry -> !extendedEntry.entry.isMessier() }
    }

    if (arguments.minVMagnitude != null) {
        filters.add { extendedEntry -> extendedEntry.entry.vMag != null && (extendedEntry.entry.vMag!! <= arguments.minVMagnitude!!) }
    }

    if (arguments.maxVMagnitude != null) {
        filters.add { extendedEntry -> extendedEntry.entry.vMag != null && (extendedEntry.entry.vMag!! >= arguments.maxVMagnitude!!) }
    }

    if (arguments.minAltitude != null) {
        filters.add { extendedEntry -> extendedEntry.horizontalCoordinates.altitude.asDecimal() >= arguments.minAltitude!! }
    }

    if (arguments.types.isNotEmpty()) {
        val selectedTypes = arguments.types.toSet()
        filters.add { extendedEntry -> extendedEntry.entry.objectType in selectedTypes }
    }

    catalog.findExtendedEntries(GeographicCoordinates(latitude, longitude), dateTime) { entry ->
        filters.map { filter -> filter(entry) }.reduceOrNull { acc, b -> acc && b } ?: true
    }.forEach { extendedEntryPrinter.print(it) }
}

private fun parseTime(str: String): OffsetDateTime {
    if (str == "now") {
        return OffsetDateTime.now()
    }

    return OffsetDateTime.parse(str)
}
