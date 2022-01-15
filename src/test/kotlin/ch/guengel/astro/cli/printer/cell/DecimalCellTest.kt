package ch.guengel.astro.cli.printer.cell

import assertk.assertThat
import assertk.assertions.hasClass
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import org.junit.jupiter.api.Test

internal class DecimalCellTest {
    @Test
    fun `should validate decimal places`() {
        assertThat { DecimalCell(1.25, 0) }.isFailure().hasClass(IllegalArgumentException::class)
    }

    @Test
    fun `should format decimals`() {
        var decimalCell = DecimalCell(1.25, 6).apply { decimalPlaces = 1 }
        assertThat(decimalCell.getFormattedText()).isEqualTo("   1.3")

        decimalCell = DecimalCell(1.25, 6).apply { decimalPlaces = 2 }
        assertThat(decimalCell.getFormattedText()).isEqualTo("  1.25")

        decimalCell = DecimalCell(1.25, 6).apply { decimalPlaces = 3 }
        assertThat(decimalCell.getFormattedText()).isEqualTo(" 1.250")
    }

    @Test
    fun `should format decimals with leading zeros`() {
        var decimalCell = DecimalCell(1.25, 5).apply {
            decimalPlaces = 1
            leadingZero = true
        }
        assertThat(decimalCell.getFormattedText()).isEqualTo("001.3")

        decimalCell = DecimalCell(1.25, 6).apply {
            decimalPlaces = 2
            leadingZero = true
        }
        assertThat(decimalCell.getFormattedText()).isEqualTo("001.25")

        decimalCell = DecimalCell(1.25, 6).apply {
            decimalPlaces = 3
            leadingZero = true
        }
        assertThat(decimalCell.getFormattedText()).isEqualTo("01.250")
    }
}
