/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.configuratie;

import nl.moderniseringgba.isc.jbpm.AbstractJbpmDaoTest;

import org.jbpm.calendar.Duration;
import org.junit.Assert;
import org.junit.Test;

public class JbpmConfiguratieDaoTest extends AbstractJbpmDaoTest {

    private final JbpmConfiguratieDao subject = new JbpmConfiguratieDao();

    public JbpmConfiguratieDaoTest() {
        super("/sql/configuratie.sql");
    }

    @Test
    public void test() {
        Assert.assertNull(subject.getConfiguratie("bla"));
        Assert.assertEquals("4 hours", subject.getConfiguratie("brp.timeout"));
    }

    @Test
    public void testInt() {
        Assert.assertNull(subject.getConfiguratieAsInteger("bla"));
        Assert.assertEquals(Integer.valueOf(2), subject.getConfiguratieAsInteger("brp.herhalingen"));
    }

    @Test
    public void testDuration() {
        Assert.assertNull(subject.getConfiguratieAsDuration("bla"));

        final Duration duration = subject.getConfiguratieAsDuration("brp.timeout");

        Assert.assertEquals(new Duration("4 hours").getMilliseconds(), duration.getMilliseconds());
    }
}
