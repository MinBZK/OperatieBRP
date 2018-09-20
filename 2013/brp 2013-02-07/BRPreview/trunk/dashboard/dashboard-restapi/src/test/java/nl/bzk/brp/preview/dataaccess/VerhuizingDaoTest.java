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


public class VerhuizingDaoTest extends AbstractIntegratieTest {

    @Autowired
    private VerhuizingDao verhuizingDao;

    @Test
    public void testHaalOpVerhuizingPersoon() {

        final Long id = Long.valueOf(5000);
        final Integer bsn = 400140780;
        final Integer datumGeboorte = Integer.valueOf(19811002);
        final String gemeenteCode = "519";
        final String gemeenteNaam = "'s-Gravenzande";
        final String voornaam = "Cees_p1";
        final String voorvoegsel = "voorvoeg";
        final String geslachtsnaam = "Vlag";
        final String geslacht = "Man";

        final Persoon persoon = verhuizingDao.haalOpPersoon(id);

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
    public void testHaalOpVerhuizingOudAdres() {

        final Long id = Long.valueOf(5000);
        final String plaatsCode = "3295";
        final String plaatsNaam = "Utrecht";
        final String straatNaam = "Vredenburg";
        final String huisNr = "3";
        final String huisNrToevoeging = "II";
        final String gemeenteCode = "344";
        final String gemeenteNaam = "Utrecht";
        final Integer datumAanvang = Integer.valueOf(20050328);

        final Adres adresNieuwGeborene = verhuizingDao.haalOpOudAdres(id);

        assertNotNull(adresNieuwGeborene);
        assertEquals(plaatsCode, adresNieuwGeborene.getPlaats().getCode());
        assertEquals(plaatsNaam, adresNieuwGeborene.getPlaats().getNaam());
        assertEquals(straatNaam, adresNieuwGeborene.getStraat());
        assertEquals(huisNr, adresNieuwGeborene.getHuisnummer());
        assertEquals(huisNrToevoeging, adresNieuwGeborene.getHuisnummertoevoeging());
        assertEquals(gemeenteCode, adresNieuwGeborene.getGemeente().getCode());
        assertEquals(gemeenteNaam, adresNieuwGeborene.getGemeente().getNaam());
        assertEquals(datumAanvang, Integer.valueOf(adresNieuwGeborene.getDatumAanvang()));
    }

    @Test
    public void testHaalOpVerhuizingNieuwAdres() {

        final Long id = Long.valueOf(5000);
        final String plaatsCode = "3295";
        final String plaatsNaam = "Utrecht";
        final String straatNaam = "Vredenburg";
        final String huisNr = "3";
        final String huisNrToevoeging = "II";
        final String gemeenteCode = "344";
        final String gemeenteNaam = "Utrecht";
        final Integer datumAanvang = Integer.valueOf(20050328);

        final Adres adresNieuwGeborene = verhuizingDao.haalOpNieuwAdres(id);

        assertNotNull(adresNieuwGeborene);
        assertEquals(plaatsCode, adresNieuwGeborene.getPlaats().getCode());
        assertEquals(plaatsNaam, adresNieuwGeborene.getPlaats().getNaam());
        assertEquals(straatNaam, adresNieuwGeborene.getStraat());
        assertEquals(huisNr, adresNieuwGeborene.getHuisnummer());
        assertEquals(huisNrToevoeging, adresNieuwGeborene.getHuisnummertoevoeging());
        assertEquals(gemeenteCode, adresNieuwGeborene.getGemeente().getCode());
        assertEquals(gemeenteNaam, adresNieuwGeborene.getGemeente().getNaam());
        assertEquals(datumAanvang, Integer.valueOf(adresNieuwGeborene.getDatumAanvang()));
    }

}
