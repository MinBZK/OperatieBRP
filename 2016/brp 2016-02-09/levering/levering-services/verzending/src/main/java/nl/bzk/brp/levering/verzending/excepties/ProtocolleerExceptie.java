/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.excepties;

/**
 * Exceptie die gegooid wordt door de protocolleringservice als het protocolleren mislukt is.
 */
public class ProtocolleerExceptie extends RuntimeException {

    /**
     * Constructor die een bericht aanneemt.
     *
     * @param bericht de foutmelding
     */
    public ProtocolleerExceptie(final String bericht) {
        super(bericht);
    }

    /**
     * Constructor die een bericht en voorgaande fout aanneemt.
     *
     * @param bericht de foutmelding
     * @param throwable de voorgaande fout
     */
    public ProtocolleerExceptie(final String bericht, final Throwable throwable) {
        super(bericht, throwable);
    }
}
