/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.transformers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class IntegerTransformerTest {

    private IntegerTransformer integerTransformer = new IntegerTransformer();

    @Test
    public void testTransform() {
        final String string = "12345";

        final Integer integer = ((Integer) integerTransformer.transform(string));

        Assert.assertEquals(Integer.valueOf(string), integer);
    }

    @Test(expected = NumberFormatException.class)
    public void testTransformFoutief() {
        final String string = "123AA";

        final Integer integer = ((Integer) integerTransformer.transform(string));

        Assert.assertNotEquals(Integer.valueOf(string), integer);
    }

}
