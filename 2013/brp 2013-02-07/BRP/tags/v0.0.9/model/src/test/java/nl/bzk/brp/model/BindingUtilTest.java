/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import nl.bzk.brp.BindingUtil;
import org.junit.Test;

/**
 * Unit test klasse voor de {@link BindingUtil} klasse.
 */
public class BindingUtilTest {

    @Test
    public void testLijstInstantiatie() {
        List nieuweLijst = BindingUtil.newListInstance();
        Assert.assertNotNull(nieuweLijst);
        Assert.assertTrue(nieuweLijst.isEmpty());
    }

    @Test
    public void testSetInstantiatie() {
        Set nieuweSet = BindingUtil.newSetInstance();
        Assert.assertNotNull(nieuweSet);
        Assert.assertTrue(nieuweSet.isEmpty());
    }

    @Test
    public void testTreeSetInstantiatie() {
        Set nieuweSet = BindingUtil.newTreeSetInstance();
        Assert.assertNotNull(nieuweSet);
        Assert.assertTrue(nieuweSet.isEmpty());
    }
}
