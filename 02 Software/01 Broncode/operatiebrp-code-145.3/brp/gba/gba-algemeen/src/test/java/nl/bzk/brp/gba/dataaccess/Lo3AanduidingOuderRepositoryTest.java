/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */


package nl.bzk.brp.gba.dataaccess;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.AanduidingOuder;
import nl.bzk.algemeenbrp.test.dal.DBUnit;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Lo3AanduidingOuderRepositoryTest extends AbstractIntegratieTest {

    @Autowired
    private Lo3AanduidingOuderRepository repository;

    /**
     * Test of getOuderIdentificatie method, of class Lo3AanduidingOuderRepository.
     */
    @DBUnit.InsertBefore({"/data/kern.xml", "/data/autaut.xml", "/data/ist.xml"})
    @Test
    public void testGetOuderIdentificatie1() {
        final AanduidingOuder result = repository.getOuderIdentificatie(11L);
        Assert.assertEquals(AanduidingOuder.OUDER_1, result);
    }

    /**
     * Test of getOuderIdentificatie method, of class Lo3AanduidingOuderRepository.
     */
    @DBUnit.InsertBefore({"/data/kern.xml", "/data/autaut.xml", "/data/ist.xml"})
    @Test
    public void testGetOuderIdentificatie2() {
        final AanduidingOuder result = repository.getOuderIdentificatie(12L);
        Assert.assertEquals(AanduidingOuder.OUDER_2, result);
    }

    /**
     * Test of getOuderIdentificatie method, of class Lo3AanduidingOuderRepository.
     */
    @DBUnit.InsertBefore({"/data/kern.xml", "/data/autaut.xml", "/data/ist.xml"})
    @Test
    public void testGetOuderIdentificatieLeeg() {
        final AanduidingOuder result = repository.getOuderIdentificatie(14L);
        Assert.assertNull(result);
    }

    /**
     * Test of setAanduidingOuderBijOuderBetrokkenheid method, of class Lo3AanduidingOuderRepository.
     */
    @DBUnit.InsertBefore({"/data/kern.xml", "/data/autaut.xml", "/data/ist.xml"})
    @Test
    public void testSetAanduidingOuderBijOuderBetrokkenheid() {
        repository.setAanduidingOuderBijOuderBetrokkenheid(15L, AanduidingOuder.OUDER_2);
        final AanduidingOuder result = repository.getOuderIdentificatie(15L);
        Assert.assertEquals(AanduidingOuder.OUDER_2, result);
    }
}
