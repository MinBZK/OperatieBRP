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


public class GeboorteBerichtTest {

    private GeboorteBericht geboorteBericht;

    @Before
    public void setup() {
        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling();
        final Geboorte geboorte = new Geboorte(administratieveHandeling);

        final Persoon nieuwGeborene =
            getTestNieuwGeborene("voornaamKind", "tussenvoegselKind", "achtenaamKind", "geslachtKind", 12345, 20101001,
                    "Alkmaar");
        geboorte.setNieuwgeborene(nieuwGeborene);
        final Persoon ouder1 = getTestPersoon("voornaamOuder1", "tussenvoegselOuder1", "achtenaamOuder1", 111);
        geboorte.setOuder1(ouder1);
        final Persoon ouder2 = getTestPersoon("voornaamOuder2", "tussenvoegselOuder2", "achtenaamOuder2", 112);
        geboorte.setOuder2(ouder2);

        geboorteBericht = new GeboorteBericht(geboorte);
    }

    private Persoon getTestPersoon(final String voornaam, final String tussenVoegsel, final String achternaam,
            final Integer bsn)
    {
        final Persoon persoon = new Persoon();
        persoon.setVoornamen(Arrays.asList(new String[] { voornaam }));
        persoon.setGeslachtsnaamcomponenten(Arrays.asList(new Geslachtsnaamcomponent[] { new Geslachtsnaamcomponent(
                achternaam, tussenVoegsel) }));
        persoon.setBsn(bsn);

        return persoon;
    }

    private Persoon getTestNieuwGeborene(final String voornaam, final String tussenVoegsel, final String achternaam,
            final String geslacht, final Integer bsn, final Integer geboorteDatum, final String gemeenteGeboorte)
    {
        final Persoon nieuwGeborene = getTestPersoon(voornaam, tussenVoegsel, achternaam, bsn);
        nieuwGeborene.setGeslacht(geslacht);
        nieuwGeborene.setDatumGeboorte(geboorteDatum);

        final Gemeente gemeente = new Gemeente();
        gemeente.setNaam(gemeenteGeboorte);
        nieuwGeborene.setGemeenteGeboorte(gemeente);

        return nieuwGeborene;
    }

    @Test
    public void testCreeerBerichtTekst() {
        final String berichtTekst = geboorteBericht.creeerBerichtTekst();

        assertEquals("voornaamKind achtenaamKind tussenvoegselKind geboren op 01-10-2010 te Alkmaar. BSN: 12345. "
            + "Ouders: voornaamOuder1 achtenaamOuder1 tussenvoegselOuder1 en voornaamOuder2 achtenaamOuder2 "
            + "tussenvoegselOuder2.", berichtTekst);
    }

    @Test
    public void testCreeerBsnLijst() {
        final List<Integer> bsnLijst = geboorteBericht.creeerBsnLijst();

        assertEquals(3, bsnLijst.size());
        assertTrue(bsnLijst.contains(12345));
        assertTrue(bsnLijst.contains(111));
        assertTrue(bsnLijst.contains(112));
    }

}
