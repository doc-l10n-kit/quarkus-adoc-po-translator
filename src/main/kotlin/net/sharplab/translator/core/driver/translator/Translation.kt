package net.sharplab.translator.core.driver.translator

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

@Suppress("ConvertSecondaryConstructorToPrimary")
class Translation {
    val text: String
    val to: String
    val alignment: String?
    val sentLen: Any?

    @JsonCreator
    constructor(@JsonProperty("text") text: String,@JsonProperty("to") to: String, @JsonProperty("alignment") alignment: String?, @JsonProperty("sentLen") sentLen: Any?) {
        this.text = text
        this.to = to
        this.alignment = alignment
        this.sentLen = sentLen
    }
}