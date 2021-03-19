package net.sharplab.translator.core.driver.translator.xliff

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlValue

@XmlAccessorType(XmlAccessType.NONE)
data class Target(@field:XmlValue var text: String? = null, @field:XmlAttribute(name = "state") var state: String? = null, @field:XmlAttribute(name = "state-qualifier") var stateQualifier: String? = null)
