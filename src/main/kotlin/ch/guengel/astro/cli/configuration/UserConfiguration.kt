package ch.guengel.astro.cli.configuration

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

private val configFile = AppDirectories.config.resolve("config.yaml")

class UserConfiguration {
    var longitude: Double = 8.8322787
    var latitude: Double = 47.5486024

    companion object {
        fun load(): UserConfiguration {
            if (!configFile.exists()) configFile.createNewFile()

            return configFile.readText(Charsets.UTF_8).takeIf { it.isNotBlank() }?.let {
                val yaml = Yaml(Constructor(UserConfiguration::class.java))
                yaml.loadAs(it, UserConfiguration::class.java)
            } ?: UserConfiguration()
        }
    }
}

fun UserConfiguration.save() {
    val yaml = Yaml(Constructor(UserConfiguration::class.java))
    configFile.writeText(yaml.dumpAsMap(this), Charsets.UTF_8)
}
