package net.sharplab.translator.core.driver.translator.xliff

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
data class File(
        @field:XmlElement(name = "body", namespace = "urn:oasis:names:tc:xliff:document:1.2") var body: Body? = null,
        @field:XmlAttribute(name = "source-language") var sourceLanguage: String? = null,
        @field:XmlAttribute(name = "target-language") var targetLanguage: String? = null)
