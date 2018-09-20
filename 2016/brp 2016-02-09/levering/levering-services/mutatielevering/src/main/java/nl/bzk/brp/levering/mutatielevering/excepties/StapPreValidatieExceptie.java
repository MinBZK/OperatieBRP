/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.excepties;

/**
 * De stap prevalidatie exceptie die wordt gegooid bij een exceptie in de prevalidatie binnen een stap.
 */
public class StapPreValidatieExceptie extends StapExceptie {

    /**
     * Constructor die een foutmelding aanneemt.
     * @param bericht De foutmelding.
     */
    public StapPreValidatieExceptie(final String bericht) {
        super(bericht);
    }

}
