package ch.guengel.astro.cli.configuration

object AppDirectories {
    internal val appDirectoryName = "astro-cli"
    val config = XdgDirectories.xdgConfigDirectory.resolve(appDirectoryName)
    val data = XdgDirectories.xdgDataDirectory.resolve(appDirectoryName)

    fun createDirectories() {
        if (!config.exists()) config.mkdirs()
        if (!data.exists()) data.mkdirs()
    }
}
