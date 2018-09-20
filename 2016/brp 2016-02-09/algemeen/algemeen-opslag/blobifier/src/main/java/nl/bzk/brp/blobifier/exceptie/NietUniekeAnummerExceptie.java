/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.exceptie;

/**
 * De exceptie die gegooid wordt als er meerdere resultaten terugkomen, maar er één resultaat verwacht wordt.
 */
public class NietUniekeAnummerExceptie extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public NietUniekeAnummerExceptie() {
    }

    /**
     * Constructor met fouttekst.
     *
     * @param fouttekst fouttekst
     */
    public NietUniekeAnummerExceptie(final String fouttekst) {
        super(fouttekst);
    }

    /**
     * Constructor met oorzaak.
     *
     * @param oorzaak de oorzaak
     */
    public NietUniekeAnummerExceptie(final Throwable oorzaak) {
        super(oorzaak);
    }

    /**
     * De constructor voor deze klasse.
     *
     * @param fouttekst De fouttekst.
     * @param oorzaak De oorzaak.
     */
    public NietUniekeAnummerExceptie(final String fouttekst, final Throwable oorzaak) {
        super(fouttekst, oorzaak);
    }

}
