/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import javax.xml.xpath.XPath;
import nl.bzk.brp.delivery.algemeen.AbstractVerzoekParserTest;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;
import nl.bzk.brp.service.bevraging.detailspersoon.GeefDetailsPersoonVerzoek;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

/**
 * Unit test voor {@link AbstractBevragingVerzoekParser}.
 */
public class AbstractBevragingVerzoekParserTest extends AbstractVerzoekParserTest {

    private static final String BRP_ZOEKCRITERIA_PERSOON_BRP_COMMUNICATIE_ID = "/brp:zoekcriteriaPersoon/@brp:communicatieID";
    private static final String BRP_PARAMETERS_BRP_COMMUNICATIE_ID = "/brp:parameters/@brp:communicatieID";
    private static final String BRP_PARAMETERS_BRP_ROL_NAAM = "/brp:parameters/brp:rolNaam";
    private static final String BRP_PARAMETERS_BRP_LEVERINGSAUTORISATIE_IDENTIFICATIE = "/brp:parameters/brp:leveringsautorisatieIdentificatie";
    private static final String BRP_PARAMETERS_BRP_DIENST_IDENTIFICATIE = "/brp:parameters/brp:dienstIdentificatie";

    private final AbstractBevragingVerzoekParser<BevragingVerzoek> parser = new TestBevragingVerzoekParser();

    @Before
    public void setUp() throws Exception {
        mockNode(BRP_ZOEKCRITERIA_PERSOON_BRP_COMMUNICATIE_ID);
        mockNode(BRP_PARAMETERS_BRP_COMMUNICATIE_ID);
        mockNode(BRP_PARAMETERS_BRP_ROL_NAAM);
        mockNode(BRP_PARAMETERS_BRP_LEVERINGSAUTORISATIE_IDENTIFICATIE);
        mockNode(BRP_PARAMETERS_BRP_DIENST_IDENTIFICATIE);
    }

    @Test
    public void testVulParameters() throws Exception {
        final BevragingVerzoek testVerzoek = new GeefDetailsPersoonVerzoek();

        parser.vulParameters(testVerzoek, node, xPath);

        assertThat(testVerzoek.getParameters().getCommunicatieId(), is(BRP_PARAMETERS_BRP_COMMUNICATIE_ID));
        assertThat(testVerzoek.getParameters().getRolNaam(), is(BRP_PARAMETERS_BRP_ROL_NAAM));
        assertThat(testVerzoek.getParameters().getLeveringsAutorisatieId(), is(BRP_PARAMETERS_BRP_LEVERINGSAUTORISATIE_IDENTIFICATIE));
        assertThat(testVerzoek.getParameters().getDienstIdentificatie(), is(BRP_PARAMETERS_BRP_DIENST_IDENTIFICATIE));
    }

    private static final class TestBevragingVerzoekParser extends AbstractBevragingVerzoekParser<BevragingVerzoek> {

        @Override
        public String getPrefix() {
            return "";
        }

        @Override
        public void vulDienstSpecifiekeGegevens(final BevragingVerzoek verzoek, final Node node, final XPath xPath) {

        }
    }
}