package ch.guengel.astro.cli.actions

import ch.guengel.astro.cli.arguments.ShowVersionCommand

fun showVersionAction(arguments: ShowVersionCommand) {
    val version = Version()
    println("astro-cli ${version.version} (${version.gitId})")
}
