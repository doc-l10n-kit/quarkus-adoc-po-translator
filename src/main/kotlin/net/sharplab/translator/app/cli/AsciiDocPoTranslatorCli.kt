package net.sharplab.translator.app.cli

import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain
import net.sharplab.translator.app.service.AsciiDocPoTranslatorAppService
import net.sharplab.translator.app.setting.AsciiDocPoTranslatorSetting
import picocli.CommandLine
import picocli.CommandLine.IFactory
import java.io.File
import javax.inject.Inject

@QuarkusMain
@CommandLine.Command(name = "quarkus-adoc-po-translator", subcommands = [AsciiDocPoTranslatorCli.TranslateCommand::class])
class AsciiDocPoTranslatorCli : QuarkusApplication {

    @Inject
    var factory: IFactory? = null

    @CommandLine.Command(name = "translate")
    class TranslateCommand(private val asciiDocPoTranslatorAppService: AsciiDocPoTranslatorAppService, private val asciiDocPoTranslatorSetting: AsciiDocPoTranslatorSetting) : Runnable {

        @CommandLine.Parameters(description = ["file path"])
        private var path: List<File>? = null
        @CommandLine.Option(order = 2, names = ["--srcLang"], description = ["source language"])
        private var srcLang: String? = null
        @CommandLine.Option(order = 3, names = ["--dstLang"], description = ["destination language"])
        private var dstLang: String? = null
        @CommandLine.Option(order = 4, names = ["--asciidoctor"], description = ["asciidoctor"])
        private var asciidoctor = true
        @CommandLine.Option(order = 9, names = ["--help", "-h"], description = ["print help"], usageHelp = true)
        private var help = false

        override fun run() {
            val filePath = path?: throw IllegalArgumentException("path must be provided")
            val resolvedSrcLang = srcLang ?: asciiDocPoTranslatorSetting.defaultSrcLang ?: throw IllegalArgumentException("srcLang must be provided")
            val resolvedDstLang = dstLang ?: asciiDocPoTranslatorSetting.defaultDstLang ?: throw IllegalArgumentException("dstLang must be provided")
            filePath.forEach {
                asciiDocPoTranslatorAppService.translateAsciiDocPoFile(it, resolvedSrcLang, resolvedDstLang, asciidoctor)
            }
        }

    }

    @CommandLine.Command(name = "normalize")
    class NormalizeCommand(private val asciiDocPoTranslatorAppService: AsciiDocPoTranslatorAppService, private val asciiDocPoTranslatorSetting: AsciiDocPoTranslatorSetting) : Runnable {

        @CommandLine.Parameters(description = ["file path"])
        private var path: List<File>? = null
        @CommandLine.Option(order = 9, names = ["--help", "-h"], description = ["print help"], usageHelp = true)
        private var help = false

        override fun run() {
            val filePath = path?: throw IllegalArgumentException("path must be provided")
            filePath.forEach {
                //asciiDocPoTranslatorAppService.translateAsciiDocPoFile(it)
            }
        }

    }

    @Throws(Exception::class)
    override fun run(vararg args: String?): Int {
        val commandLine = CommandLine(this, factory)
        commandLine.isCaseInsensitiveEnumValuesAllowed = true
        return commandLine.execute(*args)
    }

}