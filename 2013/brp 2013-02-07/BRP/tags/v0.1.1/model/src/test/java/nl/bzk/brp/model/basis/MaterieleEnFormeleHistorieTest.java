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
 * AbstractMaterieleEnFormeleHistorieEntiteit} klasse.
 */
public class MaterieleEnFormeleHistorieTest {

    @Test
    public void testDatumAanvangGeldigheid() {
        AbstractMaterieleEnFormeleHistorieEntiteit materieleEnFormeleHistorie = bouwMaterieleEnFormeleHistorieInstantie();
        Integer datum = Integer.valueOf(20120325);

        materieleEnFormeleHistorie.setDatumAanvangGeldigheid(datum);
        Assert.assertEquals(datum, materieleEnFormeleHistorie.getDatumAanvangGeldigheid());
    }

    @Test
    public void testDatumAanvangGeldigheidMetNull() {
        AbstractMaterieleEnFormeleHistorieEntiteit materieleEnFormeleHistorie = bouwMaterieleEnFormeleHistorieInstantie();

        materieleEnFormeleHistorie.setDatumAanvangGeldigheid(null);
        Assert.assertNull(materieleEnFormeleHistorie.getDatumAanvangGeldigheid());
    }

    @Test
    public void testDatumEindeGeldigheid() {
        AbstractMaterieleEnFormeleHistorieEntiteit materieleEnFormeleHistorie = bouwMaterieleEnFormeleHistorieInstantie();
        Integer datum = Integer.valueOf(20120325);

        materieleEnFormeleHistorie.setDatumEindeGeldigheid(datum);
        Assert.assertEquals(datum, materieleEnFormeleHistorie.getDatumEindeGeldigheid());
    }

    @Test
    public void testDatumEindeGeldigheidMetNull() {
        AbstractMaterieleEnFormeleHistorieEntiteit materieleEnFormeleHistorie = bouwMaterieleEnFormeleHistorieInstantie();

        materieleEnFormeleHistorie.setDatumEindeGeldigheid(null);
        Assert.assertNull(materieleEnFormeleHistorie.getDatumEindeGeldigheid());
    }

    /**
     * Bouwt een instantie van de {@link AbstractMaterieleEnFormeleHistorieEntiteit} om de materiele historie mee te testen.
     *
     * @return een {@link AbstractMaterieleEnFormeleHistorieEntiteit} instantie.
     */
    private AbstractMaterieleEnFormeleHistorieEntiteit bouwMaterieleEnFormeleHistorieInstantie() {
        AbstractMaterieleEnFormeleHistorieEntiteit materieleEnFormeleHistorieInstantie =
            new AbstractMaterieleEnFormeleHistorieEntiteit() {
            };
        return materieleEnFormeleHistorieInstantie;
    }

}
