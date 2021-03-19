package net.sharplab.translator.app.setting

import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.Dependent

@Dependent
class AsciiDocPoTranslatorSetting {
    @ConfigProperty(name = "translator.engine")
    var engine: String? = null
    @ConfigProperty(name = "translator.globalese.endpoint")
    var globaleseEndpoint: String? = null
    @ConfigProperty(name = "translator.globalese.username")
    var globaleseUsername: String? = null
    @ConfigProperty(name = "translator.globalese.apiKey")
    var globaleseApiKey: String? = null
    @ConfigProperty(name = "translator.globalese.projectId")
    var globaleseProjectId: Int? = null
    @ConfigProperty(name = "translator.deepL.apiKey")
    var deepLApiKey: String? = null
    @ConfigProperty(name = "translator.msTranslator.subscriptionKey")
    var msTranslatorSubscriptionKey: String? = null
    @ConfigProperty(name = "translator.msTranslator.location")
    var msTranslatorLocation: String? = null
    @ConfigProperty(name = "translator.msTranslator.category")
    var msTranslatorCategory: String? = null
    @ConfigProperty(name = "translator.language.source", defaultValue = "en")
    var defaultSrcLang: String? = null
    @ConfigProperty(name = "translator.language.destination", defaultValue = "ja")
    var defaultDstLang: String? = null
}
