package net.sharplab.translator.core.driver.translator

import net.sharplab.deepl4j.DeepLApi
import net.sharplab.deepl4j.DeepLApiFactory
import net.sharplab.deepl4j.client.ApiException
import net.sharplab.deepl4j.model.Translations

class DeepLTranslator(apiKey: String) : Translator {

    private val deepLApi : DeepLApi = DeepLApiFactory().create(apiKey);

    init {
        deepLApi.apiClient.servers.first().URL = "https://api.deepl.com/v2/"
    }

    override fun translate(texts: List<String>, srcLang: String, dstLang: String): List<String> {
        if (texts.isEmpty()) {
            return emptyList()
        }

        val nonSplittingTags = java.lang.String.join(",", INLINE_ELEMENT_NAMES)
        val ignoreTags = java.lang.String.join(",", IGNORE_ELEMENT_NAMES)

        val results = ArrayList<String>()
        val textsBuffer = ArrayList<String>()
        texts.forEach { text ->
            if(textsBuffer.sumOf { it.length } + text.length > MAX_REQUESTABLE_TEXT_LENGTH){
                try {
                    val translations = when (textsBuffer.size) {
                        0 -> Translations()
                        1 -> deepLApi.translateText(textsBuffer.first(), srcLang, dstLang, null, null, null, null, "xml", nonSplittingTags, null, null, ignoreTags)
                        else -> deepLApi.translateTexts(textsBuffer, srcLang, dstLang, null, null, null, null, "xml", nonSplittingTags, null, null, ignoreTags)
                    }
                    results.addAll(translations.translations.map { translation -> translation.text })
                } catch (e: ApiException) {
                    val message = String.format("%d error is thrown: %s", e.code, e.responseBody)
                    throw DeepLTranslatorException(message, e)
                }
                textsBuffer.clear()
            }

            textsBuffer.add(text)
        }
        return results.toList()
    }

    companion object {
        private const val MAX_REQUESTABLE_TEXT_LENGTH = 1000

        private val IGNORE_ELEMENT_NAMES = listOf("abbr", "b", "cite", "code", "data", "dfn", "kbd", "rp", "rt", "rtc", "ruby", "samp", "time", "var")

        /**
         * インライン要素のタグリスト
         */
        val INLINE_ELEMENT_NAMES = listOf("a", "abbr", "b", "bdi", "bdo", "br", "cite", "code", "data", "dfn", "em", "i", "kbd", "mark", "q", "rp", "rt", "rtc", "ruby", "s", "samp", "small", "span", "strong", "sub", "sup", "time", "u", "var", "wbr")
    }

}