package net.sharplab.translator.core.driver.translator

import com.deepl.api.DeepLException
import com.deepl.api.TextResult
import com.deepl.api.TextTranslationOptions
import com.deepl.api.TranslatorOptions

class DeepLTranslator(apiKey: String) : Translator {

    private val deepLApi : com.deepl.api.Translator;

    init {
        val translatorOptions = TranslatorOptions()
        translatorOptions.serverUrl = "https://api.deepl.com/"
        deepLApi = com.deepl.api.Translator(apiKey,translatorOptions)
    }

    override fun translate(texts: List<String>, srcLang: String, dstLang: String): List<String> {
        if (texts.isEmpty()) {
            return emptyList()
        }
        val translations: List<TextResult>
        try {
            val textTranslatorOptions = TextTranslationOptions()
            textTranslatorOptions.nonSplittingTags = INLINE_ELEMENT_NAMES
            textTranslatorOptions.ignoreTags = IGNORE_ELEMENT_NAMES
            textTranslatorOptions.tagHandling = "xml"
            translations = deepLApi.translateText(texts, srcLang, dstLang, textTranslatorOptions)
        } catch (e: DeepLException) {
            throw DeepLTranslatorException("DeepL error is thrown", e)
        }
        return translations.map{ it.text }
    }

    companion object {

        private val IGNORE_ELEMENT_NAMES = listOf("abbr", "b", "cite", "code", "data", "dfn", "kbd", "rp", "rt", "rtc", "ruby", "samp", "time", "var")

        /**
         * インライン要素のタグリスト
         */
        val INLINE_ELEMENT_NAMES = listOf("a", "abbr", "b", "bdi", "bdo", "br", "cite", "code", "data", "dfn", "em", "i", "kbd", "mark", "q", "rp", "rt", "rtc", "ruby", "s", "samp", "small", "span", "strong", "sub", "sup", "time", "u", "var", "wbr")
    }

}