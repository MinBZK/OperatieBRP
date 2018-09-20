/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.tokenizer;

/**
 * Type van een Comment.
 */
public enum CommentType {

    /** Old Pascal code block. */
    CODE_OLD_BLOCK("*)"),

    /** New Pascal code block. */
    CODE_NEW_BLOCK("}"),

    /** Comment block. */
    COMMENT_BLOCK("*/"),

    /** Double slash. */
    DOUBLE_SLASH(null);

    private final String terminator;

    private CommentType(final String name) {
        this.terminator = name;
    }

    public String getTerminator() {
        return terminator;
    }

}
