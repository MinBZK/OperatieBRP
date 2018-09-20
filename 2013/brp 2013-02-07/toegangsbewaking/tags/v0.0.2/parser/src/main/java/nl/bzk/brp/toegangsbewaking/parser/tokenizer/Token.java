/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.tokenizer;



/**
 * Lexical part of a script such as an identifier, symbol, comment etc.
 */
public class Token implements Cloneable {

    private final TokenType type;
    private int line;
    private int position;
    private String text;
    private int i;
    private float f;
    private CommentType commentType;

    public Token(final TokenType kind, final int line, final int position) {
        this.type = kind;
        this.line = line;
        this.position = position;
    }

    /**
     *
     */
    @Override
    public Token clone() {
        Token result = new Token(this.type, this.line, this.position);
        result.text = text;
        result.i = i;
        result.f = f;
        result.commentType = commentType;
        return result;
    }


    /**
     * @return the type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * @return the line
     */
    public int getLine() {
        return line;
    }

    /**
     * @param line the line to set
     */
    public void setLine(final int line) {
        this.line = line;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(final int position) {
        this.position = position;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(final String text) {
        this.text = text;
    }

    /**
     * @return the i
     */
    public int getI() {
        return i;
    }

    /**
     * @param i the i to set
     */
    public void setI(final int i) {
        this.i = i;
    }

    /**
     * @return the f
     */
    public float getF() {
        return f;
    }

    /**
     * @param f the f to set
     */
    public void setF(final float f) {
        this.f = f;
    }

    /**
     * @return the commentType
     */
    public CommentType getCommentType() {
        return commentType;
    }

    /**
     * @param commentType the commentType to set
     */
    public void setCommentType(final CommentType commentType) {
        this.commentType = commentType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s;%s <%s> '%s'", line, position, type, text);
    }

}
