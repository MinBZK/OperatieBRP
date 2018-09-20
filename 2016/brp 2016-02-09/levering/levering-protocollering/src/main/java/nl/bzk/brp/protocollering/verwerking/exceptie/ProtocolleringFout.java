/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.verwerking.exceptie;

/**
 * Wrapper voor foutmeldingen in het protocolleren.
 */
public class ProtocolleringFout extends RuntimeException {

    /**
     * Wrapper om de originele foutmelding om 1 ProtocolleringFout naar buiten te communiceren.
     *
     * @param foutmelding Tekst voor de melding.
     * @param throwable   originele exceptie.
     */
    public ProtocolleringFout(final String foutmelding, final Throwable throwable) {
        super(foutmelding, throwable);
    }

    /**
     * Maak een foutmelding voor protocollering.
     * @param foutmelding de melding tekst.
     */
    public ProtocolleringFout(final String foutmelding) {
        super(foutmelding);
    }

    /**
     * Wrap de fout in een protocollering fout.
     * @param throwable de originele fout.
     */
    public ProtocolleringFout(final Throwable throwable) {
        super(throwable);
    }
}
