/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.ConversieMapEntry;

/**
 * Lijst converter.
 *
 * @param <C> Conversietabel regel type
 * @param <L> LO3 waarde type
 * @param <B> BRP waarde type
 */
public abstract class AbstractLijstConverter<C, L, B> {

    /**
     * Converteer een conversietabel naar een conversie map.
     *
     * @param lijst conversietabel
     * @return conversie map
     */
    public final List<Map.Entry<L, B>> converteer(final Collection<C> lijst) {
        final List<Map.Entry<L, B>> resultaat = new ArrayList<>();
        for (final C regel : lijst) {
            resultaat.add(new ConversieMapEntry<L, B>(maakLo3Waarde(regel), maakBrpWaarde(regel)));
        }
        toevoegenStatischeVertalingen(resultaat);
        return resultaat;
    }

    /**
     * Maak de LO3 waarde voor de conversie map.
     *
     * @param regel conversietabel regel
     * @return LO3 waarde
     */
    protected abstract L maakLo3Waarde(final C regel);

    /**
     * Maak de BRP waarde voor de conversie map.
     *
     * @param regel conversietabel regel
     * @return LO3 waarde
     */
    protected abstract B maakBrpWaarde(final C regel);

    /**
     * Hook om statische vertalingen toe te voegen.
     *
     * @param resultaat conversie map
     */
    protected void toevoegenStatischeVertalingen(final List<Map.Entry<L, B>> resultaat) {
    }
}
