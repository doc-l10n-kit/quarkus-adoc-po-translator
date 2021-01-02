package net.sharplab.translator.core.service

import net.sharplab.translator.core.driver.translator.Translator
import net.sharplab.translator.core.model.MessageType
import net.sharplab.translator.core.model.PoFile
import net.sharplab.translator.core.model.PoMessage
import org.asciidoctor.jruby.internal.JRubyAsciidoctor
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import javax.enterprise.context.Dependent

@Dependent
class PoTranslatorServiceImpl(private val translator: Translator) : PoTranslatorService {

    private val asciidoctor = JRubyAsciidoctor()
    private val space = " "

    override fun translate(poFile: PoFile, srcLang: String, dstLang: String): PoFile {
        val messages = poFile.messages
        val targetMessages = messages.filter(this::requiresTranslation)
        val targetMessageStrings = targetMessages.map { message -> message.messageId }.map { preProcess(it) }
        val translatedStrings = translator.translate(targetMessageStrings, srcLang, dstLang).map { postProcess(it) }
        translatedStrings.forEachIndexed { index, item -> targetMessages[index].also { it.messageString = item; } }

        messages.filter(this::isHeader).filter { it.messageString.isEmpty() }.forEach{ translateHeader(it, srcLang, dstLang)}

        return PoFile(messages)
    }

    private fun translateHeader(message: PoMessage, srcLang: String, dstLang: String){
        message.messageString = translateHeader(message.messageId, srcLang, dstLang)
    }

    internal fun translateHeader(message: String, srcLang: String, dstLang: String): String{
        val titleRegex = Regex("""^(title:)(.*)\n""", RegexOption.MULTILINE)
        val synopsisRegex = Regex("""^(synopsis:)(.*)\n""", RegexOption.MULTILINE)
        val title = titleRegex.find(message)!!.groupValues[2].trim()
        val synopsis = synopsisRegex.find(message)!!.groupValues[2].trim()
        val translated = translator.translate(listOf(title, synopsis), srcLang, dstLang)
        val titleTranslated = translated[0]
        val synopsisTranslated = translated[1]
        var replaced = message
        replaced = titleRegex.replace(replaced, "title: %s\n".format(titleTranslated))
        replaced = synopsisRegex.replace(replaced, "synopsis: %s\n".format(synopsisTranslated))
        return replaced
    }

    private fun requiresTranslation(message: PoMessage): Boolean{
        if(message.messageString.isNotEmpty()){
            return false
        }
        if(isHeader(message)){
            return false
        }
        return when(message.type){
            MessageType.DelimitedBlock -> false
            MessageType.TargetForMacroImage -> false
            else -> true
        }
    }

    private fun isHeader(message: PoMessage): Boolean {
        val lines = message.messageId.lines()
        return  lines.any { line -> line.startsWith("layout:") } &&
                lines.any { line -> line.startsWith("title:") } &&
                lines.any { line -> line.startsWith("date:") } &&
                lines.any { line -> line.startsWith("tags:") } &&
                lines.any { line -> line.startsWith("author:") }
    }

    internal fun preProcess(messageString: String): String {
        val options = HashMap<String, Any>()
        options.put("backend", "html")
        val asciidoc = asciidoctor.load(messageString, options)
        val html = asciidoc.convert()
        val doc = Jsoup.parseBodyFragment(html)
        return doc.body().children().first().children().html()
    }

    internal fun postProcess(messageString: String): String {
        val doc = Jsoup.parseBodyFragment(messageString)
        replaceToQuote(doc.body(), "em", " _", "_ ")
        replaceToQuote(doc.body(), "strong", " *", "* ")
        replaceToQuote(doc.body(), "code", " `", "` ")
        replaceToQuote(doc.body(), "monospace", " `", "` ")
        replaceToQuote(doc.body(), "superscript", " ^", "^ ")
        replaceToQuote(doc.body(), "subscript", " ~", "~ ")
        replaceLink(doc.body())
        return doc.body().html()
    }

    private fun replaceLink(element: Element) {
        if(element.tagName() == "a"){
            if(element.attr("class") == "bare"){
                val url = element.attr("href")
                val linkText = " %s ".format(url)
                element.replaceWith(TextNode(linkText))
            }
            else {
                val url = element.attr("href")
                val text = element.text()
                var attrsText = ""
                val attrs = element.attributes().filter { attr -> attr.key != "href" }.filterNot { attr -> attr.key == "rel" && attr.value == "noopener" }
                for (attr in attrs) {
                    attrsText += ", %s=%s".format(mapAttrKey(attr.key), attr.value)
                }
                val linkText = " %s[%s%s]".format(url, text, attrsText)
                element.replaceWith(TextNode(linkText))
            }
        }
        element.children().forEach(this::replaceLink)
    }

    private fun mapAttrKey(key: String): String{
        return when(key){
            "class" -> "role"
            "target" -> "window"
            else -> key
        }
    }

    private fun replaceToQuote(element: Element, tagName: String, openQuote: String, closeQuote: String){
        if(element.tagName() == tagName){
            val quotedString = openQuote + element.html() + closeQuote
            element.replaceWith(TextNode(quotedString))
        }
        element.children().forEach{
            replaceToQuote(it, tagName, openQuote, closeQuote)
        }
    }

}
