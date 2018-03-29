/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie;

import java.io.InputStream;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.delivery.algemeen.TransformerUtil;
import nl.bzk.brp.delivery.algemeen.VerzoekParseException;
import nl.bzk.brp.service.afnemerindicatie.AfnemerindicatieVerzoek;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import nl.bzk.brp.service.algemeen.request.MaakDomSourceException;
import nl.bzk.brp.service.algemeen.request.XmlUtils;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;

/**
 * OnderhoudAfnemerindicatiesBerichtParserTest.
 */
public class OnderhoudAfnemerindicatiesBerichtParserTest {

    @Test
    public void parseRegistreerAfnemerIndicatie()
            throws MaakDomSourceException, VerzoekParseException, TransformerException {
        InputStream inputStream = this.getClass().getResourceAsStream("/lvg_synRegistreerAfnemerindicatie_plaats.xml");
        DOMSource domSource = XmlUtils.toDOMSource(new InputSource(inputStream));

        final OnderhoudAfnemerindicatiesBerichtParser parser = new OnderhoudAfnemerindicatiesBerichtParser();
        final AfnemerindicatieVerzoek bericht = parser.parse(TransformerUtil.initializeNode(domSource));
        Assert.assertEquals("053001", bericht.getStuurgegevens().getZendendePartijCode());
        Assert.assertEquals(SoortDienst.PLAATSING_AFNEMERINDICATIE, bericht.getSoortDienst());
        Assert.assertEquals("238651974", bericht.getAfnemerindicatie().getBsn());
        Assert.assertEquals("051801", bericht.getAfnemerindicatie().getPartijCode());
        Assert.assertEquals("2012-01-01", bericht.getAfnemerindicatie().getDatumAanvangMaterielePeriode());
        Assert.assertEquals("2016-12-31", bericht.getAfnemerindicatie().getDatumEindeVolgen());
    }

    @Test
    public void parseVerwijderAfnemerIndicatie()
            throws MaakDomSourceException, VerzoekParseException, TransformerException {
        InputStream stream = this.getClass().getResourceAsStream("/lvg_synRegistreerAfnemerindicatie_verwijder.xml");
        DOMSource domSource = XmlUtils.toDOMSource(new InputSource(stream));

        final OnderhoudAfnemerindicatiesBerichtParser parser = new OnderhoudAfnemerindicatiesBerichtParser();
        final AfnemerindicatieVerzoek bericht = parser.parse(TransformerUtil.initializeNode(domSource));
        Assert.assertEquals("053001", bericht.getStuurgegevens().getZendendePartijCode());
        Assert.assertEquals(SoortDienst.VERWIJDERING_AFNEMERINDICATIE, bericht.getSoortDienst());
        Assert.assertEquals("238651974", bericht.getAfnemerindicatie().getBsn());
        Assert.assertEquals("051801", bericht.getAfnemerindicatie().getPartijCode());
        Assert.assertEquals("2012-01-01", bericht.getAfnemerindicatie().getDatumAanvangMaterielePeriode());
        Assert.assertEquals("2016-12-31", bericht.getAfnemerindicatie().getDatumEindeVolgen());

    }

    @Test(expected = BrpServiceRuntimeException.class)
    public void parseFouteAfnemerIndicatie()
            throws MaakDomSourceException, VerzoekParseException, TransformerException {
        InputStream stream = this.getClass().getResourceAsStream("/lvg_synRegistreerAfnemerindicatie_fout.xml");
        DOMSource domSource = XmlUtils.toDOMSource(new InputSource(stream));

        final OnderhoudAfnemerindicatiesBerichtParser parser = new OnderhoudAfnemerindicatiesBerichtParser();
        parser.parse(TransformerUtil.initializeNode(domSource));
    }
}
