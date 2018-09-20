/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common.logging;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoggingContextTest {


    @Test
    public void testContext() throws Exception {
        assertFalse(LoggingContext.isKlaarVoorGebruik());
        LoggingContext.registreerActueelAdministratienummer(1234556781L);
        assertTrue(LoggingContext.isKlaarVoorGebruik());
        assertEquals("[a-nummer=1234556781]", LoggingContext.getLogPrefix());
        LoggingContext.reset();
        assertFalse(LoggingContext.isKlaarVoorGebruik());
        assertEquals("[a-nummer=null]", LoggingContext.getLogPrefix());

    }

}