/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.TestPersonen;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.DatumLiteralExpressie;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import org.joda.time.DateTime;

/**
 * Test de evaluatie van expressies.
 */
public final class ExpressieEvaluator {
    public static final String WAAR = "WAAR";
    public static final String ONWAAR = "ONWAAR";
    public static final String ONBEKEND = "NULL";


    /**
     * Utility klasse zonder publieke constructor.
     */
    private ExpressieEvaluator() { }

    /**
     * Geeft de stringrepresentatie van een DateTime-object, zoals gebruikt in de expressietaal.
     *
     * @param dt Datum.
     * @return Stringrepresentatie van datum.
     */
    public static String getDatumString(final DateTime dt) {
        final DatumLiteralExpressie exp = new DatumLiteralExpressie(dt);
        return exp.toString();
    }

    public static void testEvaluatie(final String input, final String expectedOutput, final BrpObject persoon) {
        final Context context = new Context();
        context.definieer("persoon", new BrpObjectExpressie(persoon, ExpressieType.PERSOON));

        testEvaluatie(input, expectedOutput, context);
    }

    public static void testEvaluatie(final String input, final String expectedOutput, final Context context) {
        final Comparator<Expressie> comparator = new Comparator<Expressie>() {
            @Override
            public int compare(final Expressie s, final Expressie s2) {
                return s.toString().compareTo(s2.toString());
            }
        };

        final ParserResultaat pr = BRPExpressies.parse(input);
        assertNotNull("Error in expressie: " + pr.getFoutmelding(), pr.getExpressie());

        final Expressie geoptimaliseerdeExpressie = pr.getExpressie().optimaliseer(new Context());
        Expressie resultaat = pr.getExpressie().evalueer(context);
        if (resultaat.isLijstExpressie() && resultaat instanceof LijstExpressie) {
            ((LijstExpressie) resultaat).sorteer(comparator, false);
        }
        assertFalse("Evaluatie gefaald: " + resultaat.toString(), resultaat.isFout());

        final String actualOutput = resultaat.toString();
        assertEquals("Onverwachte uitvoer van expressie: " + input, expectedOutput, actualOutput);

        final String stringRepresentatie = pr.getExpressie().toString();
        final ParserResultaat prStringRepresentatie = BRPExpressies.parse(stringRepresentatie);
        assertNotNull("Error in stringrepresentatie: " + prStringRepresentatie.getFoutmelding(), prStringRepresentatie.getExpressie());

        final Expressie resultaatStringRepresentatie = prStringRepresentatie.getExpressie().evalueer(context);
        if (resultaatStringRepresentatie.isLijstExpressie() && resultaatStringRepresentatie instanceof LijstExpressie) {
            ((LijstExpressie) resultaatStringRepresentatie).sorteer(comparator, false);
        }
        assertFalse("Evaluatie stringrepresentatie gefaald: " + resultaatStringRepresentatie.toString(), resultaatStringRepresentatie.isFout());

        final String outputStringRepresentatie = resultaatStringRepresentatie.toString();
        assertEquals("Resultaat stringrepresentatie wijkt af: " + stringRepresentatie, expectedOutput, outputStringRepresentatie);

        resultaat = geoptimaliseerdeExpressie.evalueer(context);
        if (resultaat.isLijstExpressie() && resultaat instanceof LijstExpressie) {
            ((LijstExpressie) resultaat).sorteer(comparator, false);
        }
        assertFalse("Evaluatie van geoptimaliseerde expressie gefaald: " + resultaat.toString(), resultaat.isFout());

        final String actualOutputGeoptimaliseerd = resultaat.toString();
        assertEquals("Expressie wijkt af van geoptimaliseerde expressie: " + geoptimaliseerdeExpressie.toString(), actualOutput, actualOutputGeoptimaliseerd);

        final Expressie tweemaalGeoptimaliseerdeExpressie = geoptimaliseerdeExpressie.optimaliseer(new Context());
        assertEquals("Expressie nog niet volledig geoptimaliseerd", geoptimaliseerdeExpressie.toString(), tweemaalGeoptimaliseerdeExpressie.toString());
    }

    public static void testEvaluatie(final String input, final String expectedOutput) {
        testEvaluatie(input, expectedOutput, new PersoonView(TestPersonen.maakTestPersoon()));
    }

    public static void testEvaluatieHisVolledig(final String input, final String expectedOutput) {
        testEvaluatie(input, expectedOutput, new PersoonHisVolledigView(TestPersonen.maakTestPersoon(), null));
    }

    public static void testPositive(final String input, final Persoon testPersoon) {
        final Expressie evaluatieResultaat = BRPExpressies.evalueer(input, testPersoon);
        assertFalse(
            "Fout bij evaluatie (Slice): " + evaluatieResultaat.toString(),
            evaluatieResultaat.isFout());
        assertTrue(
            "Geen boolean expressie (Slice): " + evaluatieResultaat.toString(),
            evaluatieResultaat.isConstanteWaarde(ExpressieType.BOOLEAN));
        assertTrue("ONWAAR ipv WAAR (Slice)", evaluatieResultaat.alsBoolean());
    }

    public static void testNull(final String input, final Persoon testPersoon) {
        final Expressie evaluatieResultaat = BRPExpressies.evalueer(input, testPersoon);
        assertFalse(
            "Fout bij evaluatie: " + evaluatieResultaat.toString(),
            evaluatieResultaat.isFout());
        assertTrue("Geen null-waarde", evaluatieResultaat.isNull(null));
    }

    public static void testEvaluatieFout(final String input, final Persoon testPersoon,
                                   final EvaluatieFoutCode verwachteFout)
    {
        final Expressie evaluatieResultaat = BRPExpressies.evalueer(input, testPersoon);
        assertTrue("Fout verwacht bij evaluatie", evaluatieResultaat.isFout());
        final FoutExpressie foutExpressie = (FoutExpressie) evaluatieResultaat;
        assertEquals(verwachteFout, foutExpressie.getFoutCode());
    }
}
