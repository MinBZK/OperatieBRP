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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.RedenBlokkering;
import nl.bzk.algemeenbrp.test.dal.DBUnit.InsertBefore;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test BlokkeringRepository
 */
@Rollback()
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
        final String teBlokkerenANummer = "1234567890";
        final Blokkering teBlokkeren = new Blokkering(teBlokkerenANummer, new Timestamp(tijdstip.getTimeInMillis()));
        teBlokkeren.setProcessId(1L);
        teBlokkeren.setGemeenteCodeNaar("059901");
        teBlokkeren.setRegistratieGemeente("060001");
        teBlokkeren.setRedenBlokkering(RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP);

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
        final String aNummer = "1234567890";
        final Blokkering blokkering = blokkeringRepository.statusBlokkering(aNummer);
        assertNotNull("De blokkering kon niet worden gevonden.", blokkering);
        assertEquals("Het A-nummer komt niet overeen met het opgegeven A-nummer.", aNummer, blokkering.getaNummer());
        assertEquals("Het process id komt niet overeen met de verwachting", Long.valueOf(1), blokkering.getProcessId());
        assertEquals("De registratiegemeente komt niet overeen met de verwachting", "060001", blokkering.getRegistratieGemeente());
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
    @InsertBefore(value = "BlokkeringTestData.xml")
    public void testDeblokkering() {
        final String aNummer = "1234567890";
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
