/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;

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
     * @param aExpressie De resultaatexpressie van een parse-opdracht.
     * @param aFout      De aangetroffen fout tijdens parsen.
     */
    public ParserResultaat(final Expressie aExpressie, final ParserFout aFout) {
        if (aFout == null) {
            this.expressie = aExpressie;
        } else {
            this.expressie = null;
        }
        this.fout = aFout;
    }

    /**
     * Constructor.
     *
     * @param aFout Fout die is opgetreden tijdens parsing.
     */
    public ParserResultaat(final ParserFout aFout) {
        this(null, aFout);
    }

    /**
     * Constructor.
     *
     * @param aExpressie De resultaatexpressie van een parse-opdracht.
     */
    public ParserResultaat(final Expressie aExpressie) {
        this(aExpressie, null);
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
        ExpressieType type = ExpressieType.ONBEKEND_TYPE;
        if (getExpressie() != null) {
            type = getExpressie().getType(null);
        }
        return type;
    }

    /**
     * Geeft de gevonden fout terug als string. Als er geen fout is, is het resultaat een lege string.
     *
     * @return Gevonden fout als string.
     */
    public final String getFoutmelding() {
        String foutmelding = "";
        if (fout != null) {
            foutmelding = fout.toString();
        }
        return foutmelding;
    }

    /**
     * Geeft TRUE als de expressie succesvol vertaald is.
     *
     * @return TRUE als de expressie succesvol vertaald is.
     */
    public final boolean succes() {
        return fout == null && expressie != null;
    }
}
