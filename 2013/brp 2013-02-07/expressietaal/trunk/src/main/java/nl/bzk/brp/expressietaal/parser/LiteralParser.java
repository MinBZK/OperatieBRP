/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import nl.bzk.brp.expressietaal.lexical.tokens.Token;
import nl.bzk.brp.expressietaal.lexical.tokens.TokenStack;
import nl.bzk.brp.expressietaal.lexical.tokens.TokenSubtype;
import nl.bzk.brp.expressietaal.lexical.tokens.TokenType;
import nl.bzk.brp.expressietaal.parser.syntaxtree.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.DateLiteralExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.NullValue;
import nl.bzk.brp.expressietaal.parser.syntaxtree.NumberLiteralExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.StringLiteralExpressie;
import nl.bzk.brp.expressietaal.symbols.Keywords;

/**
 * Parserfunctionaliteit voor literals (constante waarden).
 */
public final class LiteralParser {

    /**
     * Constructor.
     */
    private LiteralParser() {
    }

    /**
     * Parse een expressie met een vaste waarde haakjes volgens afleidingsregel:
     * <literal> ::= <booleanLiteral> | <numericLiteral> | <stringLiteral> | <dateLiteral>.
     *
     * @param stack Stack met tokens van de string die vertaald wordt.
     * @return Parserresultaat.
     */
    public static ParserResultaat parseLiteral(final TokenStack stack) {
        ParserResultaat pr = parseNumberLiteral(stack);
        if (pr.getExpressie() == null && pr.getFout() == null) {
            pr = parseStringLiteral(stack);
            if (pr.getExpressie() == null && pr.getFout() == null) {
                pr = parseBooleanLiteral(stack);
                if (pr.getExpressie() == null && pr.getFout() == null) {
                    pr = parseDateLiteral(stack);
                    if (pr.getExpressie() == null && pr.getFout() == null) {
                        pr = parseNullValue(stack);
                    }
                }
            }
        }
        return pr;
    }

    /**
     * Parse een getal (constante).
     *
     * @param stack Stack met tokens van de string die vertaald wordt.
     * @return Parserresultaat.
     */
    public static ParserResultaat parseNumberLiteral(final TokenStack stack) {
        Expressie exp = null;
        if (stack.matchNextToken(TokenType.NUMBER_LITERAL, false)) {
            Token ct = stack.shift();
            exp = new NumberLiteralExpressie(ct.getValueAsInteger());
        }
        return new ParserResultaat(exp);
    }

    /**
     * Parse een stringwaarde (constante).
     *
     * @param stack Stack met tokens van de string die vertaald wordt.
     * @return Parserresultaat.
     */
    public static ParserResultaat parseStringLiteral(final TokenStack stack) {
        Expressie exp = null;
        if (stack.matchNextToken(TokenType.STRING_LITERAL, false)) {
            Token ct = stack.shift();
            exp = new StringLiteralExpressie(ct.getValueAsString());
        }
        return new ParserResultaat(exp);
    }

    /**
     * Parse een boolean waarde (constante).
     *
     * @param stack Stack met tokens van de string die vertaald wordt.
     * @return Parserresultaat.
     */
    public static ParserResultaat parseBooleanLiteral(final TokenStack stack) {
        Expressie exp = null;
        if (stack.matchKeyword(Keywords.TRUE, true)) {
            exp = new BooleanLiteralExpressie(true);
        } else if (stack.matchKeyword(Keywords.FALSE, true)) {
            exp = new BooleanLiteralExpressie(false);
        }
        return new ParserResultaat(exp);
    }

    /**
     * Parse de null-waarde (ONBEKEND).
     *
     * @param stack Stack met tokens van de string die vertaald wordt.
     * @return Parserresultaat.
     */
    public static ParserResultaat parseNullValue(final TokenStack stack) {
        Expressie exp = null;
        if (stack.matchKeyword(Keywords.ONBEKEND, true)) {
            exp = new NullValue();
        }
        return new ParserResultaat(exp);
    }

