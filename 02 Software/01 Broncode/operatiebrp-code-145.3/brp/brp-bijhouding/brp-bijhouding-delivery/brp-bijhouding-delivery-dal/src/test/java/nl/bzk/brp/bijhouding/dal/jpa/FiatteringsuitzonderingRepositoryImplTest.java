/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhouderFiatteringsuitzondering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.test.dal.DBUnit;
import nl.bzk.brp.bijhouding.dal.AbstractRepositoryTest;
import nl.bzk.brp.bijhouding.dal.FiatteringsuitzonderingRepository;
import org.junit.Test;

/**
 * Testen voor {@link FiatteringsuitzonderingRepositoryImpl}.
 */
public class FiatteringsuitzonderingRepositoryImplTest extends AbstractRepositoryTest {
    @Inject
    FiatteringsuitzonderingRepository fiatteringsuitzonderingRepository;

    @Inject
    private DynamischeStamtabelRepository stamtabelRepository;

    @DBUnit.InsertBefore("Fiaterringsuitzondering.xml")
    @Test
    public void testFindBijhouderFiatteringsuitzonderingen() {
        final Partij almere = stamtabelRepository.getPartijByCode("003401");
        final List<BijhouderFiatteringsuitzondering> bijhouderFiatteringsuitzonderingen =
                fiatteringsuitzonderingRepository.findBijhouderFiatteringsuitzonderingen(almere);
        assertNotNull(bijhouderFiatteringsuitzonderingen);
        assertEquals(bijhouderFiatteringsuitzonderingen.size(), 2);
        final Partij amsterdam = stamtabelRepository.getPartijByCode("036301");
        assertTrue(fiatteringsuitzonderingRepository.findBijhouderFiatteringsuitzonderingen(amsterdam).isEmpty());
    }

}
