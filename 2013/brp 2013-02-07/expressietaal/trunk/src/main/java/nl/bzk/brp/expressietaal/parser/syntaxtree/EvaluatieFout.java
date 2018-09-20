/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

/**
 * Beschrijft een fout die is opgetreden tijdens evaluatie. De beschrijving bestaat uit een code voor de fout en
 * aanvullende informatie over de fout.
 */
public class EvaluatieFout {
    private final EvaluatieFoutCode foutCode;
    private final String informatie;

    /**
     * Constructor.
     *
     * @param foutCode   Code van de aangetroffen fout.
     * @param informatie Aanvullende informatie over de fout.
     */
    public EvaluatieFout(final EvaluatieFoutCode foutCode, final String informatie) {
        this.foutCode = foutCode;
        this.informatie = informatie;
    }

    public EvaluatieFoutCode getFoutCode() {
        return foutCode;
    }

    public String getInformatie() {
        return informatie;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", getFoutCode().getFoutmelding(), getInformatie());
    }
}
