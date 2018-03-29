/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.vrijbericht;

import java.io.InputStream;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import nl.bzk.brp.delivery.algemeen.TransformerUtil;
import nl.bzk.brp.delivery.algemeen.VerzoekParseException;
import nl.bzk.brp.service.algemeen.request.MaakDomSourceException;
import nl.bzk.brp.service.algemeen.request.XmlUtils;
import nl.bzk.brp.service.vrijbericht.VrijBerichtVerzoek;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;

/**
 * StuurVrijBerichtParserTest.
 */
public class StuurVrijBerichtParserTest {

    @Test
    public void testStuurVrijBerichtParse() throws MaakDomSourceException, TransformerException, VerzoekParseException {
        InputStream inputStream = this.getClass().getResourceAsStream("/vrb_vrbStuurVrijBericht.xml");
        DOMSource domSource = XmlUtils.toDOMSource(new InputSource(inputStream));
        final VrijBerichtParser stuurVrijBerichtParser = new VrijBerichtParser();
        final VrijBerichtVerzoek bericht = stuurVrijBerichtParser.parse(TransformerUtil.initializeNode(domSource));

        Assert.assertEquals("053001", bericht.getStuurgegevens().getZendendePartijCode());
        Assert.assertNull(bericht.getSoortDienst());
        Assert.assertEquals("199901", bericht.getParameters().getOntvangerVrijBericht());
        Assert.assertEquals("053001", bericht.getParameters().getZenderVrijBericht());

        Assert.assertEquals("Beheer", bericht.getVrijBericht().getSoortNaam());
        Assert.assertEquals("Vanwege onderhoudswerkzaamheden zijn de systemen van gemeente Hellevoetsluis niet bereikbaar op 31 januari 2017.",
                bericht.getVrijBericht().getInhoud());


    }

}
