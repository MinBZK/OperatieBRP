/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import nl.bzk.brp.preview.integratie.AbstractIntegratieTest;
import nl.bzk.brp.preview.model.Overlijden;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class OverlijdenDaoTest extends AbstractIntegratieTest {

    @Autowired
    private OverlijdenDao overlijdenDao;

    @Test
    public void testHaalOpOverlijden() {
        final Long id = Long.valueOf(506);
        final Integer bsn = 912345603;
        final Integer datumOverlijden = Integer.valueOf(20111111);
        final String gemeenteCode = "394";
        final String gemeenteNaam = "Haarlemmermeer";
        final String voornaam = "Jan";
        final String geslachtsnaam = "Moes";
        final String geslacht = "Man";

        final Overlijden overlijdenIncompleet = overlijdenDao.haalOpOverlijden(id);

        assertNotNull(overlijdenIncompleet);
        assertEquals(bsn, overlijdenIncompleet.getPersoon().getBsn());
        assertEquals(datumOverlijden, Integer.valueOf(overlijdenIncompleet.getDatumOverlijden().getDecimalen()));
        assertEquals(gemeenteCode, overlijdenIncompleet.getGemeenteOverlijden().getCode());
        assertEquals(gemeenteNaam, overlijdenIncompleet.getGemeenteOverlijden().getNaam());
        assertEquals(voornaam, overlijdenIncompleet.getPersoon().getVoornamen().get(0));
        assertNull(overlijdenIncompleet.getPersoon().getGeslachtsnaamcomponenten().get(0).getVoorvoegsel());
        assertEquals(geslachtsnaam, overlijdenIncompleet.getPersoon().getGeslachtsnaamcomponenten().get(0).getNaam());
        assertEquals(geslacht, overlijdenIncompleet.getPersoon().getGeslacht());
    }

}
