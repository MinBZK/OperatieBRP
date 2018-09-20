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
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.logisch.groep.PersoonSamengesteldeNaam;
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

    @Before
    public void init() {
        opvragenPersoonBerichtUitvoerStap = new OpvragenPersoonBerichtUitvoerStap();

        ReflectionTestUtils.setField(opvragenPersoonBerichtUitvoerStap, "persoonRepository", persoonRepository);
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
        Mockito.when(persoonRepository.haalPotentieleVaderViaBsnMoeder(Matchers.anyString())).thenReturn(
                new ArrayList<Persoon>());
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagKandidaatVaderBericht(), null,
                resultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.INFO, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
        Assert.assertNull(resultaat.getGevondenPersonen());
    }

    @Test
    public void testVraagOpKandidaatVader() {
        Mockito.when(persoonRepository.haalPotentieleVaderViaBsnMoeder(Matchers.anyString())).thenReturn(
                maakGevondenPersonenLijst());
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagKandidaatVaderBericht(), null,
                resultaat);
        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Persoon gevondenPersoon = resultaat.getGevondenPersonen().iterator().next();
        PersoonAdres persoonAdres = gevondenPersoon.getAdressen().iterator().next();
    }

    @Test
    public void testVraagPersonenOpAdresViaBsnNietGevonden() {
        Mockito.when(persoonRepository.haalPersonenMetAdresOpViaBurgerservicenummer(Matchers.anyString())).thenReturn(
                new ArrayList<Persoon>());
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagPersoonOpAdresBericht(), null,
                resultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.INFO, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
        Assert.assertNull(resultaat.getGevondenPersonen());
    }

    @Test
    public void testVraagPersonenOpAdresViaBsn() {
        Mockito.when(persoonRepository.haalPersonenMetAdresOpViaBurgerservicenummer(Matchers.anyString())).thenReturn(
                maakGevondenPersonenLijst());
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());
        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(maakVraagPersoonOpAdresBericht(), null,
                resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Persoon gevondenPersoon = resultaat.getGevondenPersonen().iterator().next();
        PersoonAdres persoonAdres = gevondenPersoon.getAdressen().iterator().next();
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

    @Test
    public void testVraagOpPersonenOpAdresInclusiefBetrokkenhedenMetBsn() {
        List<Persoon> personen = Arrays.asList(maakGevondenPersoon());
        Mockito.when(persoonRepository.haalPersonenMetAdresOpViaBurgerservicenummer(Matchers.anyString())).thenReturn(
                personen);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(new ArrayList<Melding>());

        VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht =
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht();
        ReflectionTestUtils.setField(bericht, "vraag", new PersonenOpAdresInclusiefBetrokkenhedenVraag());
        bericht.getVraag().setBurgerservicenummer("123455");

        opvragenPersoonBerichtUitvoerStap.voerVerwerkingsStapUitVoorBericht(bericht, null, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());
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
        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();

        PersoonAdres persoonAdres = new PersoonAdres();
        persoonAdres.setHuisletter("A");
        persoonAdres.setHuisnummer("12");
        persoonAdres.setPostcode("Postcode");
        adressen.add(persoonAdres);

        Persoon persoon = new Persoon();
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

        return persoon;
    }

}
