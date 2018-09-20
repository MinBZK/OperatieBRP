/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieTaalConstanten;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.TestPersonen;
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.GetalLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.expressies.literals.StringLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.PlusOperatorExpressie;
import nl.bzk.brp.expressietaal.symbols.ExpressieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test van Expressie.
 */
public final class ExpressieTest {

    private static final String SOORT = "soort";

    @Test
    public void testGetElementen() {
        List<Expressie> elementen;
        Expressie list = new LijstExpressie();
        Assert.assertEquals("[]", list.getElementen().toString());

        elementen = new ArrayList<>();
        elementen.add(new GetalLiteralExpressie(10));
        list = new LijstExpressie(elementen);
        Assert.assertEquals("[10]", list.getElementen().toString());

        elementen = new ArrayList<>();
        elementen.add(new GetalLiteralExpressie(20));
        elementen.add(new GetalLiteralExpressie(30));
        list = new LijstExpressie(elementen);
        Assert.assertEquals("[20, 30]", list.getElementen().toString());

        list = new GetalLiteralExpressie(15);
        Assert.assertEquals("[15]", list.getElementen().toString());
    }

    @Test
    public void testGetElement() {
        Expressie lijst = new LijstExpressie();
        Assert.assertTrue(lijst.getElement(0).isNull(null));
        Assert.assertTrue(lijst.getElement(-1).isNull(null));
        Assert.assertTrue(lijst.getElement(1).isNull(null));
        List<Expressie> elementen = new ArrayList<>();
        elementen.add(new GetalLiteralExpressie(10));
        lijst = new LijstExpressie(elementen);
        Assert.assertEquals("10", lijst.getElement(0).toString());
        Assert.assertTrue(lijst.getElement(-1).isNull(null));
        Assert.assertTrue(lijst.getElement(1).isNull(null));
        elementen = new ArrayList<>();
        elementen.add(new GetalLiteralExpressie(10));
        elementen.add(new GetalLiteralExpressie(20));
        lijst = new LijstExpressie(elementen);
        Assert.assertEquals("{10, 20}", lijst.toString());
        Assert.assertEquals("10", lijst.getElement(0).toString());
        Assert.assertEquals("20", lijst.getElement(1).toString());
        Assert.assertTrue(lijst.getElement(-1).isNull(null));
        Assert.assertTrue(lijst.getElement(2).isNull(null));
    }

    @Test
    public void testBooleanLiteralExpressie() {
        BooleanLiteralExpressie b = BooleanLiteralExpressie.WAAR;
        Assert.assertTrue(b.alsBoolean());
        b = BooleanLiteralExpressie.ONWAAR;
        Assert.assertFalse(b.alsBoolean());
        Assert.assertEquals(ExpressieType.BOOLEAN, b.getType(null));
        Assert.assertTrue(b.isConstanteWaarde());
        Assert.assertTrue(b.isConstanteWaarde(ExpressieType.BOOLEAN));
    }

    @Test
    public void testAttributen() {
        final Context context = new Context();
        final Persoon persoon = new PersoonView(TestPersonen.maakSukkel());
        context.definieer(ExpressieTaalConstanten.DEFAULT_OBJECT, new BrpObjectExpressie(persoon, ExpressieType.PERSOON));
        for (final ExpressieAttribuut attr : ExpressieAttribuut.values()) {
            if (attr.getParentType() == ExpressieType.PERSOON) {
                final Expressie exp = new AttribuutExpressie("persoon", attr);
                exp.evalueer(context);
            }
        }
    }

    @Test
    public void testReguliereExpressies() {
        StringLiteralExpressie str;

        str = new StringLiteralExpressie("abc");
        Assert.assertEquals("Regexp niet correct", "abc", str.getStringWaardeAlsReguliereExpressie());
        str = new StringLiteralExpressie("");
        Assert.assertEquals("Regexp niet correct", "", str.getStringWaardeAlsReguliereExpressie());
        str = new StringLiteralExpressie("abc.");
        Assert.assertEquals("Regexp niet correct", "abc\\.", str.getStringWaardeAlsReguliereExpressie());
        str = new StringLiteralExpressie("?abc");
        Assert.assertEquals("Regexp niet correct", ".abc", str.getStringWaardeAlsReguliereExpressie());
        str = new StringLiteralExpressie("ab*c");
        Assert.assertEquals("Regexp niet correct", "ab.*c", str.getStringWaardeAlsReguliereExpressie());
        str = new StringLiteralExpressie("ab^c");
        Assert.assertEquals("Regexp niet correct", "ab\\^c", str.getStringWaardeAlsReguliereExpressie());
        str = new StringLiteralExpressie(".");
        Assert.assertEquals("Regexp niet correct", "\\.", str.getStringWaardeAlsReguliereExpressie());
        str = new StringLiteralExpressie("a*b?*.c");
        Assert.assertEquals("Regexp niet correct", "a.*b..*\\.c", str.getStringWaardeAlsReguliereExpressie());
    }

