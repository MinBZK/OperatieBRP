/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.business.bedrijfsregels.impl.bericht.BRBY9905;
import nl.bzk.brp.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.business.stappen.AbstractStapTest;
import nl.bzk.brp.business.util.BerichtAdministratieUtil;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerliesCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingAdresCode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.BerichtBuilder;
import nl.bzk.brp.util.PersoonAdresBuilder;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class BerichtAdministratieUtilTest extends AbstractStapTest {

    private static final String                  GEM_34      = "0034";
    private static final String                  LAND_2      = "0002";
    private static final String                  PLAATS_234  = "0234";
    private static final RedenVerkrijgingCode    VERKRIJG_10 = new RedenVerkrijgingCode((short) 10);
    private static final RedenVerliesCode        VERLIES_07  = new RedenVerliesCode((short) 7);
    private static final RedenWijzigingAdresCode WIJZADRES_P = new RedenWijzigingAdresCode("P");

    @Mock
    private ReferentieDataRepository             referentieDataRepository;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
//        ReflectionTestUtils.setField(berichtAdministratieStap, "referentieRepository", referentieDataRepository);
    }

    @Test
    public void testNormaalBericht() {
        BerichtVerwerkingsResultaat res = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());

        AbstractBijhoudingsBericht simpleVerhuisBericht = maakSimpleVerhuisBericht2();
        Assert.assertNull(simpleVerhuisBericht.getIdentificeerbaarObjectIndex());
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simpleVerhuisBericht);
        Assert.assertNotNull(simpleVerhuisBericht.getIdentificeerbaarObjectIndex());


        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        Assert.assertNull(simplegGeboorteBericht.getIdentificeerbaarObjectIndex());
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        Assert.assertNotNull(simplegGeboorteBericht.getIdentificeerbaarObjectIndex());
    }

    @Test
    public void testBRY9905Normaal() {
        BRBY9905 brby = new BRBY9905();
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        brby.executeer(simplegGeboorteBericht);
    }

    @Test
    public void testBRY9905NaarZichZelf() {
        BRBY9905 brby = new BRBY9905();
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        FamilierechtelijkeBetrekkingBericht famBericht = (FamilierechtelijkeBetrekkingBericht)
                simplegGeboorteBericht.getAdministratieveHandeling().getActies().get(0).getRootObjecten().get(0);
        // Kind refereert naar zichtzelf
        famBericht.getKindBetrokkenheid().getPersoon().setReferentieID(
                famBericht.getKindBetrokkenheid().getPersoon().getCommunicatieID());
        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = brby.executeer(simplegGeboorteBericht);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testBRY9905RefereertNaarEenReferentie() {
        BRBY9905 brby = new BRBY9905();
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        FamilierechtelijkeBetrekkingBericht famBericht = (FamilierechtelijkeBetrekkingBericht)
                simplegGeboorteBericht.getAdministratieveHandeling().getActies().get(0).getRootObjecten().get(0);
        // een van de ouders refereer naar kind (is technisch toegestaan).
        // refereer de 2e ouder naar de eerste.

        PersoonBericht kind = famBericht.getKindBetrokkenheid().getPersoon();
        PersoonBericht ouder1 = famBericht.getOuderBetrokkenheden().iterator().next().getPersoon();
        PersoonBericht ouder2 = famBericht.getOuderBetrokkenheden().iterator().next().getPersoon();

        ouder1.setReferentieID(kind.getCommunicatieID());
        ouder2.setReferentieID(ouder1.getCommunicatieID());

        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = brby.executeer(simplegGeboorteBericht);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testBRY9905RefereertNaarEenReferentieEnNaarZichzelf() {
        BRBY9905 brby = new BRBY9905();
        AbstractBijhoudingsBericht simplegGeboorteBericht = maakSimpleInschrijvingBericht2();
        FamilierechtelijkeBetrekkingBericht famBericht = (FamilierechtelijkeBetrekkingBericht)
                simplegGeboorteBericht.getAdministratieveHandeling().getActies().get(0).getRootObjecten().get(0);
        // een van de ouders refereer naar kind (is technisch toegestaan).
        // refereer de 2e ouder naar de eerste.

        PersoonBericht kind = famBericht.getKindBetrokkenheid().getPersoon();
        PersoonBericht ouder1 = famBericht.getOuderBetrokkenheden().iterator().next().getPersoon();
        PersoonBericht ouder2 = famBericht.getOuderBetrokkenheden().iterator().next().getPersoon();

        ouder1.setReferentieID(kind.getCommunicatieID());
        ouder2.setReferentieID(ouder1.getCommunicatieID());
        kind.setReferentieID(kind.getCommunicatieID());

        BerichtAdministratieUtil.verrijktBerichtMetIdentificeerbaarObjectIndex(simplegGeboorteBericht);
        List<Melding> meldingen = brby.executeer(simplegGeboorteBericht);
        // EEN omdat Alle fouten gecombineerd wordt tot 1 fout.
        Assert.assertEquals(2, meldingen.size());
    }

    private VerhuizingBericht maakSimpleVerhuisBericht2() {
        VerhuizingBericht bericht = new  VerhuizingBericht();
        PersoonBericht verhuisdePersoon = PersoonBuilder.bouwPersoon(
                123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Piet", "van", "Veldhuijsen");
        verhuisdePersoon.setAdressen(Arrays.asList(
                PersoonAdresBuilder.bouwWoonadres(
                    "STRAAT", 12, "1255AA",
                    StatischeObjecttypeBuilder.WOONPLAATS_BREDA,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                    20100809)));

        ActieBericht verhuisActie = BerichtBuilder.bouwActieRegistratieAdres(
                20100909, null,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                verhuisdePersoon);
        verhuisActie.setBronnen(Arrays.asList(
                BerichtBuilder.bouwActieBron("AA", GEM_34, null)));

        bericht.setAdministratieveHandeling(
            BerichtBuilder.bouwHandelingVerhuizing(
                StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                null, null, verhuisActie
            ));
        bericht.setMeldingen(null);
        bericht.setStuurgegevens(BerichtBuilder.bouwStuurGegegevens(
                "APP", "ORG", "REFNR", "CROSREF", "FUNCTIE"));
        return bericht;
    }

    private InschrijvingGeboorteBericht maakSimpleInschrijvingBericht2() {
        final InschrijvingGeboorteBericht bericht = new InschrijvingGeboorteBericht();
        PersoonBericht kind = PersoonBuilder.bouwPersoon(
                123456789, Geslachtsaanduiding.MAN, 20100704,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Klaas", "van", "Veldhuijsen");
        kind.setAdressen(Arrays.asList(
                PersoonAdresBuilder.bouwWoonadres(
                    "STRAAT", 12, "1255AA",
                    StatischeObjecttypeBuilder.WOONPLAATS_BREDA,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                    20100809)));
        PersoonBericht vader = PersoonBuilder.bouwPersoon(
                123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Piet", "van", "Veldhuijsen");
        vader.setAdressen(Arrays.asList(
                PersoonAdresBuilder.bouwWoonadres(
                    "STRAAT", 12, "1255AA",
                    StatischeObjecttypeBuilder.WOONPLAATS_BREDA,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                    20100809)));
        PersoonBericht moeder = PersoonBuilder.bouwPersoon(
                123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Marie", "van", "Veldhuijsen-Teunis");
        moeder.setAdressen(Arrays.asList(
                PersoonAdresBuilder.bouwWoonadres(
                    "STRAAT", 12, "1255AA",
                    StatischeObjecttypeBuilder.WOONPLAATS_BREDA,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                    20100809)));
        RelatieBuilder builder = new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        RelatieBericht familieBericht = builder
            .bouwFamilieRechtelijkeBetrekkingRelatie()
            .voegKindToe(kind)
            .voegOuderToe(vader)
            .voegOuderToe(moeder)
            .getRelatie();

        PersoonBericht kindNat = PersoonBuilder.bouwPersoon(
                123456789, Geslachtsaanduiding.MAN, 20100704,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA, "Klaas", "van", "Veldhuijsen");
        kindNat.setAdressen(Arrays.asList(
                PersoonAdresBuilder.bouwWoonadres(
                    "STRAAT", 12, "1255AA",
                    StatischeObjecttypeBuilder.WOONPLAATS_BREDA,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                    20100809)));

        ActieRegistratieGeboorteBericht geboorteActie = BerichtBuilder.bouwActieRegistratieGeboorte(
                20100909, null,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                familieBericht);
        ActieRegistratieNationaliteitBericht nationActie = BerichtBuilder.bouwActieRegistratieNationaliteit(
                20100909, null,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                kindNat);

        bericht.setAdministratieveHandeling(
                BerichtBuilder.bouwHandelingGeboorte(
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                    null, null, geboorteActie, nationActie
            ));
        bericht.setMeldingen(null);
        bericht.setStuurgegevens(BerichtBuilder.bouwStuurGegegevens(
                "APP", "ORG", "REFNR", "CROSREF", "FUNCTIE"));
        return bericht;
    }


    // TODO zet de unit test beter op .....


    private void bouwMaximaalOverlijdenBericht() {

    }
}
