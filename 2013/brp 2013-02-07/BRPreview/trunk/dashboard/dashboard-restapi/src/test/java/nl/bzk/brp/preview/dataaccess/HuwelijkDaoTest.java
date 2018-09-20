/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.preview.integratie.AbstractIntegratieTest;
import nl.bzk.brp.preview.model.Persoon;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class HuwelijkDaoTest extends AbstractIntegratieTest {

    @Autowired
    private HuwelijkDao huwelijkDao;

    @Test
    public void testHaalOpHuwelijkPersonen() {

        final Long id = Long.valueOf(630);
        final List<Integer> geboorteData = Arrays.asList(new Integer[]{19811002, 19820528});
        final List<String> voornamen = Arrays.asList(new String[]{"Paula", "Cees"});
        final List<Integer> bsns = Arrays.asList(new Integer[]{302533928, 303937828});
        final List<String> achternamen = Arrays.asList(new String[]{"Vlag", "Prins"});
        final List<String> geslacht = Arrays.asList(new String[]{"Vrouw", "Man"});

        final List<Persoon> huwelijkPersonen = huwelijkDao.haalOpHuwelijkPersonen(id);

        assertEquals(2, huwelijkPersonen.size());
        for (Persoon persoon : huwelijkPersonen) {
            assertTrue(voornamen.contains(persoon.getVoornamen().get(0)));
            assertTrue(geslacht.contains(persoon.getGeslacht()));
            assertTrue(geboorteData.contains(persoon.getDatumGeboorte()));
            assertTrue(bsns.contains(persoon.getBsn()));
            assertTrue(achternamen.contains(persoon.getGeslachtsnaamcomponenten().get(0).getNaam()));
        }
    }

}
