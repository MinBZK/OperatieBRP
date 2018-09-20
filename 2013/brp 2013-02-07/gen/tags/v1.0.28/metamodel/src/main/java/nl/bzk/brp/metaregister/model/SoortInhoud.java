/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.model;

import java.util.HashMap;
import java.util.Map;


public enum SoortInhoud {

    DYNAMISCH('D'),
    STATISCH('S'),
    ENUMERATIE('X');

    private static final Map<Character, SoortInhoud> SOORTEN = new HashMap<Character, SoortInhoud>();

    private final Character code;

    static {
        for (SoortInhoud elementSoort : SoortInhoud.values()) {
            SOORTEN.put(elementSoort.code, elementSoort);
        }
    }

    private SoortInhoud(final Character code) {
        this.code = code;
    }

    public boolean isSoort(final Element element) {
        return this == getSoort(element);
    }

    public static SoortInhoud getSoort(final Element element) {
        return SOORTEN.get(element.getSoortInhoud());
    }

}
