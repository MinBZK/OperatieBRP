/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;
import static nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator.ONBEKEND;
import static nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator.getDatumString;
import static nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator.testEvaluatie;
import static nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator.testEvaluatieFout;
import static nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator.testEvaluatieHisVolledig;
import static nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator.testNull;
import static nl.bzk.brp.expressietaal.expressies.ExpressieEvaluator.testPositive;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.TestPersonen;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.GetalLiteralExpressie;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieOnderCurateleModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test de evaluatie van expressies.
 */
@SuppressWarnings("MagicNumber")
public final class EvaluatieTest {

    private static final String WAAR = "WAAR";
    private static final String ONWAAR = "ONWAAR";
    private static final String NIEUW = "nieuw";
    private static final String OUD = "oud";

    @Test
    public void testNumberLiterals() {
        testEvaluatie("10", "10");
        testEvaluatie("0", "0");
        testEvaluatie("-2", "-2");
    }

    @Test
    public void testBooleanLiterals() {
        testEvaluatie(WAAR, WAAR);
        testEvaluatie(ONWAAR, ONWAAR);
    }

    @Test
    public void testStringLiterALS() {
        testEvaluatie("\"\"", "\"\"");
        testEvaluatie("\"abc\"", "\"abc\"");
        testEvaluatie("\"     \"", "\"     \"");
    }

    @Test
    public void testNumeriekeBerekeningen() {
        testEvaluatie("10+20", "30");
        testEvaluatie("10-5", "5");
        testEvaluatie("5-10", "-5");
    }

    @Test
    public void testBooleanBerekeningen() {
        testEvaluatie(WAAR, WAAR);
        testEvaluatie("TRUE", WAAR);
        testEvaluatie(ONWAAR, ONWAAR);
        testEvaluatie("FALSE", ONWAAR);
        testEvaluatie("WAAR EN WAAR", WAAR);
        testEvaluatie("WAAR EN ONWAAR", ONWAAR);
        testEvaluatie("ONWAAR EN WAAR", ONWAAR);
        testEvaluatie("ONWAAR EN ONWAAR", ONWAAR);
        testEvaluatie("WAAR OF ONWAAR", WAAR);
        testEvaluatie("ONWAAR OF ONWAAR", ONWAAR);
        testEvaluatie("NIET WAAR", ONWAAR);
        testEvaluatie("NIET ONWAAR", WAAR);
    }

    @Test
    public void testFunctieMAAND() {
        testEvaluatie("MAAND(2000/JAN/10)", "1");
        testEvaluatie("MAAND(1975/08/?)", "8");
        testEvaluatie("MAAND(1980/?/?)", "NULL");
    }

    @Test
    public void testFunctieJAAR() {
        testEvaluatie("JAAR(2000/JAN/10)", "2000");
        testEvaluatie("JAAR(1975/08/?)", "1975");
    }

    @Test
    public void testFunctieDAG() {
        testEvaluatie("DAG(2000/JAN/10)", "10");
        testEvaluatie("DAG(1970/03/?)", "NULL");
    }

    @Test
    public void testFunctieDATUM() {
        testEvaluatie("DATUM(1920, 7, 5)", "1920/07/05");
        testEvaluatie("DATUM(1920, 6, 62)", "1920/08/01");
        testEvaluatie("DATUM(1970, 4, 0)", "1970/03/31");
        testEvaluatie("DATUM(1970, 4, -6)", "1970/03/25");
        testEvaluatie("DATUM(1970, 1, 0)", "1969/12/31");
        testEvaluatie("DATUM(1970, 3, 32)", "1970/04/01");
        testEvaluatie("DATUM(1970, 12, 32)", "1971/01/01");
        testEvaluatie("DATUM(1970, 13, 1)", "1971/01/01");
        testEvaluatie("DATUM(1970, 26, 1)", "1972/02/01");
        testEvaluatie("DATUM(1970, -1, 1)", "1969/11/01");
        testEvaluatie("DATUM(1970, 14, 29)", "1971/03/01");
        testEvaluatie("DATUM(1971, 14, 29)", "1972/02/29");

        // d + ^p^ == DATUM(d.j+p.j, d.m+p.m, d.d+p.d)
    }

    @Test
    public void testFunctieAANTALDAGEN() {
        testEvaluatie("AANTAL_DAGEN(2000)", "366");
        testEvaluatie("AANTAL_DAGEN(1982)", "365");
        testEvaluatie("AANTAL_DAGEN(2001)", "365");
        testEvaluatie("AANTAL_DAGEN(2000,1)", "31");
        testEvaluatie("AANTAL_DAGEN(2000,2)", "29");
        testEvaluatie("AANTAL_DAGEN(1950,1)", "31");
        testEvaluatie("AANTAL_DAGEN(1950,2)", "28");
        testEvaluatie("AANTAL_DAGEN(1950,3)", "31");
        testEvaluatie("AANTAL_DAGEN(1950,4)", "30");
        testEvaluatie("AANTAL_DAGEN(1950,5)", "31");
        testEvaluatie("AANTAL_DAGEN(1950,6)", "30");
        testEvaluatie("AANTAL_DAGEN(1950,7)", "31");
        testEvaluatie("AANTAL_DAGEN(1950,8)", "31");
        testEvaluatie("AANTAL_DAGEN(1950,9)", "30");
        testEvaluatie("AANTAL_DAGEN(1950,10)", "31");
        testEvaluatie("AANTAL_DAGEN(1950,11)", "30");
        testEvaluatie("AANTAL_DAGEN(1950,12)", "31");
        testEvaluatie("AANTAL_DAGEN(NULL,10)", "NULL");
    }

    @Test
    public void testFunctieLAATSTEDAG() {
        testEvaluatie("LAATSTE_DAG(2000)", "2000/12/31");
        testEvaluatie("LAATSTE_DAG(1940)", "1940/12/31");
        testEvaluatie("LAATSTE_DAG(2000,1)", "2000/01/31");
        testEvaluatie("LAATSTE_DAG(2000,2)", "2000/02/29");
        testEvaluatie("LAATSTE_DAG(1950,1)", "1950/01/31");
        testEvaluatie("LAATSTE_DAG(1950,2)", "1950/02/28");
        testEvaluatie("LAATSTE_DAG(1950,6)", "1950/06/30");
        testEvaluatie("LAATSTE_DAG(NULL, 2)", "NULL");
        testEvaluatie("LAATSTE_DAG(1970, NULL)", "NULL");
        testEvaluatie("DATUM(1975, 1, AANTAL_DAGEN(1975)) = LAATSTE_DAG(1975)", WAAR);
        testEvaluatie("DATUM(1975, 1, AANTAL_DAGEN(1975, 1)) = LAATSTE_DAG(1975, 1)", WAAR);
    }

    @Test
    public void testDatumBerekeningen() {
        testEvaluatie("2013/04/20 + 10", "2013/04/30");
        testEvaluatie("2013/04/20 + 0", "2013/04/20");
        testEvaluatie("2013/04/20 + -5", "2013/04/15");
        testEvaluatie("2013/04/20 + 11", "2013/05/01");
        testEvaluatie("2013/04/20 - 19", "2013/04/01");
        testEvaluatie("2013/04/20 - 0", "2013/04/20");
        testEvaluatie("2013/04/20 - -5", "2013/04/25");
        testEvaluatie("2013/04/20 - 20", "2013/03/31");
    }

    @Test
    public void testDatumtijd() {
        testEvaluatie("2013/04/05/12/00/30", "2013/04/05/12/00/30");
        testEvaluatie("2013/04/05/12/00/30 > 2013/04/05/12/00/00", WAAR);
        testEvaluatie("2013/04/05/12/00/30 < 2013/04/05/12/00/00", ONWAAR);
        testEvaluatie("2013/04/05 > 2013/04/04", WAAR);
        testEvaluatie("2013/04/05 > 2013/04/05/12/00/00", ONWAAR);
        testEvaluatie("2013/04/05 > 2013/04/05/00/00/00", ONWAAR);
        testEvaluatie("2013/04/05 < 2013/04/05/00/00/00", ONWAAR);
        testEvaluatie("2013/04/06 > 2013/04/05/00/00/00", WAAR);
        testEvaluatie("2013/04/06 < 2013/04/05/00/00/00", ONWAAR);
        testEvaluatie("2013/04/06 >= 2013/04/05/00/00/00", WAAR);
        testEvaluatie("2013/04/06 >= 2013/04/06/00/00/00", WAAR);
        testEvaluatie("2014/04/? >= 2013/04/06/00/00/00", WAAR);
        testEvaluatie("2014/04/? >= 2015/04/06/00/00/00", ONWAAR);
        testEvaluatie("2014/?/? >= 2015/04/06/00/00/00", ONWAAR);
        testEvaluatie("2018/?/? >= 2015/04/06/00/00/00", WAAR);
        testEvaluatie("?/?/? >= 2015/04/06/00/00/00", ONBEKEND);
        testEvaluatie("JAAR(2015/04/06/00/00/00)", "2015");
        testEvaluatie("MAAND(2015/04/06/00/00/00)", "4");
        testEvaluatie("DAG(2015/04/06/00/00/00)", "6");
    }

    @Test
    public void testFunctieVANDAAG() {
        testEvaluatie("VANDAAG()", getDatumString(new DateTime()));
        testEvaluatie("VANDAAG(0)", getDatumString(new DateTime()));
        testEvaluatie("VANDAAG(1)", getDatumString(new DateTime().plusYears(1)));
        testEvaluatie("VANDAAG(-1)", getDatumString(new DateTime().minusYears(1)));
        testEvaluatie("VANDAAG(100)", getDatumString(new DateTime().plusYears(100)));
    }

    @Test
    public void testLijsten() {
        testEvaluatie("{1}", "{1}");
        testEvaluatie("{1, 2, 3}", "{1, 2, 3}");
        testEvaluatie("{}", "{}");
        testEvaluatie("{1+1}", "{2}");
        testEvaluatie("{0,1+1,1+2}", "{0, 2, 3}");
        testEvaluatie("1 IN {1, 2, 3}", WAAR);
        testEvaluatie("2 IN {1, 2, 3}", WAAR);
        testEvaluatie("3 IN {1, 2, 3}", WAAR);
        testEvaluatie("4 IN {1, 2, 3}", ONWAAR);
        testEvaluatie("4 IN {}", ONWAAR);
        testEvaluatie("{1,2} + {3,4}", "{1, 2, 3, 4}");
        testEvaluatie("{} + {3,4}", "{3, 4}");
        testEvaluatie("{1} + {2} + {}", "{1, 2}");
        testEvaluatie("{} + {} + {}", "{}");
    }

