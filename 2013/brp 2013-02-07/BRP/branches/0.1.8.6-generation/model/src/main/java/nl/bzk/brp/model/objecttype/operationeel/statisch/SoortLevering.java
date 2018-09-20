/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Deze code is gegenereerd vanuit het metaregister van het BRP, versie 1.0.18.
 *
 */
package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Categorisatie van Levering.
.
 * @version 1.0.18.
 */
public enum SoortLevering {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY(""),
    /** Bevraging. */
    BEVRAGING("Bevraging"),
    /** Mutatie. */
    MUTATIE("Mutatie"),
    /** Selectie. */
    SELECTIE("Selectie");

    /** De naam van de soort levering. */
    private final String naam;

    /**
     * Constructor.
     *
     * @param naam De naam van de soort levering.
     *
     */
    private SoortLevering(final String naam) {
        this.naam = naam;
    }

    /**
     * @return De naam van de soort levering.
     */
    public String getNaam() {
        return naam;
    }

}
