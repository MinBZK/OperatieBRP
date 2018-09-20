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
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.dataaccess.selectie.RelatieSelectieFilter;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortPersoon;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.logisch.groep.PersoonIdentiteit;
import nl.bzk.brp.model.logisch.groep.PersoonSamengesteldeNaam;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonAdres;
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
    private PersoonRepository                 persoonRepository;
    @Mock
    private RelatieRepository                 relatieRepository;

    @Before
    public void init() {
        opvragenPersoonBerichtUitvoerStap = new OpvragenPersoonBerichtUitvoerStap();
        // fake the @inject annotations
        ReflectionTestUtils.setField(opvragenPersoonBerichtUitvoerStap, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(opvragenPersoonBerichtUitvoerStap, "relatieRepository", relatieRepository);
    }

    @Test
    public void testVraagOpDetailPersoonPersoonGevonden() {
        Mockito.when(persoonRepository.haalPersoonOpMetBurgerservicenummer(Matchers.anyString())).thenReturn(
                maakGevondenPersoon());

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagDetailsPersoonBericht(), null,
                resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Persoon gevondenPersoon = resultaat.getGevondenPersonen().iterator().next();
        PersoonAdres persoonAdres = gevondenPersoon.getAdressen().iterator().next();

        // Note: alleen velden die voorkomen in de XSD zijn hier getest
        Assert.assertEquals("1234", gevondenPersoon.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertEquals("1235", gevondenPersoon.getIdentificatienummers().getAdministratienummer());
        Assert.assertEquals(19800101, gevondenPersoon.getGeboorte().getDatumGeboorte().longValue());
        Assert.assertEquals(GeslachtsAanduiding.MAN, gevondenPersoon.getPersoonGeslachtsAanduiding()
                .getGeslachtsAanduiding());
        // Assert.assertEquals("M", gevondenPersoon.getGeslachtsNaam());
        Assert.assertEquals("12", persoonAdres.getHuisnummer());
        Assert.assertEquals("Postcode", persoonAdres.getPostcode());
        // Assert.assertEquals("voornaam", gevondenPersoon.getVoornamen());
    }

    @Test
    public void testVraagOpDetailPersoonGeenPersoonGevonden() {
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.anyString())).thenReturn(null);

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
        PersistentPersoon moeder = maakPersistentPersoon();
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.anyString())).thenReturn(moeder);
        Mockito.when(
                relatieRepository.haalopRelatiesVanPersoon(Matchers.anyLong(),
                        Matchers.any(RelatieSelectieFilter.class))).thenReturn(new ArrayList<Long>());
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagKandidaatVaderBericht(), null,
                resultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals("Kandidaat vader kan niet worden bepaald.", resultaat.getMeldingen().get(0).getOmschrijving());
        Assert.assertEquals(SoortMelding.INFO, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
        Assert.assertNull(resultaat.getGevondenPersonen());
    }

    @Test
    public void testVraagOpKandidaatVaderBsnIsGeenVrouw() {
        // fake dat de moeder is gevonden
        PersistentPersoon moeder = maakPersistentPersoon();
        moeder.setGeslachtsAanduiding(GeslachtsAanduiding.MAN);
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.anyString())).thenReturn(moeder);
        Mockito.when(
                relatieRepository.haalopRelatiesVanPersoon(Matchers.anyLong(),
                        Matchers.any(RelatieSelectieFilter.class))).thenReturn(new ArrayList<Long>());
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagKandidaatVaderBericht(), null,
                resultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals("De persoon is niet van het vrouwelijk geslacht.", resultaat.getMeldingen()
                .get(0).getOmschrijving());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testVraagPersonenOpAdresViaBsnNietGevonden() {
        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers.anyString()))
                .thenReturn(new ArrayList<Persoon>());
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
        PersistentPersoon moeder = maakPersistentPersoon();
        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.anyString())).thenReturn(moeder);
        List<Long> echtgenotenIds = Arrays.asList(new Long(8731137));
        Mockito.when(
                relatieRepository.haalopRelatiesVanPersoon(Matchers.any(java.lang.Long.class),
                        Matchers.any(RelatieSelectieFilter.class))).thenReturn(echtgenotenIds);
        Mockito.when(persoonRepository.haalPersoonMetAdres(Matchers.any(java.lang.Long.class))).thenReturn(
                maakGevondenPersoon());
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagKandidaatVaderBericht(), null,
                resultaat);
        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Persoon gevondenPersoon = resultaat.getGevondenPersonen().iterator().next();
        PersoonAdres persoonAdres = gevondenPersoon.getAdressen().iterator().next();
    }

    @Test
    public void testVraagPersonenOpAdresViaBsnZonderAdres() {
        List<Persoon> personen = maakGevondenPersonenLijst();
        personen.get(0).setAdressen(null);

        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers.anyString()))
                .thenReturn(personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagPersoonOpAdresBericht(), null,
                resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(Matchers.anyString());
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
                Matchers.anyString(), Matchers.anyString(), (Plaats) Matchers.anyObject());
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    @Test
    public void testVraagPersonenOpAdresViaBsnIdMetIdCodeAanduidingStap1() {
        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers.anyString()))
                .thenReturn(maakGevondenPersonenLijst());

        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding("abcd"))
                .thenReturn(Arrays.asList(new Persoon(), new Persoon()));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagPersoonOpAdresBericht(), null,
                resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
                Matchers.anyString(), Matchers.anyString(), (Plaats) Matchers.anyObject());
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is aanwezig en IdentificatiecodeNummeraanduiding levert
     * geen extra resultaten op, er wordt gezocht met volledige adres.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetIdCodeAanduidingStap2() {
        Plaats plaats = new Plaats();

        List<Persoon> personen = maakGevondenPersonenLijst();
        personen.get(0).getAdressen().iterator().next().setNaamOpenbareRuimte("naamOpenbareRuimte");
        personen.get(0).getAdressen().iterator().next().setWoonplaats(plaats);

        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers.anyString()))
                .thenReturn(personen);

        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding("abcd"))
                .thenReturn(personen);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres("naamOpenbareRuimte", "12", "A", null,
                        null, null, plaats)).thenReturn(Arrays.asList(new Persoon(), new Persoon()));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagPersoonOpAdresBericht(), null,
                resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                "naamOpenbareRuimte", "12", "A", null, null, null, plaats);
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is aanwezig en IdentificatiecodeNummeraanduiding levert
     * geen extra resultaten op, er wordt gezocht met volledige adres en volledige adres levert ook geen resultaten op.
     * Er wordt verder gezocht met postcode en huisnummer.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetIdCodeAanduidingStap2b() {
        Plaats plaats = new Plaats();

        List<Persoon> personen = maakGevondenPersonenLijst();
        personen.get(0).getAdressen().iterator().next().setNaamOpenbareRuimte("naamOpenbareRuimte");
        personen.get(0).getAdressen().iterator().next().setWoonplaats(plaats);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers.anyString()))
                .thenReturn(personen);

        // Vervolgens wordt met de persoon adres van de persoon gezocht op identificatiecodeNummerAanduiding
        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding("abcd"))
                .thenReturn(personen);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres("naamOpenbareRuimte", "12", "A", null,
                        null, null, plaats)).thenReturn(personen);

        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.when(
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.anyString(),
                        Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(
                Arrays.asList(new Persoon(), new Persoon()));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().setBurgerservicenummer("123455");

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                "naamOpenbareRuimte", "12", "A", null, null, null, plaats);
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is aanwezig en IdentificatiecodeNummeraanduiding levert
     * geen extra resultaten op, volledige adres is niet aanwezig, er wordt gezocht met postcode huisnummmer.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetIdCodeAanduidingStap3() {
        List<Persoon> personen = maakGevondenPersonenLijst();

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers.anyString()))
                .thenReturn(personen);

        // Vervolgens wordt met de persoon adres van de persoon gezocht op identificatiecodeNummerAanduiding
        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding("abcd"))
                .thenReturn(personen);

        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.when(
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.anyString(),
                        Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(
                Arrays.asList(new Persoon(), new Persoon()));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().setBurgerservicenummer("123455");

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
                Matchers.anyString(), Matchers.anyString(), (Plaats) Matchers.anyObject());
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

        List<Persoon> personen = maakGevondenPersonenLijst();
        personen.get(0).getAdressen().iterator().next().setNaamOpenbareRuimte("naamOpenbareRuimte");
        personen.get(0).getAdressen().iterator().next().setWoonplaats(plaats);
        personen.get(0).getAdressen().iterator().next().setPostcode(null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers.anyString()))
                .thenReturn(personen);

        // Vervolgens wordt met de persoon adres van de persoon gezocht op identificatiecodeNummerAanduiding
        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding("abcd"))
                .thenReturn(personen);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres("naamOpenbareRuimte", "12", "A", null,
                        null, null, plaats)).thenReturn(personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().setBurgerservicenummer("123455");

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
                Matchers.anyString(), Matchers.anyString(), (Plaats) Matchers.anyObject());
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is niet aanwezig. Er wordt gezocht met Volledige adres.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetVolledigeAdresStap1() {
        Plaats plaats = new Plaats();

        List<Persoon> personen = maakGevondenPersonenLijst();
        personen.get(0).getAdressen().iterator().next().setNaamOpenbareRuimte("naamOpenbareRuimte");
        personen.get(0).getAdressen().iterator().next().setWoonplaats(plaats);
        personen.get(0).getAdressen().iterator().next().setPostcode(null);
        personen.get(0).getAdressen().iterator().next().setIdentificatiecodeNummeraanduiding(null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers.anyString()))
                .thenReturn(personen);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres("naamOpenbareRuimte", "12", "A", null,
                        null, null, plaats)).thenReturn(Arrays.asList(new Persoon(), new Persoon()));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().setBurgerservicenummer("123455");

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(Matchers.anyString());
        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
                Matchers.anyString(), Matchers.anyString(), (Plaats) Matchers.anyObject());
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is niet aanwezig. Er wordt gezocht met Volledige adres.
     * Volledige adres levert geen resultaten op. Er wordt gezocht met postcode huisnummer.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetVolledigeAdresStap2() {
        Plaats plaats = new Plaats();

        List<Persoon> personen = maakGevondenPersonenLijst();
        personen.get(0).getAdressen().iterator().next().setNaamOpenbareRuimte("naamOpenbareRuimte");
        personen.get(0).getAdressen().iterator().next().setWoonplaats(plaats);
        personen.get(0).getAdressen().iterator().next().setIdentificatiecodeNummeraanduiding(null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers.anyString()))
                .thenReturn(personen);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres("naamOpenbareRuimte", "12", "A", null,
                        null, null, plaats)).thenReturn(personen);

        Mockito.when(
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.anyString(),
                        Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(
                Arrays.asList(new Persoon(), new Persoon()));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().setBurgerservicenummer("123455");

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(Matchers.anyString());
        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
                Matchers.anyString(), Matchers.anyString(), (Plaats) Matchers.anyObject());
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is niet aanwezig. Er wordt gezocht met Volledige adres.
     * Volledige adres levert geen resultaten op. Postcode huisnummer niet aanwezig, flow eindigd
     * zonder personen.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetVolledigeAdresStap2b() {
        Plaats plaats = new Plaats();

        List<Persoon> personen = maakGevondenPersonenLijst();
        personen.get(0).getAdressen().iterator().next().setNaamOpenbareRuimte("naamOpenbareRuimte");
        personen.get(0).getAdressen().iterator().next().setWoonplaats(plaats);
        personen.get(0).getAdressen().iterator().next().setPostcode(null);
        personen.get(0).getAdressen().iterator().next().setIdentificatiecodeNummeraanduiding(null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers.anyString()))
                .thenReturn(personen);

        Mockito.when(
                persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres("naamOpenbareRuimte", "12", "A", null,
                        null, null, plaats)).thenReturn(personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().setBurgerservicenummer("123455");

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(Matchers.anyString());
        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
                Matchers.anyString(), Matchers.anyString(), (Plaats) Matchers.anyObject());
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    /**
     * Test zoeken met BSN, IdentificatiecodeNummeraanduiding en volledige adres niet aanwezig. Er wordt gezocht met
     * postcode.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetPostcodehuisnummer() {
        List<Persoon> personen = maakGevondenPersonenLijst();
        personen.get(0).getAdressen().iterator().next().setIdentificatiecodeNummeraanduiding(null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers.anyString()))
                .thenReturn(personen);

        Mockito.when(
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.anyString(),
                        Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(
                Arrays.asList(new Persoon(), new Persoon()));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().setBurgerservicenummer("123455");

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(Matchers.anyString());
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
                Matchers.anyString(), Matchers.anyString(), (Plaats) Matchers.anyObject());
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    /**
     * Test zoeken met BSN, IdentificatiecodeNummeraanduiding en volledige adres niet aanwezig. Er postcode niet
     * aanwezig. Flow eindigd.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetPostcodehuisnummerNietAanwezig() {
        List<Persoon> personen = maakGevondenPersonenLijst();
        personen.get(0).getAdressen().iterator().next().setPostcode(null);
        personen.get(0).getAdressen().iterator().next().setIdentificatiecodeNummeraanduiding(null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(Matchers.anyString()))
                .thenReturn(personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().setBurgerservicenummer("123455");

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
                .haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(Matchers.anyString());
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenMetWoonAdresOpViaVolledigAdres(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
                Matchers.anyString(), Matchers.anyString(), (Plaats) Matchers.anyObject());
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersonenOpMetAdresViaPostcodeHuisnummer(
                Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    @Test
    public void testVraagOpPersonenOpAdresInclusiefBetrokkenhedenMetPostcode() {
        List<Persoon> personen = Arrays.asList(maakGevondenPersoon());
        Mockito.when(
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(Matchers.anyString(),
                        Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().setPostcode("1000AB");
        bericht.getVraag().setHuisnummer("10");

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
        bericht.getVraag().setGemeenteCode("gemCode");
        bericht.getVraag().setHuisnummer("10");
        bericht.getVraag().setNaamOpenbareRuimte("straat");

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
        bericht.getVraag().setGemeenteCode("gemCode");
        bericht.getVraag().setHuisnummer("10");
        bericht.getVraag().setNaamOpenbareRuimte("straat");
        bericht.getVraag().setBurgerservicenummer("12323");

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
        ReflectionTestUtils.setField(vraag, "burgerservicenummer", "1234");
        vraag.setGeboortedatumKind(20120101);
        VraagKandidaatVaderBericht bericht = new VraagKandidaatVaderBericht();
        ReflectionTestUtils.setField(bericht, "vraag", vraag);
        return bericht;
    }

    private VraagPersonenOpAdresInclusiefBetrokkenhedenBericht maakVraagPersoonOpAdresBericht() {
        PersonenOpAdresInclusiefBetrokkenhedenVraag vraag = new PersonenOpAdresInclusiefBetrokkenhedenVraag();
        ReflectionTestUtils.setField(vraag, "burgerservicenummer", "1234");
        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", vraag);
        return bericht;
    }

    private VraagDetailsPersoonBericht maakVraagDetailsPersoonBericht() {
        DetailsPersoonVraag vraag = new DetailsPersoonVraag();
        ReflectionTestUtils.setField(vraag, "burgerservicenummer", "1234");

        VraagDetailsPersoonBericht bericht = new VraagDetailsPersoonBericht();
        ReflectionTestUtils.setField(bericht, "vraag", vraag);

        return bericht;
    }

    private ArrayList<Persoon> maakGevondenPersonenLijst() {
        ArrayList<Persoon> personen = new ArrayList<Persoon>();
        personen.add(maakGevondenPersoon());
        return personen;
    }

    private Persoon maakGevondenPersoon() {
        Persoon persoon = new Persoon();

        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();

        PersoonAdres persoonAdres = new PersoonAdres();
        persoonAdres.setPersoon(persoon);
        persoonAdres.setHuisletter("A");
        persoonAdres.setHuisnummer("12");
        persoonAdres.setPostcode("Postcode");
        persoonAdres.setIdentificatiecodeNummeraanduiding("abcd");
        adressen.add(persoonAdres);


        persoon.setIdentiteit(new PersoonIdentiteit());
        persoon.getIdentiteit().setId(23654823L);
        persoon.getIdentiteit().setSoort(SoortPersoon.INGESCHREVENE);
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setAdministratienummer("1235");
        persoon.getIdentificatienummers().setBurgerservicenummer("1234");
        persoon.setGeboorte(new PersoonGeboorte());
        persoon.getGeboorte().setDatumGeboorte(19800101);
        persoon.setPersoonGeslachtsAanduiding(new PersoonGeslachtsAanduiding());
        persoon.getPersoonGeslachtsAanduiding().setGeslachtsAanduiding(GeslachtsAanduiding.MAN);
        persoon.setSamengesteldenaam(new PersoonSamengesteldeNaam());

        // persoon.setGeslachtsNaam("M");
        // persoon.setVoornaam("voornaam");
        persoon.setAdressen(adressen);

        Relatie huwelijk = new Relatie();

        Betrokkenheid partner1 = new Betrokkenheid();
        partner1.setRelatie(huwelijk);
        partner1.setBetrokkene(persoon);
        partner1.setSoortBetrokkenheid(SoortBetrokkenheid.PARTNER);
        persoon.getBetrokkenheden().add(partner1);

        Betrokkenheid partner2 = new Betrokkenheid();
        partner2.setRelatie(huwelijk);
        partner2.setSoortBetrokkenheid(SoortBetrokkenheid.PARTNER);
        // moeder als andere partner. !! (vast id = 12L)
        partner2.setBetrokkene(new Persoon());
        partner2.getBetrokkene().setIdentiteit(new PersoonIdentiteit());
        partner2.getBetrokkene().getIdentiteit().setId(12L);
        partner2.getBetrokkene().getIdentiteit().setSoort(SoortPersoon.INGESCHREVENE);

        huwelijk.setBetrokkenheden(new HashSet<Betrokkenheid>());
        huwelijk.getBetrokkenheden().add(partner1);
        huwelijk.getBetrokkenheden().add(partner2);
        huwelijk.setDatumAanvang(20120101);
        huwelijk.setSoortRelatie(SoortRelatie.HUWELIJK);
        return persoon;
    }

    private PersistentPersoon maakPersistentPersoon() {
        Set<PersistentPersoonAdres> adressen = new HashSet<PersistentPersoonAdres>();

        PersistentPersoonAdres persoonAdres = new PersistentPersoonAdres();
        persoonAdres.setHuisletter("A");
        persoonAdres.setHuisnummer("12");
        persoonAdres.setPostcode("Postcode");
        adressen.add(persoonAdres);

        PersistentPersoon persistentPersoon = new PersistentPersoon();
        persistentPersoon.setId(12L);
        persistentPersoon.setBurgerservicenummer("123456782");
        persistentPersoon.setANummer("9234567821");
        persistentPersoon.setDatumGeboorte(19800101);
        persistentPersoon.setGeslachtsAanduiding(GeslachtsAanduiding.VROUW);
        persistentPersoon.setGeslachtsNaam("Pietersen");
        persistentPersoon.setVoornaam("Johanna Karel Marie");
        persistentPersoon.setAdressen(adressen);

        return persistentPersoon;
    }

}
