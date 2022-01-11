package ch.guengel.astro.cli

import ch.guengel.astro.openngc.CatalogName
import ch.guengel.astro.openngc.ObjectType
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import kotlinx.cli.multiple

enum class Operation {
    LIST
}

class Arguments {
    private val parser = ArgParser("astro-cli")
    val update by parser.option(
        ArgType.Boolean,
        shortName = "U",
        description = "Update catalog"
    ).default(false)

    val longitude by parser.option(
        ArgType.Double,
        shortName = "l",
        description = "Longitude of the observer in decimal degrees"
    ).default(8.8322787)

    val latitude by parser.option(
        ArgType.Double,
        shortName = "L",
        description = "Latitude of the observer in decimal degrees"
    ).default(47.5486024)

    val time by parser.option(
        ArgType.String,
        shortName = "t",
        description = "Local time of the observer [YYYY-MM-dd'T'hh:mm:ss+HH:MM|'now']"
    ).default("now")

    val messierOnly by parser.option(
        ArgType.Boolean,
        shortName = "m",
        fullName = "messier-only",
        description = "Only Messier objects"
    )

    val nonMessier by parser.option(
        ArgType.Boolean,
        shortName = "M",
        fullName = "non-messier",
        description = "Non-Messier objects only"
    )

    val subCatalog by parser.option(
        ArgType.Choice<CatalogName>(),
        shortName = "s",
        fullName = "sub-catalog",
        description = "IC or NGC. Both if not specified"
    )

    val maxVMagnitude by parser.option(
        ArgType.Double,
        fullName = "max-v-magnitude",
        description = "Maximum V-Magnitude"
    )

    val minVMagnitude by parser.option(
        ArgType.Double,
        fullName = "min-v-magnitude",
        description = "Minimum V-Magnitude"
    )

    val minAltitude by parser.option(
        ArgType.Double,
        fullName = "min-altitude",
        description = "Minimum altitude"
    )

    val types by parser.option(
        ArgType.Choice<ObjectType>(),
        description = "Only this types"
    ).multiple()

    fun parse(args: Array<String>) {
        parser.parse(args)
    }
}
