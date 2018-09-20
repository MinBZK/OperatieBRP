/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.brp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

import java.math.BigInteger;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.AuthenticatieMiddelDTO;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.business.dto.antwoord.PersoonZoekCriteriaAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.PersoonZoekCriteria;
import nl.bzk.brp.bevraging.business.service.AuthenticatieService;
import nl.bzk.brp.bevraging.business.service.BerichtUitvoerderService;
import nl.bzk.brp.bevraging.business.service.SvnVersionService;
import nl.bzk.brp.bevraging.business.service.exception.MeerdereAuthenticatieMiddelenExceptie;
import nl.bzk.brp.bevraging.ws.service.interceptor.helper.BerichtIdsThreadLocal;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonAntwoord;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonVraag;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.kern.Persoon;
import nl.bzk.brp.domein.kern.SoortPersoon;

import org.apache.cxf.binding.soap.SoapFault;
import org.apache.ws.security.WSSecurityEngineResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link OpvragenPersoonService} class.
 */
public class OpvragenPersoonServiceTest {

    @Mock
    private AuthenticatieService                authenticatieServiceMock;
    @Mock
    private BerichtUitvoerderService            berichtUitvoerderServiceMock;
    @Mock
    private WebServiceContext                   webServiceContextMock;
    @Mock
    private SvnVersionService                   svnVersionServiceMock;

    private OpvragenPersoonService              service;

    private DomeinObjectFactory                 factory = new PersistentDomeinObjectFactory();

    private ArgumentCaptor<PersoonZoekCriteria> cmdVerzoek;
    private ArgumentCaptor<BerichtContext>      cmdContext;

    /**
     * Unit test voor de {@link OpvragenPersoonService#opvragenPersoon(OpvragenPersoonVraag)} methode. In deze test
     * wordt specifiek getest op het opvragen van een persoon op basis van een <b>leeg</b> BSN.
     */
    @Test
    public void testOpvragenPersoonMetLeegBSN() {
        OpvragenPersoonVraag vraag = new OpvragenPersoonVraag();
        vraag.setBsn(null);

        Mockito.when(
                berichtUitvoerderServiceMock.voerBerichtUit(any(PersoonZoekCriteria.class), any(BerichtContext.class)))
                .thenReturn(voegPersonenToeAanAntwoordBericht());

        OpvragenPersoonAntwoord antwoord = service.opvragenPersoon(vraag);
        Mockito.verify(berichtUitvoerderServiceMock).voerBerichtUit(cmdVerzoek.capture(), cmdContext.capture());

        assertNotNull(cmdContext.getValue());
        assertNotNull(cmdVerzoek.getValue());
        assertNull(cmdVerzoek.getValue().getBsn());
        assertEquals(0, antwoord.getAantalPersonen());
        assertTrue(antwoord.getPersoon().isEmpty());
    }

    /**
     * Unit test voor de {@link OpvragenPersoonService#opvragenPersoon(OpvragenPersoonVraag)} methode. In deze test
     * wordt specifiek getest op het opvragen van een persoon op basis van een <b>bestaand</b> BSN.
     */
    @Test
    public final void testOpvragenPersoonMetBestaandBSN() {
        OpvragenPersoonVraag vraag = new OpvragenPersoonVraag();
        vraag.setBsn("123456789");

        Mockito.when(
                berichtUitvoerderServiceMock.voerBerichtUit(any(PersoonZoekCriteria.class), any(BerichtContext.class)))
                .thenReturn(voegPersonenToeAanAntwoordBericht("123456789"));

        OpvragenPersoonAntwoord antwoord = service.opvragenPersoon(vraag);
        Mockito.verify(berichtUitvoerderServiceMock).voerBerichtUit(cmdVerzoek.capture(), cmdContext.capture());

        assertNotNull(cmdContext.getValue());
        assertNotNull(cmdVerzoek.getValue());
        assertEquals("123456789", cmdVerzoek.getValue().getBsn());
        assertEquals(1, antwoord.getAantalPersonen());
        assertEquals("123456789", antwoord.getPersoon().get(0).getBsn());
    }

