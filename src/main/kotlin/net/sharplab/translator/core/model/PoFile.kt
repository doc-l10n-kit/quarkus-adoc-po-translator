package net.sharplab.translator.core.model

@Suppress("ConvertSecondaryConstructorToPrimary")
class PoFile {

    val messages: List<PoMessage>

    constructor(messages: List<PoMessage>){
        this.messages = messages
    }


}
