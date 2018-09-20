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
 * Binaire operator.
 */
public class BinaireOperatorNode extends OperatorNode {

    private OperandNode argument1;
    private OperandNode argument2;

    public BinaireOperatorNode(final Node parentNode, final Token token, final OperatorType operatorType) {
        super(parentNode, token, operatorType);
        argument1 = null;
        argument2 = null;
    }

    public void bindArgumenten(final OperandNode node1, final OperandNode node2) {
        gebonden = true;

        argument1 = node1;
        argument2 = node2;
    }

    public OperandNode getArgument1() {
        return argument1;
    }

    public OperandNode getArgument2() {
        return argument2;
    }

    @Override
    public void debugDump(final List<String> script, final int indent) {
        script.add(String.format("%1$sBinaire operator: %2$s", indentToStr(indent), getOperatorType().getNaam()));
        if (argument1 != null) {
            argument1.debugDump(script, indent + 1);
        }
        if (argument2 != null) {
            argument2.debugDump(script, indent + 1);
        }
    }

    @Override
    public void accept(final NodeVisitor visitor) throws ParserException {
        visitor.visit(this);
    }

}
