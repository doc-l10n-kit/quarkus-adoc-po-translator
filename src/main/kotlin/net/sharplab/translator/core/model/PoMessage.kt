package net.sharplab.translator.core.model

import org.fedorahosted.tennera.jgettext.Message

@Suppress("ConvertSecondaryConstructorToPrimary")
class PoMessage{

    val message: Message

    constructor(message: Message) {
        this.message = message
    }

    val type: MessageType
        get(){
            return when {
                checkMessageType("type: Title =") -> MessageType.Title1
                checkMessageType("type: Title ==") -> MessageType.Title2
                checkMessageType("type: Title ===") -> MessageType.Title3
                checkMessageType("type: Plain Text") -> MessageType.PlainText
                checkMessageType("type: delimited block =") -> MessageType.DelimitedBlock1
                checkMessageType("type: delimited block ==") -> MessageType.DelimitedBlock2
                checkMessageType("type: delimited block ===") -> MessageType.DelimitedBlock3
                checkMessageType("type: delimited block -") -> MessageType.DelimitedBlock
                checkMessageType("type: Table") -> MessageType.Table
                checkMessageType("type: Target for macro image") -> MessageType.TargetForMacroImage
                else -> MessageType.None
            }
        }

    val messageId: String
        get() = message.msgid

    var messageString: String
        get() = message.msgstr
        set(value){
            message.msgstr = value
        }

    var fuzzy: Boolean
        get() = message.isFuzzy
        set(value){
            message.isFuzzy = value
        }

    private fun checkMessageType(type: String): Boolean {
        return this.message.extractedComments.any { comment -> comment == type }
    }
}