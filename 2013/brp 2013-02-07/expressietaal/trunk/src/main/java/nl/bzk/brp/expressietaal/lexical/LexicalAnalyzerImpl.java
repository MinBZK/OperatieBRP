/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.lexical;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.expressietaal.lexical.tokens.AttributeToken;
import nl.bzk.brp.expressietaal.lexical.tokens.EndOfLineToken;
import nl.bzk.brp.expressietaal.lexical.tokens.KeywordToken;
import nl.bzk.brp.expressietaal.lexical.tokens.NumberToken;
import nl.bzk.brp.expressietaal.lexical.tokens.StringToken;
import nl.bzk.brp.expressietaal.lexical.tokens.Token;
import nl.bzk.brp.expressietaal.lexical.tokens.TokenStack;
import nl.bzk.brp.expressietaal.lexical.tokens.TokenStackImpl;
import nl.bzk.brp.expressietaal.lexical.tokens.TokenSubtype;
import nl.bzk.brp.expressietaal.lexical.tokens.TokenType;
import nl.bzk.brp.expressietaal.parser.ParserFoutCode;
import nl.bzk.brp.expressietaal.symbols.Attributes;
import nl.bzk.brp.expressietaal.symbols.BmrSymbolTableFactory;
import nl.bzk.brp.expressietaal.symbols.Characters;
import nl.bzk.brp.expressietaal.symbols.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.symbols.Keywords;
import nl.bzk.brp.expressietaal.symbols.SymbolTable;

/**
 * Implementeert de interface LexicalAnalyzer. De taak van een lexical analyzer is om een string om te zetten in een
 * lijst van tokens.
 */
public class LexicalAnalyzerImpl implements LexicalAnalyzer {

    private List<Token> tokens;
    private SymbolTable symbolTable;
    private ParserFoutCode fout;
    private int foutPositie;
    private static final Map<Character, TokenSubtype> SINGLE_CHARACTER_OPERATORS;
    private static final Map<String, TokenSubtype> DOUBLE_CHARACTER_OPERATORS;
    private static final Map<Character, TokenSubtype> HAAKJES;

