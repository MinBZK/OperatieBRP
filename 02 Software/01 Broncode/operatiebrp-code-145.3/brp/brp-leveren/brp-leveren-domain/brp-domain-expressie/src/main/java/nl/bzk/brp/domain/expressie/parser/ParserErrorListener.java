/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import nl.bzk.brp.domain.expressie.parser.exception.ExpressieParseException;
import nl.bzk.brp.domain.expressie.parser.exception.ParserFoutCode;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Token;

/**
 * Implementatie van {@link BaseErrorListener} voor het rapporteren van syntaxfouten geconstateerd door ANTLR.
 */
final class ParserErrorListener extends BaseErrorListener {

    /**
     * Constructor.
     */
    ParserErrorListener() {
    }

    @Override
    public void syntaxError(final org.antlr.v4.runtime.Recognizer<?, ?> recognizer,
                            final java.lang.Object offendingSymbol,
                            final int line, final int charPositionInLine, final java.lang.String msg,
                            final org.antlr.v4.runtime.RecognitionException e) {
        final Token antlrToken = (Token) offendingSymbol;

        if (offendingSymbol == null) {
            //in het geval van lexer fouten, bijv. token recognition error at: '*' voor de expressie **bla**
            throw new ExpressieParseException(msg);
        } else {
            throw new ExpressieParseException(
                    String.format("%s \"%s\" op positie: %d", ParserFoutCode.SYNTAX_ERROR, msg, antlrToken.getStartIndex())
            );
        }
    }
}
