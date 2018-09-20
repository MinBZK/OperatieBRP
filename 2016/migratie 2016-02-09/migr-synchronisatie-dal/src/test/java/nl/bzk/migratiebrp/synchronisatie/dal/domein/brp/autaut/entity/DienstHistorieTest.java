/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

import java.sql.Timestamp;
import org.junit.Test;

public class DienstHistorieTest {

    @Test(expected = NullPointerException.class)
    public void abonnementConstructorNullDatumIngang() {
        new DienstHistorie(null, null, 0);
    }

    @Test(expected = NullPointerException.class)
    public void abonnementConstructorNullToestand() {
        new DienstHistorie(new Dienst(), null, 0);
    }

    @Test(expected = NullPointerException.class)
    public void abonnementConstructorNullDienst() {
        new DienstHistorie(null, new Timestamp(System.currentTimeMillis()), 0);
    }
}
