/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import java.lang.reflect.Field;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.soap.SOAPFaultException;
import nl.bzk.brp.service.algemeen.request.XmlUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.InputSource;


@RunWith(MockitoJUnitRunner.class)
public class VerzendingVerwerkVrijBerichtWebServiceClientImplTest {

    private VerzendingVerwerkVrijBerichtWebServiceClientImpl wsClient;

    @Before
    public void voorTest() {
        wsClient = new VerzendingVerwerkVrijBerichtWebServiceClientImpl();
        wsClient.initWebServiceClient();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testInitWebServiceClient() throws Exception {
        Assert.assertEquals("verwerkVrijBericht", wsClient.getSoapAction());

        Field field =
                VerzendingVerwerkVrijBerichtWebServiceClientImpl.class.getSuperclass().getDeclaredField("webserviceClient");
        Dispatch<Source> client = (Dispatch<Source>) field.get(wsClient);
        Assert.assertEquals("true", client.getRequestContext().get("thread.local.request.context"));
    }

    @Test
    public void testPropertyWaardenNaVerstuurRequest() throws Exception {
        DOMSource request = XmlUtils.toDOMSource(new InputSource(new ClassPathResource("vrijbericht_verzoek.xml").getInputStream()));

        try {
            wsClient.verstuurRequest(request, "endpoint");
        } catch (SOAPFaultException e) {
            //E2E vormt integratietest voor invoke
        }

        Field field =
                VerzendingVerwerkVrijBerichtWebServiceClientImpl.class.getSuperclass().getDeclaredField("webserviceClient");
        Dispatch<Source> client = (Dispatch<Source>) field.get(wsClient);
        Assert.assertEquals("true", client.getRequestContext().get("schema-validation-enabled"));
        Assert.assertEquals("endpoint", client.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
        Assert.assertEquals(true, client.getRequestContext().get(BindingProvider.SOAPACTION_USE_PROPERTY));
        Assert.assertEquals("verwerkVrijBericht", client.getRequestContext().get(BindingProvider.SOAPACTION_URI_PROPERTY));
    }

}