package net.sharplab.translator.core.processor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class AsciidoctorMessageProcessorTest {

    private val target = AsciidoctorMessageProcessor()

    @Test
    fun preProcess_decorationTag_test(){
        val messageString = target.preProcess("This is a link:https://github.com/webauthn4j[link], to _webauthn4j_ GitHub org.")
        assertThat(messageString).isEqualTo("This is a <a href=\"https://github.com/webauthn4j\">link</a>, to <em>webauthn4j</em> GitHub org.")
    }

    @Disabled
    @Test
    fun postProcess_decorationTag_test(){
        val messageString = target.postProcess("これは、<em>webauthn4j</em>GitHub組織への<a href=\"https://github.com/webauthn4j\" window=\"_blank\">リンク</a>です。")
        assertThat(messageString).isEqualTo("これは、 _webauthn4j_ GitHub組織への link:https://github.com/webauthn4j[リンク, window=\"_blank\"] です。")
    }

    @Test
    fun preProcess_linkTag_test(){
        val result = target.preProcess("<<test,test>>")
        assertThat(result).isEqualTo("<a href=\"#test\">test</a>")
    }

    @Test
    fun postProcess_linkTag_test(){
        val result = target.postProcess("<a href=\"#test\">test</a>")
        assertThat(result).isEqualTo("link:#test[test]")
    }

    @Test
    fun preProcess_linkTag_test2(){
        val result = target.preProcess("You may wonder about Reactive Streams (https://www.reactive-streams.org/).")
        assertThat(result).isEqualTo("You may wonder about Reactive Streams (<a href=\"https://www.reactive-streams.org/\" class=\"bare\">https://www.reactive-streams.org/</a>).")
    }

    @Test
    fun postProcess_linkTag_test2(){
        val result = target.postProcess("You may wonder about Reactive Streams (<a href=\"https://www.reactive-streams.org/\" class=\"bare\">https://www.reactive-streams.org/</a>).")
        assertThat(result).isEqualTo("You may wonder about Reactive Streams ( https://www.reactive-streams.org/ ).")
    }

    @Test
    fun postProcess_linkTag_test3(){
        val result = target.postProcess("<a href=\"#bootstrapping-the-project\">Bootstrappingプロジェクト</a>以降の手順に沿って、ステップバイステップでアプリを作成していくことをお勧めします。")
        assertThat(result).isEqualTo("link:#bootstrapping-the-project[Bootstrappingプロジェクト] 以降の手順に沿って、ステップバイステップでアプリを作成していくことをお勧めします。")
    }

    @Test
    fun preProcess_imageTag(){
        val result = target.preProcess("image:quarkus-reactive-stack.png[alt=Quarkus is based on a reactive engine, 50%]")
        assertThat(result).isEqualTo("<span class=\"image\"><img src=\"quarkus-reactive-stack.png\" alt=\"Quarkus is based on a reactive engine\" width=\"50%\"></span>")
    }

    @Disabled
    @Test
    fun postProcess_imageTag(){
        val result = target.postProcess("<span class=\"image\"><img src=\"quarkus-reactive-stack.png\" alt=\"Quarkus is based on a reactive engine\" width=\"50%\"></span>")
        assertThat(result).isEqualTo("image:quarkus-reactive-stack.png[alt=\"Quarkus is based on a reactive engine\", width=\"50%\"]")
    }

    @Test
    fun preProcess(){
        val result = target.preProcess("(>_<)")
        assertThat(result).isEqualTo("(&gt;_&lt;)")
    }

    @Disabled
    @Test
    fun postProcess(){
        val result = target.postProcess("<code>(&gt;_&lt;)</code>")
        assertThat(result).isEqualTo(" `(>_<)` ")
    }
}
