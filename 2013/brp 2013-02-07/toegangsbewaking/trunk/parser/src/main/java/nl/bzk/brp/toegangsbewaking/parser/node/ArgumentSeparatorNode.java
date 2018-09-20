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
 * Separator van argumenten in een functie. Mag niet voorkomen in de uiteindelijke parse
 * tree. Is alleen aanwezig als tijdelijke structuur tijdens het parsen.
 */
public class ArgumentSeparatorNode extends Node {

    public ArgumentSeparatorNode(final Node parentNode, final Token token) {
        super(parentNode, token);
    }

    @Override
    public void debugDump(final List<String> script, final int indent) {
        script.add(String.format("%1$sArgument separator", indentToStr(indent)));
    }

    @Override
    public void accept(final NodeVisitor visitor) throws ParserException {
        visitor.visit(this);
    }

}
