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

public class VerhuizingBerichtTest {

    private VerhuizingBericht verhuizingBericht;

    @Before
    public void setup() {
        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling();
        final Verhuizing verhuizing = new Verhuizing(administratieveHandeling);

        final Persoon persoon = getTestPersoon("Viktor", "de", "Klerk",
                "geslachtPersoon", 12345, 20101001, "Alkmaar");
        verhuizing.setPersoon(persoon);

        final Adres oudAdres = getAdres(20101001, "Almere", "Almere-Buiten", "Kokosstraat", "14", null);
        verhuizing.setOudAdres(oudAdres);

        final Adres nieuwAdres = getAdres(20121001, "Almere", "Almere-Stad", "Banaanstraat", "15", "bis");
        verhuizing.setNieuwAdres(nieuwAdres);

        verhuizingBericht = new VerhuizingBericht(verhuizing);
    }

    private Adres getAdres(final Integer datumAanvang, final String gemeenteNaam, final String plaatsNaam,
                           final String straat, final String huisnummer, final String huisnummertoevoeging)
    {
        Adres adres = new Adres();

        adres.setDatumAanvang(datumAanvang);

        final Gemeente gemeente = new Gemeente();
        gemeente.setNaam(gemeenteNaam);
        adres.setGemeente(gemeente);

        final Plaats plaats = new Plaats();
        plaats.setNaam(plaatsNaam);
        adres.setPlaats(plaats);

        adres.setStraat(straat);
        adres.setHuisnummer(huisnummer);
        adres.setHuisnummertoevoeging(huisnummertoevoeging);

        return adres;
    }

    private Persoon getTestPersoon(final String voornaam, final String tussenVoegsel, final String achternaam,
                                   final String geslacht, final Integer bsn, final Integer geboorteDatum,
                                   final String gemeenteGeboorte)
    {
        final Persoon persoon = new Persoon();
        persoon.setVoornamen(Arrays.asList(new String[]{voornaam}));
        persoon.setGeslachtsnaamcomponenten(
                Arrays.asList(new Geslachtsnaamcomponent[]{new Geslachtsnaamcomponent(achternaam, tussenVoegsel)}));
        persoon.setBsn(bsn);
        persoon.setGeslacht(geslacht);
        persoon.setDatumGeboorte(geboorteDatum);

        final Gemeente gemeente = new Gemeente();
        gemeente.setNaam(gemeenteGeboorte);
        persoon.setGemeenteGeboorte(gemeente);

        return persoon;
    }

    @Test
    public void testCreeerBerichtTekst() {
        final String berichtTekst = verhuizingBericht.creeerBerichtTekst();

        assertEquals("Verhuizing van Viktor Klerk de per 01-10-2012 naar Banaanstraat 15 bis (Almere-Stad).",
                berichtTekst);
    }

    @Test
    public void testCreeerBsnLijst() {
        final List<Integer> bsnLijst = verhuizingBericht.creeerBsnLijst();

        assertEquals(1, bsnLijst.size());
        assertTrue(bsnLijst.contains(12345));
    }

}
