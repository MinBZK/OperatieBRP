/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.processer;

import nl.bzk.brp.toegangsbewaking.parser.ParserException;
import nl.bzk.brp.toegangsbewaking.parser.node.ArgumentSeparatorNode;
import nl.bzk.brp.toegangsbewaking.parser.node.BinaireOperatorNode;
import nl.bzk.brp.toegangsbewaking.parser.node.FunctieNode;
import nl.bzk.brp.toegangsbewaking.parser.node.IdentifierNode;
import nl.bzk.brp.toegangsbewaking.parser.node.IntegerNode;
import nl.bzk.brp.toegangsbewaking.parser.node.ListNode;
import nl.bzk.brp.toegangsbewaking.parser.node.Node;
import nl.bzk.brp.toegangsbewaking.parser.node.NodeVisitor;
import nl.bzk.brp.toegangsbewaking.parser.node.OperandNode;
import nl.bzk.brp.toegangsbewaking.parser.node.StringNode;
import nl.bzk.brp.toegangsbewaking.parser.node.UnaireOperatorNode;


/**
 * Standaard processor die een {@link nl.bzk.brp.toegangsbewaking.parser.ParseTree} omzet in een implementatie specifek
 * filter object. Deze abstracte implementatie maakt gebruik van het Visitor pattern, waarvoor elke node een 'accept'
 * methode implementeert, die dan de node type specifieke visit methode in deze class aanroept.
 *
 * @see NodeVisitor
 */
public abstract class AbstractProcesser<T> implements Processer<T>, NodeVisitor {

    /**
     * Verwerkt een node in het eind resultaat van de processor.
     *
     * @param node de node die verwerkt dient te worden.
     * @throws ParserException indien er fouten optreden bij het verwerken van de node.
     */
    protected void process(final Node node) throws ParserException {
        if (node != null) {
            node.accept(this);
        }
    }

    /**
     * Verwerkt de logische 'EN' operator in het eindresultaat met de twee opgegeven argumenten.
     *
     * @param argument1 argument voor de operator.
     * @param argument2 argument voor de operator.
     * @throws ParserException indien er fouten optreden bij het verwerken van de operator.
     */
    protected abstract void verwerkLogischeEnOperator(final OperandNode argument1, final OperandNode argument2)
            throws ParserException;

    /**
     * Verwerkt de logische 'OF' operator in het eindresultaat met de twee opgegeven argumenten.
     *
     * @param argument1 argument voor de operator.
     * @param argument2 argument voor de operator.
     * @throws ParserException indien er fouten optreden bij het verwerken van de operator.
     */
    protected abstract void verwerkLogischeOfOperator(final OperandNode argument1, final OperandNode argument2)
            throws ParserException;

    /**
     * Verwerkt de logische 'NIET' operator in het eindresultaat met de twee opgegeven argumenten.
     *
     * @param argument argument voor de operator.
     * @throws ParserException indien er fouten optreden bij het verwerken van de operator.
     */
    protected abstract void verwerkLogischeNietOperator(final OperandNode argument) throws ParserException;

    /**
     * Verwerkt de (case insensitive) vergelijkings operator in het eindresultaat met de twee opgegeven argumenten.
     *
     * @param argument1 argument voor de operator.
     * @param argument2 argument voor de operator.
     * @throws ParserException indien er fouten optreden bij het verwerken van de operator.
     */
    protected abstract void verwerkGelijkOperator(final OperandNode argument1, final OperandNode argument2)
            throws ParserException;

    /**
     * Verwerkt de case sensitive vergelijkings operator in het eindresultaat met de twee opgegeven argumenten.
     *
     * @param argument1 argument voor de operator.
     * @param argument2 argument voor de operator.
     * @throws ParserException indien er fouten optreden bij het verwerken van de operator.
     */
    protected abstract void
            verwerkGelijkCaseSensitiveOperator(final OperandNode argument1, final OperandNode argument2)
                    throws ParserException;

    /**
     * Verwerkt de groter-dan operator in het eindresultaat met de twee opgegeven argumenten.
     *
     * @param argument1 argument voor de operator.
     * @param argument2 argument voor de operator.
     * @throws ParserException indien er fouten optreden bij het verwerken van de operator.
     */
    protected abstract void verwerkGroterDanOperator(final OperandNode argument1, final OperandNode argument2)
            throws ParserException;

