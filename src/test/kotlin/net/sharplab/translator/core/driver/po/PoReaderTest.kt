package net.sharplab.translator.core.driver.po

import net.sharplab.translator.test.PoTestUtil
import org.asciidoctor.Asciidoctor
import org.asciidoctor.ast.Document
import org.asciidoctor.jruby.internal.JRubyAsciidoctor
import org.junit.jupiter.api.Test

internal class PoReaderTest {

    private val target = PoReader()

    @Test
    fun readTest(){
        val file = PoTestUtil.loadTestPoFile()
        target.read(file)
    }

    @Test
    fun test(){
        val asciidoctor: Asciidoctor = JRubyAsciidoctor()
        val options = HashMap<String, Any>()
        options.put("doctype", "inline")
        val document: Document = asciidoctor.load("By the way you can see that Quarkus is pretty well https://quarkus.io/guides/[documented,role=external,window=_blank].", options)
        document.toString()
    }

}