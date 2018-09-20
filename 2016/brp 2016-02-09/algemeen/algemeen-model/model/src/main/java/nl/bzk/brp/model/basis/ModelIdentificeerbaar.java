/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Interface voor het identificeerbaar zijn van (database) model instanties. Via deze interface is de database id van het object en/of groep op te halen
 * (indien het object gepersisteerd is).
 *
 * @param <T> Nummer type van de id (dus bijvoorbeeld of het een {@link Integer} of {@link Long} is).
 */
public interface ModelIdentificeerbaar<T extends Number> {

    /**
     * Retourneert de id, een (over het type heen) uniek identificeerbaar id, van het object. Dit komt overeen met de id uit de database.
     *
     * @return de id van het object.
     */
    T getID();

}
