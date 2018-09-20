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
public class FunctieNode extends OperatorNode {

    private final String   functie;
    private final int      aantalArgumenten;
    private final ListNode argumenten;

    public FunctieNode(final Node parentNode, final Token token, final OperatorType operatorType, final String functie,
            final int aantalArgumenten)
    {
        super(parentNode, token, operatorType);
        this.functie = functie;
        this.aantalArgumenten = aantalArgumenten;
        argumenten = new ListNode(parentNode, token);
    }

    public void bindArgumenten(final Node node) throws ParserException {
        gebonden = true;

        // Enkele argument
        if (node instanceof OperandNode) {
            argumenten.add(node);
        } else {
            // Lijst met argumenten
            assert node instanceof ListNode;

            // Zet elementen uit de lijst node over in de argumenten lijst
            ListNode listNode = (ListNode) node;
            while (listNode.getNodeCount() > 0) {
                Node localNode = listNode.getNode(0);

                // Argument moet een operand zijn
                if (!(localNode instanceof OperandNode)) {
                    throw new ParserException("Syntax error.", localNode.getToken());
                }

                // Voeg argument toe en haal het weg uit de lijst
                argumenten.add(localNode);
                listNode.removeAt(0);

                // Als de lijst niet leeg is ...
                if (listNode.getNodeCount() > 0) {
                    // Volgt een separator ...
                    localNode = listNode.getNode(0);
                    if (!(localNode instanceof ArgumentSeparatorNode)) {
                        throw new ParserException("Syntax error.", localNode.getToken());
                    }
                    listNode.removeAt(0);
                    localNode = null;

                    // met nog een argument
                    if (listNode.getNodeCount() == 0) {
                        throw new ParserException("Syntax error.", null);
                    }
                }
            }
        }

        // Controleer aantal argumenten
        if (aantalArgumenten != argumenten.getNodeCount()) {
            throw new ParserException("Ongeldig aantal argumenten.", token);
        }
    }

    @Override
    public void debugDump(final List<String> script, final int indent) {
        script.add(String.format("%1$sFunctie: %2$s", indentToStr(indent), functie));
        if (argumenten.getNodeCount() > 0) {
            for (int i = 0; i < argumenten.getNodeCount(); i++) {
                argumenten.getNode(i).debugDump(script, indent + 1);
            }
        }
    }

    public String getFunctie() {
        return functie;
    }

    public ListNode getArgumenten() {
        return argumenten;
    }

    public int getAantalArgumenten() {
        return aantalArgumenten;
    }

    @Override
    public void accept(final NodeVisitor visitor) throws ParserException {
        visitor.visit(this);
    }

}
