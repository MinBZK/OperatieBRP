/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bijhouding;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.CorrectieAdresBericht;
import nl.bzk.brp.business.dto.bijhouding.HuwelijkBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.business.stappen.AbstractStapTest;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nationaliteitcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredikaatCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerliesCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingAdresCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.ActieCorrectieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieHuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingErkenningOngeborenVruchtBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BerichtVerrijkingsStapTest extends AbstractStapTest {

    private static final String                  GEM_34      = "0034";
    private static final String                  LAND_2      = "0002";
    private static final String                  PLAATS_234  = "0234";
    private static final RedenVerkrijgingCode    VERKRIJG_10 = new RedenVerkrijgingCode("0010");
    private static final RedenVerliesCode        VERLIES_07  = new RedenVerliesCode("0007");
    private static final RedenWijzigingAdresCode WIJZADRES_P = new RedenWijzigingAdresCode("P");

    @Mock
    private ReferentieDataRepository             referentieDataRepository;

    private BerichtVerrijkingsStap               berichtVerrijkingsStap;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        berichtVerrijkingsStap = new BerichtVerrijkingsStap();
        ReflectionTestUtils.setField(berichtVerrijkingsStap, "referentieRepository", referentieDataRepository);
    }

    @Test
    public void testVerhuizing() {
        BerichtVerwerkingsResultaat res = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        final VerhuizingBericht verhuisBericht = new VerhuizingBericht();

        ActieBronBericht bron = new ActieBronBericht();
        bron.setDocument(new DocumentBericht());
        bron.getDocument().setSoortNaam("abc");
        bron.getDocument().setStandaard(new DocumentStandaardGroepBericht());
        bron.getDocument().getStandaard().setPartijCode(GEM_34);

        ActieBericht verhuisActie = new ActieRegistratieAdresBericht();
        verhuisActie.setBronnen(new ArrayList<ActieBronBericht>());
        verhuisActie.getBronnen().add(bron);

        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Piet", "van", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        rootObjecten.add(pers);
        verhuisActie.setRootObjecten(rootObjecten);
        verhuisBericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        verhuisBericht.getAdministratieveHandeling().setPartij(
                new Partij(null, null, null, null, null, null, null, null, null, null));
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);

        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        standaard.setGemeenteCode(GEM_34);
        standaard.setLandCode(LAND_2);
        standaard.setRedenWijzigingCode(WIJZADRES_P.getWaarde());
        standaard.setWoonplaatsCode(PLAATS_234);

        Assert.assertTrue(berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht,
                bouwBerichtContext(""), res));
        Mockito.verify(referentieDataRepository, Mockito.times(2)).vindGemeenteOpCode(Matchers.any(GemeenteCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindWoonplaatsOpCode(
                Matchers.any(Woonplaatscode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindLandOpCode(Matchers.any(Landcode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindRedenWijzingAdresOpCode(
                Matchers.any(RedenWijzigingAdresCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindSoortDocumentOpNaam(
                Matchers.any(NaamEnumeratiewaarde.class));
        Assert.assertNotNull(verhuisActie.getPartij());
        Assert.assertNotNull(verhuisActie.getTijdstipRegistratie());
    }

    @Test
    public void testVerhuizingGeenVerrijkingNodig() {
        BerichtVerwerkingsResultaat res = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        final VerhuizingBericht verhuisBericht = new VerhuizingBericht();
        ActieBericht verhuisActie = new ActieRegistratieAdresBericht();
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Piet", "van", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        rootObjecten.add(pers);
        verhuisActie.setRootObjecten(rootObjecten);
        verhuisBericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);

        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        Assert.assertTrue(berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht,
                bouwBerichtContext(""), res));
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindGemeenteOpCode(Matchers.any(GemeenteCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindWoonplaatsOpCode(
                Matchers.any(Woonplaatscode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindLandOpCode(BrpConstanten.NL_LAND_CODE);
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindRedenWijzingAdresOpCode(
                Matchers.any(RedenWijzigingAdresCode.class));
    }

    @Test
    public void testCorrectieAdresNL() {
        BerichtVerwerkingsResultaat res = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        final CorrectieAdresBericht correctieAdresBericht = new CorrectieAdresBericht();
        ActieBericht correactieAdresActie = new ActieCorrectieAdresBericht();
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Piet", "van", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        rootObjecten.add(pers);
        correactieAdresActie.setRootObjecten(rootObjecten);
        correctieAdresBericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        correctieAdresBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        correctieAdresBericht.getAdministratieveHandeling().getActies().add(correactieAdresActie);

        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        standaard.setGemeenteCode(GEM_34);
        standaard.setLandCode(LAND_2);
        standaard.setRedenWijzigingCode(WIJZADRES_P.getWaarde());
        standaard.setWoonplaatsCode(PLAATS_234);

        Assert.assertTrue(berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(correctieAdresBericht,
                bouwBerichtContext(""), res));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindGemeenteOpCode(Matchers.any(GemeenteCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindWoonplaatsOpCode(
                Matchers.any(Woonplaatscode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindLandOpCode(Matchers.any(Landcode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindRedenWijzingAdresOpCode(
                Matchers.any(RedenWijzigingAdresCode.class));
    }

    @Test
    public void testCorrectieAdresNLGeenVerrijkingNodig() {
        BerichtVerwerkingsResultaat res = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        final CorrectieAdresBericht correctieAdresBericht = new CorrectieAdresBericht();
        ActieBericht correactieAdresActie = new ActieCorrectieAdresBericht();
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Piet", "van", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        rootObjecten.add(pers);
        correactieAdresActie.setRootObjecten(rootObjecten);
        correctieAdresBericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        correctieAdresBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        correctieAdresBericht.getAdministratieveHandeling().getActies().add(correactieAdresActie);

        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        Assert.assertTrue(berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(correctieAdresBericht,
                bouwBerichtContext(""), res));
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindGemeenteOpCode(Matchers.any(GemeenteCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindWoonplaatsOpCode(
                Matchers.any(Woonplaatscode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindLandOpCode(BrpConstanten.NL_LAND_CODE);
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindRedenWijzingAdresOpCode(
                Matchers.any(RedenWijzigingAdresCode.class));
    }

    @Test
    public void testEersteInschrijving() {
        final BerichtVerwerkingsResultaat res = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        final InschrijvingGeboorteBericht bericht = new InschrijvingGeboorteBericht();
        ActieBericht actie = new ActieRegistratieGeboorteBericht();
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        actie.setRootObjecten(rootObjecten);
        FamilierechtelijkeBetrekkingBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        rootObjecten.add(relatie);
        bericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        bericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        bericht.getAdministratieveHandeling().getActies().add(actie);

        PersoonBericht kind =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Piet", "van", "Veldhuijsen");
        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();

        geboorte.setGemeenteGeboorteCode(GEM_34);
        geboorte.setWoonplaatsGeboorteCode(PLAATS_234);
        geboorte.setLandGeboorteCode(LAND_2);

        kind.setGeboorte(geboorte);
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        KindBericht betr = new KindBericht();
        betr.setPersoon(kind);
        relatie.getBetrokkenheden().add(betr);

        ReflectionTestUtils.setField(kind.getGeslachtsnaamcomponenten().get(0).getStandaard(), "predikaatCode", "pred");
        ReflectionTestUtils.setField(kind.getGeslachtsnaamcomponenten().get(0).getStandaard(), "adellijkeTitelCode",
                "adel");

        Assert.assertTrue(berichtVerrijkingsStap
                .voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(""), res));

        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindGemeenteOpCode(Matchers.any(GemeenteCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindWoonplaatsOpCode(
                Matchers.any(Woonplaatscode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindLandOpCode(Matchers.any(Landcode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindPredikaatOpCode(
                Matchers.any(PredikaatCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindAdellijkeTitelOpCode(
                Matchers.any(AdellijkeTitelCode.class));
    }

    @Test
    public void testEersteInschrijvingGeenVerrijkingNodig() {
        final BerichtVerwerkingsResultaat res = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        final InschrijvingGeboorteBericht bericht = new InschrijvingGeboorteBericht();
        ActieBericht actie = new ActieRegistratieGeboorteBericht();
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        actie.setRootObjecten(rootObjecten);
        FamilierechtelijkeBetrekkingBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        rootObjecten.add(relatie);
        bericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        bericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        bericht.getAdministratieveHandeling().getActies().add(actie);

        PersoonBericht kind =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Piet", "van", "Veldhuijsen");
        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();

        kind.setGeboorte(geboorte);
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        KindBericht betr = new KindBericht();
        betr.setPersoon(kind);
        relatie.getBetrokkenheden().add(betr);

        Assert.assertTrue(berichtVerrijkingsStap
                .voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(""), res));

        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindGemeenteOpCode(Matchers.any(GemeenteCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindWoonplaatsOpCode(
                Matchers.any(Woonplaatscode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindLandOpCode(Matchers.any(Landcode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindPredikaatOpCode(
                Matchers.any(PredikaatCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(0)).vindAdellijkeTitelOpCode(
                Matchers.any(AdellijkeTitelCode.class));
    }

    @Test
    public void testEersteInschrijvingOnbekendeReferentie() {
        final BerichtVerwerkingsResultaat res = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        final InschrijvingGeboorteBericht bericht = new InschrijvingGeboorteBericht();
        ActieBericht actie = new ActieRegistratieGeboorteBericht();
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        actie.setRootObjecten(rootObjecten);
        FamilierechtelijkeBetrekkingBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        rootObjecten.add(relatie);
        bericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        bericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        bericht.getAdministratieveHandeling().getActies().add(actie);
        //
        // relatieStandaardGroep.setWoonWoonplaatscode(PLAATS_234);
        // relatieStandaardGroep.setWoonWoonplaatscode(PLAATS_234);

        PersoonBericht kind =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Piet", "van", "Veldhuijsen");
        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();

        geboorte.setGemeenteGeboorteCode(GEM_34);
        geboorte.setWoonplaatsGeboorteCode(PLAATS_234);
        geboorte.setLandGeboorteCode(LAND_2);

        kind.setGeboorte(geboorte);
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        KindBericht betr = new KindBericht();
        betr.setPersoon(kind);
        relatie.getBetrokkenheden().add(betr);

        ReflectionTestUtils.setField(kind.getGeslachtsnaamcomponenten().get(0).getStandaard(), "predikaatCode", "pred");
        ReflectionTestUtils.setField(kind.getGeslachtsnaamcomponenten().get(0).getStandaard(), "adellijkeTitelCode",
                "adel");

        Mockito.when(referentieDataRepository.vindWoonplaatsOpCode(Matchers.any(Woonplaatscode.class))).thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PLAATSCODE, "foo",
                        new Exception()));

        Mockito.when(referentieDataRepository.vindGemeenteOpCode(Matchers.any(GemeenteCode.class))).thenThrow(
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

        Assert.assertEquals(5, res.getMeldingen().size());
        Assert.assertEquals(MeldingCode.BRAL1001, res.getMeldingen().get(0).getCode());
        Assert.assertEquals("Land 0002 bestaat niet.", res.getMeldingen().get(0).getOmschrijving());
        Assert.assertEquals(MeldingCode.BRAL1002, res.getMeldingen().get(1).getCode());
        Assert.assertEquals("Gemeente 0034 bestaat niet.", res.getMeldingen().get(1).getOmschrijving());
        Assert.assertEquals(MeldingCode.BRAL1015, res.getMeldingen().get(2).getCode());
        Assert.assertEquals("Adellijke titel adel bestaat niet.", res.getMeldingen().get(2).getOmschrijving());
        Assert.assertEquals(MeldingCode.BRAL1018, res.getMeldingen().get(3).getCode());
        Assert.assertEquals("Predikaat pred bestaat niet.", res.getMeldingen().get(3).getOmschrijving());
        Assert.assertEquals(MeldingCode.BRAL1020, res.getMeldingen().get(4).getCode());
        Assert.assertEquals("Woonplaats 0234 bestaat niet.", res.getMeldingen().get(4).getOmschrijving());
    }

    @Test
    public void testOnbekendeReferentieExceptionAlsResultaat() {
        BerichtVerwerkingsResultaat res = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        final VerhuizingBericht verhuisBericht = new VerhuizingBericht();
        ActieBericht verhuisActie = new ActieRegistratieAdresBericht();
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Piet", "van", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        rootObjecten.add(pers);
        verhuisActie.setRootObjecten(rootObjecten);
        verhuisBericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);

        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        standaard.setWoonplaatsCode(PLAATS_234);
        standaard.setGemeenteCode(GEM_34);
        standaard.setRedenWijzigingCode(WIJZADRES_P.getWaarde());

        Mockito.when(referentieDataRepository.vindWoonplaatsOpCode(Matchers.any(Woonplaatscode.class))).thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PLAATSCODE, "foo",
                        new Exception()));

        Mockito.when(referentieDataRepository.vindGemeenteOpCode(Matchers.any(GemeenteCode.class))).thenThrow(
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
        Assert.assertEquals("Reden wijziging adres P bestaat niet.", res.getMeldingen().get(2).getOmschrijving());
        Assert.assertEquals(MeldingCode.BRAL1020, res.getMeldingen().get(3).getCode());
    }

    @Test
    public void testRegistratieNationaliteit() {
        BerichtVerwerkingsResultaat res = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        final VerhuizingBericht verhuisBericht = new VerhuizingBericht();
        ActieBericht verhuisActie = new ActieRegistratieNationaliteitBericht();
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Piet", "van", "Veldhuijsen");
        rootObjecten.add(pers);
        verhuisActie.setRootObjecten(rootObjecten);
        verhuisBericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);
        pers.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        PersoonNationaliteitBericht persNation = new PersoonNationaliteitBericht();
        pers.getNationaliteiten().add(persNation);
        persNation.setNationaliteitCode(BrpConstanten.NL_NATIONALITEIT_CODE.toString());
        PersoonNationaliteitStandaardGroepBericht gegevens = new PersoonNationaliteitStandaardGroepBericht();
        gegevens.setRedenVerkrijgingCode(VERKRIJG_10.toString());
        gegevens.setRedenVerliesCode(VERLIES_07.toString());
        persNation.setStandaard(gegevens);

        Assert.assertTrue(berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht,
                bouwBerichtContext(""), res));

        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindNationaliteitOpCode(
                Matchers.any(Nationaliteitcode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindRedenVerkregenNlNationaliteitOpCode(
                Matchers.any(RedenVerkrijgingCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindRedenVerliesNLNationaliteitOpCode(
                Matchers.any(RedenVerliesCode.class));
    }

    @Test
    public void testHuwelijk() {
        BerichtVerwerkingsResultaat res = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());

        HuwelijkGeregistreerdPartnerschapStandaardGroepBericht standaard = new HuwelijkGeregistreerdPartnerschapStandaardGroepBericht();
        standaard.setLandAanvangCode(LAND_2);
        standaard.setLandEindeCode(LAND_2);
        standaard.setGemeenteAanvangCode(GEM_34);
        standaard.setGemeenteEindeCode(GEM_34);
        standaard.setWoonplaatsAanvangCode(PLAATS_234);
        standaard.setWoonplaatsEindeCode(PLAATS_234);

        nl.bzk.brp.model.bericht.kern.HuwelijkBericht relatieBericht = new nl.bzk.brp.model.bericht.kern.HuwelijkBericht();
        relatieBericht.setStandaard(standaard);

        ActieBericht huwelijkActie = new ActieRegistratieHuwelijkBericht();
        huwelijkActie.setRootObjecten(new ArrayList<RootObject>());
        huwelijkActie.getRootObjecten().add(relatieBericht);

        final HuwelijkBericht huwelijkBericht = new HuwelijkBericht();
        huwelijkBericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        huwelijkBericht.getAdministratieveHandeling().setPartij(
                new Partij(null, null, null, null, null, null, null, null, null, null));
        huwelijkBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        huwelijkBericht.getAdministratieveHandeling().getActies().add(huwelijkActie);

        Assert.assertTrue(berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(huwelijkBericht,
                bouwBerichtContext(""), res));
        Mockito.verify(referentieDataRepository, Mockito.times(2)).vindLandOpCode(Matchers.any(Landcode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(2)).vindGemeenteOpCode(Matchers.any(GemeenteCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(2)).vindWoonplaatsOpCode(
                Matchers.any(Woonplaatscode.class));

        Assert.assertNotNull(huwelijkActie.getPartij());
        Assert.assertNotNull(huwelijkActie.getTijdstipRegistratie());
    }

    @Test
    public void testOverlijdenGroepVerrijking() {
        BerichtVerwerkingsResultaat res = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        final VerhuizingBericht verhuisBericht = new VerhuizingBericht();

        ActieBericht verhuisActie = new ActieRegistratieAdresBericht();

        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Piet", "van", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        rootObjecten.add(pers);
        verhuisActie.setRootObjecten(rootObjecten);
        verhuisBericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        verhuisBericht.getAdministratieveHandeling().setPartij(
                new Partij(null, null, null, null, null, null, null, null, null, null));
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);


        PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setGemeenteOverlijdenCode(GEM_34);
        overlijden.setWoonplaatsOverlijdenCode(PLAATS_234);
        overlijden.setLandOverlijdenCode(LAND_2);

        pers.setOverlijden(overlijden);

        Assert.assertTrue(berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht,
                bouwBerichtContext(""), res));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindGemeenteOpCode(Matchers.any(GemeenteCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindWoonplaatsOpCode(
                Matchers.any(Woonplaatscode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindLandOpCode(Matchers.any(Landcode.class));
        Assert.assertNotNull(verhuisActie.getPartij());
        Assert.assertNotNull(verhuisActie.getTijdstipRegistratie());
    }

    @Test
    public void testRegistratieNationaliteitOnbekendeReferentie() {
        BerichtVerwerkingsResultaat res = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        final VerhuizingBericht verhuisBericht = new VerhuizingBericht();
        ActieBericht verhuisActie = new ActieRegistratieNationaliteitBericht();
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers =
            PersoonBuilder.bouwPersoon("123456789", Geslachtsaanduiding.MAN, 19830404,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Piet", "van", "Veldhuijsen");
        rootObjecten.add(pers);
        verhuisActie.setRootObjecten(rootObjecten);
        verhuisBericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);
        pers.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        PersoonNationaliteitBericht persNation = new PersoonNationaliteitBericht();
        pers.getNationaliteiten().add(persNation);
        persNation.setNationaliteitCode(BrpConstanten.NL_NATIONALITEIT_CODE.toString());
        PersoonNationaliteitStandaardGroepBericht gegevens = new PersoonNationaliteitStandaardGroepBericht();
        gegevens.setRedenVerkrijgingCode(VERKRIJG_10.toString());
        gegevens.setRedenVerliesCode(VERLIES_07.toString());
        persNation.setStandaard(gegevens);

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
                referentieDataRepository.vindRedenVerliesNLNationaliteitOpCode(Matchers.any(RedenVerliesCode.class)))
                .thenThrow(
                        new OnbekendeReferentieExceptie(
                                OnbekendeReferentieExceptie.ReferentieVeld.REDENVERLIESNLNATION, "foo", new Exception()));

        Assert.assertFalse(berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht,
                bouwBerichtContext(""), res));

        Assert.assertEquals(3, res.getMeldingen().size());
        Assert.assertEquals(MeldingCode.BRAL1017, res.getMeldingen().get(0).getCode());
        Assert.assertEquals("Nationaliteit 0001 bestaat niet.", res.getMeldingen().get(0).getOmschrijving());
        Assert.assertEquals(MeldingCode.BRAL1021, res.getMeldingen().get(1).getCode());
        Assert.assertEquals("Reden verkrijging Nederlandse nationaliteit 010 bestaat niet.", res.getMeldingen().get(1)
                .getOmschrijving());
        Assert.assertEquals(MeldingCode.BRAL1022, res.getMeldingen().get(2).getCode());
        Assert.assertEquals("Reden verlies Nederlandse nationaliteit 007 bestaat niet.", res.getMeldingen().get(2)
                .getOmschrijving());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testOngeldigeRootObject() {
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(new RootObject() {
        });

        ActieBericht verhuisActie = new ActieRegistratieAdresBericht();
        verhuisActie.setRootObjecten(rootObjecten);

        final VerhuizingBericht verhuisBericht = new VerhuizingBericht();
        verhuisBericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);

        BerichtVerwerkingsResultaat res = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht, bouwBerichtContext(""), res);
    }
}
