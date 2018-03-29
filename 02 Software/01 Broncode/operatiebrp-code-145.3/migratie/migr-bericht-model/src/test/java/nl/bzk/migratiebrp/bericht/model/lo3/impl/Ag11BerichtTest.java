/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.IOException;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTestBasis;
import org.junit.Test;

public class Ag11BerichtTest extends AbstractLo3BerichtTestBasis {

    private static final String BRON_GEMEENTE = "0599";
    private static final String DOEL_GEMEENTE = "0600";

    @Test
    public void testEmpty() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Ag11Bericht bericht = new Ag11Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void test() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Ag11Bericht bericht = new Ag11Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setBronPartijCode(DOEL_GEMEENTE);
        bericht.setDoelPartijCode(BRON_GEMEENTE);

        testFormatAndParseBericht(bericht);
    }

}
