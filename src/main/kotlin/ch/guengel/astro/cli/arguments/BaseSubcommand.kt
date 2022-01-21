package ch.guengel.astro.cli.arguments

import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand


@OptIn(ExperimentalCli::class)
abstract class BaseSubcommand(
    name: String,
    actionDescription: String,
) :
    Subcommand(name, actionDescription) {
    var called: Boolean = false
        private set

    final override fun execute() {
        called = true
    }

    abstract fun run()
}
