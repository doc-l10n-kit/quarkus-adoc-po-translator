package net.sharplab.translator.core.driver.translator

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import net.sharplab.translator.app.service.AsciiDocPoTranslatorAppServiceImpl
import okhttp3.*
import org.jboss.logging.Logger
import java.time.Duration


class MSTranslator(private val subscriptionKey: String, private val location: String, private val category: String) : Translator {

    private val logger = Logger.getLogger(MSTranslator::class.java)

    private var client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(Duration.ofMinutes(1))
            .readTimeout(Duration.ofMinutes(1))
            .build()
    private val objectMapper = ObjectMapper()

    init {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    override fun translate(texts: List<String>, srcLang: String, dstLang: String): List<String> {
        val subLists = texts.withIndex().groupBy { it.index / 20 };
        return subLists.map{
            val subList = it.value.map { item -> item.value }
            doTranslate(subList, srcLang, dstLang)
        }.flatten()
    }

    private fun doTranslate(texts: List<String>, srcLang: String, dstLang: String): List<String> {

        val url = HttpUrl.Builder()
                .scheme("https")
                .host("api.cognitive.microsofttranslator.com")
                .addPathSegment("/translate")
                .addQueryParameter("api-version", "3.0")
                .addQueryParameter("from", srcLang)
                .addQueryParameter("to", dstLang)
                .addQueryParameter("textType", "html")
                .addQueryParameter("category", category)
                .addQueryParameter("includeAlignment", "true")
                .addQueryParameter("includeSentenceLength", "true")
                .addQueryParameter("allowFallback", "false")
                .build()
        val mediaType = MediaType.parse("application/json")
        val bodyString = objectMapper.writeValueAsString(texts.map { text -> mapOf("text" to text)})
        val body = RequestBody.create(mediaType, bodyString)
        val request = Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey)
                .addHeader("Ocp-Apim-Subscription-Region", location)
                .addHeader("Content-type", "application/json")
                .build()
        val response = client.newCall(request).execute()
        val translateResponse = objectMapper.readValue<TranslateResponse>(response.body()?.string(), TranslateResponse::class.java)
        return translateResponse.map { sentence -> sentence.translations.first().text }
    }

}