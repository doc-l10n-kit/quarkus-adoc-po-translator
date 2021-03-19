package net.sharplab.translator.core.driver.translator.xliff

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "xliff", namespace = "urn:oasis:names:tc:xliff:document:1.2")
data class Xliff(@field:XmlElement(name = "file", namespace = "urn:oasis:names:tc:xliff:document:1.2") var file: File? = null)

