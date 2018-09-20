/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.tokenizer;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.toegangsbewaking.parser.ParserException;


/**
 * Reads a string (script) and converts it into a list of tokens.
 */
public abstract class AbstractTokenizer {

    private final List<String> lines;
    private final List<Token>  tokens;
    private int                charNr;
    private int                lineNr;
    private int                maxCharNr;
    private String             line;
    private boolean            ignoreComment;

    /**
     * Standaard constructor die de regels die getokenized moeten worden zet.
     *
     * @param lines de regels die getokenized moeten worden.
     */
    public AbstractTokenizer(final List<String> lines) {
        this.lines = lines;
        this.tokens = new ArrayList<Token>();
        this.ignoreComment = true;
    }

    /**
     * Performs the actual tokenizing.
     */
    public abstract void execute() throws ParserException;

    /**
     * Calls the ErrorEvent or reraises the last script error.
     */
    protected void handleError(final ParserException e) {
        System.out.println(String.format("ERROR at line %1$s, character %2$s: %3$s", e.getLijnNr(), e.getKarakterNr(),
                e.getMessage()));
    }

    /**
     * Adds a new token to the list. Basic add.
     *
     * @param tKind type van de token.
     * @param atlineNr regel nummer waarop de token start.
     * @param atcharNr karakter nummer waarop de token start.
     * @return de toegevoegde token.
     */
    protected Token addToken(final TokenType tKind, final int atlineNr, final int atcharNr) {
        Token result = new Token(tKind, atlineNr, atcharNr);
        tokens.add(result);
        return result;
    }

    /**
     * Adds a new token to the list. Adds Text.
     *
     * @param tKind type van de token.
     * @param atlineNr regel nummer waarop de token start.
     * @param atcharNr karakter nummer waarop de token start.
     * @param text de tekst van de token.
     * @return de toegevoegde token.
     */
    protected Token addToken(final TokenType tKind, final int atlineNr, final int atcharNr, final String text) {
        Token result = addToken(tKind, atlineNr, atcharNr);
        result.setText(text);
        return result;
    }

    /**
     * Adds a new token to the list. Adds integer and string representation
     *
     * @param tKind type van de token.
     * @param atlineNr regel nummer waarop de token start.
     * @param atcharNr karakter nummer waarop de token start.
     * @param text de tekst van de token.
     * @return de toegevoegde token.
     */
    protected Token addToken(final TokenType tKind, final int atlineNr, final int atcharNr, final Integer number,
            final String text)
    {
        Token result = addToken(tKind, atlineNr, atcharNr);
        result.setText(text);
        result.setI(number);
        return result;
    }

    /**
     * Adds a new token to the list. Adds float and string representation
     *
     * @param tKind type van de token.
     * @param atlineNr regel nummer waarop de token start.
     * @param atcharNr karakter nummer waarop de token start.
     * @param text de tekst van de token.
     * @return de toegevoegde token.
     */
    protected Token addToken(final TokenType tKind, final int atlineNr, final int atcharNr, final Float number,
            final String text)
    {
        Token result = addToken(tKind, atlineNr, atcharNr);
        result.setText(text);
        result.setF(number);
        return result;
    }

    /**
     * Adds a new token to the list. Adds float and string representation
     *
     * @param tKind type van de token.
     * @param atlineNr regel nummer waarop de token start.
     * @param atcharNr karakter nummer waarop de token start.
     * @param comment het comment bij een token.
     * @param cKind het type van het comment.
     * @return de toegevoegde token.
     */
    protected Token addToken(final TokenType tKind, final int atlineNr, final int atcharNr, final String comment,
            final CommentType cKind)
    {
        Token result = addToken(tKind, atlineNr, atcharNr);
        result.setText(comment);
        result.setCommentType(cKind);
        return result;
    }

    /**
     * Retourneert het laatste token uit de lijst van tokens.
     *
     * @return het laatste token uit de lijst van tokens.
     */
    protected Token getLastToken() {
        Token token = null;
        if (getTokens().size() > 1) {
            token = getTokens().get(getTokens().size() - 1);
        }
        return token;
    }

    /**
     * Gooit een exceptie die aangeeft dat het karakter niet geldig is.
     *
     * @param aChar het karakter dat niet geldig is.
     * @throws ParserException gooit een exceptie die aangeeft dat het karakter niet geldig is.
     */
    protected void throwErrorIllegalCharacter(final char aChar) throws ParserException {
        throwErrorIllegalCharacter(aChar, getLineNr(), getCharNr());
    }

    /**
     * Gooit een exceptie die aangeeft dat het karakter niet geldig is.
     *
     * @param aChar het karakter dat niet geldig is.
     * @param errorlineNr de regel waarop het ongeldige karakter staat.
     * @param errorCharNr het nummer van het ongeldige karakter op de regel.
     * @throws ParserException gooit een exceptie die aangeeft dat het karakter niet geldig is.
     */
    protected void throwErrorIllegalCharacter(final char aChar, final int errorlineNr, final int errorCharNr)
            throws ParserException
    {
        final String str;

        int charValue = aChar;
        if (charValue < 32 || charValue > 126) {
            str = "#" + Integer.toString(charValue);
        } else {
            str = Character.toString(aChar);
        }

        throw new ParserException(String.format("Illegal character '%s'.", str), errorlineNr, errorCharNr);
    }