    /**
     * Unit test voor een BSN met voorloopnullen. Test of het hebben van voorloopnullen niet fout gaat en dus het
     * correcte BSN (zonder voorloopnullen) oplevert en of het antwoord daarna wel weer correct wordt voorzien van
     * voorloopnullen.
     */
    @Test
    public final void testVoorloopnullenInBsn() {
        OpvragenPersoonVraag vraag = new OpvragenPersoonVraag();
        vraag.setBsn("001234567");

        Mockito.when(
                berichtUitvoerderServiceMock.voerBerichtUit(any(PersoonZoekCriteria.class), any(BerichtContext.class)))
                .thenReturn(voegPersonenToeAanAntwoordBericht("1234567"));

        OpvragenPersoonAntwoord antwoord = service.opvragenPersoon(vraag);
        Mockito.verify(berichtUitvoerderServiceMock).voerBerichtUit(cmdVerzoek.capture(), cmdContext.capture());

        assertNotNull(cmdVerzoek.getValue());
        assertNotNull(cmdContext.getValue());
        assertEquals("001234567", cmdVerzoek.getValue().getBsn());
        assertEquals(1, antwoord.getAantalPersonen());
        assertEquals("1234567", antwoord.getPersoon().get(0).getBsn());
    }

    /**
     * Unit test voor de {@link OpvragenPersoonService#opvragenPersoon(OpvragenPersoonVraag)} methode. In deze test
     * wordt specifiek getest op het opvragen van een persoon maar wat een fout oplevert.
     */
    @Test
    public final void testOpvragenPersoonMetFout() {
        OpvragenPersoonVraag vraag = new OpvragenPersoonVraag();
        vraag.setBsn("123456789");

        Mockito.when(
                berichtUitvoerderServiceMock.voerBerichtUit(any(PersoonZoekCriteria.class), any(BerichtContext.class)))
                .thenReturn(voegFoutenToeAanAntwoordBericht(BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT));

        OpvragenPersoonAntwoord antwoord = service.opvragenPersoon(vraag);
        Mockito.verify(berichtUitvoerderServiceMock).voerBerichtUit(cmdVerzoek.capture(), cmdContext.capture());

        assertNotNull(cmdVerzoek.getValue());
        assertNotNull(cmdContext.getValue());
        assertEquals("123456789", cmdVerzoek.getValue().getBsn());
        assertEquals(0, antwoord.getAantalPersonen());
        assertEquals(1, antwoord.getAantalFouten());
        assertEquals("ALG0001", antwoord.getOpvragenPersoonAntwoordFout().get(0).getId());
    }

    /**
     * Unit test voor het opvragen van een persoon, maar waarvoor het certificaat onbekend of niet geldig is.
     */
    @Test
    public final void testOpvragenPersoonMetOnbekendCertificaat() {
        // Zorg dat er geen authenticatie middel wordt gevonden
        Mockito.when(
                authenticatieServiceMock.zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(any(Long.class),
                        (byte[]) Matchers.any(), anyString())).thenReturn(null);

        OpvragenPersoonVraag vraag = new OpvragenPersoonVraag();
        vraag.setBsn("123456789");

        Mockito.when(
                berichtUitvoerderServiceMock.voerBerichtUit(any(PersoonZoekCriteria.class), any(BerichtContext.class)))
                .thenReturn(voegPersonenToeAanAntwoordBericht("123456789"));

        OpvragenPersoonAntwoord antwoord = service.opvragenPersoon(vraag);
        assertNotNull(antwoord);
        assertEquals(0, antwoord.getAantalPersonen());
        assertEquals(0, antwoord.getPersoon().size());
        assertEquals(1, antwoord.getAantalFouten());
        assertEquals("AUT0001", antwoord.getOpvragenPersoonAntwoordFout().get(0).getId());
    }

