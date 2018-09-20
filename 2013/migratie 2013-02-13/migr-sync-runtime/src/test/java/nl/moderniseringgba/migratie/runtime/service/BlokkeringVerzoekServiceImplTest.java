/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringVerzoekBericht;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.OngeldigePersoonslijstException;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.synchronisatie.domein.blokkering.entity.Blokkering;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BlokkeringVerzoekServiceImplTest {

    private static final String ANUMMER = "186512465";
    private static final String ANUMMER_BESTAANDE_BLOKKERING = "14534465";
    private static final String PROCESS_ID = "2451";
    private static final String GEMEENTE_NAAR = "1904";
    private static final String GEMEENTE_REGISTRATIE = "1905";
    private static final PersoonsaanduidingType PERSOONSAANDUIDING =
            PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP;
    private static final Blokkering DUMMY_BLOKKERING = Blokkering.newInstance(ANUMMER, Long.valueOf(PROCESS_ID),
            GEMEENTE_NAAR, GEMEENTE_REGISTRATIE, PERSOONSAANDUIDING.toString());

    @Mock
    private BrpDalService brpDalService;

    @InjectMocks
    private final BlokkeringVerzoekService blokkeringVerzoekService = new BlokkeringVerzoekServiceImpl();

    @Before
    public void setup() throws OngeldigePersoonslijstException {
        when(brpDalService.vraagOpBlokkering(ANUMMER)).thenReturn(null);
        when(brpDalService.vraagOpBlokkering(ANUMMER_BESTAANDE_BLOKKERING)).thenReturn(DUMMY_BLOKKERING);
        when(brpDalService.persisteerBlokkering(Matchers.<Blokkering>any())).thenReturn(DUMMY_BLOKKERING);
    }

    @Test
    public void testBlokkeringAntwoordOK() throws InputValidationException {
        final BlokkeringVerzoekType blokkeringVerzoekType = new BlokkeringVerzoekType();

        blokkeringVerzoekType.setANummer(ANUMMER);
        blokkeringVerzoekType.setGemeenteNaar(GEMEENTE_NAAR);
        blokkeringVerzoekType.setGemeenteRegistratie(GEMEENTE_REGISTRATIE);
        blokkeringVerzoekType.setProcessId(PROCESS_ID);
        blokkeringVerzoekType.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP);

        final BlokkeringVerzoekBericht blokkeringVerzoek = new BlokkeringVerzoekBericht(blokkeringVerzoekType);

        final BlokkeringAntwoordBericht blokkeringAntwoordBericht =
                blokkeringVerzoekService.verwerkBlokkeringVerzoek(blokkeringVerzoek);

        verify(brpDalService, times(1)).vraagOpBlokkering(ANUMMER);
        final ArgumentCaptor<Blokkering> argumentCaptor = ArgumentCaptor.forClass(Blokkering.class);
        verify(brpDalService, times(1)).persisteerBlokkering(argumentCaptor.capture());

        assertEquals(blokkeringVerzoek.getMessageId(), blokkeringAntwoordBericht.getCorrelationId());
        assertEquals(GEMEENTE_NAAR, blokkeringAntwoordBericht.getGemeenteNaar());
        assertEquals(PERSOONSAANDUIDING.toString(), blokkeringAntwoordBericht.getPersoonsaanduiding().toString());
        assertEquals(PROCESS_ID, blokkeringAntwoordBericht.getProcessId());
        assertNull(blokkeringAntwoordBericht.getToelichting());
        assertNull(blokkeringAntwoordBericht.getStartCyclus());
        assertEquals(StatusType.OK, blokkeringAntwoordBericht.getStatus());
        assertNotNull(blokkeringAntwoordBericht.getMessageId());
    }

    @Test
    public void testBlokkeringAntwoordBestaatAl() throws InputValidationException {
        final BlokkeringVerzoekType blokkeringVerzoekType = new BlokkeringVerzoekType();

        blokkeringVerzoekType.setANummer(ANUMMER_BESTAANDE_BLOKKERING);
        blokkeringVerzoekType.setGemeenteNaar(GEMEENTE_NAAR);
        blokkeringVerzoekType.setGemeenteRegistratie(GEMEENTE_REGISTRATIE);
        blokkeringVerzoekType.setProcessId(PROCESS_ID);
        blokkeringVerzoekType.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP);

        final BlokkeringVerzoekBericht blokkeringVerzoek = new BlokkeringVerzoekBericht(blokkeringVerzoekType);

        final BlokkeringAntwoordBericht blokkeringAntwoordBericht =
                blokkeringVerzoekService.verwerkBlokkeringVerzoek(blokkeringVerzoek);

        verify(brpDalService, times(1)).vraagOpBlokkering(ANUMMER_BESTAANDE_BLOKKERING);
        verify(brpDalService, times(0)).verwijderBlokkering(DUMMY_BLOKKERING);

        assertEquals(blokkeringVerzoek.getMessageId(), blokkeringAntwoordBericht.getCorrelationId());
        assertEquals("Er is voor het opgegeven aNummer al een blokkering gevonden.",
                blokkeringAntwoordBericht.getToelichting());
        assertNull(blokkeringAntwoordBericht.getStartCyclus());
        assertEquals(StatusType.FOUT, blokkeringAntwoordBericht.getStatus());
        assertNotNull(blokkeringAntwoordBericht.getMessageId());
    }

    @Test
    public void testBlokkeringAntwoordFout() throws InputValidationException {
        final BlokkeringVerzoekType blokkeringVerzoekType = new BlokkeringVerzoekType();

        final BlokkeringVerzoekBericht blokkeringInfoVerzoek = new BlokkeringVerzoekBericht(blokkeringVerzoekType);

        final BlokkeringAntwoordBericht blokkeringAntwoordBericht =
                blokkeringVerzoekService.verwerkBlokkeringVerzoek(blokkeringInfoVerzoek);

        verify(brpDalService, times(0)).vraagOpBlokkering(null);
        verify(brpDalService, times(0)).verwijderBlokkering(null);

        assertEquals(blokkeringInfoVerzoek.getMessageId(), blokkeringAntwoordBericht.getCorrelationId());
        assertEquals("Het BlokkeringInfoVerzoekBericht bevat geen aNummer.",
                blokkeringAntwoordBericht.getToelichting());
        assertNull(blokkeringAntwoordBericht.getStartCyclus());
        assertEquals(StatusType.FOUT, blokkeringAntwoordBericht.getStatus());
        assertNotNull(blokkeringAntwoordBericht.getMessageId());
    }

    @Test
    public void testBlokkeringAntwoordLeegVerzoek() throws InputValidationException {

        final BlokkeringAntwoordBericht blokkeringAntwoordBericht =
                blokkeringVerzoekService.verwerkBlokkeringVerzoek(null);

        verify(brpDalService, times(0)).vraagOpBlokkering(null);
        verify(brpDalService, times(0)).verwijderBlokkering(null);

        assertNull(blokkeringAntwoordBericht.getCorrelationId());
        assertEquals("Het BlokkeringInfoVerzoekBericht bevat geen inhoud.",
                blokkeringAntwoordBericht.getToelichting());
        assertNull(blokkeringAntwoordBericht.getStartCyclus());
        assertEquals(StatusType.FOUT, blokkeringAntwoordBericht.getStatus());
        assertNotNull(blokkeringAntwoordBericht.getMessageId());
    }
}
