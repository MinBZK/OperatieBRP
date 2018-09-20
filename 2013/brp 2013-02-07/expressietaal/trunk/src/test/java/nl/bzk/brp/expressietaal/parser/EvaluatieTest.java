/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Context;
import nl.bzk.brp.expressietaal.parser.syntaxtree.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.parser.syntaxtree.EvaluatieResultaat;
import nl.bzk.brp.expressietaal.symbols.DefaultSolver;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.GeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Test de evaluatie van expressies.
 */
@SuppressWarnings("MagicNumber")
public class EvaluatieTest {

    private static final int OPA_BSN = 111111111;
    private static final int OMA_BSN = 222222222;
    private static final int ZOON_BSN = 333333333;
    private static final int DOCHTER_BSN = 444444444;
    private static final int SCHOONDOCHTER_BSN = 55555555;
    private static final int KLEINZOON_BSN = 66666666;

    @Test
    public void testNumberLiterals() {
        testEvaluatie("10", "#10");
        testEvaluatie("0", "#0");
    }

    @Test
    public void testBooleanLiterals() {
        testEvaluatie("TRUE", "TRUE");
        testEvaluatie("FALSE", "FALSE");
    }

    @Test
    public void testStringLiterals() {
        testEvaluatie("\"\"", "\"\"");
        testEvaluatie("\"abc\"", "\"abc\"");
        testEvaluatie("\"     \"", "\"     \"");
    }

    @Test
    public void testNumeriekeBerekeningen() {
        testEvaluatie("10+20", "#30");
        testEvaluatie("2*5", "#10");
        testEvaluatie("(2+4)*5", "#30");
        testEvaluatie("2+4*5", "#22");
        testEvaluatie("2+(4*5)", "#22");
        testEvaluatie("10-5", "#5");
        testEvaluatie("5-10", "#-5");
        testEvaluatie("-2*8", "#-16");
        testEvaluatie("-(2*8)", "#-16");
        testEvaluatie("20/4", "#5");
        testEvaluatie("10/3", "#3");
        testEvaluatie("0*2000", "#0");
    }

    @Test
    public void testBooleanBerekeningen() {
        testEvaluatie("TRUE EN TRUE", "TRUE");
        testEvaluatie("TRUE EN FALSE", "FALSE");
        testEvaluatie("FALSE EN TRUE", "FALSE");
        testEvaluatie("FALSE EN FALSE", "FALSE");
        testEvaluatie("TRUE OF FALSE", "TRUE");
        testEvaluatie("FALSE OF FALSE", "FALSE");
    }

    @Test
    public void testDatumBerekeningen() {
        testEvaluatie("MAAND(#2000-JAN-10#)", "#1");
        testEvaluatie("MAAND(#1975-08#)", "#8");
        testEvaluatie("JAAR(#2000-JAN-10#)", "#2000");
        testEvaluatie("JAAR(#1975-08#)", "#1975");
        testEvaluatie("DAG(#2000-JAN-10#)", "#10");
        testEvaluatie("DAG(#1975-08#)", "#0");
    }

    @Test
    public void testVergelijkingen() {
        testEvaluatie("10 > 2", "TRUE");
        testEvaluatie("12 = 12", "TRUE");
        testEvaluatie("4 * 5 > 10", "TRUE");
        testEvaluatie("#1970-12-10# < #1980-JUN-20#", "TRUE");
        testEvaluatie("#1970-12-10# <= #1980-JUN-20#", "TRUE");
        testEvaluatie("#1990-JAN-01# > #1989-DEC-31#", "TRUE");
        testEvaluatie("#1990-JAN-01# >= #1989-DEC-31#", "TRUE");
        testEvaluatie("#1975-AUG-20# <> #1980-AUG-20#", "TRUE");
        testEvaluatie("#1975-AUG-20# = #1975-08-20#", "TRUE");
        testEvaluatie("#1990-JAN-01# > #1990-JAN#", "TRUE");
        testEvaluatie("#1990-JAN-01# < #1990-FEB#", "TRUE");

        DateTime vandaag = new DateTime();
        String datumString = ParserUtils.getDatumString(vandaag);
        testEvaluatie("NU()", datumString);
    }

    @Test
    public void testLijsten() {
        testEvaluatie("{1}", "{#1}");
        testEvaluatie("{1, 2, 3}", "{#1,#2,#3}");
        testEvaluatie("{}", "{}");
        testEvaluatie("1 in {1, 2, 3}", "TRUE");
        testEvaluatie("2 in {1, 2, 3}", "TRUE");
        testEvaluatie("3 in {1, 2, 3}", "TRUE");
        testEvaluatie("4 in {1, 2, 3}", "FALSE");
    }

