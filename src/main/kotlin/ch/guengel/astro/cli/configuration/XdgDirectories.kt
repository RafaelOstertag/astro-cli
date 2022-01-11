package ch.guengel.astro.cli.configuration

import org.apache.commons.lang3.SystemUtils
import java.io.File

object XdgDirectories {
    val xdgConfigDirectory = System.getenv("XDG_CONFIG_HOME")?.let { File(it) }
        ?: SystemUtils.getUserHome().resolve(".config")
    val xdgDataDirectory = System.getenv("XDG_DATA_HOME")?.let { File(it) }
        ?: SystemUtils.getUserHome().resolve(".local").resolve("share")

    fun createDirectories() {
        if (!xdgConfigDirectory.exists()) xdgConfigDirectory.mkdirs()
        if (!xdgDataDirectory.exists()) xdgDataDirectory.mkdirs()
    }
}
