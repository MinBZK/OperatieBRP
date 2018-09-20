/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.exceptie;

/**
 * Exceptie die aangeeft dat een persoon niet gevonden kon worden.
 */
public class PersoonNietAanwezigExceptie extends RuntimeException {

    /**
     * Default constructor.
     */
    public PersoonNietAanwezigExceptie() {
    }

    /**
     * Constructor met fouttekst.
     *
     * @param fouttekst fouttekst
     */
    public PersoonNietAanwezigExceptie(final String fouttekst) {
        super(fouttekst);
    }

    /**
     * Constructor met oorzaak.
     *
     * @param oorzaak de oorzaak
     */
    public PersoonNietAanwezigExceptie(final Throwable oorzaak) {
        super(oorzaak);
    }

    /**
     * Constructor voor deze exceptie die het bericht aanneemt.
     *
     * @param bericht Het bericht.
     * @param oorzaak De oorzaak.
     */
    public PersoonNietAanwezigExceptie(final String bericht, final Throwable oorzaak) {
        super(bericht, oorzaak);
    }

}
