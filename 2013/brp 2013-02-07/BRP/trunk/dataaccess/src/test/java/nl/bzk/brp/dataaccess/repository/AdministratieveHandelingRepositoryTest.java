/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingRegistratieOverlijdenNederlandBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Test;

public class AdministratieveHandelingRepositoryTest extends AbstractRepositoryTestCase {

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    @Inject
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    @Test
    public void testOpslaanNieuwAdministratieveHandeling() {
        final AdministratieveHandelingBericht ahb = new HandelingRegistratieOverlijdenNederlandBericht();
        final AdministratieveHandelingModel opgeslagenAh =
                administratieveHandelingRepository.opslaanNieuwAdministratieveHandeling(ahb);

        assertNotNull(opgeslagenAh.getID());
        assertEquals(SoortAdministratieveHandeling.REGISTRATIE_OVERLIJDEN_NEDERLAND, opgeslagenAh.getSoort());
    }

    @Test
    public void testHaalAdministratieveHandeling() {
        final AdministratieveHandelingModel ah = administratieveHandelingRepository.haalAdministratieveHandeling(1000L);

        assertNotNull(ah);
        assertEquals(SoortAdministratieveHandeling.ERKENNING_ONGEBOREN_VRUCHT, ah.getSoort());
    }

}
