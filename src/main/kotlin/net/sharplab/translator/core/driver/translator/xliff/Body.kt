package net.sharplab.translator.core.driver.translator.xliff

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement


@XmlAccessorType(XmlAccessType.NONE)
data class Body(@field:XmlElement(name = "trans-unit", namespace = "urn:oasis:names:tc:xliff:document:1.2") var transUnits: List<TransUnit>? = null)