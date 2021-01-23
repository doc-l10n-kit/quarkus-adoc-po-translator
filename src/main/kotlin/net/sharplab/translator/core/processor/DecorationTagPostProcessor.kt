package net.sharplab.translator.core.processor

import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

class DecorationTagPostProcessor(private val tagName: String, private val openQuote: String, private val closeQuote: String) : TagPostProcessor{

    override fun postProcess(message: Element) {
        replaceToQuote(message)
    }

    private fun replaceToQuote(element: Element){
        if(element.tagName() == tagName){
            var isSpaced = false
            val prev =element.previousSibling()
            if(prev is TextNode && prev.text().endsWith(" ")) {
                isSpaced = true
            }
            val quotedString = if(isSpaced){
                openQuote + element.html() + closeQuote
            } else{
                " " + openQuote + element.html() + closeQuote
            }
            element.replaceWith(TextNode(quotedString))
        }
        element.children().forEach{
            replaceToQuote(it)
        }
    }

}