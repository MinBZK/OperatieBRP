/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class OverlijdenBerichtTest {

    private OverlijdenBericht overlijdenBericht;

    @Before
    public void setup() {
        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling();
        final Overlijden overlijden = new Overlijden(administratieveHandeling);

        final Persoon persoon = getTestPersoon("voornaam", "tussenvoegsel", "achtenaam", 12345);

        overlijden.setPersoon(persoon);
        final Gemeente gemeente = new Gemeente();
        gemeente.setNaam("testGemeente");
        overlijden.setGemeenteOverlijden(gemeente);
        overlijden.setDatumOverlijden(new Datum(20100707));

        overlijdenBericht = new OverlijdenBericht(overlijden);
    }

    private Persoon getTestPersoon(final String voornaam, final String tussenVoegsel, final String achternaam,
            final Integer bsn)
    {
        final Persoon persoon = new Persoon();
        persoon.setVoornamen(Arrays.asList(new String[] { voornaam }));
        persoon.setGeslachtsnaamcomponenten(Arrays.asList(new Geslachtsnaamcomponent[] { new Geslachtsnaamcomponent(achternaam, tussenVoegsel) }));
        persoon.setBsn(bsn);

        return persoon;
    }

    @Test
    public void testCreeerBerichtTekst() {
        final String berichtTekst = overlijdenBericht.creeerBerichtTekst();

        assertEquals("voornaam achtenaam tussenvoegsel overleden op 07-07-2010 "
                + "te testGemeente. BSN: 12345.", berichtTekst);
    }

    @Test
    public void testCreeerBsnLijst() {
        final List<Integer> bsnLijst = overlijdenBericht.creeerBsnLijst();

        assertEquals(1, bsnLijst.size());
        assertTrue(bsnLijst.contains(12345));
    }

}
