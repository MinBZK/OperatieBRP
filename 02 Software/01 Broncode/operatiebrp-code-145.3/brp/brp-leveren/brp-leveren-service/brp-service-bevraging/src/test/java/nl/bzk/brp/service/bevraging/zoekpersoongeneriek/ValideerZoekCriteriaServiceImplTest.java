/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekbereik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.service.algemeen.request.DatumService;
import nl.bzk.brp.service.bevraging.algemeen.PeilmomentValidatieService;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.bevraging.zoekpersoon.ZoekPersoonVerzoek;
import nl.bzk.brp.service.cache.GeldigeAttributenElementenCache;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * ValideerZoekCriteriaServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class ValideerZoekCriteriaServiceImplTest {

    @InjectMocks
    private ValideerZoekCriteriaServiceImpl valideerZoekCriteriaService;

    @Mock
    private DatumService datumService;
    @Mock
    private GeldigeAttributenElementenCache geldigeAttributenElementenCache;
    @Mock
    private PeilmomentValidatieService peilmomentValidatieService;

    @Before
    public void setup() {
        Mockito.when(geldigeAttributenElementenCache.geldigVoorAttribuutAutorisatie(Mockito.any())).thenReturn(true);
    }

    @Test
    public void testGeldigOfZoekCriteria() {
        final AbstractZoekPersoonVerzoek bevragingVerzoek = new ZoekPersoonVerzoek();
        final AbstractZoekPersoonVerzoek.ZoekCriteria criterium = new AbstractZoekPersoonVerzoek.ZoekCriteria();
        final AttribuutElement attribuutElementDatum = getAttribuutElement(Element.PERSOON_ADRES_POSTCODE.getId());
        final AttribuutElement attribuutElementHuisNummer = getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId());
        criterium.setElementNaam(attribuutElementDatum.getNaam());
        criterium.setWaarde("1010TY");

        final AbstractZoekPersoonVerzoek.ZoekCriteria criteriumOf = new AbstractZoekPersoonVerzoek.ZoekCriteria();
        criteriumOf.setElementNaam(attribuutElementHuisNummer.getNaam());
        criteriumOf.setWaarde("3");
        criterium.setOf(criteriumOf);

        bevragingVerzoek.getZoekCriteriaPersoon().add(criterium);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElementDatum, attribuutElementHuisNummer);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testValideerPersoonSoortCodeUitzondering() {
        //supertypen hebben geen autorisatie
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_SOORTCODE.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "D", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2542, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testAttribuutIsStamgegevenReferentieGeldigVoorLeveren() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_ADELLIJKETITELCODE.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "4", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testAttribuutWaardeTypeIsDatumTijd() {
        final AttribuutElement attribuutElement = getAttribuutElement(
                Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "4", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testAttribuutWaardeTypeIsDatumTijdParseFout() throws StapMeldingException {
        Mockito.doThrow(StapMeldingException.class).when(datumService).parseDateTime(Mockito.anyString());

        final AttribuutElement attribuutElement = getAttribuutElement(
                Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "4", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2308, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testHistorischZoekBereikMaterieelGeenAutorisatieMaarGeenHistorieGroep() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "1900-10-10", Zoekoptie.EXACT);
        bevragingVerzoek.getParameters().setZoekBereik(Zoekbereik.MATERIELE_PERIODE);
        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testHistorischZoekBereikMaterieelGeenAutorisatie() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "4", Zoekoptie.EXACT);
        bevragingVerzoek.getParameters().setZoekBereik(Zoekbereik.MATERIELE_PERIODE);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testHistorischZoekBereikPeilMomentGeenAutorisatie() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "4", Zoekoptie.EXACT);
        bevragingVerzoek.getParameters().setZoekBereik(Zoekbereik.PEILMOMENT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testDatumAanvangGeldigheid() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "19001010", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(true, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2610, meldingen.iterator().next().getRegel());
    }


    @Test
    public void testHistorischPeilmomentOpgegevenGeenAutorisatie() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "19001010", Zoekoptie.EXACT);
        bevragingVerzoek.getParameters().setPeilmomentMaterieel("19001010");

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testHistorischPeilmomentOpgegevenInToekomst() throws StapMeldingException {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId());
        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(true, attribuutElement);
        String peilmoment = String.format("%s1010", ZonedDateTime.now().plusYears(5).getYear());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, peilmoment, Zoekoptie.EXACT);
        bevragingVerzoek.getParameters().setPeilmomentMaterieel(peilmoment);
        Mockito.doThrow(new StapMeldingException(new Melding(Regel.R2295))).when(peilmomentValidatieService).valideerMaterieel(peilmoment);

        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2295, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testHistorischPeilmomentOpgegevenGeautoriseerd() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "19001010", Zoekoptie.EXACT);
        bevragingVerzoek.getParameters().setPeilmomentMaterieel("19001010");

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(true, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testElementNietInKernSchema() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "1", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2542, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testGeldigeDatumWaarde() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "1910-10-10", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOngeldigeAutorisatie() {
        final AbstractZoekPersoonVerzoek bevragingVerzoek = new ZoekPersoonVerzoek();
        final AbstractZoekPersoonVerzoek.ZoekCriteria criterium = new AbstractZoekPersoonVerzoek.ZoekCriteria();
        final AttribuutElement attribuutElementDatum = getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM.getId());
        final AttribuutElement attribuutElementHuisNummer = getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId());
        criterium.setElementNaam(attribuutElementDatum.getNaam());
        criterium.setWaarde("1900-10-10");
        bevragingVerzoek.getZoekCriteriaPersoon().add(criterium);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElementHuisNummer);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2290, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testGeldigeAutorisatie() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "1910-10-10", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeldigeAutorisatieSoortAutorisatieOptioneel() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_PREDICAATCODE.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "A", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeldigeAutorisatieSoortAutorisatieVerplicht() {
        //element heeft geen autorisatie
        final AttribuutElement attribuutElement = getAttribuutElement(Element.NADEREBIJHOUDINGSAARD_CODE.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "C", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testGeldigeAutorisatieSoortAutorisatieVerplichtGeenOnderzoekZoeken() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.ONDERZOEK_DATUMAANVANG.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "1910-10-10", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testGeldigeAutorisatieSoortAutorisatieAanbevolen() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "123456789", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOngeldigeBooleanWaarde() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "X", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2308, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testGeldigeBooleanWaarde() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "J", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOngeldigeGetalWaarde() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "X", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2308, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testGeldigeGetalWaarde() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "3", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }


    @Test
    public void testOngeldigeMaximumLengteWaarde() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "1234567891123456", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2311, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testOngeldigZoekElementOnbekend() {
        final AbstractZoekPersoonVerzoek bevragingVerzoek = new ZoekPersoonVerzoek();
        final AbstractZoekPersoonVerzoek.ZoekCriteria criterium = new AbstractZoekPersoonVerzoek.ZoekCriteria();
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_ADRES_POSTCODE.getId());
        criterium.setElementNaam("xxx");
        criterium.setWaarde("2210AFR");
        bevragingVerzoek.getZoekCriteriaPersoon().add(criterium);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2265, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testOngeldigZoekElement() {
        Mockito.when(geldigeAttributenElementenCache.geldigVoorAttribuutAutorisatie(Mockito.any())).thenReturn(false);

        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_OBJECTSLEUTEL.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "2210AFR", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2542, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testOngeldigZoekElementGeenAttribuutElement() {
        final ObjectElement element = getObjectElement(Element.PERSOON_ADRES.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = new ZoekPersoonVerzoek();
        final AbstractZoekPersoonVerzoek.ZoekCriteria criterium = new AbstractZoekPersoonVerzoek.ZoekCriteria();
        criterium.setElementNaam(element.getNaam());
        criterium.setWaarde("z");
        criterium.setZoekOptie(Zoekoptie.KLEIN);
        bevragingVerzoek.getZoekCriteriaPersoon().add(criterium);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, (AttribuutElement) null);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2265, meldingen.iterator().next().getRegel());
    }


    @Test
    public void testOngeldigZoekElementOnderzoeksgegeven() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.ONDERZOEK_OMSCHRIJVING.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "2210AFR", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2389, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testOngeldigZoekElementVerantwoordingsgegeven() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.DOCUMENT_OMSCHRIJVING.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "2210AFR", Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2389, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testGeldigeMaximumLengteWaarde() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "123456789", Zoekoptie.EXACT);
        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }


    @Test
    public void testNietLeegMetZonderWaarde() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, null, Zoekoptie.EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2267, meldingen.iterator().next().getRegel());
    }


    @Test
    public void testLeegMetWaarde() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "1900-10-10", Zoekoptie.LEEG);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2266, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testLeegZonderWaarde() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, null, Zoekoptie.LEEG);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }

    /**
     * Type varchar,text,char mag vanaf zoeken.
     * NB : Voor type text bestaan (nog) geen attributen waarop gezocht mag worden, char moet nog doorgevoerd worden.
     */
    @Test
    public void testStringVanaf_DbTypeVarChar() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STAM.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "TEST", Zoekoptie.VANAF_KLEIN);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testStringKlein() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STAM.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "TEST", Zoekoptie.KLEIN);
        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testStringVanafLeeg() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STAM.getId());
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, null, Zoekoptie.VANAF_KLEIN);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2267, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testStringKleinLeeg() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STAM);
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, null, Zoekoptie.KLEIN);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2267, meldingen.iterator().next().getRegel());

    }

    @Test
    public void testStringVanaf() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STAM);
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "van", Zoekoptie.VANAF_KLEIN);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testStringVanafExact() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STAM);
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "van", Zoekoptie.VANAF_EXACT);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testGeenNummerVanaf() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER);
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "44", Zoekoptie.VANAF_KLEIN);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2281, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testGeenBooleanVanaf() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG);
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "J", Zoekoptie.VANAF_KLEIN);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2281, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testGeenNummerKlein() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER);
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "44", Zoekoptie.KLEIN);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2281, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testGeenDatumKlein() {
        final AttribuutElement attribuutElement = getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM);
        final AbstractZoekPersoonVerzoek bevragingVerzoek = maakBevragingVerzoek(attribuutElement, "20100101", Zoekoptie.KLEIN);

        final Autorisatiebundel autorisatieBundel = maakAutorisatiebundel(false, attribuutElement);
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(bevragingVerzoek, autorisatieBundel);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R2281, meldingen.iterator().next().getRegel());
    }


    private Autorisatiebundel maakAutorisatiebundel(final boolean historisch, final AttribuutElement... attribuutElements) {
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.ZOEK_PERSOON;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienst.setDienstbundel(dienstbundel);
        final Set<DienstbundelGroep> dienstbundelGroepSet = new HashSet<>();

        for (AttribuutElement attribuutElement : attribuutElements) {
            if (attribuutElement != null) {
                final Element groep = Element.parseId(attribuutElement.getGroepId());
                final DienstbundelGroep dienstbundelGroep = new DienstbundelGroep(dienstbundel, groep, historisch, historisch, historisch);
                dienstbundelGroepSet.add(dienstbundelGroep);
                dienstbundel.setDienstbundelGroepSet(dienstbundelGroepSet);
                final Set<DienstbundelGroepAttribuut> dienstbundelGroepAttrSet = new HashSet<>();
                final Element attribuut = Element.parseId(attribuutElement.getId());
                final DienstbundelGroepAttribuut dienstbundelGroepAttr = new DienstbundelGroepAttribuut(dienstbundelGroep, attribuut);
                dienstbundelGroepAttrSet.add(dienstbundelGroepAttr);
                dienstbundelGroep.setDienstbundelGroepAttribuutSet(dienstbundelGroepAttrSet);
            }
        }

        return autorisatiebundel;
    }

    private AbstractZoekPersoonVerzoek maakBevragingVerzoek(final AttribuutElement attribuutElement, final String waarde, final Zoekoptie zoekoptie) {
        final AbstractZoekPersoonVerzoek bevragingVerzoek = new ZoekPersoonVerzoek();
        final AbstractZoekPersoonVerzoek.ZoekCriteria criterium = maakCriterium(attribuutElement, waarde, zoekoptie);
        bevragingVerzoek.getZoekCriteriaPersoon().add(criterium);
        return bevragingVerzoek;
    }

    private AbstractZoekPersoonVerzoek.ZoekCriteria maakCriterium(final AttribuutElement attribuutElement, final String waarde,
                                                                  final Zoekoptie zoekoptie) {
        final AbstractZoekPersoonVerzoek.ZoekCriteria criterium = new AbstractZoekPersoonVerzoek.ZoekCriteria();
        criterium.setElementNaam(attribuutElement.getNaam());
        criterium.setWaarde(waarde);
        criterium.setZoekOptie(zoekoptie);
        return criterium;
    }

}
