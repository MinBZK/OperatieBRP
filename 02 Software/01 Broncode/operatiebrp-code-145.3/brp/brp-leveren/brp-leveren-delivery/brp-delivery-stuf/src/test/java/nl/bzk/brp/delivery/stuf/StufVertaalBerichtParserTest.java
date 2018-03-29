/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.stuf;

import static nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst.GEEF_STUF_BG_BERICHT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import nl.bzk.brp.delivery.algemeen.TransformerUtil;
import nl.bzk.brp.delivery.algemeen.VerzoekParseException;
import nl.bzk.brp.service.algemeen.request.MaakDomSourceException;
import nl.bzk.brp.service.algemeen.request.XmlUtils;
import nl.bzk.brp.service.stuf.StufBerichtVerzoek;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * StufVertaalBerichtParserTest.
 */
public class StufVertaalBerichtParserTest {

    @Test
    public void testStuurStufVertaalBerichtParse() throws MaakDomSourceException, TransformerException, VerzoekParseException, IOException, SAXException {
        InputStream inputStream = this.getClass().getResourceAsStream("/stv_stvGeefStufBgBericht_Verzoek_met_dummy_brp_bericht.xml");
        DOMSource domSource = XmlUtils.toDOMSource(new InputSource(inputStream));
        final StufBerichtParser stufBerichtParser = new StufBerichtParser();
        final StufBerichtVerzoek bericht = stufBerichtParser.parse(TransformerUtil.initializeNode(domSource));

        Assert.assertEquals("059901", bericht.getStuurgegevens().getZendendePartijCode());
        Assert.assertEquals(GEEF_STUF_BG_BERICHT, bericht.getSoortDienst());
        Assert.assertEquals("Mutatiebericht", bericht.getParameters().getVertalingBerichtsoortBRP());
        Assert.assertEquals("0310", bericht.getParameters().getVersieStufbg());
        Assert.assertEquals("Mutatiebericht", bericht.getStufBericht().getSoortSynchronisatie());

        XMLUnit.setIgnoreWhitespace(true);
        final String expected = new String(Files.readAllBytes(Paths.get("target/test-classes//verwerkpersoon.xml")));
        final Diff
                diff =
                XMLUnit.compareXML(expected, bericht.getStufBericht().getInhoud());
        assertThat(diff.identical(), is(true));
    }
}
