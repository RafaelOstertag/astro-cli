package ch.guengel.astro.cli.arguments

import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class ShowVersionCommand(private val action: (ShowVersionCommand) -> Unit) : Subcommand("version", "Show version") {
    override fun execute() {
        action(this)
    }
}
