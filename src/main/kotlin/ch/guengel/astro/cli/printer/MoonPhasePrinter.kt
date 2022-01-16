package ch.guengel.astro.cli.printer

class MoonPhasePrinter : Printer<Double> {
    override fun printTitle() {
        println("-".repeat(maxLength))
    }

    override fun print(item: Double) {
        println("Phase: %-6.2f".format(item))
    }
}
