/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.synchronisatie.AbstractDatabaseTest;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Land;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.InsertBefore;

import org.junit.Test;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(defaultRollback = false)
public class DynamischeStamtabelRepositoryTest extends AbstractDatabaseTest {

    @Inject
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    @InsertBefore("LandTestData.xml")
    @Test
    public void vraagBepaaldLandOpMetCode() {
        final Land land = dynamischeStamtabelRepository.findLandByLandcode(new BigDecimal("6030"));
        assertNotNull(land);
        assertEquals("Nederland", land.getNaam());
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void vraagBepaaldLandOpMetCodeZonderSucces() {
        dynamischeStamtabelRepository.findLandByLandcode(new BigDecimal("9999"));
    }
}
