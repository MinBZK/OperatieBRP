/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.IOException;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.generated.TypeSynchronisatieBericht;
import org.junit.Assert;
import org.junit.Test;

public class SynchroniseerNaarBrpVerzoekBerichtTest extends AbstractSyncBerichtTestBasis {

    private static final String PL_ALS_TELETEXT =
            "0028001148011001000000012340210003Jan0240006Jansen03100081970010103200040518033000460300410001M6110001E81"
                    +
                    "10004051881200071-X000185100081970010186100081970010208122091000405990920008019701011010001W1030008019701011110006Straat11200021511600069876A"
                    + "A7210001I851000819700101861000819700102";

    @Test
    public void test() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final SynchroniseerNaarBrpVerzoekBericht bericht = new SynchroniseerNaarBrpVerzoekBericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setLo3PersoonslijstAlsTeletexString(PL_ALS_TELETEXT);
        bericht.setTypeBericht(TypeSynchronisatieBericht.LG_01);
        bericht.setVerzendendeGemeente("0599");

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        Assert.assertEquals("SynchroniseerNaarBrpVerzoek", bericht.getBerichtType());
        Assert.assertNull(bericht.getStartCyclus());
        Assert.assertEquals(PL_ALS_TELETEXT, bericht.getLo3PersoonslijstAlsTeletexString());
        Assert.assertNotNull(bericht.getLo3Persoonslijst());
    }

}
