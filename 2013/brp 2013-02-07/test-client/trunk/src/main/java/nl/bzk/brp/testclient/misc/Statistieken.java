/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.misc;

/**
 * De Interface Statistieken.
 */
public interface Statistieken {

    /**
     * Bijwerken na bericht.
     *
     * @param bericht de bericht
     * @param status de status
     * @param duur de duur
     */
    void bijwerkenNaBericht(Bericht.BERICHT bericht, Bericht.STATUS status, Long duur);

    /**
     * Bijwerken na berichten flow.
     */
    void bijwerkenNaBerichtenFlow();

    /**
     * Reset.
     */
    void reset();

    /**
     * Haalt een aantal verzonden flow op.
     *
     * @return aantal verzonden flow
     */
    int getAantalVerzondenFlow();
}
