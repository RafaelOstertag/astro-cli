package ch.guengel.astro.cli.printer.cell

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.hasClass
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import org.junit.jupiter.api.Test

internal class TextCellTest {
    @Test
    fun `right alignment`() {
        val textCell = TextCell("abc", 6).apply { alignment = Alignment.RIGHT }
        assertThat(textCell.getFormattedText()).isEqualTo("   abc")

        val largeText = TextCell("abcdefg", 6).apply { alignment = Alignment.RIGHT }
        assertThat(largeText.getFormattedText()).isEqualTo("abcdef")
    }

    @Test
    fun `left alignment`() {
        val textCell = TextCell("abc", 6).apply { alignment = Alignment.LEFT }
        assertThat(textCell.getFormattedText()).isEqualTo("abc   ")

        val largeText = TextCell("abcdefg", 6).apply { alignment = Alignment.LEFT }
        assertThat(largeText.getFormattedText()).isEqualTo("abcdef")
    }

    @Test
    fun `right alignment with extra padding`() {
        val textCell = TextCell("abc", 6).apply {
            alignment = Alignment.RIGHT
            extraPaddingLeftChar = '*'
            extraPaddingLeft = 2
            extraPaddingRightChar = '-'
            extraPaddingRight = 3
        }
        assertThat(textCell.getFormattedText()).isEqualTo("**   abc---")
    }

    @Test
    fun `left alignment with extra padding`() {
        val textCell = TextCell("abc", 6).apply {
            alignment = Alignment.LEFT
            extraPaddingLeftChar = '*'
            extraPaddingLeft = 3
            extraPaddingRightChar = '-'
            extraPaddingRight = 2
        }
        assertThat(textCell.getFormattedText()).isEqualTo("***abc   --")

        val largeText = TextCell("abcdefg", 6).apply { alignment = Alignment.LEFT }
        assertThat(largeText.getFormattedText()).isEqualTo("abcdef")
    }

    @Test
    fun `right alignment ansi`() {
        val textCell = TextCell("abc", 6).apply { alignment = Alignment.RIGHT }
        assertThat(textCell.getAnsiText()).contains("   abc")

        val largeText = TextCell("abcdefg", 6).apply { alignment = Alignment.RIGHT }
        assertThat(largeText.getAnsiText()).contains("abcdef")
    }

    @Test
    fun `left alignment ansi`() {
        val textCell = TextCell("abc", 6).apply { alignment = Alignment.LEFT }
        assertThat(textCell.getAnsiText()).contains("abc   ")

        val largeText = TextCell("abcdefg", 6).apply { alignment = Alignment.LEFT }
        assertThat(largeText.getAnsiText()).contains("abcdef")
    }

    @Test
    fun `should validate width`() {
        assertThat { TextCell("a", 0) }.isFailure().hasClass(IllegalArgumentException::class)
    }
}
