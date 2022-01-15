package ch.guengel.astro.cli.actions

import ch.guengel.astro.cli.arguments.ListCommand
import ch.guengel.astro.cli.printer.ExtendedEntryPrinter
import ch.guengel.astro.coordinates.Angle
import ch.guengel.astro.coordinates.GeographicCoordinates
import ch.guengel.astro.openngc.Entry
import ch.guengel.astro.openngc.ExtendedEntry
import ch.guengel.astro.openngc.parser.CSVParser

fun listAction(arguments: ListCommand) {
    updateCatalog(arguments)

    val catalog = CSVParser.parse(OpenNGCFile.catalog)

    val extendedEntryPrinter = ExtendedEntryPrinter()
    extendedEntryPrinter.printTitle()

    val dateTime = parseTime(arguments.time)
    val observerCoordinates = GeographicCoordinates(Angle.of(arguments.latitude), Angle.of(arguments.longitude))

    val filters = compileFilters(arguments)

    if (filters.entryFilters.isEmpty() && filters.extendedEntryFilters.isEmpty()) {
        catalog.findExtendedEntries(observerCoordinates, dateTime) { true }.forEach { extendedEntryPrinter.print(it) }
    } else if (filters.entryFilters.isEmpty()) {
        catalog.findExtendedEntries(observerCoordinates, dateTime) { extendedEntry ->
            filters.extendedEntryFilters.map { filter -> filter(extendedEntry) }.reduceOrNull { acc, b -> acc && b }
                ?: true
        }.forEach { extendedEntryPrinter.print(it) }
    } else if (filters.extendedEntryFilters.isEmpty()) {
        val entryList = catalog.find { entry ->
            filters.entryFilters.map { filter -> filter(entry) }.reduceOrNull { acc, b -> acc && b } ?: true
        }
        catalog.extendEntries(observerCoordinates, dateTime, entryList).forEach { extendedEntryPrinter.print(it) }
    } else {
        val entryList = catalog.find { entry ->
            filters.entryFilters.map { filter -> filter(entry) }.reduceOrNull { acc, b -> acc && b } ?: true
        }
        catalog.extendEntries(observerCoordinates, dateTime, entryList).filter { extendedEntry ->
            filters.extendedEntryFilters.map { filter -> filter(extendedEntry) }.reduceOrNull { acc, b -> acc && b }
                ?: true
        }.forEach { extendedEntryPrinter.print(it) }
    }
}

private data class Filters(
    val entryFilters: List<(Entry) -> Boolean>,
    val extendedEntryFilters: List<(ExtendedEntry) -> Boolean>,
)

private fun compileFilters(arguments: ListCommand): Filters {
    val extendedEntryFilters = mutableListOf<(ExtendedEntry) -> Boolean>()
    val entryFilters = mutableListOf<(Entry) -> Boolean>()

    if (arguments.subCatalog != null) {
        entryFilters.add { entry -> entry.catalogName == arguments.subCatalog }
    }

    if (arguments.messierOnly != null) {
        entryFilters.add { entry -> entry.isMessier() }
    }

    if (arguments.nonMessier != null) {
        entryFilters.add { entry -> !entry.isMessier() }
    }

    if (arguments.minVMagnitude != null) {
        entryFilters.add { entry -> entry.vMag != null && (entry.vMag!! <= arguments.minVMagnitude!!) }
    }

    if (arguments.maxVMagnitude != null) {
        entryFilters.add { entry -> entry.vMag != null && (entry.vMag!! >= arguments.maxVMagnitude!!) }
    }

    if (arguments.minAltitude != null) {
        extendedEntryFilters.add { extendedEntry -> extendedEntry.horizontalCoordinates.altitude.asDecimal() >= arguments.minAltitude!! }
    }

    if (arguments.minAzimuth != null) {
        extendedEntryFilters.add { extendedEntry -> extendedEntry.horizontalCoordinates.azimuth.asDecimal() >= arguments.minAzimuth!! }
    }

    if (arguments.maxAzimuth != null) {
        extendedEntryFilters.add { extendedEntry -> extendedEntry.horizontalCoordinates.azimuth.asDecimal() <= arguments.maxAzimuth!! }
    }

    if (arguments.objects.isNotEmpty()) {
        val selectedObjects = arguments.objects.toSet()
        entryFilters.add { entry -> entry.name in selectedObjects }
    }

    if (arguments.types.isNotEmpty()) {
        val selectedTypes = arguments.types.toSet()
        entryFilters.add { entry -> entry.objectType in selectedTypes }
    }

    return Filters(entryFilters, extendedEntryFilters)
}

private fun updateCatalog(arguments: ListCommand) {
    if (arguments.update) {
        OpenNGCFile.update()
    } else {
        OpenNGCFile.updateWhenNotExists()
    }
}

