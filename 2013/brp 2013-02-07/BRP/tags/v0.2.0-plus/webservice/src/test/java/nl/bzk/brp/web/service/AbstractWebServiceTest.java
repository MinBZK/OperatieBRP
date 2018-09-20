/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import nl.bzk.brp.business.dto.AbstractBRPBericht;
import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.business.dto.ResultaatCode;
import nl.bzk.brp.business.service.AuthenticatieService;
import nl.bzk.brp.business.service.BerichtVerwerker;
import nl.bzk.brp.dataaccess.repository.BerichtRepository;
import nl.bzk.brp.model.objecttype.operationeel.AuthenticatieMiddelModel;
import nl.bzk.brp.model.objecttype.operationeel.BerichtModel;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractAuthenticatieMiddelModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Richting;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortbericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.web.interceptor.ArchiveringBericht;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.WSSecurityEngineResult;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/** Unit Test class voor het testen van de methodes in de {@link AbstractWebService} class. */
public abstract class AbstractWebServiceTest<T extends BRPBericht, Y extends BerichtResultaat> {

    private static final PhaseInterceptorChain INTERCEPTOR_CHAIN = new PhaseInterceptorChain(new TreeSet<Phase>());

    @Mock
    private Message                            message;
    @Mock
    private WebServiceContext                  wsContext;
    @Mock
    private AuthenticatieService               authenticatieService;

    private AbstractWebService<T, Y>           webService;

    @Mock
    private BerichtRepository                  berichtRepository;

    private AbstractBRPBericht                 bericht;

    /**
     * Retourneert een nieuwe (reeds geconfigureerde) instantie van de webservice die getest moet worden.
     *
     * @return een nieuwe (reeds geconfigureerde) instantie van de webservice die getest moet worden.
     */
    protected abstract AbstractWebService<T, Y> getNieuweWebServiceVoorTest();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void initMocks(final Class berichtClass) {
        MockitoAnnotations.initMocks(this);

        setBericht((AbstractBRPBericht) Mockito.mock(berichtClass));
        initWebService(getNieuweWebServiceVoorTest());
        initBerichtArchiveringIds(true);
        initBericht(true);
        initBerichtVerwerker(new ArrayList<Melding>(), BerichtResultaat.class, true);
        initWSContext(true, true);
        initAuthenticatieService(1);
    }

    /**
     * Initialiseert de webservice zelf door de normaliter geinjecteerde velden te zetten.
     *
     * @param ws de webservice die getest moet worden.
     */
    protected void initWebService(final AbstractWebService<T, Y> ws) {
        this.setWebService(ws);

        ReflectionTestUtils.setField(ws, "authenticatieService", getAuthenticatieService());
        ReflectionTestUtils.setField(ws, "wsContext", getWsContext());
        ReflectionTestUtils.setField(ws, "berichtRepository", berichtRepository);
        Mockito.when(berichtRepository.findOne(Mockito.anyLong())).thenReturn(new BerichtModel(Richting.INGAAND, null));
    }

    /**
     * Initialiseert de CXF Message mock en zet daarin de berichtarchiverings ids, afhankelijk van opgegeven parameter.
     *
     * @param heeftArchiveringIds bepaalt of de archivering ids <code>null</code> zijn of niet.
     */
    protected void initBerichtArchiveringIds(final boolean heeftArchiveringIds) {
        ThreadLocal<Message> messages =
            (ThreadLocal<Message>) ReflectionTestUtils.getField(INTERCEPTOR_CHAIN, "CURRENT_MESSAGE");
        messages.set(getMessage());
        Exchange exchange = Mockito.mock(Exchange.class);
        Mockito.when(getMessage().getExchange()).thenReturn(exchange);

        Long inId = null;
        Long uitId = null;
        if (heeftArchiveringIds) {
            inId = 1L;
            uitId = 2L;
        }
        Mockito.when(exchange.get(ArchiveringBericht.BERICHT_ARCHIVERING_IN_ID)).thenReturn(inId);
        Mockito.when(exchange.get(ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID)).thenReturn(uitId);
    }

    /**
     * Initialiseert de mock van het bericht door de partij id van het bericht te configureren op basis van parameter.
     *
     * @param heeftPartijId bepaalt of er een geldige partij id wordt geretourneerd of <code>null</code>.
     */
    protected void initBericht(final boolean heeftPartijId) {
        Short partijId = null;
        if (heeftPartijId) {
            partijId = (short) 3;
        }
        BerichtStuurgegevens stuurgegevens = new BerichtStuurgegevens();
        stuurgegevens.setReferentienummer("REF001");
        Mockito.when(getBericht().getBerichtStuurgegevens()).thenReturn(stuurgegevens);
        Mockito.when(getBericht().getPartijId()).thenReturn(partijId);
        Mockito.when(getBericht().getSoortBericht()).thenReturn(Soortbericht.AFSTAMMING_INSCHRIJVINGAANGIFTEGEBOORTE_BIJHOUDING);
    }

