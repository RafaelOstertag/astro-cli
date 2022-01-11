package ch.guengel.astro.cli.printer

import ch.guengel.astro.openngc.ExtendedEntry

class ExtendedEntryPrinterImpl : ExtendedEntryPrinter {
    private val titleFormatString = "%-8s %-22s %-5s %-4s %-12s %-16s %-16s %-16s"
    private val formatString = "%-8s %-22s %05.2f %-4d %12s %16s %16s %16s"
    override fun printTitle() {
        println(titleFormatString.format("Object", "Type", "V-Mag", "Mes", "RA", "Dec", "Alt", "Az"))
    }

    override fun print(extendedEntry: ExtendedEntry) {
        println(
            formatString.format(
                extendedEntry.entry.name,
                extendedEntry.entry.objectType.description,
                extendedEntry.entry.vMag ?: Double.NaN,
                extendedEntry.entry.messier,
                extendedEntry.entry.equatorialCoordinates?.rightAscension.toString(),
                extendedEntry.entry.equatorialCoordinates?.declination.toString(),
                extendedEntry.horizontalCoordinates.altitude.toString(),
                extendedEntry.horizontalCoordinates.azimuth.toString()
            )
        )

    }
}
