package ch.guengel.astro.cli

import ch.guengel.astro.cli.actions.eqToHorizonAction
import ch.guengel.astro.cli.actions.listAction
import ch.guengel.astro.cli.actions.setLocationAction
import ch.guengel.astro.cli.actions.showVersionAction
import ch.guengel.astro.cli.arguments.*
import ch.guengel.astro.cli.configuration.AppDirectories
import ch.guengel.astro.cli.configuration.UserConfiguration
import ch.guengel.astro.cli.configuration.XdgDirectories
import kotlinx.cli.ExperimentalCli

@OptIn(ExperimentalCli::class)
fun main(args: Array<String>) {
    XdgDirectories.createDirectories()
    AppDirectories.createDirectories()

    val userConfiguration = UserConfiguration.load()

    val arguments = Arguments(
        listOf(
            ListCommand(userConfiguration, ::listAction),
            EqToHorizonCommand(userConfiguration, ::eqToHorizonAction),
            SetLocationCommand(::setLocationAction),
            ShowVersionCommand(::showVersionAction),
        )
    )
    arguments.parse(args)
}


