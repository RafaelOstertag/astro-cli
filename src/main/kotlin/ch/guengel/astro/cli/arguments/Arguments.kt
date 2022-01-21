package ch.guengel.astro.cli.arguments

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.default

@OptIn(ExperimentalCli::class)
class Arguments(private val subcommands: List<BaseSubcommand>) {
    private val parser = ArgParser("astro-cli")
        .also { argParser ->
            subcommands.forEach { argParser.subcommands(it) }
        }

    var noColor by parser.option(ArgType.Boolean,
        fullName = "no-color",
        description = "Disable colors").default(false)

    fun parse(args: Array<String>) {
        parser.parse(args)
    }
}
