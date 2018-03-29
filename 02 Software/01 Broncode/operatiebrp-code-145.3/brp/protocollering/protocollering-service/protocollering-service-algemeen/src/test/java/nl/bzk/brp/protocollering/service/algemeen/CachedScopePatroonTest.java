/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.service.algemeen;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.ScopePatroon;
import org.junit.Assert;
import org.junit.Test;

/**
 * CachedScopePatroonTest.
 */
public class CachedScopePatroonTest {

    @Test
    public void testCachedScopePatroonEquals() {

        final ScopePatroon scopePatroon1 = new ScopePatroon();
        final CachedScopePatroon cachedScopePatroon1 = new CachedScopePatroon(scopePatroon1);
        final ScopePatroon scopePatroon2 = new ScopePatroon();
        final CachedScopePatroon cachedScopePatroon2 = new CachedScopePatroon(scopePatroon2);

        Assert.assertTrue(cachedScopePatroon1.equals(cachedScopePatroon1));
        Assert.assertTrue(cachedScopePatroon1.equals(cachedScopePatroon2));

        cachedScopePatroon1.getElementAttribuutSet().add(1);
        cachedScopePatroon2.getElementAttribuutSet().add(1);

        Assert.assertTrue(cachedScopePatroon1.equals(cachedScopePatroon2));

        cachedScopePatroon2.getElementAttribuutSet().add(2);

        Assert.assertFalse(cachedScopePatroon1.equals(cachedScopePatroon2));

    }
}
