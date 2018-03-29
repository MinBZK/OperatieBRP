/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.excepties;

/**
 * Fout treedt op indien niet kan worden bepaald welke ouder ouder 1 of 2 dient te zijn.
 */
public class OnduidelijkeOudersException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Maak nieuwe fout aan met een bericht.
     * @param message bericht van de fout.
     */
    public OnduidelijkeOudersException(final String message) {
        super(message);
    }
}
