package ch.guengel.astro.cli.printer

import ch.guengel.astro.openngc.ExtendedEntry

class ExtendedEntryPrinter : Printer<ExtendedEntry> {
    private val titleFormatString = "%-8s %-22s %-5s %-4s %-12s %-16s %-16s %-16s"
    private val formatString = "%-8s %-22s %05.2f %-4d %12s %16s %16s %16s"
    override fun printTitle() {
        println(titleFormatString.format("Object", "Type", "V-Mag", "Mes", "RA", "Dec", "Alt", "Az"))
    }

    override fun print(item: ExtendedEntry) {
        println(
            formatString.format(
                item.entry.name,
                item.entry.objectType.description,
                item.entry.vMag ?: Double.NaN,
                item.entry.messier,
                item.entry.equatorialCoordinates?.rightAscension.toString(),
                item.entry.equatorialCoordinates?.declination.toString(),
                item.horizontalCoordinates.altitude.toString(),
                item.horizontalCoordinates.azimuth.toString()
            )
        )

    }
}
