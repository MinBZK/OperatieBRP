/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.dalapi;

/**
 * AlgemeneQueryFout.
 */
public class AlgemeneQueryFout extends QueryNietUitgevoerdException {

    private static final long serialVersionUID = 4786397568164023953L;

    private static final String STANDAARD_FOUT_MELDING = "algemene query fout";

    /**
     * Constructor.
     */
    public AlgemeneQueryFout() {
        super(STANDAARD_FOUT_MELDING);
    }

    /**
     * @param cause cause
     */
    public AlgemeneQueryFout(final Throwable cause) {
        super(STANDAARD_FOUT_MELDING, cause);
    }
}
