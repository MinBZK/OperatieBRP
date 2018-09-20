/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;

/**
 * Bundelt het resultaat van een parse-poging. Indien parsing succesvol was, is er een resultaatexpressie voorhanden;
 * zo niet, dan is de aangetroffen fout beschikbaar.
 */
public class ParserResultaat {

    private final Expressie expressie;
    private final ParserFout fout;

    /**
     * Constructor.
     *
     * @param expressie De resultaatexpressie van een parse-opdracht.
     * @param fout      De aangetroffen fout tijdens parsen.
     */
    public ParserResultaat(final Expressie expressie, final ParserFout fout) {
        if (fout == null) {
            this.expressie = expressie;
        } else {
            this.expressie = null;
        }
        this.fout = fout;
    }

    /**
     * Constructor.
     *
     * @param fout Fout die is opgetreden tijdens parsing.
     */
    public ParserResultaat(final ParserFout fout) {
        this(null, fout);
    }

    /**
     * Constructor.
     *
     * @param expressie De resultaatexpressie van een parse-opdracht.
     */
    public ParserResultaat(final Expressie expressie) {
        this(expressie, null);
    }

    public final Expressie getExpressie() {
        return expressie;
    }

    public final ParserFout getFout() {
        return fout;
    }

    /**
     * Geeft het type van de expressie die als resultaat is teruggegeven. Geeft type UNKNOWN terug als parsing is
     * mislukt.
     *
     * @return Type van de resultaatexpressie.
     */
    public final ExpressieType getType() {
        if (getExpressie() != null) {
            return getExpressie().getType();
        } else {
            return ExpressieType.UNKNOWN;
        }
    }

    /**
     * Geeft de gevonden fout terug als string. Als er geen fout is, is het resultaat een lege string.
     *
     * @return Gevonden fout als string.
     */
    public final String getFoutmelding() {
        if (fout != null) {
            return fout.toString();
        } else {
            return "";
        }
    }

    /**
     * Geeft TRUE als de expressie succesvol vertaald is.
     *
     * @return TRUE als de expressie succesvol vertaald is.
     */
    public boolean succes() {
        return (fout == null && expressie != null);
    }
}
