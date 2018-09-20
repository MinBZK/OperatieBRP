/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message;

import org.junit.Test;

public class BerichtInhoudExceptionTest {

    @Test(expected = BerichtInhoudException.class)
    public void testExceptie() throws Exception {

        throw new BerichtInhoudException("Bericht inhoud foutief");
    }

    @Test(expected = BerichtInhoudException.class)
    public void testExceptieMetNullPointer() throws Exception {

        throw new BerichtInhoudException("Bericht inhoud foutief.", new NullPointerException());
    }

}
