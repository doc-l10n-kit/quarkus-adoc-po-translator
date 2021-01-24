package net.sharplab.translator.core.processor

import org.jsoup.Jsoup
import org.jsoup.nodes.TextNode
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class LinkTagPostProcessorTest {

    private val target = LinkTagPostProcessor()

    @Test
    fun test(){
        val message = Jsoup.parseBodyFragment("")
        target.postProcess(message)
    }
}