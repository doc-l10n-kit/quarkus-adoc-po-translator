package net.sharplab.translator.app.config

import net.sharplab.translator.app.setting.AsciiDocPoTranslatorSetting
import net.sharplab.translator.core.driver.translator.DeepLTranslator
import net.sharplab.translator.core.driver.translator.Translator
import javax.enterprise.context.Dependent
import javax.enterprise.inject.Produces

@Dependent
class AsciiDocPoTranslatorConfig(private val asciiDocPoTranslatorSetting: AsciiDocPoTranslatorSetting) {

    @Produces
    fun translator(): Translator
    {
        val deepLApiKey = asciiDocPoTranslatorSetting.deepLApiKey
        return DeepLTranslator(deepLApiKey.get())
    }
}
