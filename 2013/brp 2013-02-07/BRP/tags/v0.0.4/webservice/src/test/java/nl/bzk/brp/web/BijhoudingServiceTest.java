/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import nl.bzk.brp.binding.BRPBericht;
import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.binding.MeldingCode;
import nl.bzk.brp.binding.SoortMelding;
import nl.bzk.brp.business.service.AuthenticatieService;
import nl.bzk.brp.business.service.BerichtVerwerker;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.logisch.BRPActie;
import org.apache.cxf.jaxws.context.WebServiceContextImpl;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityEngineResult;
import org.apache.ws.security.message.token.Timestamp;
import org.dom4j.dom.DOMElement;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test voor de {@link BijhoudingService} class. */
public class BijhoudingServiceTest {

    private BijhoudingService bijhoudingService;

    @Mock
    private BerichtVerwerker berichtVerwerker;

    @Mock
    private AuthenticatieService authService;

    private BRPBericht      testBericht;
    private X509Certificate certificaat;

    @Test
    public void testBijhouden() throws Exception {
        init(true, true);
        BerichtResultaat resultaat = new BerichtResultaat(null);
        Mockito.when(berichtVerwerker.verwerkBericht((BRPBericht) Mockito.any())).thenReturn(resultaat);

        Assert.assertEquals(resultaat, bijhoudingService.bijhouden(testBericht));
    }

    @Test
    public void testBijhoudenMislukteAuthenticatieGeenCertificaat() throws Exception {
        init(true, false);
        BerichtResultaat resultaat = bijhoudingService.bijhouden(testBericht);
        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Melding melding = resultaat.getMeldingen().get(0);
        Assert.assertEquals(MeldingCode.AUTH0001, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.getSoort());
    }

    @Test
    public void testBijhoudenMislukteAuthenticatiePartijNietGeauthenticeerd() throws Exception {
        init(true, true);
        Mockito.when(authService.isPartijGeauthenticeerdInBRP(1, certificaat)).thenReturn(false);
        BerichtResultaat resultaat = bijhoudingService.bijhouden(testBericht);
        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Melding melding = resultaat.getMeldingen().get(0);
        Assert.assertEquals(MeldingCode.AUTH0001, melding.getCode());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.getSoort());
    }

    /**
     * Methode voor het initialiseren van de te testen {@link BijhoudingService} implementatie en het zetten van de
     * security context hiervoor.
     */
    private void init(final boolean metSecurity, final boolean metCertificaat) throws Exception {
        MockitoAnnotations.initMocks(this);

        bijhoudingService = new BijhoudingServiceImpl();

        MessageContext messageContex = new WrappedMessageContext(new HashMap<String, Object>(), null, null);
        WebServiceContext wsContext = new WebServiceContextImpl(messageContex);

        if (metCertificaat) {
            final URL certificaatLocatie = BijhoudingServiceTest.class.getResource("/certificaat.crt");
            InputStream inStream = new FileInputStream(new File(certificaatLocatie.toURI()));
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            certificaat = (X509Certificate) cf.generateCertificate(inStream);
            inStream.close();
        }

        if (metSecurity) {
            //Security info
            WSSecurityEngineResult result = new WSSecurityEngineResult(WSConstants.SIGN,
                    new Timestamp(new DOMElement("test"), false));
            messageContex.put(WSS4JInInterceptor.SIGNATURE_RESULT, result);
            result.put(WSSecurityEngineResult.TAG_X509_CERTIFICATE, certificaat);
        }

        ReflectionTestUtils.setField(bijhoudingService, "wsContext", wsContext);
        ReflectionTestUtils.setField(bijhoudingService, "berichtVerwerker", berichtVerwerker);
        ReflectionTestUtils.setField(bijhoudingService, "authenticatieService", authService);

        testBericht = new BRPBericht();
        List<BRPActie> acties = new ArrayList<BRPActie>();
        BRPActie actie = new BRPActie();
        Partij partij = new Partij();
        partij.setId(1);
        actie.setPartij(partij);
        acties.add(actie);
        testBericht.setBrpActies(acties);

        Mockito.when(authService.isPartijGeauthenticeerdInBRP(1, certificaat)).thenReturn(true);

    }

}
