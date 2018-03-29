/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.algemeen;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.NullLiteral;
import nl.bzk.brp.domain.expressie.StringLiteral;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test van Expressie.
 */
public final class ExpressieTest {

    @Test
    public void testGetElementen() {
        List<Expressie> elementen;
        LijstExpressie list = new LijstExpressie();
        Assert.assertEquals("[]", list.getElementen().toString());

        elementen = new ArrayList<>();
        elementen.add(new GetalLiteral(10));
        list = new LijstExpressie(elementen);
        Assert.assertEquals("[10]", list.getElementen().toString());

        elementen = new ArrayList<>();
        elementen.add(new GetalLiteral(20));
        elementen.add(new GetalLiteral(30));
        list = new LijstExpressie(elementen);
        Assert.assertEquals("[20, 30]", list.getElementen().toString());
    }

    @Test
    public void testGetElement() {
        List<Expressie> elementen = new ArrayList<>();
        elementen.add(new GetalLiteral(10));
        LijstExpressie lijst = new LijstExpressie(elementen);
        Assert.assertEquals("10", lijst.getElement(0).toString());
        elementen = new ArrayList<>();
        elementen.add(new GetalLiteral(10));
        elementen.add(new GetalLiteral(20));
        lijst = new LijstExpressie(elementen);
        Assert.assertEquals("{10, 20}", lijst.toString());
        Assert.assertEquals("10", lijst.getElement(0).toString());
        Assert.assertEquals("20", lijst.getElement(1).toString());
    }

    @Test
    public void testBooleanLiteralExpressie() {
        BooleanLiteral b = BooleanLiteral.WAAR;
        Assert.assertTrue(b.alsBoolean());
        b = BooleanLiteral.ONWAAR;
        Assert.assertFalse(b.alsBoolean());
        Assert.assertEquals(ExpressieType.BOOLEAN, b.getType(null));
        Assert.assertTrue(b.isConstanteWaarde());
        Assert.assertTrue(b.isConstanteWaarde(ExpressieType.BOOLEAN));
    }


    @Test
    public void testReguliereExpressies() {
        StringLiteral str;

        str = new StringLiteral("abc");
        Assert.assertEquals("Regexp niet correct", "abc", str.getStringWaardeAlsReguliereExpressie());
        str = new StringLiteral("");
        Assert.assertEquals("Regexp niet correct", "", str.getStringWaardeAlsReguliereExpressie());
        str = new StringLiteral("abc.");
        Assert.assertEquals("Regexp niet correct", "abc\\.", str.getStringWaardeAlsReguliereExpressie());
        str = new StringLiteral("?abc");
        Assert.assertEquals("Regexp niet correct", ".abc", str.getStringWaardeAlsReguliereExpressie());
        str = new StringLiteral("ab*c");
        Assert.assertEquals("Regexp niet correct", "ab.*c", str.getStringWaardeAlsReguliereExpressie());
        str = new StringLiteral("ab^c");
        Assert.assertEquals("Regexp niet correct", "ab\\^c", str.getStringWaardeAlsReguliereExpressie());
        str = new StringLiteral(".");
        Assert.assertEquals("Regexp niet correct", "\\.", str.getStringWaardeAlsReguliereExpressie());
        str = new StringLiteral("a*b?*.c");
        Assert.assertEquals("Regexp niet correct", "a.*b..*\\.c", str.getStringWaardeAlsReguliereExpressie());
    }

