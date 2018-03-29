/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering;

import com.google.common.collect.Maps;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.brp.domain.internbericht.admhndpublicatie.HandelingVoorPublicatie;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.dalapi.AdministratieveHandelingRepository;
import nl.bzk.brp.service.mutatielevering.brp.MaakBrpBerichtService;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiebericht;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import nl.bzk.brp.service.mutatielevering.dto.Mutatielevering;
import nl.bzk.brp.service.mutatielevering.leveringbepaling.MutatieleveringService;
import nl.bzk.brp.service.mutatielevering.lo3.MaakLo3BerichtService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class VerwerkHandelingServiceImplTest {

    private static final long ADMINISTRATIEVE_HANDELING_ID = 1L;

    @InjectMocks
    private VerwerkHandelingServiceImpl service;

    @Mock
    private VerwerkHandelingService.MaakMutatiehandelingService maakMutatiehandelingService;
    @Mock
    private MutatieleveringService mutatieleveringService;
    @Mock
    private MaakBrpBerichtService maakBrpBerichtService;
    @Mock
    private MaakLo3BerichtService maakLo3BerichtService;
    @Mock
    private VerwerkHandelingService.AttenderingPlaatsAfnemerindicatieService attenderingPlaatsAfnemerindicatieService;
    @Mock
    private VerwerkHandelingService.VerstuurAfnemerBerichtService verstuurAfnemerBerichtService;
    @Mock
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void testVolgordeVanStappen() throws VerwerkHandelingException, StapException, BlobException {

        final HandelingVoorPublicatie handelingVoorPublicatie = new HandelingVoorPublicatie();
        handelingVoorPublicatie.setAdmhndId(ADMINISTRATIEVE_HANDELING_ID);

        final Mutatiehandeling mutatiehandeling = new Mutatiehandeling(0L, Maps
                .newHashMap());
        Mockito.when(maakMutatiehandelingService.maakHandeling(handelingVoorPublicatie)).thenReturn(mutatiehandeling);
        Mockito.when(administratieveHandelingRepository.markeerAdministratieveHandelingAlsVerwerkt(Mockito.anyLong())).thenReturn(1);

        service.verwerkAdministratieveHandeling(handelingVoorPublicatie);

        final InOrder inOrder = Mockito.inOrder(
                maakMutatiehandelingService,
                maakBrpBerichtService,
                maakLo3BerichtService,
                attenderingPlaatsAfnemerindicatieService,
                verstuurAfnemerBerichtService,
                administratieveHandelingRepository
        );

        inOrder.verify(maakMutatiehandelingService).maakHandeling(handelingVoorPublicatie);
        inOrder.verify(maakBrpBerichtService).maakBerichten(Mockito.anyListOf(Mutatielevering.class), Mockito.anyObject());
        inOrder.verify(maakLo3BerichtService).maakBerichten(Mockito.anyObject(), Mockito.anyObject());
        inOrder.verify(attenderingPlaatsAfnemerindicatieService).plaatsAfnemerindicaties(Mockito.anyObject(), Mockito.anyListOf(Mutatiebericht.class));
        inOrder.verify(administratieveHandelingRepository).markeerAdministratieveHandelingAlsVerwerkt(ADMINISTRATIEVE_HANDELING_ID);
        inOrder.verify(verstuurAfnemerBerichtService).verstuurBerichten(Mockito.anyListOf(Mutatiebericht.class));
    }

    @Test
    public void testGeenGemarkeerdeAdministratieveHandelingen() throws StapException, BlobException, VerwerkHandelingException {

        final HandelingVoorPublicatie handelingVoorPublicatie = new HandelingVoorPublicatie();
        handelingVoorPublicatie.setAdmhndId(ADMINISTRATIEVE_HANDELING_ID);
        final Mutatiehandeling mutatiehandeling = new Mutatiehandeling(0L, Maps.newHashMap());
        Mockito.when(maakMutatiehandelingService.maakHandeling(handelingVoorPublicatie)).thenReturn(mutatiehandeling);
        Mockito.when(administratieveHandelingRepository.markeerAdministratieveHandelingAlsVerwerkt(Mockito.anyLong())).thenReturn(0);

        service.verwerkAdministratieveHandeling(handelingVoorPublicatie);

        final InOrder inOrder = Mockito.inOrder(
                maakMutatiehandelingService,
                maakBrpBerichtService,
                maakLo3BerichtService,
                attenderingPlaatsAfnemerindicatieService,
                administratieveHandelingRepository
        );

        inOrder.verify(maakMutatiehandelingService).maakHandeling(handelingVoorPublicatie);
        inOrder.verify(maakBrpBerichtService).maakBerichten(Mockito.anyListOf(Mutatielevering.class), Mockito.anyObject());
        inOrder.verify(maakLo3BerichtService).maakBerichten(Mockito.anyObject(), Mockito.anyObject());
        inOrder.verify(attenderingPlaatsAfnemerindicatieService).plaatsAfnemerindicaties(Mockito.anyObject(), Mockito.anyListOf(Mutatiebericht.class));
        inOrder.verify(administratieveHandelingRepository).markeerAdministratieveHandelingAlsVerwerkt(ADMINISTRATIEVE_HANDELING_ID);

        Mockito.verifyZeroInteractions(verstuurAfnemerBerichtService);
        Mockito.verifyNoMoreInteractions(administratieveHandelingRepository);
    }

    @Test
    public void testBlobExceptionBijPlaatsenAfnemerIndicatie() throws StapException, BlobException, VerwerkHandelingException {

        final HandelingVoorPublicatie handelingVoorPublicatie = new HandelingVoorPublicatie();
        handelingVoorPublicatie.setAdmhndId(ADMINISTRATIEVE_HANDELING_ID);

        final Mutatiehandeling mutatiehandeling = new Mutatiehandeling(0L, Maps.newHashMap());
        Mockito.when(maakMutatiehandelingService.maakHandeling(handelingVoorPublicatie)).thenReturn(mutatiehandeling);
        Mockito.when(administratieveHandelingRepository.markeerAdministratieveHandelingAlsVerwerkt(Mockito.anyLong())).thenReturn(1);
        Mockito.doThrow(BlobException.class).when(attenderingPlaatsAfnemerindicatieService)
                .plaatsAfnemerindicaties(Mockito.anyObject(), Mockito.anyListOf(Mutatiebericht.class));

        expectedException.expect(VerwerkHandelingException.class);

        service.verwerkAdministratieveHandeling(handelingVoorPublicatie);

        final InOrder inOrder = Mockito.inOrder(
                maakMutatiehandelingService,
                maakBrpBerichtService,
                maakLo3BerichtService,
                attenderingPlaatsAfnemerindicatieService
        );

        inOrder.verify(maakMutatiehandelingService).maakHandeling(handelingVoorPublicatie);
        inOrder.verify(maakBrpBerichtService).maakBerichten(Mockito.anyListOf(Mutatielevering.class), Mockito.anyObject());
        inOrder.verify(maakLo3BerichtService).maakBerichten(Mockito.anyObject(), Mockito.anyObject());
        inOrder.verify(attenderingPlaatsAfnemerindicatieService).plaatsAfnemerindicaties(Mockito.anyObject(), Mockito.anyListOf(Mutatiebericht.class));

        Mockito.verifyZeroInteractions(administratieveHandelingRepository);

    }


    @Test
    public void testStapExceptionBijMakenMutatiehandeling() throws StapException, BlobException, VerwerkHandelingException {

        final HandelingVoorPublicatie handelingVoorPublicatie = new HandelingVoorPublicatie();
        handelingVoorPublicatie.setAdmhndId(ADMINISTRATIEVE_HANDELING_ID);
        Mockito.doThrow(StapException.class).when(maakMutatiehandelingService).maakHandeling(handelingVoorPublicatie);

        expectedException.expect(VerwerkHandelingException.class);

        service.verwerkAdministratieveHandeling(handelingVoorPublicatie);

        final InOrder inOrder = Mockito.inOrder(
                maakMutatiehandelingService
        );

        inOrder.verify(maakMutatiehandelingService).maakHandeling(handelingVoorPublicatie);

        Mockito.verifyZeroInteractions(administratieveHandelingRepository);
    }


}
