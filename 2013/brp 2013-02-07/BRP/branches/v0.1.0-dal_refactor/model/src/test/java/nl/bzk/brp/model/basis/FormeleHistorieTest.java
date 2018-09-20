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
 * Test klasse voor de formele historie functionaliteit welke wordt geboden door de {@link AbstractFormeleHistorie}
 * klasse.
 */
public class FormeleHistorieTest {

    @Test
    public void testDatumTijdRegistratie() {
        AbstractFormeleHistorie formeleHistorie = bouwFormeleHistorieInstantie();
        Date nu = new Date();

        formeleHistorie.setDatumTijdRegistratie(nu);
        Assert.assertEquals(nu, formeleHistorie.getDatumTijdRegistratie());
        Assert.assertNotSame(
            "Bij het zetten, moet wel de datum worden overgezet, maar niet dezelfde instantie gebruikt worden", nu,
            formeleHistorie.getDatumTijdRegistratie());

        nu.setTime(3);
        Assert.assertFalse("Aanpassingen aan datum die gebruikt is om tijdstip vervallen te zetten, "
            + "zou niet dit tijdstip mogen wijzigen", nu.equals(formeleHistorie.getDatumTijdRegistratie()));
    }

    @Test
    public void testDatumTijdRegistratieMetNull() {
        AbstractFormeleHistorie formeleHistorie = bouwFormeleHistorieInstantie();
        Date nu = new Date();

        formeleHistorie.setDatumTijdRegistratie(nu);
        Assert.assertNotNull(formeleHistorie.getDatumTijdRegistratie());

        formeleHistorie.setDatumTijdRegistratie(null);
        Assert.assertNull(formeleHistorie.getDatumTijdRegistratie());
    }


    @Test
    public void testDatumTijdVerval() {
        AbstractFormeleHistorie formeleHistorie = bouwFormeleHistorieInstantie();
        Date nu = new Date();

        formeleHistorie.setDatumTijdVerval(nu);
        Assert.assertEquals(nu, formeleHistorie.getDatumTijdVerval());
        Assert.assertNotSame(
            "Bij het zetten, moet wel de datum worden overgezet, maar niet dezelfde instantie gebruikt worden", nu,
            formeleHistorie.getDatumTijdVerval());

        nu.setTime(3);
        Assert.assertFalse("Aanpassingen aan datum die gebruikt is om tijdstip vervallen te zetten, "
            + "zou niet dit tijdstip mogen wijzigen", nu.equals(formeleHistorie.getDatumTijdVerval()));
    }

    @Test
    public void testDatumTijdVervalMetNull() {
        AbstractFormeleHistorie formeleHistorie = bouwFormeleHistorieInstantie();
        Date nu = new Date();

        formeleHistorie.setDatumTijdVerval(nu);
        Assert.assertNotNull(formeleHistorie.getDatumTijdVerval());

        formeleHistorie.setDatumTijdVerval(null);
        Assert.assertNull(formeleHistorie.getDatumTijdVerval());
    }

    /**
     * Bouwt een instantie van de {@link AbstractFormeleHistorie} om de formele historie mee te testen.
     *
     * @return een {@link AbstractFormeleHistorie} instantie.
     */
    private AbstractFormeleHistorie bouwFormeleHistorieInstantie() {
        AbstractFormeleHistorie formeleHistorieInstantie = new AbstractFormeleHistorie() {
        };
        return formeleHistorieInstantie;
    }
}