    @Test
    public void testAttributen() {
        testEvaluatie("persoon.identificatienummers.burgerservicenummer", "#" + OPA_BSN);
        testEvaluatie("identificatienummers.burgerservicenummer", "#" + OPA_BSN);
        testEvaluatie("bsn", "#" + OPA_BSN);
        testEvaluatie("persoon.geboorte.datum", "#1943-NOV-21#");
        testEvaluatie("geboorte.datum", "#1943-NOV-21#");
        testEvaluatie("persoon.voornamen[1].naam", "\"Jack\"");
        testEvaluatie("persoon.geslachtsaanduiding", "\"M\"");
        testEvaluatie("geboorte.gemeente", "\"758\"");
        testEvaluatie("overlijden.woonplaats", "\"1024\"");
        testEvaluatie("overlijden.gemeente", "\"363\"");
        testEvaluatie("overlijden.datum", "#1995-MRT-26#");
        testEvaluatie("persoon.nationaliteiten[1].nationaliteit", "\"1\"");
        testEvaluatie("persoon.nationaliteiten[2].nationaliteit", "\"339\"");
        testEvaluatie("aantal(persoon.nationaliteiten[])", "#2");
        testEvaluatie("aantal(persoon.voornamen[])", "#1");
    }

    @Test
    public void testRelaties() {
        testEvaluatie("kinderen(persoon)", String.format("{@Persoon(%s)@,@Persoon(%s)@}", DOCHTER_BSN, ZOON_BSN));
        testEvaluatie("kinderen()", String.format("{@Persoon(%s)@,@Persoon(%s)@}", DOCHTER_BSN, ZOON_BSN));
        testEvaluatie("aantal(kinderen(persoon))", "#2");
        testEvaluatie("aantal(kinderen())", "#2");
        testEvaluatie("alle(kinderen(), k, TRUE)", "TRUE");
        testEvaluatie("alle(kinderen(), k, FALSE)", "FALSE");
        testEvaluatie("er_is(kinderen(), k, TRUE)", "TRUE");
        testEvaluatie("er_is(kinderen(), k, FALSE)", "FALSE");
        testEvaluatie("huwelijken(persoon)", "{@Huwelijk(?)@}");
    }

    @Test
    public void testVariabelen() {
        testEvaluatie("persoon", String.format("@Persoon(%s)@", OPA_BSN));
    }

    @Test
    public void testPersoonBRPExpressies() {
        Persoon testPersoon = maakTestPersoon();

        testPositive("geboorte.datum > #1940-10-10#", testPersoon);
        testPositive("NIET(geboorte.datum < #1924-DEC-4#)", testPersoon);
        testPositive("voornamen[1].naam = \"Jack\"", testPersoon);
        testPositive("voornamen[1].naam >= \"Inge\"", testPersoon);
        testPositive("voornamen[1].naam <> \"Henk\"", testPersoon);
        testPositive("geboorte.datum < overlijden.datum", testPersoon);
        testPositive("aantal(kinderen()) = 2", testPersoon);
        testPositive("aantal(kinderen()) > 0", testPersoon);
        testPositive("alle(kinderen(), k, TRUE)", testPersoon);
        testPositive("alle(kinderen(), k, k.geboorte.datum > #1970-01-01#)", testPersoon);
        testPositive("niet alle(kinderen(), k, k.geboorte.datum < #1970-01-01#)", testPersoon);
        testPositive("niet alle(kinderen(), k, k.geboorte.datum = #1990-01-01#)", testPersoon);
        testPositive("alle(kinderen(), k, k.geboorte.datum < #2000-01-01#)", testPersoon);
        testPositive("er_is(kinderen(), k, k.bsn = " + ZOON_BSN + ")", testPersoon);
        testPositive("er_is(kinderen(), k, k.bsn = " + DOCHTER_BSN + ")", testPersoon);
        testPositive("niet er_is(kinderen(), k, k.bsn = " + OPA_BSN + ")", testPersoon);
        testPositive("er_is(kinderen(), k, k.geboorte.datum > #1968-01-01# en k.geboorte.datum < #1971-01-01#)",
                testPersoon);
        testPositive("er_is(kinderen(), k, k.geslachtsaanduiding = \"M\" en er_is(kinderen(), l, "
                + "l.geslachtsaanduiding = \"V\" en l.bsn <> k.bsn))",
                testPersoon);
        testPositive("niet er_is(kinderen(), k, k.geslachtsaanduiding = \"M\" en er_is(kinderen(), l, "
                + "l.geslachtsaanduiding = \"M\" en l.bsn <> k.bsn))",
                testPersoon);
        testPositive("er_is(kinderen(), k, aantal(kinderen(k)) > 0)", testPersoon);
        testPositive("niet er_is(kinderen(), k, aantal(kinderen(k)) > 1)", testPersoon);
        testPositive("alle(kinderen(), k, er_is(ouders(k), o, o.geboorte.datum < #1950-JAN-01#))", testPersoon);
        testPositive("alle(kinderen(), k, alle(ouders(k), o, o.geboorte.datum < k.geboorte.datum))", testPersoon);
        testPositive("niet er_is(kinderen(), k, alle(ouders(k), o, o.geboorte.datum >= k.geboorte.datum))",
                testPersoon);
        testPositive("er_is(partners(), p, p.bsn = " + OMA_BSN + ")", testPersoon);
        testPositive("er_is(kinderen(), k, er_is(partners(k), p, p.bsn = " + SCHOONDOCHTER_BSN + "))", testPersoon);
        //testPositive("reisdocumenten[1].nummer", testPersoon);

        testEvaluatieFout("voornamen[2].naam", testPersoon, EvaluatieFoutCode.INDEX_BUITEN_BEREIK);
        testEvaluatieFout("persoon.nationaliteiten[3].nationaliteit", testPersoon,
                EvaluatieFoutCode.INDEX_BUITEN_BEREIK);
    }

