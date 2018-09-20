/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class Lo3NationaliteitCodeTest {

    @Test
    public void testZonderOnderzoek() throws Exception {
        Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(1), new Lo3Datum(100), new Lo3Datum(200));
        Lo3NationaliteitCode lo3 = new Lo3NationaliteitCode("42", lo3Onderzoek);
        Lo3NationaliteitCode result = Lo3NationaliteitCode.zonderOnderzoek(lo3);
        assertNull(result.getOnderzoek());
        assertNull(Lo3NationaliteitCode.zonderOnderzoek(null));
    }
}
