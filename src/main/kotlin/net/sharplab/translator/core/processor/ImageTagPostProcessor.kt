package net.sharplab.translator.core.processor

import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

class ImageTagPostProcessor : TagPostProcessor {

    override fun postProcess(message: Element) {
        replaceImage(message)
    }

    private fun replaceImage(element: Element) {
        if(element.tagName() == "span" && element.classNames().contains("image")){
            val imgTag = element.selectFirst("img")
            val src = imgTag.attr("src")
            val attrs = imgTag.attributes().filter { attr -> attr.key != "src" }
            val attrsText = attrs.map { "%s=\"%s\"".format(it.key, it.value) }.joinToString(", ")
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
        element.children().forEach(this::replaceImage)
    }

}