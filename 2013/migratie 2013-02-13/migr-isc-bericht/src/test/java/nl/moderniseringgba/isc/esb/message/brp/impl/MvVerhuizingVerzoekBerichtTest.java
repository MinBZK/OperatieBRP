/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import java.math.BigDecimal;

import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.junit.Assert;
import org.junit.Test;

public class MvVerhuizingVerzoekBerichtTest {

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void test() throws Exception {
        final MvVerhuizingVerzoekBericht bericht = new MvVerhuizingVerzoekBericht();
        bericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal("0512")));
        bericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("0600")));

        bericht.setAfzender("Piet");
        bericht.setANummer("1234567890");
        bericht.setBsn("123456789");
        bericht.setHuidigeGemeente("1234");
        bericht.setNieuweGemeente("5432");
        bericht.setDatumInschrijving("20100504");
        bericht.setAdresseerbaarObject("1234567890123456");
        bericht.setIdNummerAanduiding("6543210987654321");
        bericht.setNaamOpenbareRuimte("Plein");
        bericht.setAfgekorteNaamOpenbareRuimte("Pl");
        bericht.setHuisnummer("123");
        bericht.setHuisletter("a");
        bericht.setHuisnummerToevoeging("-b");
        bericht.setPostcode("1234RR");
        bericht.setWoonplaats("Leeuwaarden");
        bericht.setLocatieTovAdres("TO");
        bericht.setLocatieOmschrijving("In de woonboot");

        final String formatted = bericht.format();
        System.out.println(formatted);
        final BrpBericht parsed = factory.getBericht(formatted);
        parsed.setMessageId(bericht.getMessageId());

        Assert.assertEquals(bericht, parsed);
    }
}
