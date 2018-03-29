/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.geefmedebewoners;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import com.google.common.collect.Iterables;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekbereik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.request.DatumService;
import nl.bzk.brp.service.bevraging.algemeen.PeilmomentValidatieService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import nl.bzk.brp.service.bevraging.algemeen.BevragingSelecteerPersoonService;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoon;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonBerichtFactory;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonGeneriekVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoonopadres.ZoekPersoonOpAdresVerzoek;
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

/**
 * Unit test voor {@link GeefMedebewonersMaakBerichtServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class GeefMedebewonersMaakBerichtServiceImplTest {

    private static final int MATERIEEL_PEIL_MOMENT = 20100101;

    @Mock
    private ZoekPersoon.OphalenPersoonService<ZoekPersoonOpAdresVerzoek> geefMedebewonersOphalenPersoonService;
    @Mock
    private ZoekPersoonBerichtFactory berichtFactory;
    @Mock
    private RelatiefilterService relatiefilterService;
    @Mock
    private PartijService partijService;
    @Mock
    private LeveringsautorisatieValidatieService leveringsautorisatieService;
    @Mock
    private GeefMedebewonersBepaalBAGSleutelService bepaalBAGSleutelService;
    @Mock
    private BevragingSelecteerPersoonService bevragingSelecteerPersoonService;
    @Mock
    private DatumService datumService;
    @Mock
    private PeilmomentValidatieService peilmomentValidatieService;
    @InjectMocks
    private GeefMedebewonersMaakBerichtServiceImpl service = new GeefMedebewonersMaakBerichtServiceImpl(leveringsautorisatieService, partijService);

    @Captor
    private ArgumentCaptor<ZoekPersoonOpAdresVerzoek> zoekPersoonOpAdresVerzoekArgumentCaptor;
    private GeefMedebewonersVerzoek verzoek;
    private Autorisatiebundel autorisatiebundel;
    private List<Persoonslijst> persoonslijstList;

    @Before
    public void voorTest() throws Exception {
        autorisatiebundel = new Autorisatiebundel(null, null);
        persoonslijstList = Collections.singletonList(new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L));

        verzoek = new GeefMedebewonersVerzoek();
        verzoek.setSoortDienst(SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON);
        verzoek.getStuurgegevens().setZendendePartijCode(Integer.toString(1));
        verzoek.getParameters().setDienstIdentificatie(Integer.toString(1));
        verzoek.getParameters().setLeveringsAutorisatieId(Integer.toString(1));
        verzoek.getParameters().setRolNaam(Rol.AFNEMER.getNaam());
        verzoek.getParameters().setPeilmomentMaterieel("2010-01-01");

        when(partijService.geefBrpPartij()).thenReturn(TestPartijBuilder.maakBuilder().metCode("000001").build());
        when(leveringsautorisatieService.controleerAutorisatie(any())).thenReturn(autorisatiebundel);
        when(geefMedebewonersOphalenPersoonService.voerStapUit(any(), any())).thenReturn(persoonslijstList);
        when(datumService.parseDate("2010-01-01")).thenReturn(LocalDate.of(2010, 1, 1));
    }

    @Test
    public void testGeefMedebewonersZelfdeBAG() throws Exception {
        verzoek.getIdentificatiecriteria().setIdentificatiecodeNummeraanduiding("bagsleutel");
        final List<Persoonslijst> persoonslijstListOpPeilmoment = persoonslijstList.stream().map(pl ->
                pl.beeldVan().materieelPeilmoment(MATERIEEL_PEIL_MOMENT)).collect(Collectors.toList());
        when(geefMedebewonersOphalenPersoonService.voerStapUit(any(), any())).thenReturn(persoonslijstList);
        when(relatiefilterService.filterRelaties(any(), eq(20100101))).thenReturn(persoonslijstListOpPeilmoment);

        service.voerStappenUit(verzoek);

        final InOrder inOrder = inOrder(peilmomentValidatieService, geefMedebewonersOphalenPersoonService, relatiefilterService, berichtFactory);
        inOrder.verify(peilmomentValidatieService).valideerMaterieel("2010-01-01");
        inOrder.verify(geefMedebewonersOphalenPersoonService).voerStapUit(zoekPersoonOpAdresVerzoekArgumentCaptor.capture(), eq(autorisatiebundel));
        inOrder.verify(relatiefilterService).filterRelaties(refEq(persoonslijstList), eq(20100101));
        inOrder.verify(berichtFactory).maakZoekPersoonBericht(persoonslijstListOpPeilmoment, autorisatiebundel, verzoek, MATERIEEL_PEIL_MOMENT);
        valideerZoekPersoonVerzoekBAG();
        assertThat(zoekPersoonOpAdresVerzoekArgumentCaptor.getValue().getZoekCriteriaPersoon(),
                not(contains(maakZoekCriteria("Persoon.Adres.Woonplaatsnaam", "Amsterdam"))));
    }

    @Test
    public void testGeefMedebewonersVanPersoon() throws StapException {
        verzoek.getIdentificatiecriteria().setBurgerservicenummer("132465798");
        final List<Persoonslijst> persoonslijstList = Collections.singletonList(new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L));
        when(bevragingSelecteerPersoonService.selecteerPersoon(any(), any(), any(), any(), any()))
                .thenReturn(new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L));
        final List<Persoonslijst> persoonslijstListOpPeilmoment = persoonslijstList.stream().map(pl ->
                pl.beeldVan().materieelPeilmoment((MATERIEEL_PEIL_MOMENT))).collect(Collectors.toList());
        when(geefMedebewonersOphalenPersoonService.voerStapUit(any(), any())).thenReturn(persoonslijstList);
        when(relatiefilterService.filterRelaties(any(), eq(20100101))).thenReturn(persoonslijstListOpPeilmoment);
        when(bepaalBAGSleutelService.bepaalBAGSleutel(any(), eq(MATERIEEL_PEIL_MOMENT))).thenReturn("bagsleutel");

        service.voerStappenUit(verzoek);

        final InOrder inOrder = inOrder(peilmomentValidatieService, bevragingSelecteerPersoonService, bepaalBAGSleutelService,
                geefMedebewonersOphalenPersoonService, relatiefilterService,
                berichtFactory);
        inOrder.verify(peilmomentValidatieService).valideerMaterieel("2010-01-01");
        inOrder.verify(bevragingSelecteerPersoonService).selecteerPersoon("132465798", null, null, "1", autorisatiebundel);
        inOrder.verify(bepaalBAGSleutelService).bepaalBAGSleutel(any(), eq(MATERIEEL_PEIL_MOMENT));
        inOrder.verify(geefMedebewonersOphalenPersoonService).voerStapUit(zoekPersoonOpAdresVerzoekArgumentCaptor.capture(), eq(autorisatiebundel));
        inOrder.verify(relatiefilterService).filterRelaties(refEq(persoonslijstList), eq(20100101));
        inOrder.verify(berichtFactory).maakZoekPersoonBericht(persoonslijstListOpPeilmoment, autorisatiebundel, verzoek, MATERIEEL_PEIL_MOMENT);
        valideerZoekPersoonVerzoekBAG();
    }

    @Test
    public void testGeefMedebewonersZelfdeAdres() throws StapException {
        verzoek.getIdentificatiecriteria().setPostcode("1111AB");
        verzoek.getIdentificatiecriteria().setHuisnummer("1");
        verzoek.getIdentificatiecriteria().setHuisletter("A");
        verzoek.getIdentificatiecriteria().setWoonplaatsnaam("Amsterdam");
        verzoek.getIdentificatiecriteria().setAfgekorteNaamOpenbareRuimte("123");
        verzoek.getIdentificatiecriteria().setGemeenteCode("AMS");
        verzoek.getIdentificatiecriteria().setHuisnummertoevoeging("-");
        verzoek.getIdentificatiecriteria().setLocatieTenOpzichteVanAdres("ergens aan de overkant");
        final List<Persoonslijst> persoonslijstList = Collections.singletonList(new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L));
        final List<Persoonslijst> persoonslijstListOpPeilmoment = persoonslijstList.stream().map(pl -> pl.beeldVan()
                .materieelPeilmoment(MATERIEEL_PEIL_MOMENT)).collect(Collectors.toList());
        when(geefMedebewonersOphalenPersoonService.voerStapUit(any(), any())).thenReturn(persoonslijstList);
        when(relatiefilterService.filterRelaties(any(), eq(20100101))).thenReturn(persoonslijstListOpPeilmoment);

        service.voerStappenUit(verzoek);

        final InOrder inOrder = inOrder(peilmomentValidatieService, geefMedebewonersOphalenPersoonService, relatiefilterService,
                berichtFactory);
        inOrder.verify(peilmomentValidatieService).valideerMaterieel("2010-01-01");
        inOrder.verify(geefMedebewonersOphalenPersoonService).voerStapUit(zoekPersoonOpAdresVerzoekArgumentCaptor.capture(), eq(autorisatiebundel));
        inOrder.verify(relatiefilterService).filterRelaties(refEq(persoonslijstList), eq(20100101));
        inOrder.verify(berichtFactory).maakZoekPersoonBericht(persoonslijstListOpPeilmoment, autorisatiebundel, verzoek, MATERIEEL_PEIL_MOMENT);
        final ZoekPersoonOpAdresVerzoek zoekPersoonOpAdresVerzoek = zoekPersoonOpAdresVerzoekArgumentCaptor.getValue();
        final ZoekPersoonGeneriekVerzoek.ZoekBereikParameters zoekBereikParameters = zoekPersoonOpAdresVerzoek.getParameters().getZoekBereikParameters();
        assertThat(zoekBereikParameters.getZoekBereik(), is(Zoekbereik.PEILMOMENT));
        assertThat(zoekBereikParameters.getPeilmomentMaterieel(), is("2010-01-01"));
        final Set<ZoekPersoonGeneriekVerzoek.ZoekCriteria> zoekCriteriaPersoon = zoekPersoonOpAdresVerzoek.getZoekCriteriaPersoon();
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteriaPostcode = maakZoekCriteria("Persoon.Adres.Postcode", "1111AB");
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteriaHuisnummer = maakZoekCriteria("Persoon.Adres.Huisnummer", "1");
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteriaHuisletter = maakZoekCriteria("Persoon.Adres.Huisletter", "A");
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteriaWoonplaats = maakZoekCriteria("Persoon.Adres.Woonplaatsnaam", "Amsterdam");
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteriaAnor = maakZoekCriteria("Persoon.Adres.AfgekorteNaamOpenbareRuimte", "123");
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteriaGemeentecode = maakZoekCriteria("Persoon.Adres.GemeenteCode", "AMS");
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteriaHuisnummertoevoeging = maakZoekCriteria("Persoon.Adres.Huisnummertoevoeging", "-");
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteriaLtovAdres = maakZoekCriteria("Persoon.Adres.LocatieTenOpzichteVanAdres",
                "ergens aan de overkant");
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteriaNadereBijhoudingsaardActueel = maakZoekCriteria(
                Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getNaam(), NadereBijhoudingsaard.ACTUEEL.getCode());
        assertThat(zoekCriteriaPersoon,
                containsInAnyOrder(zoekCriteriaPostcode, zoekCriteriaHuisnummer, zoekCriteriaHuisletter, zoekCriteriaWoonplaats, zoekCriteriaAnor,
                        zoekCriteriaGemeentecode, zoekCriteriaHuisnummertoevoeging, zoekCriteriaLtovAdres, zoekCriteriaNadereBijhoudingsaardActueel));
        zoekPersoonOpAdresVerzoekArgumentCaptor.getValue().getZoekCriteriaPersoon().forEach(zcp -> {
            if (zcp == zoekCriteriaNadereBijhoudingsaardActueel) {
                assertThat(zcp.getOf(), is(maakZoekCriteria(
                        Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getNaam(), NadereBijhoudingsaard.ONBEKEND.getCode())));
            }
        });
    }

    @Test
    public void geeftR2383AlsErGeenResultatenGevondenZijn() throws Exception {
        verzoek.getIdentificatiecriteria().setIdentificatiecodeNummeraanduiding("bagsleutel");
        when(geefMedebewonersOphalenPersoonService.voerStapUit(any(), any())).thenReturn(Collections.emptyList());

        final BevragingResultaat resultaat = service.voerStappenUit(verzoek);

        final InOrder inOrder = inOrder(peilmomentValidatieService, geefMedebewonersOphalenPersoonService);
        inOrder.verify(peilmomentValidatieService).valideerMaterieel("2010-01-01");
        inOrder.verify(geefMedebewonersOphalenPersoonService).voerStapUit(zoekPersoonOpAdresVerzoekArgumentCaptor.capture(), eq(autorisatiebundel));
        assertThat(Iterables.getOnlyElement(resultaat.getMeldingen()).getRegel(), is(Regel.R2383));
    }

    @Test
    public void geeftR2377AlsAdresCriteriaEnBagCriteriaGevuldZijn() throws Exception {
        verzoek.getIdentificatiecriteria().setIdentificatiecodeNummeraanduiding("bagsleutel");
        verzoek.getIdentificatiecriteria().setWoonplaatsnaam("Amsterdam");

        final BevragingResultaat resultaat = service.voerStappenUit(verzoek);

        Mockito.verifyZeroInteractions(peilmomentValidatieService);
        Mockito.verifyZeroInteractions(geefMedebewonersOphalenPersoonService);
        Mockito.verifyZeroInteractions(relatiefilterService);
        Mockito.verifyZeroInteractions(berichtFactory);
        assertThat(Iterables.getOnlyElement(resultaat.getMeldingen()).getRegel(), is(Regel.R2377));
    }

    @Test
    public void geeftR2377AlsPersoonCriteriaEnBagCriteriaGevuldZijn() throws Exception {
        verzoek.getIdentificatiecriteria().setIdentificatiecodeNummeraanduiding("bagsleutel");
        verzoek.getIdentificatiecriteria().setBurgerservicenummer("123456789");

        final BevragingResultaat resultaat = service.voerStappenUit(verzoek);

        Mockito.verifyZeroInteractions(peilmomentValidatieService);
        Mockito.verifyZeroInteractions(geefMedebewonersOphalenPersoonService);
        Mockito.verifyZeroInteractions(relatiefilterService);
        Mockito.verifyZeroInteractions(berichtFactory);
        assertThat(Iterables.getOnlyElement(resultaat.getMeldingen()).getRegel(), is(Regel.R2377));
    }

    @Test
    public void geeftR2377AlsPersoonCriteriaEnAdresCriteriaGevuldZijn() throws Exception {
        verzoek.getIdentificatiecriteria().setBurgerservicenummer("123456789");
        verzoek.getIdentificatiecriteria().setWoonplaatsnaam("Amsterdam");

        final BevragingResultaat resultaat = service.voerStappenUit(verzoek);

        Mockito.verifyZeroInteractions(peilmomentValidatieService);
        Mockito.verifyZeroInteractions(geefMedebewonersOphalenPersoonService);
        Mockito.verifyZeroInteractions(relatiefilterService);
        Mockito.verifyZeroInteractions(berichtFactory);
        assertThat(Iterables.getOnlyElement(resultaat.getMeldingen()).getRegel(), is(Regel.R2377));
    }

  @Test
    public void geeftR2377AlsPersoonCriteriaEnAdresseerObjectGevuldZijn() throws Exception {
        verzoek.getIdentificatiecriteria().setBurgerservicenummer("123456789");
        verzoek.getIdentificatiecriteria().setIdentificatiecodeAdresseerbaarObject("adresseerbaarobject");

        final BevragingResultaat resultaat = service.voerStappenUit(verzoek);

        Mockito.verifyZeroInteractions(peilmomentValidatieService);
        Mockito.verifyZeroInteractions(geefMedebewonersOphalenPersoonService);
        Mockito.verifyZeroInteractions(relatiefilterService);
        Mockito.verifyZeroInteractions(berichtFactory);
        assertThat(Iterables.getOnlyElement(resultaat.getMeldingen()).getRegel(), is(Regel.R2377));
    }

    @Test
    public void geeftR2377AlsGeenCriteriaGevuldZijn() throws Exception {
        final BevragingResultaat resultaat = service.voerStappenUit(verzoek);

        Mockito.verifyZeroInteractions(peilmomentValidatieService);
        Mockito.verifyZeroInteractions(geefMedebewonersOphalenPersoonService);
        Mockito.verifyZeroInteractions(relatiefilterService);
        Mockito.verifyZeroInteractions(berichtFactory);
        assertThat(Iterables.getOnlyElement(resultaat.getMeldingen()).getRegel(), is(Regel.R2377));
    }

    @Test
    public void geeftR2295IndienPeilmomentInToekomst() throws StapException {
        verzoek.getIdentificatiecriteria().setIdentificatiecodeNummeraanduiding("bagsleutel");
        String peilmomentMaterieel = String.format("%s-01-01", ZonedDateTime.now().plusYears(5).getYear());
        doThrow(new StapMeldingException(new Melding(Regel.R2295))).when(peilmomentValidatieService).valideerMaterieel(peilmomentMaterieel);

        verzoek.getParameters().setPeilmomentMaterieel(peilmomentMaterieel);

        final BevragingResultaat resultaat = service.voerStappenUit(verzoek);

        Mockito.verify(peilmomentValidatieService).valideerMaterieel(peilmomentMaterieel);
        Mockito.verifyZeroInteractions(geefMedebewonersOphalenPersoonService);
        Mockito.verifyZeroInteractions(relatiefilterService);
        Mockito.verifyZeroInteractions(berichtFactory);
        assertThat(Iterables.getOnlyElement(resultaat.getMeldingen()).getRegel(), is(Regel.R2295));
    }

    @Test
    public void geeftR1274IndienPeilmomentVerkeerdFormaat() throws StapException {
        verzoek.getIdentificatiecriteria().setIdentificatiecodeNummeraanduiding("bagsleutel");
        String peilmomentMaterieel = "20111-01-01";
        doThrow(new StapMeldingException(new Melding(Regel.R1274))).when(peilmomentValidatieService).valideerMaterieel(peilmomentMaterieel);

        verzoek.getParameters().setPeilmomentMaterieel(peilmomentMaterieel);

        final BevragingResultaat resultaat = service.voerStappenUit(verzoek);

        Mockito.verify(peilmomentValidatieService).valideerMaterieel(peilmomentMaterieel);
        Mockito.verifyZeroInteractions(geefMedebewonersOphalenPersoonService);
        Mockito.verifyZeroInteractions(relatiefilterService);
        Mockito.verifyZeroInteractions(berichtFactory);
        assertThat(Iterables.getOnlyElement(resultaat.getMeldingen()).getRegel(), is(Regel.R1274));
    }

    private ZoekPersoonGeneriekVerzoek.ZoekCriteria maakZoekCriteria(final String elementNaam, final String waarde) {
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteria = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        zoekCriteria.setZoekOptie(Zoekoptie.EXACT);
        zoekCriteria.setElementNaam(elementNaam);
        zoekCriteria.setWaarde(waarde);
        return zoekCriteria;
    }

    private void valideerZoekPersoonVerzoekBAG() {
        final ZoekPersoonOpAdresVerzoek zoekPersoonOpAdresVerzoek = zoekPersoonOpAdresVerzoekArgumentCaptor.getValue();
        final ZoekPersoonGeneriekVerzoek.ZoekBereikParameters zoekBereikParameters = zoekPersoonOpAdresVerzoek.getParameters().getZoekBereikParameters();
        assertThat(zoekBereikParameters.getZoekBereik(), is(Zoekbereik.PEILMOMENT));
        assertThat(zoekBereikParameters.getPeilmomentMaterieel(), is("2010-01-01"));
        final Set<ZoekPersoonGeneriekVerzoek.ZoekCriteria> zoekCriteriaPersoon = zoekPersoonOpAdresVerzoek.getZoekCriteriaPersoon();
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteria1 = maakZoekCriteria(
                Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getNaam(), NadereBijhoudingsaard.ACTUEEL.getCode());
        final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteria2 = maakZoekCriteria(
                Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getNaam(), "bagsleutel");
        assertThat(zoekCriteriaPersoon, containsInAnyOrder(zoekCriteria1, zoekCriteria2));
        zoekPersoonOpAdresVerzoek.getZoekCriteriaPersoon().forEach(zcp -> {
            if (zcp == zoekCriteria1) {
                assertThat(zcp.getOf(), is(maakZoekCriteria(
                        Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getNaam(), NadereBijhoudingsaard.ONBEKEND.getCode())));
            }
        });
    }
}
