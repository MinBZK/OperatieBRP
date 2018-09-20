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

import java.util.UUID;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.blokkering.entity.Blokkering;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.blokkering.entity.RedenBlokkering;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BlokkeringVerzoekServiceTest {

    private static final Long ANUMMER = 186512465L;
    private static final Long ANUMMER_BESTAANDE_BLOKKERING = 14534465L;
    private static final String PROCESS_ID = "2451";
    private static final String GEMEENTE_NAAR = "1904";
    private static final String GEMEENTE_REGISTRATIE = "1905";
    private static final Blokkering DUMMY_BLOKKERING = Blokkering.newInstance(
        ANUMMER,
        Long.valueOf(PROCESS_ID),
        GEMEENTE_NAAR,
        GEMEENTE_REGISTRATIE,
        RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP);

    @Mock
    private BrpDalService brpDalService;

    @InjectMocks
    private final BlokkeringVerzoekService blokkeringVerzoekService = new BlokkeringVerzoekService();

    @Before
    public void setup() throws OngeldigePersoonslijstException {
        when(brpDalService.vraagOpBlokkering(ANUMMER)).thenReturn(null);
        when(brpDalService.vraagOpBlokkering(ANUMMER_BESTAANDE_BLOKKERING)).thenReturn(DUMMY_BLOKKERING);
        when(brpDalService.persisteerBlokkering(Matchers.<Blokkering>any())).thenReturn(DUMMY_BLOKKERING);
    }

    @Test
    public void testBlokkeringAntwoordOK() {
        final BlokkeringVerzoekType blokkeringVerzoekType = new BlokkeringVerzoekType();

        blokkeringVerzoekType.setANummer(ANUMMER.toString());
        blokkeringVerzoekType.setGemeenteNaar(GEMEENTE_NAAR);
        blokkeringVerzoekType.setGemeenteRegistratie(GEMEENTE_REGISTRATIE);
        blokkeringVerzoekType.setProcessId(PROCESS_ID);
        blokkeringVerzoekType.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP);

        final BlokkeringVerzoekBericht blokkeringVerzoek = new BlokkeringVerzoekBericht(blokkeringVerzoekType);
        blokkeringVerzoek.setMessageId(UUID.randomUUID().toString());

        final BlokkeringAntwoordBericht blokkeringAntwoordBericht = blokkeringVerzoekService.verwerkBericht(blokkeringVerzoek);

        verify(brpDalService, times(1)).vraagOpBlokkering(ANUMMER);
        final ArgumentCaptor<Blokkering> argumentCaptor = ArgumentCaptor.forClass(Blokkering.class);
        verify(brpDalService, times(1)).persisteerBlokkering(argumentCaptor.capture());

        assertNotNull(blokkeringAntwoordBericht.getMessageId());
        assertEquals(blokkeringVerzoek.getMessageId(), blokkeringAntwoordBericht.getCorrelationId());
        assertNull(blokkeringAntwoordBericht.getGemeenteNaar());
        assertNull(blokkeringAntwoordBericht.getPersoonsaanduiding());
        assertNull(blokkeringAntwoordBericht.getProcessId());
        assertNull(blokkeringAntwoordBericht.getStartCyclus());
        assertEquals(StatusType.OK, blokkeringAntwoordBericht.getStatus());
        assertNotNull(blokkeringAntwoordBericht.getMessageId());
    }

    @Test
    public void testBlokkeringAntwoordBestaatAl() {
        final BlokkeringVerzoekType blokkeringVerzoekType = new BlokkeringVerzoekType();

        blokkeringVerzoekType.setANummer(ANUMMER_BESTAANDE_BLOKKERING.toString());
        blokkeringVerzoekType.setGemeenteNaar(GEMEENTE_NAAR);
        blokkeringVerzoekType.setGemeenteRegistratie(GEMEENTE_REGISTRATIE);
        blokkeringVerzoekType.setProcessId(PROCESS_ID);
        blokkeringVerzoekType.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP);

        final BlokkeringVerzoekBericht blokkeringVerzoek = new BlokkeringVerzoekBericht(blokkeringVerzoekType);
        blokkeringVerzoek.setMessageId(UUID.randomUUID().toString());

        final BlokkeringAntwoordBericht blokkeringAntwoordBericht = blokkeringVerzoekService.verwerkBericht(blokkeringVerzoek);

        verify(brpDalService, times(1)).vraagOpBlokkering(ANUMMER_BESTAANDE_BLOKKERING);
        verify(brpDalService, times(0)).verwijderBlokkering(DUMMY_BLOKKERING);

        assertNotNull(blokkeringAntwoordBericht.getMessageId());
        assertEquals(blokkeringVerzoek.getMessageId(), blokkeringAntwoordBericht.getCorrelationId());
        assertNull(blokkeringAntwoordBericht.getStartCyclus());
        assertEquals(StatusType.GEBLOKKEERD, blokkeringAntwoordBericht.getStatus());
        assertNotNull(blokkeringAntwoordBericht.getMessageId());
    }

    @Test
    public void testBlokkeringAntwoordFout() {
        final BlokkeringVerzoekType blokkeringVerzoekType = new BlokkeringVerzoekType();

        final BlokkeringVerzoekBericht blokkeringInfoVerzoek = new BlokkeringVerzoekBericht(blokkeringVerzoekType);

        try {
            blokkeringVerzoekService.verwerkBericht(blokkeringInfoVerzoek);
            fail("Er zou een fout op moeten treden.");
        } catch (final Exception e) {
            assertNotNull("Er zou een fout op moeten treden.", e);
        }
        verify(brpDalService, times(0)).vraagOpBlokkering(null);
        verify(brpDalService, times(0)).verwijderBlokkering(null);
    }

    @Test
    public void testBlokkeringAntwoordLeegVerzoek() {

        try {
            blokkeringVerzoekService.verwerkBericht(null);
            fail("Er zou een fout op moeten treden.");
        } catch (final Exception e) {
            assertNotNull("Er zou een fout op moeten treden.", e);
        }
        verify(brpDalService, times(0)).vraagOpBlokkering(null);
        verify(brpDalService, times(0)).verwijderBlokkering(null);

    }
}
