/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.jbehave;

import org.jbehave.core.annotations.AsParameters;
import org.jbehave.core.annotations.Parameter;
import org.jbehave.core.model.ExamplesTable;

/**
 * Klasse voor vertaling van {@link ExamplesTable} met meldingen.
 */
@AsParameters
public final class GeprotocolleerdePersoon {
    /**
     * pers
     */
    @Parameter(name = "pers")
    private String pers;
    /**
     * tslaatstewijzpers
     */
    @Parameter(name = "tslaatstewijzpers")
    private String tslaatstewijzpers;

    public String getPers() {
        return pers;
    }

    public String getTslaatstewijzpers() {
        return tslaatstewijzpers;
    }
}
