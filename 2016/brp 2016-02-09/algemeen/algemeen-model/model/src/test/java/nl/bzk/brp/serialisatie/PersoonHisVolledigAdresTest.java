/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonAdres;
import nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigStringSerializer;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


public class PersoonHisVolledigAdresTest {

    private final JacksonJsonSerializer<PersoonHisVolledigImpl> serializer =
        new PersoonHisVolledigStringSerializer();

    @Test
    public void leesPersoonMetPeildatumVandaag() throws IOException {
        final PersoonHisVolledigImpl persoonHisVolledig =
            leesPersoonHisVolledigUitJsonBestand("/data/persoonvolledig-14.json");

        final Persoon persoon = new PersoonView(persoonHisVolledig);

        assertNotNull(persoon);
    }

    @Test
    public void controleerAdresIn1960Over1960() throws IOException {
        final PersoonHisVolledigImpl persoonHisVolledig =
            leesPersoonHisVolledigUitJsonBestand("/data/persoonvolledig-14.json");

        final DatumTijdAttribuut formeelPeilmoment = DatumTijdAttribuut.bouwDatumTijd(1960, 1, 1, 0, 0, 0);
        final DatumAttribuut materieleDatum = new DatumAttribuut(19600101);

        final Persoon persoon = new PersoonView(persoonHisVolledig, formeelPeilmoment, materieleDatum);

        assertEquals(Integer.valueOf(22), persoon.getAdressen().iterator().next().getStandaard().getHuisnummer()
                .getWaarde());
    }

    @Test
    public void controleerAdresIn1993Over1960() throws IOException {
        final PersoonHisVolledigImpl persoonHisVolledig =
                leesPersoonHisVolledigUitJsonBestand("/data/persoonvolledig-14.json");

        final DatumTijdAttribuut formeelPeilmoment = DatumTijdAttribuut.bouwDatumTijd(1993, 1, 1, 0, 0, 0);
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(19900101);

        final Persoon persoon = new PersoonView(persoonHisVolledig, formeelPeilmoment, materieelPeilmoment);

        assertEquals(Integer.valueOf(21), persoon.getAdressen().iterator().next().getStandaard().getHuisnummer()
                .getWaarde());
    }

    @Test
    public void controleerAdresIn1993Over1993() throws IOException {
        final PersoonHisVolledigImpl persoonHisVolledig =
                leesPersoonHisVolledigUitJsonBestand("/data/persoonvolledig-14.json");

        final DatumTijdAttribuut formeelPeilmoment = DatumTijdAttribuut.bouwDatumTijd(1993, 1, 1, 0, 0, 0);
        final DatumAttribuut materieleDatum = new DatumAttribuut(19930101);

        final Persoon persoon = new PersoonView(persoonHisVolledig, formeelPeilmoment, materieleDatum);

        final PersoonAdres adres = persoon.getAdressen().iterator().next();

        assertEquals("Vredenburg", adres.getStandaard().getAfgekorteNaamOpenbareRuimte().getWaarde());
        assertEquals(Integer.valueOf(27), adres.getStandaard().getHuisnummer().getWaarde());
        assertEquals("3511BC", adres.getStandaard().getPostcode().getWaarde());
    }

    private PersoonHisVolledigImpl leesPersoonHisVolledigUitJsonBestand(final String bestandLokatie)
            throws IOException
    {
        final Resource resource = new ClassPathResource(bestandLokatie);
        byte[] data = IOUtils.toByteArray(resource.getInputStream());

        return serializer.deserialiseer(data);
    }

}
