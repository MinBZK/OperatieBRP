/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository.jdbc;


import org.junit.Test;

public class BasisJdbcRepositoryTest {

    private BasisJdbcRepository subject = new BasisJdbcRepository();

    @Test(expected = IllegalArgumentException.class)
    public void test() {
        subject.getStringResourceData("/bestaatniet.csv.data.bestand");
    }
}
