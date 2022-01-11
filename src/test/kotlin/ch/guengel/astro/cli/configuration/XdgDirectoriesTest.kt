package ch.guengel.astro.cli.configuration

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.apache.commons.lang3.SystemUtils
import org.junit.jupiter.api.Test

internal class XdgDirectoriesTest {

    @Test
    fun getXdgConfigDirectory() {
        assertThat(XdgDirectories.xdgConfigDirectory).isEqualTo(SystemUtils.getUserHome().resolve(".config"))
    }

    @Test
    fun getXdgDataDirectory() {
        assertThat(XdgDirectories.xdgDataDirectory).isEqualTo(SystemUtils.getUserHome().resolve(".local")
            .resolve("share"))
    }
}