    protected void throwErrorUnterminatedString(final int errorlineNr, final int errorCharNr) throws ParserException {
        throw new ParserException("'Unterminated string.", errorlineNr, errorCharNr);
    }

    protected void throwErrorUnterminatedComment(final int errorlineNr, final int errorCharNr) throws ParserException {
        throw new ParserException("'Unterminated comment.", errorlineNr, errorCharNr);
    }

    protected void throwErrorInvalidFloatNumber(final String string, final int errorlineNr, final int errorCharNr)
            throws ParserException
    {
        throw new ParserException(String.format("Invalid float number '%s'.", string), errorlineNr, errorCharNr);
    }

    protected void throwErrorInvalidIntegerNumber(final String string, final int errorlineNr, final int errorCharNr)
            throws ParserException
    {
        throw new ParserException(String.format("Invalid integer number '%s'.", string), errorlineNr, errorCharNr);
    }

    /**
     * Returns true if the LineNr is past the last line.
     * @return indicatie of het einde van de regels is behaald.
     */
    protected boolean isEOF() {
        return getLineNr() > getLines().size() - 1;
    }

    /**
     * Returns true if the CharNr is past the last position.
     * @return indicatie of het einde van de regel is behaald.
     */
    protected boolean isEOL() {
        return getCharNr() >= getMaxCharNr();
    }

    /**
     * Returns the remaining line (including the CurrentChar).
     *
     * @return the remaining line (including the CurrentChar).
     */
    protected String getRemainingLine() {
        return getLine().substring(getCharNr());
    }

    /**
     * Returns the current character (Performs no checks).
     *
     * @return het huidige karakter nummer waar de tokenizer is gebleven.
     */
    protected char getCurrentChar() {
        return getLine().charAt(getCharNr());
    }

    /**
     * Increments the character counter (Performs no checks).
     */
    protected void nextChar() {
        charNr++;
    }

    protected boolean hasLineChars(final int nrOfChars) {
        return getCharNr() < getMaxCharNr() - nrOfChars + 1;
    }

    /**
     * Fetches the next line and sets the LineNr, CharNr and MaxCharNr.
     */
    protected void nextLine() {
        setLineNr(getLineNr() + 1);

        if (getLineNr() < getLines().size()) {
            setLine(getLines().get(getLineNr()));
            setCharNr(0);
            setMaxCharNr(getLine().length());
        }
    }

    protected void fetchSingleCharSymbol() {
        addToken(TokenType.SYMBOL, getLineNr(), getCharNr(), Character.toString(getCurrentChar()));
        nextChar();
    }

    protected void fetchGroeperingSymbol() {
        addToken(TokenType.GROEPERING, getLineNr(), getCharNr(), Character.toString(getCurrentChar()));
        nextChar();
    }

    protected void fetchSingleOrDoubleCharSymbol(final char secondChar) {
        final int oldCharNr = getCharNr();
        final char oldChar = getCurrentChar();
        nextChar();

        if (!isEOL() && (getCurrentChar() == secondChar)) {
            addToken(TokenType.SYMBOL, getLineNr(), oldCharNr, new String(new char[] { oldChar, getCurrentChar() }));
            nextChar();
        } else {
            addToken(TokenType.SYMBOL, getLineNr(), oldCharNr, Character.toString(oldChar));
        }
    }

    protected void fetchIdentifier() {
        final int oldCharNr = getCharNr();
        final StringBuilder sb = new StringBuilder(Character.toString(getCurrentChar()));
        nextChar();

        while (!isEOL() && isIdentifierChar(getCurrentChar())) {
            sb.append(getCurrentChar());
            nextChar();
        }
        addToken(TokenType.IDENTIFIER, getLineNr(), oldCharNr, sb.toString());
    }

    protected void fetchString(final char quote) throws ParserException {
        // Remember start position
        int oldCharNr = getCharNr();

        // Skip '
        nextChar();

        // Search the end of the string
        StringBuilder sb = new StringBuilder();
        boolean stop = false;

        do {
            stop = getCurrentChar() == quote;
            while (!isEOL() && !stop) {
                sb.append(getCurrentChar());
                nextChar();
                stop = getCurrentChar() == quote;
            }

            if (isEOL()) {
                throwErrorUnterminatedString(getLineNr(), oldCharNr);
            }

            if (stop) {
                // Check for a stuffed char
                if (hasLineChars(2) && getLine().charAt(getCharNr() + 1) == quote) {
                    sb.append(quote);
                    nextChar();
                    nextChar();
                    stop = false;
                }
            }
        } while (!stop);
        nextChar();

        // Concatenate string to an existing string or create a new string
        Token lastToken = getLastToken();
        if (lastToken != null && lastToken.getType() == TokenType.STRING) {
            lastToken.setText(lastToken.getText() + sb.toString());
        } else {
            addToken(TokenType.STRING, getLineNr(), oldCharNr, sb.toString());
        }
    }

