/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.IOException;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import org.junit.Test;

public class LeesGemeenteRegisterVerzoekBerichtTest extends AbstractSyncBerichtTest {

    @Test
    public void test() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final LeesGemeenteRegisterVerzoekBericht bericht = new LeesGemeenteRegisterVerzoekBericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        controleerFormatParse(bericht);
        controleerSerialization(bericht);
    }

}
