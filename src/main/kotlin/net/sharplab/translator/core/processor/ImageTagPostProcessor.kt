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
            var imageText = "image:%s[alt=%s]".format(src, alt)

            val prev =element.previousSibling()
            val next =element.nextSibling()
            val isPrevSpaced= prev is TextNode && prev.text().endsWith(" ")
            val isNextSpaced= next is TextNode && next.text().startsWith(" ")

            if(!isPrevSpaced){
                imageText = " $imageText"
            }
            if(!isNextSpaced){
                imageText = "$imageText "
            }
            element.replaceWith(TextNode(imageText))
        }
        element.children().forEach(this::replaceImage)
    }

}