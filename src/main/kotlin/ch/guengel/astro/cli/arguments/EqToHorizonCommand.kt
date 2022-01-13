package ch.guengel.astro.cli.arguments

import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import kotlinx.cli.default

@OptIn(ExperimentalCli::class)
class EqToHorizonCommand(private val action: (EqToHorizonCommand) -> Unit) :
    Subcommand("eq-to-altaz", "Equatorial to Horizon Coordinates") {
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
        shortName = "T",
        description = "Local time of the observer [YYYY-MM-dd'T'hh:mm:ss+HH:MM|'now']"
    ).default("now")

    val terse by option(
        ArgType.Boolean,
        shortName = "t",
        description = "Terse output"
    ).default(false)

    val rightAscension by argument(ArgType.String,
        fullName = "right_ascension",
        description = "Right ascension [hh:mm:ss.sss]")

    val declination by argument(ArgType.String,
        fullName = "declination",
        description = "Declination in degrees [dd:min:sec]")


    override fun execute() {
        action(this)
    }
}
