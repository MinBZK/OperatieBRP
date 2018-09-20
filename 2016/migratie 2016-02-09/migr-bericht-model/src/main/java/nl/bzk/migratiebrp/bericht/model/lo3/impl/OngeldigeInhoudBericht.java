/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;


/**
 * Bericht om aan te geven dat een bericht om inhoudelijke redenen ongeldig is.
 */
public final class OngeldigeInhoudBericht extends OngeldigBericht {
    private static final long serialVersionUID = 1L;

    // private static final ToStringStyle TO_STRING_STYLE = new
    // OverridenClassToStringStyle("OngeldigeInhoud(Lo3)Bericht");

    /**
     * Constructor.
     *
     * @param bericht
     *            bericht
     * @param melding
     *            melding
     */
    public OngeldigeInhoudBericht(final String bericht, final String melding) {
        super(bericht, melding);
    }
}
