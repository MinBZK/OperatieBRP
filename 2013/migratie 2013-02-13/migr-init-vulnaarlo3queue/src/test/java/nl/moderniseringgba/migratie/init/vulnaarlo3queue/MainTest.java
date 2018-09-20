/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.init.vulnaarlo3queue;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

//CHECKSTYLE:OFF
public class MainTest {

    @Test
    public void testMainHappy() {
        final String[] args = new String[] { "-config", "test-config.properties" };
        try {
            Main.setSpringConfig("vulnaarlo3queue-test-beans.xml");
            Main.main(args);
        } catch (final Exception e) {
            e.printStackTrace();
            fail("Exception opgetreden bij het draaien van init vulling.");
        }
    }

    @Test
    public void testMainNoConfig() {
        final String[] args = new String[] {};
        try {
            Main.setSpringConfig("vulnaarlo3queue-test-beans.xml");
            Main.main(args);
        } catch (final Exception e) {
            e.printStackTrace();
            fail("Onverwachte exception opgetreden");
        }
        assertTrue("", true);
    }

    @Test
    public void testMainWrongConfig() {
        final String[] args = new String[] { "-config", "t-config.properties" };
        try {
            Main.setSpringConfig("vulnaarlo3queue-test-beans.xml");
            Main.main(args);
        } catch (final Exception e) {
            assertTrue("Geen exception die wel verwacht was.", e instanceof NullPointerException);
            return;
        }
        fail("Geen verwachte exception gevangen.");
    }
}
