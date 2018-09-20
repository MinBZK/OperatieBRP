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

public class HuwelijkBerichtTest {

    private HuwelijkBericht huwelijkBericht;

    @Before
    public void setup() {
        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling();
        final Huwelijk huwelijk = new Huwelijk(administratieveHandeling);

        huwelijk.setDatumAanvang(new Datum(20020202));
        final Plaats plaats = new Plaats();
        plaats.setCode("001");
        plaats.setNaam("testplaats");
        huwelijk.setPlaats(plaats);

        final Persoon persoon1 = getTestPersoon("voornaamPersoon1", "tussenvoegselPersoon1", "achtenaamPersoon1", 111);
        final Persoon persoon2 = getTestPersoon("voornaamPersoon2", "tussenvoegselPersoon2", "achtenaamPersoon1", 112);
        huwelijk.setPersoon1(persoon1);
        huwelijk.setPersoon2(persoon2);

        huwelijkBericht = new HuwelijkBericht(huwelijk);
    }

    private Persoon getTestPersoon(final String voornaam, final String tussenVoegsel, final String achternaam,
                                   final Integer bsn)
    {
        final Persoon persoon = new Persoon();
        persoon.setVoornamen(Arrays.asList(new String[]{voornaam}));
        persoon.setGeslachtsnaamcomponenten(
                Arrays.asList(new Geslachtsnaamcomponent[]{new Geslachtsnaamcomponent(achternaam, tussenVoegsel)}));
        persoon.setBsn(bsn);

        return persoon;
    }

    @Test
    public void testCreeerBerichtTekst() {
        final String berichtTekst = huwelijkBericht.creeerBerichtTekst();

        assertEquals("Huwelijk op 02-02-2002 te testplaats tussen voornaamPersoon1 achtenaamPersoon1 "
            + "tussenvoegselPersoon1 en voornaamPersoon2 achtenaamPersoon1 tussenvoegselPersoon2.", berichtTekst);
    }

    @Test
    public void testCreeerBsnLijst() {
        final List<Integer> bsnLijst = huwelijkBericht.creeerBsnLijst();

        assertEquals(2, bsnLijst.size());
        assertTrue(bsnLijst.contains(111));
        assertTrue(bsnLijst.contains(112));
    }

}
