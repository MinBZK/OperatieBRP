/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.vergrendeling;

import java.sql.SQLException;

/**
 * Er is een SQLExceptie opgetreden bij het vergrendelen van een persoon.
 */
public class VergrendelFout extends SQLException {

    /**
     * Default constructor.
     */
    public VergrendelFout() {

    }

    /**
     * Contructor waar de originele SQLException aan kan worden meegegeven.
     * @param ex de originele SQLException.
     */
    public VergrendelFout(final SQLException ex) {
        super(ex);
    }
}
