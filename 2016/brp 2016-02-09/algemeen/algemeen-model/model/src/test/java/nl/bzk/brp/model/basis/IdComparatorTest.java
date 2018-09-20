/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link nl.bzk.brp.model.basis.IdComparator} klasse.
 */
public class IdComparatorTest {

    private final IdComparator comparator = new IdComparator();

    @Test
    public void testComparatorLong() {
        final ModelIdentificeerbaar<Long> obj1 = bouwModelIdentificeerbaarObject(1L);
        final ModelIdentificeerbaar<Long> obj2 = bouwModelIdentificeerbaarObject(2L);

        Assert.assertTrue(comparator.compare(obj1, obj1) == 0);
        Assert.assertTrue(comparator.compare(obj1, bouwModelIdentificeerbaarObject(1L)) == 0);
        Assert.assertTrue(comparator.compare(obj1, obj2) < 0);
        Assert.assertTrue(comparator.compare(obj2, obj1) > 0);
    }

    @Test
    public void testComparatorInteger() {
        final ModelIdentificeerbaar<Integer> obj1 = bouwModelIdentificeerbaarObject(1);
        final ModelIdentificeerbaar<Integer> obj2 = bouwModelIdentificeerbaarObject(2);

        Assert.assertTrue(comparator.compare(obj1, obj1) == 0);
        Assert.assertTrue(comparator.compare(obj1, bouwModelIdentificeerbaarObject(1)) == 0);
        Assert.assertTrue(comparator.compare(obj1, obj2) < 0);
        Assert.assertTrue(comparator.compare(obj2, obj1) > 0);
    }

    @Test
    public void testComparatorShort() {
        final ModelIdentificeerbaar<Short> obj1 = bouwModelIdentificeerbaarObject((short) 1);
        final ModelIdentificeerbaar<Short> obj2 = bouwModelIdentificeerbaarObject((short) 2);

        Assert.assertTrue(comparator.compare(obj1, obj1) == 0);
        Assert.assertTrue(comparator.compare(obj1, bouwModelIdentificeerbaarObject((short) 1)) == 0);
        Assert.assertTrue(comparator.compare(obj1, obj2) < 0);
        Assert.assertTrue(comparator.compare(obj2, obj1) > 0);
    }

    @Test
    public void testNullIds() {
        Assert.assertTrue(comparator.compare(bouwModelIdentificeerbaarObject(null),
            bouwModelIdentificeerbaarObject(1)) > 0);
        Assert.assertTrue(comparator.compare(bouwModelIdentificeerbaarObject(1),
            bouwModelIdentificeerbaarObject(null)) < 0);
        Assert.assertTrue(comparator.compare(bouwModelIdentificeerbaarObject(null),
            bouwModelIdentificeerbaarObject(null)) == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullObject1() {
        comparator.compare(null, bouwModelIdentificeerbaarObject(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullObject2() {
        comparator.compare(bouwModelIdentificeerbaarObject(1), null);
    }

    /**
     * Instantieert en retourneert een {@link ModelIdentificeerbaar} object met opgegeven nummer als id.
     *
     * @param id het nummer dat het object als id dient te retourneren.
     * @return een ModelIdentificeerbaar object.
     */
    private <T extends Number> ModelIdentificeerbaar<T> bouwModelIdentificeerbaarObject(final T id) {
        return new ModelIdentificeerbaar<T>() {
            @Override
            public T getID() {
                return id;
            }
        };
    }
}
