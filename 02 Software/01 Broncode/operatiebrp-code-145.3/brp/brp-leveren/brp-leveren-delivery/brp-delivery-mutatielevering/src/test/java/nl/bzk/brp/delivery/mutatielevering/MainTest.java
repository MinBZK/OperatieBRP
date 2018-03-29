/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.mutatielevering;

import static nl.bzk.brp.delivery.mutatielevering.Main.MUTATIELEVERING_BOOTER;

import nl.bzk.brp.delivery.mutatielevering.boot.Booter;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Suffe test
 */
public class MainTest {

    @Test
    public void testMain() {

        final Booter mock = Mockito.mock(Booter.class);
        System.getProperties().put(MUTATIELEVERING_BOOTER, mock);
        Main.main(null);
        Mockito.verify(mock).springboot(Mockito.any());
    }
}
