package ch.guengel.astro.cli.printer

import ch.guengel.astro.openngc.ExtendedEntry

interface ExtendedEntryPrinter {
    fun printTitle()
    fun print(extendedEntry: ExtendedEntry)
}
