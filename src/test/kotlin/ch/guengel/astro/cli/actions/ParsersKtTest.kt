package ch.guengel.astro.cli.actions

import assertk.assertThat
import assertk.assertions.isBetween
import assertk.assertions.isEqualTo
import ch.guengel.astro.coordinates.Angle
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.time.ZoneOffset

internal class ParsersKtTest {

    @Test
    fun `should parse time`() {
        val now = OffsetDateTime.now()
        assertThat(parseTime("now")).isBetween(now.minusSeconds(2), now.plusSeconds(2))

        assertThat(OffsetDateTime.parse("2022-01-13T00:08:00+01:00"))
            .isEqualTo(OffsetDateTime.of(2022, 1, 13, 0, 8, 0, 0, ZoneOffset.ofHours(1)))
    }

    @Test
    fun `should parse declination`() {
        assertThat(parseDeclination("290:01:02.1234")).isEqualTo(Angle(290, 1, 2.1234))
        assertThat(parseDeclination("290:1:2.1234")).isEqualTo(Angle(290, 1, 2.1234))
        assertThat(parseDeclination("+290:01:02.1234")).isEqualTo(Angle(290, 1, 2.1234))
        assertThat(parseDeclination("+290:1:2.1234")).isEqualTo(Angle(290, 1, 2.1234))
        assertThat(parseDeclination("-290:01:02.1234")).isEqualTo(Angle(-290, 1, 2.1234))
        assertThat(parseDeclination("-290:1:2.1234")).isEqualTo(Angle(-290, 1, 2.1234))

    }
}
