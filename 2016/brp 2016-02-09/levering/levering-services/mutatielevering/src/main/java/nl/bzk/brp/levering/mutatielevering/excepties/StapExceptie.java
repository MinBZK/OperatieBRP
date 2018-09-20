/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.excepties;

/**
 * De algemene stap exceptie die wordt gegooid bij een exceptie binnen een stap.
 */
public class StapExceptie extends RuntimeException {

    /**
     * Constructor die een foutmelding aanneemt.
     * @param bericht De foutmelding.
     */
    public StapExceptie(final String bericht) {
        super(bericht);
    }

    /**
     * Constructor die een throwable aanneemt.
     *
     * @param t de throwable
     */
    public StapExceptie(final Throwable t) {
        super(t);
    }

}
