package net.sharplab.translator.core.driver.po

import net.sharplab.translator.core.model.PoFile
import org.fedorahosted.tennera.jgettext.Catalog
import org.fedorahosted.tennera.jgettext.PoWriter
import java.io.File
import java.io.FileOutputStream

class PoWriter {
    fun write(poFile: PoFile, filePath: File) {
        FileOutputStream(filePath).use {
            val poWriter = PoWriter()
            val catalog = Catalog()
            poFile.messages.forEach { item -> catalog.addMessage(item.message) }
            poWriter.write(catalog, it)
        }
    }
}