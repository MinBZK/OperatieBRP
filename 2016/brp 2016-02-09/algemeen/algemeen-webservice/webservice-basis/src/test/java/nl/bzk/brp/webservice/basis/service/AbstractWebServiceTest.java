/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.basis.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.webservice.business.service.AntwoordBerichtFactory;
import nl.bzk.brp.webservice.business.service.ArchiveringService;
import nl.bzk.brp.webservice.business.service.BerichtVerwerker;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtContext;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaatImpl;
import nl.bzk.brp.webservice.kern.interceptor.ArchiveringBericht;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.junit.After;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit Test class voor het testen van de methodes in de {@link AbstractWebService} class.
 */
public abstract class AbstractWebServiceTest<T extends BerichtBericht, C extends BerichtContext, Y extends BerichtVerwerkingsResultaat> {

    private static final PhaseInterceptorChain INTERCEPTOR_CHAIN = new PhaseInterceptorChain(new TreeSet<Phase>());
    public static final String CURRENT_MESSAGE = "CURRENT_MESSAGE";

    @Mock
    private Message                      message;

    private AbstractWebService<T, C, Y> webService;

    @Mock
    private ArchiveringService archiveringService;

    @Mock
    private AutorisatieService autorisatieService;

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    private AntwoordBerichtFactory antwoordBerichtFactory;

    private BerichtBericht bericht;

    /**
     * Retourneert een nieuwe (reeds geconfigureerde) instantie van de webservice die getest moet worden.
     *
     * @return een nieuwe (reeds geconfigureerde) instantie van de webservice die getest moet worden.
     */
    protected abstract AbstractWebService<T, C, Y> getNieuweWebServiceVoorTest();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void initMocks(final Class berichtClass, final SoortBericht soortbericht) {
        MockitoAnnotations.initMocks(this);

        try {
            final Constructor constructor = berichtClass.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            final Object[] constructorArgumenten;
            if (constructor.getParameterTypes().length == 0) {
                constructorArgumenten = new Object[]{ };
            } else {
                constructorArgumenten = new Object[]{ soortbericht };
            }
            final BerichtBericht bericht = (BerichtBericht) constructor.newInstance(constructorArgumenten);
            setBericht(bericht);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Fout bij initializeren bericht.", e);
        }
        initWebService(getNieuweWebServiceVoorTest());
        initBerichtArchiveringIds(true);
        initBericht(true, "3", soortbericht);
        initBerichtVerwerker(new ArrayList<Melding>(), BerichtVerwerkingsResultaatImpl.class, true);
    }

    /**
     * Initialiseert de webservice zelf door de normaliter geinjecteerde velden te zetten.
     *
     * @param ws de webservice die getest moet worden.
     */
    protected void initWebService(final AbstractWebService<T, C, Y> ws) {
        this.setWebService(ws);

        antwoordBerichtFactory = mock(AntwoordBerichtFactory.class);

        ReflectionTestUtils.setField(ws, "archiveringService", archiveringService);
        ReflectionTestUtils.setField(ws, "antwoordBerichtFactory", antwoordBerichtFactory);
        ReflectionTestUtils.setField(ws, "autorisatieService", autorisatieService);
        ReflectionTestUtils.setField(ws, "referentieDataRepository", referentieDataRepository);
    }

    @After
    public void teardown() {
        // Ruim de eventueel aangemaakte thread local variabele weer op.
        @SuppressWarnings("unchecked") final
        ThreadLocal<Message> messages =
            (ThreadLocal<Message>) ReflectionTestUtils.getField(INTERCEPTOR_CHAIN, CURRENT_MESSAGE);
        messages.set(null);
    }

    /**
     * Initialiseert de CXF Message mock en zet daarin de berichtarchiverings ids, afhankelijk van opgegeven parameter.
     *
     * @param heeftArchiveringIds bepaalt of de archivering ids <code>null</code> zijn of niet.
     */
    protected void initBerichtArchiveringIds(final boolean heeftArchiveringIds) {
        initBerichtArchiveringIds(heeftArchiveringIds, heeftArchiveringIds);
    }