    @Test
    public void testWaarden() {
        {
            GetalLiteral expressie = new GetalLiteral(10);
            Assert.assertEquals(10, expressie.getWaarde());
            Assert.assertEquals("10", expressie.alsString());
            Assert.assertEquals(false, expressie.alsBoolean());
        }

        {
            BooleanLiteral expressie = BooleanLiteral.WAAR;
            Assert.assertEquals("WAAR", expressie.alsString());
            Assert.assertEquals(true, expressie.alsBoolean());
        }

        {
            BooleanLiteral expressie = BooleanLiteral.ONWAAR;
            Assert.assertEquals("ONWAAR", expressie.alsString());
            Assert.assertEquals(false, expressie.alsBoolean());
        }

        {
            NullLiteral expressie = NullLiteral.INSTANCE;
            Assert.assertEquals(false, expressie.alsBoolean());
        }

        {
            StringLiteral expressie = new StringLiteral("");
            Assert.assertEquals("", expressie.alsString());
            Assert.assertEquals("", expressie.getStringWaardeAlsReguliereExpressie());
            Assert.assertEquals(false, expressie.alsBoolean());
        }

        {
            StringLiteral expressie = new StringLiteral("test");
            Assert.assertEquals("test", expressie.alsString());
            Assert.assertEquals("test", expressie.getStringWaardeAlsReguliereExpressie());
            Assert.assertEquals(false, expressie.alsBoolean());
        }

        {
            StringLiteral expressie = new StringLiteral("test?");
            Assert.assertEquals("test?", expressie.alsString());
            Assert.assertEquals("test.", expressie.getStringWaardeAlsReguliereExpressie());
            Assert.assertEquals(false, expressie.alsBoolean());
        }
    }

    @Test
    public void testVeiligeExpressie() {
//        final PlusOperator expressie = new PlusOperator(null, null);
//        Assert.assertNotNull(expressie.getOperandLinks());
//        Assert.assertNotNull(expressie.getOperandRechts());
//        Assert.assertTrue(expressie.getOperandLinks().isNull(null));
//        Assert.assertTrue(expressie.getOperandRechts().isNull(null));
    }

    @Test
    public void testContextAssignments() {
        final Context context = new Context();
        context.definieer("a", new GetalLiteral(2));
        context.definieer("b", new GetalLiteral(5));
        Assert.assertTrue(context.definieert("a"));
        Assert.assertTrue(context.definieert("b"));
        Assert.assertNotNull(context.zoekWaarde("a"));
        Assert.assertNotNull(context.zoekWaarde("b"));
        Assert.assertNull(context.zoekWaarde("c"));
        Assert.assertEquals(5, ((GetalLiteral)context.zoekWaarde("b")).getWaarde());
        Assert.assertEquals(ExpressieType.GETAL, context.zoekType("a"));
        Assert.assertEquals(ExpressieType.GETAL, context.zoekType("b"));

        Assert.assertEquals("[a, b]", context.identifiers().toString());

        context.declareer("a", ExpressieType.DATUM);
        Assert.assertTrue(context.definieert("a"));
        Assert.assertEquals(ExpressieType.DATUM, context.zoekType("a"));

        Assert.assertEquals("[a, b]", context.identifiers().toString());
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

        Assert.assertEquals("[a, b]", context.identifiers().toString());

        context.declareer("a", ExpressieType.DATUM);
        Assert.assertTrue(context.definieert("a"));
        Assert.assertEquals(ExpressieType.DATUM, context.zoekType("a"));

        context.definieer("b", new StringLiteral("test"));
        Assert.assertTrue(context.definieert("b"));
        Assert.assertEquals(ExpressieType.STRING, context.zoekType("b"));

        Assert.assertEquals("[a, b]", context.identifiers().toString());
    }

    @Test
    public void testEnclosingContext() {
        final Context enclosingContext = new Context();
        enclosingContext.definieer("a", new GetalLiteral(2));
        enclosingContext.definieer("b", new GetalLiteral(5));
        final Context context = new Context(enclosingContext);
        context.definieer("c", new GetalLiteral(8));
        context.definieer("a", new GetalLiteral(12));

        Assert.assertEquals(12, ((GetalLiteral)context.zoekWaarde("a")).getWaarde());
        Assert.assertEquals(5, ((GetalLiteral)context.zoekWaarde("b")).getWaarde());
        Assert.assertEquals(8, ((GetalLiteral)context.zoekWaarde("c")).getWaarde());
        Assert.assertEquals("[a, b]", enclosingContext.identifiers().toString());
        Assert.assertEquals("[a, b, c]", context.identifiers().toString());
    }
}