    /**
     * Initialiseert de mock van de berichtenverwerker service en zet de meldingen die in het te retourneren resultaat
     * moeten zitten.
     *
     * @param meldingen de meldingen die door de berichtverwerker geretourneerd dienen te worden.
     * @param clazz de klasse van het bericht resultaat.
     * @param resultaat of het resultaat goed of fout moet zijn.
     */
    protected <U extends BerichtResultaat> void initBerichtVerwerker(final List<Melding> meldingen,
            final Class<U> clazz, final boolean resultaat)
    {
        U mock = Mockito.mock(clazz);
        Mockito.when(mock.getBerichtStuurgegevens()).thenReturn(new BerichtStuurgegevens());

        Mockito.when(mock.getMeldingen()).thenReturn(meldingen);
        if (resultaat) {
            Mockito.when(mock.getResultaatCode()).thenReturn(ResultaatCode.GOED);
        } else {
            Mockito.when(mock.getResultaatCode()).thenReturn(ResultaatCode.FOUT);
        }
        Mockito.when(
                getBerichtVerwerker()
                        .verwerkBericht(Matchers.any(BRPBericht.class), Matchers.any(BerichtContext.class)))
                .thenReturn(mock);
    }

    /**
     * Initialiseert de WSContext mock en conditioneert deze op basis van de opgegeven parameters om al dan niet een
     * signature en/of certificaat te retourneren.
     *
     * @param heeftSignatureResult bepaalt of er een geldige signature wordt geretourneerd of <code>null</code>.
     * @param heeftCertificaat bepaalt of er een geldig certificaat wordt geretourneerd of <code>null</code>.
     */
    protected void initWSContext(final boolean heeftSignatureResult, final boolean heeftCertificaat) {
        MessageContext messageContext = Mockito.mock(MessageContext.class);
        WSSecurityEngineResult wsSecurityEngineResult = null;
        X509Certificate certificaat = null;

        if (heeftSignatureResult) {
            wsSecurityEngineResult = Mockito.mock(WSSecurityEngineResult.class);
        }
        if (heeftCertificaat) {
            certificaat = Mockito.mock(X509Certificate.class);
        }

        Mockito.when(getWsContext().getMessageContext()).thenReturn(messageContext);
        Mockito.when(messageContext.get(WSS4JInInterceptor.SIGNATURE_RESULT)).thenReturn(wsSecurityEngineResult);
        if (heeftSignatureResult) {
            Mockito.when(wsSecurityEngineResult.get(WSSecurityEngineResult.TAG_X509_CERTIFICATE)).thenReturn(
                    certificaat);
        }
    }

    /**
     * Initialiseert de mock van de authenticatie service en zorgt er voor dat de mock het opgegeven aantal
     * authenticatiemiddelen retourneert.
     *
     * @param aantalAuthenticatieMiddelen het aantal authenticatiemiddelen dat geretourneerd dient te worden.
     */
    protected void initAuthenticatieService(final int aantalAuthenticatieMiddelen) {
        List<AuthenticatieMiddelModel> authenticatieMiddelen = new ArrayList<AuthenticatieMiddelModel>();
        for (int i = 0; i < aantalAuthenticatieMiddelen; i++) {
            AuthenticatieMiddelModel authenticatieMiddel =
                new AuthenticatieMiddelModel(new AbstractAuthenticatieMiddelModel() {
                });
            ReflectionTestUtils.setField(authenticatieMiddel, "partij", new Partij());
            ReflectionTestUtils.setField(authenticatieMiddel, "id", 2);
            authenticatieMiddelen.add(authenticatieMiddel);
        }

        Mockito.when(
                getAuthenticatieService().haalAuthenticatieMiddelenOp(Matchers.anyShort(),
                        (X509Certificate) Matchers.notNull())).thenReturn(authenticatieMiddelen);
    }

    protected abstract BerichtVerwerker getBerichtVerwerker();

    protected Message getMessage() {
        return message;
    }

    protected void setMessage(final Message message) {
        this.message = message;
    }

    protected WebServiceContext getWsContext() {
        return wsContext;
    }

    protected void setWsContext(final WebServiceContext wsContext) {
        this.wsContext = wsContext;
    }

    protected AuthenticatieService getAuthenticatieService() {
        return authenticatieService;
    }

    protected void setAuthenticatieService(final AuthenticatieService authenticatieService) {
        this.authenticatieService = authenticatieService;
    }

    protected AbstractWebService<T, Y> getWebService() {
        return webService;
    }

    protected void setWebService(final AbstractWebService<T, Y> webService) {
        this.webService = webService;
    }

    protected AbstractBRPBericht getBericht() {
        return bericht;
    }

    protected void setBericht(final AbstractBRPBericht bericht) {
        this.bericht = bericht;
    }
}
