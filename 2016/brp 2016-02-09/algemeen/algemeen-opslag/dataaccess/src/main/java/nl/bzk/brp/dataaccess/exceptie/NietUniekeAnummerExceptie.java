/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.exceptie;

/**
 * Exceptie die aangeeft dat een opgegeven Anummer niet uniek is.
 */
public class NietUniekeAnummerExceptie extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * De constructor voor deze klasse.
     *
     * @param bericht Het bericht.
     * @param oorzaak De oorzaak.
     */
    public NietUniekeAnummerExceptie(final String bericht, final Throwable oorzaak) {
        super(bericht, oorzaak);
    }

}
