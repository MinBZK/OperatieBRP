/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.sql.Timestamp;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.test.dal.DBUnit.ExpectedAfter;
import nl.bzk.algemeenbrp.test.dal.DBUnit.InsertBefore;
import nl.bzk.algemeenbrp.test.dal.DBUnitBrpUtil;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingPersoon;
import nl.bzk.brp.bijhouding.dal.AbstractRepositoryTest;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;
import nl.bzk.brp.bijhouding.dal.PersoonRepository;
import org.dbunit.DatabaseUnitException;
import org.junit.Before;
import org.junit.Test;

/**
 * Test de {@link PersoonRepository}.
 */
public class PersoonRepositoryImplTest extends AbstractRepositoryTest {

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    @Inject
    private DBUnitBrpUtil dbUnitBrpUtil;

    @Before
    public void setup() throws DatabaseUnitException, SQLException {
        dbUnitBrpUtil.resetDB(this.getClass(), LoggerFactory.getLogger(), false);
        doInsertBefore();
    }

    @InsertBefore("PersoonTestData.xml")
    @Test
    public void testFindById() {
        final Persoon persoon = persoonRepository.findById(1L);
        assertNotNull(persoon);
        assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoortPersoon());
        assertNull(persoonRepository.findById(0L));

        final Persoon persoonGeblokkeerd = persoonRepository.findById(3L);
        assertNotNull(persoonGeblokkeerd);
        assertTrue(persoonRepository.isPersoonGeblokkeerd(persoonGeblokkeerd));
    }

    @InsertBefore("PersoonTestData.xml")
    @Test
    public void testZoekGerelateerdePersonen() {
        assertEquals(0, persoonRepository.zoekGerelateerdePseudoPersonen("1234557890", "000000000").size());
        assertEquals(1, persoonRepository.zoekGerelateerdePseudoPersonen("2234567890", "000000000").size());
        assertEquals(1, persoonRepository.zoekGerelateerdePseudoPersonen("0000000000", "993456780").size());
        assertEquals(1, persoonRepository.zoekGerelateerdePseudoPersonen("9934567810", "993456780").size());
        assertEquals(2, persoonRepository.zoekGerelateerdePseudoPersonen("2234567890", "993456780").size());
        assertEquals(0, persoonRepository.zoekGerelateerdePseudoPersonen(null, "993456780").size());
        assertEquals(0, persoonRepository.zoekGerelateerdePseudoPersonen("2234567890", null).size());
    }

    @Test
    @ExpectedAfter("PersoonExpected.xml")
    public void testSave() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final Partij partij = dynamischeStamtabelRepository.findPartijByNaam("Onbekend");
        final Timestamp timestamp = new Timestamp(1056556);
        final PersoonBijhoudingHistorie persoonBijhoudingHistorie =
                new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(partij, SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, timestamp);
        final BRPActie brpActie = new BRPActie(SoortActie.REGISTRATIE_AANVANG_HUWELIJK, administratieveHandeling, partij, timestamp);
        persoon.setSoortPersoon(SoortPersoon.INGESCHREVENE);
        persoonBijhoudingHistorie.setDatumTijdRegistratie(timestamp);
        persoonBijhoudingHistorie.setDatumAanvangGeldigheid(20110101);
        persoonBijhoudingHistorie.setActieInhoud(brpActie);
        persoon.addPersoonBijhoudingHistorie(persoonBijhoudingHistorie);
        persoonRepository.slaPersoonOp(persoon);
        final Persoon persoon2 = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon2.setSoortPersoon(SoortPersoon.INGESCHREVENE);
        persoon2.setAdministratienummer("1234567888");
        persoonRepository.slaPersoonOp(persoon2);
        assertEquals(1, persoon.getId().longValue());
        assertEquals(2, persoon2.getId().longValue());
        persoonRepository.slaPersoonOp(persoon2);
        assertEquals(1, persoon.getId().longValue());
        assertEquals(2, persoon2.getId().longValue());
    }

    @Test
    @ExpectedAfter("PersoonExpected.xml")
    public void testSaveBijhoudingPersoon() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final Partij partij = dynamischeStamtabelRepository.findPartijByNaam("Onbekend");
        final Timestamp timestamp = new Timestamp(1056556);
        final PersoonBijhoudingHistorie persoonBijhoudingHistorie =
                new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(partij, SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, timestamp);
        final BRPActie brpActie = new BRPActie(SoortActie.REGISTRATIE_AANVANG_HUWELIJK, administratieveHandeling, partij, timestamp);
        persoon.setSoortPersoon(SoortPersoon.INGESCHREVENE);
        persoonBijhoudingHistorie.setDatumTijdRegistratie(timestamp);
        persoonBijhoudingHistorie.setDatumAanvangGeldigheid(20110101);
        persoonBijhoudingHistorie.setActieInhoud(brpActie);
        persoon.addPersoonBijhoudingHistorie(persoonBijhoudingHistorie);
        persoonRepository.slaPersoonOp(BijhoudingPersoon.decorate(persoon));
        final Persoon persoon2 = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon2.setSoortPersoon(SoortPersoon.INGESCHREVENE);
        persoon2.setAdministratienummer("1234567888");
        persoonRepository.slaPersoonOp(BijhoudingPersoon.decorate(persoon2));
        assertEquals(1, persoon.getId().longValue());
        assertEquals(2, persoon2.getId().longValue());
        persoonRepository.slaPersoonOp(BijhoudingPersoon.decorate(persoon2));
        assertEquals(1, persoon.getId().longValue());
        assertEquals(2, persoon2.getId().longValue());
    }

    @Test
    @InsertBefore("PersoonTestData.xml")
    public void testKomstBsnReedsVoor() {
        assertTrue("BSN moet reeds voorkomen", persoonRepository.komtBsnReedsVoor("578603305"));
        assertFalse("BSN mag niet voorkomen", persoonRepository.komtBsnReedsVoor("224328177"));
    }

    @Test
    @InsertBefore("PersoonTestData.xml")
    public void testKomstAnrReedsVoor() {
        assertTrue("A-nummer moet reeds voorkomen", persoonRepository.komtAdministratienummerReedsVoor("3974851873"));
        assertFalse("A-nummer mag niet voorkomen", persoonRepository.komtAdministratienummerReedsVoor("1496069569"));
    }

    @Test
    public void testApplicationContextProvider() {
        assertNotNull(ApplicationContextProvider.getPersoonRepository());
        assertNotNull(ApplicationContextProvider.getPersoonCacheRepository());
    }
}
