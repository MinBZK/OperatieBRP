/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import junit.framework.TestCase;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.testrunner.omgeving.Omgeving;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class OmgevingTest extends TestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger();


    @Test
    public void testMaakLegeOmgeving() {
        new OmgevingBouwer().maak();
    }

    @Test
    public void testStartStopLegeOmgeving() {
        final Omgeving omgeving = new OmgevingBouwer().maak();
        assertFalse(omgeving.isGestart());
        assertFalse(omgeving.isGestopt());
        omgeving.start();
        assertTrue(omgeving.isGestart());
        assertFalse(omgeving.isGestopt());
        omgeving.stop();
        assertFalse(omgeving.isGestart());
        assertTrue(omgeving.isGestopt());
    }

}