    @Test
    public void testWaarden() {
        Expressie expressie = new GetalLiteralExpressie(10);
        Assert.assertEquals(10, expressie.alsInteger());
        Assert.assertEquals("10", expressie.alsString());
        Assert.assertEquals(false, expressie.alsBoolean());
        Assert.assertEquals(null, expressie.getAttribuut());

        expressie = BooleanLiteralExpressie.WAAR;
        Assert.assertEquals(0, expressie.alsInteger());
        Assert.assertEquals("WAAR", expressie.alsString());
        Assert.assertEquals(true, expressie.alsBoolean());
        Assert.assertEquals(null, expressie.getAttribuut());

        expressie = BooleanLiteralExpressie.ONWAAR;
        Assert.assertEquals(0, expressie.alsInteger());
        Assert.assertEquals("ONWAAR", expressie.alsString());
        Assert.assertEquals(false, expressie.alsBoolean());
        Assert.assertEquals(null, expressie.getAttribuut());

        expressie = NullValue.getInstance();
        Assert.assertEquals(false, expressie.alsBoolean());

        expressie = new StringLiteralExpressie("");
        Assert.assertEquals(0, expressie.alsInteger());
        Assert.assertEquals("", expressie.alsString());
        Assert.assertEquals("", ((StringLiteralExpressie) expressie).getStringWaardeAlsReguliereExpressie());
        Assert.assertEquals(false, expressie.alsBoolean());
        Assert.assertEquals(null, expressie.getAttribuut());

        expressie = new StringLiteralExpressie("test");
        Assert.assertEquals(0, expressie.alsInteger());
        Assert.assertEquals("test", expressie.alsString());
        Assert.assertEquals("test", ((StringLiteralExpressie) expressie).getStringWaardeAlsReguliereExpressie());
        Assert.assertEquals(false, expressie.alsBoolean());
        Assert.assertEquals(null, expressie.getAttribuut());

        expressie = new StringLiteralExpressie("test?");
        Assert.assertEquals(0, expressie.alsInteger());
        Assert.assertEquals("test?", expressie.alsString());
        Assert.assertEquals("test.", ((StringLiteralExpressie) expressie).getStringWaardeAlsReguliereExpressie());
        Assert.assertEquals(false, expressie.alsBoolean());
        Assert.assertEquals(null, expressie.getAttribuut());
    }

    @Test
    public void testVeiligeExpressie() {
        final PlusOperatorExpressie expressie = new PlusOperatorExpressie(null, null);
        Assert.assertNotNull(expressie.getOperandLinks());
        Assert.assertNotNull(expressie.getOperandRechts());
        Assert.assertTrue(expressie.getOperandLinks().isNull(null));
        Assert.assertTrue(expressie.getOperandRechts().isNull(null));
    }