    protected void fetchIntegerOrFloat() throws ParserException {
        final int oldCharNr = getCharNr();
        final StringBuilder sb = new StringBuilder(Character.toString(getCurrentChar()));
        nextChar();

        // Part 1: Digits
        while (!isEOL() && Character.isDigit(getCurrentChar())) {
            sb.append(getCurrentChar());
            nextChar();
        }

        // Part 2: Optional Fraction
        boolean hasFraction = false;
        if (!isEOL() && getCurrentChar() == '.') {
            sb.append(getCurrentChar());
            nextChar();
            hasFraction = true;
        }

        // Part 3: Optional Digits
        while (!isEOL() && Character.isDigit(getCurrentChar())) {
            sb.append(getCurrentChar());
            nextChar();
        }

        // Part 4: Optional Exponent
        if (!isEOL() && (getCurrentChar() == 'E')) {
            sb.append(getCurrentChar());
            nextChar();

            // Part 5: Optional Sign
            if (!isEOL() && (getCurrentChar() == '+' || getCurrentChar() == '-')) {
                sb.append(getCurrentChar());
                nextChar();
            }

            // Part 6: Required Digits
            if (isEOL() || !Character.isDigit(getCurrentChar())) {
                throwErrorInvalidFloatNumber(sb.toString(), getLineNr(), oldCharNr);
            }

            while (!isEOL() && Character.isDigit(getCurrentChar())) {
                sb.append(getCurrentChar());
                nextChar();
            }
        }

        if (hasFraction) {
            try {
                float floatResult = Float.parseFloat(sb.toString());
                addToken(TokenType.FLOAT, getLineNr(), oldCharNr, floatResult, sb.toString());
            } catch (NumberFormatException e) {
                throwErrorInvalidFloatNumber(sb.toString(), getLineNr(), oldCharNr);
            }
        } else {
            try {
                int intResult = Integer.parseInt(sb.toString());
                addToken(TokenType.INTEGER, getLineNr(), oldCharNr, intResult, sb.toString());
            } catch (NumberFormatException e) {
                throwErrorInvalidIntegerNumber(sb.toString(), getLineNr(), oldCharNr);
            }
        }
    }

    protected void fetchBlockComment(final int startCharNr, final CommentType commentType) throws ParserException {
        int oldLineNr = getLineNr();

        String terminator = commentType.getTerminator();

        // Search terminator on the current line
        int p = getRemainingLine().indexOf(terminator);
        if (p != -1) {
            // Block comment ends on this line
            if (!isIgnoreComment()) {
                addToken(TokenType.COMMENT, getLineNr(), startCharNr, getRemainingLine().substring(getCharNr(), p),
                        commentType);
            }
            setCharNr(getCharNr() + p + terminator.length() - 1);
        } else {
            // Block comment does not end on this line
            // Store the first line and continue searching
            StringBuilder comment = new StringBuilder(getLine().substring(getCharNr()));
            while (p != -1) {
                nextLine();
                if (isEOF()) {
                    throwErrorUnterminatedComment(oldLineNr, startCharNr);
                }

                p = getLine().indexOf(terminator);
                if (p == -1) {
                    comment.append(System.getProperty("line.separator")).append(getLine());
                }
            }
            if (!ignoreComment) {
                comment.append(System.getProperty("line.separator")).append(getLine().substring(0, p));
                addToken(TokenType.COMMENT, oldLineNr, startCharNr, comment.toString(), commentType);
            }
            setCharNr(charNr + p + terminator.length() - 1);
        }
    }

    /**
     * @return the lines
     */
    public List<String> getLines() {
        return lines;
    }

    /**
     * @return the tokens
     */
    public List<Token> getTokens() {
        return tokens;
    }

    /**
     * @return the charNr
     */
    public int getCharNr() {
        return charNr;
    }

    /**
     * @param charNr the charNr to set
     */
    public void setCharNr(final int charNr) {
        this.charNr = charNr;
    }

    /**
     * @return the lineNr
     */
    public int getLineNr() {
        return lineNr;
    }

    /**
     * @param lineNr the lineNr to set
     */
    public void setLineNr(final int lineNr) {
        this.lineNr = lineNr;
    }

    /**
     * @return the maxCharNr
     */
    public int getMaxCharNr() {
        return maxCharNr;
    }

    /**
     * @param maxCharNr the maxCharNr to set
     */
    public void setMaxCharNr(final int maxCharNr) {
        this.maxCharNr = maxCharNr;
    }

    /**
     * @return the line
     */
    public String getLine() {
        return line;
    }

    /**
     * @param line the line to set
     */
    public void setLine(final String line) {
        this.line = line;
    }

    /**
     * @return the ignoreComment
     */
    public boolean isIgnoreComment() {
        return ignoreComment;
    }

    /**
     * @param ignoreComment the ignoreComment to set
     */
    public void setIgnoreComment(final boolean ignoreComment) {
        this.ignoreComment = ignoreComment;
    }

    protected boolean isIdentifierChar(final char aChar) {
        return Character.isLetterOrDigit(aChar) || aChar == '_';
    }
}
