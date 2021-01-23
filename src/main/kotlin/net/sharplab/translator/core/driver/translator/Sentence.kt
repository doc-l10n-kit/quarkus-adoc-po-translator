package net.sharplab.translator.core.driver.translator

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

@Suppress("ConvertSecondaryConstructorToPrimary")
class Sentence {
    val translations: List<Translation>

    @JsonCreator
    constructor(@JsonProperty("translations") translations: List<Translation>) {
        this.translations = translations
    }
}
