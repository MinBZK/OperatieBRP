/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

import java.util.Objects;

/**
 * Een BRP Type.
 */
public class BrpType {

    private static final int HASH_5 = 5;
    private static final int HASH_97 = 97;
    private final String type;
    private final boolean lijst;

    /**
     * Maakt een nieuw BRP Type aan.
     * @param type BRP Type
     */
    public BrpType(final String type) {
        this.type = type;
        this.lijst = type.contains("RMAP");
    }

    /**
     * Geeft type van BRP type.
     * @return  het type
     */
    public final String getType() {
        return type;
    }

    /**
     * Indicatie of BROP Type een lijst is.
     * @return de indicatie of het lijst is
     */
    public final boolean isLijst() {
        return lijst;
    }

    @Override
    public final int hashCode() {
        int hash = HASH_5;
        hash = HASH_97 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BrpType other = (BrpType) obj;
        return Objects.equals(this.type, other.type);
    }

}
