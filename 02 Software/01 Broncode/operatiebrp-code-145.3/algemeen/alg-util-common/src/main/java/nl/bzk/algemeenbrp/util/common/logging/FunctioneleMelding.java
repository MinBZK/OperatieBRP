/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common.logging;

/**
 * Interface voor functionele meldingen.
 */
public interface FunctioneleMelding {

    /**
     * Geeft de code van de functionele melding terug.
     *
     * @return De code van de functionele melding.
     */
    String getCode();

    /**
     * Geeft de omschrijving van de functionele melding terug.
     *
     * @return De omschrijving van de functionele melding.
     */
    String getOmschrijving();
}
