/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.impl.MvVerhuizingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingVerzoekBericht;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.junit.Assert;
import org.junit.Test;

public class MaakMvVerhuizingVerzoekActionTest {
    private final MaakMvVerhuizingVerzoekAction subject = new MaakMvVerhuizingVerzoekAction();

    @Test
    public void test() {
        final VerhuizingVerzoekBericht input = new VerhuizingVerzoekBericht();
        input.setAfzender("afzender");
        input.setANummer("1234567890");
        input.setBsn("987654321");
        input.setHuidigeGemeente("1234");
        input.setNieuweGemeente("4321");
        input.setDatumInschrijving("20030405");
        input.setAdresseerbaarObject("1234567890123456");
        input.setIdNummerAanduiding("6543210987654321");
        input.setNaamOpenbareRuimte("Openbare ruimte");
        input.setAfgekorteNaamOpenbareRuimte("Op. ruim.");
        input.setHuisnummer("123");
        input.setHuisletter("a");
        input.setHuisnummerToevoeging("-b");
        input.setPostcode("1234RR");
        input.setWoonplaats("Appingedam");
        input.setLocatieTovAdres("BY");
        input.setLocatieOmschrijving("Links voor de deur");
        input.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal("0512")));
        input.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("0600")));

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", input);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final MvVerhuizingVerzoekBericht verhuizing = (MvVerhuizingVerzoekBericht) result.get("mvVerhuizingVerzoek");
        Assert.assertEquals("afzender", verhuizing.getAfzender());
        Assert.assertEquals("1234567890", verhuizing.getANummer());
        Assert.assertEquals("987654321", verhuizing.getBsn());
        Assert.assertEquals("1234", verhuizing.getHuidigeGemeente());
        Assert.assertEquals("4321", verhuizing.getNieuweGemeente());
        Assert.assertEquals("20030405", verhuizing.getDatumInschrijving());
        Assert.assertEquals("1234567890123456", verhuizing.getAdresseerbaarObject());
        Assert.assertEquals("6543210987654321", verhuizing.getIdNummerAanduiding());
        Assert.assertEquals("Openbare ruimte", verhuizing.getNaamOpenbareRuimte());
        Assert.assertEquals("Op. ruim.", verhuizing.getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals("123", verhuizing.getHuisnummer());
        Assert.assertEquals("a", verhuizing.getHuisletter());
        Assert.assertEquals("-b", verhuizing.getHuisnummerToevoeging());
        Assert.assertEquals("1234RR", verhuizing.getPostcode());
        Assert.assertEquals("Appingedam", verhuizing.getWoonplaats());
        Assert.assertEquals("BY", verhuizing.getLocatieTovAdres());
        Assert.assertEquals("Links voor de deur", verhuizing.getLocatieOmschrijving());
        Assert.assertEquals(new BrpGemeenteCode(new BigDecimal("0512")), verhuizing.getLo3Gemeente());
        Assert.assertEquals(new BrpGemeenteCode(new BigDecimal("0600")), verhuizing.getBrpGemeente());
    }
}
