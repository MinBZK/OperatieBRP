/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java.domein;

import java.util.LinkedHashSet;
import java.util.Set;


public class Enumeration extends ObjectClass {

    private Set<EnumValue> enumValues = new LinkedHashSet<EnumValue>();


    public Enumeration(String naam, String documentatie) {
        super(naam, documentatie);
    }

    public Set<EnumValue> getEnumValues() {
        return enumValues;
    }

    public void addValue(EnumValue value) {
        enumValues.add(value);

    }

    public void addValues(Set<EnumValue> values) {
        enumValues.addAll(values);

    }

}
