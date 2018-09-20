package nl.brp.acceptance.internal

import nl.bzk.brp.funqmachine.datalezers.DataProcessor
import nl.bzk.brp.funqmachine.processors.TemplateProcessor
import nl.bzk.brp.funqmachine.processors.XmlProcessor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * builder voor het maken van een gevulde template.
 */
class TemplateBerichtBuilder {
    private final Logger logger = LoggerFactory.getLogger(TemplateBerichtBuilder)

    private String templateNaam
    private String handelingBasis
    private List<String> actieBases
    private String scenarioData


    TemplateBerichtBuilder(String template) {
        this.templateNaam = template
        this.actieBases = []
    }

    TemplateBerichtBuilder metHandelingBasis(String basis) {
        this.handelingBasis = basis
        return this
    }

    TemplateBerichtBuilder metActieBasis(String basis) {
        this.actieBases << basis
        return this
    }

    TemplateBerichtBuilder metActieBases(List<String> bases) {
        bases.each {
            actieBases << it
        }
    }

    TemplateBerichtBuilder metSpecifiekeData(String data) {
        this.scenarioData = data
        return this
    }

    String build() {
        final List<File> files = new ArrayList<>()

        files.add(geefFile("/berichtdefinities/${templateNaam}/${templateNaam}.yml"))
        files.add(geefFile("/berichtdefinities/${templateNaam}/${handelingBasis}/${handelingBasis}.yml"))
        actieBases.each {
            files.add(geefFile("/berichtdefinities/${templateNaam}/${handelingBasis}/acties/${it}.yml"))
        }
        files.add(geefFile("$scenarioData"))

        maakXmlVanTemplateEnTestdataFiles("/berichtdefinities/${templateNaam}/${templateNaam}.xml", files.findAll { it != null })
    }

    /**
     * Maakt van een template en een lijst databestanden een XML bericht.
     *
     * @param templateFilePath het pad naar de template file voor het bericht
     * @param files de lijst met databestanden
     * @return een xml dat een bericht voorstelt
     */
    private String maakXmlVanTemplateEnTestdataFiles(String templateFilePath, ArrayList<File> files) {
        final Map<String, Object> samengevoegdeDataMap = geefSamengevoegdeMapVoorFiles(files);

        TemplateProcessor templateProcessor = new TemplateProcessor();
        String xml = templateProcessor.verwerkTemplateBestand(templateFilePath, samengevoegdeDataMap)
        return new XmlProcessor().verwijderLegeElementen(xml)
    }

    /**
     * Geeft de samengevoegde map terug op basis van data die via verschillende files in volgorde over elkaar heen wordt gelegd.
     * @param files, de bestanden die de data invoegen
     * @return map met sameengevoegde data
     */
    private Map<String, Object> geefSamengevoegdeMapVoorFiles(ArrayList<File> files) {
        final DataProcessor dataProcessor = new DataProcessor()
        Map<String, Object> samengevoegdeMap = [:]
        for (final File file : files) {
            samengevoegdeMap = dataProcessor.process(samengevoegdeMap, file)
        }

        return samengevoegdeMap
    }

    /**
     * Geeft de file op basis van een filenaam binnen zijn classpath.
     * @param fileNaam
     * @return de file als deze gevonden kan worden.
     */
    private File geefFile(String fileNaam) {
        try {
            return new File(getClass().getResource(fileNaam).toURI());
        } catch (Exception e) {
            logger.error 'Kan het bestand [{}] niet vinden', fileNaam
        }

        return null
    }

}
