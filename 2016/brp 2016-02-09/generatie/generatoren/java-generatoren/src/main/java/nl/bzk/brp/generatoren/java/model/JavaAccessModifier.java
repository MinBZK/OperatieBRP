/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

/** Access modifiers die we in Java kennen. */
public enum JavaAccessModifier {
    /** Protected. */
    PROTECTED("protected"),
    /** Private. */
    PRIVATE("private"),
    /** Public. */
    PUBLIC("public");

    private final String modifierNaam;

    /**
     * Standaard constructor die de naam direct zet.
     *
     * @param modifierNaam de naam van de modifier.
     */
    private JavaAccessModifier(final String modifierNaam) {
        this.modifierNaam = modifierNaam;
    }

    /**
     * Retourneert de naam van de modifier.
     *
     * @return de naam van de modifier.
     */
    public String getModifierNaam() {
        return modifierNaam;
    }
}
