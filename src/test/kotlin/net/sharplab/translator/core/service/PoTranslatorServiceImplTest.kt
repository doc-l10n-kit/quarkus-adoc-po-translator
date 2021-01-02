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

    @Test
    fun preProcess_test(){
        val messageString = poTranslatorServiceImplTest.preProcess("This is a link:https://github.com/webauthn4j[link], to _webauthn4j_ GitHub org.")
        assertThat(messageString).isEqualTo("This is a <a href=\"https://github.com/webauthn4j\">link</a>, to <em>webauthn4j</em> GitHub org.")
    }

    @Test
    fun postProcess_test(){
        val messageString = poTranslatorServiceImplTest.postProcess("これは、<em>webauthn4j</em>GitHub組織への<a href=\"https://github.com/webauthn4j\" window=\"_blank\">リンク</a>です。")
        assertThat(messageString).isEqualTo("これは、 _webauthn4j_ GitHub組織への https://github.com/webauthn4j[リンク, window=\"_blank\"]です。")
    }
}