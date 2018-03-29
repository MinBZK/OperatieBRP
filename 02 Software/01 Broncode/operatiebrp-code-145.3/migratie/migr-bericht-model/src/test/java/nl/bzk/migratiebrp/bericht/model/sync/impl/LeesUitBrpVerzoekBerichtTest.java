/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.IOException;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AntwoordFormaatType;

import org.junit.Assert;
import org.junit.Test;

public class LeesUitBrpVerzoekBerichtTest extends AbstractSyncBerichtTestBasis {

    private static final String ANUMMER = "1352456245";
    private static final String TECHNISCHE_SLEUTEL = "objectPersoon";

    @Test
    public void testOpAnummer() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final LeesUitBrpVerzoekBericht bericht = new LeesUitBrpVerzoekBericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setAntwoordFormaat(AntwoordFormaatType.LO_3_XML);
        bericht.setANummer(ANUMMER);
        bericht.setMessageId(MessageIdGenerator.generateId());

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        Assert.assertEquals(ANUMMER, bericht.getANummer());
        Assert.assertEquals("LeesUitBrpVerzoek", bericht.getBerichtType());
        Assert.assertNull(bericht.getStartCyclus());
        Assert.assertNull(bericht.getTechnischeSleutel());
        Assert.assertEquals(AntwoordFormaatType.LO_3_XML, bericht.getAntwoordFormaat());
    }

    @Test
    public void testOpAnummerMetAntwoordformaat() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final LeesUitBrpVerzoekBericht bericht = new LeesUitBrpVerzoekBericht(ANUMMER, AntwoordFormaatType.LO_3_XML);
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setMessageId(MessageIdGenerator.generateId());

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        Assert.assertEquals(ANUMMER, bericht.getANummer());
        Assert.assertEquals("LeesUitBrpVerzoek", bericht.getBerichtType());
        Assert.assertEquals(AntwoordFormaatType.LO_3_XML, bericht.getAntwoordFormaat());
        Assert.assertNull(bericht.getStartCyclus());
        Assert.assertNull(bericht.getTechnischeSleutel());
    }

    @Test
    public void testOpAnummerShort() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final LeesUitBrpVerzoekBericht bericht = new LeesUitBrpVerzoekBericht(ANUMMER);
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setMessageId(MessageIdGenerator.generateId());

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        Assert.assertEquals(ANUMMER, bericht.getANummer());
        Assert.assertEquals("LeesUitBrpVerzoek", bericht.getBerichtType());
        Assert.assertEquals(AntwoordFormaatType.LO_3, bericht.getAntwoordFormaat());
        Assert.assertNull(bericht.getStartCyclus());
        Assert.assertNull(bericht.getTechnischeSleutel());
    }

    @Test
    public void testTechnischeSleutel() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final LeesUitBrpVerzoekBericht bericht = new LeesUitBrpVerzoekBericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setTechnischeSleutel(TECHNISCHE_SLEUTEL);
        bericht.setANummer(null);
        bericht.setMessageId(MessageIdGenerator.generateId());

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        Assert.assertEquals(TECHNISCHE_SLEUTEL, bericht.getTechnischeSleutel());
        Assert.assertEquals("LeesUitBrpVerzoek", bericht.getBerichtType());
        Assert.assertNull(bericht.getStartCyclus());
        Assert.assertNull(bericht.getANummer());
        Assert.assertEquals(AntwoordFormaatType.LO_3, bericht.getAntwoordFormaat());
    }

}
