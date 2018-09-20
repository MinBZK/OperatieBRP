/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stapel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StapelVoorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit;
import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit.ExpectedAfter;
import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit.InsertBefore;
import org.junit.Assert;
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
        final Persoon persoon = persoonRepository.findByAdministratienummer(123455789L, SoortPersoon.INGESCHREVENE, false).get(0);
        assertNotNull(persoon);
        assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoortPersoon());
    }

    @InsertBefore("PersoonTestData.xml")
    @Test
    public void vraagBepaaldPersoonOpMetTechischeSleutel() {
        final Persoon persoon = persoonRepository.findByTechnischeSleutel(1L, SoortPersoon.INGESCHREVENE).get(0);
        assertNotNull(persoon);
        assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoortPersoon());
    }

    @InsertBefore("PersoonTestData.xml")
    @Test
    public void vraagFoutiefOpgeschortBepaaldPersoonOp() {
        assertTrue(persoonRepository.findByAdministratienummer(356356334L, SoortPersoon.INGESCHREVENE, true).isEmpty());
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

    @InsertBefore({"PersoonHistTestPersoonData.xml", "PersoonHistTestHistData.xml" })
    @Test
    public void vraagHistorischdPersoonOp() {
        final List<Persoon> actueel = persoonRepository.findByAdministratienummerHistorisch(4445556667L, SoortPersoon.INGESCHREVENE);
        Assert.assertNotNull(actueel);
        Assert.assertTrue(actueel.isEmpty()); // Actueel

        final List<Persoon> persoon = persoonRepository.findByAdministratienummerHistorisch(3334445556L, SoortPersoon.INGESCHREVENE);
        Assert.assertNotNull(persoon);
        Assert.assertFalse(persoon.isEmpty());

        final List<Persoon> vervallen = persoonRepository.findByAdministratienummerHistorisch(2223334445L, SoortPersoon.INGESCHREVENE);
        Assert.assertNotNull(vervallen);
        Assert.assertTrue(vervallen.isEmpty());
    }

    @Transactional(value = "syncDalTransactionManager")
    @Test
    @InsertBefore("PersoonMetIstTestData.xml")
    @ExpectedAfter("PersoonMetIstExpected.xml")
    public void insertEenPersoonMetIstTabel() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setSoortPersoon(SoortPersoon.INGESCHREVENE);

        final String categorie = "01";
        final int volgnummer = 0;
        final Stapel stapel = new Stapel(persoon, categorie, volgnummer);

        final Partij partij = dynamischeStamtabelRepository.findPartijByCode(8);

        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(partij, SoortAdministratieveHandeling.GBA_INITIELE_VULLING);

        final StapelVoorkomen stapelVoorkomen1 = new StapelVoorkomen(stapel, 0, administratieveHandeling);
        final StapelVoorkomen stapelVoorkomen2 = new StapelVoorkomen(stapel, 1, administratieveHandeling);

        stapel.addStapelVoorkomen(stapelVoorkomen1);
        stapel.addStapelVoorkomen(stapelVoorkomen2);

        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        stapel.addRelatie(relatie);

        persoon.addStapel(stapel);

        persoonRepository.save(persoon);
        assertEquals(1, persoon.getId().longValue());
    }

    @InsertBefore("ZoekPersonenTestData.xml")
    @Test
    public void zoekPersoonOpActueleGegevens() {
        List<Persoon> personen;

        // Gevonden ID=1
        personen = persoonRepository.zoekPersonenOpActueleGegevens(1273053921L, null, null, null);
        Assert.assertEquals(1, personen.size());
        checkIds(personen, 1);

        // Gevonden ID= 2
        personen = persoonRepository.zoekPersonenOpActueleGegevens(null, 610049641, null, null);
        Assert.assertEquals(1, personen.size());
        checkIds(personen, 2);

        // Gevonden ID=1,2,3
        personen = persoonRepository.zoekPersonenOpActueleGegevens(null, null, "A", null);
        Assert.assertEquals(3, personen.size());
        checkIds(personen, 1, 2, 3);

        // Gevonden ID=2,3,5
        personen = persoonRepository.zoekPersonenOpActueleGegevens(null, null, null, "1234RE");
        Assert.assertEquals(3, personen.size());
        checkIds(personen, 2, 3, 5);

        // Gevonden ID=2
        personen = persoonRepository.zoekPersonenOpActueleGegevens(null, 610049641, "A", "1234RE");
        Assert.assertEquals(1, personen.size());
        checkIds(personen, 2);

        // Gevonden ID=
        personen = persoonRepository.zoekPersonenOpActueleGegevens(1273053921L, 610049641, "A", "1234RE");
        Assert.assertEquals(0, personen.size());
    }

    @InsertBefore("ZoekPersonenHisTestData.xml")
    @Test
    public void zoekPersoonOpHistorischeGegevens() {
        List<Persoon> personen;

        // Gevonden ID=7,8
        personen = persoonRepository.zoekPersonenOpHistorischeGegevens(2807629217L, null, null);
        Assert.assertEquals(2, personen.size());
        checkIds(personen, 7, 8);

        // Gevonden ID= 8
        personen = persoonRepository.zoekPersonenOpHistorischeGegevens(null, 906273481, null);
        Assert.assertEquals(1, personen.size());
        checkIds(personen, 8);

        // Gevonden ID=3, 8
        personen = persoonRepository.zoekPersonenOpHistorischeGegevens(null, null, "F");
        Assert.assertEquals(2, personen.size());
        checkIds(personen, 3, 8);

        // Gevonden ID= 8
        personen = persoonRepository.zoekPersonenOpHistorischeGegevens(2807629217L, 906273481, "F");
        Assert.assertEquals(1, personen.size());
        checkIds(personen, 8);

        // Gevonden ID=
        personen = persoonRepository.zoekPersonenOpHistorischeGegevens(2807629217L, 333333305, "F");
        Assert.assertEquals(0, personen.size());
    }

    @Transactional(value = "syncDalTransactionManager")
    @InsertBefore("PersoonCacheTestData.xml")
    @Test
    @ExpectedAfter("PersoonCacheExpected.xml")
    @DBUnit.NotExpectedAfter("PersoonCacheNotExpected.xml")
    public void testRemoveCache() {
        final List<Persoon> personen = persoonRepository.zoekPersonenOpActueleGegevens(123455789L, null, null, null);
        Assert.assertEquals(1, personen.size());

        persoonRepository.removeCache(personen.get(0));
    }

    private void checkIds(final List<Persoon> personen, final int... ids) {
        for (final Persoon persoon : personen) {
            final int persoonId = persoon.getId();
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
                final int persoonId = persoon.getId();

                if (id == persoonId) {
                    gevonden = true;
                    break;
                }
            }
            Assert.assertTrue("Expected ID " + id + " kwam niet voor in lijst met personen", gevonden);
        }
    }
}
