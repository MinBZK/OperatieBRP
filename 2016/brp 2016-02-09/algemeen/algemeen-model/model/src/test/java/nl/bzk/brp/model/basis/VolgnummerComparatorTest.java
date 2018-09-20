/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.operationeel.kern.PersoonVoornaamModel;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link VolgnummerComparator} klasse.
 */
public class VolgnummerComparatorTest {

    private final VolgnummerComparator comparator = new VolgnummerComparator();

    @Test
    public void testComparator() {
        final VolgnummerBevattend obj1 = bouwVolgnummerBevattendObject(1);
        final VolgnummerBevattend obj2 = bouwVolgnummerBevattendObject(2);

        Assert.assertTrue(comparator.compare(obj1, obj1) == 0);
        Assert.assertTrue(comparator.compare(obj1, bouwVolgnummerBevattendObject(1)) == 0);
        Assert.assertTrue(comparator.compare(obj1, obj2) < 0);
        Assert.assertTrue(comparator.compare(obj2, obj1) > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullObject1() {
        comparator.compare(null, bouwVolgnummerBevattendObject(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullObject2() {
        comparator.compare(bouwVolgnummerBevattendObject(1), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullVolgnummer1() {
        comparator.compare(new VolgnummerBevattend() {

            @Override
            public VolgnummerAttribuut getVolgnummer() {
                return null;
            }
        }, bouwVolgnummerBevattendObject(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullVolgnummer2() {
        comparator.compare(bouwVolgnummerBevattendObject(1), new VolgnummerBevattend() {

            @Override
            public VolgnummerAttribuut getVolgnummer() {
                return null;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullVolgnummerWaarde1() {
        comparator.compare(bouwVolgnummerBevattendObject(null), bouwVolgnummerBevattendObject(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullVolgnummerWaarde2() {
        comparator.compare(bouwVolgnummerBevattendObject(1), bouwVolgnummerBevattendObject(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVerschillendeKlassesVergelijking() {
        final PersoonVoornaamModel voornaam = new PersoonVoornaamModel(null, new VolgnummerAttribuut(2));
        comparator.compare(bouwVolgnummerBevattendObject(1), voornaam);
    }

    /**
     * Instantieert en retourneert een {@link VolgnummerBevattend} object met opgegeven volgnummer.
     *
     * @param volgnummer het volgnummer dat het object dient te retourneren.
     * @return een VolgnummerBevattend object.
     */
    private VolgnummerBevattend bouwVolgnummerBevattendObject(final Integer volgnummer) {
        return new VolgnummerBevattend() {

            @Override
            public VolgnummerAttribuut getVolgnummer() {
                return new VolgnummerAttribuut(volgnummer);
            }
        };
    }
}
