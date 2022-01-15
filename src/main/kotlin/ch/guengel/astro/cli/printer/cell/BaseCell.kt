package ch.guengel.astro.cli.printer.cell

import org.fusesource.jansi.Ansi
import org.fusesource.jansi.Ansi.ansi

abstract class BaseCell(val width: Int) : Cell {
    var alignment: Alignment = Alignment.LEFT
    var color: Ansi.Color = Ansi.Color.DEFAULT
    var backgroundColor: Ansi.Color = Ansi.Color.DEFAULT
    var attribute: Ansi.Attribute? = null
    var colorIntensity: Intensity = Intensity.DIM
    var backgroundColorIntensity: Intensity = Intensity.BRIGHT
    var extraPaddingLeftChar = ' '
    var extraPaddingLeft = 0
    var extraPaddingRightChar = ' '
    var extraPaddingRight = 0

    init {
        if (width < 1) {
            throw IllegalArgumentException("Width must be greater than 0")
        }
    }

    abstract fun textProvider(): String

    override fun getFormattedText(): String {
        var rawText = textProvider()
        rawText = if (rawText.length > width) rawText.substring(0 until width) else rawText

        val alignmentChar = when (alignment) {
            Alignment.LEFT -> '-'
            Alignment.RIGHT -> null
        }

        val formatString = extraPaddingLeftChar.toString().repeat(extraPaddingLeft) +
                "%${alignmentChar ?: ""}${width}s" +
                extraPaddingRightChar.toString().repeat(extraPaddingRight)
        return formatString.format(rawText)
    }

    override fun getAnsiText(): String {
        var ansiPrinter = ansi()
        when (backgroundColorIntensity) {
            Intensity.DIM -> ansiPrinter = ansiPrinter.bg(backgroundColor)
            Intensity.BRIGHT -> ansiPrinter = ansiPrinter.bgBright(backgroundColor)
        }
        when (colorIntensity) {
            Intensity.DIM -> ansiPrinter = ansiPrinter.fg(color)
            Intensity.BRIGHT -> ansiPrinter = ansiPrinter.fgBright(color)
        }
        if (attribute != null) ansiPrinter.a(attribute)

        return ansiPrinter.a(getFormattedText()).reset().toString()
    }
}
