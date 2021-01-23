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
            val alt = imgTag.attr("alt")
            val imageText = " image:%s[alt=%s]".format(src, alt)
            element.replaceWith(TextNode(imageText))
        }
        element.children().forEach(this::replaceImage)
    }

}