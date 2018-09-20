/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import org.junit.Test;

public class ParserExceptionTest {

    @Test(expected = ParseException.class)
    public void testParseExceptionMetMelding() {

        throw new ParseException("Fout tijdens parsen.");

    }

    @Test(expected = ParseException.class)
    public void testParseExceptionMetMeldingEnExceptie() {

        throw new ParseException("Fout tijdens parsen.", new NullPointerException());

    }
}
