/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.util;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.BRPExpressies;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 */
public class TestUtils {


    private static final Comparator<Expressie> LIJST_COMPARATOR = (s, s2) -> s.toString().compareTo(s2.toString());

    public static final String WAAR = "WAAR";
    public static final String ONWAAR = "ONWAAR";
    public static final String ONBEKEND = "NULL";

    public static void assertEqual(final Expressie expressie, final String s) {
        Assert.isTrue(StringUtils.equals(expressie.alsString(), s), String.format("%s ongelijk aan %s", expressie.alsString(), s));
    }

    public static void assertExceptie(final String expressie, final String foutmelding) {
        assertExceptie(expressie, ExpressietaalTestPersoon.PERSOONSLIJST, foutmelding);
    }

    public static void assertExceptie(final String expressie, final Persoonslijst persoon, final String foutmelding) {
        Throwable ex = null;
        try {
            BRPExpressies.evalueer(ExpressieParser.parse(expressie), persoon);
        } catch (ExpressieException e) {
            e.printStackTrace();
            ex = e.getCause() instanceof ExpressieRuntimeException ? e.getCause() : e;
        }
        Assert.notNull(ex, "Geen fout opgetreden, wel verwacht");
        Assert.isTrue(StringUtils.equals(foutmelding, ex.getMessage()), String.format("'%s'!= '%s'", foutmelding, ex.getMessage()));
    }

    /**
     * Vertaalt en evalueert de expressie voor de gegeven persoon.
     *
     * @param expressieString De te evalueren expressie.
     * @param persoon         De persoon (zonder historie).
     * @return Resultaat van de evaluatie.
     * @throws ExpressieException als expressie exvaluatie faalt
     */
    public static Expressie evalueer(final String expressieString, final Persoonslijst persoon) throws ExpressieException {
        return BRPExpressies.evalueer(ExpressieParser.parse(expressieString), persoon);
    }

    public static Expressie evalueer(final String s) throws ExpressieException {
        return evalueer(s, new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L));
    }

    public static void testEvaluatie(final String input, final String expectedOutput, final Persoonslijst persoon) throws ExpressieException {

        final Expressie pr = ExpressieParser.parse(input);
        Expressie resultaat = BRPExpressies.evalueer(pr, persoon);
        if (resultaat instanceof LijstExpressie) {
            resultaat = sorteer((LijstExpressie) resultaat);
        }

        final String actualOutput = resultaat.toString();
        assertEquals("Onverwachte uitvoer van expressie: " + input, expectedOutput, actualOutput);
    }

    public static void testEvaluatie(final String input, final String expectedOutput) throws ExpressieException {
        testEvaluatie(input, expectedOutput, new Persoonslijst(TestBuilders.maakIngeschrevenPersoon().build(), 0L));
    }

    /**
     * Sorteert de elementen van de lijst met behulp van de comparator.
     */
    private static LijstExpressie sorteer(final LijstExpressie lijstExpressie) {

        final ArrayList<Expressie> lijstElementen = Lists.newArrayList(lijstExpressie.getElementen());
        Collections.sort(lijstElementen, LIJST_COMPARATOR);
        return new LijstExpressie(lijstElementen);
    }
}
