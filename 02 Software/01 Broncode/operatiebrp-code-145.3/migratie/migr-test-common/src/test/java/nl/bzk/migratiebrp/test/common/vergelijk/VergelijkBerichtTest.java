/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.vergelijk;

import org.junit.Assert;
import org.junit.Test;

public class VergelijkBerichtTest {

    private static final String
            REISDOCUMENT_2 =
            "121473510002NI3520009NI12345673530008196801013540005B0450355000819730101821000404508220008197301018230017aanvraagformulier851000819680101861000819680102"
                    + "620703510000352000035300003540000355000082100008220000823000085100008610000";
    private static final String
            REISDOCUMENT_1 =
            "121473510002PN3520009PN12345673530008196801013540005B0450355000819730101821000404508220008197301018230017aanvraagformulier851000819680101861000819680102"
                    + "620703510000352000035300003540000355000082100008220000823000085100008610000";
    private static final String INSCHRIJVING = "07035801000400058020017196801010000000005703580100040004802001719550201000000000";
    private static final String HEADER = "00000000Gv01460756942600534";
    private final String BERICHT = HEADER + INSCHRIJVING + REISDOCUMENT_1 + REISDOCUMENT_2;
    private final String BERICHT_MET_REISDOCUMENT_OMGEKEERD = HEADER + INSCHRIJVING + REISDOCUMENT_2 + REISDOCUMENT_1;

    @Test
    public void test() {
        Assert.assertTrue(Vergelijk.vergelijk("{text}", BERICHT));
        Assert.assertTrue(Vergelijk.vergelijk(BERICHT, BERICHT));
        Assert.assertFalse(Vergelijk.vergelijk(BERICHT, BERICHT_MET_REISDOCUMENT_OMGEKEERD));
        Assert.assertTrue(VergelijkGbaBericht.vergelijk("{text}", BERICHT));
        Assert.assertTrue(VergelijkGbaBericht.vergelijk(BERICHT, BERICHT));
        Assert.assertTrue(VergelijkGbaBericht.vergelijk(BERICHT, BERICHT_MET_REISDOCUMENT_OMGEKEERD));
    }
}
