/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.toegangsbewaking.parser.ParseTree;
import nl.bzk.brp.toegangsbewaking.parser.Parser;
import nl.bzk.brp.toegangsbewaking.parser.ParserException;
import nl.bzk.brp.toegangsbewaking.parser.grammar.Grammar;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.AbstractTokenizer;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.Token;


/**
 * Wrapper around a tekst, a tokenizer instance and a parser instance.
 */
public abstract class AbstractScript {

    private final List<String> lines;
    private final List<Token>  tokens;
    private final Grammar      grammar;
    private ParseTree          parseTree;

    /**
     * Constructor die direct de regels zet die moeten worden geparsed en ook de grammar waarmee dit moet gebeuren.
     *
     * @param lines de regels die moeten worden geparsed.
     * @param grammar de grammar conform welke de regels zijn opgesteld.
     */
    public AbstractScript(final List<String> lines, final Grammar grammar) {
        this.lines = lines;
        this.grammar = grammar;
        tokens = new ArrayList<Token>();
        parseTree = null;
    }

    protected abstract Parser createParser(final Grammar usingGrammar);

    protected abstract AbstractTokenizer createTokenizer(final List<String> lineList);

    /**
     * Creeert de parser voor de in het script vastgelegde grammar en parseert daarna de tokens in het script.
     */
    public void parse() {
        tokenize();

        final Parser parser = createParser(grammar);
        try {
            parseTree = parser.bouwParseTree(tokens);
        } catch (ParserException e) {
            handleError(e);
        }
    }

    public List<String> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public List<Token> getTokens() {
        return Collections.unmodifiableList(tokens);
    }

    public ParseTree getParseTree() {
        return parseTree;
    }

    /**
     * Creeert de tokenizer voor de regels van dit script en tokenized deze. De uit de tokenizer komende tokens
     * worden aan de lijst van tokens in dit script toegevoegd.
     */
    protected void tokenize() {
        final AbstractTokenizer tokenizer = createTokenizer(lines);
        try {
            tokenizer.execute();
            tokens.addAll(tokenizer.getTokens());
        } catch (ParserException p) {
            handleError(p);
        }
    }

    protected void handleError(final ParserException e) {
        System.out.println(String.format("ERROR at line %1$s, character %2$s: %3$s", e.getLijnNr(), e.getKarakterNr(),
                e.getMessage()));
    }
}
