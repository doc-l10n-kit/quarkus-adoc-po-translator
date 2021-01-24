package net.sharplab.translator.core.service

import net.sharplab.translator.core.driver.translator.Translator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PoTranslatorServiceImplTest{

    private val poTranslatorServiceImplTest = PoTranslatorServiceImpl(object : Translator {
        override fun translate(texts: List<String>, srcLang: String, dstLang: String): List<String> {
            TODO("not implemented")
        }
    })

//    @Test
//    fun translateHeader_test(){
//        val header =
//"""layout: post\n
//title: Extension codestarts - A new way to learn & discover Quarkus\n
//date: 2020-12-07\n
//tags: extensions codestarts quickstart\n
//synopsis: All our tooling has been updated and can now generate Quarkus application with example code (Extension Codestarts) showing the true power of the selected extensions...\n
//author: adamevin\n"""
//        val result = poTranslatorServiceImplTest.translateHeader(header, "en", "ja")
//        assertThat(result).isNotNull();
//    }

}