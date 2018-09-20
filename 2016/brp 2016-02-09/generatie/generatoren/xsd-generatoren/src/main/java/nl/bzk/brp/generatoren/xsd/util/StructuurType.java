/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.xsd.util;

/**
 * Een type structuur dat voorkomt in de XSD. Hoofdzakelijk afhankelijk van het historie patroon.
 */
public enum StructuurType {

    /** Gewone structuur. */
    STRUCTUUR("Structuur"),
    /** Structuur die identificeerbaar is. */
    STRUCTUUR_IDENTIFICEERBAAR("StructuurIdentificeerbaar"),
    /** Structuur met een bestaans periode. */
    STRUCTUUR_BESTAANSPERIODE("StructuurBestaansperiode"),
    /** Structuur met formele historie. */
    STRUCTUUR_FORMELE_HISTORIE("StructuurFormeleHistorie"),
    /** Structuur met materiele historie. */
    STRUCTUUR_MATERIELE_HISTORIE("StructuurMaterieleHistorie");

    private String xsdType;

    /**
     * Nieuwe structuur enum met het opgegeven XSD type..
     *
     * @param xsdType het xsd type.
     */
    private StructuurType(final String xsdType) {
        this.xsdType = xsdType;
    }

    /**
     * Retourneert het XSD type behorende bij dit structuur type.
     *
     * @return het XSD type.
     */
    public String getXsdType() {
        return xsdType;
    }

}
