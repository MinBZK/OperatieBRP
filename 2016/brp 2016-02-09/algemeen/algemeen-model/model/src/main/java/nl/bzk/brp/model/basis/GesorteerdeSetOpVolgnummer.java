/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import java.util.SortedSet;
import java.util.TreeSet;


/**
 * Subklasse van de {@link TreeSet} klasse waarbij standaard de {@link VolgnummerComparator} als comparator voor de sortering is ingesteld.
 *
 * @param <T> Type van de 'volgnummer bevattende' elementen die in deze set worden bijgehouden.
 */
public class GesorteerdeSetOpVolgnummer<T extends VolgnummerBevattend> extends TreeSet<T> implements SortedSet<T> {

    /**
     * Standaard constructor die de Comparator zet.
     */
    public GesorteerdeSetOpVolgnummer() {
        super(new VolgnummerComparator());
    }
}
