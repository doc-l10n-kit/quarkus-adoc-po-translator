package net.sharplab.translator.core.service

import net.sharplab.translator.core.model.PoFile
import net.sharplab.translator.core.processor.DecorationTagPostProcessor
import net.sharplab.translator.core.processor.ImageTagPostProcessor
import net.sharplab.translator.core.processor.LinkTagPostProcessor
import org.asciidoctor.jruby.internal.JRubyAsciidoctor
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

class PoNormalizerServiceImpl : PoNormalizerService {

    private val asciidoctor = JRubyAsciidoctor()

    fun toHtmlString(message: String): String {
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

//    private fun parseHtmlString(html: String): Element{
//        val doc = Jsoup.parseBodyFragment(html)
//        val body = doc.body()
//    }

//    fun toAsciidoctorString(element: Element): String {
//        DecorationTagPostProcessor("em", "_", "_").postProcess(element)
//        DecorationTagPostProcessor("strong", "*", "*").postProcess(element)
//        DecorationTagPostProcessor("code", "`", "`").postProcess(element)
//        DecorationTagPostProcessor("monospace", "`", "`").postProcess(element)
//        DecorationTagPostProcessor("superscript", "^", "^").postProcess(element)
//        DecorationTagPostProcessor("subscript", "~", "~").postProcess(element)
//        LinkTagPostProcessor().postProcess(element)
//        ImageTagPostProcessor().postProcess(element)
//        return element.text()
//    }

    override fun normalize(poFile: PoFile): PoFile {
        val str = ""
        val htmlString = toHtmlString(str)
        TODO("")
//        val element = parseHtmlString(htmlString)
//
//
//
//        val asciidoctorString = toAsciidoctorString(element)
//        TODO("")
    }

    fun normalize(element: Element){
        if(element.tagName() == "span" && element.classNames().contains("image")){
            val imgTag = element.selectFirst("img")
            val src = imgTag.attr("src")
            val attrs = imgTag.attributes().filter { attr -> attr.key != "src" }
            val attrsText = attrs.joinToString(", ") { "%s=\"%s\"".format(it.key, it.value) }
            var imageText = "image:%s[%s]".format(src, attrsText)

            val prev =element.previousSibling()
            val next =element.nextSibling()
            val isPrevNonSpacedTextNode= prev is TextNode && prev.text().endsWith(" ")
            val isNextNonSpacedTextNode= next is TextNode && next.text().startsWith(" ")

            if(isPrevNonSpacedTextNode){
                imageText = " $imageText"
            }
            if(isNextNonSpacedTextNode){
                imageText = "$imageText "
            }
            element.replaceWith(TextNode(imageText))
        }
        element.children().forEach(this::normalize)
    }

}