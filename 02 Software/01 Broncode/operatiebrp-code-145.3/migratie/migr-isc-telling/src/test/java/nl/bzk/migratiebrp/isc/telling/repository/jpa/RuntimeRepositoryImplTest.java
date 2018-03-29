/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.repository.jpa;

import javax.inject.Inject;
import nl.bzk.migratiebrp.isc.telling.AbstractDatabaseTest;
import nl.bzk.migratiebrp.isc.telling.repository.RuntimeRepository;
import nl.bzk.migratiebrp.isc.telling.util.DBUnit.InsertBefore;
import org.junit.Assert;
import org.junit.Test;

public class RuntimeRepositoryImplTest extends AbstractDatabaseTest {

    private static final String GELDIGE_RUNTIME_NAAM = "tellingen";
    private static final String GELDIGE_RUNTIME_NAAM_NIET_GESTART = "opschonen";

    @Inject
    private RuntimeRepository service;

    @Test
    @InsertBefore("/sql/data/soa_data.xml")
    public void testRuntimeNietGestart() {

        final Boolean resultaat = service.voegRuntimeToe(GELDIGE_RUNTIME_NAAM_NIET_GESTART);

        Assert.assertNotNull(resultaat);
        Assert.assertTrue(resultaat);
    }

    @Test(expected = org.springframework.transaction.UnexpectedRollbackException.class)
    @InsertBefore({"/sql/data/soa_data.xml", "/sql/data/runtime_data.xml"})
    public void testGeldigeRuntimeReedsGestart() {

        final Boolean resultaat = service.voegRuntimeToe(GELDIGE_RUNTIME_NAAM);

        Assert.assertNotNull(resultaat);
        Assert.assertFalse(resultaat);
    }

    @Test
    @InsertBefore("/sql/data/soa_data.xml")
    public void tesVerwijderRuntimeGestart() {

        service.verwijderRuntime(GELDIGE_RUNTIME_NAAM);

    }

    @InsertBefore("/sql/data/soa_data.xml")
    public void testVerwijderenNietGestarteRuntime() {

        service.verwijderRuntime(GELDIGE_RUNTIME_NAAM_NIET_GESTART);

    }
}
