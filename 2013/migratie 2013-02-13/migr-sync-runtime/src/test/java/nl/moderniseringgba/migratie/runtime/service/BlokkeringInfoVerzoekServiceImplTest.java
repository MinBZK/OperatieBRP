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
import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringInfoVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.OngeldigePersoonslijstException;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.synchronisatie.domein.blokkering.entity.Blokkering;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BlokkeringInfoVerzoekServiceImplTest {

    private static final String ANUMMER = "186512465";
    private static final Long PROCESS_ID = 14L;
    private static final String GEMEENTE_NAAR = "1904";
    private static final String GEMEENTE_REGISTRATIE = "1905";
    private static final String PERSOONSAANDUIDING = PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP.toString();
    private static final Blokkering DUMMY_BLOKKERING = Blokkering.newInstance(ANUMMER, PROCESS_ID, GEMEENTE_NAAR,
            GEMEENTE_REGISTRATIE, PERSOONSAANDUIDING);

    @Mock
    private BrpDalService brpDalService;

    @InjectMocks
    private final BlokkeringInfoVerzoekService blokkeringInfoVerzoekService = new BlokkeringInfoVerzoekServiceImpl();

    @Before
    public void setup() throws OngeldigePersoonslijstException {
        when(brpDalService.vraagOpBlokkering(ANUMMER)).thenReturn(DUMMY_BLOKKERING);
    }

    @Test
    public void testBlokkeringInfoAntwoordOK() throws InputValidationException {
        final BlokkeringInfoVerzoekType blokkeringInfoVerzoekType = new BlokkeringInfoVerzoekType();

        blokkeringInfoVerzoekType.setANummer(ANUMMER);

        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek =
                new BlokkeringInfoVerzoekBericht(blokkeringInfoVerzoekType);

        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBericht =
                blokkeringInfoVerzoekService.verwerkBlokkeringInfoVerzoek(blokkeringInfoVerzoek);

        verify(brpDalService, times(1)).vraagOpBlokkering(ANUMMER);

        assertEquals(blokkeringInfoVerzoek.getMessageId(), blokkeringInfoAntwoordBericht.getCorrelationId());
        assertNull(blokkeringInfoAntwoordBericht.getToelichting());
        assertNull(blokkeringInfoAntwoordBericht.getStartCyclus());
        assertEquals(StatusType.OK, blokkeringInfoAntwoordBericht.getStatus());
        assertNotNull(blokkeringInfoAntwoordBericht.getMessageId());
    }

    @Test
    public void testBlokkeringInfoAntwoordFout() throws InputValidationException {
        final BlokkeringInfoVerzoekType blokkeringInfoVerzoekType = new BlokkeringInfoVerzoekType();

        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek =
                new BlokkeringInfoVerzoekBericht(blokkeringInfoVerzoekType);

        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBericht =
                blokkeringInfoVerzoekService.verwerkBlokkeringInfoVerzoek(blokkeringInfoVerzoek);

        verify(brpDalService, times(1)).vraagOpBlokkering(null);

        assertEquals(blokkeringInfoVerzoek.getMessageId(), blokkeringInfoAntwoordBericht.getCorrelationId());
        assertNotNull(blokkeringInfoAntwoordBericht.getToelichting());
        assertNull(blokkeringInfoAntwoordBericht.getStartCyclus());
        assertEquals(StatusType.FOUT, blokkeringInfoAntwoordBericht.getStatus());
        assertNotNull(blokkeringInfoAntwoordBericht.getMessageId());
    }

    @Test
    public void testBlokkeringInfoAntwoordLeegVerzoek() throws InputValidationException {

        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBericht =
                blokkeringInfoVerzoekService.verwerkBlokkeringInfoVerzoek(null);

        verify(brpDalService, times(0)).vraagOpBlokkering(null);

        assertNull(blokkeringInfoAntwoordBericht.getCorrelationId());
        assertNotNull(blokkeringInfoAntwoordBericht.getToelichting());
        assertNull(blokkeringInfoAntwoordBericht.getStartCyclus());
        assertEquals(StatusType.FOUT, blokkeringInfoAntwoordBericht.getStatus());
        assertNotNull(blokkeringInfoAntwoordBericht.getMessageId());
    }
}
