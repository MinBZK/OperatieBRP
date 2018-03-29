/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.protocollering;

import nl.bzk.brp.service.selectie.protocollering.SelectieProtocolleringService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SelectieProtocolleringJMXAgentTest {

    @InjectMocks
    private SelectieProtocolleringJMXAgent selectieProtocolleringJMXAgent;

    @Mock
    private SelectieProtocolleringService selectieProtocolleringService;


    @Test
    public void startProtocolleren() throws Exception {
        selectieProtocolleringJMXAgent.startProtocolleren();

        Mockito.verify(selectieProtocolleringService, Mockito.times(1)).start();
    }

    @Test
    public void stopProtocolleren() throws Exception {
        selectieProtocolleringJMXAgent.stopProtocolleren();

        Mockito.verify(selectieProtocolleringService, Mockito.times(1)).stop();
    }

    @Test
    public void wachtTotProtocollerenGestopt() throws Exception {
        selectieProtocolleringJMXAgent.wachtTotProtocollerenGestopt();

        Mockito.verify(selectieProtocolleringService, Mockito.times(1)).isRunning();
    }

}