    /**
     * Unit test voor het opvragen van een persoon, maar waarvoor meerdere certificaten geldig zijn. Dit zou overigens
     * niet mogen optreden, daar dit functioneel niet kan/mag.
     */
    @Test(expected = SoapFault.class)
    public final void testOpvragenPersoonMetMeerdereGeldigeCertificaat() {
        Mockito.when(
                authenticatieServiceMock.zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(any(Long.class),
                        (byte[]) Matchers.any(), anyString())).thenThrow(new MeerdereAuthenticatieMiddelenExceptie());

        OpvragenPersoonVraag vraag = new OpvragenPersoonVraag();
        vraag.setBsn("123456789");

        Mockito.when(
                berichtUitvoerderServiceMock.voerBerichtUit(any(PersoonZoekCriteria.class), any(BerichtContext.class)))
                .thenReturn(voegPersonenToeAanAntwoordBericht("123456789"));

        OpvragenPersoonAntwoord antwoord = service.opvragenPersoon(vraag);
        assertNotNull(antwoord);
        assertEquals(0, antwoord.getAantalPersonen());
        assertEquals(0, antwoord.getPersoon().size());
        assertEquals(1, antwoord.getAantalFouten());
        assertEquals("AUT0001", antwoord.getOpvragenPersoonAntwoordFout().get(0).getId());
    }

    /**
     * Unit test voor het opvragen van een persoon, maar wat een fout oplevert en er een id in de threadlocal zit.
     */
    @Test
    public final void testOpvragenPersoonMetOnbekendeFoutMetIdInThreadLocal() {
        Mockito.when(
                authenticatieServiceMock.zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(any(Long.class),
                        (byte[]) Matchers.any(), anyString())).thenThrow(new RuntimeException());

        OpvragenPersoonVraag vraag = new OpvragenPersoonVraag();
        vraag.setBsn("123456789");
        BerichtIdsThreadLocal.setBerichtenIds(3L, 4L);

        try {
            service.opvragenPersoon(vraag);
            fail("Verwachte exceptie is niet opgetreden");
        } catch (SoapFault e) {
            assertNotNull(e.getMessage());
            assertNotNull(e.getCause());
        }
    }

    /**
     * Unit test voor het opvragen van een persoon, maar wat een fout oplevert en er geen id in de threadlocal zit.
     */
    @Test
    public final void testOpvragenPersoonMetOnbekendeFoutZonderIdInThreadLocal() {
        Mockito.when(
                authenticatieServiceMock.zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(any(Long.class),
                        (byte[]) Matchers.any(), anyString())).thenThrow(new RuntimeException());

        OpvragenPersoonVraag vraag = new OpvragenPersoonVraag();
        vraag.setBsn("123456789");

        try {
            service.opvragenPersoon(vraag);
            fail("Verwachte exceptie is niet opgetreden");
        } catch (SoapFault e) {
            assertNotNull(e.getMessage());
            assertNotNull(e.getCause());
        }
    }

    /**
     * Helper methode die personen toevoegd aan het het antwoord, op basis van de opgegeven bsns, en het dan
     * gevormde antwoord zet in het bericht.
     *
     * @param bsns de bsns van de personen die moeten worden toegevoegd.
     * @return PersoonZoekCriteriaAntwoord
     */
    private PersoonZoekCriteriaAntwoord voegPersonenToeAanAntwoordBericht(final String... bsns) {
        Set<Persoon> personen = new HashSet<Persoon>();
        for (String bsn : bsns) {
            Persoon persoon = factory.createPersoon();
            persoon.setSoort(SoortPersoon.INGESCHREVENE);
            persoon.setBurgerservicenummer(bsn);
            personen.add(persoon);
        }
        return new PersoonZoekCriteriaAntwoord(personen);
    }

