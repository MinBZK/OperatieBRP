/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import nl.bzk.brp.expressietaal.lexical.tokens.Token;

/**
 * Beschrijft een fout die is opgetreden tijdens parsing. De beschrijving bestaat uit een code voor de fout en het
 * token dat de fout veroorzaakte of vanaf waar de fout optreedt.
 */
public class ParserFout {
    private final ParserFoutCode foutCode;
    private final Token token;

    /**
     * Constructor.
     *
     * @param foutCode Code van de aangetroffen fout.
     * @param token    Token dat de fout veroorzaakt.
     */
    public ParserFout(final ParserFoutCode foutCode, final Token token) {
        this.foutCode = foutCode;
        this.token = token;
    }

    public ParserFoutCode getFoutCode() {
        return foutCode;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String toString() {
        String foutmelding;
        if (getFoutCode() != null) {
            foutmelding = getFoutCode().getFoutmelding();
        } else {
            foutmelding = ParserFoutCode.ONBEKENDE_FOUT.getFoutmelding();
        }
        if (getToken() != null) {
            return String.format("%s@%d", foutmelding, getToken().getPosition());
        } else {
            return foutmelding;
        }
    }
}