    /**
     * Parse een datumwaarde (constante) volgens afleidingsregel:
     * <dateLiteral> ::= { <numericLiteral> - <numericLiteral> [ - <numericLiteral> ] }.
     *
     * @param stack Stack met tokens van de string die vertaald wordt.
     * @return Parserresultaat.
     */
    public static ParserResultaat parseDateLiteral(final TokenStack stack) {
        Expressie exp = null;
        ParserFout fout = null;
        int year = 0, month = DateLiteralExpressie.MAAND_ONBEKEND, day = DateLiteralExpressie.DAG_ONBEKEND;

        if (stack.matchNextToken(TokenType.BRACKET, TokenSubtype.DATE_MARKER, true)) {
            if (fout == null && stack.matchNextToken(TokenType.NUMBER_LITERAL, false)) {
                year = parseJaartal(stack);
                if (year < 0) {
                    fout = new ParserFout(ParserFoutCode.FOUT_IN_DATUM_JAARTAL_ONTBREEKT, stack.currentToken());
                }
            } else {
                fout = new ParserFout(ParserFoutCode.FOUT_IN_DATUM_JAARTAL_ONTBREEKT, stack.currentToken());
            }

            if (fout == null && stack.matchNextToken(TokenType.OPERATOR, TokenSubtype.MINUS, true)) {
                month = parseMaandnummer(stack);
                if (month < 0) {
                    fout = new ParserFout(ParserFoutCode.FOUT_IN_DATUM_MAAND_ONTBREEKT, stack.currentToken());
                } else if (month < ParserUtils.EERSTE_MAAND || month > ParserUtils.LAATSTE_MAAND) {
                    fout = new ParserFout(ParserFoutCode.FOUT_IN_DATUM_INCORRECTE_MAAND, stack.currentToken());
                }

                if (fout == null && stack.matchNextToken(TokenType.OPERATOR, TokenSubtype.MINUS, true)) {
                    day = parseDagnummer(stack);
                    if (day < 0) {
                        fout = new ParserFout(ParserFoutCode.FOUT_IN_DATUM_DAG_ONTBREEKT, stack.currentToken());
                    } else if (day < ParserUtils.EERSTE_DAG || day > ParserUtils.LAATSTE_DAG) {
                        fout = new ParserFout(ParserFoutCode.FOUT_IN_DATUM_INCORRECT_DAGNUMMER, stack.currentToken());
                    }
                }
            }

            if (fout == null) {
                if (stack.matchNextToken(TokenType.BRACKET, TokenSubtype.DATE_MARKER, true)) {
                    exp = new DateLiteralExpressie(year, month, day);
                } else {
                    fout = new ParserFout(ParserFoutCode.FOUT_IN_DATUM_AFSLUITINGSTEKEN_ONTBREEKT,
                            stack.currentToken());
                }
            }
        }
        return new ParserResultaat(exp, fout);
    }

    /**
     * Parse een jaartal.
     *
     * @param stack Stack met tokens van de string die vertaald wordt.
     * @return Jaartal of -1, indien fout.
     */
    public static int parseJaartal(final TokenStack stack) {
        int result = -1;
        if (stack.matchNextToken(TokenType.NUMBER_LITERAL, false)) {
            Token ct = stack.shift();
            result = ct.getValueAsInteger();
        }
        return result;
    }

    /**
     * Parse een maand (nummer of naam).
     *
     * @param stack Stack met tokens van de string die vertaald wordt.
     * @return Maandnummer of -1, indien fout.
     */
    public static int parseMaandnummer(final TokenStack stack) {
        int result = -1;
        if (stack.matchNextToken(TokenType.NUMBER_LITERAL, false)) {
            Token ct = stack.shift();
            result = ct.getValueAsInteger();
        } else if (stack.matchNextToken(TokenType.KEYWORD, false)) {
            Token ct = stack.shift();
            result = ParserUtils.maandnaamNaarMaandnummer(ct.getValueAsKeyword());
        }
        return result;
    }

    /**
     * Parse een dagnummer.
     *
     * @param stack Stack met tokens van de string die vertaald wordt.
     * @return Dagnummer of -1, indien fout.
     */
    public static int parseDagnummer(final TokenStack stack) {
        int result = -1;
        if (stack.matchNextToken(TokenType.NUMBER_LITERAL, false)) {
            Token ct = stack.shift();
            result = ct.getValueAsInteger();
        }
        return result;
    }
}
