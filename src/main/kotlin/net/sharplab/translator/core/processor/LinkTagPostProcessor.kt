package net.sharplab.translator.core.processor

import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

class LinkTagPostProcessor : TagPostProcessor{

    override fun postProcess(message: Element) {
        replaceLink(message)
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
                val linkText = " link:%s[%s%s]".format(url, text, attrsText)
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

}