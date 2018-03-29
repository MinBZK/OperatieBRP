/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.request;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorServiceImpl;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;

/**
 * SchemaValidatorServiceImplTest.
 */
public class SchemaValidatorServiceTest {

    @Test
    public void testValide() throws Exception {
        final String pad = "/resultaat/lvg_selVerwerkSelectieresultaatSetPersonen.xml";
        SchemaValidatorService.doValideerTegenSchema(maakRequest(pad),
                SchemaValidatorService.maakSchema("/xsd/BRP0200/brp0200_lvgSelectie_Berichten.xsd"));
    }

    @Test
    public void testValideDefault() throws Exception {
        final SchemaValidatorServiceImpl dummyImpl = new SchemaValidatorServiceImpl() { };
        dummyImpl.valideer(
                maakRequest("/resultaat/lvg_selVerwerkSelectieresultaatSetPersonen.xml"),
                SchemaValidatorService.maakSchema("/xsd/BRP0200/brp0200_lvgSelectie_Berichten.xsd"));
    }


    @Test(expected = SchemaValidatorService.SchemaValidatieException.class)
    public void testNietValid() throws Exception {
        final String pad = "/resultaat/lvg_selVerwerkSelectieresultaatSetPersonen.xml";
        SchemaValidatorService.doValideerTegenSchema(maakRequest(pad),
                SchemaValidatorService.maakSchema("/xsd/BRP0200/brp0200_lvgAfnemerindicatie_Berichten.xsd"));

    }

    @Test(expected = SchemaValidatorService.SchemaNietGevondenException.class)
    public void testSchemaNietGevonden() throws Exception {
        final String pad = "/resultaat/lvg_selVerwerkSelectieresultaatSetPersonen.xml";
        SchemaValidatorService.doValideerTegenSchema(maakRequest(pad),
                SchemaValidatorService.maakSchema("/xsd/404.xsd"));

    }


    private Source maakRequest(String pad) throws IOException, ParserConfigurationException, SAXException {
        return new StreamSource(new ClassPathResource(pad).getInputStream());
    }

}
