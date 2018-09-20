/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.expressietaal.TestPersonen;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: maels
 * Date: 10-12-13
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class OptimalisatieTest {

    @Test
    public void testOptimalisatie() {
        testOptimalisatie("10", "10");
        testOptimalisatie("WAAR", "WAAR");
        testOptimalisatie("\"abc\"", "\"abc\"");
        testOptimalisatie("1980/02/20", "1980/02/20");
        testOptimalisatie("^1/2/3", "^1/2/3");
        testOptimalisatie("1+1", "2");
        testOptimalisatie("x+1", "x + 1");
        testOptimalisatie("x+0", "x");
        testOptimalisatie("0+x", "x");
        testOptimalisatie("IS_NULL(NULL)", "WAAR");
        testOptimalisatie("IS_NULL(1)", "ONWAAR");
        testOptimalisatie("DAG(1980/05/15)", "15");
        testOptimalisatie("DAG(1980/05/?)", "NULL");
        testOptimalisatie("DAG(VANDAAG())", "DAG(VANDAAG(0))");
        testOptimalisatie("1<2", "WAAR");
        testOptimalisatie("1<2 OF x", "WAAR");
        testOptimalisatie("1<2 EN x", "x");
        testOptimalisatie("NIET (1<2 OF 4>3)", "ONWAAR");
        testOptimalisatie("x WAARBIJ x = 2", "2");
        testOptimalisatie("x<5 WAARBIJ x = 2", "WAAR");
        testOptimalisatie("{1,2,3}", "{1, 2, 3}");
        testOptimalisatie("1 IN { 1,2,3 }", "WAAR");
        testOptimalisatie("4 IN { 1,2,3 }", "ONWAAR");
        testOptimalisatie("x IN { 1,2,3 }", "x IN {1, 2, 3}");
        testOptimalisatie("x IN { 1,2,3 } WAARBIJ x=2", "WAAR");
        testOptimalisatie("DAG(VANDAAG())", "DAG(VANDAAG(0))");
        testOptimalisatie(
            "ER_IS(KINDEREN(), k, k.bsn = 1234)",
            "ER_IS(KINDEREN(persoon), k, k.identificatienummers.burgerservicenummer = 1234)");
        testOptimalisatie("ER_IS(KINDEREN(), k, WAAR)", "ER_IS(KINDEREN(persoon), k, WAAR)");
        testOptimalisatie("ER_IS({1}, k, WAAR)", "WAAR");
        testOptimalisatie("ER_IS({}, x, x=1)", "ONWAAR");
        testOptimalisatie("ER_IS({1}, x, x=1)", "WAAR");
        testOptimalisatie("ER_IS({1}, x, x=2)", "ONWAAR");
        testOptimalisatie("ER_IS(NULL, k, WAAR)", "NULL");
        testOptimalisatie("ALLE({}, x, x>5)", "WAAR");
        testOptimalisatie("ALLE({7}, x, x>5)", "WAAR");
        testOptimalisatie("ALLE({2}, x, x>5)", "ONWAAR");
        testOptimalisatie("ALLE(NULL, x, x>5)", "NULL");
        testOptimalisatie("ALLE({1,2}, x, WAAR)", "WAAR");
        testOptimalisatie("ALLE({1,2}, x, ONWAAR)", "ONWAAR");
        testOptimalisatie("10 WAARBIJ x = VANDAAG()", "10");
        testOptimalisatie("x WAARBIJ x = 10", "10");
        testOptimalisatie("x WAARBIJ y = 5", "x");
        testOptimalisatie("x + x WAARBIJ x = 10", "20");
        testOptimalisatie("ALS(WAAR, NULL, {1,2})", "NULL");
        testOptimalisatie("ALS(WAAR, geboorte.datum, overlijden.datum)", "persoon.geboorte.datum");
        testOptimalisatie("ALS(ONWAAR, geboorte.datum, overlijden.datum)", "persoon.overlijden.datum");
        testOptimalisatie("AANTAL(NULL)", "NULL");
        testOptimalisatie("AANTAL(ALS(WAAR, NULL, {1,2}))", "NULL");
        testOptimalisatie("AANTAL(ALS(ONWAAR, NULL, {1,2}))", "2");
        testOptimalisatie("persoon.geboorte.datum", "persoon.geboorte.datum");
        testOptimalisatie("DATUM(j, m, d) WAARBIJ j=JAAR(persoon.geboorte.datum),m=12,d=5",
            "(DATUM(j, 12, 5)) WAARBIJ j = JAAR(persoon.geboorte.datum)");
    }

    private void testOptimalisatie(final String expressie, final String verwachtResultaat) {
        Context context = new Context();
        Persoon testPersoon = new PersoonView(TestPersonen.maakTestPersoon());
        context.definieer("persoon", new BrpObjectExpressie(testPersoon, ExpressieType.PERSOON));
        ParserResultaat pr = BRPExpressies.parse(expressie);
        Assert.assertNotNull("Error in expressie: " + pr.getFoutmelding(), pr.getExpressie());
        Expressie geoptimaliseerdeExpressie = pr.getExpressie().optimaliseer(new Context());
        Assert.assertEquals(verwachtResultaat, geoptimaliseerdeExpressie.toString());
    }
}
