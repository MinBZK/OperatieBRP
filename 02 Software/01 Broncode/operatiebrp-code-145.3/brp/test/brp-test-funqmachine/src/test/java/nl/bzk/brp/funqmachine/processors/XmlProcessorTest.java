/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors;

import org.junit.Test;

public class XmlProcessorTest {

    private XmlProcessor xmlProcessor = new XmlProcessor();

    @Test
    public void testBerichValidatie() {
        final String test = "<aap><noot><mies>test</mies></noot><pinda><rots>test</rots></pinda></aap>";
        final String expected = "<aap><pinda><rots>test</rots></pinda><noot><mies>test</mies></noot></aap>";

        xmlProcessor.vergelijk("//aap", expected, test);
    }

    @Test
    public void testBerichValidatieMetNegeerWaarde() {
        final String test = "<aap><noot><mies>test</mies></noot><pinda><rots>test</rots></pinda></aap>";
        final String expected = "<aap><pinda><rots>*</rots></pinda><noot><mies>*</mies></noot></aap>";

        xmlProcessor.vergelijk("//aap", expected, test);
    }

    @Test
    public void testBerichValidatieMetNegeerNodes() {
        final String test = "<aap><noot><mies>test</mies></noot><pinda><rots>test</rots><rots>test2</rots></pinda></aap>";
        final String expected = "<aap><pinda>*</pinda><noot><mies>*</mies></noot></aap>";

        xmlProcessor.vergelijk("//aap", expected, test);
    }

    @Test(expected = AssertionError.class)
    public void testBerichValidatieMetAttributen() {
        final String test = "<aap><noot><mies>test</mies></noot><pinda><rots a=\"1\">test</rots><rots a=\"2\">test2</rots></pinda></aap>";
        final String expected = "<aap><pinda><rots a=\"2\">test2</rots></pinda><noot><mies>*</mies></noot></aap>";

        xmlProcessor.vergelijk("//aap", expected, test);
    }

    @Test(expected = AssertionError.class)
    public void testBerichValidatieMetAttributenEnAttribuutIsGenegeerd() {
        final String test = "<aap><noot><mies>test</mies></noot><pinda><rots a=\"1\">test</rots><rots a=\"2\">test2</rots></pinda></aap>";
        final String expected = "<aap><pinda><rots a=\"*\">test2</rots></pinda><noot><mies>*</mies></noot></aap>";

        xmlProcessor.vergelijk("//aap", expected, test);
    }
}
