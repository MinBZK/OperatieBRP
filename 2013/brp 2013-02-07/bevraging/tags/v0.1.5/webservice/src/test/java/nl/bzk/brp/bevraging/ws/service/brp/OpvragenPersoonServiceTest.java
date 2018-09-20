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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;
import nl.bzk.brp.bevraging.business.berichtcmds.OpvragenPersoonBerichtCommand;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.business.dto.BrpBerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.PersoonZoekCriteriaAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.PersoonZoekCriteria;
import nl.bzk.brp.bevraging.business.service.AutenticatieService;
import nl.bzk.brp.bevraging.business.service.AutenticatieService.AuthenticatieMiddelDTO;
import nl.bzk.brp.bevraging.business.service.BerichtUitvoerderService;
import nl.bzk.brp.bevraging.business.service.SvnVersionService;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonAntwoord;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonVraag;
import org.apache.ws.security.WSSecurityEngineResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import sun.security.x509.X509CertImpl;


/**
 * Unit test voor de {@link OpvragenPersoonService} class.
 */
public class OpvragenPersoonServiceTest {

    @Mock
    private AutenticatieService                           authenticatieServiceMock;
    @Mock
    private BerichtUitvoerderService                      berichtUitvoerderServiceMock;
    @Mock
    private ApplicationContext                            applicationContextMock;
    @Mock
    private WebServiceContext                             webServiceContextMock;

    @Mock
    private SvnVersionService                             svnVersionServiceMock;

    private OpvragenPersoonService                        service;

    private ArgumentCaptor<OpvragenPersoonBerichtCommand> cmdArgument;

    /**
     * Unit test voor de {@link OpvragenPersoonService#opvragenPersoon(OpvragenPersoonVraag)} methode. In deze test
     * wordt specifiek getest op het opvragen van een persoon op basis van een <b>leeg</b> BSN.
     */
    @Test
    public void testOpvragenPersoonMetLeegBSN() {
        OpvragenPersoonVraag vraag = new OpvragenPersoonVraag();
        vraag.setBsn(null);

        OpvragenPersoonAntwoord antwoord = service.opvragenPersoon(vraag);
        Mockito.verify(berichtUitvoerderServiceMock).voerBerichtUit(cmdArgument.capture());

        assertNotNull(cmdArgument.getValue().getContext());
        assertNotNull(cmdArgument.getValue().getVerzoek());
        assertNull(cmdArgument.getValue().getVerzoek().getBsn());
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
        vraag.setBsn(new BigDecimal("123456789"));

        Mockito.doAnswer(new Answer<Void>() {

            @Override
            public Void answer(final InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                OpvragenPersoonBerichtCommand bericht = (OpvragenPersoonBerichtCommand) args[0];
                voegPersonenToeAanAntwoordBericht(bericht, 123456789L);
                return null;
            }

        }).when(berichtUitvoerderServiceMock).voerBerichtUit(any(BrpBerichtCommand.class));

        OpvragenPersoonAntwoord antwoord = service.opvragenPersoon(vraag);
        Mockito.verify(berichtUitvoerderServiceMock).voerBerichtUit(cmdArgument.capture());

        assertNotNull(cmdArgument.getValue().getContext());
        assertNotNull(cmdArgument.getValue().getVerzoek());
        assertEquals(Long.valueOf(123456789), cmdArgument.getValue().getVerzoek().getBsn());
        assertEquals(1, antwoord.getAantalPersonen());
        assertEquals(123456789L, antwoord.getPersoon().get(0).getBsn().longValue());
    }

    /**
     * Unit test voor de {@link OpvragenPersoonService#opvragenPersoon(OpvragenPersoonVraag)} methode. In deze test
     * wordt specifiek getest op het opvragen van een persoon maar wat een fout oplevert.
     */
    @Test
    public final void testOpvragenPersoonMetFout() {
        OpvragenPersoonVraag vraag = new OpvragenPersoonVraag();
        vraag.setBsn(new BigDecimal("123456789"));

        Mockito.doAnswer(new Answer<Void>() {

            @Override
            public Void answer(final InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                OpvragenPersoonBerichtCommand bericht = (OpvragenPersoonBerichtCommand) args[0];
                voegFoutenToeAanAntwoordBericht(bericht, BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT);
                return null;
            }

        }).when(berichtUitvoerderServiceMock).voerBerichtUit(any(BrpBerichtCommand.class));

        OpvragenPersoonAntwoord antwoord = service.opvragenPersoon(vraag);
        Mockito.verify(berichtUitvoerderServiceMock).voerBerichtUit(cmdArgument.capture());

        assertNotNull(cmdArgument.getValue().getContext());
        assertNotNull(cmdArgument.getValue().getVerzoek());
        assertEquals(Long.valueOf(123456789), cmdArgument.getValue().getVerzoek().getBsn());
        assertEquals(0, antwoord.getAantalPersonen());
        assertEquals(1, antwoord.getAantalFouten());
        assertEquals("ALG0001", antwoord.getOpvragenPersoonAntwoordFout().get(0).getId());
    }

