/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatiecontrole.repository;

import java.math.BigInteger;
import java.util.List;

import nl.bzk.brp.levering.mutatiecontrole.AbstractRepositoryTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdministratieveHandelingRepositoryTest extends AbstractRepositoryTestCase {

    @Autowired
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    @Test
    public void testHaalOnverwerkteAdministratieveHandelingenOp() {

        // when
        List<BigInteger> onverwerkteAdministratieveHandelingen = administratieveHandelingRepository.haalOnverwerkteAdministratieveHandelingenOp();

        // then
        Assert.assertNotNull(onverwerkteAdministratieveHandelingen);
        Assert.assertEquals(535, onverwerkteAdministratieveHandelingen.size());
    }

}
