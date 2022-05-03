package net.sharplab.translator.core.driver.translator

import net.sharplab.deepl4j.DeepLApi
import net.sharplab.deepl4j.DeepLApiFactory
import net.sharplab.deepl4j.client.ApiException
import net.sharplab.deepl4j.model.Translations
import org.eclipse.microprofile.rest.client.RestClientBuilder
import org.jboss.resteasy.specimpl.MultivaluedMapImpl
import java.net.URI
import javax.enterprise.context.Dependent
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MultivaluedMap

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
        val translations: Translations
        try {
            translations = when (texts.size) {
                1 -> deepLApi.translateText(texts.first(), srcLang, dstLang, null, null, null, null, "xml", nonSplittingTags, null, null, ignoreTags)
                else -> deepLApi.translateTexts(texts, srcLang, dstLang, null, null, null, null, "xml", nonSplittingTags, null, null, ignoreTags)
            }
        } catch (e: ApiException) {
            val message = String.format("%d error is thrown: %s", e.code, e.responseBody)
            throw DeepLTranslatorException(message, e)
        }
        return translations.translations.map{ it.text }
    }

    companion object {
        private val IGNORE_ELEMENT_NAMES = java.util.List.of("abbr", "b", "cite", "code", "data", "dfn", "kbd", "rp", "rt", "rtc", "ruby", "samp", "time", "var")

        /**
         * インライン要素のタグリスト
         */
        val INLINE_ELEMENT_NAMES = listOf("a", "abbr", "b", "bdi", "bdo", "br", "cite", "code", "data", "dfn", "em", "i", "kbd", "mark", "q", "rp", "rt", "rtc", "ruby", "s", "samp", "small", "span", "strong", "sub", "sup", "time", "u", "var", "wbr")
    }

}