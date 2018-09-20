/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.toegangsbewaking.parser.node.ListNode;
import nl.bzk.brp.toegangsbewaking.parser.node.Node;
import nl.bzk.brp.toegangsbewaking.parser.node.OperatorNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Container van de parse tree van een BRP Expressie. Deze parse tree bestaat uit een boom van nodes, welke ten tijde
 * van het creeeren van de parse tree wordt opgebouwd op basis van een lijst van nodes.
 */
public class ParseTree {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParseTree.class);

    private final Node          rootNode;

    /**
     * Standaard constructor voor het aanmaken van een nieuwe instantie. De parse tree wordt opgebouwd op basis
     * van een lege {@link ListNode}.
     *
     * @throws ParserException indien er een fout optreedt tijdens het parsen.
     */
    public ParseTree() throws ParserException {
        this(new ListNode(null, null));
    }

    /**
     * Standaard constructor voor het aanmaken van een nieuwe instantie, waarbij de parse tree wordt opgebouwd op basis
     * van de de opgegeven {@link ListNode}.
     *
     * @param listNode de lijst met nodes waar de parse tree vor moet worden opgebouwd.
     * @throws ParserException indien er een fout optreedt tijdens het parsen.
     */
    public ParseTree(final ListNode listNode) throws ParserException {
        super();
        rootNode = bouwBoowOpBasisVanListNode(listNode);
    }

    /**
     * Bouwt de parse tree op op basis van de opgegeven {@link ListNode}, welke via recursie en binding omgezet wordt in
     * een node tree met een {@link nl.bzk.brp.toegangsbewaking.parser.node.OperandNode} als root node.
     *
     * @param node de nodelijst waar de parse tree voor moet worden opgebouwd.
     * @return de parse tree.
     * @throws ParserException indien er een fout optreedt tijdens het parsen.
     */
    private Node bouwBoowOpBasisVanListNode(final ListNode node) throws ParserException {
        final Node nieuweNode = node.bouwBoomVanOnderliggendeNodes();

        if (nieuweNode == null) {
            LOGGER.error("Binden van root node leverde geen (nieuwe) node op.");
            throw new ParserException("Syntax error.", node.getToken());
        }

        // Na binding hoort de root node een operator node te zijn
        if (!(nieuweNode instanceof OperatorNode)) {
            LOGGER.error("RootNode is niet van het verwachte type OperatorNode.");
            throw new ParserException("Syntax error.", nieuweNode.getToken());
        }
        return nieuweNode;
    }

    /**
     * Geeft de root node terug.
     *
     * @return de root node.
     */
    public Node getRootNode() {
        return rootNode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        List<String> script = new ArrayList<String>();
        rootNode.debugDump(script, 0);

        final String ls = System.getProperty("line.separator");

        StringBuilder sb = new StringBuilder("Parse Tree: ");
        sb.append(ls);
        for (String line : script) {
            sb.append(line).append(ls);
        }

        return sb.toString();
    }
}
