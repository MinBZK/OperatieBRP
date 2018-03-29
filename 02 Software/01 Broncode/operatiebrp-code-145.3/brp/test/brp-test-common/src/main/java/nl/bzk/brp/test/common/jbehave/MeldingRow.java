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
public final class MeldingRow {
    /**
     * code
     */
    @Parameter(name = "CODE")
    private String code;
    /**
     * melding
     */
    @Parameter(name = "MELDING")
    private String melding;

    public String getCode() {
        return code;
    }

    public String getMelding() {
        return melding;
    }
}
