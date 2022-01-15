package ch.guengel.astro.cli

import ch.guengel.astro.cli.actions.*
import ch.guengel.astro.cli.arguments.*
import ch.guengel.astro.cli.configuration.AppDirectories
import ch.guengel.astro.cli.configuration.UserConfiguration
import ch.guengel.astro.cli.configuration.XdgDirectories
import kotlinx.cli.ExperimentalCli
import org.fusesource.jansi.AnsiConsole

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
            SunCommand(userConfiguration, ::sunAction),
            MoonCommand(userConfiguration, ::moonAction),
        )
    )

    AnsiConsole.systemInstall()
    arguments.parse(args)
    AnsiConsole.systemInstall()
}


