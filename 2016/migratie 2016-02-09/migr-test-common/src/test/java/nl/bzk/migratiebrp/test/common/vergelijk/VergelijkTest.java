/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.vergelijk;

import java.io.IOException;
import nl.bzk.migratiebrp.test.common.vergelijk.vergelijking.VergelijkingContext;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class VergelijkTest {

    @Test
    public void testExact() {
        final String expected = "sdfsjfasldfjdfl;adsjfdsa;f";
        final String actualOk = "sdfsjfasldfjdfl;adsjfdsa;f";
        final String actualNok = "asflasuhaslaudsdkulsfhds;f";

        Assert.assertTrue(Vergelijk.vergelijk(expected, actualOk));
        Assert.assertFalse(Vergelijk.vergelijk(expected, actualNok));
    }

    @Test
    public void testExactEnDecimal() {
        final String expected = "<bla>{decimal}</bla>";
        final String actualOk = "<bla>123123123</bla>";
        final String actualNok = "<bla>wr345weewr</bla>";

        Assert.assertTrue(Vergelijk.vergelijk(expected, actualOk));
        Assert.assertFalse(Vergelijk.vergelijk(expected, actualNok));
    }

    @Test
    public void testExactEnMultipleDecimal() {
        final String expected = "<bla>{decimal}</bla><blorp>{decimal}</blorp>";
        final String actualOk = "<bla>123123123</bla><blorp>454564654</blorp>";
        final String actualNok = "<bla>wer</bla><blorp>454564654</blorp>";

        Assert.assertTrue(Vergelijk.vergelijk(expected, actualOk));
        Assert.assertFalse(Vergelijk.vergelijk(expected, actualNok));
    }

    @Test
    public void testExactEnConstantDecimal() {
        final String expected = "<bla>{decimal:1}</bla>";
        final String actualOk = "<bla>123123123</bla>";
        final String actualNok = "<bla>wr345weewr</bla>";

        Assert.assertTrue(Vergelijk.vergelijk(expected, actualOk));
        Assert.assertFalse(Vergelijk.vergelijk(expected, actualNok));
    }

    @Test
    public void testExactEnmultipleConstantDecimal() {
        final String expected = "<bla>{decimal:1}</bla><blorp>{decimal:1}</blorp>";
        final String actualOk = "<bla>123123123</bla><blorp>123123123</blorp>";
        final String actualNok = "<bla>123123123</bla><blorp>454564654</blorp>";

        Assert.assertTrue(Vergelijk.vergelijk(expected, actualOk));
        Assert.assertFalse(Vergelijk.vergelijk(expected, actualNok));
    }

    @Test
    public void testExactEnMessageId() {
        final String expected = "<bla>{messageId}</bla>\n<blarp/>";
        final String actualOk = "<bla>272d3920-4533-11e2-bcfd-0800200c9a66</bla>\n<blarp/>";
        final String actualNok = "<bla>wr345weewr</bla>";

        Assert.assertTrue(Vergelijk.vergelijk(expected, actualOk));
        Assert.assertFalse(Vergelijk.vergelijk(expected, actualNok));
    }

    @Test
    public void testConsistentContext() {
        final String expected = "<bla>{decimal:1}</bla>";
        final String actualOk = "<bla>272</bla>";
        final String actualNok = "<bla>123</bla>";

        Assert.assertTrue(Vergelijk.vergelijk(expected, actualOk));
        Assert.assertTrue(Vergelijk.vergelijk(expected, actualNok));

        final VergelijkingContext context = new VergelijkingContext();
        Assert.assertTrue(Vergelijk.vergelijk(context, expected, actualOk));
        Assert.assertFalse(Vergelijk.vergelijk(context, expected, actualNok));
    }

    @Test
    public void testExactEnTimestamp() {
        final String expected = "<bla>{timestamp}</bla>";
        final String actualOk = "<bla>2013-11-29T08:00:13.274</bla>";
        final String actualNok = "<bla>wr345weewr</bla>";

        Assert.assertTrue(Vergelijk.vergelijk(expected, actualOk));
        Assert.assertFalse(Vergelijk.vergelijk(expected, actualNok));
    }

    @Test
    public void testExactEnText() {
        final String expected = "<bla>{text}</bla>";
        final String actualOk = "<bla>934563498 Abonnement Spontaan</bla>";
        final String actualNok = "<sla>asdfsdfasdfds</sla>";

        Assert.assertTrue(Vergelijk.vergelijk(expected, actualOk));
        Assert.assertFalse(Vergelijk.vergelijk(expected, actualNok));
    }

    @Test
    public void testPraktijk() throws IOException {
        final String expected = IOUtils.toString(VergelijkTest.class.getResourceAsStream("0003-in0002-vospg-vrij bericht.expected.txt"));
        final String actual = IOUtils.toString(VergelijkTest.class.getResourceAsStream("0003-in0002-vospg-vrij bericht.txt"));

        Assert.assertTrue(Vergelijk.vergelijk(expected, actual));
    }

}
