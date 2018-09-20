/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.node;

import java.util.List;

import nl.bzk.brp.toegangsbewaking.parser.ParserException;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.Token;

/**
 * String constante.
 */
public class StringNode extends ConstanteNode {

    private final String waarde;

    public StringNode(final Node parentNode, final Token token, final String waarde) {
        super(parentNode, token);
        this.waarde = waarde;
    }

    public String getWaarde() {
        return waarde;
    }

    @Override
    public void debugDump(final List<String> script, final int indent) {
        script.add(String.format("%1$sString: %2$s", indentToStr(indent), waarde));
    }

    @Override
    public void accept(final NodeVisitor visitor) throws ParserException {
        visitor.visit(this);
    }
}
