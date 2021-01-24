package net.sharplab.translator.core.processor

import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

class DecorationTagPostProcessor(private val tagName: String, private val openQuote: String, private val closeQuote: String) : TagPostProcessor{

    override fun postProcess(message: Element) {
        replaceToQuote(message)
    }

    private fun replaceToQuote(element: Element){
        if(element.tagName() == tagName){
            val prev =element.previousSibling()
            val next =element.nextSibling()
            val isPrevSpaced= prev is TextNode && prev.text().endsWith(" ")
            val isNextSpaced= next is TextNode && next.text().startsWith(" ")

            val openStr = when {
                isPrevSpaced -> openQuote
                else -> " $openQuote"
            }
            val closeStr = when {
                isNextSpaced -> closeQuote
                else -> "$closeQuote "
            }
            element.replaceWith(TextNode(openStr + element.wholeText() + closeStr))
        }
        element.children().forEach{
            replaceToQuote(it)
        }
    }

}