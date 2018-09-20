/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContextImpl;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.levering.mutatielevering.jmx.MutatieLeveringInfoBean;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import support.AdministratieveHandelingTestBouwer;

@RunWith(MockitoJUnitRunner.class)
public class ZetBerichtOpQueueStapTest {
    private static final String TEST_REQUEST       = "test request";
    private static final String TEST_GEGEVENS_JSON =
        "{\"administratieveHandelingId\":123456,\"administratieveHandelingTijdstipRegistratie\":1388574000000,"
            + "\"datumAanvangMaterielePeriodeResultaat\":20140520,\"datumTijdAanvangFormelePeriodeResultaat\":1388574000000,"
            + "\"datumTijdEindeFormelePeriodeResultaat\":1388574000000,\"dienstId\":1674,\"geleverdePersoonsIds\":[1],"
            + "\"soortDienst\":\"MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING\",\"soortSynchronisatie\":{\"waarde\":\"VOLLEDIGBERICHT\"},"
            + "\"stelsel\":\"BRP\",\"stuurgegevens\":{\"ontvangendePartijId\":364,\"zendendePartijId\":363},\"toegangLeveringsautorisatieId\":123}";

    private static final String TEST_QUEUE_NAAM = "AFNEMER-1";

    @Mock
    private JmsTemplate afnemersJmsTemplate;

    @Mock
    private MutatieLeveringInfoBean mutatieLeveringInfoBean;

    @InjectMocks
    private final ZetBerichtOpQueueStap zetBerichtOpQueueStap = new ZetBerichtOpQueueStap();

    private Partij afnemer;

    @Mock
    private LeveringautorisatieStappenOnderwerp leveringautorisatieStappenOnderwerp;

    private LeveringsautorisatieVerwerkingContext context;

    private final LeveringautorisatieVerwerkingResultaat afnemerVerwerkingResultaat = new LeveringautorisatieVerwerkingResultaat();


    @Before
    public final void setup() {
        afnemer = TestPartijBuilder.maker().metNaam("Afnemernaam").metCode(123).maak();
        final Dienst dienst = TestDienstBuilder.maker().metSoortDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING).metId(1674).maak();
        final Dienstbundel dienstbundel = TestDienstbundelBuilder.maker().metDiensten(dienst).maak();

        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("TestLeveringsautorisatie").metPopulatiebeperking
            ("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(DatumAttribuut.gisteren()).metDienstbundels(dienstbundel).metStelsel(Stelsel.BRP).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).metDummyGeautoriseerde().metAfleverpunt("uri").metId(123).maak();

        final Leveringinformatie leveringinformatie = new Leveringinformatie(toegangLeveringsautorisatie, dienst);

        Mockito.when(leveringautorisatieStappenOnderwerp.getLeveringinformatie()).thenReturn(leveringinformatie);

        final AdministratieveHandelingModel administratieveHandeling =
            AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();


        final Map<Integer, Populatie> persoonPopulatie = new HashMap<>();
        persoonPopulatie.put(1, Populatie.BINNEN);
        persoonPopulatie.put(2, Populatie.BETREEDT);

        context = new LeveringsautorisatieVerwerkingContextImpl(administratieveHandeling, null, persoonPopulatie, null, null);
        context.setUitgaandePlatteTekstBerichten(Collections.singletonList(TEST_REQUEST));
        context.setLeveringBerichten(new ArrayList<SynchronisatieBericht>());
        final VolledigBericht volledigBericht =
            new VolledigBericht(new AdministratieveHandelingSynchronisatie(administratieveHandeling));
        volledigBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        volledigBericht.getStuurgegevens().setZendendePartij(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM);
        volledigBericht.getStuurgegevens().setOntvangendePartij(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_UTRECHT);
        context.getLeveringBerichten()
            .add(volledigBericht);

        final HistorieVanafPredikaat vanafPredikaat = HistorieVanafPredikaat.geldigOpEnNa(
            DatumTijdAttribuut.bouwDatumTijd(2014, 5, 20).naarDatum());
        final PersoonHisVolledigView view = new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), vanafPredikaat);
        context.setBijgehoudenPersoonViews(Collections.singletonList(view));
        volledigBericht.getAdministratieveHandeling().setBijgehoudenPersonen(Collections.singletonList(view));
    }

    @Test
    public final void testVoerStapUit() {
        final boolean resultaat = zetBerichtOpQueueStap.voerStapUit(leveringautorisatieStappenOnderwerp, context,
            afnemerVerwerkingResultaat);

        verify(afnemersJmsTemplate).send(eq(TEST_QUEUE_NAAM), any(MessageCreator.class));
        assertTrue(resultaat);
    }

    @Test
    public final void testVoerStapUitMessageCreator() throws JMSException {
        //Omdat we niet bij de message creator kunnen komen, eerst wat truuks...
        zetBerichtOpQueueStap.voerStapUit(leveringautorisatieStappenOnderwerp, context, afnemerVerwerkingResultaat);

        final ArgumentCaptor<MessageCreator> messageCaptor = ArgumentCaptor.forClass(MessageCreator.class);
        verify(afnemersJmsTemplate).send(anyString(), messageCaptor.capture());

        final Session sessionMock = mock(Session.class);
        final Message messageMock = mock(Message.class);
        when(sessionMock.createMessage()).thenReturn(messageMock);

        //Nu hebben we de messageCreator, roep hem nogmaals aan
        final MessageCreator messageCreator = messageCaptor.getValue();
        messageCreator.createMessage(sessionMock);

        //En verifier of het bericht in de message zit.
        verify(messageMock).setStringProperty("afnemerXmlBericht", TEST_REQUEST);

        verify(messageMock).setStringProperty("gegevens", TEST_GEGEVENS_JSON);

        verify(messageMock).setJMSType(Stelsel.BRP.toString());
    }
}
