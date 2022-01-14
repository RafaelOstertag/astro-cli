package ch.guengel.astro.cli.arguments

import ch.guengel.astro.cli.configuration.UserConfiguration
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import kotlinx.cli.default

@OptIn(ExperimentalCli::class)
class MoonCommand(userConfiguration: UserConfiguration, private val action: (MoonCommand) -> Unit) :
    Subcommand("moon", "Show Moon information") {
    val longitude by option(
        ArgType.Double,
        shortName = "l",
        description = "Longitude of the observer in decimal degrees"
    ).default(userConfiguration.longitude)

    val latitude by option(
        ArgType.Double,
        shortName = "L",
        description = "Latitude of the observer in decimal degrees"
    ).default(userConfiguration.latitude)

    val time by option(
        ArgType.String,
        shortName = "T",
        description = "Local time of the observer [YYYY-MM-dd'T'hh:mm:ss+HH:MM|'now']"
    ).default("now")

    override fun execute() {
        action(this)
    }
}
