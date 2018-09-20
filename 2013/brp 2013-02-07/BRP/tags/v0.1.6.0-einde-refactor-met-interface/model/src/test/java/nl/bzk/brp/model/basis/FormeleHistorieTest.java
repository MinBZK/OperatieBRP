/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import java.util.Date;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Test klasse voor de formele historie functionaliteit welke wordt geboden door de {@link AbstractFormeleHistorieEntiteit}
 * klasse.
 */
public class FormeleHistorieTest {

    @Test
    public void testDatumTijdRegistratie() {
        AbstractFormeleHistorieEntiteit formeleHistorieEntiteit = bouwFormeleHistorieInstantie();
        Date nu = new Date();

        formeleHistorieEntiteit.setDatumTijdRegistratie(nu);
        Assert.assertEquals(nu, formeleHistorieEntiteit.getDatumTijdRegistratie());
        Assert.assertNotSame(
            "Bij het zetten, moet wel de datum worden overgezet, maar niet dezelfde instantie gebruikt worden", nu,
            formeleHistorieEntiteit.getDatumTijdRegistratie());

        nu.setTime(3);
        Assert.assertFalse("Aanpassingen aan datum die gebruikt is om tijdstip vervallen te zetten, "
            + "zou niet dit tijdstip mogen wijzigen", nu.equals(formeleHistorieEntiteit.getDatumTijdRegistratie()));
    }

    @Test
    public void testDatumTijdRegistratieMetNull() {
        AbstractFormeleHistorieEntiteit formeleHistorieEntiteit = bouwFormeleHistorieInstantie();
        Date nu = new Date();

        formeleHistorieEntiteit.setDatumTijdRegistratie(nu);
        Assert.assertNotNull(formeleHistorieEntiteit.getDatumTijdRegistratie());

        formeleHistorieEntiteit.setDatumTijdRegistratie(null);
        Assert.assertNull(formeleHistorieEntiteit.getDatumTijdRegistratie());
    }


    @Test
    public void testDatumTijdVerval() {
        AbstractFormeleHistorieEntiteit formeleHistorieEntiteit = bouwFormeleHistorieInstantie();
        Date nu = new Date();

        formeleHistorieEntiteit.setDatumTijdVerval(nu);
        Assert.assertEquals(nu, formeleHistorieEntiteit.getDatumTijdVerval());
        Assert.assertNotSame(
            "Bij het zetten, moet wel de datum worden overgezet, maar niet dezelfde instantie gebruikt worden", nu,
            formeleHistorieEntiteit.getDatumTijdVerval());

        nu.setTime(3);
        Assert.assertFalse("Aanpassingen aan datum die gebruikt is om tijdstip vervallen te zetten, "
            + "zou niet dit tijdstip mogen wijzigen", nu.equals(formeleHistorieEntiteit.getDatumTijdVerval()));
    }

    @Test
    public void testDatumTijdVervalMetNull() {
        AbstractFormeleHistorieEntiteit formeleHistorieEntiteit = bouwFormeleHistorieInstantie();
        Date nu = new Date();

        formeleHistorieEntiteit.setDatumTijdVerval(nu);
        Assert.assertNotNull(formeleHistorieEntiteit.getDatumTijdVerval());

        formeleHistorieEntiteit.setDatumTijdVerval(null);
        Assert.assertNull(formeleHistorieEntiteit.getDatumTijdVerval());
    }

    /**
     * Bouwt een instantie van de {@link AbstractFormeleHistorieEntiteit} om de formele historie mee te testen.
     *
     * @return een {@link AbstractFormeleHistorieEntiteit} instantie.
     */
    private AbstractFormeleHistorieEntiteit bouwFormeleHistorieInstantie() {
        AbstractFormeleHistorieEntiteit formeleHistorieInstantie = new AbstractFormeleHistorieEntiteit() {
        };
        return formeleHistorieInstantie;
    }
}