    /**
     * Verwerkt de groter-dan-of-gelijk-aan operator in het eindresultaat met de twee opgegeven argumenten.
     *
     * @param argument1 argument voor de operator.
     * @param argument2 argument voor de operator.
     * @throws ParserException indien er fouten optreden bij het verwerken van de operator.
     */
    protected abstract void
            verwerkGroterDanOfGelijkAanOperator(final OperandNode argument1, final OperandNode argument2)
                    throws ParserException;

    /**
     * Verwerkt de kleiner-dan operator in het eindresultaat met de twee opgegeven argumenten.
     *
     * @param argument1 argument voor de operator.
     * @param argument2 argument voor de operator.
     * @throws ParserException indien er fouten optreden bij het verwerken van de operator.
     */
    protected abstract void verwerkKleinerDanOperator(final OperandNode argument1, final OperandNode argument2)
            throws ParserException;

    /**
     * Verwerkt de kleiner-dan-of-gelijk-aan operator in het eindresultaat met de twee opgegeven argumenten.
     *
     * @param argument1 argument voor de operator.
     * @param argument2 argument voor de operator.
     * @throws ParserException indien er fouten optreden bij het verwerken van de operator.
     */
    protected abstract void verwerkKleinerDanOfGelijkAanOperator(final OperandNode argument1,
            final OperandNode argument2) throws ParserException;

    /**
     * Verwerkt een functie.
     *
     * @param functie de functie die moet worden verwerkt.
     * @throws ParserException indien er fouten optreden bij de verwerking.
     */
    protected abstract void verwerkFunctie(final FunctieNode functie) throws ParserException;

    /**
     * Verwerkt een identifier in het eindresultaat.
     *
     * @param identifier de identifier die moet worden verwerkt.
     */
    protected abstract void verwerkIdentifier(final String identifier);

    /**
     * Verwerkt een stuk tekst in het eindresultaat.
     *
     * @param tekst de tekst die moet worden verwerkt.
     */
    protected abstract void verwerkTekst(final String tekst);

    /**
     * Verwerkt een getal in het eindresultaat.
     *
     * @param getal het getal dat moet worden verwerkt.
     */
    protected abstract void verwerkGetal(final Integer getal);

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final BinaireOperatorNode visited) throws ParserException {
        try {
            switch (visited.getOperatorType()) {
                case EN:
                    verwerkLogischeEnOperator(visited.getArgument1(), visited.getArgument2());
                    break;
                case OF:
                    verwerkLogischeOfOperator(visited.getArgument1(), visited.getArgument2());
                    break;
                case GELIJK:
                    verwerkGelijkOperator(visited.getArgument1(), visited.getArgument2());
                    break;
                case GELIJK_CS:
                    verwerkGelijkCaseSensitiveOperator(visited.getArgument1(), visited.getArgument2());
                    break;
                case GROTER:
                    verwerkGroterDanOperator(visited.getArgument1(), visited.getArgument2());
                    break;
                case GROTER_GELIJK:
                    verwerkGroterDanOfGelijkAanOperator(visited.getArgument1(), visited.getArgument2());
                    break;
                case KLEINER:
                    verwerkKleinerDanOperator(visited.getArgument1(), visited.getArgument2());
                case KLEINER_GELIJK:
                    verwerkKleinerDanOfGelijkAanOperator(visited.getArgument1(), visited.getArgument2());
                    break;
                case BEVAT:
                case LEEG:
                case START:
                case EINDE:
                default:
                    throw new ParserException(String.format(
                            "Binaire operator '%s' wordt niet ondersteund in processer", visited.getOperatorType()
                                    .getNaam()), visited.getToken());
            }
        } catch (final ParserException e) {
            if (!e.isLokatieGezet()) {
                e.zetFoutieveToken(visited.getToken());
            }
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ListNode visited) throws ParserException {
        for (int i = 0; i < visited.getNodeCount(); i++) {
            process(visited.getNode(i));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final UnaireOperatorNode visited) throws ParserException {
        verwerkLogischeNietOperator(visited.getArgument());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final StringNode visited) throws ParserException {
        verwerkTekst(visited.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final IntegerNode visited) throws ParserException {
        verwerkGetal(visited.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FunctieNode visited) throws ParserException {
        verwerkFunctie(visited);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final IdentifierNode visited) throws ParserException {
        verwerkIdentifier(visited.getIdentifier());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ArgumentSeparatorNode visited) throws ParserException {
        throw new ParserException("Argument afscheidings node wordt niet ondersteund.", visited.getToken());
    }

}
