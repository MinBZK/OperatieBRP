/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests voor All class.
 */
public class AnyTest {

    @Test
    public void zouWaarMoetenZijnAlsGeenParametersMeegegevenVoorIsNullCheck() {
        Assert.assertTrue(Any.isNull());
    }

    @Test
    public void zouWaarMoetenZijnAlsEenOfMeerdereParametersNullIs() {
        Assert.assertTrue(Any.isNull("a", "b", null, 1));
    }

    @Test
    public void zouNietWaarMoetenZijnAlsGeenParameterNullIs() {
        Assert.assertFalse(Any.isNull("a", "a"));
    }

    @Test
    public void zouWaarMoetenZijnAlsEenParameterNietNullIsVoorNietNullCheck() {
        Assert.assertTrue(Any.isNotNull(null, "a", null));
    }
}
