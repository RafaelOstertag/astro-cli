package ch.guengel.astro.cli

import java.util.*

class Version {
    private val gitInfoProperties = Properties().apply {
        Version::class.java.getResourceAsStream("/git.properties")?.let {
            load(it)
        }
    }

    val gitId: String
        get() = gitInfoProperties.getProperty("git.commit.id.abbrev", "n/a")

    val version: String get() = gitInfoProperties.getProperty("git.build.version", "n/a")
}
