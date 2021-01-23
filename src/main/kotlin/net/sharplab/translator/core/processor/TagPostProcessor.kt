package net.sharplab.translator.core.processor

import org.jsoup.nodes.Element

interface TagPostProcessor {
    fun postProcess(message: Element)
}
