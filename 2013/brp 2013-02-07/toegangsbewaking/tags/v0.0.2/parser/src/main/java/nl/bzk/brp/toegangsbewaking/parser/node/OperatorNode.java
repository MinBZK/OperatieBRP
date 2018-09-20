/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.node;

import nl.bzk.brp.toegangsbewaking.parser.OperatorType;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.Token;

/**
 * Base class voor alle operator nodes.
 */
public abstract class OperatorNode extends OperandNode {

    private final OperatorType operatorType;

    public OperatorNode(final Node parentNode, final Token token, final OperatorType operatorType) {
        super(parentNode, token);
        this.operatorType = operatorType;
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }
}
