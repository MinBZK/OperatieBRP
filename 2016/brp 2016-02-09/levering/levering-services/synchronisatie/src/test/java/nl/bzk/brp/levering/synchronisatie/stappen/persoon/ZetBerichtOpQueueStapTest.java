/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.persoon;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.synchronisatie.stappen.AbstractStappenTest;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.util.ReflectionTestUtils;

public class ZetBerichtOpQueueStapTest extends AbstractStappenTest {

    private static final String TEST_REQUEST        = "test request";
    private static final String AFNEMER_XML_BERICHT = "afnemerXmlBericht";
    private static final String GEGEVENS            = "gegevens";

    private static final String TEST_GEGEVENS_JSON
        = "{\"datumAanvangMaterielePeriodeResultaat\":20140520,\"dienstId\":1,\"geleverdePersoonsIds\":[123],\"soortDienst\":\"SYNCHRONISATIE_PERSOON\","
        + "\"soortSynchronisatie\":{\"waarde\":\"VOLLEDIGBERICHT\"},\"stelsel\":\"BRP\",\"stuurgegevens\":{\"ontvangendePartijId\":364,\"zendendePartijId\":363},\"toegangLeveringsautorisatieId\":1234}";

    @InjectMocks
    private ZetBerichtOpQueueStap zetBerichtOpQueueStap = new ZetBerichtOpQueueStap();

    @Mock
    private JmsTemplate afnemersJmsTemplate;

    private Leveringinformatie leveringAutorisatie;

    @Before
    public final void setup() {

        final Partij testPartij = TestPartijBuilder.maker().metCode(23).metNaam("PartijX").maak();

        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.SYNCHRONISATIE_PERSOON);
        maakBericht(987654321, la, 321, "ZendendeSysteem");
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().
            metGeautoriseerde(new PartijRol(testPartij, Rol.AFNEMER, null, null)).
            metLeveringsautorisatie(la).metId(1234).metAfleverpunt("http://localhost").maak();
        final Leveringinformatie leveringinformatie = new Leveringinformatie(tla, la.geefDiensten().iterator().next());
        getBerichtContext().setLeveringinformatie(leveringinformatie);

        final VolledigBericht volledigBericht = mock(VolledigBericht.class);
        final AdministratieveHandelingSynchronisatie administratieveHandeling = getTestAdministratieveHandeling();
        when(volledigBericht.getAdministratieveHandeling()).thenReturn(administratieveHandeling);
        when(volledigBericht.getStuurgegevens()).thenReturn(new BerichtStuurgegevensGroepBericht());
        volledigBericht.getStuurgegevens().setZendendePartij(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM);
        volledigBericht.getStuurgegevens().setOntvangendePartij(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_UTRECHT);
        getBerichtContext().setVolledigBericht(volledigBericht);
        getBerichtContext().setXmlBericht(TEST_REQUEST);
    }

    @Test
    public final void testVoerStapUit() throws JMSException {
        final boolean stapResultaat = zetBerichtOpQueueStap.voerStapUit(getOnderwerp(), getBerichtContext(),
            getResultaat());

        assertTrue(stapResultaat);

        final ArgumentCaptor<MessageCreator> messageCaptor = ArgumentCaptor.forClass(MessageCreator.class);
        verify(afnemersJmsTemplate).send(anyString(), messageCaptor.capture());

        final Session sessionMock = mock(Session.class);
        final Message messageMock = mock(Message.class);
        when(sessionMock.createMessage()).thenReturn(messageMock);

        //Nu hebben we de messageCreator, roep hem nogmaals aan
        final MessageCreator messageCreator = messageCaptor.getValue();
        messageCreator.createMessage(sessionMock);

        //En verifier of het bericht in de message zit.
        verify(messageMock).setStringProperty(AFNEMER_XML_BERICHT, TEST_REQUEST);
        verify(messageMock).setStringProperty(GEGEVENS, TEST_GEGEVENS_JSON);
        verify(messageMock).setJMSType(Stelsel.BRP.toString());
    }

    @Test(expected = Exception.class)
    public final void testVoerStapUitMetExceptie() {
        doThrow(Exception.class).when(afnemersJmsTemplate).send(anyString(), any(MessageCreator.class));

        zetBerichtOpQueueStap.voerStapUit(getOnderwerp(), getBerichtContext(),
            getResultaat());
    }

    private AdministratieveHandelingSynchronisatie getTestAdministratieveHandeling() {
        final AdministratieveHandelingModel administratieveHandelingModel = maakAdministratieveHandelingModel();

        final AdministratieveHandelingSynchronisatie administratieveHandelingSynchronisatie =
            new AdministratieveHandelingSynchronisatie(administratieveHandelingModel);

        final PersoonHisVolledigImpl johnny = TestPersoonJohnnyJordaan.maak();
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(johnny, HistorieVanafPredikaat.geldigOpEnNa(
            DatumTijdAttribuut.bouwDatumTijd(2014, 5, 20).naarDatum()));
        ReflectionTestUtils.setField(johnny, "iD", 123);
        administratieveHandelingSynchronisatie.setBijgehoudenPersonen(Collections.singletonList(persoonHisVolledigView));

        return administratieveHandelingSynchronisatie;
    }
}
