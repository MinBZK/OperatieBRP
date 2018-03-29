/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import javax.xml.xpath.XPathExpressionException;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.brp.delivery.algemeen.AbstractVerzoekParserTest;
import nl.bzk.brp.delivery.algemeen.VerzoekParser;
import nl.bzk.brp.service.bevraging.geefmedebewoners.GeefMedebewonersVerzoek;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link GeefMedebewonersVerzoekParser}.
 */
@RunWith(MockitoJUnitRunner.class)
public class GeefMedebewonersVerzoekParserTest extends AbstractVerzoekParserTest {

    private final GeefMedebewonersVerzoekParser parser = new GeefMedebewonersVerzoekParser();

    @Test
    public void testVulParameters() throws XPathExpressionException {
        final GeefMedebewonersVerzoek testVerzoek = new GeefMedebewonersVerzoek();
        mockNode("/brp:" + SoortBericht.LVG_BVG_GEEF_MEDEBEWONERS.getIdentifier() + "/brp:parameters/brp:peilmomentMaterieel", "1980-07-23");

        parser.vulParameters(testVerzoek, node, xPath);

        assertThat(testVerzoek.getParameters().getPeilmomentMaterieel(), is("1980-07-23"));
    }

    @Test
    public void testParseParametersLeegVerzoek() {
        // Voor coverage null checks.
        parser.vulParameters(new GeefMedebewonersVerzoek(), node, xPath);
    }

    @Test
    public void testVulDienstSpecifiekeGegevens() throws XPathExpressionException {
        final GeefMedebewonersVerzoek testVerzoek = new GeefMedebewonersVerzoek();
        mockNode("/brp:lvg_bvgGeefMedebewoners/brp:identificatiecriteria/brp:burgerservicenummer", "123456789");

        parser.vulDienstSpecifiekeGegevens(testVerzoek, node, xPath);

        assertThat(testVerzoek.getIdentificatiecriteria().getBurgerservicenummer(), is("123456789"));
    }
}
