/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.jbehave;

import java.util.Arrays;
import java.util.List;
import org.jbehave.core.annotations.AsParameters;
import org.jbehave.core.annotations.Parameter;

/**
 * Klasse voor vertaling van {@link org.jbehave.core.model.ExamplesTable} met sorteringen.
 */
@AsParameters
public final class SorteringRegel {
    /**
     * groep
     */
    @Parameter(name = "groep")
    private String groep;
    /**
     * attribuut
     */
    @Parameter(name = "attribuut")
    private String attribuut;
    /**
     * verwachteWaardes
     */
    @Parameter(name = "verwachteWaardes")
    private String verwachteWaardes;

    public List<String> getVerwachteWaardes() {
        return Arrays.asList(verwachteWaardes.split(","));
    }

    public String getGroep() {
        return groep;
    }

    public String getAttribuut() {
        return attribuut;
    }
}
