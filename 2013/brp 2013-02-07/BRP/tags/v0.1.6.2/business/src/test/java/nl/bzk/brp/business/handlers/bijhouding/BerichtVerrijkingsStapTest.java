/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ReferentieDataMdlRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.GemeenteCode;
import nl.bzk.brp.model.attribuuttype.LandCode;
import nl.bzk.brp.model.attribuuttype.NationaliteitCode;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.RedenVerkrijgingNaam;
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
import nl.bzk.brp.model.objecttype.operationeel.statisch.GeslachtsAanduiding;
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
    private ReferentieDataMdlRepository referentieDataMdlRepository;

    private BerichtVerrijkingsStap berichtVerrijkingsStap;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        berichtVerrijkingsStap = new BerichtVerrijkingsStap();
        ReflectionTestUtils.setField(berichtVerrijkingsStap, "referentieRepository", referentieDataMdlRepository);
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
                GeslachtsAanduiding.MAN,
                19830404,
                new Partij(),
                "Piet",
                "van",
                "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        rootObjecten.add(pers);
        verhuisActie.setRootObjecten(rootObjecten);
        verhuisBericht.voegActieToe(verhuisActie);

        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setGegevens(standaard);
        pers.getAdressen().add(adres);

        standaard.setGemeenteCode(new GemeenteCode("foo"));
        standaard.setLandCode(new LandCode("foo"));
        standaard.setRedenwijzigingCode(new RedenWijzigingAdresCode("foo"));
        standaard.setWoonplaatsCode(new PlaatsCode("foo"));

        Assert.assertTrue(
                berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht, bouwBerichtContext(""), res));
        Mockito.verify(referentieDataMdlRepository, Mockito.times(1)).vindGemeenteOpCode(
                Matchers.any(GemeenteCode.class));
        Mockito.verify(referentieDataMdlRepository, Mockito.times(1)).vindWoonplaatsOpCode(
                Matchers.any(PlaatsCode.class));
        Mockito.verify(referentieDataMdlRepository, Mockito.times(1)).vindLandOpCode(
                Matchers.any(LandCode.class));
        Mockito.verify(referentieDataMdlRepository, Mockito.times(1)).vindRedenWijzingAdresOpCode(
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
        bericht.voegActieToe(actie);

        RelatieStandaardGroepBericht relatieStandaardGroep = new RelatieStandaardGroepBericht();
        relatie.setGegevens(relatieStandaardGroep);

        PersoonBericht kind = PersoonBuilder.bouwPersoon(
                "123456789",
                GeslachtsAanduiding.MAN,
                19830404,
                new Partij(),
                "Piet",
                "van",
                "Veldhuijsen");
        PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();

        geboorte.setGemeenteGeboorteCode(new GemeenteCode("foo"));
        geboorte.setWoonplaatsGeboorteCode(new PlaatsCode("foo"));
        geboorte.setLandGeboorteCode(new LandCode("foo"));

        kind.setGeboorte(geboorte);
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        BetrokkenheidBericht betr = new BetrokkenheidBericht();
        betr.setRol(SoortBetrokkenheid.KIND);
        betr.setBetrokkene(kind);
        relatie.getBetrokkenheden().add(betr);

        relatieStandaardGroep.setLandAanvangCode(new LandCode("foo"));
        relatieStandaardGroep.setLandEindeCode(new LandCode("foo"));

        relatieStandaardGroep.setGemeenteAanvangCode(new GemeenteCode("foo"));
        relatieStandaardGroep.setGemeenteEindeCode(new GemeenteCode("foo"));

        relatieStandaardGroep.setWoonPlaatsAanvangCode(new PlaatsCode("foo"));
        relatieStandaardGroep.setWoonPlaatsEindeCode(new PlaatsCode("foo"));

        Assert.assertTrue(
                berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(""), res));

        Mockito.verify(referentieDataMdlRepository, Mockito.times(3)).vindGemeenteOpCode(
                Matchers.any(GemeenteCode.class));
        Mockito.verify(referentieDataMdlRepository, Mockito.times(3)).vindWoonplaatsOpCode(
                Matchers.any(PlaatsCode.class));
        Mockito.verify(referentieDataMdlRepository, Mockito.times(3)).vindLandOpCode(
                Matchers.any(LandCode.class));
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
                GeslachtsAanduiding.MAN,
                19830404,
                new Partij(),
                "Piet",
                "van",
                "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        rootObjecten.add(pers);
        verhuisActie.setRootObjecten(rootObjecten);
        verhuisBericht.voegActieToe(verhuisActie);

        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setGegevens(standaard);
        pers.getAdressen().add(adres);

        standaard.setWoonplaatsCode(new PlaatsCode("foo"));

        Mockito.when(referentieDataMdlRepository.vindWoonplaatsOpCode(Matchers.any(PlaatsCode.class))).thenThrow(
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
                GeslachtsAanduiding.MAN,
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
        persNation.setNationaliteitCode(new NationaliteitCode("NL"));
        PersoonNationaliteitStandaardGroepBericht gegevens = new PersoonNationaliteitStandaardGroepBericht();
        gegevens.setRedenVerkregenNlNationaliteitNaam(new RedenVerkrijgingNaam("foo"));
        gegevens.setRedenVerliesNlNationaliteitNaam(new RedenVerliesNaam("bar"));
        persNation.setGegevens(gegevens);

        Assert.assertTrue(
                berichtVerrijkingsStap.voerVerwerkingsStapUitVoorBericht(verhuisBericht, bouwBerichtContext(""), res));

        Mockito.verify(referentieDataMdlRepository, Mockito.times(1)).vindNationaliteitOpCode(
                Matchers.any(NationaliteitCode.class));
        Mockito.verify(referentieDataMdlRepository, Mockito.times(1)).vindRedenVerkregenNlNationaliteitOpNaam(
                Matchers.any(RedenVerkrijgingNaam.class));
        Mockito.verify(referentieDataMdlRepository, Mockito.times(1)).vindRedenVerliesNLNationaliteitOpNaam(
                Matchers.any(RedenVerliesNaam.class));
    }
}
