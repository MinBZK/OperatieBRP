/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import java.sql.Timestamp;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit.InsertBefore;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DynamischeStamtabelRepositoryTest extends AbstractDatabaseTest {

    @Inject
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    private final static Short CURRENT_SEQUENCE_NUMBER = Short.valueOf("9999");

    @InsertBefore("LandTestData.xml")
    @Test
    public void vraagBepaaldLandOpMetCode() {
        final LandOfGebied landOfGebied = dynamischeStamtabelRepository.getLandOfGebiedByCode(new Short("6030"));
        assertNotNull(landOfGebied);
        assertEquals("Nederland", landOfGebied.getNaam());
    }

    @InsertBefore("/sql/data/brpStamgegevens-kern.xml")
    @Test
    public void vraagGemeenteOpMetPartij() {
        final Gemeente gemeente = dynamischeStamtabelRepository.getGemeenteByPartij(dynamischeStamtabelRepository.getPartijByCode(3401));
        assertNotNull(gemeente);
        assertEquals("Almere", gemeente.getNaam());
    }

    @Test(expected = IllegalArgumentException.class)
    public void vraagBepaaldLandOpMetCodeZonderSucces() {
        dynamischeStamtabelRepository.getLandOfGebiedByCode(new Short("9999"));
    }

    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    @Transactional(value = "syncDalTransactionManager")
    @Test
    @InsertBefore("/sql/data/brpStamgegevens-kern.xml")
    public void insertEenPartij() {

        // clear caches (omdat anders hibernate misschien de 'nieuwe id' al ooit heeft gecached en
        // het dan een detached entity vind ipv een nieuwe.
        final Session s = (Session) em.getDelegate();
        final SessionFactory sf = s.getSessionFactory();
        sf.getCache().evictCollectionRegions();
        sf.getCache().evictDefaultQueryRegion();
        sf.getCache().evictEntityRegions();
        sf.getCache().evictNaturalIdRegions();
        sf.getCache().evictQueryRegions();

        final Partij partij = new Partij("Reinder", 123502);
        partij.setDatumIngang(20130101);
        Timestamp datumTijdRegistratie = new Timestamp(System.currentTimeMillis());
        final PartijHistorie partijHistorie = new PartijHistorie(partij, datumTijdRegistratie, 20130101, false, partij.getNaam());
        partijHistorie.setDatumTijdRegistratie(new Timestamp(1L));
        partij.addHisPartij(partijHistorie);
        dynamischeStamtabelRepository.savePartij(partij);
        assertEquals(CURRENT_SEQUENCE_NUMBER + 1, partij.getId().longValue());

        // Als deze test faalt met een constraint op His_Partij.ID (R4631), pas dan de sequence reset aan
        // naar (SELECT MAX(ID)+1 FROM KERN.HIS_PARTIJ) op de volgende locaties:
        // migr-sync-dal/src/test/java/nl/bzk/migratiebrp/synchronisatie/util/brpkern/DBUnitBrpUtil.java
        // methode: resetSequences()
        // migr-sync-dal/src/test/resources/sql/data/fixBrpSequences.sql
        // migr-brp/brp-aanvulling-op-dump.sql
    }
}
