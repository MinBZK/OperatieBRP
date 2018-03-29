/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.dao;

import nl.bzk.migratiebrp.isc.jbpm.common.AbstractJbpmDaoTest;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.VirtueelProces;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class VirtueelProcesDaoTest extends AbstractJbpmDaoTest {

    @Autowired
    private VirtueelProcesDao subject;

    public VirtueelProcesDaoTest() {
        super("/sql/mig-create.sql");
    }

    @Test
    public void test() {
        final long id = subject.maak();

        System.out.println("ID: " + id);

        VirtueelProces proces = subject.lees(id);
        Assert.assertNotNull(proces);
        Assert.assertNotNull(proces.getTijdstip());
        Assert.assertTrue(proces.getGerelateerdeGegevens().isEmpty());

        subject.toevoegenGerelateerdeGegevens(id, "TST", "WAARDE");
        proces = subject.lees(id);
        Assert.assertNotNull(proces);
        Assert.assertNotNull(proces.getTijdstip());
        // We doen niet bi-directioneel
        // Assert.assertFalse(proces.getGerelateerdeGegevens().isEmpty());

        subject.verwijder(id);
        proces = subject.lees(id);
        Assert.assertNull(proces);
    }
}
