package nl.bzk.brp.artconversie.conversie

import static nl.bzk.brp.artconversie.model.art.Kolommen.*
import static nl.bzk.brp.artconversie.model.jbehave.Step.Type.*

import nl.bzk.brp.artconversie.model.art.ArtSheet
import nl.bzk.brp.artconversie.model.art.ExcelRegel
import nl.bzk.brp.artconversie.model.jbehave.Scenario
import nl.bzk.brp.artconversie.model.jbehave.Step
import nl.bzk.brp.artconversie.model.jbehave.Story

/**
 * Converteert de regels van een ArtSheet naar JBehave Scenario's.
 */
class ArtConverter {
    private def artProperties
    private File parent

    ArtConverter(Properties properties, File artDir) {
        artProperties = properties
        parent = artDir
    }

    Story convert(final ArtSheet excelRegels) {
        Story story = new Story(name: artProperties['art_input_file'].split('\\.')[0])
        story.meta.add("@soapEndpoint ${EndpointMapper.map(artProperties['soap_endpoint'] as String)?.getAt(0)}")
        story.meta.add("@soapNamespace ${EndpointMapper.map(artProperties['soap_endpoint'] as String)?.getAt(1)}")
        story.meta.add('@auteur tools')
        story.meta.add('@status Uitgeschakeld')

        excelRegels.scenarios.each { String naam, List lines ->
            Scenario scenario = new Scenario(name: naam)

            lines.each { ExcelRegel regel ->
                scenario << new Step(COMMENT, "A${regel.getRegel()}: ${regel[TestGeval]} # ${regel[Volgnummer]} : status=${regel[Status]}")
                Properties props = artProperties.clone()
                if (regel[Alternatieve_Properties]) {
                    props.load(new FileReader(new File(parent, "${regel[Alternatieve_Properties]}/test.properties")))
                }

                if (regel[Test_Bedrijfsregels]) {
                    scenario.meta.put('@regels', regel[Test_Bedrijfsregels].toUpperCase())
                }
                if (regel[Test_Verwerkingsregels]) {
                    scenario.meta.put('@regels', regel[Test_Verwerkingsregels].toUpperCase())
                }
                if (regel.alternatiefSoap() && regel.isVerzenden()) {
                    scenario.meta.put('@soapEndpoint', EndpointMapper.map(regel[SOAP_Endpoint] ?: props['soap_endpoint'])?.getAt(0))
                    scenario.meta.put('@soapNamespace', EndpointMapper.map(regel[SOAP_Endpoint] ?: props['soap_endpoint'])?.getAt(1))
                }

                def excelStep = new Step(GIVEN, "data uit excel /templatedata/${props['art_input_file']} , ${regel[TestGeval]}")
                if (!scenario.contains(excelStep)) {
                    scenario << excelStep
                }

                if (regel[Prepare_Data]) {
                    scenario << new Step(GIVEN, "de database wordt gereset voor de personen ${regel[Prepare_Data]}")
                }

                if (regel[Overschrijf_Variabelen]) {
                    def vars = new Properties()
                    def templateConverter = new TemplateConverter()
                    vars.load(new StringReader(regel[Overschrijf_Variabelen]))

                    def stepText = 'extra waardes:\nSLEUTEL | WAARDE'
                    vars.each { k, v ->
                        stepText += "\n${KeyNamerStrategy.rename(k)} | ${templateConverter.conversieClosure(v)}"
                    }
                    scenario << new Step(GIVEN, stepText)
                }

                if (regel[Pre_Query]) {
                    if (regel[Pre_Query].endsWith('.sql')) {
                        scenario << new Step(GIVEN, "de database is aangepast dmv /sqltemplates/${regel[Pre_Query]}")
                    } else {
                        scenario << new Step(GIVEN, "de database is aangepast met:\n ${regel[Pre_Query]}")
                    }
                }

                if(regel.isVerzenden()) {
                    scenario << new Step(GIVEN, "de sjabloon /berichtdefinities/${(regel[Request_Template] ?: props['request_template_file'])?.split('/')?.getAt(-1)}")
                    scenario << new Step(WHEN, 'het bericht is naar endpoint verstuurd')
                }

                if(regel[Soap_Response_Query]) {
                    scenario << new Step(THEN, "is het antwoordbericht gelijk aan /testcases/${story.name}/response/${regel[TestGeval]}-${regel[Volgnummer]}-soapresponse.xml voor expressie ${regel[Soap_Response_Query]}")
                }

                if (regel[DB_Query]) {
                    int delay = 0
                    if (regel[DB_Query_Delay]) {
                        delay += 12
                        delay += Integer.parseInt(regel[DB_Query_Delay]) / 1000
                    }
                    scenario << new Step(THEN, "is ${delay > 0 ? 'binnen ' + delay +'s ' : ''}de query /sqltemplates/${regel[DB_Query]} gelijk aan /testcases/${story.name}/data/${regel[TestGeval]}-${regel[Volgnummer]}-dataresponse.xml voor de expressie ${regel[DB_Response_Query] ?: '//Results'}")
                }

                if (regel[Post_Query]) {
                    if (regel[Post_Query].endsWith('.sql')) {
                        scenario << new Step(THEN, "de database wordt opgeruimd dmv /sqltemplates/${regel[Post_Query]}")
                    } else {
                        scenario << new Step(THEN, "de database wordt opgeruimd met:\n ${regel[Post_Query]}")
                    }
                }

                // extra lege regel
                scenario << new Step(LEEG, '')
            }


            story << scenario
        }

        return story
    }
}
