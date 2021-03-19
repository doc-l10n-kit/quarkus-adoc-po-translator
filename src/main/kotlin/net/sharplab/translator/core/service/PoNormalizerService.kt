package net.sharplab.translator.core.service

import net.sharplab.translator.core.model.PoFile

interface PoNormalizerService {

    fun normalize(poFile: PoFile): PoFile
}