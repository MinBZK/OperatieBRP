/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.node;

import java.util.List;

import nl.bzk.brp.toegangsbewaking.parser.OperatorType;
import nl.bzk.brp.toegangsbewaking.parser.ParserException;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.Token;


/**
 * Unaire operator.
 */
public class UnaireOperatorNode extends OperatorNode {

    private OperatorNode argument;

    public UnaireOperatorNode(final Node parentNode, final Token token, final OperatorType operatorType) {
        super(parentNode, token, operatorType);
        argument = null;
    }

    public void bindArgumenten(final OperatorNode node) {
        gebonden = true;
        argument = node;
    }

    public OperatorNode getArgument() {
        return argument;
    }

    @Override
    public void debugDump(final List<String> script, final int indent) {
        script.add(String.format("%1$sUnaire operator: %2$s", indentToStr(indent), getOperatorType().getNaam()));
        if (argument != null) {
            argument.debugDump(script, indent + 1);
        }
    }

    @Override
    public void accept(final NodeVisitor visitor) throws ParserException {
        visitor.visit(this);
    }

}