    static {
        SINGLE_CHARACTER_OPERATORS = new Hashtable<Character, TokenSubtype>();
        SINGLE_CHARACTER_OPERATORS.put('<', TokenSubtype.LESS);
        SINGLE_CHARACTER_OPERATORS.put('>', TokenSubtype.GREATER);
        SINGLE_CHARACTER_OPERATORS.put('=', TokenSubtype.EQUAL);
        SINGLE_CHARACTER_OPERATORS.put('+', TokenSubtype.PLUS);
        SINGLE_CHARACTER_OPERATORS.put('-', TokenSubtype.MINUS);
        SINGLE_CHARACTER_OPERATORS.put('*', TokenSubtype.MULTIPLY);
        SINGLE_CHARACTER_OPERATORS.put('/', TokenSubtype.DIVIDE);
        DOUBLE_CHARACTER_OPERATORS = new Hashtable<String, TokenSubtype>();
        DOUBLE_CHARACTER_OPERATORS.put("<=", TokenSubtype.LESS_OR_EQUAL);
        DOUBLE_CHARACTER_OPERATORS.put("<>", TokenSubtype.NOT_EQUAL);
        DOUBLE_CHARACTER_OPERATORS.put(">=", TokenSubtype.GREATER_OR_EQUAL);
        HAAKJES = new Hashtable<Character, TokenSubtype>();
        HAAKJES.put(Characters.HAAKJE_START, TokenSubtype.LEFT_BRACKET);
        HAAKJES.put(Characters.HAAKJE_EIND, TokenSubtype.RIGHT_BRACKET);
        HAAKJES.put(Characters.DATUM_START, TokenSubtype.DATE_MARKER);
        HAAKJES.put(Characters.DATUM_EIND, TokenSubtype.DATE_MARKER);
        HAAKJES.put(Characters.LIJST_START, TokenSubtype.LIST_START);
        HAAKJES.put(Characters.LIJST_EIND, TokenSubtype.LIST_END);
        HAAKJES.put(Characters.INDEX_START, TokenSubtype.INDEX_START);
        HAAKJES.put(Characters.INDEX_EIND, TokenSubtype.INDEX_EIND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final TokenStack tokenize(final String source) {
        tokens = new ArrayList<Token>();
        symbolTable = BmrSymbolTableFactory.createSymbolTable();
        fout = null;
        foutPositie = 0;

        int position = 0;

        if (source != null) {
            while (position < source.length() && fout == null) {
                position = slaWitruimteOver(source, position);
                if (position < source.length()) {
                    position = verwerkVolgendToken(source, position);
                }
            }
        } else {
            zetFout(ParserFoutCode.EXPRESSIE_VERWACHT, 0);
        }

        if (fout != null) {
            return new TokenStackImpl(fout, foutPositie);
        } else {
            tokens.add(new EndOfLineToken(position));
            return new TokenStackImpl(tokens);
        }
    }

    /**
     * Zet de tijdens analyse gevonden fout.
     *
     * @param gevondenFout        Gevonden fout.
     * @param positieGevondenFout Positie van de fout.
     */
    private void zetFout(final ParserFoutCode gevondenFout, final int positieGevondenFout) {
        fout = gevondenFout;
        foutPositie = positieGevondenFout;
    }

    /**
     * Verwerkt het volgende karakter van de invoer aangeduid met start. Voegt de gevonden tokens toe aan de lijst.
     *
     * @param source De invoer.
     * @param start  Positie waar het lezen in de invoer begint.
     * @return De positie voorbij het volgende token of -1 indien een fout is opgetreden.
     */
    private int verwerkVolgendToken(final String source, final int start) {
        int position = start;
        char ch = source.charAt(position);

        if (Characters.isHaakje(ch)) {
            position = matchHaakje(ch, position);
        } else if (Characters.isCijfer(ch)) {
            position = matchGetal(source, position);
        } else if (Characters.isStarttekenIdentifier(ch)) {
            position = matchAttribuutOfKeyword(source, position);
        } else if (Characters.isStarttekenOperator(ch)) {
            position = matchOperator(source, position);
        } else if (Characters.isScheidingsteken(ch)) {
            position = marchScheidingsteken(ch, position);
        } else if (ch == Characters.OBJECT_QUALIFIER) {
            position = matchQualifier(ch, position);
        } else if (ch == Characters.STRING_DELIMITER) {
            position = matchStringLiteral(source, position);
        } else {
            zetFout(ParserFoutCode.INCORRECT_TEKEN, position);
        }
        return position;
    }

    /**
     * Slaat de witruimte in de invoer over.
     *
     * @param source De invoer.
     * @param start  Positie vanwaar het lezen start.
     * @return Positie voorbij het laatst gevonden witruimte-karakter.
     */
    private int slaWitruimteOver(final CharSequence source, final int start) {
        int position = start;
        while (position < source.length() && Characters.isWitruimte(source.charAt(position))) {
            position++;
        }
        return position;
    }

    /**
     * Controleert of het volgende token een haakje is.
     *
     * @param ch    Volgende karakter op de invoer.
     * @param start Positie van het volgende karakter.
     * @return Positie voorbij het haakje, indien aangetroffen.
     */
    private int matchHaakje(final char ch, final int start) {
        TokenSubtype subtype = HAAKJES.get(ch);
        if (subtype == null) {
            subtype = TokenSubtype.NONE;
        }
        tokens.add(new StringToken(TokenType.BRACKET, subtype, start, ch));
        return start + 1;
    }

    /**
     * Controleert of het volgende token een scheidingsteken is.
     *
     * @param ch    Volgende karakter op de invoer.
     * @param start Positie van het volgende karakter.
     * @return Positie voorbij het scheidingsteken, indien aangetroffen.
     */
    private int marchScheidingsteken(final char ch, final int start) {
        tokens.add(new StringToken(TokenType.SEPARATOR, start, ch));
        return start + 1;
    }

    /**
     * Controleert of het volgende token een scheidingsteken voor attributen is.
     *
     * @param ch    Volgende karakter op de invoer.
     * @param start Positie van het volgende karakter.
     * @return Positie voorbij het scheidingsteken, indien aangetroffen.
     */
    private int matchQualifier(final char ch, final int start) {
        tokens.add(new StringToken(TokenType.QUALIFIER, start, ch));
        return start + 1;
    }

    /**
     * Controleert of het volgende token een getal is.
     *
     * @param source De invoer.
     * @param start  Positie van het volgende karakter.
     * @return Positie voorbij het getal, indien aangetroffen.
     */
    private int matchGetal(final String source, final int start) {
        int position = start;
        while (position < source.length() && Characters.isCijfer(source.charAt(position))) {
            position++;
        }
        if (position < source.length() && Characters.isLetter(source.charAt(position))) {
            zetFout(ParserFoutCode.INCORRECT_GETAL, position);
            position = -1;
        } else {
            String number = source.substring(start, position);
            tokens.add(new NumberToken(start, Integer.valueOf(number)));
        }
        return position;
    }

    /**
     * Controleert of het volgende token een attribuut of keyword is.
     *
     * @param source De invoer.
     * @param start  Positie van het volgende karakter.
     * @return Positie voorbij de identifier, indien aangetroffen.
     */
    private int matchAttribuutOfKeyword(final String source, final int start) {
        int position = start;
        int attributeStart = start;
        while (position < source.length()
                && (Characters.isIdentifierTeken(source.charAt(position))))
        {
            position++;
        }
        String object = source.substring(start, position);
        String fullIdentifier;
        String attribute;

        if (position < source.length() && Characters.isAttribuutScheidingsteken(source.charAt(position))) {
            position++;
            attributeStart = position;

            while (position < source.length()
                    && (Characters.isIdentifierTeken(source.charAt(position))
                    || Characters.isAttribuutScheidingsteken(source.charAt(position))))
            {
                position++;
            }

            fullIdentifier = source.substring(start, position);
            attribute = source.substring(attributeStart, position);
        } else {
            fullIdentifier = object;
            attribute = "";
        }

        Keywords k = DefaultKeywordMapping.findKeyword(fullIdentifier);
        Attributes a = symbolTable.lookupSymbolWithWildcard(fullIdentifier);
        if (k != null) {
            tokens.add(new KeywordToken(start, k));
        } else if (a != null) {
            tokens.add(new AttributeToken(start, fullIdentifier));
        } else {
            a = symbolTable.lookupSymbolWithWildcard(attribute);
            if (a != null) {
                tokens.add(new StringToken(TokenType.IDENTIFIER, start, object));
                tokens.add(new StringToken(TokenType.QUALIFIER, attributeStart - 1, Characters.OBJECT_QUALIFIER));
                tokens.add(new AttributeToken(attributeStart, attribute));
            } else {
                tokens.add(new StringToken(TokenType.IDENTIFIER, start, fullIdentifier));
            }
        }

        return position;
    }

    /**
     * Controleert of het volgende token een operator is.
     *
     * @param source De invoer.
     * @param start  Positie van het volgende karakter.
     * @return Positie voorbij de operator, indien aangetroffen.
     */
    private int matchOperator(final CharSequence source, final int start) {
        int position = start;
        char first = source.charAt(position);
        String operator = String.valueOf(first);
        position++;
        TokenSubtype subtype = TokenSubtype.NONE;

        if (position < source.length()) {
            char second = source.charAt(position);

            subtype = getDoubleCharacterOperator(first, second);

            if (subtype != TokenSubtype.NONE) {
                operator += second;
                position++;
            }
        }

        if (subtype == TokenSubtype.NONE) {
            subtype = getSingleCharacterOperator(first);
        }

        tokens.add(new StringToken(TokenType.OPERATOR, subtype, start, operator));
        return position;
    }

    /**
     * Bepaalt het TokenSubtype dat overeenstemt met het enkelvoudige karakter.
     *
     * @param ch Het karakter.
     * @return TokenSubtype van het de operator, indien correct; anders TokenSubtype.NONE.
     */
    private TokenSubtype getSingleCharacterOperator(final char ch) {
        if (SINGLE_CHARACTER_OPERATORS.containsKey(ch)) {
            return SINGLE_CHARACTER_OPERATORS.get(ch);
        } else {
            return TokenSubtype.NONE;
        }
    }

    /**
     * Bepaalt het TokenSubtype dat overeenstemt met de operator die bestaat uit de twee karakters.
     *
     * @param ch1 Het eerste karakter.
     * @param ch2 Het tweede karakter.
     * @return TokenSubtype van het de operator, indien correct; anders TokenSubtype.NONE.
     */
    private TokenSubtype getDoubleCharacterOperator(final char ch1, final char ch2) {
        String op = String.valueOf(ch1) + String.valueOf(ch2);
        if (DOUBLE_CHARACTER_OPERATORS.containsKey(op)) {
            return DOUBLE_CHARACTER_OPERATORS.get(op);
        } else {
            return TokenSubtype.NONE;
        }
    }

    /**
     * Controleert of het volgende token een vaste stringwaarde is.
     *
     * @param source De invoer.
     * @param start  Positie van het volgende karakter.
     * @return Positie voorbij de vaste stringwaarde, indien aangetroffen.
     */
    private int matchStringLiteral(final String source, final int start) {
        int position = start;
        position++;

        while (position < source.length() && source.charAt(position) != Characters.STRING_DELIMITER) {
            position++;
        }

        if (position < source.length()) {
            String stringLiteral = source.substring(start + 1, position);
            tokens.add(new StringToken(TokenType.STRING_LITERAL, start, stringLiteral));
            position++;
            return position;
        } else {
            zetFout(ParserFoutCode.AANHALINGSTEKENS_ONTBREKEN, position);
            return -1;
        }
    }
}
