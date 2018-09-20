/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.verwerking.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.bzk.brp.model.internbericht.ProtocolleringOpdracht;
import nl.bzk.brp.model.operationeel.lev.LeveringPersoonModel;
import nl.bzk.brp.protocollering.TestData;
import nl.bzk.brp.protocollering.verwerking.exceptie.ProtocolleringFout;
import nl.bzk.brp.protocollering.verwerking.repository.LeveringPersoonRepository;
import nl.bzk.brp.protocollering.verwerking.repository.LeveringRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProtocolleringVerwerkingServiceImplTest {

    @Mock
    private LeveringPersoonRepository leveringPersoonRepository;

    @Mock
    private LeveringRepository leveringRepository;

    @InjectMocks
    private ProtocolleringVerwerkingService protocolleringVerwerkingService = new ProtocolleringVerwerkingServiceImpl();

    private ProtocolleringOpdracht protocolleringOpdracht = TestData.geefTestProtocolleringOpdracht();

    @Test
    public void testSlaProtocolleringOp() {
        when(leveringRepository.opslaanNieuweLevering(protocolleringOpdracht.getLevering()))
                .thenReturn(protocolleringOpdracht.getLevering());

        protocolleringVerwerkingService.slaProtocolleringOp(protocolleringOpdracht);

        verify(leveringRepository).opslaanNieuweLevering(protocolleringOpdracht.getLevering());
        verify(leveringPersoonRepository, times(2)).opslaanNieuweLeveringPersoon(any(LeveringPersoonModel.class));
    }

    @Test(expected = ProtocolleringFout.class)
    public void testSlaProtocolleringOpMetExceptieOpLevering() {
        when(leveringRepository.opslaanNieuweLevering(protocolleringOpdracht.getLevering()))
                .thenThrow(RuntimeException.class);

        protocolleringVerwerkingService.slaProtocolleringOp(protocolleringOpdracht);
    }

    @Test(expected = ProtocolleringFout.class)
    public void testSlaProtocolleringOpMetExceptieOpLeveringPersoon() {
        when(leveringRepository.opslaanNieuweLevering(protocolleringOpdracht.getLevering()))
                .thenReturn(protocolleringOpdracht.getLevering());
        when(leveringPersoonRepository.opslaanNieuweLeveringPersoon(any(LeveringPersoonModel.class)))
                .thenThrow(RuntimeException.class);

        protocolleringVerwerkingService.slaProtocolleringOp(protocolleringOpdracht);
    }

}
