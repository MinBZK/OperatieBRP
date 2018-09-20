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
public class AllTest {

    @Test
    public void zouWaarMoetenZijnAlsGeenParametersMeegegevenVoorIsNullCheck() {
        Assert.assertTrue(All.isNull());
    }

    @Test
    public void zouWaarMoetenZijnAlsAlleParametersNullZijnVoorIsNullCheck() {
        Assert.assertTrue(All.isNull(null, null));
    }

    @Test
    public void zouNietWaarMoetenZijnAlsEenParameterNietNullIsVoorIsNullCheck() {
        Assert.assertFalse(All.isNull(null, "a", null, null));
    }

    @Test
    public void zouWaarMoetenZijnAlsGeenParameterNullIsVoorNietNullCheck() {
        Assert.assertTrue(All.isNotNull("a", "b"));
    }

    @Test
    public void zouNietWaarMoetenZijnAlsGeenParametersMeegegevenVoorNietNullCheck() {
        Assert.assertFalse(All.isNotNull());
    }
    @Test
    public void zouNietWaarMoetenZijnAlsEenParameterNullIsVoorNietNullCheck() {
        Assert.assertFalse(All.isNotNull("a", null, "b"));
    }

}
