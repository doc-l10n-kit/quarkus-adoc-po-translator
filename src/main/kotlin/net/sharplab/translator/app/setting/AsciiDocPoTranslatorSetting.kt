package net.sharplab.translator.app.setting

import org.eclipse.microprofile.config.inject.ConfigProperty
import java.util.*
import javax.enterprise.context.Dependent

@Dependent
class AsciiDocPoTranslatorSetting {
    @ConfigProperty(name = "translator.engine")
    var engine: String = "deepL"
    @ConfigProperty(name = "translator.globalese.endpoint")
    var globaleseEndpoint: Optional<String> = Optional.empty()
    @ConfigProperty(name = "translator.globalese.username")
    var globaleseUsername: Optional<String> = Optional.empty()
    @ConfigProperty(name = "translator.globalese.apiKey")
    var globaleseApiKey: Optional<String> = Optional.empty()
    @ConfigProperty(name = "translator.globalese.projectId")
    var globaleseProjectId: Optional<Int> = Optional.empty()
    @ConfigProperty(name = "translator.deepL.apiKey")
    var deepLApiKey: Optional<String> = Optional.empty()
    @ConfigProperty(name = "translator.msTranslator.subscriptionKey")
    var msTranslatorSubscriptionKey: Optional<String> = Optional.empty()
    @ConfigProperty(name = "translator.msTranslator.location")
    var msTranslatorLocation: Optional<String> = Optional.empty()
    @ConfigProperty(name = "translator.msTranslator.category")
    var msTranslatorCategory: Optional<String> = Optional.empty()
    @ConfigProperty(name = "translator.language.source", defaultValue = "en")
    var defaultSrcLang: String? = null
    @ConfigProperty(name = "translator.language.destination", defaultValue = "ja")
    var defaultDstLang: String? = null
}
