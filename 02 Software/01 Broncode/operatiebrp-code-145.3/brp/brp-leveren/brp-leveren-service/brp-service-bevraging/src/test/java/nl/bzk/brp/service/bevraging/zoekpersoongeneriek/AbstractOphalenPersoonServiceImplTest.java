/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekbereik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.request.DatumService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.bevraging.zoekpersoon.ZoekPersoonVerzoek;
import nl.bzk.brp.service.dalapi.QueryNietUitgevoerdException;
import nl.bzk.brp.service.dalapi.ZoekPersoonDataOphalerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * AbstractOphalenPersoonServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractOphalenPersoonServiceImplTest {

    @Mock
    private PersoonslijstService persoonslijstService;
    @Mock
    private GevondenZoekPersoonFilterService gevondenZoekPersoonFilterService;
    @Mock
    private ZoekPersoonDataOphalerService zoekPersoonDataOphalerService;
    @Mock
    private ConverteerVerzoekZoekCriteriaService zoekCriteriaConverteerService;
    @Mock
    private DatumService datumService;

    @InjectMocks
    private TestAbstractOphalenPersoonServiceImpl ophalenZoekPersoonPersoonService = new TestAbstractOphalenPersoonServiceImpl();

    private StapMeldingException exception;

    @Test
    public void testGoedPad() throws QueryNietUitgevoerdException, StapException {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(10);
        final ZoekPersoonVerzoek bevragingVerzoek = new ZoekPersoonVerzoek();
        final AbstractZoekPersoonVerzoek.ZoekCriteria zoekCriterium = new AbstractZoekPersoonVerzoek.ZoekCriteria();
        zoekCriterium.setElementNaam(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()).getNaam());
        zoekCriterium.setZoekOptie(Zoekoptie.EXACT);
        zoekCriterium.setWaarde("123");
        bevragingVerzoek.getZoekCriteriaPersoon().add(zoekCriterium);

        ReflectionTestUtils.setField(ophalenZoekPersoonPersoonService, "maxConcurrentRequest", 10);
        ReflectionTestUtils.setField(ophalenZoekPersoonPersoonService, "maxResulatenTussenResultaat", 15);

        Mockito.when(zoekPersoonDataOphalerService.zoekPersonenActueel(Mockito.anySetOf(ZoekCriterium.class), Mockito.anyInt()))
                .thenReturn(Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L));

        final long persoonId = 1;

        final Persoonslijst testPersoon = new Persoonslijst(TestBuilders.maakLeegPersoon().metId(persoonId).build(), 0L);
        final List<Persoonslijst> persoonslijstLijstMock = Lists.newArrayList(testPersoon);
        Mockito.when(persoonslijstService.getById(persoonId)).thenReturn(testPersoon);

        Mockito.when(gevondenZoekPersoonFilterService.filterPersoonslijst(Mockito.any(Autorisatiebundel.class), Mockito.anyListOf(Persoonslijst.class)))
                .thenReturn(persoonslijstLijstMock);

        final List<Persoonslijst> persoonslijstLijst = ophalenZoekPersoonPersoonService.voerStapUit(bevragingVerzoek, autorisatiebundel);

        Assert.assertEquals(persoonslijstLijstMock.size(), persoonslijstLijst.size());

        Mockito.verify(zoekPersoonDataOphalerService, Mockito.times(0)).zoekPersonenHistorisch(Mockito.anySetOf(ZoekCriterium.class), Mockito.eq(20101010),
                Mockito.anyBoolean(), Mockito
                        .anyInt());
        Mockito.verify(zoekPersoonDataOphalerService, Mockito.times(1)).zoekPersonenActueel(Mockito.anySetOf(ZoekCriterium.class), Mockito.anyInt());
    }


    @Test
    public void testGoedPadPeilperiodeMetPeilmoment() throws QueryNietUitgevoerdException, StapException {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(10);
        final ZoekPersoonVerzoek bevragingVerzoek = new ZoekPersoonVerzoek();
        bevragingVerzoek.getParameters().setZoekBereik(Zoekbereik.MATERIELE_PERIODE);

        ReflectionTestUtils.setField(ophalenZoekPersoonPersoonService, "maxConcurrentRequest", 10);
        ReflectionTestUtils.setField(ophalenZoekPersoonPersoonService, "maxResulatenTussenResultaat", 20);
        final String peilmomentMaterieel = "2010-10-10";

        final long persoonId = 1;
        final List<Long> persoonIds = Lists.newArrayList(persoonId);

        Mockito.when(zoekPersoonDataOphalerService.zoekPersonenActueel(Mockito.anySetOf(ZoekCriterium.class), Mockito.anyInt()))
                .thenReturn(Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L));
        Mockito.when(zoekPersoonDataOphalerService.zoekPersonenHistorisch(Mockito.anySetOf(ZoekCriterium.class), Mockito.eq(20101010), Mockito.anyBoolean(), Mockito.anyInt()))
                .thenReturn(persoonIds);
        final LocalDate localDate = LocalDate.parse(peilmomentMaterieel, DateTimeFormatter.ISO_DATE);
        Mockito.when(datumService.parseDate(peilmomentMaterieel)).thenReturn(localDate);

        bevragingVerzoek.getParameters().setPeilmomentMaterieel(peilmomentMaterieel);
        final ZoekPersoonVerzoek.ZoekCriteria zoekCriterium = new AbstractZoekPersoonVerzoek.ZoekCriteria();
        zoekCriterium.setElementNaam(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()).getNaam());
        zoekCriterium.setZoekOptie(Zoekoptie.EXACT);
        zoekCriterium.setWaarde("123");
        bevragingVerzoek.getZoekCriteriaPersoon().add(zoekCriterium);

        final Persoonslijst testPersoon = new Persoonslijst(TestBuilders.maakLeegPersoon().metId(persoonId).build(), 0L);
        final List<Persoonslijst> persoonslijstLijstMock = Lists.newArrayList(testPersoon);
        Mockito.when(persoonslijstService.getByIdsVoorZoeken(Sets.newHashSet(persoonId))).thenReturn(persoonslijstLijstMock);

        Mockito.when(gevondenZoekPersoonFilterService.filterPersoonslijst(Mockito.any(Autorisatiebundel.class), Mockito.anyListOf(Persoonslijst.class)))
                .thenReturn(persoonslijstLijstMock);

        final List<Persoonslijst> persoonslijstLijst = ophalenZoekPersoonPersoonService.voerStapUit(bevragingVerzoek, autorisatiebundel);

        Assert.assertEquals(persoonslijstLijstMock.size(), persoonslijstLijst.size());

        Mockito.verify(zoekPersoonDataOphalerService, Mockito.times(1)).zoekPersonenHistorisch(Mockito.anySetOf(ZoekCriterium.class), Mockito.eq(20101010),
                Mockito.anyBoolean(), Mockito
                        .anyInt());
        Mockito.verify(zoekPersoonDataOphalerService, Mockito.times(0)).zoekPersonenActueel(Mockito.anySetOf(ZoekCriterium.class), Mockito.anyInt());
    }

    @Test
    public void testGoedPadPeilperiodeMetPeilmomentNullZoekbereikMaterieel()
            throws QueryNietUitgevoerdException, StapException {
        ReflectionTestUtils.setField(ophalenZoekPersoonPersoonService, "maxConcurrentRequest", 10);
        ReflectionTestUtils.setField(ophalenZoekPersoonPersoonService, "maxResulatenTussenResultaat", 20);
        final String peilmomentMaterieel = "2010-10-10";

        Mockito.when(zoekPersoonDataOphalerService.zoekPersonenActueel(Mockito.anySetOf(ZoekCriterium.class), Mockito.anyInt()))
                .thenReturn(Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L));
        final LocalDate localDate = LocalDate.parse(peilmomentMaterieel, DateTimeFormatter.ISO_DATE);
        Mockito.when(datumService.parseDate(peilmomentMaterieel)).thenReturn(localDate);

        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(10);
        final ZoekPersoonVerzoek bevragingVerzoek = new ZoekPersoonVerzoek();
        //zoekbereik ==  materieel
        bevragingVerzoek.getParameters().setZoekBereik(Zoekbereik.MATERIELE_PERIODE);
        //peilmoment materieel == null
        bevragingVerzoek.getParameters().setPeilmomentMaterieel(null);
        final AbstractZoekPersoonVerzoek.ZoekCriteria zoekCriterium = new AbstractZoekPersoonVerzoek.ZoekCriteria();
        zoekCriterium.setElementNaam(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()).getNaam());
        zoekCriterium.setZoekOptie(Zoekoptie.EXACT);
        zoekCriterium.setWaarde("123");
        bevragingVerzoek.getZoekCriteriaPersoon().add(zoekCriterium);

        final long persoonId = 1;
        final List<Long> persoonIds = Lists.newArrayList(persoonId);
        Mockito.when(zoekPersoonDataOphalerService.zoekPersonenHistorisch(Mockito.anySetOf(ZoekCriterium.class), Mockito.eq(20101010), Mockito.anyBoolean(), Mockito.anyInt()))
                .thenReturn(persoonIds);

        final Persoonslijst testPersoon = new Persoonslijst(TestBuilders.maakLeegPersoon().metId(persoonId).build(), 0L);
        final List<Persoonslijst> persoonslijstLijstMock = Lists.newArrayList(testPersoon);
        Mockito.when(persoonslijstService.getByIdsVoorZoeken(Sets.newHashSet(persoonId))).thenReturn(persoonslijstLijstMock);

        Mockito.when(gevondenZoekPersoonFilterService.filterPersoonslijst(autorisatiebundel, persoonslijstLijstMock)).thenReturn(
                persoonslijstLijstMock);

        final List<Persoonslijst> persoonslijstLijst = ophalenZoekPersoonPersoonService.voerStapUit(bevragingVerzoek, autorisatiebundel);

        Assert.assertTrue(persoonslijstLijst.size() == 0);
    }

    @Test
    public void testGoedPadPeilperiodeMetPeilmomentMaterieelZoekbereikPeilMoment()
            throws QueryNietUitgevoerdException, StapException {
        ReflectionTestUtils.setField(ophalenZoekPersoonPersoonService, "maxConcurrentRequest", 10);
        ReflectionTestUtils.setField(ophalenZoekPersoonPersoonService, "maxResulatenTussenResultaat", 20);
        final String peilmomentMaterieel = "2010-10-10";

        Mockito.when(zoekPersoonDataOphalerService.zoekPersonenActueel(Mockito.anySetOf(ZoekCriterium.class), Mockito.anyInt()))
                .thenReturn(Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L));
        final LocalDate localDate = LocalDate.parse(peilmomentMaterieel, DateTimeFormatter.ISO_DATE);
        Mockito.when(datumService.parseDate(peilmomentMaterieel)).thenReturn(localDate);

        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(15);
        final ZoekPersoonVerzoek bevragingVerzoek = new ZoekPersoonVerzoek();
        //zoekbereik == peilmoment
        bevragingVerzoek.getParameters().setZoekBereik(Zoekbereik.PEILMOMENT);
        //incl peilmoment materieel
        bevragingVerzoek.getParameters().setPeilmomentMaterieel(peilmomentMaterieel);
        final AbstractZoekPersoonVerzoek.ZoekCriteria zoekCriterium = new AbstractZoekPersoonVerzoek.ZoekCriteria();
        zoekCriterium.setElementNaam(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()).getNaam());
        zoekCriterium.setZoekOptie(Zoekoptie.EXACT);
        zoekCriterium.setWaarde("123");
        bevragingVerzoek.getZoekCriteriaPersoon().add(zoekCriterium);

        final long persoonId = 1;
        final List<Long> persoonIds = Lists.newArrayList(persoonId);
        Mockito.when(zoekPersoonDataOphalerService.zoekPersonenHistorisch(Mockito.anySetOf(ZoekCriterium.class), Mockito.eq(20101010), Mockito.anyBoolean(), Mockito.anyInt()))
                .thenReturn(persoonIds);

        final Persoonslijst testPersoon = new Persoonslijst(TestBuilders.maakLeegPersoon().metId(persoonId).build(), 0L);
        final List<Persoonslijst> persoonslijstLijstMock = Lists.newArrayList(testPersoon);
        Mockito.when(persoonslijstService.getByIdsVoorZoeken(Sets.newHashSet(persoonId))).thenReturn(persoonslijstLijstMock);

        Mockito.when(gevondenZoekPersoonFilterService.filterPersoonslijst(autorisatiebundel, persoonslijstLijstMock)).thenReturn(
                persoonslijstLijstMock);

        final List<Persoonslijst> persoonslijstLijst = ophalenZoekPersoonPersoonService.voerStapUit(bevragingVerzoek, autorisatiebundel);

        Assert.assertTrue(persoonslijstLijst.size() == 1);
    }


    @Test(expected = StapMeldingException.class)
    public void testTeveelZoekResultatenTussenResultaat() throws QueryNietUitgevoerdException, StapException {
        //meer dan max tussenresultaten
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(10);
        final ZoekPersoonVerzoek bevragingVerzoek = new ZoekPersoonVerzoek();

        ReflectionTestUtils.setField(ophalenZoekPersoonPersoonService, "maxConcurrentRequest", 10);
        ReflectionTestUtils.setField(ophalenZoekPersoonPersoonService, "maxResulatenTussenResultaat", 1);
        Mockito.when(zoekPersoonDataOphalerService.zoekPersonenActueel(Mockito.anySetOf(ZoekCriterium.class), Mockito.anyInt()))
                .thenReturn(Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L));

        ophalenZoekPersoonPersoonService.voerStapUit(bevragingVerzoek, autorisatiebundel);
    }

    @Test(expected = StapMeldingException.class)
    public void testTeveelZoekResultaten() throws QueryNietUitgevoerdException, StapException {
        exception = new StapMeldingException(new Melding(Regel.ALG0001));
        //0 max resultaten, filter levert er 1 op dus fout
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(0);
        final ZoekPersoonVerzoek bevragingVerzoek = new ZoekPersoonVerzoek();

        ReflectionTestUtils.setField(ophalenZoekPersoonPersoonService, "maxConcurrentRequest", 10);
        ReflectionTestUtils.setField(ophalenZoekPersoonPersoonService, "maxResulatenTussenResultaat", 100);
        Mockito.when(zoekPersoonDataOphalerService.zoekPersonenActueel(Mockito.anySetOf(ZoekCriterium.class), Mockito.anyInt()))
                .thenReturn(Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L));

        final long persoonId = 1L;
        final Persoonslijst testPersoon = new Persoonslijst(TestBuilders.maakLeegPersoon().metId(persoonId).build(), 0L);
        final List<Persoonslijst> persoonslijstLijstMock = Lists.newArrayList(testPersoon);
        Mockito.when(persoonslijstService.getByIdsVoorZoeken(Sets.newHashSet(persoonId))).thenReturn(persoonslijstLijstMock);

        Mockito.when(gevondenZoekPersoonFilterService.filterPersoonslijst(Mockito.any(Autorisatiebundel.class), Mockito.anyListOf(Persoonslijst.class)))
                .thenReturn(persoonslijstLijstMock);

        ophalenZoekPersoonPersoonService.voerStapUit(bevragingVerzoek, autorisatiebundel);
    }

    @Test(expected = StapException.class)
    public void testTeDruk() throws QueryNietUitgevoerdException, StapException {
        ReflectionTestUtils.setField(ophalenZoekPersoonPersoonService, "maxConcurrentRequest", 0);
        Mockito.when(zoekPersoonDataOphalerService.zoekPersonenActueel(Mockito.anySetOf(ZoekCriterium.class), Mockito.anyInt()))
                .thenReturn(Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L));
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(10);
        final ZoekPersoonVerzoek bevragingVerzoek = new ZoekPersoonVerzoek();
        ophalenZoekPersoonPersoonService.voerStapUit(bevragingVerzoek, autorisatiebundel);
    }

    private Autorisatiebundel maakAutorisatiebundel(Integer maxZoekResultaten) {
        final Partij partij = new Partij("test", "000001");
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        partij.addPartijRol(partijRol);
        final Leveringsautorisatie la = new Leveringsautorisatie(Stelsel.BRP, false);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, la);
        final Dienstbundel dienstBundel = new Dienstbundel(la);
        final Dienst dienst = new Dienst(dienstBundel, SoortDienst.ZOEK_PERSOON);
        dienst.setMaximumAantalZoekresultaten(maxZoekResultaten);
        dienstBundel.addDienstSet(dienst);
        return new Autorisatiebundel(tla, dienst);
    }

    private class TestAbstractOphalenPersoonServiceImpl extends AbstractZoekPersoonOphalenPersoonServiceImpl<ZoekPersoonVerzoek> {

        @Override
        protected void valideerAantalZoekResultaten(final List<Persoonslijst> gefilterdePersoonsgegevens, final Autorisatiebundel autorisatiebundel,
                                                    final ZoekPersoonGeneriekVerzoek.ZoekBereikParameters zoekBereikParameters)
                throws StapMeldingException {
            if (exception != null) {
                throw exception;
            }
        }
    }
}
