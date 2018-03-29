/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.vragen.parser;

import static org.junit.Assert.assertEquals;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.vragen.parser.AdhocInhoudParser;
import org.junit.Test;

public class AdhocInhoudParserTest {
    @Test
    public void parse() {
        List<Lo3CategorieWaarde> result = AdhocInhoudParser.parse("000220101701100101234567890");
        assertEquals("[Lo3CategorieWaarde[categorie=01,elementen={ELEMENT_0110=1234567890}]]", result.toString());
    }

    @Test
    public void parseMetPuntAdres() {
        List<Lo3CategorieWaarde> result = AdhocInhoudParser.parse("00013080081110001.");
        assertEquals("[Lo3CategorieWaarde[categorie=08,elementen={ELEMENT_1110=}]]", result.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseMetSyntaxError() {
        AdhocInhoudParser.parse("000222");
    }
}
