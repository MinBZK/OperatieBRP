/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortVrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBerichtVrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVrijBericht;
import nl.bzk.brp.domain.berichtmodel.VrijBericht;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtParameters;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtVerwerkBericht;
import nl.bzk.brp.domain.internbericht.vrijbericht.VrijBerichtGegevens;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.dalapi.BeheerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.Assert;

/**
 * Unit test voor {@link VrijBerichtServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class VrijBerichtServiceImplTest {

    @InjectMocks
    private VrijBerichtServiceImpl vrijBerichtService;

    @Mock
    private PartijService partijService;
    @Mock
    private VrijBerichtAutorisatieService autorisatieService;
    @Mock
    private PlaatsVerwerkVrijBerichtService plaatsVerwerkVrijBerichtService;
    @Mock
    private MaakVerwerkVrijBerichtService maakVerwerkVrijBerichtService;
    @Mock
    private VrijBerichtBerichtFactory vrijBerichtBerichtFactory;
    @Mock
    private VrijBerichtInhoudControleService vrijBerichtInhoudControleService;
    @Mock
    private BeheerRepository beheerRepository;

    @Captor
    private ArgumentCaptor<VrijBerichtGegevens> plaatsVrijBerichtCaptor;
    @Captor
    private ArgumentCaptor<nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht> beheerRepositoryCaptor;

    private Partij ontvangendePartij;
    private Partij zendendePartij;
    private VrijBerichtVerzoek verzoek;

    @Before
    public void before() {
        ontvangendePartij = TestPartijBuilder.maakBuilder().metId(1).metCode("000123").metDatumOvergangNaarBrp(DatumUtil.datumRondVandaag(1)).build();
        ontvangendePartij.setAfleverpuntVrijBericht("afleverpunt");
        zendendePartij = TestPartijBuilder.maakBuilder().metId(2).metCode("000456").metDatumOvergangNaarBrp(DatumUtil.datumRondVandaag(1)).build();
        zendendePartij.setAfleverpuntVrijBericht("afleverpunt");
        when(partijService.vindPartijOpCode("000123")).thenReturn(ontvangendePartij);
        when(partijService.vindPartijOpCode("000456")).thenReturn(zendendePartij);

        when(partijService.geefBrpPartij()).thenReturn(zendendePartij);

        verzoek = new VrijBerichtVerzoek();
        verzoek.getParameters().setOntvangerVrijBericht("000123");
        verzoek.getParameters().setZenderVrijBericht("000456");
        verzoek.getStuurgegevens().setReferentieNummer("123");
    }

    @Test
    public void happyFlow() throws Exception {
        final VrijBericht vrijBericht = new VrijBericht("vrij bericht", null);
        //@formatter:off
        final VrijBerichtVerwerkBericht verwerkBericht = new VrijBerichtVerwerkBericht(
                BasisBerichtGegevens.builder()
                                        .metStuurgegevens()
                                            .metReferentienummer("refNr")
                                            .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                                        .eindeStuurgegevens().build(),
                new BerichtVrijBericht(vrijBericht),
                new VrijBerichtParameters(ontvangendePartij, zendendePartij)
                );
        when(vrijBerichtBerichtFactory.maakVerwerkBericht(any(), any(), any())).thenReturn(verwerkBericht);
        when(maakVerwerkVrijBerichtService.maakVerwerkVrijBericht(verwerkBericht)).thenReturn("xml");

        final VrijBerichtResultaat resultaat = vrijBerichtService.verwerkVerzoek(verzoek);

        assertThat(resultaat.getMeldingen(), is(Collections.emptyList()));
        final InOrder
                inOrder =
                inOrder(partijService, autorisatieService, vrijBerichtInhoudControleService, vrijBerichtBerichtFactory, maakVerwerkVrijBerichtService,
                        plaatsVerwerkVrijBerichtService);
        inOrder.verify(autorisatieService).valideerAutorisatieBrpZender(verzoek);
        inOrder.verify(autorisatieService).valideerAutorisatieBrpOntvanger(verzoek);
        inOrder.verify(vrijBerichtInhoudControleService).controleerInhoud(verzoek);
        inOrder.verify(vrijBerichtBerichtFactory).maakVerwerkBericht(resultaat, ontvangendePartij, zendendePartij);
        inOrder.verify(maakVerwerkVrijBerichtService).maakVerwerkVrijBericht(verwerkBericht);
        inOrder.verify(plaatsVerwerkVrijBerichtService).plaatsVrijBericht(plaatsVrijBerichtCaptor.capture());
        final VrijBerichtGegevens berichtGegevens = plaatsVrijBerichtCaptor.getValue();
        assertThat(berichtGegevens.getBrpEndpointURI(), is("afleverpunt"));
        assertThat(berichtGegevens.getPartij(), is(ontvangendePartij));
        assertThat(berichtGegevens.getStelsel(), is(Stelsel.BRP));
        assertThat(berichtGegevens.getArchiveringOpdracht().getData(), is("xml"));
    }


    @Test
    public void happyFlow_OntvangendePartijIsBrpVoorziening() throws Exception {
        final Partij brpVoorzieningPartij = TestPartijBuilder.maakBuilder().metCode(Partij.PARTIJ_CODE_BRP).build();
        final SoortVrijBericht soortVrijBericht = new SoortVrijBericht("dummy");
        Mockito.when(beheerRepository.haalSoortVrijBerichtOp(any())).thenReturn(soortVrijBericht);
        when(partijService.vindPartijOpCode(Partij.PARTIJ_CODE_BRP)).thenReturn(brpVoorzieningPartij);
        ontvangendePartij = brpVoorzieningPartij;
        verzoek.getParameters().setOntvangerVrijBericht(String.valueOf(Partij.PARTIJ_CODE_BRP));
        final VrijBericht vrijBericht = new VrijBericht("vrij bericht", soortVrijBericht);

        //@formatter:off
        final VrijBerichtVerwerkBericht verwerkBericht = new VrijBerichtVerwerkBericht(
            BasisBerichtGegevens.builder()
                            .metStuurgegevens()
                                .metReferentienummer("refNr")
                                .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                            .eindeStuurgegevens().build(),
            new BerichtVrijBericht(vrijBericht),
            new VrijBerichtParameters(zendendePartij, ontvangendePartij)
        );
        when(vrijBerichtBerichtFactory.maakVerwerkBericht(any(), any(), any())).thenReturn(verwerkBericht);
        when(maakVerwerkVrijBerichtService.maakVerwerkVrijBericht(verwerkBericht)).thenReturn("xml");

        final VrijBerichtResultaat resultaat = vrijBerichtService.verwerkVerzoek(verzoek);

        assertThat(resultaat.getMeldingen(), is(Collections.emptyList()));
        final InOrder
                inOrder =
                inOrder(partijService, autorisatieService, vrijBerichtInhoudControleService, vrijBerichtBerichtFactory, maakVerwerkVrijBerichtService,
                        beheerRepository);
        inOrder.verify(autorisatieService).valideerAutorisatieBrpZender(verzoek);
        inOrder.verify(vrijBerichtInhoudControleService).controleerInhoud(verzoek);
        inOrder.verify(vrijBerichtBerichtFactory).maakVerwerkBericht(resultaat, ontvangendePartij, zendendePartij);
        inOrder.verify(maakVerwerkVrijBerichtService).maakVerwerkVrijBericht(verwerkBericht);
        inOrder.verify(beheerRepository).opslaanNieuwVrijBericht(beheerRepositoryCaptor.capture());
        final nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht vrijBerichtEntity = beheerRepositoryCaptor.getValue();
        assertThat(vrijBerichtEntity.getData(), is("vrij bericht"));
        assertThat(vrijBerichtEntity.getSoortVrijBericht(), is(soortVrijBericht));
        assertThat(vrijBerichtEntity.getSoortBerichtVrijBericht(), is(SoortBerichtVrijBericht.VERWERK_VRIJ_BERICHT));
        assertThat(vrijBerichtEntity.getIndicatieGelezen(), is(false));
        Assert.notNull(vrijBerichtEntity.getTijdstipRegistratie());
        assertThat(vrijBerichtEntity.getVrijBerichtPartijen().size(), is(1));
        assertThat(vrijBerichtEntity.getVrijBerichtPartijen().get(0).getPartij(), is(zendendePartij));
    }

    @Test
    public void testStelseISCAutorisatieGeenDatumOvergang() throws Exception {
        ontvangendePartij.setDatumOvergangNaarBrp(null);
        zendendePartij.setDatumOvergangNaarBrp(null);

        final VrijBericht vrijBericht = new VrijBericht("vrij bericht", new SoortVrijBericht("dummy"));

        final VrijBerichtVerwerkBericht verwerkBericht = new VrijBerichtVerwerkBericht(
                BasisBerichtGegevens.builder()
                                        .metStuurgegevens()
                                            .metReferentienummer("refNr")
                                            .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                                        .eindeStuurgegevens().build(),
                new BerichtVrijBericht(vrijBericht),
                new VrijBerichtParameters(zendendePartij, ontvangendePartij)
        );
        when(vrijBerichtBerichtFactory.maakVerwerkBericht(any(), any(), any())).thenReturn(verwerkBericht);
        when(maakVerwerkVrijBerichtService.maakVerwerkVrijBericht(verwerkBericht)).thenReturn("xml");


        vrijBerichtService.verwerkVerzoek(verzoek);

        Mockito.verify(autorisatieService, Mockito.times(1)).valideerAutorisatie(Mockito.any());
        Mockito.verify(autorisatieService, Mockito.never()).valideerAutorisatieBrpOntvanger(Mockito.any());
        Mockito.verify(autorisatieService, Mockito.never()).valideerAutorisatieBrpZender(Mockito.any());
    }

    @Test
    public void testStelseISCAutorisatieGeenDatumToekomstig() throws Exception {
        ontvangendePartij.setDatumOvergangNaarBrp(DatumUtil.datumRondVandaag(-1));
        zendendePartij.setDatumOvergangNaarBrp(DatumUtil.datumRondVandaag(-1));

        final VrijBericht vrijBericht = new VrijBericht("vrij bericht", new SoortVrijBericht("dummy"));

        //@formatter:off
        final VrijBerichtVerwerkBericht verwerkBericht = new VrijBerichtVerwerkBericht(
                BasisBerichtGegevens.builder()
                                        .metStuurgegevens()
                                            .metReferentienummer("refNr")
                                            .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                                        .eindeStuurgegevens().build(),
                new BerichtVrijBericht(vrijBericht),
                new VrijBerichtParameters(zendendePartij, ontvangendePartij)
        );
        when(vrijBerichtBerichtFactory.maakVerwerkBericht(any(), any(), any())).thenReturn(verwerkBericht);
        when(maakVerwerkVrijBerichtService.maakVerwerkVrijBericht(verwerkBericht)).thenReturn("xml");


        vrijBerichtService.verwerkVerzoek(verzoek);

        Mockito.verify(autorisatieService, Mockito.times(1)).valideerAutorisatie(Mockito.any());
        Mockito.verify(autorisatieService, Mockito.never()).valideerAutorisatieBrpOntvanger(Mockito.any());
        Mockito.verify(autorisatieService, Mockito.never()).valideerAutorisatieBrpZender(Mockito.any());
    }

    @Test
    public void testStelseBRPAutorisatie() throws Exception {
        final VrijBericht vrijBericht = new VrijBericht("vrij bericht", null);
        //@formatter:off
        final VrijBerichtVerwerkBericht verwerkBericht = new VrijBerichtVerwerkBericht(
                BasisBerichtGegevens.builder()
                                        .metStuurgegevens()
                                            .metReferentienummer("refNr")
                                            .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                                        .eindeStuurgegevens().build(),
                new BerichtVrijBericht(vrijBericht),
                new VrijBerichtParameters(zendendePartij, ontvangendePartij)
        );
        //@formatter:on
        when(vrijBerichtBerichtFactory.maakVerwerkBericht(any(), any(), any())).thenReturn(verwerkBericht);
        when(maakVerwerkVrijBerichtService.maakVerwerkVrijBericht(verwerkBericht)).thenReturn("xml");

        verzoek.getStuurgegevens().setZendendSysteem("NIET_ISC");
        ontvangendePartij.setDatumOvergangNaarBrp(20011010);

        vrijBerichtService.verwerkVerzoek(verzoek);

        Mockito.verify(autorisatieService, Mockito.times(1)).valideerAutorisatie(Mockito.any());
        Mockito.verify(autorisatieService, Mockito.times(1)).valideerAutorisatieBrpOntvanger(Mockito.any());
        Mockito.verify(autorisatieService, Mockito.times(1)).valideerAutorisatieBrpZender(Mockito.any());
    }

    @Test
    public void meldingR2405BijAutorisatieException() throws Exception {
        doThrow(new AutorisatieException(new Melding(Regel.R2464))).when(autorisatieService).valideerAutorisatieBrpZender(verzoek);

        final VrijBerichtResultaat resultaat = vrijBerichtService.verwerkVerzoek(verzoek);

        assertThat(resultaat.getMeldingen().get(0).getRegel(), is(Regel.R2343));
    }

    @Test
    public void stapMeldingException() throws Exception {
        doThrow(new StapMeldingException(new Melding(Regel.R2472))).when(vrijBerichtInhoudControleService).controleerInhoud(any());

        final VrijBerichtResultaat resultaat = vrijBerichtService.verwerkVerzoek(verzoek);

        assertThat(resultaat.getMeldingen().get(0).getRegel(), is(Regel.R2472));
    }

    @Test
    public void stapException() throws Exception {
        doThrow(new StapException("stapException")).when(maakVerwerkVrijBerichtService).maakVerwerkVrijBericht(any());

        final VrijBerichtResultaat resultaat = vrijBerichtService.verwerkVerzoek(verzoek);

        assertThat(resultaat.getMeldingen().get(0).getRegel(), is(Regel.ALG0001));
        assertThat(resultaat.getMeldingen().get(0).getMeldingTekst(), is("Algemene fout opgetreden."));
    }

    @Test(expected = RuntimeException.class)
    public void runtimeException() throws Exception {
        doThrow(new RuntimeException("krak")).when(maakVerwerkVrijBerichtService).maakVerwerkVrijBericht(any());

        final VrijBerichtResultaat resultaat = vrijBerichtService.verwerkVerzoek(verzoek);

        assertThat(resultaat.getMeldingen().get(0).getRegel(), is(Regel.ALG0001));
        assertThat(resultaat.getMeldingen().get(0).getMeldingTekst(), is("Algemene fout opgetreden."));
    }
}
