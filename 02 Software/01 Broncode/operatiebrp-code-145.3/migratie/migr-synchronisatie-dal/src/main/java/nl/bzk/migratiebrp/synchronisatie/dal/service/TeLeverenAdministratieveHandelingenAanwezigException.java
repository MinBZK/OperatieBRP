/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service;

/**
 * Exceptie als er nog te leveren administratieve handelingen zijn.
 */
public class TeLeverenAdministratieveHandelingenAanwezigException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param persoonslijstId persoonslijst id
     */
    public TeLeverenAdministratieveHandelingenAanwezigException(long persoonslijstId) {
        super("Er zijn nog te leveren administratieve handelingen aanwezig voor de persoonslijst met id " + persoonslijstId);
    }
}
