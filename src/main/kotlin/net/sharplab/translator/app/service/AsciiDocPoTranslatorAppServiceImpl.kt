package net.sharplab.translator.app.service

import net.sharplab.translator.core.driver.po.PoReader
import net.sharplab.translator.core.driver.po.PoWriter
import net.sharplab.translator.core.service.PoTranslatorService
import java.io.File
import javax.enterprise.context.Dependent

@Dependent
class AsciiDocPoTranslatorAppServiceImpl(private val poTranslatorService: PoTranslatorService) : AsciiDocPoTranslatorAppService {

    private val poReader = PoReader()
    private val poWriter = PoWriter()

    override fun translateAsciiDocPoFile(filePath: File, srcLang: String, dstLang: String) {
        val poFile = poReader.read(filePath)
        val translated = poTranslatorService.translate(poFile, srcLang, dstLang)
        poWriter.write(translated, filePath)
    }
}