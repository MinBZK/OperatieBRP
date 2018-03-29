/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.service.algemeen;

import static org.mockito.Mockito.verifyZeroInteractions;

import com.google.common.collect.Sets;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.protocollering.domain.algemeen.LeveringPersoon;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import nl.bzk.brp.protocollering.service.dal.ProtocolleringRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProtocolleringServiceImplTest {


    @InjectMocks
    private ProtocolleringServiceImpl protocolleringVerwerkingService;
    @Mock
    private ProtocolleringRepository protocolleringRepository;
    @Mock
    private ScopePatroonService scopePatroonService;

    private ProtocolleringOpdracht protocolleringOpdracht;
    private static final ZonedDateTime peilmomentFormeelResultaat = LocalDate.of(2000, 1, 1).atTime(1, 1, 1).atZone(DatumUtil.BRP_ZONE_ID);

    @Before
    public void voorTest() {
        protocolleringOpdracht = TestData.geefTestProtocolleringOpdracht(peilmomentFormeelResultaat, TestData.geefTestLeveringPersoon());
    }

    @Test
    public void testSlaProtocolleringOp() {
        protocolleringVerwerkingService.protocolleer(protocolleringOpdracht);

        Mockito.verify(protocolleringRepository).opslaanNieuweLevering(Matchers.any(Leveringsaantekening.class));
        verifyZeroInteractions(scopePatroonService);
    }

    @Test
    public void testSlaProtocolleringOpDatumAanvFormPeriodeIsNull() {
        protocolleringOpdracht = TestData.geefTestProtocolleringOpdracht(null, TestData.geefTestLeveringPersoon());
        protocolleringVerwerkingService.protocolleer(protocolleringOpdracht);

        Mockito.verify(protocolleringRepository).opslaanNieuweLevering(Matchers.any(Leveringsaantekening.class));
        verifyZeroInteractions(scopePatroonService);
    }

    @Test
    public void testSlaProtocolleringGeleverdePersonenIsNull() {
        protocolleringOpdracht = TestData.geefTestProtocolleringOpdracht(peilmomentFormeelResultaat, null);
        protocolleringVerwerkingService.protocolleer(protocolleringOpdracht);

        Mockito.verify(protocolleringRepository).opslaanNieuweLevering(Matchers.any(Leveringsaantekening.class));
        verifyZeroInteractions(scopePatroonService);
    }

    @Test
    public void testSlaProtocolleringGeleverdePersonenTsLaatsteWijzigingIsNull() {
        final LeveringPersoon leveringPersoon1 = new LeveringPersoon(1L, DatumUtil.nuAlsZonedDateTime());
        final List<LeveringPersoon> leveringPersonen = new ArrayList<>();
        leveringPersonen.add(leveringPersoon1);
        protocolleringOpdracht = TestData.geefTestProtocolleringOpdracht(peilmomentFormeelResultaat, leveringPersonen);
        protocolleringVerwerkingService.protocolleer(protocolleringOpdracht);

        Mockito.verify(protocolleringRepository).opslaanNieuweLevering(Matchers.any(Leveringsaantekening.class));
        verifyZeroInteractions(scopePatroonService);
    }

    @Test
    public void testSlaProtocolleringOpMetScopingPatroon() {
        protocolleringOpdracht.setScopingAttributen(Sets.newHashSet(1));
        protocolleringVerwerkingService.protocolleer(protocolleringOpdracht);

        Mockito.verify(protocolleringRepository).opslaanNieuweLevering(Matchers.any(Leveringsaantekening.class));
        Mockito.verify(scopePatroonService).getScopePatroon(Sets.newHashSet(1));
    }

    @Test
    public void testSlaProtocolleringOpMetLeegScopingPatroon() {
        protocolleringOpdracht.setScopingAttributen(Sets.newHashSet());
        protocolleringVerwerkingService.protocolleer(protocolleringOpdracht);

        Mockito.verify(protocolleringRepository).opslaanNieuweLevering(Matchers.any(Leveringsaantekening.class));
        verifyZeroInteractions(scopePatroonService);
    }
}
