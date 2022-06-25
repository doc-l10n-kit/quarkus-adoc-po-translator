package net.sharplab.translator.core.service

import net.sharplab.translator.core.model.PoFile

interface PoTranslatorService{

    fun translate(poFile: PoFile, srcLang: String, dstLang: String, isAsciidoctor: Boolean = true, glossaryId: String? = null): PoFile

}