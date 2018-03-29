/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

public class PutMessageOperationTest {

    @Test
    public void constructor() {
        assertThat(
                new PutMessageOperation(Arrays.asList(
                        PutEnvelope.fromOperationItems("thename221611031510Z1"),
                        PutMessageHeading.fromOperationItems("112233445566665544332211       00176543210"),
                        PutMessageBody.fromOperationItems("body"))),
                instanceOf(PutMessageOperation.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void tooFewOperationRecords() {
        new PutMessageOperation(Collections.singletonList(PutEnvelope.fromOperationItems("thename221611031510Z1")));
    }
}
