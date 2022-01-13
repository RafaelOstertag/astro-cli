package ch.guengel.astro.cli.arguments

import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class SetLocationCommand(private val action: (SetLocationCommand) -> Unit) :
    Subcommand("set-location", "Set default location") {
    val longitude by argument(
        ArgType.Double,
        fullName = "longitude",
        description = "Longitude of the observer in decimal degrees"
    )

    val latitude by argument(
        ArgType.Double,
        fullName = "latitude",
        description = "Latitude of the observer in decimal degrees"
    )

    override fun execute() {
        action(this)
    }
}
