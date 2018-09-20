/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit.InsertBefore;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test BerichtLogRepository
 */
@Rollback(value = true)
@Transactional(transactionManager = "syncDalTransactionManager")
public class BerichtLogRepositoryTest extends AbstractDatabaseTest {

    @Inject
    private Lo3BerichtRepository berichtLogRepository;

    /**
     * Zoekt log record anummer(s), op basis van 'vanaf' en 'tot'.
     */
    @Test
    @InsertBefore(value = {"PersoonTestData.xml", "BerichtLogTestData.xml" })
    public void testFind3LogANummersVanafTotZonderGemeentecode() {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            final Date datumVanaf = dateFormat.parse("20120101");
            final Date datumTot = dateFormat.parse("20121231");

            final Set<Long> anummers = berichtLogRepository.findLaatsteBerichtLogAnrs(datumVanaf, datumTot);
            assertNotNull(anummers);
            assertEquals("Er moeten 3 anummers gevonden worden.", 4, anummers.size());
            assertTrue("De set anummers bevat niet het verwachte anummer", anummers.contains(123456789L));
            assertTrue("De set anummers bevat niet het verwachte anummer", anummers.contains(123456790L));
            assertTrue("De set anummers bevat niet het verwachte anummer", anummers.contains(123456791L));
            assertTrue("De set anummers bevat niet het verwachte anummer", anummers.contains(987654321L));
        } catch (final ParseException e) {
            fail("Er zou geen fout op moeten treden.");
        }
    }

    /**
     * Zoekt log record anummer(s) op een datum zonder logs.
     */
    @Test
    @InsertBefore(value = {"PersoonTestData.xml", "BerichtLogTestData.xml" })
    public void testNoANummersFoundVanafTotZonderGemeente() {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            final Date datumVanaf = dateFormat.parse("20121231");
            final Date datumTot = dateFormat.parse("20121231");

            final Set<Long> anummers = berichtLogRepository.findLaatsteBerichtLogAnrs(datumVanaf, datumTot);

            assertNotNull(anummers);
            assertTrue("De lijst zou leeg moeten zijn.", anummers.isEmpty());
        } catch (final ParseException e) {
            fail("Er zou geen fout op moeten treden.");
        }
    }

    /**
     * Zoekt en vindt een BerichtLog op basis van het anummer.
     */
    @Test
    @InsertBefore(value = {"PersoonTestData.xml", "BerichtLogTestData.xml" })
    public void testFindLaatsteBerichtLogVoorANummer() {
        final Long bestaandANummer = 123456789L;
        final Lo3Bericht bericht = berichtLogRepository.findLaatsteLo3PersoonslijstBerichtVoorANummer(bestaandANummer);
        assertNotNull(bericht);
        assertEquals(Long.valueOf(4), bericht.getId());

    }

    /**
     * Zoekt en vindt een BerichtLog op basis van het anummer.
     */
    @Test
    @InsertBefore(value = {"PersoonTestData.xml", "BerichtLogTestData.xml" })
    public void testFindLaatsteBerichtLogVoorANummerBerichtNietGevonden() {
        final Lo3Bericht bericht = berichtLogRepository.findLaatsteLo3PersoonslijstBerichtVoorANummer(987654321L);
        assertNull(bericht);
    }

    /**
     * Zoekt, maar vindt geen BerichtLog op basis van het anummer.
     */
    @Test
    @InsertBefore(value = {"PersoonTestData.xml", "BerichtLogTestData.xml" })
    public void testFindLaatsteBerichtLogVoorANummerZonderBerichtLog() {
        final Long nietBestaandANummer = 12345L;
        final Lo3Bericht bericht = berichtLogRepository.findLaatsteLo3PersoonslijstBerichtVoorANummer(nietBestaandANummer);

        assertNull(bericht);
    }
}
