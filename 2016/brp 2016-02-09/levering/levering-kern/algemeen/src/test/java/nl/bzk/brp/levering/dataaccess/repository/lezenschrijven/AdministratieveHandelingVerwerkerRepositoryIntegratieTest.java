/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.dataaccess.repository.lezenschrijven;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven.AdministratieveHandelingVerwerkerRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class AdministratieveHandelingVerwerkerRepositoryIntegratieTest extends AbstractIntegratieTest {

    @Autowired
    private AdministratieveHandelingVerwerkerRepository administratieveHandelingVerwerkerRepository;

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Test
    public final void testHaalAdministratieveHandelingPersoonIds() {
        final List<Integer> persoonIds = administratieveHandelingVerwerkerRepository.haalAdministratieveHandelingPersoonIds(1L);

        assertNotNull(persoonIds);
        assertEquals(Integer.valueOf(1), persoonIds.get(0));
    }

    @Test
    public final void testMarkeerAdministratieveHandelingAlsVerwerkt() {
        final long administratieveHandelingId = 501L;

        administratieveHandelingVerwerkerRepository
            .markeerAdministratieveHandelingAlsVerwerkt(administratieveHandelingId);

        final AdministratieveHandelingModel administratieveHandelingModel =
            em.find(AdministratieveHandelingModel.class, administratieveHandelingId);

        assertNotNull(administratieveHandelingModel.getLevering().getTijdstipLevering());
    }

    @Test
    public final void magVerwerktWordenMetNegatiefResultaat() {

        final List<Integer> persoonIds = Arrays.asList(1, 2, 3, 4);
        final AdministratieveHandelingModel administratieveHandeling =
            new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND), null, null, DatumTijdAttribuut.nu());

        final boolean magHet = administratieveHandelingVerwerkerRepository.magAdministratieveHandelingVerwerktWorden(
            administratieveHandeling,
            persoonIds,
            null);

        assertFalse(magHet);
    }

    @Test
    public final void magVerwerktWordenMetPositiefResultaat() {

        final List<Integer> persoonIds = Arrays.asList(500, 501);
        final AdministratieveHandelingModel administratieveHandeling = new AdministratieveHandelingModel(
            new SoortAdministratieveHandelingAttribuut(
                SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND), null, null, DatumTijdAttribuut.nu());

        final boolean magHet = administratieveHandelingVerwerkerRepository.magAdministratieveHandelingVerwerktWorden(
            administratieveHandeling,
            persoonIds,
            null);

        assertTrue(magHet);
    }

    @Test
    public final void magVerwerktWordenMetPositiefResultaatMetLegeLijst() {

        final List<Integer> persoonIds = Arrays.asList(500, 501);
        final AdministratieveHandelingModel administratieveHandeling = new AdministratieveHandelingModel(
            new SoortAdministratieveHandelingAttribuut(
                SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND), null, null, DatumTijdAttribuut.nu());

        final boolean magHet = administratieveHandelingVerwerkerRepository.magAdministratieveHandelingVerwerktWorden(
            administratieveHandeling,
            persoonIds,
            new ArrayList<SoortAdministratieveHandeling>());

        assertTrue(magHet);
    }

    @Test
    public final void magVerwerktWordenMetPositiefResultaatDoorOverslaanAdmHnd() {
        final List<SoortAdministratieveHandeling> overslaanSoortAdministratieveHandelingen = new ArrayList<>();
        overslaanSoortAdministratieveHandelingen.add(SoortAdministratieveHandeling.CORRECTIE_ADRES);
        overslaanSoortAdministratieveHandelingen.add(SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND_MET_ERKENNING);
        overslaanSoortAdministratieveHandelingen.add(SoortAdministratieveHandeling.DUMMY);

        final List<Integer> persoonIds = Arrays.asList(1, 2, 3, 4);
        final AdministratieveHandelingModel administratieveHandeling = new AdministratieveHandelingModel(
            new SoortAdministratieveHandelingAttribuut(
                SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND), null, null, DatumTijdAttribuut.nu());

        final boolean magHet = administratieveHandelingVerwerkerRepository.magAdministratieveHandelingVerwerktWorden(
            administratieveHandeling,
            persoonIds,
            overslaanSoortAdministratieveHandelingen);

        assertTrue(magHet);
    }

}
