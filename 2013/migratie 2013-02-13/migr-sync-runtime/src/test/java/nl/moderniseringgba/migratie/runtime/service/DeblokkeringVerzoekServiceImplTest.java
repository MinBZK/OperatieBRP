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
import nl.moderniseringgba.isc.esb.message.sync.generated.DeblokkeringVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringVerzoekBericht;
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
public class DeblokkeringVerzoekServiceImplTest {

    private static final String ANUMMER = "186512465";
    private static final String PROCESS_ID = "2451";
    private static final String GEMEENTE_NAAR = "1904";
    private static final String GEMEENTE_REGISTRATIE = "1905";
    private static final String PERSOONSAANDUIDING = PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP.toString();
    private static final Blokkering DUMMY_BLOKKERING = Blokkering.newInstance(ANUMMER, Long.valueOf(PROCESS_ID),
            GEMEENTE_NAAR, GEMEENTE_REGISTRATIE, PERSOONSAANDUIDING);

    @Mock
    private BrpDalService brpDalService;

    @InjectMocks
    private final DeblokkeringVerzoekService deblokkeringVerzoekService = new DeblokkeringVerzoekServiceImpl();

    @Before
    public void setup() throws OngeldigePersoonslijstException {
        when(brpDalService.vraagOpBlokkering(ANUMMER)).thenReturn(DUMMY_BLOKKERING);
    }

    @Test
    public void testDeblokkeringAntwoordOK() throws InputValidationException {
        final DeblokkeringVerzoekType deblokkeringVerzoekType = new DeblokkeringVerzoekType();

        deblokkeringVerzoekType.setANummer(ANUMMER);
        deblokkeringVerzoekType.setGemeenteRegistratie(GEMEENTE_REGISTRATIE);
        deblokkeringVerzoekType.setProcessId(PROCESS_ID);

        final DeblokkeringVerzoekBericht deblokkeringVerzoek =
                new DeblokkeringVerzoekBericht(deblokkeringVerzoekType);

        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht =
                deblokkeringVerzoekService.verwerkDeblokkeringVerzoek(deblokkeringVerzoek);

        verify(brpDalService, times(1)).vraagOpBlokkering(ANUMMER);
        verify(brpDalService, times(1)).verwijderBlokkering(DUMMY_BLOKKERING);

        assertEquals(deblokkeringVerzoek.getMessageId(), deblokkeringAntwoordBericht.getCorrelationId());
        assertNull(deblokkeringAntwoordBericht.getToelichting());
        assertNull(deblokkeringAntwoordBericht.getStartCyclus());
        assertEquals(StatusType.OK, deblokkeringAntwoordBericht.getStatus());
        assertNotNull(deblokkeringAntwoordBericht.getMessageId());
    }

    @Test
    public void testDeblokkeringAntwoordFout() throws InputValidationException {
        final DeblokkeringVerzoekType deblokkeringVerzoekType = new DeblokkeringVerzoekType();

        final DeblokkeringVerzoekBericht blokkeringInfoVerzoek =
                new DeblokkeringVerzoekBericht(deblokkeringVerzoekType);

        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht =
                deblokkeringVerzoekService.verwerkDeblokkeringVerzoek(blokkeringInfoVerzoek);

        verify(brpDalService, times(1)).vraagOpBlokkering(null);

        assertEquals(blokkeringInfoVerzoek.getMessageId(), deblokkeringAntwoordBericht.getCorrelationId());
        assertNotNull(deblokkeringAntwoordBericht.getToelichting());
        assertNull(deblokkeringAntwoordBericht.getStartCyclus());
        assertEquals(StatusType.FOUT, deblokkeringAntwoordBericht.getStatus());
        assertNotNull(deblokkeringAntwoordBericht.getMessageId());
    }

    @Test
    public void testDeblokkeringAntwoordLeegVerzoek() throws InputValidationException {

        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht =
                deblokkeringVerzoekService.verwerkDeblokkeringVerzoek(null);

        verify(brpDalService, times(0)).vraagOpBlokkering(null);
        verify(brpDalService, times(0)).verwijderBlokkering(null);

        assertNull(deblokkeringAntwoordBericht.getCorrelationId());
        assertNotNull(deblokkeringAntwoordBericht.getToelichting());
        assertNull(deblokkeringAntwoordBericht.getStartCyclus());
        assertEquals(StatusType.FOUT, deblokkeringAntwoordBericht.getStatus());
        assertNotNull(deblokkeringAntwoordBericht.getMessageId());
    }
}