    @Test
    public void testHuwelijkBRPExpressies() {
        Persoon testPersoon = maakTestPersoon();
        testPositive("er_is(huwelijken(), h, h.datum_aanvang > #1960-JAN-1#)", testPersoon);
        testPositive("alle(huwelijken(), h, h.datum_aanvang > #1960-JAN-1#)", testPersoon);
    }

    private Persoon maakTestPersoon() {
        Partij gemeenteGeboorte = StatischeObjecttypeBuilder.GEMEENTE_BREDA;
        Plaats ovplaats = StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM;
        Partij ovgemeente = StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM;
        Land land = StatischeObjecttypeBuilder.LAND_NEDERLAND;

        PersoonBericht opa = PersoonBuilder.bouwPersoon(OPA_BSN, Geslachtsaanduiding.MAN, 19431121,
                gemeenteGeboorte,
                "Jack", "", "McSnor");
        PersoonOverlijdenGroepBericht o = PersoonBuilder.bouwPersoonOverlijdenGroepbericht(19950326, ovplaats,
                ovgemeente, land);
        opa.setOverlijden(o);
        opa.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        List<PersoonNationaliteitBericht> nationaliteiten = new ArrayList<PersoonNationaliteitBericht>();
        nationaliteiten.add(PersoonBuilder.bouwPersoonNationaliteit(StatischeObjecttypeBuilder
                .NATIONALITEIT_NEDERLANDS));
        nationaliteiten.add(PersoonBuilder.bouwPersoonNationaliteit(StatischeObjecttypeBuilder.NATIONALITEIT_TURKS));
        opa.setNationaliteiten(nationaliteiten);

        PersoonBericht oma = PersoonBuilder.bouwPersoon(OMA_BSN, Geslachtsaanduiding.VROUW, 19431224,
                gemeenteGeboorte, "Ria", "", "Hola");
        oma.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        voegHuwelijkToe(opa, oma);

        PersoonBericht dochter = PersoonBuilder.bouwPersoon(DOCHTER_BSN, Geslachtsaanduiding.VROUW, 19720326,
                gemeenteGeboorte, "Tut", "", "Hola");
        dochter.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        voegKindToe(opa, oma, dochter);

        PersoonBericht zoon = PersoonBuilder.bouwPersoon(ZOON_BSN, Geslachtsaanduiding.MAN, 19700102,
                gemeenteGeboorte, "Piet", "", "Hola");
        zoon.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        voegKindToe(opa, oma, zoon);

        PersoonBericht schoondochter = PersoonBuilder.bouwPersoon(SCHOONDOCHTER_BSN, Geslachtsaanduiding.VROUW,
                19740520,
                gemeenteGeboorte, "Mien", "", "Bakgraag");
        schoondochter.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        voegGeregistreerdPartnerschapToe(zoon, schoondochter);

        PersoonBericht kleinzoon = PersoonBuilder.bouwPersoon(KLEINZOON_BSN, Geslachtsaanduiding.MAN, 20011209,
                gemeenteGeboorte, "Kleine", "", "Hola");
        kleinzoon.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        voegKindToe(zoon, schoondochter, kleinzoon);

        return opa;
    }

