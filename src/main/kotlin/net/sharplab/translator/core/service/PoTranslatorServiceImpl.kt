package net.sharplab.translator.core.service

import net.sharplab.translator.core.driver.translator.Translator
import net.sharplab.translator.core.model.MessageType
import net.sharplab.translator.core.model.PoFile
import net.sharplab.translator.core.model.PoMessage
import net.sharplab.translator.core.processor.AsciidoctorMessageProcessor
import org.jboss.logging.Logger
import javax.enterprise.context.Dependent

@Dependent
class PoTranslatorServiceImpl(private val translator: Translator) : PoTranslatorService {

    private val logger = Logger.getLogger(PoTranslatorServiceImpl::class.java)
    private val messageProcessor = AsciidoctorMessageProcessor()

    override fun translate(poFile: PoFile, srcLang: String, dstLang: String): PoFile {
        val messages = poFile.messages
        val translationTargets = messages.filter{ requiresTranslation(it)}

        val blogHeaderMessages = translationTargets.filter { isBlogHeader(it) }
        val nonBlogHeaderMessages = translationTargets.filterNot { isBlogHeader(it) }

        translateBlogHeaders(blogHeaderMessages, srcLang, dstLang)
        translateMessages(nonBlogHeaderMessages, srcLang, dstLang)

        return PoFile(messages)
    }

    private fun translate(messages: List<String>, srcLang: String, dstLang: String): List<String> {
        val preProcessedMessages = messages.map { messageProcessor.preProcess(it) }
        val processedMessages = translator.translate(preProcessedMessages, srcLang, dstLang)
        return processedMessages.map { messageProcessor.postProcess(it) }
    }

    private fun translateMessages(messages: List<PoMessage>, srcLang: String, dstLang: String){
        val translatedStrings = translate(messages.map { it.messageId }, srcLang, dstLang)
        translatedStrings.forEachIndexed { index, item -> messages[index].also {
            if(item.isNotEmpty()){
                it.messageString = item
            }
            else{
                it.messageString = it.messageId
            }
            it.fuzzy = true
        } }
    }

    private fun translateBlogHeaders(messages: List<PoMessage>, srcLang: String, dstLang: String){
        messages.forEach { message ->
            message.messageString = translateBlogHeader(message.messageId, srcLang, dstLang)
            message.fuzzy = true
        }
    }

    private fun translateBlogHeader(message: String, srcLang: String, dstLang: String): String{
        val titleRegex = Regex("""^(title:)(.*)\n""", RegexOption.MULTILINE)
        val synopsisRegex = Regex("""^(synopsis:)(.*)\n""", RegexOption.MULTILINE)
        val titleFindResult = titleRegex.find(message)
        val synopsisFindResult = synopsisRegex.find(message)
        var title = ""
        var synopsis = ""
        if(titleFindResult!=null){
            title = titleFindResult.groupValues[2].trim()
        }
        if(synopsisFindResult!=null){
            synopsis = synopsisFindResult.groupValues[2].trim()
        }
        val translated = translator.translate(listOf(title, synopsis), srcLang, dstLang)
        val titleTranslated = translated[0]
        val synopsisTranslated = translated[1]
        var replaced = message
        if(titleFindResult!=null){
            replaced = titleRegex.replace(replaced, "title: %s\n".format(titleTranslated))
        }
        if(synopsisFindResult!=null){
            replaced = synopsisRegex.replace(replaced, "synopsis: %s\n".format(synopsisTranslated))
        }
        return replaced
    }

    private fun requiresTranslation(message: PoMessage): Boolean{
        if(message.messageString.isNotEmpty()){
            return false
        }
        return when(message.type){
            MessageType.DelimitedBlock -> false
            MessageType.TargetForMacroImage -> false
            else -> true
        }
    }

    private fun isBlogHeader(message: PoMessage): Boolean {
        val lines = message.messageId.lines()
        return  lines.any { line -> line.startsWith("layout:") } &&
                lines.any { line -> line.startsWith("title:") } &&
                lines.any { line -> line.startsWith("date:") } &&
                lines.any { line -> line.startsWith("tags:") } &&
                lines.any { line -> line.startsWith("author:") }
    }


}
