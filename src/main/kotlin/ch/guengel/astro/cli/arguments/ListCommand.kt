package ch.guengel.astro.cli.arguments

import ch.guengel.astro.openngc.CatalogName
import ch.guengel.astro.openngc.ObjectType
import kotlinx.cli.*

@OptIn(ExperimentalCli::class)
class ListCommand(private val action: (ListCommand) -> Unit) : Subcommand("list", "List objects") {
    val update by option(
        ArgType.Boolean,
        shortName = "U",
        description = "Update catalog"
    ).default(false)

    val longitude by option(
        ArgType.Double,
        shortName = "l",
        description = "Longitude of the observer in decimal degrees"
    ).default(8.8322787)

    val latitude by option(
        ArgType.Double,
        shortName = "L",
        description = "Latitude of the observer in decimal degrees"
    ).default(47.5486024)

    val time by option(
        ArgType.String,
        shortName = "t",
        description = "Local time of the observer [YYYY-MM-dd'T'hh:mm:ss+HH:MM|'now']"
    ).default("now")

    val messierOnly by option(
        ArgType.Boolean,
        shortName = "m",
        fullName = "messier-only",
        description = "Only Messier objects"
    )

    val nonMessier by option(
        ArgType.Boolean,
        shortName = "M",
        fullName = "non-messier",
        description = "Non-Messier objects only"
    )

    val subCatalog by option(
        ArgType.Choice<CatalogName>(),
        shortName = "s",
        fullName = "sub-catalog",
        description = "IC or NGC. Both if not specified"
    )

    val maxVMagnitude by option(
        ArgType.Double,
        fullName = "max-v-magnitude",
        description = "Maximum V-Magnitude"
    )

    val minVMagnitude by option(
        ArgType.Double,
        fullName = "min-v-magnitude",
        description = "Minimum V-Magnitude"
    )

    val minAltitude by option(
        ArgType.Double,
        fullName = "min-altitude",
        description = "Minimum altitude"
    )

    val types by option(
        ArgType.Choice<ObjectType>(),
        description = "Only this types"
    ).multiple()

    override fun execute() {
        action(this)
    }
}