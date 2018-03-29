/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.literal;

import nl.bzk.brp.domain.expressie.ElementnaamLiteral;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class ElementnaamLiteralTest {

    @Test
    public void testAttribuutcodeExpressie() throws ExpressieException {

        final Expressie expressie = ExpressieParser.parse("[geboorte.datum]");
        Assert.assertTrue(expressie instanceof ElementnaamLiteral);

    }
}
