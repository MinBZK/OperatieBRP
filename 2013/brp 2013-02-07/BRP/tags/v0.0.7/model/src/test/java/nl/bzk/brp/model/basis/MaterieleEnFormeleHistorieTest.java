/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Test klasse voor de materiele historie functionaliteit welke wordt geboden door de {@link
 * AbstractMaterieleEnFormeleHistorie} klasse.
 */
public class MaterieleEnFormeleHistorieTest {

    @Test
    public void testDatumAanvangGeldigheid() {
        AbstractMaterieleEnFormeleHistorie materieleEnFormeleHistorie = bouwMaterieleEnFormeleHistorieInstantie();
        Integer datum = Integer.valueOf(20120325);

        materieleEnFormeleHistorie.setDatumAanvangGeldigheid(datum);
        Assert.assertEquals(datum, materieleEnFormeleHistorie.getDatumAanvangGeldigheid());
    }

    @Test
    public void testDatumAanvangGeldigheidMetNull() {
        AbstractMaterieleEnFormeleHistorie materieleEnFormeleHistorie = bouwMaterieleEnFormeleHistorieInstantie();

        materieleEnFormeleHistorie.setDatumAanvangGeldigheid(null);
        Assert.assertNull(materieleEnFormeleHistorie.getDatumAanvangGeldigheid());
    }

    @Test
    public void testDatumEindeGeldigheid() {
        AbstractMaterieleEnFormeleHistorie materieleEnFormeleHistorie = bouwMaterieleEnFormeleHistorieInstantie();
        Integer datum = Integer.valueOf(20120325);

        materieleEnFormeleHistorie.setDatumEindeGeldigheid(datum);
        Assert.assertEquals(datum, materieleEnFormeleHistorie.getDatumEindeGeldigheid());
    }

    @Test
    public void testDatumEindeGeldigheidMetNull() {
        AbstractMaterieleEnFormeleHistorie materieleEnFormeleHistorie = bouwMaterieleEnFormeleHistorieInstantie();

        materieleEnFormeleHistorie.setDatumEindeGeldigheid(null);
        Assert.assertNull(materieleEnFormeleHistorie.getDatumEindeGeldigheid());
    }

    /**
     * Bouwt een instantie van de {@link AbstractMaterieleEnFormeleHistorie} om de materiele historie mee te testen.
     *
     * @return een {@link AbstractMaterieleEnFormeleHistorie} instantie.
     */
    private AbstractMaterieleEnFormeleHistorie bouwMaterieleEnFormeleHistorieInstantie() {
        AbstractMaterieleEnFormeleHistorie materieleEnFormeleHistorieInstantie =
            new AbstractMaterieleEnFormeleHistorie() {
            };
        return materieleEnFormeleHistorieInstantie;
    }

}
