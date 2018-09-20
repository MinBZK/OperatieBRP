/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.node;

import nl.bzk.brp.toegangsbewaking.parser.ParserException;


/**
 * Interface voor classes die het Visitor pattern implementeren voor het aanroepen van node specifieke functies.
 * Classes die deze interface implementeren kunnen op een node de accept methode aanroepen met als argument de
 * implementatie van deze interface, met als gevolg dat de node op diens beurt de node specifieke methode op de
 * interface implementatie aanroept.
 */
public interface NodeVisitor {

    /**
     * Visit methode vanuit een {@link ListNode}.
     * @param visited de node die werd aangeroepen en waarvoor deze methode wordt aangeroepen.
     * @throws ParserException indien er fouten optreden bij de verwerking.
     */
    void visit(ListNode visited) throws ParserException;

    /**
     * Visit methode vanuit een {@link BinaireOperatorNode}.
     *
     * @param visited de node die werd aangeroepen en waarvoor deze methode wordt aangeroepen.
     * @throws ParserException indien er fouten optreden bij de verwerking.
     */
    void visit(BinaireOperatorNode visited) throws ParserException;

    /**
     * Visit methode vanuit een {@link UnaireOperatorNode}.
     * @param visited de node die werd aangeroepen en waarvoor deze methode wordt aangeroepen.
     * @throws ParserException indien er fouten optreden bij de verwerking.
     */
    void visit(UnaireOperatorNode visited) throws ParserException;

    /**
     * Visit methode vanuit een {@link StringNode}.
     * @param visited de node die werd aangeroepen en waarvoor deze methode wordt aangeroepen.
     * @throws ParserException indien er fouten optreden bij de verwerking.
     */
    void visit(StringNode visited) throws ParserException;

    /**
     * Visit methode vanuit een {@link IntegerNode}.
     * @param visited de node die werd aangeroepen en waarvoor deze methode wordt aangeroepen.
     * @throws ParserException indien er fouten optreden bij de verwerking.
     */
    void visit(IntegerNode visited) throws ParserException;

    /**
     * Visit methode vanuit een {@link FunctieNode}.
     * @param visited de node die werd aangeroepen en waarvoor deze methode wordt aangeroepen.
     * @throws ParserException indien er fouten optreden bij de verwerking.
     */
    void visit(FunctieNode visited) throws ParserException;

    /**
     * Visit methode vanuit een {@link IdentifierNode}.
     * @param visited de node die werd aangeroepen en waarvoor deze methode wordt aangeroepen.
     * @throws ParserException indien er fouten optreden bij de verwerking.
     */
    void visit(IdentifierNode visited) throws ParserException;

    /**
     * Visit methode vanuit een {@link ArgumentSeparatorNode}.
     * @param visited de node die werd aangeroepen en waarvoor deze methode wordt aangeroepen.
     * @throws ParserException indien er fouten optreden bij de verwerking.
     */
    void visit(ArgumentSeparatorNode visited) throws ParserException;

}
