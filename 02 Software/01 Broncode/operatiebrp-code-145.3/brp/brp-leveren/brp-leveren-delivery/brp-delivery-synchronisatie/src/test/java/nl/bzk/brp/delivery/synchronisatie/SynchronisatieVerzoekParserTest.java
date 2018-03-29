/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.synchronisatie;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import javax.xml.xpath.XPath;
import nl.bzk.brp.delivery.algemeen.AbstractVerzoekParserTest;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

/**
 * Unit test voor {@link AbstractSynchronisatieVerzoekParser}.
 */
public class SynchronisatieVerzoekParserTest extends AbstractVerzoekParserTest {

    public static final String BRP_PARAMETERS_BRP_COMMUNICATIE_ID = "/brp:parameters/@brp:communicatieID";
    public static final String BRP_PARAMETERS_BRP_LEVERINGSAUTORISATIE_IDENTIFICATIE = "/brp:parameters/brp:leveringsautorisatieIdentificatie";

    private final AbstractSynchronisatieVerzoekParser parser = new TestSynchronisatieVerzoekParser();

    @Before
    public void setUp() throws Exception {
        mockNode(BRP_PARAMETERS_BRP_COMMUNICATIE_ID);
        mockNode(BRP_PARAMETERS_BRP_LEVERINGSAUTORISATIE_IDENTIFICATIE);
    }

    @Test
    public void testVulParameters() throws Exception {
        final SynchronisatieVerzoek testVerzoek = new SynchronisatieVerzoek();

        parser.vulParameters(testVerzoek, node, xPath);

        assertThat(testVerzoek.getParameters().getCommunicatieId(), is(BRP_PARAMETERS_BRP_COMMUNICATIE_ID));
        assertThat(testVerzoek.getParameters().getLeveringsAutorisatieId(), is(BRP_PARAMETERS_BRP_LEVERINGSAUTORISATIE_IDENTIFICATIE));
    }

    private static final class TestSynchronisatieVerzoekParser extends AbstractSynchronisatieVerzoekParser {

        @Override
        public String getPrefix() {
            return "";
        }

        @Override
        public void vulDienstSpecifiekeGegevens(final SynchronisatieVerzoek verzoek, final Node node, final XPath xPath) {

        }
    }
}