/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

/**
 * Exception class dat duidt op een verlopen object sleutel.
 *
 * @brp.bedrijfsregel BRAL0014
 */
public class ObjectSleutelVerlopenExceptie extends OngeldigeObjectSleutelExceptie {

    private static final long serialVersionUID = 1L;

    /**
     * Maak een nieuwe exceptie met een specifiek bericht.
     *
     * @param message  het bericht
     */
    public ObjectSleutelVerlopenExceptie(final String message) {
        super(message);
    }

}
