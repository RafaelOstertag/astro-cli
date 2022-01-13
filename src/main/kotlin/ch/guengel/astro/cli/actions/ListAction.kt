package ch.guengel.astro.cli.actions

import ch.guengel.astro.cli.arguments.ListCommand
import ch.guengel.astro.cli.printer.ExtendedEntryPrinter
import ch.guengel.astro.coordinates.Angle
import ch.guengel.astro.coordinates.GeographicCoordinates
import ch.guengel.astro.openngc.ExtendedEntry
import ch.guengel.astro.openngc.parser.CSVParser

fun listAction(arguments: ListCommand) {
    updateCatalog(arguments)

    val catalog = CSVParser.parse(OpenNGCFile.catalog)

    val extendedEntryPrinter = ExtendedEntryPrinter()
    extendedEntryPrinter.printTitle()

    val dateTime = parseTime(arguments.time)
    val longitude = Angle.of(arguments.longitude)
    val latitude = Angle.of(arguments.latitude)

    val filters = compileFilters(arguments)

    catalog.findExtendedEntries(GeographicCoordinates(latitude, longitude), dateTime) { entry ->
        filters.map { filter -> filter(entry) }.reduceOrNull { acc, b -> acc && b } ?: true
    }.forEach { extendedEntryPrinter.print(it) }
}

private fun compileFilters(arguments: ListCommand): List<(ExtendedEntry) -> Boolean> {
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

    if (arguments.objects.isNotEmpty()) {
        val selectedObjects = arguments.objects.toSet()
        filters.add { extendedEntry -> extendedEntry.entry.name in selectedObjects }
    }

    if (arguments.types.isNotEmpty()) {
        val selectedTypes = arguments.types.toSet()
        filters.add { extendedEntry -> extendedEntry.entry.objectType in selectedTypes }
    }
    return filters
}

private fun updateCatalog(arguments: ListCommand) {
    if (arguments.update) {
        OpenNGCFile.update()
    } else {
        OpenNGCFile.updateWhenNotExists()
    }
}

