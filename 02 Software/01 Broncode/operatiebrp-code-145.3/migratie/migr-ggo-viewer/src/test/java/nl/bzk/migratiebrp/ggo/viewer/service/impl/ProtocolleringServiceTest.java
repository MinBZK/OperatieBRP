/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import nl.bzk.migratiebrp.ggo.viewer.domein.protocollering.entity.Protocollering;
import nl.bzk.migratiebrp.ggo.viewer.repository.ProtocolleringRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProtocolleringServiceTest {

    @Mock
    private ProtocolleringRepository protocolleringRepository;

    @InjectMocks
    private ProtocolleringServiceImpl protocolleringService;

    @Test
    public void testZoekProtocollering() {
        final Protocollering protocollering = new Protocollering("test", new Timestamp(System.currentTimeMillis()), "123456789", true);
        Mockito.when(protocolleringRepository.save(Matchers.any(Protocollering.class))).thenReturn(protocollering);
        final Protocollering gepersisteerdeProtocollering = protocolleringService.persisteerProtocollering(protocollering);

        assertNotNull("Protocollering mag niet null zijn", gepersisteerdeProtocollering);
        assertNotNull("Id moet gevuld zijn.", gepersisteerdeProtocollering.getAdministratienummer());
    }
}
