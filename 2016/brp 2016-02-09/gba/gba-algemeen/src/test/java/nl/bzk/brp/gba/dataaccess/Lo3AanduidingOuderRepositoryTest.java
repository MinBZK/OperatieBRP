/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */


package nl.bzk.brp.gba.dataaccess;

import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Lo3AanduidingOuderRepositoryTest extends AbstractIntegratieTest {

    @Autowired
    private Lo3AanduidingOuderRepository repository;

    /**
     * Test of getOuderIdentificatie method, of class Lo3AanduidingOuderRepository.
     */
    @Test
    public void testGetOuderIdentificatie1() {
        final LO3SoortAanduidingOuder result = repository.getOuderIdentificatie(11);
        Assert.assertEquals(LO3SoortAanduidingOuder.OUDER1, result);
    }

    /**
     * Test of getOuderIdentificatie method, of class Lo3AanduidingOuderRepository.
     */
    @Test
    public void testGetOuderIdentificatie2() {
        final LO3SoortAanduidingOuder result = repository.getOuderIdentificatie(12);
        Assert.assertEquals(LO3SoortAanduidingOuder.OUDER2, result);
    }

    /**
     * Test of getOuderIdentificatie method, of class Lo3AanduidingOuderRepository.
     */
    @Test
    public void testGetOuderIdentificatieLeeg() {
        final LO3SoortAanduidingOuder result = repository.getOuderIdentificatie(14);
        Assert.assertNull(result);
    }

    /**
     * Test of setAanduidingOuderBijOuderBetrokkenheid method, of class Lo3AanduidingOuderRepository.
     */
    @Test
    public void testSetAanduidingOuderBijOuderBetrokkenheid() {
        repository.setAanduidingOuderBijOuderBetrokkenheid(15, LO3SoortAanduidingOuder.OUDER2);
        final LO3SoortAanduidingOuder result = repository.getOuderIdentificatie(15);
        Assert.assertEquals(LO3SoortAanduidingOuder.OUDER2, result);
    }

}
