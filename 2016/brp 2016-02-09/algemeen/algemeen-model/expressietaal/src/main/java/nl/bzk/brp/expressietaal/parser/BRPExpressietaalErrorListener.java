/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Token;

/**
 *
 */
public class BRPExpressietaalErrorListener extends BaseErrorListener {

    private final List<ParserFout> fouten;

    /**
     * Constructor.
     */
    public BRPExpressietaalErrorListener() {
        super();
        fouten = new ArrayList<ParserFout>();
    }

    public final List<ParserFout> getFouten() {
        return fouten;
    }

    @Override
    public final void syntaxError(final org.antlr.v4.runtime.Recognizer<?, ?> recognizer,
        final java.lang.Object offendingSymbol,
        final int line, final int charPositionInLine, final java.lang.String msg,
        final org.antlr.v4.runtime.RecognitionException e)
    {
        final Token antlrToken = (Token) offendingSymbol;
        fouten.add(new ParserFout(ParserFoutCode.SYNTAX_ERROR, antlrToken.getText(), antlrToken.getStartIndex()));
    }

    /**
     * Voegt een fout toe aan de tijdens het parsen gevonden fouten. Deze fouten worden ontdekt door de BRP-specifieke code en niet door de ANTLR-code.
     *
     * @param fout De gevonden fout.
     */
    public final void voegFoutToe(final ParserFout fout) {
        fouten.add(fout);
    }

}
