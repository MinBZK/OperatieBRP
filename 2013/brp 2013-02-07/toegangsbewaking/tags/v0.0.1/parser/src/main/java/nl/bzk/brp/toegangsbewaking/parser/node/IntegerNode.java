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
 * Integer constante.
 */
public class IntegerNode extends ConstanteNode {

    private Integer waarde;

    public IntegerNode(final Node parentNode, final Token token, final Integer waarde) {
        super(parentNode, token);
        this.waarde = waarde;
    }

    public Integer getWaarde() {
        return waarde;
    }

    @Override
    public void debugDump(final List<String> script, final int indent) {
        script.add(String.format("%1$sInteger: %2$d", indentToStr(indent), waarde));
    }

    public void accept(final NodeVisitor visitor) throws ParserException {
        visitor.visit(this);
    }

}
