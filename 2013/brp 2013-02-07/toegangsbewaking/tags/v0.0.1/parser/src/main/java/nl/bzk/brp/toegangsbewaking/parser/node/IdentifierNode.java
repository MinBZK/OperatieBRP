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
 * Identifier. Te binden aan datastructuren van de BRP.
 */
public class IdentifierNode extends OperandNode {

    private String identifier;

    public IdentifierNode(final Node parentNode, final Token token, final String identifier) {
        super(parentNode, token);
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void debugDump(final List<String> script, final int indent) {
        script.add(String.format("%1$sIdentifier: %2$s", indentToStr(indent), identifier));
    }

    public void accept(final NodeVisitor visitor) throws ParserException {
        visitor.visit(this);
    }

}
