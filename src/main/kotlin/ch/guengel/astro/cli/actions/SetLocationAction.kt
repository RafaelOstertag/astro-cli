package ch.guengel.astro.cli.actions

import ch.guengel.astro.cli.arguments.SetLocationCommand
import ch.guengel.astro.cli.configuration.UserConfiguration
import ch.guengel.astro.cli.configuration.save

fun setLocationAction(arguments: SetLocationCommand) {
    UserConfiguration().apply {
        longitude = arguments.longitude
        latitude = arguments.latitude
    }.save()
}
