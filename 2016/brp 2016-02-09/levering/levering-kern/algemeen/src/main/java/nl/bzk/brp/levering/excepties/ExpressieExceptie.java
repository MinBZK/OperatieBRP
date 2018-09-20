/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.excepties;

/**
 * Exceptie die gegooid wordt als er problemen zijn met een expressie.
 */
public class ExpressieExceptie extends Exception {

    /**
     * De constructor die de foutmelding aanneemt.
     *
     * @param bericht De foutmelding.
     */
    public ExpressieExceptie(final String bericht) {
        super(bericht);
    }

    /**
     * De constructor die de foutmelding en exceptie aanneemt.
     *
     * @param message de foutmelding
     * @param cause de oorzaak
     */
    public ExpressieExceptie(final String message, final Throwable cause) {
        super(message, cause);
    }
}
