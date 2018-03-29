/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

/**
 * Exceptie die gegooid wordt als het verzenden van een bericht mislukt.
 */
final class VerzendExceptie extends RuntimeException {

    private static final long serialVersionUID = 3648800187864174059L;

    /**
     * Constructor die een bericht aanneemt.
     * @param bericht de foutmelding
     */
    VerzendExceptie(final String bericht) {
        super(bericht);
    }

    /**
     * Constructor die een bericht en voorgaande fout aanneemt.
     * @param bericht de foutmelding
     * @param throwable de voorgaande fout
     */
    VerzendExceptie(final String bericht, final Throwable throwable) {
        super(bericht, throwable);
    }
}
