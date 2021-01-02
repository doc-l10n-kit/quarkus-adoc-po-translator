package net.sharplab.translator.test

import java.io.File
import java.net.URL


object PoTestUtil {

    fun loadTestPoFile(): File {
        val resource: URL = javaClass.classLoader.getResource("quarkus-getting-started.adoc.po") ?: throw IllegalStateException("Test file could not loaded.")
        return File(resource.toURI())
    }

}