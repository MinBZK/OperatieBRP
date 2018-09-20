/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.tokenizer;

import java.util.List;

import nl.bzk.brp.toegangsbewaking.parser.ParserException;


/**
 * Specifieke tokenizer voor BRP expressies.
 */
public class DefaultTokenizer extends AbstractTokenizer {

    /**
     *
     * @param lines
     */
    public DefaultTokenizer(final List<String> lines) {
        super(lines);
    }

    protected void fetchSingleLineComment() throws ParserException {
        char oldChar = getCurrentChar();
        int oldCharNr = getCharNr();

        nextChar();
        if (!isEOL() && (getCurrentChar() == '/')) {
            nextChar();
            if (!isIgnoreComment()) {
                addToken(TokenType.COMMENT, getLineNr(), oldCharNr, getLine().substring(getCharNr()),
                        CommentType.DOUBLE_SLASH);
            }
            nextLine();
        } else {
            throwErrorIllegalCharacter(oldChar, getLineNr(), oldCharNr);
        }
    }

    protected void fetchBlockComment() throws ParserException {
        int oldCharNr = getCharNr();
        nextChar();

        super.fetchBlockComment(oldCharNr, CommentType.CODE_NEW_BLOCK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        // Start with an empty token list
        getTokens().clear();

        // Scan every line of the script
        try {
            setLineNr(-1);
            nextLine();

            while (getLineNr() < getLines().size()) {
                // Scan every character of the line
                while (!isEOL()) {
                    // CHECKSTYLE:OFF
                    switch (getCurrentChar()) {
                        case 9: // Tab
                        case 32: // Space
                            nextChar();
                            break;
                        case 33: // ! niet
                        case 35: // # leeg
                        case 38: // & en
                            fetchSingleCharSymbol();
                            break;
                        case 40: // (
                        case 41: // )
                            fetchGroeperingSymbol();
                            break;
                        case 44: // ,
                        case 124: // | of
                            fetchSingleCharSymbol();
                            break;
                        case 37: // % bevat, %- start
                            fetchSingleOrDoubleCharSymbol('-');
                            break;
                        case 39: // '
                            fetchString('\'');
                            break;
                        case 47: // / Line Comment
                            fetchSingleLineComment();
                            break;
                        case 48:
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                            fetchIntegerOrFloat();
                            break;
                        case 60: // < kleiner, <=
                        case 61: // = gelijk, == gelijk_cs
                        case 62: // > groter, >=
                            fetchSingleOrDoubleCharSymbol('=');
                            break;
                        case 65:
                        case 66:
                        case 67:
                        case 68:
                        case 69:
                        case 70:
                        case 71:
                        case 72:
                        case 73:
                        case 74:
                        case 75:
                        case 76:
                        case 77:
                        case 78:
                        case 79:
                        case 80:
                        case 81:
                        case 82:
                        case 83:
                        case 84:
                        case 85:
                        case 86:
                        case 87:
                        case 88:
                        case 89:
                        case 90:
                            fetchIdentifier();
                            break;
                        case 97:
                        case 98:
                        case 99:
                        case 100:
                        case 101:
                        case 102:
                        case 103:
                        case 104:
                        case 105:
                        case 106:
                        case 107:
                        case 108:
                        case 109:
                        case 110:
                        case 111:
                        case 112:
                        case 113:
                        case 114:
                        case 115:
                        case 116:
                        case 117:
                        case 118:
                        case 119:
                        case 120:
                        case 121:
                        case 122:
                            fetchIdentifier();
                            break;
                        case 123:
                            fetchBlockComment();
                            break;
                        default:
                            throwErrorIllegalCharacter(getCurrentChar());
                    }
                    // CHECKSTYLE:ON
                }

                // Next line
                nextLine();
            }
        } catch (ParserException e) {
            handleError(e);
        }
    }

    @Override
    protected boolean isIdentifierChar(final char aChar) {
        return Character.isLetterOrDigit(aChar) || aChar == '_' || aChar == '.';
    }

}
