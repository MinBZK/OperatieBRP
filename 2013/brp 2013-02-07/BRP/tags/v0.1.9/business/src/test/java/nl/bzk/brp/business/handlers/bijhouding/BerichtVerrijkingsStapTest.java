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
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.Nationaliteitcode;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
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

    private BerichtVerrijkingsStap berichtVerrijkingsStap;

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
        PersoonBericht pers = PersoonBuilder.bouwPersoon(
                "123456789",
                Geslachtsaanduiding.MAN,
                19830404,
                new Partij(),
                "Piet",
                "van",
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

        Assert.assertTrue(
                berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht, bouwBerichtContext(""), res));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindGemeenteOpCode(
                Matchers.any(Gemeentecode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindWoonplaatsOpCode(
                Matchers.any(PlaatsCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindLandOpCode(
                Matchers.any(Landcode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindRedenWijzingAdresOpCode(
                Matchers.any(RedenWijzigingAdresCode.class));
    }

    @Test
    public void testCorrectieAdresNL() {
        BerichtResultaat res = new BerichtResultaat(new ArrayList<Melding>());
        final CorrectieAdresBericht correctieAdresBericht = new CorrectieAdresBericht();
        ActieBericht correactieAdresActie = new ActieBericht();
        correactieAdresActie.setSoort(SoortActie.CORRECTIE_ADRES_NL);
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers = PersoonBuilder.bouwPersoon(
                "123456789",
                Geslachtsaanduiding.MAN,
                19830404,
                new Partij(),
                "Piet",
                "van",
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

        Assert.assertTrue(
                berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(
                        correctieAdresBericht, bouwBerichtContext(""), res));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindGemeenteOpCode(
                Matchers.any(Gemeentecode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindWoonplaatsOpCode(
                Matchers.any(PlaatsCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindLandOpCode(
                Matchers.any(Landcode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindRedenWijzingAdresOpCode(
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

        PersoonBericht kind = PersoonBuilder.bouwPersoon(
                "123456789",
                Geslachtsaanduiding.MAN,
                19830404,
                new Partij(),
                "Piet",
                "van",
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

        Assert.assertTrue(
                berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(""), res));

        Mockito.verify(referentieDataRepository, Mockito.times(3)).vindGemeenteOpCode(
                Matchers.any(Gemeentecode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(3)).vindWoonplaatsOpCode(
                Matchers.any(PlaatsCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(3)).vindLandOpCode(
                Matchers.any(Landcode.class));
    }

    @Test
    public void testOnbekendeReferentieExceptionAlsResultaat() {
        BerichtResultaat res = new BerichtResultaat(new ArrayList<Melding>());
        final VerhuizingBericht verhuisBericht = new VerhuizingBericht();
        ActieBericht verhuisActie = new ActieBericht();
        verhuisActie.setSoort(SoortActie.VERHUIZING);
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers = PersoonBuilder.bouwPersoon(
                "123456789",
                Geslachtsaanduiding.MAN,
                19830404,
                new Partij(),
                "Piet",
                "van",
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

        Mockito.when(referentieDataRepository.vindWoonplaatsOpCode(Matchers.any(PlaatsCode.class))).thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PLAATSCODE,
                        "foo", new Exception()));

        Assert.assertFalse(
                berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht, bouwBerichtContext(""), res));
    }

    @Test
    public void testRegistratieNationaliteit() {
        BerichtResultaat res = new BerichtResultaat(new ArrayList<Melding>());
        final VerhuizingBericht verhuisBericht = new VerhuizingBericht();
        ActieBericht verhuisActie = new ActieBericht();
        verhuisActie.setSoort(SoortActie.REGISTRATIE_NATIONALITEIT);
        final List<RootObject> rootObjecten = new ArrayList<RootObject>();
        PersoonBericht pers = PersoonBuilder.bouwPersoon(
                "123456789",
                Geslachtsaanduiding.MAN,
                19830404,
                new Partij(),
                "Piet",
                "van",
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

        Assert.assertTrue(
                berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht, bouwBerichtContext(""), res));

        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindNationaliteitOpCode(
                Matchers.any(Nationaliteitcode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindRedenVerkregenNlNationaliteitOpCode(
                Matchers.any(RedenVerkrijgingCode.class));
        Mockito.verify(referentieDataRepository, Mockito.times(1)).vindRedenVerliesNLNationaliteitOpNaam(
                Matchers.any(RedenVerliesNaam.class));
    }
}
