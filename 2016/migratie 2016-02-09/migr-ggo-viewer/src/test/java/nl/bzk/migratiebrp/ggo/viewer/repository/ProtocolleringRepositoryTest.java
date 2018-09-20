/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.ggo.viewer.domein.protocollering.entity.Protocollering;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test ProtocolleringRepository
 */
@Transactional(transactionManager = "viewerTransactionManager")
@Rollback(false)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-viewer-beans.xml" })
public class ProtocolleringRepositoryTest {

    @Inject
    private ProtocolleringRepository protocolleringRepository;

    /**
     * Insert en vindt protocollering op basis van het anummer.
     */
    @Test
    @Transactional(value = "viewerTransactionManager", propagation = Propagation.REQUIRED)
    public void testInsertAndFindBerichtLogVoorANummer() {
        final Long bestaandANummer = 123456789L;
        insertProtocollering(new Protocollering("test", new Timestamp(System.currentTimeMillis()), bestaandANummer, true));
        final List<Protocollering> protocolleringen = protocolleringRepository.findProtocolleringVoorANummer(bestaandANummer);

        assertNotNull("Er zouden protocolleringen gevonden moeten worden!", protocolleringen);
        assertTrue("Er zou 1 protocollering gevonden moeten worden. Het zijn er: " + protocolleringen.size(), protocolleringen.size() == 1);
        assertEquals(protocolleringen.get(0).getGebruikersnaam(), "test");
        assertEquals(protocolleringen.get(0).getAdministratienummer(), bestaandANummer);
    }

    /**
     * Zoek, maar vindt geen protocollering op basis van het anummer.
     */
    @Test
    public void testInsertAndFindBerichtLogVoorANummerZonderProtocollering() {
        final Long nietBestaandANummer = 12345L;
        final List<Protocollering> protocolleringen = protocolleringRepository.findProtocolleringVoorANummer(nietBestaandANummer);

        assertNotNull(protocolleringen);
        assertTrue("Er zouden geen protocolleringen gevonden moeten worden.", protocolleringen.size() == 0);
    }

    private void insertProtocollering(final Protocollering protocollering) {
        protocolleringRepository.save(protocollering);
    }
}
