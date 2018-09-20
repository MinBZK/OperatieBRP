/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.exceptie;

/**
 *
 */
public class NietUniekeBsnExceptie extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public NietUniekeBsnExceptie() {
    }

    /**
     * Constructor met fouttekst.
     *
     * @param fouttekst fouttekst
     */
    public NietUniekeBsnExceptie(final String fouttekst) {
        super(fouttekst);
    }

    /**
     * Constructor met oorzaak.
     *
     * @param oorzaak de oorzaak
     */
    public NietUniekeBsnExceptie(final Throwable oorzaak) {
        super(oorzaak);
    }

    /**
     * De constructor voor deze klasse.
     *
     * @param bericht Het bericht.
     * @param oorzaak De oorzaak.
     */
    public NietUniekeBsnExceptie(final String bericht, final Throwable oorzaak) {
        super(bericht, oorzaak);
    }

}
