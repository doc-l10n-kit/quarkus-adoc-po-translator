package net.sharplab.translator.core.driver.translator.xliff

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlValue

@XmlAccessorType(XmlAccessType.NONE)
data class Source (@field:XmlValue var text: String? = null)
