/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

import java.util.Objects;

/**
 * Een BRP Type.
 *
 * type is het brp element. lijst geeft aan of element in brp meerdere actuele voorkomens kan hebben. inverse geeft aan of element bij KV/KNV inverse moet
 * worden meegenomen. In BRP is dit nodig omdat bij bijvoorbeeld een datum aanvang ook is opgenomen indien datum einde is gevuld, terwijl in GBA de datumaanvang
 * leeg is indien datumeinde is gevuld.
 */
public class BrpType {

    private static final int HASH_5 = 5;
    private static final int HASH_97 = 97;
    private final String type;
    private final boolean lijst;
    private final boolean inverse;

    /**
     * Maakt een nieuw BRP Type aan.
     * @param type BRP Type
     * @param lijst geeft aan of het type een lijst is
     * @param inverse geeft aan of KV inverse moet worden opgenomen
     */
    public BrpType(final String type, final boolean lijst, final boolean inverse) {
        this.type = type;
        this.lijst = lijst;
        this.inverse = inverse;
    }

    /**
     * Geeft type van BRP type.
     * @return het type
     */
    public final String getType() {
        return type;
    }

    /**
     * Indicatie of BRP Type een lijst is.
     * @return de indicatie of het lijst is
     */
    public final boolean isLijst() {
        return lijst;
    }

    /**
     * Indicatie of element inverse in vergelijking moet worden meegenomen.
     * @return indicatie iverse
     */
    public boolean isInverse() {
        return inverse;
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