    private void voegKindToe(final PersoonBericht vader, final PersoonBericht moeder, final PersoonBericht kind) {
        RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder = new
                RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        FamilierechtelijkeBetrekkingBericht nieuweSituatie = relBuilder
                .bouwFamilieRechtelijkeBetrekkingRelatie()
                .voegOuderToe(moeder).voegOuderToe(vader).voegKindToe(kind)
                .getRelatie();

        for (BetrokkenheidBericht b : nieuweSituatie.getBetrokkenheden()) {
            PersoonBericht p = b.getPersoon();

            if (p.getBetrokkenheden() == null) {
                p.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
            }

            b.getPersoon().getBetrokkenheden().add(b);
        }
    }

    private void voegHuwelijkToe(final PersoonBericht persoon1, final PersoonBericht persoon2) {
        RelatieBuilder<HuwelijkBericht> relBuilder = new RelatieBuilder<HuwelijkBericht>();
        HuwelijkBericht nieuweSituatie = relBuilder.bouwHuwlijkRelatie().setDatumAanvang(19600320)
                .voegPartnerToe(persoon1).voegPartnerToe(persoon2).getRelatie();
        for (BetrokkenheidBericht b : nieuweSituatie.getBetrokkenheden()) {
            PersoonBericht p = b.getPersoon();

            if (p.getBetrokkenheden() == null) {
                p.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
            }

            b.getPersoon().getBetrokkenheden().add(b);
        }
    }

    private void voegGeregistreerdPartnerschapToe(final PersoonBericht persoon1, final PersoonBericht persoon2) {
        RelatieBuilder<GeregistreerdPartnerschapBericht> relBuilder =
                new RelatieBuilder<GeregistreerdPartnerschapBericht>();
        GeregistreerdPartnerschapBericht nieuweSituatie = relBuilder.bouwGeregistreerdPartnerschap()
                .voegPartnerToe(persoon1).voegPartnerToe(persoon2).getRelatie();
        for (BetrokkenheidBericht b : nieuweSituatie.getBetrokkenheden()) {
            PersoonBericht p = b.getPersoon();

            if (p.getBetrokkenheden() == null) {
                p.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
            }

            b.getPersoon().getBetrokkenheden().add(b);
        }
    }

    private void testEvaluatie(final String input, final String expectedOutput) {
        Context context = new Context(new DefaultSolver());
        Persoon testPersoon = maakTestPersoon();
        context.put("persoon", testPersoon);

        ParserResultaat pr = BRPExpressies.parse(input);
        Assert.assertNotNull("Error in expressie: " + pr.getFoutmelding(), pr.getExpressie());
        EvaluatieResultaat evaluatieResultaat = pr.getExpressie().evalueer(context);
        Assert.assertTrue("Evaluatie gefaald: " + evaluatieResultaat.getFout(), evaluatieResultaat.succes());
        String actualOutput = evaluatieResultaat.getExpressie().alsFormeleString();
        Assert.assertEquals(expectedOutput, actualOutput);
    }

    private void testPositive(final String input, final Persoon testPersoon) {
        EvaluatieResultaat evaluatieResultaat = BRPExpressies.evalueer(input, testPersoon);
        Assert.assertTrue("Fout bij evaluatie: " + evaluatieResultaat.getFout(), evaluatieResultaat.succes());
        Assert.assertTrue("Geen boolean expressie", evaluatieResultaat.isBooleanWaarde());
        Assert.assertTrue("FALSE ipv TRUE", evaluatieResultaat.getBooleanWaarde());
    }

    private void testEvaluatieFout(final String input, final Persoon testPersoon,
                                   final EvaluatieFoutCode verwachteFout)
    {
        EvaluatieResultaat evaluatieResultaat = BRPExpressies.evalueer(input, testPersoon);
        Assert.assertFalse("Fout verwacht bij evaluatie", evaluatieResultaat.succes());
        Assert.assertEquals(verwachteFout, evaluatieResultaat.getFout().getFoutCode());
    }
}
