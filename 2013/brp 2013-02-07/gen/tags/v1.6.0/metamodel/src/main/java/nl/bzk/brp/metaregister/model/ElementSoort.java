/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.model;

import java.util.HashMap;
import java.util.Map;


/** Enumeratie van alle element soorten die voorkomen in het BMR. */
public enum ElementSoort {

    /** Een attribuut. */
    ATTRIBUUT("A"),
    /** Een attribuuttype. */
    ATTRIBUUTTYPE("AT"),
    /** Een (bedrijfs)regel. */
    BEDRIJFSREGEL("BR"),
    /** Een bericht sjabloon. */
    BERICHTSJABLOON("BS"),
    /** Een basis type. */
    BASISTYPE("BT"),
    /** Een domein model. */
    DOMEINMODEL("D"),
    /** Een groep. */
    GROEP("G"),
    /** Een laag. */
    LAAG("L"),
    /** Een objecttype. */
    OBJECTTYPE("OT"),
    /** Een schema. */
    SCHEMA("S"),
    /** Een waarderegel waarde. */
    WAARDEREGEL_WAARDE("W");

    private static final Map<String, ElementSoort> SOORTEN = new HashMap<String, ElementSoort>();

    private final String code;

    static {
        for (ElementSoort elementSoort : ElementSoort.values()) {
            SOORTEN.put(elementSoort.code, elementSoort);
        }
    }

    /**
     * Standaard constructor die direct de code initialiseert.
     *
     * @param code de code behorende bij de element soort.
     */
    private ElementSoort(final String code) {
        this.code = code;
    }

    /**
     * Geeft aan of een element van deze soort is.
     *
     * @param element het element dat moet worden gecontroleerd.
     * @return een boolean die aangeeft of het element van deze soort is of niet.
     */
    public boolean isSoort(final Element element) {
        return this == getSoort(element);
    }

    /**
     * Retourneert de soort van het element.
     *
     * @param element het element waarvoor de soort moet worden bepaald.
     * @return de soort van het element.
     */
    public static ElementSoort getSoort(final Element element) {
        return SOORTEN.get(element.getSoortElement().getCode());
    }

}
