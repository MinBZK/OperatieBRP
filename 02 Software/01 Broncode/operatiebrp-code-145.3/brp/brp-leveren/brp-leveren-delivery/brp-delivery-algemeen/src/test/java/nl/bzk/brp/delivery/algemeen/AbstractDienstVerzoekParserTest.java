/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.algemeen;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.service.algemeen.request.Verzoek;
import nl.bzk.brp.service.algemeen.request.VerzoekBasis;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Unit test voor {@link AbstractDienstVerzoekParser}.
 */
public class AbstractDienstVerzoekParserTest extends AbstractVerzoekParserTest {

    private static final String BRP_STUURGEGEVENS_BRP_COMMUNICATIE_ID = "/brp:stuurgegevens/@brp:communicatieID";
    private static final String BRP_STUURGEGEVENS_BRP_ZENDENDE_PARTIJ = "/brp:stuurgegevens/brp:zendendePartij";
    private static final String BRP_STUURGEGEVENS_BRP_ZENDENDE_SYSTEEM = "/brp:stuurgegevens/brp:zendendeSysteem";
    private static final String BRP_STUURGEGEVENS_BRP_REFERENTIENUMMER = "/brp:stuurgegevens/brp:referentienummer";
    private static final String BRP_STUURGEGEVENS_BRP_TIJDSTIP_VERZENDING = "/brp:stuurgegevens/brp:tijdstipVerzending";
    private static final String BRP_STUURGEGEVENS_BRP_TIJDSTIP_VERZENDING_WAARDE = "2016-11-28T13:15:02.284+01:00";

    private AbstractDienstVerzoekParser<Verzoek> parser = new TestDienstVerzoekParser();

    @Before
    public void setUp() throws Exception {
        mockNode(BRP_STUURGEGEVENS_BRP_COMMUNICATIE_ID);
        mockNode(BRP_STUURGEGEVENS_BRP_ZENDENDE_PARTIJ);
        mockNode(BRP_STUURGEGEVENS_BRP_ZENDENDE_SYSTEEM);
        mockNode(BRP_STUURGEGEVENS_BRP_REFERENTIENUMMER);
        mockNode(BRP_STUURGEGEVENS_BRP_TIJDSTIP_VERZENDING, BRP_STUURGEGEVENS_BRP_TIJDSTIP_VERZENDING_WAARDE);
    }

    @Test
    public void testVulStuurgegevens() throws ParseException {
        final Verzoek testVerzoek = new TestVerzoek();

        parser.vulStuurgegevens(testVerzoek, node, xPath);

        assertThat(testVerzoek.getStuurgegevens().getCommunicatieId(), is(BRP_STUURGEGEVENS_BRP_COMMUNICATIE_ID));
        assertThat(testVerzoek.getStuurgegevens().getZendendePartijCode(), is(BRP_STUURGEGEVENS_BRP_ZENDENDE_PARTIJ));
        assertThat(testVerzoek.getStuurgegevens().getZendendSysteem(), is(BRP_STUURGEGEVENS_BRP_ZENDENDE_SYSTEEM));
        assertThat(testVerzoek.getStuurgegevens().getReferentieNummer(), is(BRP_STUURGEGEVENS_BRP_REFERENTIENUMMER));
        assertThat(testVerzoek.getStuurgegevens().getTijdstipVerzending(), is(DatumUtil.vanXsdDatumTijdNaarZonedDateTime(
                (BRP_STUURGEGEVENS_BRP_TIJDSTIP_VERZENDING_WAARDE))));
    }

    @Test
    public void testVulStuurgegevensZonderTijdstipVerzending() throws ParseException, XPathExpressionException {
        mockNode(BRP_STUURGEGEVENS_BRP_TIJDSTIP_VERZENDING, null);
        final Verzoek testVerzoek = new TestVerzoek();

        parser.vulStuurgegevens(testVerzoek, node, xPath);

        assertThat(testVerzoek.getStuurgegevens().getTijdstipVerzending(), is(nullValue()));
    }

    @Test
    public void testNodeList() throws XPathExpressionException {
        mockNodeList("test", "een", "twee", "drie");

        final NodeList nodeList = AbstractDienstVerzoekParser.getNodeList("test", xPath, node);

        assertThat(nodeList.item(0).getTextContent(), is("een"));
        assertThat(nodeList.item(1).getTextContent(), is("twee"));
        assertThat(nodeList.item(2).getTextContent(), is("drie"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testNodeListMetExceptie() throws XPathExpressionException {
        mockNodeListException("test");

        AbstractDienstVerzoekParser.getNodeList("test", xPath, node);
    }

    private static final class TestDienstVerzoekParser extends AbstractDienstVerzoekParser<Verzoek> {

        @Override
        public String getPrefix() {
            return "";
        }

        @Override
        public void vulDienstSpecifiekeGegevens(final Verzoek verzoek, final Node node, final XPath xPath) {

        }

        @Override
        public void vulParameters(final Verzoek verzoek, final Node node, final XPath xPath) {

        }
    }

    private static final class TestVerzoek extends VerzoekBasis implements Verzoek {

    }
}
