/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

/**
 * Beschrijft een fout die is opgetreden tijdens parsing. De beschrijving bestaat uit een code voor de fout en het
 * token dat de fout veroorzaakte of vanaf waar de fout optreedt.
 */
public class ParserFout {
    private final ParserFoutCode foutCode;
    private final String token;
    private final int positie;

    /**
     * Constructor.
     *
     * @param aFoutCode Code van de aangetroffen fout.
     * @param aToken    Token dat de fout veroorzaakt.
     * @param aPositie  Positie in de tekst waar de fout begint.
     */
    public ParserFout(final ParserFoutCode aFoutCode, final String aToken, final int aPositie) {
        if (aFoutCode != null) {
            this.foutCode = aFoutCode;
        } else {
            this.foutCode = ParserFoutCode.GEEN_FOUT;
        }
        this.token = aToken;
        this.positie = aPositie;
    }

    public final ParserFoutCode getFoutCode() {
        return foutCode;
    }

    public final int getPositie() {
        return positie;
    }

    @Override
    public final String toString() {
        String foutmelding = getFoutCode().getFoutmelding();
        if (token != null) {
            foutmelding = String.format("%s@%d", foutmelding, getPositie());
        }
        return foutmelding;
    }
}
