package net.sharplab.translator.app.service

import java.io.File

interface AsciiDocPoTranslatorAppService {

    fun translateAsciiDocPoFile(filePath: File, srcLang: String, dstLang: String);

}