/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import nl.bzk.brp.domain.expressie.parser.exception.ExpressieParseException;
import org.junit.Test;

/**
 */
public class MaandnummerParserUtilTest {

    @Test
    public void maandnaamNaarMaandnummer() {
        final int jan = MaandnummerParserUtil.maandnaamNaarMaandnummer("JAN");
    }

    @Test(expected = ExpressieParseException.class)
    public void maandnaamNaarMaandnummerFail() {
        MaandnummerParserUtil.maandnaamNaarMaandnummer("xxx");
    }

}