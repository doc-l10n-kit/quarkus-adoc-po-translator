package net.sharplab.translator.core.driver.translator

import net.sharplab.translator.core.driver.translator.xliff.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GlobaleseTranslatorTest {

    private val target = GlobaleseTranslator("https://dummy", "dummy", "dummy", 0)


    @Test
    fun toXliff_test() {
        val xliff = target.toXliff(listOf("Is this a pen?", "No, that is an apple."), "en", "ja")
        assertThat(xliff).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<xliff xmlns=\"urn:oasis:names:tc:xliff:document:1.2\">\n" +
                "    <file source-language=\"en\" target-language=\"ja\">\n" +
                "        <body>\n" +
                "            <trans-unit>\n" +
                "                <source>Is this a pen?</source>\n" +
                "                <target state=\"needs-translation\"></target>\n" +
                "            </trans-unit>\n" +
                "            <trans-unit>\n" +
                "                <source>No, that is an apple.</source>\n" +
                "                <target state=\"needs-translation\"></target>\n" +
                "            </trans-unit>\n" +
                "        </body>\n" +
                "    </file>\n" +
                "</xliff>\n")
    }

    @Test
    fun parseXliff(){
        val xliff = target.parseXliff("<xliff xmlns='urn:oasis:names:tc:xliff:document:1.2'><file source-language=\"en\" target-language=\"ja\"><body><trans-unit><source>Is this a pen?</source><target state=\"needs-review-translation\">これはペンですか？</target></trans-unit><trans-unit><source>No, that is an apple.</source><target state=\"needs-review-translation\">いいえ、これはリンゴです。</target></trans-unit></body></file></xliff>")
        assertThat(xliff).isEqualTo(
                Xliff(File(Body(listOf(
                        TransUnit(Source("Is this a pen?"), Target("これはペンですか？", "needs-review-translation")),
                        TransUnit(Source("No, that is an apple."), Target("いいえ、これはリンゴです。", "needs-review-translation"))
                )), "en", "ja")))
    }
}
