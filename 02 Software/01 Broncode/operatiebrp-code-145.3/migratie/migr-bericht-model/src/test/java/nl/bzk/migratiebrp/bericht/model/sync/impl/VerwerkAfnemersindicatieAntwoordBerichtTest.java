/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatieFoutcodeType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;

import org.junit.Assert;
import org.junit.Test;

public class VerwerkAfnemersindicatieAntwoordBerichtTest extends AbstractSyncBerichtTestBasis {

    @Test
    public void test() throws Exception {
        final VerwerkAfnemersindicatieAntwoordBericht bericht = new VerwerkAfnemersindicatieAntwoordBericht();
        Assert.assertEquals(StatusType.OK, bericht.getStatus());

        bericht.setStatus(StatusType.FOUT);
        bericht.setFoutcode(AfnemersindicatieFoutcodeType.I);

        Assert.assertEquals(StatusType.FOUT, bericht.getStatus());
        Assert.assertEquals(AfnemersindicatieFoutcodeType.I, bericht.getFoutcode());

        controleerFormatParse(bericht);
        controleerSerialization(bericht);
    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerStatus() {
        final VerwerkAfnemersindicatieAntwoordBericht bericht = new VerwerkAfnemersindicatieAntwoordBericht();
        bericht.setStatus(null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerFoutcode() {
        final VerwerkAfnemersindicatieAntwoordBericht bericht = new VerwerkAfnemersindicatieAntwoordBericht();
        bericht.setFoutcode(null);
    }

}
