/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.preview.integratie.AbstractIntegratieTest;
import nl.bzk.brp.preview.model.Persoon;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GeboorteDaoTest extends AbstractIntegratieTest {

    @Autowired
    private GeboorteDao geboorteDao;

    @Test
    public void testHaalOpGeboorteNieuwGeborene() {

        final Long id = Long.valueOf(1001);
        final Integer bsn = 677080426;
        final Integer datumGeboorte = Integer.valueOf(19811002);
        final String gemeenteCode = "519";
        final String gemeenteNaam = "'s-Gravenzande";
        final String voornaam = "Cees";
        final String voorvoegsel = "de";
        final String geslachtsnaam = "Vries";
        final String geslacht = "Vrouw";

        final Persoon nieuwGeborene = geboorteDao.haalOpGeboorteNieuwGeborene(id);

        assertNotNull(nieuwGeborene);
        assertEquals(bsn, nieuwGeborene.getBsn());
        assertEquals(datumGeboorte, Integer.valueOf(nieuwGeborene.getDatumGeboorte()));
        assertEquals(gemeenteCode, nieuwGeborene.getGemeenteGeboorte().getCode());
        assertEquals(gemeenteNaam, nieuwGeborene.getGemeenteGeboorte().getNaam());
        assertEquals(voornaam, nieuwGeborene.getVoornamen().get(0));
        assertEquals(voorvoegsel, nieuwGeborene.getGeslachtsnaamcomponenten().get(0).getVoorvoegsel());
        assertEquals(geslachtsnaam, nieuwGeborene.getGeslachtsnaamcomponenten().get(0).getNaam());
        assertEquals(geslacht, nieuwGeborene.getGeslacht());
    }

    @Test
    public void testHaalOpGeboorteOuders() {

        final Long id = Long.valueOf(1001);
        final Integer datumGeboorte = Integer.valueOf(19500101);
        final List<String> voornamen = Arrays.asList(new String[]{"Adam-Cees", "Eva-Cees"});
        final String voorvoegsel = "van";
        final String geslachtsnaam = "Modernodam";
        final List<String> geslacht = Arrays.asList(new String[]{"Vrouw", "Man"});

        final List<Persoon> ouders = geboorteDao.haalOpGeboorteOuders(id);

        assertEquals(2, ouders.size());
        for (Persoon ouder : ouders) {
            assertEquals(datumGeboorte, Integer.valueOf(ouder.getDatumGeboorte()));
            assertEquals(voorvoegsel, ouder.getGeslachtsnaamcomponenten().get(0).getVoorvoegsel());
            assertEquals(geslachtsnaam, ouder.getGeslachtsnaamcomponenten().get(0).getNaam());

            assertTrue(voornamen.contains(ouder.getVoornamen().get(0)));
            assertTrue(geslacht.contains(ouder.getGeslacht()));
        }
    }

}
