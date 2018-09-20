/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.lexical.tokens;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import nl.bzk.brp.expressietaal.parser.ParserFoutCode;
import nl.bzk.brp.expressietaal.symbols.Keywords;


/**
 * Geordende collectie van tokens met een cursor, die het volgende te verwerken token aangeeft. TokenStack biedt
 * methoden om o.a. het bereiken van het einde van de collectie te testen, de cursor te verschuiven en controles uit
 * te voeren op het token dat wordt aangegeven door de cursor.
 */
public class TokenStackImpl implements TokenStack {

    private final List<Token> stack;
    private int cursor;
    private ParserFoutCode fout;
    private int foutPositie;

    /**
     * Constructor. Maak een nieuwe token stack gevuld met de meegegeven tokens.
     *
     * @param tokens Tokens die op de stack geplaatst worden.
     */
    public TokenStackImpl(final Collection<Token> tokens) {
        cursor = 0;
        stack = new LinkedList<Token>();
        stack.addAll(tokens);
        fout = null;
        foutPositie = 0;
    }

    /**
     * Constructor. Maak een nieuwe, lege token stack voor een gevonden fout in de lexicale analyse.
     *
     * @param fout        De gevonden fout.
     * @param foutPositie Positie van de fout.
     */
    public TokenStackImpl(final ParserFoutCode fout, final int foutPositie) {
        cursor = 0;
        stack = new LinkedList<Token>();
        this.fout = fout;
        this.foutPositie = foutPositie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean finished() {
        return (cursor >= stack.size());
    }

    @Override
    public boolean succes() {
        return (fout == null);
    }

    @Override
    public ParserFoutCode getFout() {
        return fout;
    }

    @Override
    public int getFoutPositie() {
        return foutPositie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int size() {
        return stack.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Token currentToken() {
        if (finished()) {
            return null;
        }
        return stack.get(cursor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Token shift() {
        if (finished()) {
            return null;
        }
        Token result = stack.get(cursor);
        cursor++;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean matchNextToken(final TokenType tokenType, final boolean shift) {
        Token ct = currentToken();
        boolean result = (ct != null && (ct.getTokenType().equals(tokenType)));
        if (result && shift) {
            shift();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean matchNextToken(final TokenType tokenType, final TokenSubtype tokenSubtype,
                                        final boolean shift)
    {
        Token ct = currentToken();
        boolean result = (ct != null && (ct.getTokenType().equals(tokenType)
                && ct.getTokenSubtype().equals(tokenSubtype)));
        if (result && shift) {
            shift();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean matchEqualityOperator(final boolean shift) {
        return matchNextToken(TokenType.OPERATOR, TokenSubtype.EQUAL, shift)
                || matchNextToken(TokenType.OPERATOR, TokenSubtype.NOT_EQUAL, shift);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean matchAdditiveOperator(final boolean shift) {
        return matchNextToken(TokenType.OPERATOR, TokenSubtype.PLUS, shift)
                || matchNextToken(TokenType.OPERATOR, TokenSubtype.MINUS, shift);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean matchMultiplicativeOperator(final boolean shift) {
        return matchNextToken(TokenType.OPERATOR, TokenSubtype.MULTIPLY, shift)
                || matchNextToken(TokenType.OPERATOR, TokenSubtype.DIVIDE, shift);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean matchRelationalOperator(final boolean shift) {
        boolean result = false;
        Token ct = currentToken();
        if (ct != null) {
            if (ct.getTokenType() == TokenType.OPERATOR) {
                result = ct.getTokenSubtype() == TokenSubtype.LESS
                        || ct.getTokenSubtype() == TokenSubtype.GREATER
                        || ct.getTokenSubtype() == TokenSubtype.LESS_OR_EQUAL
                        || ct.getTokenSubtype() == TokenSubtype.GREATER_OR_EQUAL;
            } else if (ct.getTokenType() == TokenType.KEYWORD) {
                result = ct.getValueAsKeyword() == Keywords.IN;
            }
        }
        if (result && shift) {
            shift();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean matchKeyword(final Keywords keyword, final boolean shift) {
        Token ct = currentToken();
        boolean result = (ct != null && ct.getTokenType().equals(TokenType.KEYWORD) && (ct.getValueAsKeyword()
                == keyword));
        if (result && shift) {
            shift();
        }
        return result;
    }

    @Override
    public final String toString() {
        return stack.toString();
    }
}
