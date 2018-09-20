/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;

import org.junit.Assert;
import org.junit.Test;


public class StamgegevenBuilderTest {

    @Test
    public void testBouwDynamischStamgegeven() {
        Short gemeenteCode = (short) 1234;
        Gemeente gemeente =
                StamgegevenBuilder.bouwDynamischStamgegeven(Gemeente.class, new GemeenteCodeAttribuut(gemeenteCode));
        Assert.assertEquals(gemeenteCode, gemeente.getCode().getWaarde());

        Short nationaliteitCode = (short) 4321;
        Nationaliteit nationaliteit =
                StamgegevenBuilder.bouwDynamischStamgegeven(Nationaliteit.class, new NationaliteitcodeAttribuut(
                        nationaliteitCode));
        Assert.assertEquals(nationaliteitCode, nationaliteit.getCode().getWaarde());
    }

}
