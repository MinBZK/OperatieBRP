/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

import org.junit.Assert;
import org.junit.Test;

public class TekstHelperTest {

    @Test
    public void test() {
        final TekstHelper subject = new TekstHelper("(01.02.40 OGA \"ACHTERNAAM\")");
        Assert.assertEquals("(01.02.40 OGA T00)", subject.getVeiligeRegel());
        subject.setVeiligeRegel("01.02.40 OGA T00");
        Assert.assertEquals("01.02.40 OGA \"ACHTERNAAM\"", subject.getGbaVoorwaardeRegel());
    }

    @Test
    public void testMeerdere() {
        final TekstHelper subject = new TekstHelper("(01.02.40 OGA \"ACHT(ERNAAM\" OFVGL \"IETS\" OFVGL \"AND)ERS\")");
        Assert.assertEquals("(01.02.40 OGA T00 OFVGL T01 OFVGL T02)", subject.getVeiligeRegel());
        subject.setVeiligeRegel("01.02.40 OGA T00 OFVGL T01 OFVGL T02");
        Assert.assertEquals("01.02.40 OGA \"ACHT(ERNAAM\" OFVGL \"IETS\" OFVGL \"AND)ERS\"", subject.getGbaVoorwaardeRegel());
    }

    @Test
    public void testMetQuotes() {
        final TekstHelper subject = new TekstHelper("(01.02.40 OGA \"AC/\"HTERNAAM\")");
        Assert.assertEquals("(01.02.40 OGA T00)", subject.getVeiligeRegel());
        subject.setVeiligeRegel("01.02.40 OGA T00");
        Assert.assertEquals("01.02.40 OGA \"AC/\"HTERNAAM\"", subject.getGbaVoorwaardeRegel());
    }
}
