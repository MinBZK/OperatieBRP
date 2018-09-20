/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.processer;

import java.util.Calendar;

import nl.bzk.brp.toegangsbewaking.parser.ParseTree;
import nl.bzk.brp.toegangsbewaking.parser.ParserException;
import nl.bzk.brp.toegangsbewaking.parser.node.FunctieNode;
import nl.bzk.brp.toegangsbewaking.parser.node.IdentifierNode;
import nl.bzk.brp.toegangsbewaking.parser.node.IntegerNode;
import nl.bzk.brp.toegangsbewaking.parser.node.OperandNode;
import nl.bzk.brp.toegangsbewaking.parser.node.StringNode;


/**
 * JPQL specifieke processor die een {@link ParseTree} omzet in een JPQL Filter string.
 *
 * @see #process(ParseTree)
 */
public class JPQLFilterProcesser extends AbstractProcesser<String> {

    private StringBuilder sb;

    /**
     * Standaard constructor die de class initialiseert.
     */
    public JPQLFilterProcesser() {
    }

    /**
     * Verwerkt de opgegeven {@link ParseTree} en genereert op basis daarvan een JPQL where-clause snippet.
     *
     * @param parseTree de parse tree die verwerkt dient te worden.
     * @return een JPQL where-clause snippet.
     * @throws ParserException indien er fouten optreden in de verwerking.
     */
    @Override
    public String process(final ParseTree parseTree) throws ParserException {
        sb = new StringBuilder();
        process(parseTree.getRootNode());
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verwerkLogischeEnOperator(final OperandNode argument1, final OperandNode argument2)
            throws ParserException
    {
        process(argument1);
        sb.append("AND ");
        process(argument2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verwerkLogischeOfOperator(final OperandNode argument1, final OperandNode argument2)
            throws ParserException
    {
        process(argument1);
        sb.append("OR ");
        process(argument2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verwerkLogischeNietOperator(final OperandNode argument) throws ParserException {
        sb.append("NOT ");
        process(argument);
    }

    /**
     * Verwerkt een vergelijkingsoperator.
     *
     * @param operator de operator in de JPQL snippet.
     * @param argument1 argument voor de operator.
     * @param argument2 argument voor de operator.
     * @throws ParserException indien er een verkeerd argument wordt gedetecteerd.
     */
    private void verwerkVergelijkingsOperator(final String operator, final OperandNode argument1,
            final OperandNode argument2) throws ParserException
    {
        if (argument2 instanceof IntegerNode) {
            process(argument1);
            sb.append(operator);
            process(argument2);
        } else {
            throw new ParserException("Vergelijkings operator verwacht een integer node als tweede argument");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verwerkGroterDanOperator(final OperandNode argument1, final OperandNode argument2)
            throws ParserException
    {
        verwerkVergelijkingsOperator("> ", argument1, argument2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verwerkGroterDanOfGelijkAanOperator(final OperandNode argument1, final OperandNode argument2)
            throws ParserException
    {
        verwerkVergelijkingsOperator(">= ", argument1, argument2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verwerkKleinerDanOperator(final OperandNode argument1, final OperandNode argument2)
            throws ParserException
    {
        verwerkVergelijkingsOperator("< ", argument1, argument2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verwerkKleinerDanOfGelijkAanOperator(final OperandNode argument1, final OperandNode argument2)
            throws ParserException
    {
        verwerkVergelijkingsOperator("<= ", argument1, argument2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verwerkGelijkOperator(final OperandNode argument1, final OperandNode argument2)
            throws ParserException
    {
        if (argument1 instanceof IdentifierNode) {
            if (argument2 instanceof StringNode) {
                sb.append("LOWER(");
                process(argument1);
                sb.append(") = LOWER(");
                process(argument2);
                sb.append(") ");
            } else {
                process(argument1);
                sb.append("= ");
                process(argument2);
            }
        } else if (argument1 instanceof FunctieNode) {
            if (argument2 instanceof StringNode) {
                sb.append("LOWER(");
                process(argument1);
                sb.append(") = LOWER(");
                process(argument2);
                sb.append(") ");
            } else {
                process(argument1);
                sb.append("= ");
                process(argument2);
            }
        } else {
            throw new ParserException("'Gelijk' operator verwacht een identifier of functie node als eerste argument");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verwerkGelijkCaseSensitiveOperator(final OperandNode argument1, final OperandNode argument2)
            throws ParserException
    {
        if (argument1 instanceof IdentifierNode) {
            if (argument2 instanceof StringNode) {
                process(argument1);
                sb.append("= ");
                process(argument2);
            } else {
                throw new ParserException("'Gelijk CS' operator verwacht een string node als tweede argument");
            }
        } else {
            throw new ParserException("'Gelijk' operator verwacht een identifier node als eerste argument");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verwerkFunctie(final FunctieNode functieNode) throws ParserException {
        if (functieNode.getFunctie().equalsIgnoreCase("VANDAAG")) {
            Calendar today = Calendar.getInstance();
            sb.append(String.format("%1$tY%1$tm%1$td ", today));
        } else if (functieNode.getFunctie().equalsIgnoreCase("MIN")) {
            sb.append("(");
            process(functieNode.getArgumenten().getNode(0));
            sb.append("- ");
            process(functieNode.getArgumenten().getNode(1));
            sb.append(") ");
        } else if (functieNode.getFunctie().equalsIgnoreCase("JAAR")) {
            sb.append("(");
            process(functieNode.getArgumenten());
            sb.append(" - ");
            sb.append("MOD(");
            process(functieNode.getArgumenten());
            sb.append(", 10000)");
            sb.append(")/10000 ");
        } else if (functieNode.getFunctie().equalsIgnoreCase("PROVINCIE")) {
            sb.append("(select gp.provNaam from Gemprov gp where gp.gem.id = ");
            process(functieNode.getArgumenten().getNode(0));
            sb.append(") ");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verwerkIdentifier(final String identifier) {
        String snippet;
        if (identifier.equalsIgnoreCase("Persoon.Adres.Gemeente.ID")) {
            snippet = "(SELECT a.gem.id FROM Persadres a WHERE a.pers = p AND a.functieadres.code = 'V') ";
        } else if (identifier.equalsIgnoreCase("Persoon.Adres.Woonplaats.Naam")) {
            snippet = "(SELECT a.plaats.naam FROM Persadres a WHERE a.pers = p AND a.functieadres.code = 'V') ";
        } else {
            snippet = identifier.replaceAll("Persoon", "p");
        }
        sb.append(snippet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verwerkTekst(final String text) {
        String waarde = text.replace("'", "''");
        sb.append("'").append(waarde).append("' ");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verwerkGetal(final Integer getal) {
        sb.append(getal).append(" ");
    }

}
