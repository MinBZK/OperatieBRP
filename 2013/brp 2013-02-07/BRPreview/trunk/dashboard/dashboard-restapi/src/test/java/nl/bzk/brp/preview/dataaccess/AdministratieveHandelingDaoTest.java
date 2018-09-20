/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nl.bzk.brp.preview.integratie.AbstractIntegratieTest;
import nl.bzk.brp.preview.model.AdministratieveHandeling;
import nl.bzk.brp.preview.model.Melding;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdministratieveHandelingDaoTest extends AbstractIntegratieTest {

    @Autowired
    private AdministratieveHandelingDao administratieveHandelingDao;

    @Test
    public void testHaalOpAdministratieveHandeling() {

        final Long id = new Long(1);
        final String soortCode = "05003";
        final String soort = "onbekende handeling";
        final String partij = "Migratievoorziening";
        Calendar calOntlening = Calendar.getInstance();
        calOntlening.set(1981, 9, 2, 0, 0, 0);
        final Date tsOntlening = calOntlening.getTime();
        Calendar calReg = Calendar.getInstance();
        calReg.set(1981, 9, 2, 0, 0, 0);
        final Date tsReg = calReg.getTime();

        // test
        AdministratieveHandeling administratieveHandeling = administratieveHandelingDao.haalOp(id);

        // verify
        Assert.assertEquals(id, administratieveHandeling.getAdministratieveHandelingId());
        Assert.assertEquals(soortCode, administratieveHandeling.getSoortAdministratieveHandelingCode());
        Assert.assertEquals(soort, administratieveHandeling.getSoortAdministratieveHandeling());
        Assert.assertEquals(partij, administratieveHandeling.getPartij());
        Assert.assertEquals(tsOntlening.getTime() / 1000,
                administratieveHandeling.getTijdstipOntlening().getTime() / 1000);
        Assert.assertNull(administratieveHandeling.getToelichtingOntlening());
        Assert.assertEquals(tsReg.getTime() / 1000,
                administratieveHandeling.getTijdstipRegistratie().getTime() / 1000);

    }

    @Test
    public void testHaalMeldingenOp() {

        final Long id = new Long(1);

        // test
        final List<Melding> meldingen = administratieveHandelingDao.haalMeldingenOp(id);

        // verify
        assertEquals(2, meldingen.size());
        for (Melding melding : meldingen) {
            assertNotNull(melding.getSoort().getCode());
        }
    }

}
