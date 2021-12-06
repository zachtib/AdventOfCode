package res

import java.io.FileNotFoundException
import java.net.URL


class ResourceNotFoundException(
    filename: String
) : FileNotFoundException("Requested resource, $filename, was not present")

object ResourceLoader {
    fun getUrlForFile(filename: String): URL {
        return javaClass.classLoader.getResource(filename) ?: throw ResourceNotFoundException(filename)
    }
}