/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bevraging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.business.dto.bevraging.DetailsPersoonVraag;
import nl.bzk.brp.business.dto.bevraging.KandidaatVaderVraag;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.business.dto.bevraging.PersonenOpAdresInclusiefBetrokkenhedenVraag;
import nl.bzk.brp.business.dto.bevraging.VraagDetailsPersoonBericht;
import nl.bzk.brp.business.dto.bevraging.VraagKandidaatVaderBericht;
import nl.bzk.brp.business.dto.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenBericht;
import nl.bzk.brp.business.dto.bevraging.zoekcriteria.ZoekCriteriaPersoonOpAdres;
import nl.bzk.brp.dataaccess.repository.PersoonMdlRepository;
import nl.bzk.brp.dataaccess.repository.RelatieMdlRepository;
import nl.bzk.brp.dataaccess.selectie.RelatieSelectieFilter;
import nl.bzk.brp.model.attribuuttype.Administratienummer;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummerAanduiding;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.LocatieTovAdres;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsAanduidingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatieNummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.groep.logisch.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeslachtsAanduidingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonGeslachtsAanduidingGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.RelatieStandaardGroepModel;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.GeslachtsAanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class OpvragenPersoonBerichtUitvoerStapTest {

    private OpvragenPersoonBerichtUitvoerStap opvragenPersoonBerichtUitvoerStap;

    @Mock
    private PersoonMdlRepository              persoonMdlRepository;
    @Mock
    private RelatieMdlRepository              relatieMdlRepository;

    @Before
    public void init() {
        opvragenPersoonBerichtUitvoerStap = new OpvragenPersoonBerichtUitvoerStap();
        // fake the @inject annotations
        ReflectionTestUtils.setField(opvragenPersoonBerichtUitvoerStap, "persoonMdlRepository", persoonMdlRepository);
        ReflectionTestUtils.setField(opvragenPersoonBerichtUitvoerStap, "relatieMdlRepository", relatieMdlRepository);
    }

    @Test
    public void testVraagOpDetailPersoonPersoonGevonden() {
        Mockito.when(persoonMdlRepository.haalPersoonOpMetBurgerservicenummer(Matchers.any(Burgerservicenummer.class)))
                .thenReturn(maakGevondenPersoon());

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagDetailsPersoonBericht(), null,
                resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        PersoonModel gevondenPersoon = resultaat.getGevondenPersonen().iterator().next();
        PersoonAdresModel persoonAdres = gevondenPersoon.getAdressen().iterator().next();

        // Note: alleen velden die voorkomen in de XSD zijn hier getest
        Assert.assertEquals("1234", gevondenPersoon.getIdentificatieNummers().getBurgerServiceNummer().getWaarde());
        Assert.assertEquals("1235", gevondenPersoon.getIdentificatieNummers().getAdministratieNummer().getWaarde());
        Assert.assertEquals(19800101, gevondenPersoon.getGeboorte().getDatumGeboorte().getWaarde().longValue());
        Assert.assertEquals(GeslachtsAanduiding.MAN, gevondenPersoon.getGeslachtsAanduiding().getGeslachtsAanduiding());
        // Assert.assertEquals("M", gevondenPersoon.getGeslachtsNaam());
        Assert.assertEquals("12", persoonAdres.getGegevens().getHuisnummer().getWaarde());
        Assert.assertEquals("Postcode", persoonAdres.getGegevens().getPostcode().getWaarde());
        // Assert.assertEquals("voornaam", gevondenPersoon.getVoornamen());
    }

    @Test
    public void testVraagOpDetailPersoonGeenPersoonGevonden() {
        Mockito.when(persoonMdlRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class)))
                .thenReturn(null);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagDetailsPersoonBericht(), null,
                resultaat);

        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.INFO, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
        Assert.assertNull(resultaat.getGevondenPersonen());
    }

    @Test
    public void testVraagOpKandidaatVaderNietGevonden() {
        // fake dat de moeder is gevonden
        PersoonModel moeder = maakPersistentPersoon();
        Mockito.when(persoonMdlRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class)))
                .thenReturn(moeder);
        Mockito.when(
                relatieMdlRepository.haalopPersoonIdsVanRelatiesVanPersoon(Matchers.anyLong(),
                    Matchers.any(RelatieSelectieFilter.class))).thenReturn(new ArrayList<Long>());
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagKandidaatVaderBericht(), null,
                resultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals("Kandidaat-vader kan niet worden bepaald.", resultaat.getMeldingen().get(0)
                .getOmschrijving());
        Assert.assertEquals(SoortMelding.INFO, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
        Assert.assertNull(resultaat.getGevondenPersonen());
    }

    @Test
    public void testVraagOpKandidaatVaderBsnIsGeenVrouw() {
        // fake dat de moeder is gevonden
        PersoonModel moeder = maakPersistentPersoon();
        PersoonGeslachtsAanduidingGroepBericht gesl = new PersoonGeslachtsAanduidingGroepBericht();
        ReflectionTestUtils.setField(gesl, "geslachtsAanduiding", GeslachtsAanduiding.MAN);
        ReflectionTestUtils.setField(moeder, "geslachtsAanduiding", new PersoonGeslachtsAanduidingGroepModel(gesl));
        Mockito.when(persoonMdlRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class)))
                .thenReturn(moeder);
        Mockito.when(
                relatieMdlRepository.haalopPersoonIdsVanRelatiesVanPersoon(Matchers.anyLong(),
                    Matchers.any(RelatieSelectieFilter.class))).thenReturn(new ArrayList<Long>());
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagKandidaatVaderBericht(), null,
                resultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals("De persoon is niet van het vrouwelijk geslacht.", resultaat.getMeldingen().get(0)
                .getOmschrijving());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testVraagPersonenOpAdresViaBsnNietGevonden() {
        Mockito.when(
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(new ArrayList<PersoonModel>());
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagPersoonOpAdresBericht(), null,
                resultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.INFO, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
        Assert.assertNull(resultaat.getGevondenPersonen());
    }

    /**
     * Zoeken op BSN met IdentificatiecodeNummeraanduiding in de woonadres.
     */
    @Test
    public void testVraagOpKandidaatVader() {
        // fake dat de moeder is gevonden
        PersoonModel moeder = maakPersistentPersoon();
        Mockito.when(persoonMdlRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class)))
                .thenReturn(moeder);
        List<Long> echtgenotenIds = Arrays.asList(new Long(8731137));
        Mockito.when(
                relatieMdlRepository.haalopPersoonIdsVanRelatiesVanPersoon(Matchers.any(java.lang.Long.class),
                    Matchers.any(RelatieSelectieFilter.class))).thenReturn(echtgenotenIds);
        Mockito.when(persoonMdlRepository.haalPersoonMetAdres(Matchers.any(java.lang.Long.class))).thenReturn(
                maakGevondenPersoon());
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagKandidaatVaderBericht(), null,
                resultaat);
        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        PersoonModel gevondenPersoon = resultaat.getGevondenPersonen().iterator().next();
        PersoonAdresModel persoonAdres = gevondenPersoon.getAdressen().iterator().next();
    }

    @Test
    public void testVraagPersonenOpAdresViaBsnZonderAdres() {
        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0), "adressen", null);

        Mockito.when(
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagPersoonOpAdresBericht(), null,
                resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonMdlRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                        Matchers.any(IdentificatiecodeNummerAanduiding.class));
        Mockito.verify(persoonMdlRepository, Mockito.times(0)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(LocatieOmschrijving.class),
                Matchers.any(LocatieTovAdres.class), Matchers.any(Plaats.class));
        Mockito.verify(persoonMdlRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.any(Postcode.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class));
    }

    @Test
    public void testVraagPersonenOpAdresViaBsnIdMetIdCodeAanduidingStap1() {
        Mockito.when(
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(maakGevondenPersonenLijst());

        Mockito.when(
                persoonMdlRepository
                        .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummerAanduiding(
                                "abcd"))).thenReturn(Arrays.asList(new PersoonModel(new PersoonBericht()), new PersoonModel(new PersoonBericht())));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagPersoonOpAdresBericht(), null,
                resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonMdlRepository, Mockito.times(0)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(LocatieOmschrijving.class),
                Matchers.any(LocatieTovAdres.class), Matchers.any(Plaats.class));
        Mockito.verify(persoonMdlRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
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

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "woonplaats",
                plaats);

        Mockito.when(
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        Mockito.when(
                persoonMdlRepository
                        .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummerAanduiding(
                                "abcd"))).thenReturn(personen);

        Mockito.when(
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer("12"), new Huisletter("A"), null, null, null, plaats))
                .thenReturn(Arrays.asList(new PersoonModel(new PersoonBericht()), new PersoonModel(new PersoonBericht())));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagPersoonOpAdresBericht(), null,
                resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonMdlRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                new NaamOpenbareRuimte("naamOpenbareRuimte"), new Huisnummer("12"), new Huisletter("A"), null, null,
                null, plaats);
        Mockito.verify(persoonMdlRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
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

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "woonplaats",
                plaats);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        // Vervolgens wordt met de persoon adres van de persoon gezocht op identificatiecodeNummerAanduiding
        Mockito.when(
                persoonMdlRepository
                        .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummerAanduiding(
                                "abcd"))).thenReturn(personen);

        Mockito.when(
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer("12"), new Huisletter("A"), null, null, null, plaats))
                .thenReturn(personen);

        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.when(
                persoonMdlRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.any(Postcode.class),
                        Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                        Matchers.any(Huisnummertoevoeging.class))).thenReturn(
                Arrays.asList(new PersoonModel(new PersoonBericht()), new PersoonModel(new PersoonBericht())));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123455"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonMdlRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                new NaamOpenbareRuimte("naamOpenbareRuimte"), new Huisnummer("12"), new Huisletter("A"), null, null,
                null, plaats);
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
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        // Vervolgens wordt met de persoon adres van de persoon gezocht op identificatiecodeNummerAanduiding
        Mockito.when(
                persoonMdlRepository
                        .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummerAanduiding(
                                "abcd"))).thenReturn(personen);

        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.when(
                persoonMdlRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.any(Postcode.class),
                        Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                        Matchers.any(Huisnummertoevoeging.class))).thenReturn(
                Arrays.asList(new PersoonModel(new PersoonBericht()), new PersoonModel(new PersoonBericht())));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123455"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonMdlRepository, Mockito.times(0)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(LocatieOmschrijving.class),
                Matchers.any(LocatieTovAdres.class), Matchers.any(Plaats.class));
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

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "woonplaats",
                plaats);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "postcode", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        // Vervolgens wordt met de persoon adres van de persoon gezocht op identificatiecodeNummerAanduiding
        Mockito.when(
                persoonMdlRepository
                        .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummerAanduiding(
                                "abcd"))).thenReturn(personen);

        Mockito.when(
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer("12"), new Huisletter("A"), null, null, null, plaats))
                .thenReturn(personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123455"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonMdlRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(LocatieOmschrijving.class),
                Matchers.any(LocatieTovAdres.class), Matchers.any(Plaats.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonMdlRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.any(Postcode.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class));
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is niet aanwezig. Er wordt gezocht met Volledige adres.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetVolledigeAdresStap1() {
        Plaats plaats = new Plaats();

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "woonplaats",
                plaats);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "postcode", null);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        Mockito.when(
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer("12"), new Huisletter("A"), null, null, null, plaats))
                .thenReturn(Arrays.asList(new PersoonModel(new PersoonBericht()), new PersoonModel(new PersoonBericht())));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123455"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonMdlRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                        Matchers.any(IdentificatiecodeNummerAanduiding.class));
        Mockito.verify(persoonMdlRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(LocatieOmschrijving.class),
                Matchers.any(LocatieTovAdres.class), Matchers.any(Plaats.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonMdlRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
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

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "woonplaats",
                plaats);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        Mockito.when(
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer("12"), new Huisletter("A"), null, null, null, plaats))
                .thenReturn(personen);

        Mockito.when(
                persoonMdlRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.any(Postcode.class),
                        Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                        Matchers.any(Huisnummertoevoeging.class))).thenReturn(
                Arrays.asList(new PersoonModel(new PersoonBericht()), new PersoonModel(new PersoonBericht())));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123455"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonMdlRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                        Matchers.any(IdentificatiecodeNummerAanduiding.class));
        Mockito.verify(persoonMdlRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(LocatieOmschrijving.class),
                Matchers.any(LocatieTovAdres.class), Matchers.any(Plaats.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonMdlRepository, Mockito.times(1)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
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

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "woonplaats",
                plaats);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(), "postcode", null);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getGegevens(),
                "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        Mockito.when(
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer("12"), new Huisletter("A"), null, null, null, plaats))
                .thenReturn(personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123455"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonMdlRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                        Matchers.any(IdentificatiecodeNummerAanduiding.class));
        Mockito.verify(persoonMdlRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(LocatieOmschrijving.class),
                Matchers.any(LocatieTovAdres.class), Matchers.any(Plaats.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonMdlRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
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
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        Mockito.when(
                persoonMdlRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.any(Postcode.class),
                        Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                        Matchers.any(Huisnummertoevoeging.class))).thenReturn(
                Arrays.asList(new PersoonModel(new PersoonBericht()), new PersoonModel(new PersoonBericht())));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123455"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonMdlRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                        Matchers.any(IdentificatiecodeNummerAanduiding.class));
        Mockito.verify(persoonMdlRepository, Mockito.times(0)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(LocatieOmschrijving.class),
                Matchers.any(LocatieTovAdres.class), Matchers.any(Plaats.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonMdlRepository, Mockito.times(1)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
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
                persoonMdlRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                        .any(Burgerservicenummer.class))).thenReturn(personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("123455"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonMdlRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                        Matchers.any(IdentificatiecodeNummerAanduiding.class));
        Mockito.verify(persoonMdlRepository, Mockito.times(0)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.any(NaamOpenbareRuimte.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class), Matchers.any(LocatieOmschrijving.class),
                Matchers.any(LocatieTovAdres.class), Matchers.any(Plaats.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonMdlRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.any(Postcode.class), Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                Matchers.any(Huisnummertoevoeging.class));
    }

    @Test
    public void testVraagOpPersonenOpAdresInclusiefBetrokkenhedenMetPostcode() {
        List<PersoonModel> personen = Arrays.asList(maakGevondenPersoon());
        Mockito.when(
                persoonMdlRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.any(Postcode.class),
                        Matchers.any(Huisnummer.class), Matchers.any(Huisletter.class),
                        Matchers.any(Huisnummertoevoeging.class))).thenReturn(personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setPostcode("1000AB");
        bericht.getVraag().getZoekCriteria().setHuisnummer("10");

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());
    }

    @Test
    public void testVraagOpPersonenOpAdresInclusiefBetrokkenhedenMetGemeenteCode() {
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().getZoekCriteria().setGemeenteCode("gemCode");
        bericht.getVraag().getZoekCriteria().setHuisnummer("10");
        bericht.getVraag().getZoekCriteria().setNaamOpenbareRuimte("straat");

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        // TODO Hosing: repository is nog niet geimplementeerd, tijdelijk geeft deze zoek criteria nog geen resultaat
        // terug.
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.INFO, resultaat.getMeldingen().get(0).getSoort());
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
        criteria.setGemeenteCode("gemCode");
        criteria.setHuisnummer("10");
        criteria.setNaamOpenbareRuimte("straat");
        criteria.setBurgerservicenummer(new Burgerservicenummer("12323"));

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        // TODO Hosing: repository is nog niet geimplementeerd, tijdelijk geeft deze zoek criteria nog geen resultaat
        // terug.
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.INFO, resultaat.getMeldingen().get(0).getSoort());
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
        ReflectionTestUtils.setField(criteria, "burgerservicenummer", new Burgerservicenummer("1234"));
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

    private ArrayList<PersoonModel> maakGevondenPersonenLijst() {
        ArrayList<PersoonModel> personen = new ArrayList<PersoonModel>();
        personen.add(maakGevondenPersoon());
        return personen;
    }

    private PersoonModel maakGevondenPersoon() {
        PersoonBericht persoon = new PersoonBericht();

        PersoonAdresStandaardGroep gegevens = new PersoonAdresStandaardGroepBericht();
        ReflectionTestUtils.setField(gegevens, "huisletter", new Huisletter("A"));
        ReflectionTestUtils.setField(gegevens, "huisnummer", new Huisnummer("12"));
        ReflectionTestUtils.setField(gegevens, "postcode", new Postcode("Postcode"));
        ReflectionTestUtils.setField(gegevens, "identificatiecodeNummeraanduiding",
                new IdentificatiecodeNummerAanduiding("abcd"));

        ReflectionTestUtils.setField(persoon, "soort", SoortPersoon.INGESCHREVENE);

        PersoonIdentificatieNummersGroepBericht idnrs = new PersoonIdentificatieNummersGroepBericht();
        ReflectionTestUtils.setField(idnrs, "burgerServiceNummer", new Burgerservicenummer("1234"));
        ReflectionTestUtils.setField(idnrs, "administratieNummer", new Administratienummer("1235"));
        ReflectionTestUtils.setField(persoon, "identificatieNummers", idnrs);

        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();
        ReflectionTestUtils.setField(geboorte, "datumGeboorte", new Datum(19800101));
        ReflectionTestUtils.setField(persoon, "geboorte", geboorte);

        PersoonGeslachtsAanduidingGroepBericht persGeslAand = new PersoonGeslachtsAanduidingGroepBericht();
        ReflectionTestUtils.setField(persGeslAand, "geslachtsAanduiding", GeslachtsAanduiding.MAN);
        ReflectionTestUtils.setField(persoon, "geslachtsAanduiding", persGeslAand);

        PersoonSamengesteldeNaamGroepBericht samengesteldNaam = new PersoonSamengesteldeNaamGroepBericht();
        ReflectionTestUtils.setField(samengesteldNaam, "geslachtsnaam", new GeslachtsnaamComponent("Pietersen"));
        ReflectionTestUtils.setField(persoon, "samengesteldeNaam", samengesteldNaam);

        // persoon.setGeslachtsNaam("M");
        // persoon.setVoornaam("voornaam");

        PersoonModel persoonMdl = new PersoonModel(persoon);

        Set<PersoonAdresModel> adressen = new HashSet<PersoonAdresModel>();

        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        ReflectionTestUtils.setField(persoonAdres, "persoon", persoon);
        ReflectionTestUtils.setField(persoonAdres, "gegevens", gegevens);

        adressen.add(new PersoonAdresModel(persoonAdres, persoonMdl));
        persoonMdl.getAdressen().addAll(adressen);

        Set<BetrokkenheidModel> persoonBetrokkenheden = new HashSet<BetrokkenheidModel>();

        RelatieBericht relatieWeb = new RelatieBericht();
        ReflectionTestUtils.setField(relatieWeb, "gegevens", new RelatieStandaardGroepBericht());
        RelatieModel huwelijk = new RelatieModel(relatieWeb);

        BetrokkenheidModel partner1 = new BetrokkenheidModel(new BetrokkenheidBericht(), persoonMdl, huwelijk);
        ReflectionTestUtils.setField(partner1, "rol", SoortBetrokkenheid.PARTNER);

        persoonBetrokkenheden.add(partner1);
        ReflectionTestUtils.setField(persoonMdl, "betrokkenheden", persoonBetrokkenheden);

        BetrokkenheidModel partner2 = new BetrokkenheidModel(new BetrokkenheidBericht(), new PersoonModel(new PersoonBericht()), huwelijk);
        // moeder als andere partner. !! (vast id = 12L)
        ReflectionTestUtils.setField(partner2, "rol", SoortBetrokkenheid.PARTNER);

        ReflectionTestUtils.setField(partner2.getBetrokkene(), "id", 12L);
        ReflectionTestUtils.setField(partner2.getBetrokkene(), "soort", SoortPersoon.INGESCHREVENE);

        HashSet<BetrokkenheidModel> huwlijkBetr = new HashSet<BetrokkenheidModel>();
        huwlijkBetr.add(partner1);
        huwlijkBetr.add(partner2);
        ReflectionTestUtils.setField(huwelijk, "soort", SoortRelatie.HUWELIJK);
        ReflectionTestUtils.setField(huwelijk, "betrokkenheden", huwlijkBetr);

        RelatieStandaardGroepBericht gegevensHuwelijk = new RelatieStandaardGroepBericht();
        ReflectionTestUtils.setField(gegevensHuwelijk, "datumAanvang", new Datum(20120101));
        ReflectionTestUtils.setField(huwelijk, "gegevens", new RelatieStandaardGroepModel(gegevensHuwelijk));


        ReflectionTestUtils.setField(persoonMdl, "betrokkenheden", persoonBetrokkenheden);
        ReflectionTestUtils.setField(persoonMdl, "id", 23654823L);

        return persoonMdl;
    }

    private PersoonModel maakPersistentPersoon() {
        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();

        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        PersoonAdresStandaardGroep gegevens = new PersoonAdresStandaardGroepBericht();
        ReflectionTestUtils.setField(gegevens, "huisletter", new Huisletter("A"));
        ReflectionTestUtils.setField(gegevens, "huisnummer", new Huisnummer("12"));
        ReflectionTestUtils.setField(gegevens, "postcode", new Postcode("Postcode"));
        ReflectionTestUtils.setField(persoonAdres, "gegevens", gegevens);

        adressen.add(persoonAdres);

        PersoonBericht persoon = new PersoonBericht();
        PersoonIdentificatieNummersGroepBericht idnrs = new PersoonIdentificatieNummersGroepBericht();
        ReflectionTestUtils.setField(idnrs, "burgerServiceNummer", new Burgerservicenummer("123456782"));
        ReflectionTestUtils.setField(idnrs, "administratieNummer", new Administratienummer("9234567821"));
        ReflectionTestUtils.setField(persoon, "identificatieNummers", idnrs);

        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();
        ReflectionTestUtils.setField(geboorte, "datumGeboorte", new Datum(19800101));
        ReflectionTestUtils.setField(persoon, "geboorte", geboorte);

        PersoonGeslachtsAanduidingGroep persGeslAand = new PersoonGeslachtsAanduidingGroepBericht();
        ReflectionTestUtils.setField(persGeslAand, "geslachtsAanduiding", GeslachtsAanduiding.VROUW);
        ReflectionTestUtils.setField(persoon, "geslachtsAanduiding", persGeslAand);

        PersoonSamengesteldeNaamGroep samengesteldNaam = new PersoonSamengesteldeNaamGroepBericht();
        ReflectionTestUtils.setField(samengesteldNaam, "geslachtsnaam", new GeslachtsnaamComponent("Pietersen"));
        ReflectionTestUtils.setField(persoon, "samengesteldeNaam", samengesteldNaam);

        // persoon.setVoornaam("Johanna Karel Marie");
        ReflectionTestUtils.setField(persoon, "adressen", adressen);

        PersoonModel persoonMdl = new PersoonModel(persoon);
        ReflectionTestUtils.setField(persoonMdl, "id", 12L);
        return persoonMdl;
    }

}