    @Test
    public void testAttributes() {
        Assert.assertEquals("Huwelijk.datum_einde (Datum)", ExpressieAttribuut.HUWELIJK_DATUM_EINDE.toString());
        Assert.assertEquals("Persoon.naamgebruik.voorvoegsel (String)",
            ExpressieAttribuut.PERSOON_NAAMGEBRUIK_VOORVOEGSEL.toString());
        Assert.assertEquals("Betrokkenheid.ouderschap.ouder (Boolean)",
            ExpressieAttribuut.OUDER_OUDERSCHAP_OUDER.toString());

        final List<Attribuut> legeLijst = new ArrayList<>();

        for (final ExpressieAttribuut attr : ExpressieAttribuut.values()) {
            Assert.assertEquals(attr.toString() + " attribuut: null verwacht", null, attr.getAttribuut(null));
            Assert.assertEquals(attr.toString() + " waarde: NULL verwacht", NullValue.getInstance(),
                attr.getAttribuutWaarde(null));
            if (!attr.isLijst()) {
                Assert.assertEquals(attr.toString() + " historische attributen: lege lijst verwacht", legeLijst,
                    attr.getHistorischeAttributen(null));
            }
        }

        final PersoonHisVolledig leegPersoon =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        for (final ExpressieAttribuut attr : ExpressieAttribuut.values()) {

            if (attr.getParentType() != ExpressieType.PERSOON || !SOORT.equals(attr.getSyntax())) {
                Assert.assertNull(attr.toString() + " attribuut: null verwacht",
                    attr.getAttribuut(leegPersoon));
                final Expressie waarde = attr.getAttribuutWaarde(leegPersoon);
                Assert.assertNotNull("Geen expressie opgeleverd", waarde);
                Assert.assertTrue(attr.toString() + " waarde: lege lijst of NULL verwacht",
                    (waarde.isLijstExpressie() && waarde.aantalElementen() == 0) || waarde.isNull(null));
                if (!attr.isLijst()) {
                    Assert.assertEquals(attr.toString() + " historische attributen: lege lijst verwacht", legeLijst,
                        attr.getHistorischeAttributen(leegPersoon));
                }
            }
        }

        final Persoon leegPersoonView = new PersoonView(leegPersoon);
        for (final ExpressieAttribuut attr : ExpressieAttribuut.values()) {
            if (attr.getParentType() == ExpressieType.PERSOON && !SOORT.equals(attr.getSyntax())) {
                Assert.assertEquals(attr.toString() + " attribuut: null verwacht", null,
                    attr.getAttribuut(leegPersoonView));
                final Expressie waarde = attr.getAttribuutWaarde(leegPersoonView);
                Assert.assertTrue(attr.toString() + " waarde: lege lijst of NULL verwacht",
                    (waarde.isLijstExpressie() && waarde.aantalElementen() == 0) || waarde.isNull(null));
                if (!attr.isLijst()) {
                    Assert.assertEquals(attr.toString() + " historische attributen: lege lijst verwacht", legeLijst,
                        attr.getHistorischeAttributen(leegPersoonView));
                }
            }
        }
    }

    @Test
    public void testContextAssignments() {
        final Context context = new Context();
        context.definieer("a", new GetalLiteralExpressie(2));
        context.definieer("b", new GetalLiteralExpressie(5));
        Assert.assertTrue(context.definieert("a"));
        Assert.assertTrue(context.definieert("b"));
        Assert.assertNotNull(context.zoekWaarde("a"));
        Assert.assertNotNull(context.zoekWaarde("b"));
        Assert.assertNull(context.zoekWaarde("c"));
        Assert.assertEquals(5, context.zoekWaarde("b").alsInteger());
        Assert.assertEquals(ExpressieType.GETAL, context.zoekType("a"));
        Assert.assertEquals(ExpressieType.GETAL, context.zoekType("b"));

        Assert.assertEquals("[b, a]", context.identifiers().toString());

        context.declareer("a", ExpressieType.DATUM);
        Assert.assertTrue(context.definieert("a"));
        Assert.assertEquals(ExpressieType.DATUM, context.zoekType("a"));

        Assert.assertEquals("[b, a]", context.identifiers().toString());
    }

    @Test
    public void textContextDeclaraties() {
        final Context context = new Context();
        context.declareer("a", ExpressieType.BOOLEAN);
        context.declareer("b", ExpressieType.GETAL);
        Assert.assertTrue(context.definieert("a"));
        Assert.assertTrue(context.definieert("b"));
        Assert.assertEquals(ExpressieType.BOOLEAN, context.zoekType("a"));
        Assert.assertEquals(ExpressieType.GETAL, context.zoekType("b"));
        Assert.assertNull(context.zoekType("c"));

        Assert.assertEquals("[b, a]", context.identifiers().toString());

        context.declareer("a", ExpressieType.DATUM);
        Assert.assertTrue(context.definieert("a"));
        Assert.assertEquals(ExpressieType.DATUM, context.zoekType("a"));

        context.definieer("b", new StringLiteralExpressie("test"));
        Assert.assertTrue(context.definieert("b"));
        Assert.assertEquals(ExpressieType.STRING, context.zoekType("b"));

        Assert.assertEquals("[b, a]", context.identifiers().toString());
    }

    @Test
    public void testEnclosingContext() {
        final Context enclosingContext = new Context();
        enclosingContext.definieer("a", new GetalLiteralExpressie(2));
        enclosingContext.definieer("b", new GetalLiteralExpressie(5));
        final Context context = new Context(enclosingContext);
        context.definieer("c", new GetalLiteralExpressie(8));
        context.definieer("a", new GetalLiteralExpressie(12));

        Assert.assertEquals(12, context.zoekWaarde("a").alsInteger());
        Assert.assertEquals(5, context.zoekWaarde("b").alsInteger());
        Assert.assertEquals(8, context.zoekWaarde("c").alsInteger());
        Assert.assertEquals("[b, a]", enclosingContext.identifiers().toString());
        Assert.assertEquals("[b, c, a]", context.identifiers().toString());
    }
}
