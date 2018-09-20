/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.CorrectieAdresBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.Nationaliteitcode;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.RedenVerkrijgingCode;
import nl.bzk.brp.model.attribuuttype.RedenVerliesNaam;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonNationaliteitBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.PersoonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BerichtVerrijkingsStapTest extends AbstractStapTest {

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    private BerichtVerrijkingsStap   berichtVerrijkingsStap;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        berichtVerrijkingsStap = new BerichtVerrijkingsStap();
        ReflectionTestUtils.setField(berichtVerrijkingsStap, "referentieRepository", referentieDataRepository);
    }

    @Test
    public void testVerhuizing() {
        BerichtResultaat res = new BerichtResultaat(new ArrayList<Melding>());
        final VerhuizingBericht verhuisBericht = new VerhuizingBericht();
        ActieBericht verhuisActie = new ActieBericht();
        verhuisActie.setSoort(SoortActie.VERHUIZING);
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404, new Partij(), "Piet", "van",
                    "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        rootObjecten.add(pers);
        verhuisActie.setRootObjecten(rootObjecten);
        verhuisBericht.setBrpActies(new ArrayList<Actie>());
        verhuisBericht.getBrpActies().add(verhuisActie);

        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setGegevens(standaard);
        pers.getAdressen().add(adres);

        standaard.setGemeentecode(new Gemeentecode((short) 34));
        standaard.setLandcode(new Landcode((short) 2));
        standaard.setRedenwijzigingCode(new RedenWijzigingAdresCode("foo"));
        standaard.setWoonplaatsCode(new PlaatsCode((short) 324));

        Assert.assertTrue(berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht,
                bouwBerichtContext(""), res));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindGemeenteOpCode(Matchers.any(Gemeentecode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindWoonplaatsOpCode(Matchers.any(PlaatsCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindLandOpCode(Matchers.any(Landcode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindRedenWijzingAdresOpCode(
                Matchers.any(RedenWijzigingAdresCode.class));
    }

    @Test
    public void testVerhuizingGeenVerrijkingNodig() {
        BerichtResultaat res = new BerichtResultaat(new ArrayList<Melding>());
        final VerhuizingBericht verhuisBericht = new VerhuizingBericht();
        ActieBericht verhuisActie = new ActieBericht();
        verhuisActie.setSoort(SoortActie.VERHUIZING);
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404, new Partij(), "Piet", "van",
                    "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        rootObjecten.add(pers);
        verhuisActie.setRootObjecten(rootObjecten);
        verhuisBericht.setBrpActies(new ArrayList<Actie>());
        verhuisBericht.getBrpActies().add(verhuisActie);

        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setGegevens(standaard);
        pers.getAdressen().add(adres);

        Assert.assertTrue(berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht,
                bouwBerichtContext(""), res));
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindGemeenteOpCode(Matchers.any(Gemeentecode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindWoonplaatsOpCode(Matchers.any(PlaatsCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindLandOpCode(BrpConstanten.NL_LAND_CODE);
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindRedenWijzingAdresOpCode(
                Matchers.any(RedenWijzigingAdresCode.class));
    }

    @Test
    public void testCorrectieAdresNL() {
        BerichtResultaat res = new BerichtResultaat(new ArrayList<Melding>());
        final CorrectieAdresBericht correctieAdresBericht = new CorrectieAdresBericht();
        ActieBericht correactieAdresActie = new ActieBericht();
        correactieAdresActie.setSoort(SoortActie.CORRECTIE_ADRES_NL);
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404, new Partij(), "Piet", "van",
                    "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        rootObjecten.add(pers);
        correactieAdresActie.setRootObjecten(rootObjecten);
        correctieAdresBericht.setBrpActies(new ArrayList<Actie>());
        correctieAdresBericht.getBrpActies().add(correactieAdresActie);

        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setGegevens(standaard);
        pers.getAdressen().add(adres);

        standaard.setGemeentecode(new Gemeentecode((short) 34));
        standaard.setLandcode(new Landcode((short) 2));
        standaard.setRedenwijzigingCode(new RedenWijzigingAdresCode("foo"));
        standaard.setWoonplaatsCode(new PlaatsCode((short) 324));

        Assert.assertTrue(berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(correctieAdresBericht,
                bouwBerichtContext(""), res));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindGemeenteOpCode(Matchers.any(Gemeentecode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindWoonplaatsOpCode(Matchers.any(PlaatsCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindLandOpCode(Matchers.any(Landcode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindRedenWijzingAdresOpCode(
                Matchers.any(RedenWijzigingAdresCode.class));
    }

    @Test
    public void testCorrectieAdresNLGeenVerrijkingNodig() {
        BerichtResultaat res = new BerichtResultaat(new ArrayList<Melding>());
        final CorrectieAdresBericht correctieAdresBericht = new CorrectieAdresBericht();
        ActieBericht correactieAdresActie = new ActieBericht();
        correactieAdresActie.setSoort(SoortActie.CORRECTIE_ADRES_NL);
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404, new Partij(), "Piet", "van",
                    "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        rootObjecten.add(pers);
        correactieAdresActie.setRootObjecten(rootObjecten);
        correctieAdresBericht.setBrpActies(new ArrayList<Actie>());
        correctieAdresBericht.getBrpActies().add(correactieAdresActie);

        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setGegevens(standaard);
        pers.getAdressen().add(adres);

        Assert.assertTrue(berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(correctieAdresBericht,
                bouwBerichtContext(""), res));
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindGemeenteOpCode(Matchers.any(Gemeentecode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindWoonplaatsOpCode(Matchers.any(PlaatsCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindLandOpCode(BrpConstanten.NL_LAND_CODE);
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindRedenWijzingAdresOpCode(
                Matchers.any(RedenWijzigingAdresCode.class));
    }

    @Test
    public void testEersteInschrijving() {
        final BerichtResultaat res = new BerichtResultaat(new ArrayList<Melding>());
        final InschrijvingGeboorteBericht bericht = new InschrijvingGeboorteBericht();
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.AANGIFTE_GEBOORTE);
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        actie.setRootObjecten(rootObjecten);
        RelatieBericht relatie = new RelatieBericht();
        rootObjecten.add(relatie);
        bericht.setBrpActies(new ArrayList<Actie>());
        bericht.getBrpActies().add(actie);

        RelatieStandaardGroepBericht relatieStandaardGroep = new RelatieStandaardGroepBericht();
        relatie.setGegevens(relatieStandaardGroep);

        PersoonBericht kind =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404, new Partij(), "Piet", "van",
                    "Veldhuijsen");
        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();

        geboorte.setGemeenteGeboorteCode(new Gemeentecode((short) 34));
        geboorte.setWoonplaatsGeboorteCode(new PlaatsCode((short) 243));
        geboorte.setLandGeboorteCode(new Landcode((short) 2));

        kind.setGeboorte(geboorte);
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        BetrokkenheidBericht betr = new BetrokkenheidBericht();
        betr.setRol(SoortBetrokkenheid.KIND);
        betr.setBetrokkene(kind);
        relatie.getBetrokkenheden().add(betr);

        relatieStandaardGroep.setLandAanvangCode(new Landcode((short) 2));
        relatieStandaardGroep.setLandEindeCode(new Landcode((short) 2));

        relatieStandaardGroep.setGemeenteAanvangCode(new Gemeentecode((short) 34));
        relatieStandaardGroep.setGemeenteEindeCode(new Gemeentecode((short) 34));

        relatieStandaardGroep.setWoonPlaatsAanvangCode(new PlaatsCode((short) 243));
        relatieStandaardGroep.setWoonPlaatsEindeCode(new PlaatsCode((short) 243));

        ReflectionTestUtils.setField(kind.getGeslachtsnaamcomponenten().get(0).getGegevens(), "predikaatCode",
                new PredikaatCode("pred"));
        ReflectionTestUtils.setField(kind.getGeslachtsnaamcomponenten().get(0).getGegevens(), "adellijkeTitelCode",
                new AdellijkeTitelCode("adel"));

        Assert.assertTrue(berichtVerrijkingsStap
                .voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(""), res));

        Mockito.verify(referentieDataRepository, Mockito.times(3)).vindGemeenteOpCode(Matchers.any(Gemeentecode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(3)).vindWoonplaatsOpCode(Matchers.any(PlaatsCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(3)).vindLandOpCode(Matchers.any(Landcode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindPredikaatOpCode(
                Matchers.any(PredikaatCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindAdellijkeTitelOpCode(
                Matchers.any(AdellijkeTitelCode.class));
    }

    @Test
    public void testEersteInschrijvingGeenVerrijkingNodig() {
        final BerichtResultaat res = new BerichtResultaat(new ArrayList<Melding>());
        final InschrijvingGeboorteBericht bericht = new InschrijvingGeboorteBericht();
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.AANGIFTE_GEBOORTE);
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        actie.setRootObjecten(rootObjecten);
        RelatieBericht relatie = new RelatieBericht();
        rootObjecten.add(relatie);
        bericht.setBrpActies(new ArrayList<Actie>());
        bericht.getBrpActies().add(actie);

        RelatieStandaardGroepBericht relatieStandaardGroep = new RelatieStandaardGroepBericht();
        relatie.setGegevens(relatieStandaardGroep);

        PersoonBericht kind =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404, new Partij(), "Piet", "van",
                    "Veldhuijsen");
        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();

        kind.setGeboorte(geboorte);
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        BetrokkenheidBericht betr = new BetrokkenheidBericht();
        betr.setRol(SoortBetrokkenheid.KIND);
        betr.setBetrokkene(kind);
        relatie.getBetrokkenheden().add(betr);

        Assert.assertTrue(berichtVerrijkingsStap
                .voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(""), res));

        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindGemeenteOpCode(Matchers.any(Gemeentecode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindWoonplaatsOpCode(Matchers.any(PlaatsCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindLandOpCode(Matchers.any(Landcode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindPredikaatOpCode(
                Matchers.any(PredikaatCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindAdellijkeTitelOpCode(
                Matchers.any(AdellijkeTitelCode.class));
    }

    @Test
    public void testEersteInschrijvingOnbekendeReferentie() {
        final BerichtResultaat res = new BerichtResultaat(new ArrayList<Melding>());
        final InschrijvingGeboorteBericht bericht = new InschrijvingGeboorteBericht();
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.AANGIFTE_GEBOORTE);
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        actie.setRootObjecten(rootObjecten);
        RelatieBericht relatie = new RelatieBericht();
        rootObjecten.add(relatie);
        bericht.setBrpActies(new ArrayList<Actie>());
        bericht.getBrpActies().add(actie);

        RelatieStandaardGroepBericht relatieStandaardGroep = new RelatieStandaardGroepBericht();
        relatie.setGegevens(relatieStandaardGroep);

        PersoonBericht kind =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404, new Partij(), "Piet", "van",
                    "Veldhuijsen");
        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();

        geboorte.setGemeenteGeboorteCode(new Gemeentecode((short) 34));
        geboorte.setWoonplaatsGeboorteCode(new PlaatsCode((short) 243));
        geboorte.setLandGeboorteCode(new Landcode((short) 2));

        kind.setGeboorte(geboorte);
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        BetrokkenheidBericht betr = new BetrokkenheidBericht();
        betr.setRol(SoortBetrokkenheid.KIND);
        betr.setBetrokkene(kind);
        relatie.getBetrokkenheden().add(betr);

        relatieStandaardGroep.setLandAanvangCode(new Landcode((short) 2));
        relatieStandaardGroep.setLandEindeCode(new Landcode((short) 2));

        relatieStandaardGroep.setGemeenteAanvangCode(new Gemeentecode((short) 34));
        relatieStandaardGroep.setGemeenteEindeCode(new Gemeentecode((short) 34));

        relatieStandaardGroep.setWoonPlaatsAanvangCode(new PlaatsCode((short) 243));
        relatieStandaardGroep.setWoonPlaatsEindeCode(new PlaatsCode((short) 243));

        ReflectionTestUtils.setField(kind.getGeslachtsnaamcomponenten().get(0).getGegevens(), "predikaatCode",
                new PredikaatCode("pred"));
        ReflectionTestUtils.setField(kind.getGeslachtsnaamcomponenten().get(0).getGegevens(), "adellijkeTitelCode",
                new AdellijkeTitelCode("adel"));

        Mockito.when(referentieDataRepository.vindWoonplaatsOpCode(Matchers.any(PlaatsCode.class))).thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PLAATSCODE, "foo",
                        new Exception()));

        Mockito.when(referentieDataRepository.vindGemeenteOpCode(Matchers.any(Gemeentecode.class))).thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE, "foo",
                        new Exception()));

        Mockito.when(referentieDataRepository.vindLandOpCode(Matchers.any(Landcode.class))).thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDCODE, "foo",
                        new Exception()));

        Mockito.when(referentieDataRepository.vindPredikaatOpCode(Matchers.any(PredikaatCode.class))).thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PREDIKAAT, "foo",
                        new Exception()));

        Mockito.when(referentieDataRepository.vindAdellijkeTitelOpCode(Matchers.any(AdellijkeTitelCode.class)))
                .thenThrow(
                        new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.ADELLIJKETITEL,
                                "foo", new Exception()));

        Assert.assertFalse(berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(""),
                res));

        Assert.assertEquals(11, res.getMeldingen().size());
        Assert.assertEquals(MeldingCode.BRAL1001, res.getMeldingen().get(0).getCode());
        Assert.assertEquals(MeldingCode.BRAL1001, res.getMeldingen().get(1).getCode());
        Assert.assertEquals(MeldingCode.BRAL1001, res.getMeldingen().get(2).getCode());
        Assert.assertEquals("Land 2 bestaat niet.", res.getMeldingen().get(2).getOmschrijving());
        Assert.assertEquals(MeldingCode.BRAL1002, res.getMeldingen().get(3).getCode());
        Assert.assertEquals(MeldingCode.BRAL1002, res.getMeldingen().get(4).getCode());
        Assert.assertEquals(MeldingCode.BRAL1002, res.getMeldingen().get(5).getCode());
        Assert.assertEquals("Gemeente 34 bestaat niet.", res.getMeldingen().get(5).getOmschrijving());
        Assert.assertEquals(MeldingCode.BRAL1015, res.getMeldingen().get(6).getCode());
        Assert.assertEquals("Adellijke titel adel bestaat niet.", res.getMeldingen().get(6).getOmschrijving());
        Assert.assertEquals(MeldingCode.BRAL1018, res.getMeldingen().get(7).getCode());
        Assert.assertEquals("Predikaat pred bestaat niet.", res.getMeldingen().get(7).getOmschrijving());
        Assert.assertEquals(MeldingCode.BRAL1020, res.getMeldingen().get(8).getCode());
        Assert.assertEquals(MeldingCode.BRAL1020, res.getMeldingen().get(9).getCode());
        Assert.assertEquals(MeldingCode.BRAL1020, res.getMeldingen().get(10).getCode());
        Assert.assertEquals("Woonplaats 243 bestaat niet.", res.getMeldingen().get(10).getOmschrijving());
    }

    @Test
    public void testOnbekendeReferentieExceptionAlsResultaat() {
        BerichtResultaat res = new BerichtResultaat(new ArrayList<Melding>());
        final VerhuizingBericht verhuisBericht = new VerhuizingBericht();
        ActieBericht verhuisActie = new ActieBericht();
        verhuisActie.setSoort(SoortActie.VERHUIZING);
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404, new Partij(), "Piet", "van",
                    "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        rootObjecten.add(pers);
        verhuisActie.setRootObjecten(rootObjecten);
        verhuisBericht.setBrpActies(new ArrayList<Actie>());
        verhuisBericht.getBrpActies().add(verhuisActie);

        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setGegevens(standaard);
        pers.getAdressen().add(adres);

        standaard.setWoonplaatsCode(new PlaatsCode((short) 243));
        standaard.setGemeentecode(new Gemeentecode((short) 243));
        standaard.setRedenwijzigingCode(new RedenWijzigingAdresCode("abc"));

        Mockito.when(referentieDataRepository.vindWoonplaatsOpCode(Matchers.any(PlaatsCode.class))).thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PLAATSCODE, "foo",
                        new Exception()));

        Mockito.when(referentieDataRepository.vindGemeenteOpCode(Matchers.any(Gemeentecode.class))).thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE, "foo",
                        new Exception()));

        Mockito.when(referentieDataRepository.vindLandOpCode(Matchers.any(Landcode.class))).thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDCODE, "foo",
                        new Exception()));

        Mockito.when(referentieDataRepository.vindRedenWijzingAdresOpCode(Matchers.any(RedenWijzigingAdresCode.class)))
                .thenThrow(
                        new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENWIJZINGADRES,
                                "foo", new Exception()));

        Assert.assertFalse(berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht,
                bouwBerichtContext(""), res));

        Assert.assertEquals(4, res.getMeldingen().size());
        Assert.assertEquals(MeldingCode.BRAL1001, res.getMeldingen().get(0).getCode());
        Assert.assertEquals(MeldingCode.BRAL1002, res.getMeldingen().get(1).getCode());
        Assert.assertEquals(MeldingCode.BRAL1007, res.getMeldingen().get(2).getCode());
        Assert.assertEquals("Reden wijziging adres abc bestaat niet.", res.getMeldingen().get(2).getOmschrijving());
        Assert.assertEquals(MeldingCode.BRAL1020, res.getMeldingen().get(3).getCode());
    }

    @Test
    public void testRegistratieNationaliteit() {
        BerichtResultaat res = new BerichtResultaat(new ArrayList<Melding>());
        final VerhuizingBericht verhuisBericht = new VerhuizingBericht();
        ActieBericht verhuisActie = new ActieBericht();
        verhuisActie.setSoort(SoortActie.REGISTRATIE_NATIONALITEIT);
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404, new Partij(), "Piet", "van",
                    "Veldhuijsen");
        rootObjecten.add(pers);
        verhuisActie.setRootObjecten(rootObjecten);
        verhuisBericht.setBrpActies(new ArrayList<Actie>());
        verhuisBericht.getBrpActies().add(verhuisActie);
        pers.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        PersoonNationaliteitBericht persNation = new PersoonNationaliteitBericht();
        pers.getNationaliteiten().add(persNation);
        persNation.setNationaliteitcode(new Nationaliteitcode((short) 2));
        PersoonNationaliteitStandaardGroepBericht gegevens = new PersoonNationaliteitStandaardGroepBericht();
        gegevens.setRedenVerkregenNlNationaliteitCode(new RedenVerkrijgingCode((short) 10));
        gegevens.setRedenVerliesNlNationaliteitNaam(new RedenVerliesNaam("bar"));
        persNation.setGegevens(gegevens);

        Assert.assertTrue(berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht,
                bouwBerichtContext(""), res));

        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindNationaliteitOpCode(
                Matchers.any(Nationaliteitcode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindRedenVerkregenNlNationaliteitOpCode(
                Matchers.any(RedenVerkrijgingCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindRedenVerliesNLNationaliteitOpNaam(
                Matchers.any(RedenVerliesNaam.class));
    }

    @Test
    public void testRegistratieNationaliteitOnbekendeReferentie() {
        BerichtResultaat res = new BerichtResultaat(new ArrayList<Melding>());
        final VerhuizingBericht verhuisBericht = new VerhuizingBericht();
        ActieBericht verhuisActie = new ActieBericht();
        verhuisActie.setSoort(SoortActie.REGISTRATIE_NATIONALITEIT);
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404, new Partij(), "Piet", "van",
                    "Veldhuijsen");
        rootObjecten.add(pers);
        verhuisActie.setRootObjecten(rootObjecten);
        verhuisBericht.setBrpActies(new ArrayList<Actie>());
        verhuisBericht.getBrpActies().add(verhuisActie);
        pers.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        PersoonNationaliteitBericht persNation = new PersoonNationaliteitBericht();
        pers.getNationaliteiten().add(persNation);
        persNation.setNationaliteitcode(new Nationaliteitcode((short) 2));
        PersoonNationaliteitStandaardGroepBericht gegevens = new PersoonNationaliteitStandaardGroepBericht();
        gegevens.setRedenVerkregenNlNationaliteitCode(new RedenVerkrijgingCode((short) 10));
        gegevens.setRedenVerliesNlNationaliteitNaam(new RedenVerliesNaam("bar"));
        persNation.setGegevens(gegevens);

        Mockito.when(referentieDataRepository.vindNationaliteitOpCode(Matchers.any(Nationaliteitcode.class)))
                .thenThrow(
                        new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.NATIONALITITEITCODE,
                                "foo", new Exception()));

        Mockito.when(
                referentieDataRepository.vindRedenVerkregenNlNationaliteitOpCode(Matchers
                        .any(RedenVerkrijgingCode.class))).thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENVERKRIJGENNLNATION,
                                "foo", new Exception()));

        Mockito.when(
                referentieDataRepository.vindRedenVerliesNLNationaliteitOpNaam(Matchers.any(RedenVerliesNaam.class)))
                .thenThrow(
                        new OnbekendeReferentieExceptie(
                                OnbekendeReferentieExceptie.ReferentieVeld.REDENVERLIESNLNATION, "foo", new Exception()));

        Assert.assertFalse(berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht,
                bouwBerichtContext(""), res));

        Assert.assertEquals(3, res.getMeldingen().size());
        Assert.assertEquals(MeldingCode.BRAL1017, res.getMeldingen().get(0).getCode());
        Assert.assertEquals("Nationaliteit 2 bestaat niet.", res.getMeldingen().get(0).getOmschrijving());
        Assert.assertEquals(MeldingCode.BRAL1021, res.getMeldingen().get(1).getCode());
        Assert.assertEquals("Reden verkrijging Nederlandse nationaliteit 10 bestaat niet.", res.getMeldingen().get(1)
                .getOmschrijving());
        Assert.assertEquals(MeldingCode.BRAL1022, res.getMeldingen().get(2).getCode());
        Assert.assertEquals("Reden verlies Nederlandse nationaliteit bar bestaat niet.", res.getMeldingen().get(2)
                .getOmschrijving());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testOngeldigeRootObject() {
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(new RootObject() {
        });

        ActieBericht verhuisActie = new ActieBericht();
        verhuisActie.setSoort(SoortActie.VERHUIZING);
        verhuisActie.setRootObjecten(rootObjecten);

        final VerhuizingBericht verhuisBericht = new VerhuizingBericht();
        verhuisBericht.setBrpActies(new ArrayList<Actie>());
        verhuisBericht.getBrpActies().add(verhuisActie);

        BerichtResultaat res = new BerichtResultaat(new ArrayList<Melding>());
        berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht, bouwBerichtContext(""), res);
    }
}
