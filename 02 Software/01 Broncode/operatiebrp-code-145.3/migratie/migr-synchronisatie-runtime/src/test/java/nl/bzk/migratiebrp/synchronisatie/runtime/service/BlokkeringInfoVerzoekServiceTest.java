/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.UUID;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.RedenBlokkering;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringInfoVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BlokkeringInfoVerzoekServiceTest {

    private static final String ANUMMER = "1865124650";
    private static final Long PROCESS_ID = 14L;
    private static final String GEMEENTE_NAAR = "190401";
    private static final String GEMEENTE_REGISTRATIE = "190501";

    @Mock
    private BrpDalService brpDalService;

    private BlokkeringInfoVerzoekService blokkeringInfoVerzoekService;

    @Before
    public void setup() throws OngeldigePersoonslijstException {
        final nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering blokkering = new Blokkering(ANUMMER, new Timestamp(System.currentTimeMillis()));
        blokkering.setProcessId(PROCESS_ID);
        blokkering.setGemeenteCodeNaar(GEMEENTE_NAAR);
        blokkering.setRegistratieGemeente(GEMEENTE_REGISTRATIE);
        blokkering.setRedenBlokkering(RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP);
        when(brpDalService.vraagOpBlokkering(ANUMMER)).thenReturn(blokkering);
        blokkeringInfoVerzoekService = new BlokkeringInfoVerzoekService(brpDalService);
    }

    @Test
    public void testBlokkeringInfoAntwoordOK() {
        final BlokkeringInfoVerzoekType blokkeringInfoVerzoekType = new BlokkeringInfoVerzoekType();

        blokkeringInfoVerzoekType.setANummer(ANUMMER);

        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek = new BlokkeringInfoVerzoekBericht(blokkeringInfoVerzoekType);
        blokkeringInfoVerzoek.setMessageId(UUID.randomUUID().toString());

        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBericht = blokkeringInfoVerzoekService.verwerkBericht(blokkeringInfoVerzoek);

        verify(brpDalService, times(1)).vraagOpBlokkering(ANUMMER);

        assertEquals(blokkeringInfoVerzoek.getMessageId(), blokkeringInfoAntwoordBericht.getCorrelationId());
        assertNull(blokkeringInfoAntwoordBericht.getStartCyclus());
        assertEquals(StatusType.OK, blokkeringInfoAntwoordBericht.getStatus());
        assertNotNull(blokkeringInfoAntwoordBericht.getMessageId());
    }

    @Test
    public void testBlokkeringInfoAntwoordFout() {
        final BlokkeringInfoVerzoekType blokkeringInfoVerzoekType = new BlokkeringInfoVerzoekType();

        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek = new BlokkeringInfoVerzoekBericht(blokkeringInfoVerzoekType);
        blokkeringInfoVerzoek.setMessageId(UUID.randomUUID().toString());

        try {
            when(brpDalService.vraagOpBlokkering(null)).thenCallRealMethod();
            blokkeringInfoVerzoekService.verwerkBericht(blokkeringInfoVerzoek);
            fail("Er zou een technische fout op moeten treden.");
        } catch (final Exception e) {
            assertNotNull("Er zou een technische fout op moeten treden.", e);
        }
    }

    @Test
    public void testBlokkeringInfoAntwoordLeegVerzoek() {

        try {
            blokkeringInfoVerzoekService.verwerkBericht(null);
            fail("Er zou een technische fout op moeten treden.");
        } catch (final Exception e) {
            assertNotNull("Er zou een technische fout op moeten treden.", e);
        }
        verify(brpDalService, times(0)).vraagOpBlokkering(null);
    }
}
