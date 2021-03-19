package net.sharplab.translator.core.driver.translator

import net.sharplab.translator.core.driver.translator.xliff.*
import net.sharplab.translator.core.driver.translator.xliff.Target
import net.sharplab.translator.generated.api.ProjectsApi
import net.sharplab.translator.generated.api.TranslationFilesApi
import net.sharplab.translator.generated.model.CreateProject
import net.sharplab.translator.generated.model.CreateTranslationFile
import net.sharplab.translator.generated.model.TranslationFile
import org.jboss.logging.Logger
import org.openapitools.client.infrastructure.ApiClient
import org.openapitools.client.infrastructure.ClientError
import org.openapitools.client.infrastructure.ClientException
import java.io.StringReader
import java.io.StringWriter
import javax.xml.bind.JAXB


class GlobaleseTranslator(endpoint: String, username: String, apiKey: String, private val projectId: Int) : Translator {

    private val logger = Logger.getLogger(GlobaleseTranslator::class.java)

    private val translationFilesApi = TranslationFilesApi(endpoint)
    private val projectsApi = ProjectsApi(endpoint)

    init {
        ApiClient.username = username
        ApiClient.password = apiKey
    }

    override fun translate(texts: List<String>, srcLang: String, dstLang: String): List<String> {

        val body = toXliff(texts, srcLang, dstLang)
        if(texts.isEmpty()){
            return listOf()
        }

        val createTranslationFile = CreateTranslationFile(projectId, "quarkus-adoc-po-translator-working-file.xliff", srcLang, dstLang)
        val translationFilePostResponse = translationFilesApi.translationFilesPost(createTranslationFile)
        if(translationFilePostResponse.id == null){
            throw RuntimeException("translationFile id is null")
        }
        try{
            translationFilesApi.translationFilesIdPost(translationFilePostResponse.id, body)
            translationFilesApi.translationFilesIdTranslatePost(translationFilePostResponse.id)
            do {
                val translationFilesIdGetResponse = translationFilesApi.translationFilesIdGet(translationFilePostResponse.id)
                Thread.sleep(1000)
            } while (translationFilesIdGetResponse.status != TranslationFile.Status.translated)
            val translatedFile = translationFilesApi.translationFilesIdDownloadGet(translationFilePostResponse.id, "translated", false)
            val translatedXliff = parseXliff(translatedFile)
            return translatedXliff.file?.body?.transUnits?.map { item -> item.target?.text?: "" }?: listOf()
        }
        catch (e: ClientException){
            logger.error((e.response as ClientError<*>).body)
            throw e
        }
        catch (e: Exception){
            throw e
        }
        finally {
            translationFilesApi.translationFilesIdDelete(translationFilePostResponse.id)
        }
    }



    fun createProject(name:String, srcLang: String, dstLang: String, group: Int, engine: Int){
        val createProject = CreateProject(name, srcLang, dstLang, group, engine)
        val project = projectsApi.projectsPost(createProject)
        System.out.println(project.id)
    }

    fun toXliff(texts: List<String>, srcLang: String, dstLang: String): String{
        val transUnits = texts.map { text -> TransUnit(Source(text), Target("", "needs-translation")) }
        val xliff = Xliff(File(Body(transUnits), srcLang, dstLang))
        val writer = StringWriter()
        JAXB.marshal(xliff, writer)
        return writer.toString()
    }

    fun parseXliff(source: String): Xliff{
        val reader = StringReader(source)
        return JAXB.unmarshal(reader, Xliff::class.java)
    }

}
