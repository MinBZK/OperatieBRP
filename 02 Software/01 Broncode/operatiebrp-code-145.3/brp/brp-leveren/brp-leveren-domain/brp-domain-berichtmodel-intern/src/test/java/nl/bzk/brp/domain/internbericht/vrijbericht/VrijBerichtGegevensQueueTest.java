/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.vrijbericht;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VrijBerichtGegevensQueueTest {

    @Test
    public void test() {
        assertEquals("VrijBerichtQueue", VrijBerichtGegevensQueue.NAAM.getQueueNaam());
    }
}
