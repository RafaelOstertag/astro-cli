package ch.guengel.astro.cli.arguments

import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class Arguments(private val subcommands: List<Subcommand>) {
    private val parser = ArgParser("astro-cli").also { argParser ->
        subcommands.forEach { argParser.subcommands(it) }
    }

    fun parse(args: Array<String>) {
        parser.parse(args)
    }
}
