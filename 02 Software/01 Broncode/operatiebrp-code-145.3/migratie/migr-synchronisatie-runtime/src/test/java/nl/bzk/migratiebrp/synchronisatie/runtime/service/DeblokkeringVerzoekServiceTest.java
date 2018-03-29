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
import nl.bzk.migratiebrp.bericht.model.sync.generated.DeblokkeringVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeblokkeringVerzoekServiceTest {

    private static final String ANUMMER = "1865124650";
    private static final Long PROCESS_ID = 2451L;
    private static final String GEMEENTE_NAAR = "190401";
    private static final String GEMEENTE_REGISTRATIE = "190501";
    private static final Blokkering DUMMY_BLOKKERING;

    static {
        DUMMY_BLOKKERING = new Blokkering(ANUMMER, new Timestamp(System.currentTimeMillis()));
        DUMMY_BLOKKERING.setProcessId(PROCESS_ID);
        DUMMY_BLOKKERING.setGemeenteCodeNaar(GEMEENTE_NAAR);
        DUMMY_BLOKKERING.setRegistratieGemeente(GEMEENTE_REGISTRATIE);
        DUMMY_BLOKKERING.setRedenBlokkering(RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP);
    }

    @Mock
    private BrpDalService brpDalService;

    private DeblokkeringVerzoekService deblokkeringVerzoekService;

    @Before
    public void setup() throws OngeldigePersoonslijstException {
        when(brpDalService.vraagOpBlokkering(ANUMMER)).thenReturn(DUMMY_BLOKKERING);
        deblokkeringVerzoekService = new DeblokkeringVerzoekService(brpDalService);
    }

    @Test
    public void testDeblokkeringAntwoordOK() {
        final DeblokkeringVerzoekType deblokkeringVerzoekType = new DeblokkeringVerzoekType();

        deblokkeringVerzoekType.setANummer(ANUMMER);
        deblokkeringVerzoekType.setGemeenteRegistratie(GEMEENTE_REGISTRATIE);
        deblokkeringVerzoekType.setProcessId(PROCESS_ID.toString());

        final DeblokkeringVerzoekBericht deblokkeringVerzoek = new DeblokkeringVerzoekBericht(deblokkeringVerzoekType);
        deblokkeringVerzoek.setMessageId(UUID.randomUUID().toString());

        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = deblokkeringVerzoekService.verwerkBericht(deblokkeringVerzoek);

        verify(brpDalService, times(1)).vraagOpBlokkering(ANUMMER);
        verify(brpDalService, times(1)).verwijderBlokkering(DUMMY_BLOKKERING);

        assertEquals(deblokkeringVerzoek.getMessageId(), deblokkeringAntwoordBericht.getCorrelationId());
        assertNull(deblokkeringAntwoordBericht.getStartCyclus());
        assertEquals(StatusType.OK, deblokkeringAntwoordBericht.getStatus());
        assertNotNull(deblokkeringAntwoordBericht.getMessageId());
    }

    @Test
    public void testDeblokkeringAntwoordFout() {
        final DeblokkeringVerzoekType deblokkeringVerzoekType = new DeblokkeringVerzoekType();

        final DeblokkeringVerzoekBericht deblokkeringInfoVerzoek = new DeblokkeringVerzoekBericht(deblokkeringVerzoekType);
        deblokkeringInfoVerzoek.setMessageId(UUID.randomUUID().toString());

        try {
            when(brpDalService.vraagOpBlokkering(null)).thenCallRealMethod();
            deblokkeringVerzoekService.verwerkBericht(deblokkeringInfoVerzoek);
            fail("Er zou een fout op moeten treden.");
        } catch (final Exception e) {
            assertNotNull("Er zou een fout op moeten treden.", e);
        }
    }

    @Test
    public void testDeblokkeringAntwoordLeegVerzoek() {

        try {
            deblokkeringVerzoekService.verwerkBericht(null);
            fail("Er zou een fout op moeten treden.");
        } catch (final Exception e) {
            assertNotNull("Er zou een fout op moeten treden.", e);
        }
        verify(brpDalService, times(0)).vraagOpBlokkering(null);
        verify(brpDalService, times(0)).verwijderBlokkering(null);

    }
}