    /**
     * Helper methode die fouten toevoegd aan het het antwoord, op basis van de opgegeven codes, en het dan
     * gevormde antwoord zet in het bericht.
     *
     * @param foutCodes de codes van de fouten die moeten worden toegevoegd.
     * @return PersoonZoekCriteriaAntwoord
     */
    private PersoonZoekCriteriaAntwoord voegFoutenToeAanAntwoordBericht(final BerichtVerwerkingsFoutCode... foutCodes) {
        PersoonZoekCriteriaAntwoord antwoord = new PersoonZoekCriteriaAntwoord();
        for (BerichtVerwerkingsFoutCode code : foutCodes) {
            antwoord.voegFoutToe(new BerichtVerwerkingsFout(code, BerichtVerwerkingsFoutZwaarte.FOUT));
        }
        return antwoord;
    }

    /**
     * Initializeert de mocks en de service die in deze unit test class worden gebruikt.
     */
    @Before
    public final void initMocksEnService() {
        MockitoAnnotations.initMocks(this);
        MessageContext msgContext = Mockito.mock(MessageContext.class);

        Mockito.mock(SvnVersionService.class);

        WSSecurityEngineResult wsSecurityEngineResult = genereerWsSecurityEngineResultMock(msgContext);
        AuthenticatieMiddelDTO authenticatieMiddelDTO = new AuthenticatieMiddelDTO(1, 2);

        Mockito.when(msgContext.get("wss4j.signature.result")).thenReturn(wsSecurityEngineResult);
        Mockito.when(msgContext.get("BRP_BERICHT_ID")).thenReturn(2L);
        Mockito.when(
                authenticatieServiceMock.zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(any(Long.class),
                        (byte[]) Matchers.any(), anyString())).thenReturn(authenticatieMiddelDTO);
        cmdVerzoek = ArgumentCaptor.forClass(PersoonZoekCriteria.class);
        cmdContext = ArgumentCaptor.forClass(BerichtContext.class);
        service = new OpvragenPersoonService();

        ReflectionTestUtils.setField(service, "svnVersionService", svnVersionServiceMock);
        ReflectionTestUtils.setField(service, "berichtUitvoerderService", berichtUitvoerderServiceMock);
        ReflectionTestUtils.setField(service, "webServiceContext", webServiceContextMock);
        ReflectionTestUtils.setField(service, "authenticatieService", authenticatieServiceMock);

        // Initialiseer de BerichtIDThreadLocal om een NullPointerException te voorkomen.
        // Normaal gesproken wordt dit door de ArchiveringInInterceptor gedaan, maar die wordt in de unit tests
        // ge-bypassed.
        BerichtIdsThreadLocal.setBerichtenIds(1L, 2L);
    }

    /**
     * Ruimt ThreadLocal op.
     */
    @After
    public final void ruimOp() {
        BerichtIdsThreadLocal.verwijder();
    }

    /**
     * Genereert een mock voor de {@link WSSecurityEngineResult} class inclusief benodigde mocks.
     *
     * @param msgContext de context die bevraagd wordt.
     * @return een mock van {@link WSSecurityEngineResult}
     */
    private WSSecurityEngineResult genereerWsSecurityEngineResultMock(final MessageContext msgContext) {
        WSSecurityEngineResult wsSecurityEngineResult = Mockito.mock(WSSecurityEngineResult.class);

        X509Certificate cert = Mockito.mock(X509Certificate.class);

        Mockito.when(cert.getSerialNumber()).thenReturn(new BigInteger("1315331010"));

        Principal principal = Mockito.mock(Principal.class);

        Mockito.when(principal.getName()).thenReturn("CN=serverkey");

        Mockito.when(cert.getSubjectDN()).thenReturn(principal);

        Mockito.when(webServiceContextMock.getMessageContext()).thenReturn(msgContext);
        Mockito.when(wsSecurityEngineResult.get("x509-certificate")).thenReturn(cert);
        return wsSecurityEngineResult;
    }

}
