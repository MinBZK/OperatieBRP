/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.inject.Inject;

import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.blokkering.entity.Blokkering;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.blokkering.entity.RedenBlokkering;
import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit.InsertBefore;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test BlokkeringRepository
 */
@Rollback(value = true)
@Transactional(transactionManager = "syncDalTransactionManager")
public class BlokkeringRepositoryTest extends AbstractDatabaseTest {

    @Inject
    private BlokkeringRepository blokkeringRepository;

    /**
     * Test het maken van een blokkering.
     */
    @Test
    @Transactional
    @InsertBefore(value = "BlokkeringTestData.xml")
    public void testBlokkeerPersoonslijst() {
        final Calendar tijdstip = new GregorianCalendar();
        tijdstip.set(Calendar.YEAR, 2012);
        tijdstip.set(Calendar.MONTH, 0);
        tijdstip.set(Calendar.DAY_OF_MONTH, 1);
        tijdstip.set(Calendar.HOUR_OF_DAY, 11);
        tijdstip.set(Calendar.MINUTE, 11);
        tijdstip.set(Calendar.SECOND, 11);
        tijdstip.set(Calendar.MILLISECOND, 111);
        final Long teBlokkerenANummer = 1234567890L;
        final Blokkering teBlokkeren =
                new Blokkering(teBlokkerenANummer, 1L, "0599", "0600", RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP, new Timestamp(
                    tijdstip.getTimeInMillis()));

        final Blokkering blokkering = blokkeringRepository.blokkeerPersoonslijst(teBlokkeren);

        assertNotNull("Blokkering is niet aangemaakt.", blokkering);
        assertEquals("Het proces id komt niet overeen met het opgegeven procs id", teBlokkeren.getProcessId(), blokkering.getProcessId());
        assertEquals("Het tijdstip komt niet overeen met de verwachting", new Timestamp(tijdstip.getTimeInMillis()), blokkering.getTijdstip());
    }

    /**
     * Test het maken van een blokkering zonder blokkering.
     */
    @Test(expected = NullPointerException.class)
    @InsertBefore(value = "BlokkeringTestData.xml")
    public void testBlokkeerPersoonslijstZonderBlokkering() {
        blokkeringRepository.blokkeerPersoonslijst(null);
    }

    /**
     * Test het opvragen van een blokkering.
     */
    @Test
    @InsertBefore(value = "BlokkeringTestData.xml")
    public void testStatusBlokkering() {
        final Long aNummer = 1234567890L;
        final Blokkering blokkering = blokkeringRepository.statusBlokkering(aNummer);
        assertNotNull("De blokkering kon niet worden gevonden.", blokkering);
        assertEquals("Het A-nummer komt niet overeen met het opgegeven A-nummer.", aNummer, blokkering.getaNummer());
        assertEquals("Het process id komt niet overeen met de verwachting", Long.valueOf(1), blokkering.getProcessId());
        assertEquals("De registratiegemeent komt niet overeen met de verwachting", "0600", blokkering.getRegistratieGemeente());
    }

    /**
     * Test het opvragen van een blokkering zonder A-nummer.
     */
    @Test(expected = NullPointerException.class)
    @InsertBefore(value = "BlokkeringTestData.xml")
    public void testStatusBlokkeringZonderANummer() {
        blokkeringRepository.statusBlokkering(null);
    }

    /**
     * Test het deblokkeren van een blokkering.
     */
    @Test
    @Ignore
    @InsertBefore(value = "BlokkeringTestData.xml")
    public void testDeblokkering() {
        final Long aNummer = 1234567890L;
        final Blokkering blokkering = blokkeringRepository.statusBlokkering(aNummer);
        blokkeringRepository.deblokkeerPersoonslijst(blokkering);
    }

    /**
     * Test het deblokkeren zonder een blokkering.
     */
    @Test(expected = NullPointerException.class)
    @InsertBefore(value = "BlokkeringTestData.xml")
    public void testDeblokkeringZonderANummer() {
        blokkeringRepository.deblokkeerPersoonslijst(null);
    }
}
