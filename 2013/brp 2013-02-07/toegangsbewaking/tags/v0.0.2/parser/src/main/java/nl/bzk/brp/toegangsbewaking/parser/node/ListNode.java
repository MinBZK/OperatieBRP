/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.node;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.toegangsbewaking.parser.ParserException;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.Token;


/**
 * Node met daarin een lijst van nodes.
 */
public class ListNode extends Node {

    private static final int MIN_AANTAL_NODES_VOOR_BINAIRE_OPERATOR = 3;
    private final List<Node> list;

    public ListNode(final Node parentNode, final Token token) {
        super(parentNode, token);
        list = new ArrayList<Node>();
    }

    public Node bouwBoomVanOnderliggendeNodes() throws ParserException {
        Node result = null;

        if (list.isEmpty()) {
            return null;
        }

        // Recursie depth first: Bind eerst de argumenten van sublijsten en vervang deze sublijsten
        for (int i = 0; i < list.size(); i++) {
            Node node = list.get(i);
            if (node instanceof ListNode) {
                Node nieuweNode = ((ListNode) node).bouwBoomVanOnderliggendeNodes();
                if (nieuweNode != null) {
                    list.set(i, nieuweNode);
                }
            }
        }
        if (getNodeCount() == 1 && list.get(0) instanceof OperatorNode) {
            result = list.get(0);
            removeAt(0);
            return result;
        }
        if (getNodeCount() == 1 && (list.get(0) instanceof OperandNode || list.get(0) instanceof ListNode)) {
            return result;
        }

        // Bind argumenten aan functies
        for (int i = 0; i < list.size(); i++) {
            Node node = list.get(i);
            if (node instanceof FunctieNode && !node.getGebonden()) {
                FunctieNode functieNode = (FunctieNode) node;
                if (i + functieNode.getAantalArgumenten() > list.size()) {
                    throw new ParserException("Syntax error; te weinig argumenten voor functie", node.getToken());
                }

                if (functieNode.getAantalArgumenten() > 0) {
                    Node volgendeNode = list.get(i + 1);
                    if (volgendeNode instanceof OperandNode || volgendeNode instanceof ListNode) {
                        functieNode.bindArgumenten(volgendeNode);
                    } else {
                        throw new ParserException("Syntax error.", volgendeNode.getToken());
                    }
                    removeAt(i + 1);
                    // TODO: Moeten we hier niet ook nog i ophogen???
                }
            }
        }
        if (list.size() == 1 && list.get(0) instanceof FunctieNode) {
            result = list.get(0);
            removeAt(0);
            return result;
        }

        // Bind argumenten aan unaire operatoren
        for (int i = 0; i < list.size(); i++) {
            Node node = list.get(i);
            if (node instanceof UnaireOperatorNode && !node.getGebonden()) {
                if (i + 2 > list.size()) {
                    throw new ParserException("Syntax error.", node.getToken());
                } else {
                    Node volgendeNode = list.get(i + 1);
                    if (!(volgendeNode instanceof OperatorNode)) {
                        throw new ParserException("Syntax error.", volgendeNode.getToken());
                    } else {
                        ((UnaireOperatorNode) node).bindArgumenten((OperatorNode) volgendeNode);
                    }
                    removeAt(i + 1);
                    // TODO: Moeten we hier niet ook nog i ophogen???
                }
            }
        }
        if (list.size() == 1 && list.get(0) instanceof UnaireOperatorNode) {
            result = list.get(0);
            removeAt(0);
            return result;
        }

        // Controle of de lijst een lijst met argumenten voor een functie is
        boolean separatorFound = false;
        for (Node node : list) {
            if (node instanceof ArgumentSeparatorNode) {
                separatorFound = true;
                break;
            }
        }
        if (separatorFound) {
            return result;
        }

        // Bind argumenten aan binaire operatoren
        return bindArgumentenAanBinaireOperatoren();
    }

    /**
     * Bind argumenten aan binaire operatoren.
     *
     * @return
     * @throws ParserException
     */
    private Node bindArgumentenAanBinaireOperatoren() throws ParserException {
        Node result;

        // Binding vereist tenminste drie argumenten
        if (list.size() < MIN_AANTAL_NODES_VOOR_BINAIRE_OPERATOR) {
            throw new ParserException("Syntax error: Onverwacht aantal argumenten voor binaire operator.",
                    this.getToken());
        }

        splitsLijstVanOperatoren();

        // Er zouden nu drie argumenten moeten zijn
        if (list.size() != MIN_AANTAL_NODES_VOOR_BINAIRE_OPERATOR) {
            throw new ParserException("Syntax error.", this.getToken());
        }
        if (!(list.get(0) instanceof OperandNode)) {
            throw new ParserException("Syntax error.", list.get(0).getToken());
        }
        if (!(list.get(1) instanceof BinaireOperatorNode)) {
            throw new ParserException("Syntax error.", list.get(0).getToken());
        }
        if (!(list.get(2) instanceof OperandNode)) {
            throw new ParserException("Syntax error.", list.get(0).getToken());
        }
        result = list.get(1);
        ((BinaireOperatorNode) result).bindArgumenten((OperandNode) list.get(0), (OperandNode) list.get(2));
        removeAll();
        return result;
    }

    /**
     * Als er meer argumenten zijn dan hebben we mogelijk te maken met meerdere binaire operatoren achter elkaar.
     * Bijvoorbeeld x and y and z. In dat geval stoppen we y and z in een sublijst en zorgen eerst voor binding van die
     * sublijst. Daardoor ontstaat feitelijk: x and (y and z).
     *
     * @throws ParserException
     */
    private void splitsLijstVanOperatoren() throws ParserException {
        if (list.size() > MIN_AANTAL_NODES_VOOR_BINAIRE_OPERATOR) {
            // Maak een een tijdelijke lijst
            ListNode tempListNode = new ListNode(this, this.getToken());
            while (list.size() > 2) {
                tempListNode.add(list.get(2));
                removeAt(2);
            }

            // Bind deze lijst (recursief)
            Node nieuweNode = tempListNode.bouwBoomVanOnderliggendeNodes();

            // Het resultaat na binding wordt het nieuwe derde argument
            if (nieuweNode != null) {
                add(nieuweNode);
                tempListNode = null;
            } else {
                // Lijsten moeten altijd een nieuwe node opleveren
                throw new ParserException("Syntax error.", this.getToken());
            }
        }
    }

    public int add(final Node node) {
        list.add(node);
        return list.indexOf(node);
    }

    public void removeAt(final int index) {
        list.remove(index);
    }

    public void removeAll() {
        list.clear();
    }

    public int getNodeCount() {
        return list.size();
    }

    public Node getNode(final int index) {
        return list.get(index);
    }

    public void setNode(final int index, final Node node) {
        list.set(index, node);
    }

    @Override
    public void debugDump(final List<String> script, final int indent) {
        script.add(indentToStr(indent) + "List");
        for (Node node : list) {
            node.debugDump(script, indent + 1);
        }
    }

    @Override
    public void accept(final NodeVisitor visitor) throws ParserException {
        visitor.visit(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("TOKENLIST: %s (%s)", list, gebonden);
    }
}
