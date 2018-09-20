/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit.ExpectedAfter;
import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit.InsertBefore;
import org.junit.Test;

public class TestOmgevingTest extends AbstractDatabaseTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void spring() {
        assertNotNull("Spring applicationcontext is niet valide", em);
    }

    @InsertBefore("TestData.xml")
    @ExpectedAfter("TestData.xml")
    @Test
    public void dbunitAnnotations() {
    }
}
