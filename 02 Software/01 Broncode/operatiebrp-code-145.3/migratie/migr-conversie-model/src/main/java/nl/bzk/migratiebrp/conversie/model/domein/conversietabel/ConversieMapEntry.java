/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel;

import java.util.Map;

/**
 * Een conversie key paar.
 * @param <L> LO3 waarde
 * @param <B> BRP waarde
 */
public final class ConversieMapEntry<L, B> implements Map.Entry<L, B> {

    private final L lo3Waarde;
    private final B brpWaarde;

    /**
     * Maakt een ConversieMapEntry object.
     * @param lo3Waarde lo3 waarde
     * @param brpWaarde brp waarde
     */
    public ConversieMapEntry(final L lo3Waarde, final B brpWaarde) {
        this.lo3Waarde = lo3Waarde;
        this.brpWaarde = brpWaarde;
    }

    /**
     * Geef de waarde van key.
     * @return de lo3 waarde
     */
    @Override
    public L getKey() {
        return lo3Waarde;
    }

    /**
     * Geef de waarde van value.
     * @return de brp waarde
     */
    @Override
    public B getValue() {
        return brpWaarde;
    }

    @Override
    public B setValue(final B value) {
        throw new UnsupportedOperationException("Deze methode wordt niet ondersteund.");
    }

}
