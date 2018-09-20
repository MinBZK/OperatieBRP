/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import static nl.bzk.brp.model.hisvolledig.util.DatumTijdBuilder.bouwDatumTijd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.hisvolledig.JacksonJsonSerializer;
import nl.bzk.brp.model.hisvolledig.PersoonHisVolledigSerializer;
import nl.bzk.brp.model.hisvolledig.PersoonHisVolledigView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonAdres;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class PersoonHisVolledigAdresTest {

    private PersoonHisVolledigSerializer serializer = new JacksonJsonSerializer();

    @Test
    public void leesPersoonMetPeildatumVandaag() throws IOException {
        final PersoonHisVolledig persoonHisVolledig =
                leesPersoonHisVolledigUitJsonBestand("/data/persoonvolledig-13-aangepast-en-geformatteerd.json");

        final Persoon persoon = new PersoonHisVolledigView(persoonHisVolledig);

        assertNotNull(persoon);
    }

    @Test
    public void controleerAdresIn1960Over1960() throws IOException {
        final PersoonHisVolledig persoonHisVolledig =
                leesPersoonHisVolledigUitJsonBestand("/data/persoonvolledig-13-aangepast-en-geformatteerd.json");

        final DatumTijd formeelPeilmoment = bouwDatumTijd(1960, 1, 1, 0, 0, 0);

        final Persoon persoon = new PersoonHisVolledigView(persoonHisVolledig, formeelPeilmoment);

        assertEquals(Integer.valueOf(22),
                persoon.getAdressen().iterator().next().getStandaard().getHuisnummer().getWaarde());
    }

    @Test
    public void controleerAdresIn1993Over1960() throws IOException {
        final PersoonHisVolledig persoonHisVolledig =
                leesPersoonHisVolledigUitJsonBestand("/data/persoonvolledig-13-aangepast-en-geformatteerd.json");

        final DatumTijd formeelPeilmoment = bouwDatumTijd(1993, 1, 1, 0, 0, 0);
        final Datum materieelPeilmoment = new Datum(19900101);

        final Persoon persoon = new PersoonHisVolledigView(persoonHisVolledig, formeelPeilmoment, materieelPeilmoment);

        assertEquals(Integer.valueOf(21),
                persoon.getAdressen().iterator().next().getStandaard().getHuisnummer().getWaarde());
    }

    @Test
    public void controleerAdresIn1993Over1993() throws IOException {
        final PersoonHisVolledig persoonHisVolledig =
                leesPersoonHisVolledigUitJsonBestand("/data/persoonvolledig-13-aangepast-en-geformatteerd.json");

        final DatumTijd formeelPeilmoment = bouwDatumTijd(1993, 1, 1, 0, 0, 0);

        final Persoon persoon = new PersoonHisVolledigView(persoonHisVolledig, formeelPeilmoment);

        final PersoonAdres adres = persoon.getAdressen().iterator().next();

        assertEquals("Vredenburg", adres.getStandaard().getAfgekorteNaamOpenbareRuimte().getWaarde());
        assertEquals(Integer.valueOf(27), adres.getStandaard().getHuisnummer().getWaarde());
        assertEquals("3511BC", adres.getStandaard().getPostcode().getWaarde());
    }

    private PersoonHisVolledig leesPersoonHisVolledigUitJsonBestand(final String bestandLokatie) throws IOException {
        final Resource resource = new ClassPathResource(bestandLokatie);
        byte[] data = IOUtils.toByteArray(resource.getInputStream());

        return serializer.deserializeer(data);
    }

}
