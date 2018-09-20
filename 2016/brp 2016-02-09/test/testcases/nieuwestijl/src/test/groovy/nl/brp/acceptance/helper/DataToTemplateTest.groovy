package nl.brp.acceptance.helper

import nl.brp.acceptance.internal.TemplateBerichtBuilder
import nl.bzk.brp.funqmachine.processors.xml.XmlUtils
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

public class DataToTemplateTest {

    private final Logger logger = LoggerFactory.getLogger(DataToTemplateTest)

    @Test
    void testDataToTemplate() {
        final String xml = new TemplateBerichtBuilder('bhg_vbaCorrigeerAdres')
            .metHandelingBasis('correctieAdres')
            .metActieBasis('correctieAdres')
            .metSpecifiekeData('/testcases/afnemerindicaties/afnemerindicatie_onbekende_deg_4')
            .build()

        logger.info('XML: \n{}', XmlUtils.prettify(xml));

        XmlUtils.valideerOutputTegenSchema(xml, 'master.xsd')
    }
}