    @Test
    public void testPeriodes() {
        testEvaluatie("^0/0/1 + ^0/1/0", "^0/1/1");
        testEvaluatie("^110/5/1 + ^20/1/0", "^130/6/1");
        testEvaluatie("^0/0/1 - ^0/1/0", "^0/-1/1");
        testEvaluatie("^20", "^20/0/0");
        testEvaluatie("1980/3/1 + ^0/0/6", "1980/03/07");
        testEvaluatie("1980/3/1 + ^0/1/0", "1980/04/01");
        testEvaluatie("1980/5/31 + ^0/1/0", "1980/07/01");
        testEvaluatie("1980/3/1 - ^3/0/0", "1977/03/01");
        testEvaluatie("1980/3/1 + ^5", "1985/03/01");
        testEvaluatie("1980/3/1 + ^0/0/40", "1980/04/10");
        testEvaluatie("1980/3/1 + 40", "1980/04/10");
        testEvaluatie("1980/3/1 - 40", "1980/01/21");

        testEvaluatie("1980/MRT/01 + ^0/1/0", "1980/04/01");
        testEvaluatie("1980/FEB/28 + ^0/1/0", "1980/03/28");
        testEvaluatie("1980/JAN/31 + ^0/1/0", "1980/03/02");
        testEvaluatie("1980/MEI/31 + ^0/1/0", "1980/07/01");
        testEvaluatie("1980/MEI/31 + ^0/0/30", "1980/06/30");
        testEvaluatie("1980/MEI/31 + ^0/0/31", "1980/07/01");

        testEvaluatie("2013/04/20 + 100 = 2013/04/20 + ^0/0/100", WAAR);
        testEvaluatie("1993/04/26 - ^35/2/10", "1958/02/16");
        testEvaluatie("1993/04/26 - ^35/0/0", "1958/04/26");

        testEvaluatie("2013/02/28 + ^0/0/29", "2013/03/29");
        testEvaluatie("2013/02/28 + ^0/1/1", "2013/03/29");
        testEvaluatie("(2013/02/28 + ^0/1/0) + ^0/0/1", "2013/03/29");

        testEvaluatie("2000/02/28 + ^0/0/2", "2000/03/01");
        testEvaluatie("2001/02/28 + ^0/0/2", "2001/03/02");
        testEvaluatie("2000/02/28 + 2", "2000/03/01");
        testEvaluatie("2001/02/28 + 2", "2001/03/02");

        for (int j = -10; j < 10; j += 7) {
            for (int m = -20; m < 20; m += 7) {
                for (int d = -40; d < 40; d += 11) {
                    final String exp = String.format("1950/01/01 + ^%d/%d/%d = DATUM(1950+%d, 1+%d, 1+%d)",
                            j, m, d, j, m, d);
                    testEvaluatie(exp, WAAR);
                }
            }
        }

        testEvaluatie("1950/01/01 + ^23/10/100 = DATUM(1950+23, 1+10, 1+100)", WAAR);
    }

    @Test
    public void testGetalVergelijkingen() {
        testEvaluatie("10 > 2", WAAR);
        testEvaluatie("12 = 12", WAAR);
        testEvaluatie("-2 < -1", WAAR);
        testEvaluatie("0 < -5", ONWAAR);
        testEvaluatie("1 = 1", WAAR);
        testEvaluatie("1 <> 2", WAAR);
    }

    @Test
    public void testBooleanVergelijkingen() {
        testEvaluatie("WAAR = WAAR", WAAR);
        testEvaluatie("WAAR = ONWAAR", ONWAAR);
        testEvaluatie("ONWAAR = WAAR", ONWAAR);
        testEvaluatie("ONWAAR = ONWAAR", WAAR);
        testEvaluatie("WAAR > WAAR", ONWAAR);
        testEvaluatie("WAAR > ONWAAR", WAAR);
        testEvaluatie("ONWAAR > WAAR", ONWAAR);
        testEvaluatie("ONWAAR > ONWAAR", ONWAAR);
        testEvaluatie("WAAR < WAAR", ONWAAR);
        testEvaluatie("WAAR < ONWAAR", ONWAAR);
        testEvaluatie("ONWAAR < WAAR", WAAR);
        testEvaluatie("ONWAAR < ONWAAR", ONWAAR);
        testEvaluatie("WAAR <> WAAR", ONWAAR);
        testEvaluatie("WAAR <> ONWAAR", WAAR);
        testEvaluatie("ONWAAR <> WAAR", WAAR);
        testEvaluatie("ONWAAR <> ONWAAR", ONWAAR);
        testEvaluatie("WAAR >= WAAR", WAAR);
        testEvaluatie("WAAR >= ONWAAR", WAAR);
        testEvaluatie("ONWAAR >= WAAR", ONWAAR);
        testEvaluatie("ONWAAR >= ONWAAR", WAAR);
        testEvaluatie("WAAR <= WAAR", WAAR);
        testEvaluatie("WAAR <= ONWAAR", ONWAAR);
        testEvaluatie("ONWAAR <= WAAR", WAAR);
        testEvaluatie("ONWAAR <= ONWAAR", WAAR);
    }

    @Test
    public void testLijstVergelijkingen() {
        testEvaluatie("{} = {}", WAAR);
        testEvaluatie("{0} = {0}", WAAR);
        testEvaluatie("{1} = {2}", ONWAAR);
        testEvaluatie("{1,2} = {1,2}", WAAR);
        testEvaluatie("{1,2} = {2,1}", WAAR);
        testEvaluatie("{1,\"abc\"} = {\"abc\",1}", WAAR);
        testEvaluatie("{1,2,3,4} = {1,2,3,4}", WAAR);
        testEvaluatie("{1,2,3,4} = {3,4,1,2}", WAAR);
        testEvaluatie("{1,2,3,4} = {4,3,2,1}", WAAR);
        testEvaluatie("{1,1,1,3} = {1,3,1,1}", WAAR);
        testEvaluatie("{1,2,3,4} = {1,2,3}", ONWAAR);
        testEvaluatie("{1,2,3,4} = {3,5,1,2}", ONWAAR);
        testEvaluatie("{1,1,1,3} = {1,3,1}", ONWAAR);

        testEvaluatie("{} <> {}", ONWAAR);
        testEvaluatie("{0} <> {0}", ONWAAR);
        testEvaluatie("{1} <> {2}", WAAR);
        testEvaluatie("{1,2} <> {1,2}", ONWAAR);
        testEvaluatie("{1,2} <> {2,1}", ONWAAR);
        testEvaluatie("{1,\"abc\"} <> {\"abc\",1}", ONWAAR);
        testEvaluatie("{1,2,3,4} <> {1,2,3,4}", ONWAAR);
        testEvaluatie("{1,2,3,4} <> {3,4,1,2}", ONWAAR);
        testEvaluatie("{1,2,3,4} <> {4,3,2,1}", ONWAAR);
        testEvaluatie("{1,1,1,3} <> {1,3,1,1}", ONWAAR);
        testEvaluatie("{1,2,3,4} <> {1,2,3}", WAAR);
        testEvaluatie("{1,2,3,4} <> {3,5,1,2}", WAAR);
        testEvaluatie("{1,1,1,3} <> {1,3,1}", WAAR);

        testEvaluatie("{1,2,3} < {1,2,3,4}", WAAR);
        testEvaluatie("{1,2,3,4} < {3,4,1,2}", ONWAAR);
        testEvaluatie("{1,2,3,4} <= {4,3,2,1}", WAAR);
        testEvaluatie("{1,3} <= {1,3,1,2}", WAAR);
        testEvaluatie("{} < {1,2,3}", WAAR);
        testEvaluatie("{1,2,3,4} <= {}", ONWAAR);

        testEvaluatie("{1,2,3,4} < {1,2,3}", ONWAAR);
        testEvaluatie("{1,2,3,4} > {3,4,1,2}", ONWAAR);
        testEvaluatie("{1,2,3,4} >= {4,3,2,1}", WAAR);
        testEvaluatie("{1,3,1,2} >= {1,1}", WAAR);
        testEvaluatie("{} > {1,2,3}", ONWAAR);
        testEvaluatie("{1,2,3,4} >= {}", WAAR);
    }

    @Test
    public void testBrpObjectVergelijkingen() {
        testEvaluatie("persoon = persoon", WAAR);
        testEvaluatie("ALLE(persoon.adressen, a, ER_IS(persoon.adressen, b, a = b))", WAAR);

        final PersoonHisVolledig testPersoon = TestPersonen.maakTestPersoon();

        testEvaluatie("persoon = persoon", WAAR, testPersoon);

        testEvaluatie("oud = oud WAARBIJ oud = VIEW(persoon, 1948/1/1), nieuw = VIEW(persoon, 1960/1/1)", WAAR,
                testPersoon);
        testEvaluatie("nieuw = nieuw WAARBIJ oud = VIEW(persoon, 1948/1/1), nieuw = VIEW(persoon, 1960/1/1)", WAAR,
                testPersoon);
        testEvaluatie("oud = nieuw WAARBIJ oud = VIEW(persoon, 1948/1/1), nieuw = VIEW(persoon, 1960/1/1)", ONWAAR,
                testPersoon);
    }

    @Test
    public void testWildcards() {
        testEvaluatie("\"Regex\" %= \"Reg*\"", WAAR);
        testEvaluatie("\"Regex\" %= \"*\"", WAAR);
        testEvaluatie("\"Regex\" %= \"?egex\"", WAAR);
        testEvaluatie("\"Regex\" %= \"Re?*ex\"", WAAR);
        testEvaluatie("\"Regex\" %= \"Regzz*\"", ONWAAR);
        testEvaluatie("\"\" %= \"?*\"", ONWAAR);
        testEvaluatie("\"Regex\" %= \"Re??ex\"", ONWAAR);

        testEvaluatie("\"Jan\" %= \"J*\"", WAAR);
        testEvaluatie("\"Jan\" %= \"J?n\"", WAAR);
        testEvaluatie("\"Jan\" %= \"Jan\"", WAAR);
        testEvaluatie("\"Jansen\" %= \"Jan*\"", WAAR);

        testEvaluatie("\"Reg.ex\" %= \"Reg.*\"", WAAR);
        testEvaluatie("\"{Reg.ex\" %= \"{Reg.*\"", WAAR);
        testEvaluatie("\"Reg^.ex=\" %= \"Reg^.*=\"", WAAR);
    }

