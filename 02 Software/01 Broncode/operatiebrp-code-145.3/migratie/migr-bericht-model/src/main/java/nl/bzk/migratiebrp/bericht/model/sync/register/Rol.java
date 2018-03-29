/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.register;

/**
 * Rol die een partij kan hebben.
 */
public enum Rol {
    /**
     * Afnemer rol.
     */
    AFNEMER,

    /**
     * Bijhouder rol.
     */
    BIJHOUDINGSORGAAN_COLLEGE,

    /**
     * Bijhouder rol.
     */
    BIJHOUDINGSORGAAN_MINISTER,

    /**
     * Bijhouder rol.
     */
    BIJHOUDINGSVOORSTELORGAAN;
}
