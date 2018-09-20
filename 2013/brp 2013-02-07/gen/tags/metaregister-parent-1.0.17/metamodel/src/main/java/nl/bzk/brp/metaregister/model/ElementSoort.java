/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.model;

import java.util.HashMap;
import java.util.Map;


public enum ElementSoort {

    ATTRIBUUT("A"),
    ATTRIBUUTTYPE("AT"),
    BEDRIJFSREGEL("BR"),
    BERICHTSJABLOON("BS"),
    BASISTYPE("BT"),
    DOMEINMODEL("D"),
    GROEP("G"),
    LAAG("L"),
    OBJECTTYPE("OT"),
    SCHEMA("S"),
    VERSIE("V"),
    WAARDEREGEL_WAARDE("W");

    private static final Map<String, ElementSoort> soorten = new HashMap<String, ElementSoort>();

    private final String code;

    static {
        for (ElementSoort elementSoort : ElementSoort.values()) {
            soorten.put(elementSoort.code, elementSoort);
        }
    }

    private ElementSoort(final String code) {
        this.code = code;
    }

    public boolean isSoort(final GeneriekElement element) {
        return this == getSoort(element);
    }

    public static ElementSoort getSoort(final GeneriekElement element) {
        return soorten.get(element.getSoortElement().getCode());
    }

}
