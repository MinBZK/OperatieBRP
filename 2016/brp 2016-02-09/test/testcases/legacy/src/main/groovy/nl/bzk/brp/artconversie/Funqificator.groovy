package nl.bzk.brp.artconversie

import static nl.bzk.brp.artconversie.model.art.Kolommen.Alternatieve_Properties
import static nl.bzk.brp.artconversie.model.art.Kolommen.DB_Query
import static nl.bzk.brp.artconversie.model.art.Kolommen.DB_Response_Query
import static nl.bzk.brp.artconversie.model.art.Kolommen.Post_Query
import static nl.bzk.brp.artconversie.model.art.Kolommen.Pre_Query
import static nl.bzk.brp.artconversie.model.art.Kolommen.Request_Template
import static nl.bzk.brp.artconversie.model.art.Kolommen.Soap_Response_Query
import static nl.bzk.brp.artconversie.model.art.Kolommen.TestGeval
import static nl.bzk.brp.artconversie.model.art.Kolommen.Volgnummer

import nl.bzk.brp.artconversie.conversie.ArtConverter
import nl.bzk.brp.artconversie.conversie.TemplateConverter
import nl.bzk.brp.artconversie.conversie.XlsDataConverter
import nl.bzk.brp.artconversie.conversie.ApachePoiConverter
import nl.bzk.brp.artconversie.model.art.ArtSheet
import nl.bzk.brp.artconversie.model.art.ExcelRegel
import nl.bzk.brp.artconversie.model.jbehave.Story
import nl.bzk.brp.artconversie.reader.ApachePoiArtControlReader
import nl.bzk.brp.artconversie.writer.JBehaveStoryWriter
import org.apache.commons.io.FileUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 */
class Funqificator {
    private final Logger logger = LoggerFactory.getLogger(Funqificator)

    File input
    File output
    File toDir
    File xls
    private Properties artProps

    Funqificator(String outputPath) {
        output = new File(outputPath)
    }

    /**
     * Vertaal een ART-Excel-bestand naar JBehave-story.
     * Verplaats daarna de benodigde bestanden.
     *
     * @param inputPath pad naar een ART
     *
     * @return {@code true} als de conversie is geslaagd
     */
    boolean funqify(String inputPath) {
        logger.info 'Funqifing {}', inputPath

        input = new File(inputPath)

        readProperties()
        xls = new File(input, artProps.getProperty('art_input_file'))

        assert xls != null

        toDir = new File(output, "testcases/${xls.name.split('\\.')[0]}")
        ArtSheet artSheet = new ApachePoiArtControlReader(xls, artProps.getProperty('art_input_sheet')).read()

        assert artSheet != null

        Story story = new ArtConverter(artProps, input).convert(artSheet)
        new JBehaveStoryWriter(new File(output, "testcases/${xls.name.split('\\.')[0]}")).write(story)

        moveResources(artSheet)

        return true
    }

    /**
     * Verplaats benodigde bestanden voor een ART naar de locatie voor JBehave.
     * @param excelRegels
     */
    private void moveResources(final ArtSheet excelRegels) {
        logger.info('Copying files')

        FileUtils.copyFileToDirectory(xls, new File(output, 'templatedata'))
        if (artProps['request_template_file']) {
            FileUtils.copyFileToDirectory(new File(input, artProps['request_template_file']), new File(output, 'berichtdefinities'))
        }

        excelRegels.each { ExcelRegel r ->
            if (r[Request_Template]) {
                FileUtils.copyFileToDirectory(new File(input, r[Request_Template]), new File(output, 'berichtdefinities'))
            }

            [DB_Query, Pre_Query, Post_Query].each {
                if (r[it]?.endsWith('.sql')) {
                    FileUtils.copyFileToDirectory(new File(input, "../afhankelijkheden/SQL/${r[it]}"), new File(output, 'sqltemplates'))
                }
            }

            if (r[Alternatieve_Properties]) {
                def props = new Properties()
                def propFile = new File(input, "${r[Alternatieve_Properties]}/test.properties")
                props.load(new FileReader(propFile))

                FileUtils.copyFileToDirectory(new File(input, props['request_template_file']), new File(output, 'berichtdefinities'))
                FileUtils.copyFileToDirectory(new File(propFile.parent, props['art_input_file']), new File(output, 'templatedata'))

                if (r[DB_Response_Query]) {
                    def from = new File(propFile.parent, "ExpectedResults/data/${r[TestGeval]}-${r[Volgnummer]}-dataresponse.xml")
                    if (from.exists())
                        FileUtils.copyFileToDirectory(from, new File(toDir, 'data/'))
                }
                if (r[Soap_Response_Query]) {
                    def from = new File(propFile.parent, "ExpectedResults/response/${r[TestGeval]}-${r[Volgnummer]}-soapresponse.xml")
                    if (from.exists())
                        FileUtils.copyFileToDirectory(from, new File(toDir, 'response/'))
                }
            }
        }

        def expectedDir = new File(input, 'ExpectedResults/')
        try { FileUtils.copyDirectoryToDirectory(new File(expectedDir, 'data/'), toDir) } catch (IOException e) { logger.info('Geen eigen expected data') }
        try { FileUtils.copyDirectoryToDirectory(new File(expectedDir, 'response/'), toDir) } catch (IOException e) { logger.info('Geen eigen expected responses') }
    }

    /**
     * Modificeer input/templates/expecteds van ART formaat naar Freemarker formaat.
     *
     * @return
     */
    boolean freemarkify() {
        processTemplates(new File(output, 'berichtdefinities'))
        processTemplates(new File(output, 'sqltemplates'))
        processTemplates(new File(output, 'testcases'))

        processExcels(new File(output, 'templatedata'))

        return true
    }

    /**
     * Vertaal SQL en XML naar freemarker formaat.
     * @param parent
     */
    private void processTemplates(final File parent) {
        logger.info 'Processing files in {}', parent.canonicalPath
        def converter = new TemplateConverter()
        parent.eachFileRecurse {
            if (it.name.endsWith('.sql') || it.name.endsWith('.xml'))
                converter.convert(it)
        }
    }

    /**
     * Vertaal XLS naar freemarker formaat (keys in data kolom).
     * @param parent
     */
    private void processExcels(final File parent) {
        XlsDataConverter converter = new ApachePoiConverter()

        parent.eachFileRecurse {
            if (it.name.endsWith('.xls')) {
                logger.info 'Freemarkifying {}', it.name
                try {
                    converter.convert(it)
                } catch (Exception e) {
                    e.printStackTrace()
                }
            }
        }
    }

    private void readProperties() {
        artProps = new Properties()

        def propFile = new File(input, 'test.properties')
        if (propFile.exists()) {
            artProps.load(new FileReader(propFile))
        } else {
            throw new FileNotFoundException("De ART ${input.absolutePath} is lokaal beschikbaar")
        }
    }
}
