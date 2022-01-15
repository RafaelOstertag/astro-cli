package ch.guengel.astro.cli.printer.cell

class TextCell(val value: String, width: Int) : BaseCell(width) {
    override fun textProvider(): String = value
}
