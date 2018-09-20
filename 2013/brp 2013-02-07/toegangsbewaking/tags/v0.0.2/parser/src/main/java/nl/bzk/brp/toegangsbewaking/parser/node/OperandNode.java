/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.node;

import nl.bzk.brp.toegangsbewaking.parser.tokenizer.Token;

/**
 * Base class voor alle operand node classes. Alle nodes, behalve de TcListNode zijn
 * operand nodes. Operators kunnen immers bij nesting als operand gebruikt worden.
 */
public abstract class OperandNode extends Node {

    public OperandNode(final Node parentNode, final Token token) {
        super(parentNode, token);
    }

}
