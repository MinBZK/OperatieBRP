/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.jbehave;

import org.jbehave.core.annotations.AsParameters;
import org.jbehave.core.annotations.Parameter;

/**
 * Klasse voor vertaling van {@link org.jbehave.core.model.ExamplesTable} met voorkomen / attribuut aanwezig.
 */
@AsParameters
public final class VoorkomenAttribuutAanwezigRegel {

    /**
     * groep
     */
    @Parameter(name = "groep")
    private String  groep;
    /**
     * nummer
     */
    @Parameter(name = "nummer")
    private Integer nummer;
    /**
     * attribuut
     */
    @Parameter(name = "attribuut")
    private String attribuut;
    /**
     * aanwezig
     */
    @Parameter(name = "aanwezig")
    private String aanwezig;

    public boolean isAanwezig() {
        return WaarOnwaar.isWaar(aanwezig);
    }

    public String getGroep() {
        return groep;
    }

    public Integer getNummer() {
        return nummer;
    }

    public String getAttribuut() {
        return attribuut;
    }

    public String getAanwezig() {
        return aanwezig;
    }
}
