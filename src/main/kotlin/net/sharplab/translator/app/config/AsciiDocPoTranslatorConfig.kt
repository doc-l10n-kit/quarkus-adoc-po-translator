package net.sharplab.translator.app.config

import net.sharplab.translator.app.setting.AsciiDocPoTranslatorSetting
import net.sharplab.translator.core.driver.translator.DeepLTranslator
import net.sharplab.translator.core.driver.translator.GlobaleseTranslator
import net.sharplab.translator.core.driver.translator.MSTranslator
import net.sharplab.translator.core.driver.translator.Translator
import javax.enterprise.context.Dependent
import javax.enterprise.inject.Produces

@Dependent
class AsciiDocPoTranslatorConfig(private val asciiDocPoTranslatorSetting: AsciiDocPoTranslatorSetting) {

    @Produces
    fun translator(): Translator
    {
        return when (asciiDocPoTranslatorSetting.engine?.toLowerCase()){
            "globalese".toLowerCase() -> {
                val globaleseEndpoint = asciiDocPoTranslatorSetting.globaleseEndpoint
                val globaleseUsername = asciiDocPoTranslatorSetting.globaleseUsername
                val globalesePassword = asciiDocPoTranslatorSetting.globaleseApiKey
                val globaleseProjectId = asciiDocPoTranslatorSetting.globaleseProjectId
                GlobaleseTranslator(globaleseEndpoint!!, globaleseUsername!!, globalesePassword!!, globaleseProjectId!!)
            }
            "deepL".toLowerCase() -> {
                val deepLApiKey = asciiDocPoTranslatorSetting.deepLApiKey
                DeepLTranslator(deepLApiKey!!)
            }
            "msTranslator".toLowerCase() -> {
                val msTranslatorSubscriptionKey = asciiDocPoTranslatorSetting.msTranslatorSubscriptionKey
                val msTranslatorLocation = asciiDocPoTranslatorSetting.msTranslatorLocation
                val msTranslatorCategory = asciiDocPoTranslatorSetting.msTranslatorCategory
                MSTranslator(msTranslatorSubscriptionKey!!, msTranslatorLocation!!, msTranslatorCategory!!)
            }
            else -> throw RuntimeException("Configuration 'translator.engine' must be 'deepL' or 'msTranslator'.")
        }
    }
}
