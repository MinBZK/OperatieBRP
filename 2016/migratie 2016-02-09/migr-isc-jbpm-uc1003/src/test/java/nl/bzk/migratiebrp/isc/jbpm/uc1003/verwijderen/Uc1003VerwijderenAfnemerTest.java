/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.verwijderen;

import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Af11Bericht;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.AbstractUc1003Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test de technische flows rondom het opvragen van de blokkerings info.
 */
public class Uc1003VerwijderenAfnemerTest extends AbstractUc1003Test {

    private static final String A_NUMMER = "1234567890";
    private static final String AFNEMER = "123456";

    public Uc1003VerwijderenAfnemerTest() {
        super("/uc1003-verwijderen/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
        // setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @Before
    public void startProces() {
        // Start
        startProcess(VerwijderenAfnIndTestUtil.maakAv01Bericht(AFNEMER, A_NUMMER));
    }

    @After
    public void endProces() {
        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void test() {
        controleerBerichten(0, 1, 0);
        final Af11Bericht af11 = getBericht(Af11Bericht.class);
        Assert.assertEquals("X", af11.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000", af11.getHeader(Lo3HeaderVeld.GEMEENTE));
        Assert.assertEquals("0000000000", af11.getHeader(Lo3HeaderVeld.A_NUMMER));
    }
}
