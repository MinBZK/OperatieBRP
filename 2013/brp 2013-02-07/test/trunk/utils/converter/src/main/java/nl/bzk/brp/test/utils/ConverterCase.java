/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils;

public enum ConverterCase {

    DEFAULT_BESTAANSRECHT("Default gevonden met bestaansrecht"),
    DEFAULT_GEEN_BESTAANSRECHT("Default gevonden zonder bestaansrecht"),
    STRUCTUUR("Structuur mapping"),
    STRUCTUUR_NO_MATCH("Structuur mapping, maar geen match"),
    MAPPING("Nieuw naar oud mapping"),
    MAPPING_FAILED("Mapping, maar geen match"),
    SAME_XPATH("Identitieke XPath match"),
    NEW("Nieuwe inhoud"),
    NOT_FOUND("Geen match gevonden");

    private String omschrijving;

    private ConverterCase(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

}
