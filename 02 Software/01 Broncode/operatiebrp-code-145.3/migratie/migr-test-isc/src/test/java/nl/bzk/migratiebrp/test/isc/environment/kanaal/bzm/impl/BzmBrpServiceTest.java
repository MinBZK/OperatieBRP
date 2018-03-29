/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.xml.soap.SOAPMessage;

import nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm.impl.dispatch.DispatchClient;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm.impl.util.BzmSoapUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests voor de BzmBrpService.
 */
@RunWith(MockitoJUnitRunner.class)
public class BzmBrpServiceTest {

    @Mock
    private DispatchClient dispatchClient;

    @Mock
    private BzmSoapUtil bzmSoapUtil;

    @InjectMocks
    private BzmBrpServiceImpl bzmService;

    @Mock
    private SOAPMessage soapMessage;

    public void verstuurBzmBericht() {
        final String resultaat = bzmService.verstuurBzmBericht("<testRequest/>", "123ABC", "987ZYX");
        assertNotNull(resultaat);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verstuurBzmBerichtInvalidXML() {
        Mockito.when(bzmSoapUtil.maakSOAPBericht(Matchers.anyString())).thenReturn(null);
        bzmService.verstuurBzmBericht("bladiebla ongeldige xml", "123ABC", "987ZYX");
    }

    @Test
    public void verstuurBzmBerichtInvalidBzmBericht() {
        Mockito.when(dispatchClient.doInvokeService(Matchers.any(SOAPMessage.class), Matchers.anyString(), Matchers.anyString())).thenReturn(null);
        Mockito.when(bzmSoapUtil.maakSOAPBericht(Matchers.anyString())).thenReturn(soapMessage);
        final String resultaat = bzmService.verstuurBzmBericht("<testRequest/>", "123ABC", "987ZYX");
        assertNull(resultaat);
    }

    @Test
    public void verstuurBzmBerichtValid() {
        final String responseBody = "<testResponse/>";
        Mockito.when(dispatchClient.doInvokeService(Matchers.any(SOAPMessage.class), Matchers.anyString(), Matchers.anyString())).thenReturn(soapMessage);
        Mockito.when(bzmSoapUtil.maakSOAPBericht(Matchers.anyString())).thenReturn(soapMessage);
        Mockito.when(bzmSoapUtil.getSOAPResultaat(Matchers.any(SOAPMessage.class))).thenReturn(responseBody);
        final String resultaat = bzmService.verstuurBzmBericht("<testRequest/>", "123ABC", "987ZYX");
        assertNotNull(resultaat);
        assertEquals(responseBody, resultaat);
    }
}
