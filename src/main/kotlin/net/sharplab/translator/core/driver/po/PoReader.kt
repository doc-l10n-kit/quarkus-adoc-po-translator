package net.sharplab.translator.core.driver.po

import net.sharplab.translator.core.model.PoMessage
import net.sharplab.translator.core.model.PoFile
import org.fedorahosted.tennera.jgettext.PoParser
import java.io.File

class PoReader {

    private val poParser = PoParser()

    fun read(file: File): PoFile {

        val catalog = poParser.parseCatalog(file)
        val messages = catalog.map {item -> PoMessage(item) }
        return PoFile(messages)
    }
}