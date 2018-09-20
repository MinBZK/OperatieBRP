/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.repository;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.List;

import nl.bzk.brp.levering.mutatieverwerker.AbstractIntegratieTestMetDatabase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdministratieveHandelingVerwerkerRepositoryTest extends AbstractIntegratieTestMetDatabase {

    @Autowired
    private AdministratieveHandelingVerwerkerRepository administratieveHandelingVerwerkerRepository;

    @Test
    public void testHaalAdministratieveHandelingPersoonIds() {
        final List<Integer>
                persoonIds = administratieveHandelingVerwerkerRepository.haalAdministratieveHandelingPersoonIds(1L);

        assertNotNull(persoonIds);
        assertEquals(Integer.valueOf(1), persoonIds.get(0));
    }

    @Test
    public void testHaalAdministratieveHandelingPersoonBsns() {
        final List<Integer>
                persoonIds = administratieveHandelingVerwerkerRepository.haalAdministratieveHandelingPersoonBsns(1L);

        assertNotNull(persoonIds);
        assertEquals(Integer.valueOf(302533928), persoonIds.get(0));
    }

}