    protected void initBerichtArchiveringIds(final boolean heeftArchiveringIdIn, final boolean heeftArchiveringIdUIt) {
        final ThreadLocal<Message> messages =
            (ThreadLocal<Message>) ReflectionTestUtils.getField(INTERCEPTOR_CHAIN, CURRENT_MESSAGE);
        messages.set(getMessage());
        final Exchange exchange = Mockito.mock(Exchange.class);
        when(getMessage().getExchange()).thenReturn(exchange);

        Long inId = null;
        Long uitId = null;
        if (heeftArchiveringIdIn) {
            inId = 1L;
        }
        if (heeftArchiveringIdUIt) {
            uitId = 2L;
        }
        when(exchange.get(ArchiveringBericht.BERICHT_ARCHIVERING_IN_ID)).thenReturn(inId);
        when(exchange.get(ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID)).thenReturn(uitId);
    }

    /**
     * Initialiseert de mock van het bericht door de partij id van het bericht te configureren op basis van parameter.
     *
     * @param heeftPartijId bepaalt of er een geldige partij id wordt geretourneerd of <code>null</code>.
     * @param partijId      Het te gebruiken partij id.
     * @param soortbericht  Het soort bericht.
     */
    private void initBericht(final boolean heeftPartijId, final String partijId, final SoortBericht soortbericht) {
        PartijAttribuut partij = null;
        if (heeftPartijId) {
            partij = StatischeObjecttypeBuilder.bouwPartij(Integer.parseInt(partijId), "partij-" + partijId);
        }
        final BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();
        stuurgegevens.setReferentienummer(new ReferentienummerAttribuut("REF001"));
        stuurgegevens.setZendendePartij(partij);
        stuurgegevens.setZendendePartijCode(partijId);
        bericht.setStuurgegevens(stuurgegevens);
        bericht.setSoort(new SoortBerichtAttribuut(soortbericht));
        final BerichtParametersGroepBericht berichtParametersGroepBericht = new BerichtParametersGroepBericht();
        berichtParametersGroepBericht.setLeveringsautorisatieID("12345");
        bericht.setParameters(berichtParametersGroepBericht);
        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(Integer.parseInt(partijId)))).thenReturn(partij.getWaarde());
    }

    /**
     * Initialiseert de mock van de berichtenverwerker service en zet de meldingen die in het te retourneren resultaat moeten zitten.
     *
     * @param meldingen de meldingen die door de berichtverwerker geretourneerd dienen te worden.
     * @param clazz     de klasse van het bericht resultaat.
     * @param resultaat of het resultaat goed of fout moet zijn.
     */
    protected <U extends BerichtVerwerkingsResultaatImpl> void initBerichtVerwerker(final List<Melding> meldingen,
        final Class<U> clazz, final boolean resultaat)
    {
        final U mock = Mockito.mock(clazz);
        when(mock.getAdministratieveHandeling()).thenReturn(Mockito.mock(AdministratieveHandelingModel.class));

        when(mock.getMeldingen()).thenReturn(meldingen);
        if (resultaat) {
            when(mock.bevatStoppendeFouten()).thenReturn(false);
        } else {
            when(mock.bevatStoppendeFouten()).thenReturn(true);
        }
        when(getBerichtVerwerker().verwerkBericht(any(BerichtBericht.class), any(AbstractBerichtContext.class))).thenReturn(
            mock);
    }

    protected abstract BerichtVerwerker getBerichtVerwerker();

    protected Message getMessage() {
        return message;
    }

    protected void setMessage(final Message message) {
        this.message = message;
    }

    protected AbstractWebService<T, C, Y> getWebService() {
        return webService;
    }

    protected void setWebService(final AbstractWebService<T, C, Y> webService) {
        this.webService = webService;
    }

    protected BerichtBericht getBericht() {
        return bericht;
    }

    protected void setBericht(final BerichtBericht bericht) {
        this.bericht = bericht;
    }

    public AntwoordBerichtFactory getAntwoordBerichtFactory() {
        return antwoordBerichtFactory;
    }
}
