package ch.guengel.astro.cli.printer.cell

class DecimalCell(val value: Double, width: Int) : BaseCell(width) {
    var leadingZero: Boolean = false
    var decimalPlaces: Int = 1

    init {
        if (decimalPlaces < 1) {
            throw IllegalArgumentException("Decimal places must be at least 1")
        }
    }

    override fun textProvider(): String {
        val formatString = "%${if (leadingZero) '0' else ""}${width}.${decimalPlaces}f"
        return formatString.format(value)
    }
}
