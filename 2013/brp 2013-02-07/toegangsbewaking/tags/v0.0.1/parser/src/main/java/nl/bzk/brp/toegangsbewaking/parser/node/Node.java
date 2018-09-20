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
 * Base class voor alle node classes.
 */
public abstract class Node {

    protected Node parentNode;
    protected Token token;
    protected boolean gebonden;

    public Node(final Node parentNode, final Token token) {
        this.parentNode = parentNode;
        this.token = token;
        this.gebonden = false;
    }

    public abstract void debugDump(final List<String> script, final int indent);

    public Node getParent() {
        return parentNode;
    }

    public void setParentNode(final Node parent) {
        this.parentNode = parent;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(final Token token) {
        this.token = token;
    }

    public boolean getGebonden() {
        return gebonden;
    }

    protected String indentToStr(final int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
        return sb.toString();
    }

    public abstract void accept(NodeVisitor visitor) throws ParserException;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("TOKEN: %s (%s)", token, gebonden);
    }
}
