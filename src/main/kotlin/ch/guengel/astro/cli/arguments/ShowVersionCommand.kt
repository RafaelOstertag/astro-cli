package ch.guengel.astro.cli.arguments

import kotlinx.cli.ExperimentalCli

@OptIn(ExperimentalCli::class)
class ShowVersionCommand(private val action: () -> Unit) : BaseSubcommand("version", "Show version") {
    override fun run() {
        action()
    }
}
