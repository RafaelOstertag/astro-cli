package ch.guengel.astro.cli.actions

fun showVersionAction() {
    val version = Version()
    println("astro-cli ${version.version} (${version.gitId})")
}
