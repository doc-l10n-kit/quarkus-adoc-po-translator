package net.sharplab.translator.core.driver.translator.xliff

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
data class TransUnit(@field:XmlElement(namespace = "urn:oasis:names:tc:xliff:document:1.2", name = "source") var source: Source? = null,
                     @field:XmlElement(namespace = "urn:oasis:names:tc:xliff:document:1.2", name = "target") var target: Target? = null)
