/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.bezemwagen.service.impl;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.levering.algemeen.service.AdministratieveHandelingenOverslaanService;
import nl.bzk.brp.levering.dataaccess.repository.alleenlezen.OngeleverdeAdministratieveHandelingRepository;
import nl.bzk.brp.levering.bezemwagen.jms.AdministratieveHandelingVerwerker;
import nl.bzk.brp.levering.bezemwagen.service.AdministratieveHandelingPublicatieService;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class AdministratieveHandelingPublicatieServiceImplTest {

    @InjectMocks
    private final AdministratieveHandelingPublicatieService administratieveHandelingPublicatieService =
        new AdministratieveHandelingPublicatieServiceImpl();

    @Mock
    private OngeleverdeAdministratieveHandelingRepository administratieveHandelingRepository;

    @Mock
    private AdministratieveHandelingVerwerker administratieveHandelingVerwerker;

    @Mock
    private AdministratieveHandelingenOverslaanService administratieveHandelingenOverslaanService;

    private final List<BigInteger>                    onverwerkteAdministratieveHandelingen    = new ArrayList<>();
    private final List<SoortAdministratieveHandeling> overslaanSoortAdministratieveHandelingen = new ArrayList<>();

    @Before
    public final void init() {
        when(administratieveHandelingenOverslaanService
            .geefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden())
            .thenReturn(overslaanSoortAdministratieveHandelingen);
        when(administratieveHandelingRepository
            .haalOnverwerkteAdministratieveHandelingenOp(overslaanSoortAdministratieveHandelingen, null))
            .thenReturn(onverwerkteAdministratieveHandelingen);
    }

    @Test
    public final void testPlaatsOnverwerkteAdministratieveHandelingenOpQueue() {
        onverwerkteAdministratieveHandelingen.add(new BigInteger("123"));
        onverwerkteAdministratieveHandelingen.add(new BigInteger("456"));
        onverwerkteAdministratieveHandelingen.add(new BigInteger("789"));

        administratieveHandelingPublicatieService.plaatsOnverwerkteAdministratieveHandelingenOpQueue();

        verify(administratieveHandelingVerwerker)
            .plaatsAdministratieveHandelingenOpQueue(anyListOf(BigInteger.class));
        verify(administratieveHandelingenOverslaanService)
                .geefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden();
    }

    @Test
    public final void testPlaatsOnverwerkteAdministratieveHandelingenOpQueueLegeLijst() {
        administratieveHandelingPublicatieService.plaatsOnverwerkteAdministratieveHandelingenOpQueue();

        verify(administratieveHandelingVerwerker)
                .plaatsAdministratieveHandelingenOpQueue(anyListOf(BigInteger.class));
        verify(administratieveHandelingenOverslaanService)
                .geefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden();
    }

    @Test
    public final void testPlaatsOnverwerkteAdministratieveHandelingenOpQueueMetMaximum() {
        ReflectionTestUtils.setField(administratieveHandelingPublicatieService, "aantalAdministratieveHandelingenPerKeer", 2);

        administratieveHandelingPublicatieService.plaatsOnverwerkteAdministratieveHandelingenOpQueue();

        verify(administratieveHandelingRepository).haalOnverwerkteAdministratieveHandelingenOp(overslaanSoortAdministratieveHandelingen, 2);
    }
}
