/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bevraging;

import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.business.definities.impl.afstamming.KandidaatVader;
import nl.bzk.brp.business.dto.bevraging.DetailsPersoonVraag;
import nl.bzk.brp.business.dto.bevraging.KandidaatVaderVraag;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.business.dto.bevraging.PersonenOpAdresInclusiefBetrokkenhedenVraag;
import nl.bzk.brp.business.dto.bevraging.VraagDetailsPersoonBericht;
import nl.bzk.brp.business.dto.bevraging.VraagKandidaatVaderBericht;
import nl.bzk.brp.business.dto.bevraging.VraagOpties;
import nl.bzk.brp.business.dto.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenBericht;
import nl.bzk.brp.business.dto.bevraging.zoekcriteria.ZoekCriteriaPersoonOpAdres;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.attribuuttype.Administratienummer;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Nationaliteitcode;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonGeslachtsaanduidingGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.RelatieStandaardGroepModel;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonNationaliteitBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenBeeindigingRelatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class OpvragenPersoonBerichtUitvoerStapTest {

    @InjectMocks
    private final OpvragenPersoonBerichtUitvoerStap opvragenPersoonBerichtUitvoerStap =
                                                                                          new OpvragenPersoonBerichtUitvoerStap();

    @Mock
    private PersoonRepository                       persoonRepository;
    @Mock
    private KandidaatVader                          kandidaatVader;

    @Before
    public void init() {
        initMocks(this);
    }

    @Test
    public void testVraagOpDetailPersoonPersoonGevonden() {
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(Matchers.any(Burgerservicenummer.class)))
                .thenReturn(maakGevondenPersoon());

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagDetailsPersoonBericht(), null,
                resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        PersoonModel gevondenPersoon = resultaat.getGevondenPersonen().iterator().next();
        PersoonAdresModel persoonAdres = gevondenPersoon.getAdressen().iterator().next();

        // Note: alleen velden die voorkomen in de XSD zijn hier getest
        Assert.assertEquals("1234", gevondenPersoon.getIdentificatienummers().getBurgerservicenummer().getWaarde());
        Assert.assertEquals(Long.valueOf(1235L), gevondenPersoon.getIdentificatienummers().getAdministratienummer()
                .getWaarde());
        Assert.assertEquals(19800101, gevondenPersoon.getGeboorte().getDatumGeboorte().getWaarde().longValue());
        Assert.assertEquals(Geslachtsaanduiding.MAN, gevondenPersoon.getGeslachtsaanduiding().getGeslachtsaanduiding());
        // Assert.assertEquals("M", gevondenPersoon.getGeslachtsNaam());
        Assert.assertEquals(Integer.valueOf(12), persoonAdres.getGegevens().getHuisnummer().getWaarde());
        Assert.assertEquals("Postcode", persoonAdres.getGegevens().getPostcode().getWaarde());
        // Assert.assertEquals("voornaam", gevondenPersoon.getVoornamen());
    }

    @Test
    public void testVraagOpDetailPersoonGeenPersoonGevonden() {
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class))).thenReturn(
                null);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagDetailsPersoonBericht(), null,
                resultaat);

        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Soortmelding.INFORMATIE, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
        Assert.assertNull(resultaat.getGevondenPersonen());
    }

    @Test
    public void testVraagOpDetailPersoonMetHistorie() {
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(Matchers.any(Burgerservicenummer.class)))
                .thenReturn(maakGevondenPersoon());

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        VraagDetailsPersoonBericht vraag = maakVraagDetailsPersoonBericht();
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(vraag, null, resultaat);
        // methode wordt nooit aangeroepen.
        Mockito.verify(persoonRepository, Mockito.times(0)).vulaanAdresMetHistorie(Matchers.any(PersoonModel.class),
                Matchers.anyBoolean());

        vraag.getVraag().setVraagOpties(new VraagOpties());
        vraag.getVraag().getVraagOpties().setHistorieMaterieel(Ja.Ja);
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(vraag, null, resultaat);
        // methode wordt met flag false aangeroepen.
        Mockito.verify(persoonRepository, Mockito.times(1)).vulaanAdresMetHistorie(Matchers.any(PersoonModel.class),
                Matchers.eq(false));

        vraag.getVraag().getVraagOpties().setHistorieFormeel(Ja.Ja);
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(vraag, null, resultaat);
        Mockito.verify(persoonRepository, Mockito.times(1)).vulaanAdresMetHistorie(Matchers.any(PersoonModel.class),
                Matchers.eq(true));
    }

    @Test
    public void testVraagOpKandidaatVaderNietGevonden() {
        // fake dat de moeder is gevonden
        PersoonModel moeder = maakPersoon();
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class))).thenReturn(
                moeder);

        List<PersoonModel> kandidaten = new ArrayList<PersoonModel>();
        Mockito.when(kandidaatVader.bepaalKandidatenVader(Matchers.any(PersoonModel.class), Matchers.any(Datum.class)))
                .thenReturn(kandidaten);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagKandidaatVaderBericht(), null,
                resultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals("Kandidaat-vader kan niet worden bepaald.", resultaat.getMeldingen().get(0)
                .getOmschrijving());
        Assert.assertEquals(Soortmelding.INFORMATIE, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.BRPUC50110, resultaat.getMeldingen().get(0).getCode());
        Assert.assertNull(resultaat.getGevondenPersonen());
    }

    @Test
    public void testVraagOpKandidaatVaderBsnIsGeenVrouw() {
        // fake dat de moeder is gevonden
        PersoonModel moeder = maakPersoon();
        PersoonGeslachtsaanduidingGroepBericht gesl = new PersoonGeslachtsaanduidingGroepBericht();
        gesl.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);
        ReflectionTestUtils.setField(moeder, "geslachtsaanduiding", new PersoonGeslachtsaanduidingGroepModel(gesl));
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class))).thenReturn(
                moeder);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagKandidaatVaderBericht(), null,
                resultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals("De persoon is niet van het vrouwelijk geslacht.", resultaat.getMeldingen().get(0)
                .getOmschrijving());
        Assert.assertEquals(Soortmelding.FOUT, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testVraagPersonenOpAdresViaBsnNietGevonden() {
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(new ArrayList<PersoonModel>());
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagPersoonOpAdresBericht(), null,
                resultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Soortmelding.INFORMATIE, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
        Assert.assertNull(resultaat.getGevondenPersonen());
    }

    @Test
    public void testVraagPersonenOpAdresViaBsnZonderAdres() {
        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0), "adressen", null);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagPersoonOpAdresBericht(), null,
                resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                        Matchers.any(IdentificatiecodeNummeraanduiding.class));
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(Plaats.class), Matchers.any(Partij.class));
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.any(Postcode.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class));
    }

    @Test
    public void testVraagPersonenOpAdresViaBsnIdMetIdCodeAanduidingStap1() {
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(maakGevondenPersonenLijst());

        PersoonModel persoon1 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon1, "id", 1);
        PersoonModel persoon2 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon2, "id", 2);

        Mockito.when(
                persoonRepository
                        .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding(
                                "abcd"))).thenReturn(Arrays.asList(persoon1, persoon2));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagPersoonOpAdresBericht(), null,
                resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(Plaats.class), Matchers.any(Partij.class));
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.any(Postcode.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class));
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is aanwezig en IdentificatiecodeNummeraanduiding levert
     * geen extra resultaten op, er wordt gezocht met volledige adres.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetIdCodeAanduidingStap2() {
        Plaats plaats = new Plaats();
        Partij gemeente = new Partij();

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "woonplaats",
                plaats);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "gemeente",
                gemeente);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        Mockito.when(
                persoonRepository
                        .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding(
                                "abcd"))).thenReturn(personen);

        PersoonModel persoon1 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon1, "id", 1);
        PersoonModel persoon2 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon2, "id", 2);
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer(12), new Huisletter("A"), null, plaats, gemeente))
                .thenReturn(
                        Arrays.asList(persoon1, persoon2));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagPersoonOpAdresBericht(), null,
                resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                new NaamOpenbareRuimte("naamOpenbareRuimte"), new Huisnummer(12), new Huisletter("A"), null, plaats,
                gemeente);
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.any(Postcode.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class));
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is aanwezig en IdentificatiecodeNummeraanduiding levert
     * geen extra resultaten op, er wordt gezocht met volledige adres en volledige adres levert ook geen resultaten op.
     * Er wordt verder gezocht met postcode en huisnummer.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetIdCodeAanduidingStap2b() {
        Plaats plaats = new Plaats();
        Partij gemeente = new Partij();

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "woonplaats",
                plaats);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "gemeente",
                gemeente);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        // Vervolgens wordt met de persoon adres van de persoon gezocht op identificatiecodeNummeraanduiding
        Mockito.when(
                persoonRepository
                        .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding(
                                "abcd"))).thenReturn(personen);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer(12), new Huisletter("A"), null, plaats, gemeente))
                .thenReturn(personen);

        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        PersoonModel persoon1 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon1, "id", 1);
        PersoonModel persoon2 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon2, "id", 2);
        Mockito.when(
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.any(Postcode.class),
                        Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                        Matchers.any(Huisnummertoevoeging.class))).thenReturn(
                Arrays.asList(persoon1, persoon2));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123455"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                new NaamOpenbareRuimte("naamOpenbareRuimte"), new Huisnummer(12), new Huisletter("A"), null, plaats,
                gemeente);
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is aanwezig en IdentificatiecodeNummeraanduiding levert
     * geen extra resultaten op, volledige adres is niet aanwezig, er wordt gezocht met postcode huisnummmer.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetIdCodeAanduidingStap3() {
        List<PersoonModel> personen = maakGevondenPersonenLijst();

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        // Vervolgens wordt met de persoon adres van de persoon gezocht op identificatiecodeNummeraanduiding
        Mockito.when(
                persoonRepository
                        .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding(
                                "abcd"))).thenReturn(personen);

        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        PersoonModel persoon1 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon1, "id", 1);
        PersoonModel persoon2 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon2, "id", 2);
        Mockito.when(
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.any(Postcode.class),
                        Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                        Matchers.any(Huisnummertoevoeging.class))).thenReturn(
                Arrays.asList(persoon1, persoon2));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123455"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(Plaats.class), Matchers.any(Partij.class));
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is aanwezig en IdentificatiecodeNummeraanduiding levert
     * geen extra resultaten op, er wordt gezocht met volledige adres en volledige adres levert ook geen resultaten op.
     * Postcode is niet aanwezig.
     * Flow eindigt zonder personen.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetIdCodeAanduidingStap3bGeenResultaat() {
        Plaats plaats = new Plaats();
        Partij gemeente = new Partij();

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "woonplaats",
                plaats);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "postcode", null);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "gemeente",
                gemeente);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        // Vervolgens wordt met de persoon adres van de persoon gezocht op identificatiecodeNummeraanduiding
        Mockito.when(
                persoonRepository
                        .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding(
                                "abcd"))).thenReturn(personen);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer(12), new Huisletter("A"), null, plaats, gemeente))
                .thenReturn(personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123455"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(Plaats.class), Matchers.any(Partij.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.any(Postcode.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class));
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is niet aanwezig. Er wordt gezocht met Volledige
     * adres.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetVolledigeAdresStap1() {
        Plaats plaats = new Plaats();
        Partij gemeente = new Partij();

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "woonplaats",
                plaats);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "gemeente",
                gemeente);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "postcode", null);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        PersoonModel persoon1 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon1, "id", 1);
        PersoonModel persoon2 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon2, "id", 2);
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer(12), new Huisletter("A"), null, plaats, gemeente))
                .thenReturn(
                        Arrays.asList(persoon1, persoon2));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123455"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                        Matchers.any(IdentificatiecodeNummeraanduiding.class));
        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(Plaats.class), Matchers.any(Partij.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.any(Postcode.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class));
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is niet aanwezig. Er wordt gezocht met Volledige adres.
     * Volledige adres levert geen resultaten op. Er wordt gezocht met postcode huisnummer.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetVolledigeAdresStap2() {
        Plaats plaats = new Plaats();
        Partij gemeente = new Partij();

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "woonplaats",
                plaats);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "gemeente",
                gemeente);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer(12), new Huisletter("A"), null, plaats, gemeente))
                .thenReturn(personen);

        PersoonModel persoon1 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon1, "id", 1);
        PersoonModel persoon2 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon2, "id", 2);
        Mockito.when(
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.any(Postcode.class),
                        Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                        Matchers.any(Huisnummertoevoeging.class))).thenReturn(
                Arrays.asList(persoon1, persoon2));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123455"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                        Matchers.any(IdentificatiecodeNummeraanduiding.class));
        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(Plaats.class), Matchers.any(Partij.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.any(Postcode.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class));
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is niet aanwezig. Er wordt gezocht met Volledige adres.
     * Volledige adres levert geen resultaten op. Postcode huisnummer niet aanwezig, flow eindigd
     * zonder personen.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetVolledigeAdresStap2b() {
        Plaats plaats = new Plaats();
        Partij gemeente = new Partij();

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "woonplaats",
                plaats);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "gemeente",
                gemeente);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "postcode", null);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer(12), new Huisletter("A"), null, plaats, gemeente))
                .thenReturn(personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123455"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                        Matchers.any(IdentificatiecodeNummeraanduiding.class));
        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(Plaats.class), Matchers.any(Partij.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.any(Postcode.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class));
    }

    /**
     * Test zoeken met BSN, IdentificatiecodeNummeraanduiding en volledige adres niet aanwezig. Er wordt gezocht met
     * postcode.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetPostcodehuisnummer() {
        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        PersoonModel persoon1 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon1, "id", 1);
        PersoonModel persoon2 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon2, "id", 2);
        Mockito.when(
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.any(Postcode.class),
                        Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                        Matchers.any(Huisnummertoevoeging.class))).thenReturn(
                Arrays.asList(persoon1, persoon2));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123455"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                        Matchers.any(IdentificatiecodeNummeraanduiding.class));
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(Plaats.class), Matchers.any(Partij.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.any(Postcode.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class));
    }

    /**
     * Test zoeken met BSN, IdentificatiecodeNummeraanduiding en volledige adres niet aanwezig. Er postcode niet
     * aanwezig. Flow eindigd.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetPostcodehuisnummerNietAanwezig() {
        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "postcode", null);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123455"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                        Matchers.any(IdentificatiecodeNummeraanduiding.class));
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(Plaats.class), Matchers.any(Partij.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.any(Postcode.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class));
    }

    @Test
    public void testVraagOpPersonenOpAdresInclusiefBetrokkenhedenMetPostcode() {
        List<PersoonModel> personen = Arrays.asList(maakGevondenPersoon());
        Mockito.when(
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.any(Postcode.class),
                        Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                        Matchers.any(Huisnummertoevoeging.class))).thenReturn(personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setPostcode("1000AB");
        bericht.getVraag().getZoekCriteria().setHuisnummer(10);

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());
    }

    @Test
    public void testVraagOpPersonenOpAdresInclusiefBetrokkenhedenMetGemeentecode() {
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setGemeentecode("gemCode");
        bericht.getVraag().getZoekCriteria().setHuisnummer(10);
        bericht.getVraag().getZoekCriteria().setNaamOpenbareRuimte("straat");

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        // TODO Hosing: repository is nog niet geimplementeerd, tijdelijk geeft deze zoek criteria nog geen resultaat
        // terug.
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Soortmelding.INFORMATIE, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
        Assert.assertNull(resultaat.getGevondenPersonen());
    }

    @Test
    public void testVraagOpPersonenOpAdresInclusiefBetrokkenhedenMetOngeldigeZoekCriteria() {
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        ZoekCriteriaPersoonOpAdres criteria = bericht.getVraag().getZoekCriteria();
        criteria.setGemeentecode("gemCode");
        criteria.setHuisnummer(10);
        criteria.setNaamOpenbareRuimte("straat");
        criteria.setBurgerservicenummer(new Burgerservicenummer("12323"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        // TODO Hosing: repository is nog niet geimplementeerd, tijdelijk geeft deze zoek criteria nog geen resultaat
        // terug.
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Soortmelding.INFORMATIE, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
        Assert.assertNull(resultaat.getGevondenPersonen());
    }

    private VraagKandidaatVaderBericht maakVraagKandidaatVaderBericht() {
        KandidaatVaderVraag vraag = new KandidaatVaderVraag();
        vraag.getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("1234"));
        vraag.getZoekCriteria().setGeboortedatumKind(20120101);
        VraagKandidaatVaderBericht bericht = new VraagKandidaatVaderBericht();
        ReflectionTestUtils.setField(bericht, "vraag", vraag);
        return bericht;
    }

    private VraagPersonenOpAdresInclusiefBetrokkenhedenBericht maakVraagPersoonOpAdresBericht() {
        PersonenOpAdresInclusiefBetrokkenhedenVraag vraag = new PersonenOpAdresInclusiefBetrokkenhedenVraag();
        ZoekCriteriaPersoonOpAdres criteria = vraag.getZoekCriteria();
        criteria.setBurgerservicenummer(new Burgerservicenummer("1234"));
        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", vraag);
        return bericht;
    }

    private VraagDetailsPersoonBericht maakVraagDetailsPersoonBericht() {
        DetailsPersoonVraag vraag = new DetailsPersoonVraag();
        vraag.getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("1234"));
        VraagDetailsPersoonBericht bericht = new VraagDetailsPersoonBericht();
        ReflectionTestUtils.setField(bericht, "vraag", vraag);

        return bericht;
    }

    private List<PersoonModel> maakGevondenPersonenLijst() {
        List<PersoonModel> personen = new ArrayList<PersoonModel>();
        personen.add(maakGevondenPersoon());
        return personen;
    }

    private PersoonModel maakGevondenPersoon() {
        PersoonBericht persoon = new PersoonBericht();

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        gegevens.setHuisletter(new Huisletter("A"));
        gegevens.setHuisnummer(new Huisnummer(12));
        gegevens.setPostcode(new Postcode("Postcode"));
        gegevens.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding("abcd"));

        persoon.setSoort(SoortPersoon.INGESCHREVENE);

        PersoonIdentificatienummersGroepBericht idnrs = new PersoonIdentificatienummersGroepBericht();
        idnrs.setBurgerservicenummer(new Burgerservicenummer("1234"));
        idnrs.setAdministratienummer(new Administratienummer(Long.valueOf(1235)));
        persoon.setIdentificatienummers(idnrs);

        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();
        geboorte.setDatumGeboorte(new Datum(19800101));
        persoon.setGeboorte(geboorte);

        PersoonGeslachtsaanduidingGroepBericht persGeslAand = new PersoonGeslachtsaanduidingGroepBericht();
        persGeslAand.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);
        persoon.setGeslachtsaanduiding(persGeslAand);

        PersoonSamengesteldeNaamGroepBericht samengesteldNaam = new PersoonSamengesteldeNaamGroepBericht();
        samengesteldNaam.setGeslachtsnaam(new Geslachtsnaamcomponent("Pietersen"));
        persoon.setSamengesteldeNaam(samengesteldNaam);

        PersoonModel persoonMdl = new PersoonModel(persoon);
        ReflectionTestUtils.setField(persoonMdl, "id", 1);

        Set<PersoonAdresModel> adressen = new HashSet<PersoonAdresModel>();

        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setPersoon(persoon);
        persoonAdres.setGegevens(gegevens);

        adressen.add(new PersoonAdresModel(persoonAdres, persoonMdl));
        persoonMdl.getAdressen().addAll(adressen);

        Set<BetrokkenheidModel> persoonBetrokkenheden = new HashSet<BetrokkenheidModel>();

        RelatieBericht relatieBericht = new RelatieBericht();
        relatieBericht.setGegevens(new RelatieStandaardGroepBericht());
        RelatieModel huwelijk = new RelatieModel(relatieBericht);

        BetrokkenheidModel partner1 = new BetrokkenheidModel(new BetrokkenheidBericht(), persoonMdl, huwelijk);
        ReflectionTestUtils.setField(partner1, "rol", SoortBetrokkenheid.PARTNER);

        persoonBetrokkenheden.add(partner1);
        ReflectionTestUtils.setField(persoonMdl, "betrokkenheden", persoonBetrokkenheden);

        BetrokkenheidModel partner2 =
            new BetrokkenheidModel(new BetrokkenheidBericht(), new PersoonModel(new PersoonBericht()), huwelijk);
        // moeder als andere partner. !! (vast id = 12L)
        ReflectionTestUtils.setField(partner2, "rol", SoortBetrokkenheid.PARTNER);

        ReflectionTestUtils.setField(partner2.getBetrokkene(), "id", 12);
        ReflectionTestUtils.setField(partner2.getBetrokkene(), "soort", SoortPersoon.INGESCHREVENE);

        HashSet<BetrokkenheidModel> huwlijkBetr = new HashSet<BetrokkenheidModel>();
        huwlijkBetr.add(partner1);
        huwlijkBetr.add(partner2);
        ReflectionTestUtils.setField(huwelijk, "soort", SoortRelatie.HUWELIJK);
        ReflectionTestUtils.setField(huwelijk, "betrokkenheden", huwlijkBetr);

        RelatieStandaardGroepBericht gegevensHuwelijk = new RelatieStandaardGroepBericht();
        gegevensHuwelijk.setDatumAanvang(new Datum(20120101));
        ReflectionTestUtils.setField(huwelijk, "gegevens", new RelatieStandaardGroepModel(gegevensHuwelijk));

        ReflectionTestUtils.setField(persoonMdl, "betrokkenheden", persoonBetrokkenheden);
        ReflectionTestUtils.setField(persoonMdl, "id", 23654823);

        return persoonMdl;
    }

    private PersoonModel maakPersoon() {
        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();

        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht adresGegevens = new PersoonAdresStandaardGroepBericht();
        adresGegevens.setHuisletter(new Huisletter("A"));
        adresGegevens.setHuisnummer(new Huisnummer(12));
        adresGegevens.setPostcode(new Postcode("Postcode"));
        persoonAdres.setGegevens(adresGegevens);

        adressen.add(persoonAdres);

        PersoonBericht persoon = new PersoonBericht();
        PersoonIdentificatienummersGroepBericht idnrs = new PersoonIdentificatienummersGroepBericht();
        idnrs.setBurgerservicenummer(new Burgerservicenummer("123456782"));
        idnrs.setAdministratienummer(new Administratienummer(Long.valueOf(9234567821L)));
        persoon.setIdentificatienummers(idnrs);

        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();
        geboorte.setDatumGeboorte(new Datum(19800101));
        persoon.setGeboorte(geboorte);

        PersoonGeslachtsaanduidingGroepBericht persGeslAand = new PersoonGeslachtsaanduidingGroepBericht();
        persGeslAand.setGeslachtsaanduiding(Geslachtsaanduiding.VROUW);
        persoon.setGeslachtsaanduiding(persGeslAand);

        PersoonSamengesteldeNaamGroepBericht samengesteldNaam = new PersoonSamengesteldeNaamGroepBericht();
        samengesteldNaam.setGeslachtsnaam(new Geslachtsnaamcomponent("Pietersen"));
        persoon.setSamengesteldeNaam(samengesteldNaam);

        persoon.setAdressen(adressen);

        PersoonModel persoonModel = new PersoonModel(persoon);
        ReflectionTestUtils.setField(persoonModel, "id", 12);
        return persoonModel;
    }

    private BetrokkenheidModel maakBetrokkenheden(final Integer overlijdingsDatum, final short nationaliteitCode) {
        RelatieBericht relatie = new RelatieBericht();
        relatie.setGegevens(new RelatieStandaardGroepBericht());
        relatie.getGegevens().setDatumAanvang(new Datum(20101231));

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());

        PersoonNationaliteitBericht nationaliteit = new PersoonNationaliteitBericht();
        nationaliteit.setNationaliteit(new Nationaliteit());
        nationaliteit.getNationaliteit().setNationaliteitcode(new Nationaliteitcode(nationaliteitCode));

        persoon.setNationaliteiten(Arrays.asList(nationaliteit));

        if (overlijdingsDatum != null) {
            relatie.getGegevens().setDatumEinde(new Datum(overlijdingsDatum));
            RedenBeeindigingRelatie redenBeeindigingRelatie = new RedenBeeindigingRelatie();
            ReflectionTestUtils.setField(redenBeeindigingRelatie, "code",
                    BrpConstanten.REDEN_BEEINDIGING_RELATIE_OVERLIJDEN_CODE);
            relatie.getGegevens().setRedenBeeindigingRelatie(redenBeeindigingRelatie);
            persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("overleden"));
        } else {
            persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("nietoverleden"));
        }

        RelatieModel relatieModel = new RelatieModel(relatie);
        PersoonModel persoonModel = new PersoonModel(persoon);
        persoonModel.getNationaliteiten().add(new PersoonNationaliteitModel(nationaliteit, persoonModel));

        BetrokkenheidBericht betrokkenheid = new BetrokkenheidBericht();
        return new BetrokkenheidModel(betrokkenheid, persoonModel, relatieModel);
    }
}
