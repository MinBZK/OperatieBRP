/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.exceptie;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link OnbekendeReferentieExceptie} class.
 */
public class OnbekendeReferentieExceptieTest {

    private static final String WAARDE = "waarde";

    @Test
    public void testConstructorEnGettersEnSetters() {
        final OnbekendeReferentieExceptie exceptie =
            new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE, WAARDE, null);

        Assert.assertNotNull(exceptie);
        Assert.assertNotNull(exceptie.getMessage());
        Assert.assertEquals(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE.getNaam(), exceptie.getReferentieVeldNaam());
        Assert.assertEquals(WAARDE, exceptie.getReferentieWaarde());
    }

    @Test
    public void testConstructorZonderThrowableEnGettersEnSetters() {
        final OnbekendeReferentieExceptie exceptie =
            new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE, WAARDE);

        Assert.assertNotNull(exceptie);
        Assert.assertNotNull(exceptie.getMessage());
        Assert.assertEquals(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE.getNaam(), exceptie.getReferentieVeldNaam());
        Assert.assertEquals(WAARDE, exceptie.getReferentieWaarde());
    }

    @Test
    public void testMetWaardeIsNull() {
        final OnbekendeReferentieExceptie exceptie = new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE, null, null);
        Assert.assertNull(exceptie.getReferentieWaarde());

    }

}
