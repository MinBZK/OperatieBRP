/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.synchronisatie.AbstractDatabaseTest;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.InsertBefore;

import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Test BerichtLogRepository
 */
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class BerichtLogRepositoryTest extends AbstractDatabaseTest {

    @Inject
    private BerichtLogRepository berichtLogRepository;

    /**
     * Zoekt een log record op anummer.
     */
    @Test
    @InsertBefore(value = { "PersoonTestData.xml", "BerichtLogTestData.xml" })
    public void testFindLog() {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            final Date datumVanaf = dateFormat.parse("20020708");
            final Date datumTot = dateFormat.parse("20121212");

            final List<Long> anummers =
                    berichtLogRepository.findLaatsteBerichtLogAnrs(datumVanaf, datumTot, String.valueOf(1904));
            assertNotNull(anummers);
            assertTrue("Er moet minstens 1 anummer gevonden worden.", anummers.size() > 0);
            assertTrue("Het gevonden anummer is niet het verwachte anr.",
                    anummers.get(0).equals(Long.valueOf(123456789)));
        } catch (final ParseException e) {
            fail("Er zou geen fout op moeten treden.");
        }
    }

    /**
     * Zoekt een log record op anummer.
     */
    @Test
    @InsertBefore(value = { "PersoonTestData.xml", "BerichtLogTestData.xml" })
    public void testLogNotFound() {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            final Date datumVanaf = dateFormat.parse("20121212");
            final Date datumTot = dateFormat.parse("20121212");

            final List<Long> anummers = berichtLogRepository.findLaatsteBerichtLogAnrs(datumVanaf, datumTot, null);

            assertNotNull(anummers);
            assertTrue("De lijst zou leeg moeten zijn.", anummers.isEmpty());
        } catch (final ParseException e) {
            fail("Er zou geen fout op moeten treden.");
        }
    }
}
