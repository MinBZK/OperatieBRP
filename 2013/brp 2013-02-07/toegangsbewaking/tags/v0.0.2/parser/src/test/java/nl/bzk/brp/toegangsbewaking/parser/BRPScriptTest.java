/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.toegangsbewaking.parser.script.BRPScript;
import org.junit.Test;


public class BRPScriptTest {

    @Test
    public void test() {
        long start = System.currentTimeMillis();

        for (int i = 1; i <= 1; i++) {
            List<String> lines = new ArrayList<String>();
            lines.add("( Geslacht= 'M') & (Leeftijd >=55) & ( (Provincie = 'U''trecht')");
            lines.add("of (Provincie = 'Noord Holland' ) ) & niet (Woonplaats = 'Utrecht')");

            BRPScript script = new BRPScript(lines);
            script.parse();
        }
        System.out.println("Time in millis: " + (System.currentTimeMillis() - start));
    }

}
