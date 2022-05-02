package net.sharplab.translator.app.setting

import org.eclipse.microprofile.config.inject.ConfigProperty
import java.util.*
import javax.enterprise.context.Dependent

@Dependent
class AsciiDocPoTranslatorSetting {
    @ConfigProperty(name = "translator.deepL.apiKey")
    var deepLApiKey: Optional<String> = Optional.empty()
    @ConfigProperty(name = "translator.language.source", defaultValue = "en")
    var defaultSrcLang: String? = null
    @ConfigProperty(name = "translator.language.destination", defaultValue = "ja")
    var defaultDstLang: String? = null
}