    /**
     * Helper methode die personen toevoegd aan het het antwoord, op basis van de opgegeven bsns, en het dan
     * gevormde antwoord zet in het bericht.
     *
     * @param bericht het bericht dat voorzien moet worden van een antwoord.
     * @param bsns de bsns van de personen die moeten worden toegevoegd.
     */
    private void voegPersonenToeAanAntwoordBericht(final OpvragenPersoonBerichtCommand bericht, final long... bsns) {
        Set<nl.bzk.brp.bevraging.domein.Persoon> personen = new HashSet<nl.bzk.brp.bevraging.domein.Persoon>();
        for (long bsn : bsns) {
            nl.bzk.brp.bevraging.domein.Persoon persoon =
                new nl.bzk.brp.bevraging.domein.Persoon(SoortPersoon.INGESCHREVENE);
            persoon.setBurgerservicenummer(bsn);
            personen.add(persoon);
        }
        PersoonZoekCriteriaAntwoord antwoord = new PersoonZoekCriteriaAntwoord(personen);
        ReflectionTestUtils.setField(bericht, "antwoord", antwoord);
    }

    /**
     * Helper methode die fouten toevoegd aan het het antwoord, op basis van de opgegeven codes, en het dan
     * gevormde antwoord zet in het bericht.
     *
     * @param bericht het bericht dat voorzien moet worden van een antwoord.
     * @param foutCodes de codes van de fouten die moeten worden toegevoegd.
     */
    private void voegFoutenToeAanAntwoordBericht(final OpvragenPersoonBerichtCommand bericht,
            final BerichtVerwerkingsFoutCode... foutCodes)
    {
        for (BerichtVerwerkingsFoutCode code : foutCodes) {
            bericht.voegFoutToe(new BerichtVerwerkingsFout(code, BerichtVerwerkingsFoutZwaarte.FOUT));
        }
    }

    /**
     * Initializeert de mocks en de service die in deze unit test class worden gebruikt.
     */
    @Before
    public final void initMocksEnService() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(applicationContextMock.getBean(anyString(), any(), any())).thenAnswer(
                new Answer<OpvragenPersoonBerichtCommand>() {

                    @Override
                    public OpvragenPersoonBerichtCommand answer(final InvocationOnMock invocation) {
                        Object[] args = invocation.getArguments();
                        OpvragenPersoonBerichtCommand opvragenPersoonBerichtCommand =
                            new OpvragenPersoonBerichtCommand((PersoonZoekCriteria) args[1],
                                    (BrpBerichtContext) args[2]);

                        ReflectionTestUtils.setField(opvragenPersoonBerichtCommand, "antwoord",
                                new PersoonZoekCriteriaAntwoord());

                        return opvragenPersoonBerichtCommand;
                    }
                });
        MessageContext msgContext = Mockito.mock(MessageContext.class);

        Mockito.mock(SvnVersionService.class);

        WSSecurityEngineResult wsSecurityEngineResult = genereerWsSecurityEngineResultMock(msgContext);
        AuthenticatieMiddelDTO authenticatieMiddelDTO = new AuthenticatieMiddelDTO(1L, 2L);

        Mockito.when(msgContext.get("wss4j.signature.result")).thenReturn(wsSecurityEngineResult);
        Mockito.when(msgContext.get("BRP_BERICHT_ID")).thenReturn(2L);
        Mockito.when(
                authenticatieServiceMock.zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(any(BigInteger.class),
                        (byte[]) Matchers.any(), anyString())).thenReturn(authenticatieMiddelDTO);
        cmdArgument = ArgumentCaptor.forClass(OpvragenPersoonBerichtCommand.class);
        service = new OpvragenPersoonService();

        ReflectionTestUtils.setField(service, "svnVersionService", svnVersionServiceMock);
        ReflectionTestUtils.setField(service, "berichtUitvoerderService", berichtUitvoerderServiceMock);
        ReflectionTestUtils.setField(service, "applicationContext", applicationContextMock);
        ReflectionTestUtils.setField(service, "webServiceContext", webServiceContextMock);
        ReflectionTestUtils.setField(service, "autenticatieService", authenticatieServiceMock);
    }

    /**
     * Genereert een mock voor de {@link WSSecurityEngineResult} class inclusief benodigde mocks.
     *
     * @param msgContext de context die bevraagd wordt.
     * @return een mock van {@link WSSecurityEngineResult}
     */
    private WSSecurityEngineResult genereerWsSecurityEngineResultMock(final MessageContext msgContext) {
        WSSecurityEngineResult wsSecurityEngineResult = Mockito.mock(WSSecurityEngineResult.class);

        X509CertImpl cert = Mockito.mock(X509CertImpl.class);

        Mockito.when(cert.getSerialNumber()).thenReturn(new BigInteger("1315331010"));

        Principal principal = Mockito.mock(Principal.class);

        Mockito.when(principal.getName()).thenReturn("CN=serverkey");

        Mockito.when(cert.getSubjectDN()).thenReturn(principal);

        Mockito.when(webServiceContextMock.getMessageContext()).thenReturn(msgContext);
        Mockito.when(wsSecurityEngineResult.get("x509-certificate")).thenReturn(cert);
        return wsSecurityEngineResult;
    }

}
