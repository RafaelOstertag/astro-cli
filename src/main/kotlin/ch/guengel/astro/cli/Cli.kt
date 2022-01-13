package ch.guengel.astro.cli

import ch.guengel.astro.cli.actions.eqToHorizonAction
import ch.guengel.astro.cli.actions.listAction
import ch.guengel.astro.cli.actions.showVersionAction
import ch.guengel.astro.cli.arguments.Arguments
import ch.guengel.astro.cli.arguments.EqToHorizonCommand
import ch.guengel.astro.cli.arguments.ListCommand
import ch.guengel.astro.cli.arguments.ShowVersionCommand
import kotlinx.cli.ExperimentalCli

@OptIn(ExperimentalCli::class)
fun main(args: Array<String>) {
    val arguments = Arguments(
        listOf(
            ListCommand(::listAction),
            EqToHorizonCommand(::eqToHorizonAction),
            ShowVersionCommand(::showVersionAction)
        )
    )
    arguments.parse(args)
}


