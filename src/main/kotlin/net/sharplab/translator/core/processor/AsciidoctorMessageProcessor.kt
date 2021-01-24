package net.sharplab.translator.core.processor

import org.asciidoctor.jruby.internal.JRubyAsciidoctor
import org.jsoup.Jsoup

class AsciidoctorMessageProcessor {

    private val asciidoctor = JRubyAsciidoctor()

    fun preProcess(message: String): String {
        val options = HashMap<String, Any>()
        options["backend"] = "html"
        val asciidoc = asciidoctor.load(message, options)
        val html = asciidoc.convert()
        val doc = Jsoup.parseBodyFragment(html)
        return when (val first = doc.body().children().first()) {
            null -> ""
            else -> first.children().html()
        }
    }

    fun postProcess(message: String): String {
        val doc = Jsoup.parseBodyFragment(message)
        val body = doc.body()
        DecorationTagPostProcessor("em", "_", "_").postProcess(body)
        DecorationTagPostProcessor("strong", "*", "*").postProcess(body)
        DecorationTagPostProcessor("code", "`", "`").postProcess(body)
        DecorationTagPostProcessor("monospace", "`", "`").postProcess(body)
        DecorationTagPostProcessor("superscript", "^", "^").postProcess(body)
        DecorationTagPostProcessor("subscript", "~", "~").postProcess(body)
        LinkTagPostProcessor().postProcess(body)
        ImageTagPostProcessor().postProcess(body)
        return body.text()
    }

}