    @Test
    public void testFunctieALLE() {
        testEvaluatie("ALLE({1,2,3,4}, x, x < 5)", WAAR);
        testEvaluatie("ALLE({1,2,3,4}, x, x < 2)", ONWAAR);
        testEvaluatie("ALLE({}, x, x = 2)", WAAR);
        testEvaluatie("ALLE(NULL, x, x = 2)", "NULL");
        testEvaluatie("ALLE({1}, x, NULL)", "NULL");
    }

    @Test
    public void testFunctieERIS() {
        testEvaluatie("ER_IS({1,2,3,4}, x, 1 + x = 2)", WAAR);
        testEvaluatie("ER_IS({1,2,3,4}, x, x = 5)", ONWAAR);
        testEvaluatie("ER_IS({1,2,3,4}, x, x + x = 8)", WAAR);
        testEvaluatie("ER_IS({}, x, x=2)", ONWAAR);
        testEvaluatie("ER_IS(NULL, x, x=2)", "NULL");
        testEvaluatie("ER_IS({1}, x, NULL)", "NULL");
    }

    @Test
    public void testFunctieFILTER() {
        testEvaluatie("FILTER({1,2,3,4}, x, x = 2)", "{2}");
        testEvaluatie("FILTER({}, x, x > 0)", "{}");
        testEvaluatie("FILTER({1,2,3,4}, x, x <= 2)", "{1, 2}");
        testEvaluatie("FILTER({1,2,3,4}, x, x > 2)", "{3, 4}");
        testEvaluatie("AANTAL(FILTER(KINDEREN(), k, k.geboorte.datum > 1900/01/01))", "2");
        testEvaluatie("FILTER(KINDEREN(), k, k.geslachtsaanduiding.geslachtsaanduiding = \"M\")", "{@Persoon}");
    }

    @Test
    public void testFunctieMAP() {
        testEvaluatie("MAP({1,2,3}, x, 0)", "{0, 0, 0}");
        testEvaluatie("MAP({1,2,3}, x, x)", "{1, 2, 3}");
        testEvaluatie("MAP({1,2,3}, x, x+x)", "{2, 4, 6}");
        testEvaluatie("\"M\" IN MAP(KINDEREN(), k, k.geslachtsaanduiding.geslachtsaanduiding)", WAAR);
        testEvaluatie("\"M\" IN MAP(KINDEREN(), k, k.geslachtsaanduiding.geslachtsaanduiding) "
                + "EN \"V\" IN MAP(KINDEREN(), k, k.geslachtsaanduiding.geslachtsaanduiding) ", WAAR);
        testEvaluatie("MAP( { { 1 }, { 2 }, 3 }, x, x + x )", "{6, {1, 1}, {2, 2}}");
    }

    @Test
    public void testFunctieRMAP() {
        testEvaluatie("RMAP({}, x, 0)", "{}");
        testEvaluatie("RMAP({1}, x, x+10)", "{11}");
        testEvaluatie("RMAP({1,2,3}, x, x+10)", "{11, 12, 13}");
        testEvaluatie("RMAP({{1}}, x, x+10)", "{{11}}");
        testEvaluatie("RMAP({{1},{2},{{3,4},5}}, x, x+10)", "{{11}, {12}, {{13, 14}, 15}}");
        testEvaluatie("RMAP( { { 1 }, { 2 }, 3 }, x, x + x )", "{6, {2}, {4}}");
    }

    @Test
    public void testFunctieAANTAL() {
        testEvaluatie("AANTAL({1,2,3})", "3");
        testEvaluatie("AANTAL({})", "0");
        testEvaluatie("AANTAL({1, {2, 3}})", "2");
        testEvaluatie("AANTAL(KINDEREN())", "2");
        testEvaluatie("AANTAL(NULL)", "NULL");
    }

    @Test
    public void testFunctiePLATTELIJST() {
        testEvaluatie("PLATTE_LIJST({})", "{}");
        testEvaluatie("PLATTE_LIJST({1})", "{1}");
        testEvaluatie("PLATTE_LIJST({1,2,3})", "{1, 2, 3}");
        testEvaluatie("PLATTE_LIJST({{}})", "{}");
        testEvaluatie("PLATTE_LIJST({{},{}})", "{}");
        testEvaluatie("PLATTE_LIJST({1,{2,3},{4}})", "{1, 2, 3, 4}");
        testEvaluatie("PLATTE_LIJST({{1},{2,{},3},{{{4}}}})", "{1, 2, 3, 4}");
        testEvaluatie("PLATTE_LIJST(NULL)", "NULL");
        testEvaluatie("PLATTE_LIJST({NULL})", "{NULL}");
    }

    @Test
    public void testFunctieALS() {
        testEvaluatie("ALS(WAAR, 1, 2)", "1");
        testEvaluatie("ALS(ONWAAR, 1, 2)", "2");
        testEvaluatie("ALS(WAAR, {1}, {1,2})", "{1}");
        testEvaluatie("ALS(ONWAAR, {1}, {1,2})", "{1, 2}");
        testEvaluatie("ALS(WAAR, NULL, {1,2})", "NULL");
        testEvaluatie("ALS(ONWAAR, NULL, {1,2})", "{1, 2}");
        testEvaluatie("ALS(WAAR, {1}, NULL)", "{1}");
        testEvaluatie("ALS(ONWAAR, {1}, NULL)", "NULL");
        testEvaluatie("AANTAL(ALS(WAAR, {1}, {1,2}))", "1");
        testEvaluatie("AANTAL(ALS(ONWAAR, {1}, {1,2}))", "2");
        testEvaluatie("AANTAL(ALS(WAAR, NULL, {1,2}))", "NULL");
        testEvaluatie("AANTAL(ALS(ONWAAR, NULL, {1,2}))", "2");
        testEvaluatie("AANTAL(ALS(WAAR, {1}, NULL))", "1");
        testEvaluatie("AANTAL(ALS(ONWAAR, {1}, NULL))", "NULL");
    }

    @Test
    public void testFunctieVIEW() {
        final PersoonHisVolledig testPersoon = TestPersonen.maakTestPersoon();
        testEvaluatie("VIEW(persoon, 1980/01/01)", "@Persoon");
        testEvaluatie("VIEW(persoon, 1980/01/01)", "@Persoon", testPersoon);
        testEvaluatie("p.voornamen WAARBIJ p = VIEW(persoon, 1980/01/01)", "{@Voornaam, @Voornaam}");
        testEvaluatie("p.voornamen WAARBIJ p = VIEW(persoon, 1980/01/01)", "{@Voornaam, @Voornaam}",
                testPersoon);
        testEvaluatie("p.voornamen WAARBIJ p = VIEW(persoon, 1950/01/01)", "{@Voornaam, @Voornaam}",
                testPersoon);
        testEvaluatie("RMAP(p.voornamen, v, v.naam) WAARBIJ p =  VIEW( persoon, 1980/01/01)",
                "{\"Jack\", \"Johnson\"}");
        testEvaluatie("RMAP(p.voornamen, v, v.naam) WAARBIJ p =  VIEW( persoon, 1950/01/01)",
                "{\"Jack\", \"Johnson\"}");
        testEvaluatie("RMAP(p.voornamen, v, v.naam) WAARBIJ p =  VIEW( persoon, 1980/01/01)",
                "{\"Jack\", \"Johnson\"}", testPersoon);
        testEvaluatie("RMAP(p.voornamen, v, v.naam) WAARBIJ p =  VIEW( persoon, 1950/01/01)",
            "{\"Jacco\", \"Johnson\"}", testPersoon);
        testEvaluatie("p.samengestelde_naam.voornamen WAARBIJ p =  VIEW( persoon, 1980/01/01)",
            "\"Jack Johnson\"", testPersoon);
        testEvaluatie("p.samengestelde_naam.voornamen WAARBIJ p =  VIEW( persoon, 1950/01/01)",
            "\"Jacco Johnson\"", testPersoon);
    }

    @Test
    public void testLijstenWildcards() {
        testEvaluatie("\"abc\" IN% {\"abc\", \"de*\", \"kl?\", \"\"}", WAAR);
        testEvaluatie("\"def\" IN% {\"abc\", \"de*\", \"kl?\", \"\"}", WAAR);
        testEvaluatie("\"def\" IN {\"abc\", \"de*\", \"kl?\", \"\"}", ONWAAR);
        testEvaluatie("\"defg\" IN% {\"abc\", \"de*\", \"kl?\", \"\"}", WAAR);
        testEvaluatie("\"de\" IN% {\"abc\", \"de*\", \"kl?\", \"\"}", WAAR);
        testEvaluatie("\"klm\" IN% {\"abc\", \"de*\", \"kl?\", \"\"}", WAAR);
        testEvaluatie("\"abcd\" IN% {\"abc\", \"de*\", \"kl?\", \"\"}", ONWAAR);
        testEvaluatie("\"xyz\" IN% {\"abc\", \"de*\", \"kl?\", \"\"}", ONWAAR);
        testEvaluatie("\"klmn\" IN% {\"abc\", \"de*\", \"kl?\", \"\"}", ONWAAR);
        testEvaluatie("\"\" IN% {\"abc\", \"de*\", \"kl?\", \"\"}", WAAR);
    }

    @Test
    public void testAttributen() {
        testEvaluatie("persoon.identificatienummers.burgerservicenummer", String.valueOf(TestPersonen.OPA_BSN));
        testEvaluatie("identificatienummers.burgerservicenummer", String.valueOf(TestPersonen.OPA_BSN));
        testEvaluatie("bsn", String.valueOf(TestPersonen.OPA_BSN));
        testEvaluatie("persoon.geboorte.datum", "1943/11/21");
        testEvaluatie("geboorte.datum", "1943/11/21");
        testEvaluatie("persoon.geslachtsaanduiding.geslachtsaanduiding", "\"M\"");
        testEvaluatie("geboorte.gemeente", "363");
        testEvaluatie("overlijden.woonplaatsnaam", "\"Amsterdam\"");
        testEvaluatie("overlijden.gemeente", "363");
        testEvaluatie("overlijden.datum", "2002/03/19");
        testEvaluatie("persoon.bijhouding.onverwerkt_document_aanwezig", "NULL");
    }

