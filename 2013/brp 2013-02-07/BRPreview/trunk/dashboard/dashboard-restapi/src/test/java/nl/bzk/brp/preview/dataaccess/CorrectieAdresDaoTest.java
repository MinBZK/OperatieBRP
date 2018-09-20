/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import nl.bzk.brp.preview.integratie.AbstractIntegratieTest;
import nl.bzk.brp.preview.model.Adres;
import nl.bzk.brp.preview.model.Persoon;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class CorrectieAdresDaoTest extends AbstractIntegratieTest {

    @Autowired
    private CorrectieAdresDao correctieAdresDao;

    @Test
    public void testHaalOpPersoon() {

        final Long id = Long.valueOf(9000);
        final Integer bsn = 800074658;
        final Integer datumGeboorte = Integer.valueOf(19811002);
        final String gemeenteCode = "519";
        final String gemeenteNaam = "'s-Gravenzande";
        final String voornaam = "Cees";
        final String voorvoegsel = null;
        final String geslachtsnaam = "Vlag";
        final String geslacht = "Man";

        final Persoon persoon = correctieAdresDao.haalOpPersoon(id);

        assertNotNull(persoon);
        assertEquals(bsn, persoon.getBsn());
        assertEquals(datumGeboorte, Integer.valueOf(persoon.getDatumGeboorte()));
        assertEquals(gemeenteCode, persoon.getGemeenteGeboorte().getCode());
        assertEquals(gemeenteNaam, persoon.getGemeenteGeboorte().getNaam());
        assertEquals(voornaam, persoon.getVoornamen().get(0));
        assertEquals(voorvoegsel, persoon.getGeslachtsnaamcomponenten().get(0).getVoorvoegsel());
        assertEquals(geslachtsnaam, persoon.getGeslachtsnaamcomponenten().get(0).getNaam());
        assertEquals(geslacht, persoon.getGeslacht());
    }

    @Test
    public void testHaalOpAdressen() {

        final Long id = Long.valueOf(9000);
        final String plaatsCode = "3295";
        final String plaatsNaam = "Utrecht";
        final String straatNaam = "Vredenburg";
        final String huisNr = "3";
        final String huisNrToevoeging = null;
        final String gemeenteCode = "344";
        final String gemeenteNaam = "Utrecht";
        final Integer datumAanvang = Integer.valueOf(20050328);

        final Adres adres = correctieAdresDao.haalOpAdressen(id).get(0);
        assertNotNull(adres);
        assertEquals(plaatsCode, adres.getPlaats().getCode());
        assertEquals(plaatsNaam, adres.getPlaats().getNaam());
        assertEquals(straatNaam, adres.getStraat());
        assertEquals(huisNr, adres.getHuisnummer());
        assertEquals(huisNrToevoeging, adres.getHuisnummertoevoeging());
        assertEquals(gemeenteCode, adres.getGemeente().getCode());
        assertEquals(gemeenteNaam, adres.getGemeente().getNaam());
        assertEquals(datumAanvang, Integer.valueOf(adres.getDatumAanvang()));
    }

}
