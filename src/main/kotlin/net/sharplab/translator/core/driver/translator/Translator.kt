package net.sharplab.translator.core.driver.translator

interface Translator {
    fun translate(texts: List<String>, srcLang: String, dstLang: String, glossaryId: String?): List<String>
}
