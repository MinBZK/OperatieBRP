/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import nl.bzk.brp.business.stappen.Stap;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContextImpl;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContextImpl;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.ToegangLeveringsautorisatieService;
import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.mutatielevering.stappen.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling.TransactieStap;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.MutatieBericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import support.AdministratieveHandelingTestBouwer;

@Ignore
public class ZetBerichtOpQueueStapIntegratieTest extends AbstractIntegratieTest {

    private static final String TEST_REQUEST = "<element>waarde</element>";

    @Resource(name = "administratieveHandelingVerwerkingStappen")
    private List<AbstractAdministratieveHandelingVerwerkingStap> administratieveHandelingVerwerkingStappen;

    @Resource(name = "afnemerVerwerkingStappen")
    private List<AbstractAfnemerVerwerkingStap> afnemerVerwerkingStappen;

    @Inject
    private JmsTemplate afnemersJmsTemplate;

    @Inject
    private ToegangLeveringsautorisatieService toegangLeveringsautorisatieService;

    private final AdministratieveHandelingVerwerkingContext   context                                     =
        new AdministratieveHandelingVerwerkingContextImpl();
    private final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
        new AdministratieveHandelingVerwerkingResultaat();
    private final AdministratieveHandelingMutatie             administratieveHandelingMutatie             =
        new AdministratieveHandelingMutatie(1L);

    private Partij                                afnemer;
    private LeveringautorisatieStappenOnderwerp   leveringautorisatieStappenOnderwerp;
    private LeveringsautorisatieVerwerkingContext afnemerContext;
    private final LeveringautorisatieVerwerkingResultaat afnemerVerwerkingResultaat = new LeveringautorisatieVerwerkingResultaat();

    private TransactieStap transactieStap;
    private Stap           zetBerichtOpQueueStap;

    @Before
    public final void setup() {
        final AdministratieveHandelingModel administratieveHandeling = AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();
        afnemerContext = new LeveringsautorisatieVerwerkingContextImpl(administratieveHandeling, null, null, null, null);
        afnemerContext.setUitgaandePlatteTekstBerichten(Collections.singletonList(TEST_REQUEST));
        afnemerContext.setLeveringBerichten(new ArrayList<SynchronisatieBericht>());
        final MutatieBericht mutatieBericht =
            new MutatieBericht(new AdministratieveHandelingSynchronisatie(administratieveHandeling));
        mutatieBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        mutatieBericht.getStuurgegevens().setZendendePartij(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM);
        mutatieBericht.getStuurgegevens().setOntvangendePartij(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_UTRECHT);
        afnemerContext.getLeveringBerichten()
            .add(mutatieBericht);

        final PersoonHisVolledigImpl testPersoon = TestPersoonJohnnyJordaan.maak();
        ReflectionTestUtils.setField(testPersoon, "afnemerindicaties", Collections.emptySet());
        final PersoonHisVolledigView view = new PersoonHisVolledigView(testPersoon,
            HistorieVanafPredikaat.geldigOpEnNa(DatumAttribuut.gisteren()));
        afnemerContext.setBijgehoudenPersoonViews(Collections.singletonList(view));
        mutatieBericht.getAdministratieveHandeling().setBijgehoudenPersonen(Arrays.asList(view));

        afnemer = toegangLeveringsautorisatieService.geefGeldigeLeveringsautorisaties().get(0).getGeautoriseerde().getPartij();
        leveringautorisatieStappenOnderwerp = mock(LeveringautorisatieStappenOnderwerp.class);
        final Leveringinformatie leveringAutorisatie = mock(Leveringinformatie.class);

        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("TestLeveringsautorisatie").metPopulatiebeperking
            ("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(DatumAttribuut.gisteren()).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).metAfleverpunt("http://localhost/endpoint").maak();

        when(leveringautorisatieStappenOnderwerp.getLeveringinformatie()).thenReturn(leveringAutorisatie);
        when(leveringautorisatieStappenOnderwerp.getStelsel()).thenReturn(Stelsel.BRP);
        when(leveringAutorisatie.getToegangLeveringsautorisatie()).thenReturn(toegangLeveringsautorisatie);
        final Dienst dienst = mock(Dienst.class);
        when(leveringAutorisatie.getDienst()).thenReturn(dienst);
        when(dienst.getSoort())
            .thenReturn(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);

        transactieStap = (TransactieStap) administratieveHandelingVerwerkingStappen.get(0);

        zetBerichtOpQueueStap = afnemerVerwerkingStappen.get(afnemerVerwerkingStappen.size() - 1);
    }

    @Test
    @SuppressWarnings("unchecked")
    public final void testOfBerichtOpQueueKomt() throws JMSException {
        //Start transactie
        transactieStap
            .voerStapUit(administratieveHandelingMutatie, context, administratieveHandelingVerwerkingResultaat);

        zetBerichtOpQueueStap.voerStapUit(leveringautorisatieStappenOnderwerp, afnemerContext, afnemerVerwerkingResultaat);

        //Beeindig transactie
        transactieStap.voerNabewerkingStapUit(administratieveHandelingMutatie, context,
            administratieveHandelingVerwerkingResultaat);

        final Message berichtOpQueue = getBerichtVanQueue();

        assertEquals(TEST_REQUEST, berichtOpQueue.getStringProperty("afnemerXmlBericht"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public final void testOfBerichtEenRollBackKrijgt() {
        //Start transactie
        transactieStap
            .voerStapUit(administratieveHandelingMutatie, context, administratieveHandelingVerwerkingResultaat);

        zetBerichtOpQueueStap.voerStapUit(leveringautorisatieStappenOnderwerp, afnemerContext, afnemerVerwerkingResultaat);

        //Zet het resultaat op negatief, dit moet een rollback veroorzaken
        administratieveHandelingVerwerkingResultaat
            .voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001, "Test melding."));

        //Beeindig transactie
        transactieStap.voerNabewerkingStapUit(administratieveHandelingMutatie, context,
            administratieveHandelingVerwerkingResultaat);

        final Message berichtOpQueue = getBerichtVanQueue();

        assertNull(berichtOpQueue);
    }

    private Message getBerichtVanQueue() {
        return afnemersJmsTemplate.browse(afnemer.getQueueNaam(), new BrowserCallback<Message>() {
            @Override
            public final Message doInJms(final Session session, final QueueBrowser browser) throws JMSException {
                final Enumeration enumeration = browser.getEnumeration();
                if (enumeration.hasMoreElements()) {
                    return (Message) enumeration.nextElement();
                }
                return null;
            }
        });
    }

}
