/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;

import java.time.LocalDateTime;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.Protocollering;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringBericht;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.synchronisatie.dal.service.LeveringsaantekeningService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProtocolleringServiceTest {

    @Captor
    private ArgumentCaptor<Leveringsaantekening> leveringsaantekeningArg;

    @Mock
    private LeveringsaantekeningService leveringsaantekeningService;

    @InjectMocks
    private ProtocolleringService subject;

    @Test
    public void serviceNaam() {
        assertEquals("ProtocolleringService", subject.getServiceNaam());
    }

    @Test
    public void verzoekType() {
        assertEquals(ProtocolleringBericht.class, subject.getVerzoekType());
    }

    @Test
    public void verwerkTestNormaal() throws BerichtSyntaxException, OngeldigePersoonslijstException {
        final ProtocolleringAntwoordBericht antwoord = subject.verwerkBericht(createSampleBericht());
        assertEquals("", StatusType.OK, antwoord.getProtocolleringAntwoord().get(0).getStatus());
    }

    @Test
    public void verwerkTestMessageId() throws BerichtSyntaxException, OngeldigePersoonslijstException {
        final ProtocolleringAntwoordBericht antwoord = subject.verwerkBericht(createSampleBericht());
        assertThat("Er moet een message Id zijn gegenereerd", antwoord.getMessageId(), not(isEmptyString()));
    }

    @Test
    public void verwerkTestCorrelatieId() throws BerichtSyntaxException, OngeldigePersoonslijstException {
        final ProtocolleringAntwoordBericht antwoord = subject.verwerkBericht(createSampleBericht());
        assertEquals("Correlatie Id moet overeenkomen", "1234", antwoord.getCorrelationId());
    }

    @Test
    public void verwerkGeenToegangleveringsautorisatie() throws BerichtSyntaxException, OngeldigePersoonslijstException {
        final Protocollering protocollering = createSampleProtocollering();
        protocollering.setToegangLeveringsautorisatieId(null);
        final ProtocolleringAntwoordBericht antwoord = subject.verwerkBericht(createSampleBericht(protocollering));
        assertEquals("Statuscode moet correct zijn", StatusType.FOUT, antwoord.getProtocolleringAntwoord().get(0).getStatus());
        assertEquals(
                "Foutmelding moet gevuld zijn",
                "Toegang leveringsautorisatie kan niet gevonden worden",
                antwoord.getProtocolleringAntwoord().get(0).getFoutmelding());
    }

    @Test
    public void verwerkMeerdereToegangleveringsautorisatie() throws BerichtSyntaxException, OngeldigePersoonslijstException {
        final Protocollering protocollering = createSampleProtocollering();
        protocollering.setToegangLeveringsautorisatieId(null);
        protocollering.setToegangLeveringsautorisatieCount(2);
        final ProtocolleringAntwoordBericht antwoord = subject.verwerkBericht(createSampleBericht(protocollering));
        assertEquals("Statuscode moet correct zijn", StatusType.FOUT, antwoord.getProtocolleringAntwoord().get(0).getStatus());
        assertEquals(
                "Foutmelding moet gevuld zijn",
                "Er zijn meerdere Toegang leveringsautorisaties gevonden",
                antwoord.getProtocolleringAntwoord().get(0).getFoutmelding());
    }

    @Test
    public void verwerkGeenDienst() throws BerichtSyntaxException, OngeldigePersoonslijstException {
        final Protocollering protocollering = createSampleProtocollering();
        protocollering.setDienstId(null);
        final ProtocolleringAntwoordBericht antwoord = subject.verwerkBericht(createSampleBericht(protocollering));
        assertEquals("Statuscode moet correct zijn", StatusType.FOUT, antwoord.getProtocolleringAntwoord().get(0).getStatus());
        assertEquals("Foutmelding moet gevuld zijn", "Dienst kan niet gevonden worden", antwoord.getProtocolleringAntwoord().get(0).getFoutmelding());
    }

    @Test
    public void verwerkGeenPersoon() throws BerichtSyntaxException, OngeldigePersoonslijstException {
        final Protocollering protocollering = createSampleProtocollering();
        protocollering.setPersoonId(null);
        final ProtocolleringAntwoordBericht antwoord = subject.verwerkBericht(createSampleBericht(protocollering));
        assertEquals("Statuscode moet correct zijn", StatusType.FOUT, antwoord.getProtocolleringAntwoord().get(0).getStatus());
        assertEquals("Foutmelding moet gevuld zijn", "Persoon kan niet gevonden worden", antwoord.getProtocolleringAntwoord().get(0).getFoutmelding());
    }

    @Test
    public void verwerkOpgeschortPersoon() throws BerichtSyntaxException, OngeldigePersoonslijstException {
        final Protocollering protocollering = createSampleProtocollering();
        protocollering.setNadereBijhoudingsaardCode("F");
        final ProtocolleringAntwoordBericht antwoord = subject.verwerkBericht(createSampleBericht(protocollering));
        assertEquals("Statuscode moet correct zijn", StatusType.FOUT, antwoord.getProtocolleringAntwoord().get(0).getStatus());
        assertEquals("Foutmelding moet gevuld zijn", "Persoon is opgeschort met reden 'F'", antwoord.getProtocolleringAntwoord().get(0).getFoutmelding());
    }

    @Test
    public void verwerkMeerdereProtocolleringen() throws BerichtSyntaxException, OngeldigePersoonslijstException {
        final Protocollering protocollering = createSampleProtocollering();
        protocollering.setToegangLeveringsautorisatieId(null);

        final ProtocolleringBericht verzoek = new ProtocolleringBericht();
        verzoek.addProtocollering(createSampleProtocollering());
        verzoek.addProtocollering(protocollering);
        verzoek.setMessageId("1234");

        final ProtocolleringAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        assertEquals("Statuscode moet correct zijn", StatusType.OK, antwoord.getProtocolleringAntwoord().get(0).getStatus());
        assertEquals("Statuscode moet correct zijn", StatusType.FOUT, antwoord.getProtocolleringAntwoord().get(1).getStatus());
        assertEquals("Foutmelding moet leeg zijn", null, antwoord.getProtocolleringAntwoord().get(0).getFoutmelding());
        assertEquals(
                "Foutmelding moet gevuld zijn",
                "Toegang leveringsautorisatie kan niet gevonden worden",
                antwoord.getProtocolleringAntwoord().get(1).getFoutmelding());
    }

    @Test
    public void verwerkMoetLeveringaantekeningOpslaan() throws BerichtSyntaxException, OngeldigePersoonslijstException {
        final ProtocolleringBericht verzoek = createSampleBericht();
        subject.verwerkBericht(verzoek);
        Mockito.verify(leveringsaantekeningService).persisteerLeveringsaantekening(leveringsaantekeningArg.capture());
        assertEquals(
                "Protocollering en leveringsaantekening moeten dezelfde toegang leveringsautorisatie hebben",
                leveringsaantekeningArg.getValue().getToegangLeveringsautorisatie(),
                verzoek.getProtocollering().get(0).getToegangLeveringsautorisatieId());
    }

    @Test
    public void verwerkMoetLeveringaantekeningNietOpslaanIndienFoutief() throws BerichtSyntaxException, OngeldigePersoonslijstException {
        final Protocollering protocollering = createSampleProtocollering();
        protocollering.setDienstId(null);
        subject.verwerkBericht(createSampleBericht(protocollering));
        Mockito.verify(leveringsaantekeningService, never()).persisteerLeveringsaantekening(any());
    }

    private ProtocolleringBericht createSampleBericht() {
        final ProtocolleringBericht verzoek = new ProtocolleringBericht();
        verzoek.addProtocollering(createSampleProtocollering());
        verzoek.setMessageId("1234");
        return verzoek;
    }

    private ProtocolleringBericht createSampleBericht(final Protocollering protocollering) {
        final ProtocolleringBericht verzoek = new ProtocolleringBericht();
        verzoek.addProtocollering(protocollering);
        verzoek.setMessageId("1234");
        return verzoek;
    }

    private Protocollering createSampleProtocollering() {
        final Protocollering protocollering = new Protocollering(1);
        protocollering.setPersoonId(2L);
        protocollering.setNadereBijhoudingsaardCode("A");
        protocollering.setToegangLeveringsautorisatieId(3);
        protocollering.setToegangLeveringsautorisatieCount(1);
        protocollering.setDienstId(4);
        protocollering.setStartTijdstip(LocalDateTime.now());
        protocollering.setLaatsteActieTijdstip(LocalDateTime.now());
        return protocollering;
    }
}