    @Test
    public void testAttribuutCollecties() {
        testEvaluatie("persoon.nationaliteiten", "{@Nationaliteit, @Nationaliteit}");
        testEvaluatie("AANTAL(persoon.nationaliteiten)", "2");
        testEvaluatie("AANTAL(persoon.voornamen)", "2");
        testEvaluatie("persoon.adressen", "{@Adres}");
        testEvaluatie("persoon.voornamen", "{@Voornaam, @Voornaam}");
        testEvaluatie("FILTER(persoon.adressen, a, WAAR)", "{@Adres}");
        testEvaluatie("FILTER(persoon.adressen, a, a.postcode = \"1234AB\")", "{@Adres}");
        testEvaluatie("FILTER(persoon.adressen, a, a.postcode %= \"12*\")", "{@Adres}");
        testEvaluatie("MAP(persoon.adressen, a, a.huisnummer)", "{1}");
    }

    @Test
    public void testAttribuutcodes() {
        testEvaluatie("[geboorte.datum]", "[geboorte.datum]");
        testEvaluatie("[adressen]", "[adressen]");
        testEvaluatie("[land_gebied]", "[land_gebied]");
        testEvaluatieFout("[lengte_snor]", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
    }

    @Test
    public void testReferenties() {
        // Attributen
        testEvaluatie("$bsn", "$nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut");
        testEvaluatie("{ $bsn, $geboorte.datum }", "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut, "
                + "$nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut}");
        testEvaluatie("MAP(adressen, a, a.postcode)", "{\"1234AB\"}");
        testEvaluatie("MAP(adressen, a, $a.postcode)", "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut}");

        // Groepen
        testEvaluatie("$geboorte", "$nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel");
    }

    @Test
    public void testReferentiesHisvolledig() {
        // Attributen
        testEvaluatieHisVolledig("$bsn",
            "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut, "
                + "$nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut, "
                + "$nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut}");

        // Groepen
        testEvaluatieHisVolledig("$geboorte", "{$nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel}");
        testEvaluatieHisVolledig("$persoon.geboorte", "{$nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel}");
    }

    @Test
    public void testFunctieKINDEREN() {
        testEvaluatie("KINDEREN(persoon)", "{@Persoon, @Persoon}");
        testEvaluatie("KINDEREN()", "{@Persoon, @Persoon}");
        testEvaluatie("AANTAL(KINDEREN(persoon))", "2");
        testEvaluatie("AANTAL(KINDEREN())", "2");
        testEvaluatie("ALLE(KINDEREN(), k, WAAR)", WAAR);
        testEvaluatie("ALLE(KINDEREN(), k, ONWAAR)", ONWAAR);
        testEvaluatie("ER_IS(KINDEREN(), k, WAAR)", WAAR);
        testEvaluatie("ER_IS(KINDEREN(), k, ONWAAR)", ONWAAR);
    }

    @Test
    public void testFunctieHUWELIJKEN() {
        testEvaluatie("HUWELIJKEN()", "{@Huwelijk}");
        testEvaluatie("HUWELIJKEN(persoon)", "{@Huwelijk}");
    }

    @Test
    public void testFunctiePARTNERSCHAPPEN() {
        testEvaluatie("PARTNERSCHAPPEN()", "{@GeregistreerdPartnerschap}");
        testEvaluatie("ER_IS(KINDEREN(), k, AANTAL(PARTNERSCHAPPEN(k)) = 1)", WAAR);
        testEvaluatie("RMAP(PARTNERSCHAPPEN(),v,v.gemeente_aanvang)", "{344}", new PersoonView(TestPersonen.bouwZoon()));
    }

    @Test
    public void testFunctieOUDERS() {
        testEvaluatie("AANTAL(OUDERS())", "2");
        testEvaluatie("OUDERS()", "{@Persoon, @Persoon}");
        testEvaluatie("OUDERS(persoon)", "{@Persoon, @Persoon}");
    }

    @Test
    public void testFunctieERKENNER() {
        testEvaluatie("AANTAL(ERKENNERS())", "1");
        testEvaluatie("ERKENNERS()", "{@Persoon}");
        testEvaluatie("ERKENNERS(persoon)", "{@Persoon}");
        testEvaluatie("RMAP(ERKENNERS(), p, p.identificatienummers.bsn)", String.format("{%s}", TestPersonen.SCHOONDOCHTER_BSN));
    }

    @Test
    public void testFunctieINSTEMMER() {
        testEvaluatie("AANTAL(INSTEMMERS())", "1");
        testEvaluatie("INSTEMMERS()", "{@Persoon}");
        testEvaluatie("INSTEMMERS(persoon)", "{@Persoon}");
        testEvaluatie("RMAP(INSTEMMERS(), p, p.identificatienummers.bsn)", String.format("{%s}", TestPersonen.KLEINZOON_BSN));
    }

    @Test
    public void testFunctieNAAMGEVER() {
        testEvaluatie("AANTAL(NAAMGEVERS())", "1");
        testEvaluatie("NAAMGEVERS()", "{@Persoon}");
        testEvaluatie("NAAMGEVERS(persoon)", "{@Persoon}");
        testEvaluatie("RMAP(NAAMGEVERS(), p, p.identificatienummers.bsn)", String.format("{%s}", TestPersonen.SUPEROMA_BSN));
    }

    @Test
    public void testFunctieNAAMSKEUZEPARTNER() {
        testEvaluatie("AANTAL(NAAMSKEUZEPARTNERS())", "1");
        testEvaluatie("NAAMSKEUZEPARTNERS()", "{@Persoon}");
        testEvaluatie("NAAMSKEUZEPARTNERS(persoon)", "{@Persoon}");
        testEvaluatie("RMAP(NAAMSKEUZEPARTNERS(), p, p.identificatienummers.bsn)", String.format("{%s}", TestPersonen.SUPEROMA_BSN));
    }

    @Test
    public void testFunctieHUWELIJKSPARTNER() {
        testEvaluatie("AANTAL(HUWELIJKSPARTNERS())", "2");
        testEvaluatie("HUWELIJKSPARTNERS()", "{@Persoon, @Persoon}");
        testEvaluatie("HUWELIJKSPARTNERS(persoon)", "{@Persoon, @Persoon}");
        testEvaluatie("RMAP(HUWELIJKSPARTNERS(), p, p.identificatienummers.bsn)", String.format("{%s, %s}", TestPersonen.OPA_BSN, TestPersonen.OMA_BSN));
    }

    @Test
    public void testFunctieGEREGISTREERD_PARTNER() {
        testEvaluatie("AANTAL(GEREGISTREERD_PARTNERS())", "2");
        testEvaluatie("GEREGISTREERD_PARTNERS()", "{@Persoon, @Persoon}");
        testEvaluatie("GEREGISTREERD_PARTNERS(persoon)", "{@Persoon, @Persoon}");
        // TODO Testpersonen zouden in omgekeerde volgorde in de set kunnen zitten.
        testEvaluatie("RMAP(GEREGISTREERD_PARTNERS(), p, p.identificatienummers.bsn)", String.format("{%s, %s}", TestPersonen.SUPEROMA_BSN, TestPersonen
                .OPA_BSN));
    }

    @Test
    public void testFunctiePARTNERS() {
        //testEvaluatie("partners()", "{<Persoon>|@Persoon[bsn=222222222]}");

        // TODO bespreken: een persoonview verwijst via betrokkenheden en relaties nog steeds naar persoonhisvolledig,
        // TODO en bij gebrek aan identifiers is er geen andere manier om twee objecten te controleren op gelijkheid.
    }

    @Test
    public void testFunctieGEWIJZIGD() {
        final Persoon testPersoon = new PersoonView(TestPersonen.maakTestPersoon());

        testEvaluatie("GEWIJZIGD(persoon, persoon, [bsn])", ONWAAR);
        testEvaluatieFout("GEWIJZIGD(persoon, 10, [bsn])", testPersoon, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("GEWIJZIGD(persoon, persoon, 10)", testPersoon, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);

        final PersoonHisVolledig historischePersoon = TestPersonen.maakTestPersoon();

        testEvaluatie("persoon.samengestelde_naam.voornamen", "{\"\", \"Jacco Johnson\", \"Jack Johnson\"}", historischePersoon);
        testEvaluatie("oud.samengestelde_naam.voornamen WAARBIJ oud = VIEW(persoon, 1948/1/1)", "\"Jacco Johnson\"", historischePersoon);
        testEvaluatie("nieuw.samengestelde_naam.voornamen WAARBIJ nieuw = VIEW(persoon, 1960/1/1)", "\"Jack Johnson\"", historischePersoon);
        testEvaluatie("GEWIJZIGD(oud, nieuw, [samengestelde_naam.geslachtsnaamstam]) WAARBIJ oud = VIEW(persoon, 1948/1/1), nieuw = VIEW(persoon, 1960/1/1)",
                ONWAAR, historischePersoon);
        testEvaluatie("GEWIJZIGD(oud, nieuw, [samengestelde_naam.voornamen]) WAARBIJ oud = VIEW(persoon, 1948/1/1), nieuw = VIEW(persoon, 1960/1/1)",
                WAAR, historischePersoon);

        testEvaluatie("persoon.adressen", "{@Adres}");
        testEvaluatie("persoon.adressen = persoon.adressen", WAAR);
        testEvaluatie("GEWIJZIGD(persoon, persoon, [adressen])", ONWAAR);

        testEvaluatie("GEWIJZIGD(persoon, persoon, [adressen], [huisnummer])", ONWAAR);
        final Persoon copyPersoon = new PersoonView(TestPersonen.maakTestPersoon());

        final Context context = new Context();
        context.definieer(OUD, new BrpObjectExpressie(testPersoon, ExpressieType.PERSOON));
        final PersoonAdresStandaardGroep standaard = copyPersoon.getAdressen().iterator().next().getStandaard();
        final HuisnummerAttribuut huisnummer = standaard.getHuisnummer();

        ReflectionTestUtils.setField(standaard, "huisnummer", new HuisnummerAttribuut(huisnummer.getWaarde() + 1));
        context.definieer(NIEUW, new BrpObjectExpressie(copyPersoon, ExpressieType.PERSOON));
        testEvaluatie("GEWIJZIGD(oud, nieuw, [adressen], [huisnummer])", WAAR, context);

        testEvaluatie("GEWIJZIGD(oud, nieuw, [adressen]) WAARBIJ oud = VIEW(persoon, 1956/1/1), nieuw = VIEW(persoon, 1957/1/1)", ONWAAR,
            historischePersoon);
        testEvaluatie("GEWIJZIGD(oud, nieuw, [adressen]) WAARBIJ oud = VIEW(persoon, 1946/1/1), nieuw = VIEW(persoon, 1957/1/1)", WAAR, historischePersoon);

        testEvaluatieFout("GEWIJZIGD(persoon, persoon, [land_gebied])", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
    }

    @Test
    public void testFunctieGewijzigdParameters() {
        final Persoon testPersoon = new PersoonView(TestPersonen.maakTestPersoon());

        testParsingOk("GEWIJZIGD(persoon, persoon, [bsn])", testPersoon);
        testParsingOk("GEWIJZIGD(persoon, persoon, [samengestelde_naam.adellijke_titel])", testPersoon);
        testParsingOk("GEWIJZIGD(persoon, persoon, [adressen])", testPersoon);
        testParsingOk("GEWIJZIGD(persoon, persoon, [adressen], [huisnummer])", testPersoon);

        testParsingOk("GEWIJZIGD(HUWELIJKEN(persoon), HUWELIJKEN(persoon), [gemeente_aanvang])", testPersoon);
        testParsingOk(
            "GEWIJZIGD(ALS(persoon.migratie.soort=\"E\",persoon.migratie.land_gebied,NULL), ALS(persoon.migratie.soort=\"E\",persoon.migratie.land_gebied,NULL)))",
            testPersoon);

        testParsingError("GEWIJZIGD(HUWELIJKEN(persoon))", testPersoon);
        testParsingError("GEWIJZIGD(persoon, persoon, [onbekend_attribuut])", testPersoon);
        testParsingError("GEWIJZIGD(HUWELIJKEN(persoon),HUWELIJKEN(persoon))", testPersoon);
        testParsingError("GEWIJZIGD(persoon, persoon, [samengestelde_naam], [adellijke_titel])", testPersoon);
        testParsingError("GEWIJZIGD(persoon, persoon, [samengestelde_naam], [adellijke_titel], [adellijke_titel])", testPersoon);
        testParsingError("GEWIJZIGD(HUWELIJKEN(persoon), HUWELIJKEN(persoon), [gemeente_aanvang], [huisnummer])", testPersoon);

        // appels en peren vergelijken
        final Context context = new Context();
        context.definieer(OUD, new BrpObjectExpressie(testPersoon, ExpressieType.PERSOON));
        context.definieer(NIEUW, new BrpObjectExpressie(testPersoon, ExpressieType.REISDOCUMENT));

        final ParserResultaat resultaat = BRPExpressies.parse("GEWIJZIGD(oud, nieuw, [bsn])");
        if (resultaat.getExpressie() != null) {
            Assert.assertTrue(BRPExpressies.evalueer(resultaat.getExpressie(), context).isFout());
        }
    }

    @Test
    public void testSleutelrubriekResultaat() {
    	String expressie = "GEWIJZIGD(ALS(oud.migratie.soort=\"E\",oud.migratie.land_gebied,NULL), ALS(nieuw.migratie.soort=\"E\",nieuw.migratie.land_gebied,NULL))";

        final Context context = new Context();
        context.declareer(OUD, ExpressieType.PERSOON);
        context.declareer(NIEUW, ExpressieType.PERSOON);

        final ParserResultaat resultaat = BRPExpressies.parse(expressie, context);
        if(resultaat.getExpressie() == null) {
            Assert.fail(resultaat.getFoutmelding());
        }

        final Persoon testPersoon = new PersoonView(TestPersonen.maakTestPersoon());
    	context.definieer(OUD, new BrpObjectExpressie(testPersoon, ExpressieType.PERSOON));
        context.definieer(NIEUW, new BrpObjectExpressie(testPersoon, ExpressieType.PERSOON));

		Assert.assertFalse(BRPExpressies.evalueer(resultaat.getExpressie(), context).alsBoolean());
    }

    private void testParsingOk(final String invoer, final Persoon testPersoon) {
        final Expressie evaluatieResultaat = BRPExpressies.evalueer(invoer, testPersoon);
        if(evaluatieResultaat.isFout()) {
            Assert.fail("Evaluatie niet ok: " + evaluatieResultaat.alsString());
        }
    }

    private void testParsingError(final String invoer, final Persoon testPersoon) {
        final Expressie evaluatieResultaat = BRPExpressies.evalueer(invoer, testPersoon);
        Assert.assertTrue(evaluatieResultaat.isFout());
    }

    @Test
    public void testFunctieGEWIJZIGDMetLijst() {
        testEvaluatieFout("GEWIJZIGD(persoon, persoon, [samengestelde_naam.geslachtsnaamstam], 10)", null,
            EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout(
            "GEWIJZIGD(persoon, persoon, [samengestelde_naam.geslachtsnaamstam], [samengestelde_naam.geslachtsnaamstam])",
            null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("GEWIJZIGD(persoon, persoon, [adressen], [samengestelde_naam.geslachtsnaamstam])", null,
            EvaluatieFoutCode.INCORRECTE_EXPRESSIE);

        final PersoonHisVolledig historischePersoon = TestPersonen.maakTestPersoon();
        testEvaluatie(
                "GEWIJZIGD(oud, nieuw, [adressen], [huisnummer]) WAARBIJ oud = VIEW(persoon, 1946/1/1), "
                        + "nieuw = VIEW(persoon, 1957/1/1)",
                ONWAAR, historischePersoon);
        testEvaluatie(
            "GEWIJZIGD(oud, nieuw, [adressen], [postcode]) WAARBIJ oud = VIEW(persoon, 1946/1/1), "
                + "nieuw = VIEW(persoon, 1957/1/1)",
            WAAR, historischePersoon);
    }

    @Test
    public void testBetrokkenheden() {
        testEvaluatie("persoon.betrokkenheden",
            "{@Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid}");
        testEvaluatie("MAP(persoon.betrokkenheden, b, b.rol)",
            "{\"E\", \"I\", \"K\", \"N\", \"O\", \"O\", \"P\", \"P\", \"P\"}");

        testEvaluatie("FILTER(persoon.betrokkenheden, b, b.ouderlijk_gezag.ouder_heeft_gezag)",
            "{@Betrokkenheid, @Betrokkenheid}");
        testEvaluatie(
                "MAP(FILTER(persoon.betrokkenheden, b, b.ouderlijk_gezag.ouder_heeft_gezag), b, "
                        + "$b.ouderlijk_gezag.ouder_heeft_gezag)",
                "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut, "
                        + "$nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut}");
        testEvaluatie("MAP(persoon.betrokkenheden, b, b.ouderschap.datum_aanvang_geldigheid)",
                "{NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL}");
    }

    @Test
    public void testFunctieGERELATEERDEBETROKKENHEDEN() {
        final PersoonHisVolledig testPersoon = TestPersonen.maakTestPersoon();

        testEvaluatie("GERELATEERDE_BETROKKENHEDEN()",
                "{@Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid, "
                        + "@Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid}",
                testPersoon);
        testEvaluatie("GERELATEERDE_BETROKKENHEDEN(persoon)",
                "{@Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid, "
                        + "@Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid, @Betrokkenheid}",
                testPersoon);
        testEvaluatie("GERELATEERDE_BETROKKENHEDEN(\"KIND\")", "{@Betrokkenheid, @Betrokkenheid}",
                testPersoon);
        testEvaluatie("MAP(GERELATEERDE_BETROKKENHEDEN(\"KIND\"), b, b.rol)", "{{\"O\"}, {\"O\"}}", testPersoon);
        testEvaluatie("MAP(GERELATEERDE_BETROKKENHEDEN(\"KIND\", \"FAMILIERECHTELIJKE_BETREKKING\"), b, b.rol)",
                "{{\"O\"}, {\"O\"}}",
                testPersoon);
        testEvaluatie(
                "MAP(GERELATEERDE_BETROKKENHEDEN(\"KIND\", \"FAMILIERECHTELIJKE_BETREKKING\", \"OUDER\"), b, b.rol)",
                "{{\"O\"}, {\"O\"}}",
                testPersoon);
        testEvaluatie("MAP(GERELATEERDE_BETROKKENHEDEN(\"OUDER\", \"FAMILIERECHTELIJKE_BETREKKING\"), b, b.rol)",
                "{{\"K\"}, {\"K\"}, {\"O\"}, {\"O\"}}",
                testPersoon);
        testEvaluatie(
                "MAP(GERELATEERDE_BETROKKENHEDEN(\"OUDER\", \"FAMILIERECHTELIJKE_BETREKKING\", \"KIND\"), b, b.rol)",
                "{{\"K\"}, {\"K\"}}",
                testPersoon);
        testEvaluatie("MAP(GERELATEERDE_BETROKKENHEDEN(\"KIND\", \"HUWELIJK\"), b, b.rol)", "{}",
                testPersoon);
        testEvaluatie("MAP(GERELATEERDE_BETROKKENHEDEN(\"PARTNER\", \"HUWELIJK\"), b, b.rol)", "{{\"P\"}}",
                testPersoon);
        testEvaluatie("MAP(GERELATEERDE_BETROKKENHEDEN(\"PARTNER\", \"GEREGISTREERD_PARTNERSCHAP\"), b, b.rol)",
                "{{\"P\"}}",
                testPersoon);
        testEvaluatie("RMAP(GERELATEERDE_BETROKKENHEDEN(\"KIND\", \"FAMILIERECHTELIJKE_BETREKKING\", \"OUDER\"), b, "
                + "b.ouderlijk_gezag.ouder_heeft_gezag)", "{{WAAR}, {WAAR}}", testPersoon);
        testEvaluatie("RMAP(GERELATEERDE_BETROKKENHEDEN(\"KIND\", \"FAMILIERECHTELIJKE_BETREKKING\", \"OUDER\"), b, "
                        + "$b.ouderlijk_gezag.ouder_heeft_gezag)",
                "{{$nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut}, "
                        + "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut}}",
                testPersoon);
    }

    @Test
    public void testFunctieGERELATEERDEBETROKKENHEDENWithHistorie() {
        final PersoonHisVolledig testPersoon = TestPersonen.maakTestPersoon();

        final String datumTijdKindRegistratie = "2013/03/05/23/01/59";
        testEvaluatie(
                "MAP(GERELATEERDE_BETROKKENHEDEN(\"OUDER\", \"FAMILIERECHTELIJKE_BETREKKING\", \"KIND\"), b, b.datum_tijd_registratie)",
                "{" + datumTijdKindRegistratie + ", " + datumTijdKindRegistratie + "}",
                testPersoon);
    }

    @Test
    public void testFunctieBETROKKENHEDEN() {
        final PersoonHisVolledig testPersoon = TestPersonen.maakTestPersoon();

        testEvaluatie("BETROKKENHEDEN(\"KIND\")", "{@Betrokkenheid}", testPersoon);
        testEvaluatie("BETROKKENHEDEN(persoon, \"KIND\")", "{@Betrokkenheid}", testPersoon);
    }

    @Test
    public void testFunctieBETROKKENHEDENMetHistorie() {
        final PersoonHisVolledig testPersoon = TestPersonen.maakTestPersoon();

        final String datumTijdKindRegistratie = "2013/03/05/23/01/59";
        testEvaluatie("MAP(BETROKKENHEDEN(persoon, \"KIND\"), b, b.datum_tijd_registratie)", String.format("{%s}", datumTijdKindRegistratie), testPersoon);
    }

    @Test
    public void testFunctieONDERZOEKEN() {
        final PersoonHisVolledig testPersoon = TestPersonen.maakTestPersoon();

        testEvaluatie("ONDERZOEKEN()", "{@Onderzoek}", testPersoon);
        testEvaluatie("ONDERZOEKEN(persoon)", "{@Onderzoek}", testPersoon);
    }

    @Test
    public void testFunctieONDERZOEKENAttributen() {
        final PersoonHisVolledig testPersoon = TestPersonen.maakTestPersoon();

        testEvaluatie("RMAP(ONDERZOEKEN(), o, o.datum_aanvang)", "{{2015/09/09}}", testPersoon);
        testEvaluatie("RMAP(ONDERZOEKEN(), o, o.omschrijving)", "{{\"is niet pluis\"}}", testPersoon);
        testEvaluatie("RMAP(ONDERZOEKEN(), o, o.status)", "{{\"In uitvoering\"}}", testPersoon);
    }

    @Test
    public void testVariabelen() {
        testEvaluatie("persoon", "@Persoon");
        testEvaluatieFout("x", null, EvaluatieFoutCode.VARIABELE_NIET_GEVONDEN);
    }

    @Test
    public void testClosures() {
        testEvaluatie("x + 2 = 12 WAARBIJ x = 10", WAAR);
        testEvaluatie("(x + 2 = 12 WAARBIJ x = 10) WAARBIJ x = 20", WAAR);
        testEvaluatie("(x + y = 12 WAARBIJ x = 10) WAARBIJ y = 2", WAAR);
        testEvaluatie("x + y = 12 WAARBIJ x = 10, y = 2", WAAR);
        testEvaluatie("x+x+y+z WAARBIJ x=10,y=2,z=4", "26");
        testEvaluatie("(x WAARBIJ x=y) WAARBIJ y=2", "2");
        testEvaluatie("(x+y WAARBIJ x=y,y=z) WAARBIJ y=2,z=4", "6");
        testEvaluatie("y + (y + y WAARBIJ y = 3) + (y + y WAARBIJ y = 5) = 18 WAARBIJ y = 2", WAAR);
        testEvaluatie("(y + y WAARBIJ y = (x+2 WAARBIJ x = 1))", "6");
        testEvaluatie("(y + y WAARBIJ y = (y+2 WAARBIJ y = 1))", "6");
        testEvaluatie("y + (y + y WAARBIJ y = (y+2 WAARBIJ y = 1)) + (y + y WAARBIJ y = 5) WAARBIJ y = 2", "18");
        testEvaluatie("1 < 2 WAARBIJ x = 4", WAAR);
        testEvaluatie("1 < x WAARBIJ x = 4", WAAR);
        testEvaluatie("x < 2 WAARBIJ x = 4", ONWAAR);
        testEvaluatie("1 IN xY WAARBIJ xY = {1,2,3}", WAAR);
        testEvaluatie("4 IN l WAARBIJ l = {1,2,3}", ONWAAR);
        testEvaluatie("{1,x,3} WAARBIJ x=2", "{1, 2, 3}");
        testEvaluatie("DATUM(j, m, d) WAARBIJ j=1970,m=12,d=5", "1970/12/05");
        testEvaluatie("DATUM(j, m, d) WAARBIJ j=JAAR(persoon.geboorte.datum),m=12,d=5", "1943/12/05");
        testEvaluatie("(((x WAARBIJ x = 10, y = \"Gandalf\") WAARBIJ z=1970/JAN/01) WAARBIJ x = 20)", "10");
        testEvaluatie("(((y WAARBIJ x = 10, y = \"Gandalf\") WAARBIJ z=1970/JAN/01) WAARBIJ x = 20)", "\"Gandalf\"");
        testEvaluatie("(((z WAARBIJ x = 10, y = \"Gandalf\") WAARBIJ z=1970/JAN/01) WAARBIJ x = 20)", "1970/01/01");
    }

    @Test
    public void testSignaturen() {
        testEvaluatieFout("AANTAL()", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("AANTAL(10)", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("AANTAL({1,2}, 5)", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("ALLE(10,5)", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("ALLE({1,2,3},x)", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("DAG()", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("DAG(\"a\")", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("DAG(1990/04/03, 4)", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("DATUM()", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("DATUM(1900, 12)", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("DATUM(1900, 12, 14, 16)", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("ER_IS(k, WAAR)", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("ER_IS()", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("HUWELIJKEN(10)", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("IS_NULL()", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("IS_NULL(10,5)", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("JAAR()", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("JAAR({1,2})", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("JAAR(1980/?/?,5)", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("MAAND()", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("MAAND(10)", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        testEvaluatieFout("PARTNERS({1})", null, EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
    }

    @Test
    public void testNullWaarden() {
        testEvaluatie("NULL", "NULL");
        testEvaluatie("NIET(NULL)", "NULL");
        testEvaluatie("2 + NULL", "NULL");
        testEvaluatie("WAAR OF NULL", WAAR);
        testEvaluatie("ONWAAR OF NULL", "NULL");
        testEvaluatie("NULL OF NULL", "NULL");
        testEvaluatie("ONWAAR EN NULL", ONWAAR);
        testEvaluatie("WAAR EN NULL", "NULL");
        testEvaluatie("NULL EN NULL", "NULL");
        testEvaluatie("4 > NULL", "NULL");
        testEvaluatie("MAAND(NULL)", "NULL");
        testEvaluatie("HUWELIJKEN(NULL)", "NULL");
        testEvaluatie("persoon.samengestelde_naam.adellijke_titel", "NULL");
        testEvaluatie("3 = NULL", "NULL");
        testEvaluatie("persoon.samengestelde_naam.adellijke_titel = NULL", "NULL");
        testEvaluatie("IS_NULL(NULL)", WAAR);
        testEvaluatie("IS_NULL(45+NULL)", WAAR);
        testEvaluatie("IS_NULL(1940/10/10 = NULL)", WAAR);
        testEvaluatie("IS_NULL(1)", ONWAAR);

        testEvaluatie("NULL <> NULL", "NULL");
        testEvaluatie("WAAR <> NULL", "NULL");
        testEvaluatie("NULL = NULL", "NULL");
        testEvaluatie("NIET(NULL = NULL)", "NULL");
    }

    @Test
    public void testPersoonBRPExpressies() {
        final Persoon testPersoon = new PersoonView(TestPersonen.maakTestPersoon());

        testEvaluatie("KINDEREN()", "{@Persoon, @Persoon}", testPersoon);
        testPositive("geboorte.datum > 1940/10/10", testPersoon);
        testPositive("NIET(geboorte.datum < 1924/DEC/4)", testPersoon);
        testPositive("geboorte.datum < overlijden.datum", testPersoon);
        testPositive("AANTAL(KINDEREN()) = 2", testPersoon);
        testPositive("AANTAL(KINDEREN()) IN l WAARBIJ l = { 1,2,3,4,5,6,7,8,9 }", testPersoon);
        testPositive("AANTAL(KINDEREN()) > 0", testPersoon);
        testPositive("ALLE(KINDEREN(), k, WAAR)", testPersoon);
        testPositive("ALLE(KINDEREN(), k, k.geboorte.datum > 1970/01/01)", testPersoon);
        testPositive("NIET ALLE(KINDEREN(), k, k.geboorte.datum < 1970/01/01)", testPersoon);
        testPositive("NIET ALLE(KINDEREN(), k, k.geboorte.datum = 1990/01/01)", testPersoon);
        testPositive("ALLE(KINDEREN(), k, k.geboorte.datum < 2000/01/01)", testPersoon);
        testPositive("ER_IS(KINDEREN(), k, k.bsn = " + TestPersonen.ZOON_BSN + ")", testPersoon);
        testPositive("ER_IS(KINDEREN(), k, k.bsn = " + TestPersonen.DOCHTER_BSN + ")", testPersoon);
        testPositive("ER_IS(KINDEREN(), k, k.anummer = " + TestPersonen.ZOON_ANUMMER + ")", testPersoon);
        testPositive("ER_IS(KINDEREN(), k, k.anummer = " + TestPersonen.DOCHTER_ANUMMER + ")", testPersoon);
        testPositive("NIET ER_IS(KINDEREN(), k, k.bsn = " + TestPersonen.OPA_BSN + ")", testPersoon);
        testPositive(
            "ER_IS(KINDEREN(), k, k.geboorte.datum > 1968/01/01 EN k.geboorte.datum < 1971/01/01)",
            testPersoon);
        testPositive(
            "ER_IS(KINDEREN(), k, k.geslachtsaanduiding.geslachtsaanduiding = \"M\" EN ER_IS(KINDEREN(), l, "
                + "l.geslachtsaanduiding.geslachtsaanduiding = \"V\" EN l.bsn <> k.bsn))", testPersoon);
        testPositive(
            "NIET ER_IS(KINDEREN(), k, k.geslachtsaanduiding.geslachtsaanduiding = \"M\" EN ER_IS(KINDEREN(), l, "
                + "l.geslachtsaanduiding.geslachtsaanduiding = \"M\" EN l.bsn <> k.bsn))", testPersoon);
        testPositive("ER_IS(KINDEREN(), k, AANTAL(KINDEREN(k)) > 0)", testPersoon);
        testPositive("NIET ER_IS(KINDEREN(), k, AANTAL(KINDEREN(k)) > 1)", testPersoon);
        testPositive("ALLE(KINDEREN(), k, ER_IS(OUDERS(k), o, o.geboorte.datum < 1950/JAN/01))", testPersoon);
        testPositive("ALLE(KINDEREN(), k, ALLE(OUDERS(k), o, o.geboorte.datum < k.geboorte.datum))", testPersoon);
        testPositive(
            "NIET ER_IS(KINDEREN(), k, ALLE(OUDERS(k), o, o.geboorte.datum >= k.geboorte.datum))",
            testPersoon);

        testPositive("AANTAL(OUDERS()) = 2", testPersoon);
        testPositive("ER_IS(OUDERS(), o, o.geslachtsaanduiding.geslachtsaanduiding = \"M\")", testPersoon);
        testPositive("ER_IS(OUDERS(), o, o.geslachtsaanduiding.geslachtsaanduiding = \"V\")", testPersoon);

        testPositive("ER_IS(PARTNERS(), p, p.bsn = " + TestPersonen.OMA_BSN + ")", testPersoon);
        testPositive("ER_IS(KINDEREN(), k, ER_IS(PARTNERS(k), p, p.bsn = " + TestPersonen.SCHOONDOCHTER_BSN + "))",
                     testPersoon);

        testPositive("NIET IS_OPGESCHORT()", testPersoon);
        testPositive("NIET IS_OPGESCHORT(persoon)", testPersoon);
    }

    @Test
    public void testAanvangGeldigheid() {
        final PersoonHisVolledig testPersoon = TestPersonen.maakTestPersoon();

        testEvaluatie("PLATTE_LIJST(persoon.voornamen)", "{@Voornaam, @Voornaam, @Voornaam}",
                testPersoon);
        testEvaluatie("MAP(PLATTE_LIJST(persoon.voornamen), v, $v.naam)",
                "{{$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut, "
                        + "$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut}, "
                        + "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut, "
                        + "$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut}, "
                        + "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut}}",
                testPersoon);
        testEvaluatie("RMAP(persoon.voornamen, v, $v.naam)",
                "{{{$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut, "
                        + "$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut}, "
                        + "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut, "
                        + "$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut}}, "
                        + "{{$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut}}}",
                testPersoon);
        testEvaluatie("MAP(PLATTE_LIJST(persoon.voornamen), n, n.datum_aanvang_geldigheid)",
                "{1943/11/21, 1955/01/01, 1955/01/01}", testPersoon);
        testEvaluatie("RMAP(persoon.voornamen, n, n.datum_aanvang_geldigheid)",
                "{{1943/11/21}, {1955/01/01, 1955/01/01}}", testPersoon);
        testEvaluatie("persoon.geslachtsaanduiding.datum_aanvang_geldigheid", "1943/11/21", testPersoon);
        testEvaluatie("persoon.geslachtsaanduiding.datum_einde_geldigheid", "NULL", testPersoon);
    }

    @Test
    public void testPersoonBRPExpressiesHistorisch() {
        final PersoonHisVolledig testPersoon = TestPersonen.maakTestPersoon();
        testEvaluatie(
                "persoon.identificatienummers.administratienummer", "{19501950, 19601960, 19701970}",
                testPersoon);

        testEvaluatie(
                "$persoon.identificatienummers.administratienummer",
                "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut, "
                        + "$nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut, "
                        + "$nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut}",
                testPersoon);

        testEvaluatie("persoon.adressen", "{{@Adres}, {@Adres}}", testPersoon);
        testEvaluatie(
                "MAP(PLATTE_LIJST(adressen), a, { $a.postcode })",
                "{{{$nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut}}, "
                        + "{{$nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut}}}",
                testPersoon);
        testEvaluatie(
                "RMAP(adressen, a, { $a.postcode })",
                "{{{{$nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut}}}, "
                        + "{{{$nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut}}}}",
                testPersoon);

        testEvaluatie("MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.ouder_heeft_gezag)",
                "{{$nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut}, "
                        + "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut}, {}, {}, {}, {}, {}, {}, {}}",
                testPersoon);

        testEvaluatie("PLATTE_LIJST({{10},{20},{},{}})", "{10, 20}", testPersoon);

        testEvaluatie("PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.ouder_heeft_gezag))",
                "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut, "
                        + "$nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut}",
                testPersoon);

        testEvaluatie("persoon.indicaties", "{{@Indicatie}}", testPersoon);

        testEvaluatie(
                "nationaliteiten",
                "{{@Nationaliteit}, {@Nationaliteit}, {@Nationaliteit}}",
                testPersoon);

        testEvaluatie("samengestelde_naam.voornamen", "\"Jack Johnson\"");
        testEvaluatie("samengestelde_naam.voornamen", "{\"\", \"Jacco Johnson\", \"Jack Johnson\"}", testPersoon);

        testEvaluatie(
                "voornamen",
                "{{@Voornaam, @Voornaam}, {@Voornaam}}",
                testPersoon);
        testEvaluatie(
                "MAP(voornamen, l, l)",
                "{{@Voornaam, @Voornaam}, {@Voornaam}}",
                testPersoon);
        testEvaluatie(
                "PLATTE_LIJST(voornamen)",
                "{@Voornaam, @Voornaam, @Voornaam}",
                testPersoon);
        testEvaluatie(
                "PLATTE_LIJST(RMAP(PLATTE_LIJST(voornamen), l, l.naam))",
                "{\"Jacco\", \"Jacco\", \"Jack\", \"Jack\", \"Johnson\"}",
                testPersoon);
        testEvaluatie(
                "MAP(PLATTE_LIJST(voornamen), l, l)",
                "{@Voornaam, @Voornaam, @Voornaam}",
                testPersoon);
        testEvaluatie(
                "RMAP(voornamen, l, l)",
                "{{@Voornaam, @Voornaam}, {@Voornaam}}",
                testPersoon);
        testEvaluatie(
                "PLATTE_LIJST(MAP(voornamen, l, l))",
                "{@Voornaam, @Voornaam, @Voornaam}",
                testPersoon);
        testEvaluatie(
                "MAP(PLATTE_LIJST(voornamen), l, $l.naam)",
                "{{$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut, "
                        + "$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut}, "
                        + "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut, "
                        + "$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut}, "
                        + "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut}}",
                testPersoon);
        //        testEvaluatie("MAP(voornamen, l, AANTAL(l))",
        //                "",
        //                testPersoon);

        testEvaluatie("persoon.administratief.tijdstip_laatste_wijziging", "{}", testPersoon);

        testEvaluatie("adressen", "{{@Adres}, {@Adres}}", testPersoon);
        //        testEvaluatie("RMAP(adressen, v, $v.adresseerbaar_object)", "{{{$NULL}}, {{$NULL}}}", testPersoon);

        testEvaluatie("persoon.bijhouding.onverwerkt_document_aanwezig", "{}", testPersoon);
    }

    @Test
    public void testPersoonBRPNullExpressies() {
        final Persoon testPersoon = new PersoonView(TestPersonen.maakSukkel());
        testNull("persoon.geslachtsaanduiding.geslachtsaanduiding", testPersoon);
    }

    @Test
    public void testHuwelijkBRPExpressies() {
        final Persoon testPersoon = new PersoonView(TestPersonen.maakTestPersoon());
        testEvaluatie("HUWELIJKEN()", "{@Huwelijk}", testPersoon);
        testPositive("ER_IS(HUWELIJKEN(), h, h.datum_aanvang > 1960/JAN/1)", testPersoon);
        testPositive("ALLE(HUWELIJKEN(), h, h.datum_aanvang > 1960/JAN/1)", testPersoon);
        final Expressie expressie = BRPExpressies.evalueer("RMAP(HUWELIJKEN(), h, h.datum_tijd_registratie)", testPersoon);
        assertThat(expressie.alsString(), is("{1965/01/01/00/00/00}"));
    }

    @Test
    public void testGewijzigdFunctieMetFunctieParameters() {
        final PersoonHisVolledig persoon = TestPersonen.maakTestPersoon();
        final Persoon testPersoon = new PersoonView(persoon);

        final Context context = new Context();

        context.definieer(OUD, new BrpObjectExpressie(testPersoon, ExpressieType.PERSOON));
        context.definieer(NIEUW, new BrpObjectExpressie(new PersoonView(TestPersonen.maakSukkel()), ExpressieType.PERSOON));
        testEvaluatie("GEWIJZIGD(HUWELIJKEN(oud), HUWELIJKEN(nieuw), [gemeente_aanvang])", WAAR, context);

        context.definieer(OUD, new BrpObjectExpressie(testPersoon, ExpressieType.PERSOON));
        context.definieer(NIEUW, new BrpObjectExpressie(new PersoonView(TestPersonen.maakTestPersoon()), ExpressieType.PERSOON));
        testEvaluatie("GEWIJZIGD(HUWELIJKEN(oud), HUWELIJKEN(nieuw), [gemeente_aanvang])", ONWAAR, context);
        testEvaluatie("GEWIJZIGD(KINDEREN(oud), KINDEREN(nieuw), [bsn])", ONWAAR, context);
        testEvaluatie("GEWIJZIGD(PARTNERSCHAPPEN(oud), PARTNERSCHAPPEN(nieuw), [gemeente_aanvang])", ONWAAR, context);

        // pas de gemeente aanvang aan van deze persoon
        final PersoonHisVolledig aangepast = TestPersonen.maakTestPersoon();

        final HisRelatieModel actueleRecord = RelatieTestUtil.haalHuwelijkUitPersoonBetrokkenheden((PersoonHisVolledigImpl) aangepast).getRelatieHistorie().getActueleRecord();
        ReflectionTestUtils.setField(actueleRecord, "gemeenteAanvang", StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);

        context.definieer(NIEUW, new BrpObjectExpressie(aangepast, ExpressieType.PERSOON));
        testEvaluatie("GEWIJZIGD(HUWELIJKEN(oud), HUWELIJKEN(nieuw), [gemeente_aanvang])", WAAR, context);
    }

    @Test
    public void testIndicaties() {
        final PersoonHisVolledig testPersoon = TestPersonen.maakTestPersoon();
        testEvaluatie("persoon.indicatie.onder_curatele", WAAR);
        testEvaluatie("persoon.indicatie.derde_heeft_gezag", "NULL");
        testEvaluatie("persoon.indicatie.onder_curatele", "{WAAR}", testPersoon);
        testEvaluatie("$persoon.indicatie.onder_curatele",
                "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut}", testPersoon);
        testEvaluatie("persoon.indicaties", "{@Indicatie}");
        testEvaluatie("MAP(indicaties, i, i.soort)", "{\"Onder curatele?\"}");
        testEvaluatie("ER_IS(indicaties, i, i.soort = \"Verstrekkingsbeperking?\")", ONWAAR);
        testEvaluatie("ER_IS(indicaties, i, i.soort = \"Onder curatele?\")", WAAR);
        testEvaluatie("MAP(indicaties, i, i.waarde)", "{WAAR}");
        testEvaluatie(
            "MAP(indicaties, i, $i.soort)",
            "{$nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatieAttribuut}");
        testEvaluatie("MAP(indicaties, i, $i.waarde)", "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut}");
    }

    @Test
    public void testNullObjecten() {
        testEvaluatie("persoon.geboorte.datum", "NULL", (BrpObject) null);
        testEvaluatie("persoon.nationaliteiten", "NULL", (BrpObject) null);
        testEvaluatie("IS_NULL(persoon.indicatie.onder_curatele)", WAAR, (BrpObject) null);
    }

    @Test
    public void testOvergeblevenTekstDieNietGematchedKanWorden() {
        final ParserResultaat parse = BRPExpressies.parse("geslachtsaanduiding.geslachtsaanduiding.dit_kan_niet_gematched_worden = \"V\"");
        assertNotNull(parse.getFout());
    }

    @Test
    public void testAutorisaties() {
        testEvaluatie("$persoon.bsn", "$nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut");
        testEvaluatie(
                "$persoon.samengestelde_naam.voornamen",
                "$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut");
        testEvaluatie(
                "MAP(persoon.voornamen, n, $n.naam)",
                "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut, "
                        + "$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut}");
        testEvaluatie("FILTER(persoon.nationaliteiten, n, n.nationaliteit = 339)",
                "{@Nationaliteit}");
        testEvaluatie("MAP(persoon.nationaliteiten, m, m.nationaliteit)",
                "{1, 339}");
        testEvaluatie("MAP(FILTER(persoon.nationaliteiten, n, n.nationaliteit = 339), m, m.nationaliteit)",
                "{339}");
        testEvaluatie("MAP(FILTER(persoon.nationaliteiten, n, n.nationaliteit = 339), m, m.reden_verkrijging)",
                "{NULL}");
        testEvaluatie("MAP(FILTER(persoon.nationaliteiten, n, n.nationaliteit = 339), m, $m.nationaliteit)",
                "{$nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut}");

        testEvaluatie("FILTER(nationaliteiten, n, n.nationaliteit = 1)",
                "{@Nationaliteit}");
        testEvaluatie("MAP(nationaliteiten, m, m.nationaliteit)", "{1, 339}");
        testEvaluatie("MAP(adressen, a, a.soort)", "{\"W\"}");
        testEvaluatie("MAP(FILTER(adressen, a, a.soort = \"W\"), a, a.postcode)", "{\"1234AB\"}");
        testEvaluatie("MAP(FILTER(adressen, a, a.soort = \"W\"), a, $a.postcode)",
                "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut}");
        testEvaluatie(
                "MAP(FILTER(OUDERS(), o, o.geslachtsaanduiding.geslachtsaanduiding = \"V\"),p, p.samengestelde_naam.voornamen)",
                "{\"Opoe\"}");
        testEvaluatie(
                "MAP(FILTER(OUDERS(), o, o.geslachtsaanduiding.geslachtsaanduiding = \"V\"),p, $p.samengestelde_naam.voornamen)",
                "{$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut}");
        testEvaluatie("ER_IS(OUDERS(), o, o.geslachtsaanduiding.geslachtsaanduiding = \"V\")", WAAR);
        testEvaluatie("persoon.geboorte.datum", "1943/11/21");
        testEvaluatie("ALS(ONWAAR, persoon.geboorte.datum, NULL)", "NULL");
        testEvaluatie("ALS(WAAR, persoon.geboorte.datum, NULL)", "1943/11/21");
        testEvaluatie(
                "ALS(ER_IS(OUDERS(), o, o.geslachtsaanduiding.geslachtsaanduiding = \"V\"), samengestelde_naam.voornamen, \"?\")",
                "\"Jack Johnson\"");
        testEvaluatie(
                "ALS(ER_IS(OUDERS(), o, o.geslachtsaanduiding.geslachtsaanduiding = \"V\"), $samengestelde_naam.voornamen, \"?\")",
                "$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut");
        testEvaluatie(
                "ALS(ER_IS(OUDERS(), o, ER_IS(o.nationaliteiten, n, n.nationaliteit = 339)), "
                        + "$samengestelde_naam.voornamen, \"?\")",
                "$nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut");
    }


    @Test
    public void testPartijGetters() {
        final PersoonHisVolledigImpl sjonnie = TestPersoonJohnnyJordaan.maak();

        // test ophalen partijcode voor verantwoording
        final Expressie evalueer = BRPExpressies.evalueer("persoon.identificatienummers.verantwoordingInhoud.partij", sjonnie);
        assertTrue(evalueer.getElement(0) instanceof GetalLiteralExpressie);
        assertEquals(36101, evalueer.getElement(0).alsInteger());

        //test ophalen partijcode specifiek voor indicaties,
        final HisPersoonIndicatieOnderCurateleModel actueleRecord = sjonnie.getIndicatieOnderCuratele().getPersoonIndicatieHistorie().getActueleRecord();
        actueleRecord.setVerantwoordingAanpassingGeldigheid(actueleRecord.getVerantwoordingInhoud());
        ReflectionTestUtils.setField(actueleRecord.getVerantwoordingInhoud(), "partij", new PartijAttribuut(TestPartijBuilder.maker().metCode(34).maak()));

        final Expressie evalueer2 = BRPExpressies.evalueer("persoon.indicatie.onder_curatele.verantwoordingAanpassingGeldigheid.partij", sjonnie);
        assertTrue(evalueer2.getElement(0) instanceof GetalLiteralExpressie);
        assertEquals(34, evalueer2.getElement(0).alsInteger());
    }

    @Test
    public void testPartijGetters018810() {
    	testParse("identificatienummers.verantwoordingInhoud.partij = 852102");
    	testParse("samengestelde_naam.verantwoordingInhoud.partij = 852102");
    	testParse("geboorte.verantwoordingInhoud.partij = 852102");
    	testParse("geslachtsaanduiding.verantwoordingInhoud.partij = 852102");
    	testParse("nummerverwijzing.verantwoordingInhoud.partij = 852102");
    	testParse("naamgebruik.verantwoordingInhoud.partij = 852102");

    	testParse("(identificatienummers.verantwoordingInhoud.partij = 852102 OF "
    			+ "samengestelde_naam.verantwoordingInhoud.partij = 852102 OF "
    			+ "geboorte.verantwoordingInhoud.partij = 852102 OF "
    			+ "geslachtsaanduiding.verantwoordingInhoud.partij = 852102 OF "
    			+ "nummerverwijzing.verantwoordingInhoud.partij = 852102 OF "
    			+ "naamgebruik.verantwoordingInhoud.partij = 852102)");
    }

    @Test
    public void testPartijGetters048810() {
        testParse("indicatie.behandeld_als_nederlander.verantwoordingInhoud.partij = 852102");
        testParse("indicatie.vastgesteld_niet_nederlander.verantwoordingInhoud.partij = 852102");
        testParse("indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingInhoud.partij = 852102");
        testParse("ER_IS(RMAP(nationaliteiten, x, x.verantwoordingInhoud.partij), v, v = 852102)");

    	testParse("ER_IS(RMAP(nationaliteiten, x, x.verantwoordingInhoud.partij), v, v = 852102) OF "
    			+ "indicatie.behandeld_als_nederlander.verantwoordingInhoud.partij = 852102 OF "
    			+ "indicatie.vastgesteld_niet_nederlander.verantwoordingInhoud.partij = 852102 OF "
    			+ "indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingInhoud.partij = 852102)");
    }

    @Test
    public void testPartijGetters068810() {
    	testParse("overlijden.verantwoordingInhoud.partij = 852102");
    }

    @Test
    public void testPartijGetters078810() {
    	testParse("ER_IS(RMAP(verificaties, x, x.partij), v, v = 852102)");
    }

    @Test
    public void testPartijGetters088810() {
    	testParse("ER_IS(RMAP(adressen, x, x.verantwoordingInhoud.partij), v, v = 852102)");
    	testParse("bijhouding.verantwoordingInhoud.partij = 852102");
    	testParse("migratie.verantwoordingInhoud.partij = 852102");

    	testParse("(ER_IS(RMAP(adressen, x, x.verantwoordingInhoud.partij), v, v = 852102) OF "
    			+ "bijhouding.verantwoordingInhoud.partij = 852102 OF "
    			+ "migratie.verantwoordingInhoud.partij = 852102)");
    }

    private void testParse(String expressie) {
    	  final ParserResultaat resultaat = BRPExpressies.parse(expressie);
        if(resultaat.getExpressie() == null) {
            Assert.fail(resultaat.getFoutmelding());
        }
    }



}
