/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.vergelijk;

import org.junit.Assert;
import org.junit.Test;

public class SorteerJsonTest {

    @Test
    public void test() {
        final String ongesorteerd = "{\"c\":\"valueA\",\"a\":\"valueC\",\"b\":[\"z\",\"y\",\"x\"]}";
        final String gesorteerd = SorteerJson.sorteer(ongesorteerd);
        Assert.assertEquals("{\"a\":\"valueC\",\"b\":[\"x\",\"y\",\"z\"],\"c\":\"valueA\"}", gesorteerd);

    }

}
