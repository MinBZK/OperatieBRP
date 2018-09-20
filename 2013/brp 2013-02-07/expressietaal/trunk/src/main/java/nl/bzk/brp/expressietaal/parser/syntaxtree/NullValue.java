/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.expressietaal.symbols.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.symbols.Keywords;

/**
 * Representeert NULL-waarden. Dit zijn waarden van attributen die niet bekend zijn van een persoon.
 */
public class NullValue extends AbstractLiteralExpressie {
    @Override
    public ExpressieType getType() {
        return ExpressieType.UNKNOWN;
    }

    @Override
    public String alsLeesbareString() {
        return DefaultKeywordMapping.getSyntax(Keywords.ONBEKEND);
    }

    @Override
    public String alsFormeleString() {
        return "^";
    }

    @Override
    public boolean isRootObject() {
        return false;
    }
}
