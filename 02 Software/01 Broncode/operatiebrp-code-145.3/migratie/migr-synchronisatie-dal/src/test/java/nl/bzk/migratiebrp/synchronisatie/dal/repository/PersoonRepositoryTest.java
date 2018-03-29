/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.test.dal.DBUnit.ExpectedAfter;
import nl.bzk.algemeenbrp.test.dal.DBUnit.InsertBefore;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

public class PersoonRepositoryTest extends AbstractDatabaseTest {
    @Inject
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;
    @Inject
    private PersoonRepository persoonRepository;

    @InsertBefore("PersoonTestData.xml")
    @Test
    public void vraagBepaaldPersoonOp() {
        final Persoon persoon = persoonRepository.findByAdministratienummer("123455789", SoortPersoon.INGESCHREVENE, false).get(0);
        assertNotNull(persoon);
        assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoortPersoon());
    }

    @InsertBefore("PersoonTestData.xml")
    @Test
    public void vraagBepaaldPersoonOpMetTechischeSleutel() {
        final Persoon persoon = persoonRepository.findByTechnischeSleutel(1L, SoortPersoon.INGESCHREVENE, false).get(0);
        assertNotNull(persoon);
        assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoortPersoon());
    }

    @InsertBefore("PersoonTestData.xml")
    @Test
    public void vraagBepaaldOpgeschortPersoonOpMetTechischeSleutel() {
        final Persoon persoon = persoonRepository.findByTechnischeSleutel(2L, SoortPersoon.INGESCHREVENE, false).get(0);
        assertNotNull(persoon);
        assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoortPersoon());
    }

    @InsertBefore("PersoonTestData.xml")
    @Test
    public void vraagBepaaldOpgeschortPersoonOpMetTechischeSleutelFoutiefOpgeschortUitsluiten() {
        final int aantalPersonen = persoonRepository.findByTechnischeSleutel(2L, SoortPersoon.INGESCHREVENE, true).size();
        assertEquals(0, aantalPersonen);
    }

    @InsertBefore("PersoonTestData.xml")
    @Test
    public void vraagFoutiefOpgeschortBepaaldPersoonOp() {
        assertTrue(persoonRepository.findByAdministratienummer("356356334", SoortPersoon.INGESCHREVENE, true).isEmpty());
    }

    @Transactional(value = "syncDalTransactionManager")
    @Test
    @ExpectedAfter("PersoonExpected.xml")
    public void insertEenPersoon() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setSoortPersoon(SoortPersoon.INGESCHREVENE);
        persoonRepository.save(persoon);
        assertEquals(1, persoon.getId().longValue());
    }

    @InsertBefore({"PersoonHistTestPersoonData.xml", "PersoonHistTestHistData.xml"})
    @Test
    public void vraagHistorischdPersoonOp() {
        final List<Persoon> actueel = persoonRepository.findByAdministratienummerHistorisch("4445556667", SoortPersoon.INGESCHREVENE);
        Assert.assertNotNull(actueel);
        Assert.assertTrue(actueel.isEmpty()); // Actueel

        final List<Persoon> persoon = persoonRepository.findByAdministratienummerHistorisch("3334445556", SoortPersoon.INGESCHREVENE);
        Assert.assertNotNull(persoon);
        Assert.assertFalse(persoon.isEmpty());

        final List<Persoon> vervallen = persoonRepository.findByAdministratienummerHistorisch("2223334445", SoortPersoon.INGESCHREVENE);
        Assert.assertNotNull(vervallen);
        Assert.assertTrue(vervallen.isEmpty());
    }

    @InsertBefore("ZoekPersonenTestData.xml")
    @Test
    public void zoekPersoonOpActueleGegevens() {
        List<Persoon> personen;

        // Gevonden ID=1
        personen = persoonRepository.zoekPersonenOpActueleGegevens("1273053921", null, null, null);
        assertEquals(1, personen.size());
        checkIds(personen, 1);

        // Gevonden ID= 2
        personen = persoonRepository.zoekPersonenOpActueleGegevens(null, "610049641", null, null);
        assertEquals(1, personen.size());
        checkIds(personen, 2);

        // Gevonden ID=1,2,3
        personen = persoonRepository.zoekPersonenOpActueleGegevens(null, null, "A", null);
        assertEquals(3, personen.size());
        checkIds(personen, 1, 2, 3);

        // Gevonden ID=2,3,5
        personen = persoonRepository.zoekPersonenOpActueleGegevens(null, null, null, "1234RE");
        assertEquals(3, personen.size());
        checkIds(personen, 2, 3, 5);

        // Gevonden ID=2
        personen = persoonRepository.zoekPersonenOpActueleGegevens(null, "610049641", "A", "1234RE");
        assertEquals(1, personen.size());
        checkIds(personen, 2);

        // Gevonden ID=
        personen = persoonRepository.zoekPersonenOpActueleGegevens("1273053921", "610049641", "A", "1234RE");
        assertEquals(0, personen.size());
    }

    @InsertBefore("ZoekPersonenHisTestData.xml")
    @Test
    public void zoekPersoonOpHistorischeGegevens() {
        List<Persoon> personen;

        // Gevonden ID=7,8
        personen = persoonRepository.zoekPersonenOpHistorischeGegevens("2807629217", null, null);
        assertEquals(2, personen.size());
        checkIds(personen, 7, 8);

        // Gevonden ID= 8
        personen = persoonRepository.zoekPersonenOpHistorischeGegevens(null, "906273481", null);
        assertEquals(1, personen.size());
        checkIds(personen, 8);

        // Gevonden ID=3, 8
        personen = persoonRepository.zoekPersonenOpHistorischeGegevens(null, null, "F");
        assertEquals(2, personen.size());
        checkIds(personen, 3, 8);

        // Gevonden ID= 8
        personen = persoonRepository.zoekPersonenOpHistorischeGegevens("2807629217", "906273481", "F");
        assertEquals(1, personen.size());
        checkIds(personen, 8);

        // Gevonden ID=
        personen = persoonRepository.zoekPersonenOpHistorischeGegevens("2807629217", "333333305", "F");
        assertEquals(0, personen.size());
    }

    @Transactional(value = "syncDalTransactionManager")
    @InsertBefore("PersoonCacheTestData.xml")
    @Test
    @ExpectedAfter("PersoonCacheExpected.xml")
    public void testSlaPersoonCacheOp() {
        final List<Persoon> personen = persoonRepository.zoekPersonenOpActueleGegevens("1234557890", null, null, null);
        assertEquals(1, personen.size());

        persoonRepository.save(personen.get(0));
    }

    private void checkIds(final List<Persoon> personen, final int... ids) {
        for (final Persoon persoon : personen) {
            final Long persoonId = persoon.getId();
            boolean gevonden = false;
            for (final int id : ids) {
                if (id == persoonId) {
                    gevonden = true;
                    break;
                }
            }
            Assert.assertTrue("Persoon met ID " + persoonId + " kwam niet voor in expected ID lijst", gevonden);
        }

        for (final int id : ids) {
            boolean gevonden = false;
            for (final Persoon persoon : personen) {
                final Long persoonId = persoon.getId();

                if (id == persoonId) {
                    gevonden = true;
                    break;
                }
            }
            Assert.assertTrue("Expected ID " + id + " kwam niet voor in lijst met personen", gevonden);
        }
    }
}
