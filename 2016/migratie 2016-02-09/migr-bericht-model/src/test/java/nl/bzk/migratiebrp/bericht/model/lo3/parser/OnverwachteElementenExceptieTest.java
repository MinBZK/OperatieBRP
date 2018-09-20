/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import org.junit.Test;

/**
 * Exceptie die aangeeft dat er onverwachte elementen werden gevonden tijdens het parsen.
 */
public class OnverwachteElementenExceptieTest {

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testExceptie() {
        throw new OnverwachteElementenExceptie(null, null);
    }
}
