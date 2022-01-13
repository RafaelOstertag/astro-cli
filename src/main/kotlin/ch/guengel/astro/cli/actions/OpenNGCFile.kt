package ch.guengel.astro.cli.actions

import ch.guengel.astro.cli.configuration.AppDirectories
import java.io.InputStream
import java.io.OutputStream
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.math.roundToInt


private const val openNGCUrl = "https://raw.githubusercontent.com/mattiaverga/OpenNGC/master/NGC.csv"
private const val bufferSize = 1024 * 8

object OpenNGCFile {
    val catalog = AppDirectories.data.resolve("NGC.csv")

    fun update() {
        println("Update catalog from $openNGCUrl")
        val httpRequest = HttpRequest.newBuilder()
            .uri(URI(openNGCUrl))
            .GET()
            .build()

        val response = HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofInputStream())
        when (response.statusCode()) {
            200 -> {
                response.body().use { inputStream ->
                    catalog.outputStream().use { outputStream ->
                        val contentLength =
                            response.headers().firstValue("Content-Length").map { it.toInt() }.orElse(-1)
                        writeData(inputStream, outputStream, contentLength)
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

    private fun writeData(inputStream: InputStream, outputStream: OutputStream, contentLength: Int) {
        val byteArray = ByteArray(bufferSize)
        var bytesRead = inputStream.read(byteArray)
        var totalBytesRead = bytesRead
        while (bytesRead != -1) {
            outputStream.write(byteArray, 0, bytesRead)
            bytesRead = inputStream.read(byteArray)
            totalBytesRead += bytesRead
            if (contentLength > 0) {
                val pcntDone = (totalBytesRead / contentLength.toDouble() * 100).roundToInt()
                print("Downloading %3d%%\r".format(pcntDone))
            }
        }
        if (contentLength > 0) {
            println("Downloading 100% -> Done")
            println()
        }
    }
}
