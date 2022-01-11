package ch.guengel.astro.cli.configuration

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.apache.commons.lang3.SystemUtils
import org.junit.jupiter.api.Test

internal class AppDirectoriesTest {

    @Test
    fun getConfig() {
        assertThat(AppDirectories.config).isEqualTo(SystemUtils.getUserHome()
            .resolve(".config").resolve(AppDirectories.appDirectoryName))
    }

    @Test
    fun getData() {
        assertThat(AppDirectories.data).isEqualTo(SystemUtils.getUserHome()
            .resolve(".local").resolve("share").resolve(AppDirectories.appDirectoryName))
    }
}
