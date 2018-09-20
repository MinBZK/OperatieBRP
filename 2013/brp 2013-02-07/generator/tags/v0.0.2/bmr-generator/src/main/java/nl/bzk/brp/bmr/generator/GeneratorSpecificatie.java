/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator;

/**
 * Specificatie van een code generator. Deze class combineert een generator met een repository die het element met
 * een gegeven naam levert waaruit de generator code genereert.
 */
public class GeneratorSpecificatie {

    private final Generator generator;
    private String          container;

    public GeneratorSpecificatie(final Generator generator, final String container) {
        this.generator = generator;
        this.container = container;
    }

    public Generator getGenerator() {
        return generator;
    }

    /**
     * @return the container
     */
    public String getContainer() {
        return container;
    }
}
