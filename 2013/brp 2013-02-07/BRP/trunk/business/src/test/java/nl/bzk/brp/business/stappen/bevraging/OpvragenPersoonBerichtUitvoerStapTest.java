/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging;

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
import nl.bzk.brp.business.dto.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenBericht;
import nl.bzk.brp.business.dto.bevraging.zoekcriteria.ZoekCriteriaPersoonOpAdres;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ANummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisletter;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummertoevoeging;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Postcode;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkModel;
import nl.bzk.brp.model.operationeel.kern.PartnerModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsaanduidingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
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
import static org.mockito.MockitoAnnotations.initMocks;


@RunWith(MockitoJUnitRunner.class)
public class OpvragenPersoonBerichtUitvoerStapTest {

    @InjectMocks
    private final OpvragenPersoonBerichtUitvoerStap opvragenPersoonBerichtUitvoerStap =
            new OpvragenPersoonBerichtUitvoerStap();

    @Mock
    private PersoonRepository persoonRepository;
    @Mock
    private KandidaatVader kandidaatVader;

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
        Assert.assertEquals(Integer.valueOf(1234),
                            gevondenPersoon.getIdentificatienummers().getBurgerservicenummer().getWaarde());
        Assert.assertEquals("0000001235",
                            gevondenPersoon.getIdentificatienummers().getAdministratienummer().toString());
        Assert.assertEquals(19800101, gevondenPersoon.getGeboorte().getDatumGeboorte().getWaarde().longValue());
        Assert.assertEquals(Geslachtsaanduiding.MAN, gevondenPersoon.getGeslachtsaanduiding().getGeslachtsaanduiding());
        // Assert.assertEquals("M", gevondenPersoon.getGeslachtsNaam());
        Assert.assertEquals("12", persoonAdres.getStandaard().getHuisnummer().getWaarde().toString());
        Assert.assertEquals("Postcode", persoonAdres.getStandaard().getPostcode().getWaarde());
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
        Assert.assertEquals(SoortMelding.INFORMATIE, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
        Assert.assertNull(resultaat.getGevondenPersonen());
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
        Assert.assertEquals(SoortMelding.INFORMATIE, resultaat.getMeldingen().get(0).getSoort());
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
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class)))
                .thenReturn(moeder);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagKandidaatVaderBericht(),
                                                                            null,
                                                                            resultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals("De persoon is niet van het vrouwelijk geslacht.",
                            resultaat.getMeldingen().get(0).getOmschrijving());
        Assert.assertEquals(SoortMelding.FOUT, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testVraagPersonenOpAdresViaBsnNietGevonden() {
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(
                        Matchers.any(Burgerservicenummer.class)))
                .thenReturn(new ArrayList<PersoonModel>());
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagPersoonOpAdresBericht(),
                                                                            null,
                                                                            resultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.INFORMATIE, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
        Assert.assertNull(resultaat.getGevondenPersonen());
    }

    @Test
    public void testVraagPersonenOpAdresViaBsnZonderAdres() {
        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0), "adressen", null);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(
                        Matchers.any(Burgerservicenummer.class))).thenReturn(personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagPersoonOpAdresBericht(),
                                                                            null,
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
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(
                        Matchers.any(Burgerservicenummer.class)))
                .thenReturn(maakGevondenPersonenLijst());

        PersoonModel persoon1 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon1, "iD", 1);
        PersoonModel persoon2 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon2, "iD", 2);

        Mockito.when(
                persoonRepository
                        .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                                new IdentificatiecodeNummeraanduiding(
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
        Partij gemeente = StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM;
        Plaats plaats = StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM;

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(),
                                     "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(), "woonplaats",
                                     plaats);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(), "gemeente",
                                     gemeente);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(
                        Matchers.any(Burgerservicenummer.class)))
                .thenReturn(personen);

        Mockito.when(
                persoonRepository
                        .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                                new IdentificatiecodeNummeraanduiding(
                                        "abcd"))).thenReturn(personen);

        PersoonModel persoon1 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon1, "iD", 1);
        PersoonModel persoon2 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon2, "iD", 2);
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(
                        new NaamOpenbareRuimte("naamOpenbareRuimte"),
                        new Huisnummer(12),
                        new Huisletter("A"),
                        null,
                        plaats,
                        gemeente))
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
        Partij gemeente = StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM;
        Plaats plaats = StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM;

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(),
                                     "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(), "woonplaats",
                                     plaats);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(), "gemeente",
                                     gemeente);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                                                                                           .any(Burgerservicenummer.class)))
                .thenReturn(personen);

        // Vervolgens wordt met de persoon adres van de persoon gezocht op identificatiecodeNummeraanduiding
        Mockito.when(
                persoonRepository
                        .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                                new IdentificatiecodeNummeraanduiding(
                                        "abcd"))).thenReturn(personen);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer(12), new Huisletter("A"), null,
                                                                             plaats, gemeente))
                .thenReturn(personen);

        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        PersoonModel persoon1 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon1, "iD", 1);
        PersoonModel persoon2 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon2, "iD", 2);
        Mockito.when(
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.any(Postcode.class),
                                                                              Matchers.any(Huisnummer.class),
                                                                              Matchers.any(Huisletter.class),
                                                                              Matchers.any(Huisnummertoevoeging.class)))
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
                                                                                           .any(Burgerservicenummer.class)))
                .thenReturn(personen);

        // Vervolgens wordt met de persoon adres van de persoon gezocht op identificatiecodeNummeraanduiding
        Mockito.when(
                persoonRepository
                        .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                                new IdentificatiecodeNummeraanduiding(
                                        "abcd"))).thenReturn(personen);

        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        PersoonModel persoon1 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon1, "iD", 1);
        PersoonModel persoon2 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon2, "iD", 2);
        Mockito.when(
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.any(Postcode.class),
                                                                              Matchers.any(Huisnummer.class),
                                                                              Matchers.any(Huisletter.class),
                                                                              Matchers.any(Huisnummertoevoeging.class)))
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
        Partij gemeente = StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM;

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(),
                                     "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(), "woonplaats",
                                     StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(), "postcode", null);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(), "gemeente",
                                     gemeente);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                                                                                           .any(Burgerservicenummer.class)))
                .thenReturn(personen);

        // Vervolgens wordt met de persoon adres van de persoon gezocht op identificatiecodeNummeraanduiding
        Mockito.when(
                persoonRepository
                        .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                                new IdentificatiecodeNummeraanduiding(
                                        "abcd"))).thenReturn(personen);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer(12), new Huisletter("A"), null,
                                                                             StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM,
                                                                             gemeente))
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
        Partij gemeente = StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM;

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(),
                                     "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(), "woonplaats",
                                     StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(), "gemeente",
                                     gemeente);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(), "postcode", null);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(),
                                     "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                                                                                           .any(Burgerservicenummer.class)))
                .thenReturn(personen);

        PersoonModel persoon1 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon1, "iD", 1);
        PersoonModel persoon2 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon2, "iD", 2);
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer(12), new Huisletter("A"), null,
                                                                             StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM,
                                                                             gemeente))
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
        Partij gemeente = StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM;

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(),
                                     "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(), "woonplaats",
                                     StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(), "gemeente",
                                     gemeente);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(),
                                     "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                                                                                           .any(Burgerservicenummer.class)))
                .thenReturn(personen);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer(12), new Huisletter("A"), null,
                                                                             StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM,
                                                                             gemeente))
                .thenReturn(personen);

        PersoonModel persoon1 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon1, "iD", 1);
        PersoonModel persoon2 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon2, "iD", 2);
        Mockito.when(
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.any(Postcode.class),
                                                                              Matchers.any(Huisnummer.class),
                                                                              Matchers.any(Huisletter.class),
                                                                              Matchers.any(Huisnummertoevoeging.class)))
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
        Partij gemeente = StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM;

        List<PersoonModel> personen = maakGevondenPersonenLijst();
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(),
                                     "naamOpenbareRuimte", new NaamOpenbareRuimte("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(), "woonplaats",
                                     StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(), "gemeente",
                                     gemeente);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(), "postcode", null);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(),
                                     "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                                                                                           .any(Burgerservicenummer.class)))
                .thenReturn(personen);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimte(
                        "naamOpenbareRuimte"), new Huisnummer(12), new Huisletter("A"), null,
                                                                             StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM,
                                                                             gemeente))
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
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(),
                                     "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                                                                                           .any(Burgerservicenummer.class)))
                .thenReturn(personen);

        PersoonModel persoon1 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon1, "iD", 1);
        PersoonModel persoon2 = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoon2, "iD", 2);
        Mockito.when(
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.any(Postcode.class),
                                                                              Matchers.any(Huisnummer.class),
                                                                              Matchers.any(Huisletter.class),
                                                                              Matchers.any(Huisnummertoevoeging.class)))
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
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(), "postcode", null);
        ReflectionTestUtils.setField(personen.get(0).getAdressen().iterator().next().getStandaard(),
                                     "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers
                                                                                           .any(Burgerservicenummer.class)))
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
                                                                              Matchers.any(Huisnummer.class),
                                                                              Matchers.any(Huisletter.class),
                                                                              Matchers.any(Huisnummertoevoeging.class)))
                .thenReturn(personen);

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
        Assert.assertEquals(SoortMelding.INFORMATIE, resultaat.getMeldingen().get(0).getSoort());
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
        Assert.assertEquals(SoortMelding.INFORMATIE, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
        Assert.assertNull(resultaat.getGevondenPersonen());
    }

    private VraagKandidaatVaderBericht maakVraagKandidaatVaderBericht() {
        KandidaatVaderVraag vraag = new KandidaatVaderVraag();
        vraag.getZoekCriteria().setBurgerservicenummer(new Burgerservicenummer("1234"));
        vraag.getZoekCriteria().setGeboortedatumKind(new Datum(20120101));
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
        idnrs.setAdministratienummer(new ANummer(1235L));
        persoon.setIdentificatienummers(idnrs);

        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();
        geboorte.setDatumGeboorte(new Datum(19800101));
        persoon.setGeboorte(geboorte);

        PersoonGeslachtsaanduidingGroepBericht persGeslAand = new PersoonGeslachtsaanduidingGroepBericht();
        persGeslAand.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);
        persoon.setGeslachtsaanduiding(persGeslAand);

        PersoonSamengesteldeNaamGroepBericht samengesteldNaam = new PersoonSamengesteldeNaamGroepBericht();
        samengesteldNaam.setGeslachtsnaam(new Geslachtsnaam("Pietersen"));
        persoon.setSamengesteldeNaam(samengesteldNaam);

        PersoonModel persoonMdl = new PersoonModel(persoon);
        ReflectionTestUtils.setField(persoonMdl, "iD", 1);

        Set<PersoonAdresModel> adressen = new HashSet<PersoonAdresModel>();

        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setPersoon(persoon);
        persoonAdres.setStandaard(gegevens);

        adressen.add(new PersoonAdresModel(persoonAdres, persoonMdl));
        persoonMdl.getAdressen().addAll(adressen);

        Set<BetrokkenheidModel> persoonBetrokkenheden = new HashSet<BetrokkenheidModel>();


        HuwelijkBericht relatieBericht = new HuwelijkBericht();
        relatieBericht.setStandaard(new HuwelijkGeregistreerdPartnerschapStandaardGroepBericht());
        HuwelijkModel huwelijk = new HuwelijkModel(relatieBericht);
        PartnerBericht pb1 = new PartnerBericht();

        BetrokkenheidModel partner1 = new PartnerModel(pb1, huwelijk, persoonMdl);

        persoonBetrokkenheden.add(partner1);
        ReflectionTestUtils.setField(persoonMdl, "betrokkenheden", persoonBetrokkenheden);

        BetrokkenheidModel partner2 =
                new PartnerModel(new PartnerBericht(), huwelijk, new PersoonModel(new PersoonBericht()));
        // moeder als andere partner. !! (vast id = 12L)
        ReflectionTestUtils.setField(partner2.getPersoon(), "iD", 12);
        ReflectionTestUtils.setField(partner2.getPersoon(), "soort", SoortPersoon.INGESCHREVENE);

        Set<BetrokkenheidModel> huwlijkBetr = new HashSet<BetrokkenheidModel>();
        huwlijkBetr.add(partner1);
        huwlijkBetr.add(partner2);
        ReflectionTestUtils.setField(huwelijk, "betrokkenheden", huwlijkBetr);

        HuwelijkGeregistreerdPartnerschapStandaardGroepBericht gegevensHuwelijk =
                new HuwelijkGeregistreerdPartnerschapStandaardGroepBericht();
        gegevensHuwelijk.setDatumAanvang(new Datum(20120101));
        huwelijk.setStandaard(new HuwelijkGeregistreerdPartnerschapStandaardGroepModel(gegevensHuwelijk));

        ReflectionTestUtils.setField(persoonMdl, "betrokkenheden", persoonBetrokkenheden);
        ReflectionTestUtils.setField(persoonMdl, "iD", 23654823);

        return persoonMdl;
    }

    private PersoonModel maakPersoon() {
        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();

        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht adresGegevens = new PersoonAdresStandaardGroepBericht();
        adresGegevens.setHuisletter(new Huisletter("A"));
        adresGegevens.setHuisnummer(new Huisnummer(12));
        adresGegevens.setPostcode(new Postcode("Postcode"));
        persoonAdres.setStandaard(adresGegevens);

        adressen.add(persoonAdres);

        PersoonBericht persoon = new PersoonBericht();
        PersoonIdentificatienummersGroepBericht idnrs = new PersoonIdentificatienummersGroepBericht();
        idnrs.setBurgerservicenummer(new Burgerservicenummer("123456782"));
        idnrs.setAdministratienummer(new ANummer(9234567821L));
        persoon.setIdentificatienummers(idnrs);

        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();
        geboorte.setDatumGeboorte(new Datum(19800101));
        persoon.setGeboorte(geboorte);

        PersoonGeslachtsaanduidingGroepBericht persGeslAand = new PersoonGeslachtsaanduidingGroepBericht();
        persGeslAand.setGeslachtsaanduiding(Geslachtsaanduiding.VROUW);
        persoon.setGeslachtsaanduiding(persGeslAand);

        PersoonSamengesteldeNaamGroepBericht samengesteldNaam = new PersoonSamengesteldeNaamGroepBericht();
        samengesteldNaam.setGeslachtsnaam(new Geslachtsnaam("Pietersen"));
        persoon.setSamengesteldeNaam(samengesteldNaam);

        persoon.setAdressen(adressen);

        PersoonModel persoonModel = new PersoonModel(persoon);
        ReflectionTestUtils.setField(persoonModel, "iD", 12);
        return persoonModel;
    }
}
