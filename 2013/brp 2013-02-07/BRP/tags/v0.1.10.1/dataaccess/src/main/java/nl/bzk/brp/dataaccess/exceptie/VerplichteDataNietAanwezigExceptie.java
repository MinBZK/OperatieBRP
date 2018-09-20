/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.exceptie;

/**
 * Exceptie die aangeeft dat een (in een bepaalde situatie) verplicht veld/data niet in de te persisteren entity is
 * aangetroffen (was <code>null</code> of leeg). Het gaat hierbij om bijvoorbeeld referenties via primaire sleutels
 * naar records/entities in de database of om velden die in een bepaalde situatie niet leeg of <code>null</code> mogen
 * zijn.
 */
public class VerplichteDataNietAanwezigExceptie extends RuntimeException {

    /**
     * Standaard constructor die het bericht zet van de exceptie.
     * @param message het fout bericht.
     */
    public VerplichteDataNietAanwezigExceptie(final String message) {
        super(message);
    }
}
