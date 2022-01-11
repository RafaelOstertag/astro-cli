package ch.guengel.astro.cli

import ch.guengel.astro.cli.configuration.AppDirectories
import java.io.BufferedInputStream
import java.io.OutputStream
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


object OpenNGCFile {
    val catalog = AppDirectories.data.resolve("NGC.csv")

    fun update() {

        val httpRequest = HttpRequest.newBuilder()
            .uri(URI("https://raw.githubusercontent.com/mattiaverga/OpenNGC/master/NGC.csv"))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofInputStream())
        when (response.statusCode()) {
            200 -> {
                BufferedInputStream(response.body()).use { inputStream ->
                    catalog.outputStream().use { outputStream ->
                        writeData(inputStream, outputStream)
                    }
                }
            }
            else -> {
                throw RuntimeException("Got HTTP Status Code ${response.statusCode()} when trying to download catalog")
            }
        }
    }

    fun updateWhenNotExists() {
        if (catalog.exists()) {
            return
        }

        update()
    }

    private fun writeData(inputStream: BufferedInputStream, outputStream: OutputStream) {
        var c = inputStream.read()
        while (c != -1) {
            outputStream.write(c)
            c = inputStream.read()
        }
    }
}
