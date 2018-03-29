/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.Serializable;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractCategorieGebaseerdParsedLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3EindBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.syntax.Lo3SyntaxControle;

/**
 * Vb01.
 */
public final class Vb01Bericht extends AbstractCategorieGebaseerdParsedLo3Bericht implements Lo3Bericht, Lo3EindBericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER =
            new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.LENGTE_BERICHT, Lo3HeaderVeld.BERICHT);

    /**
     * Default constructor.
     */
    public Vb01Bericht() {
        super(HEADER, Lo3SyntaxControle.STANDAARD, "Vb01", "uc501-gba");
    }

    /**
     * Convenience constructor.
     * @param bericht Het vrije bericht.
     */
    public Vb01Bericht(final String bericht) {
        this();
        setBericht(bericht);
    }

    /*
     * *********************************************************************************************
     * ****************
     */

    /**
     * Zet het bericht.
     * @param bericht bericht
     */
    public void setBericht(final String bericht) {
        setHeader(Lo3HeaderVeld.BERICHT, bericht == null ? "" : bericht);
        setHeader(Lo3HeaderVeld.LENGTE_BERICHT, bericht == null ? "0" : Integer.toString(bericht.length()));
    }

}
