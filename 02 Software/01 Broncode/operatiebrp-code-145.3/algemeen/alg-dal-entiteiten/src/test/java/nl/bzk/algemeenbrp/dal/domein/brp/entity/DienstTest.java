/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import org.junit.Test;

public class DienstTest {

    @Test(expected = NullPointerException.class)
    public void abonnementConstructorNullAbonnement() {
        new Dienst(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void abonnementConstructorNullCatalogusOptie() {
        new Dienst(new Dienstbundel(), null);
    }
}
