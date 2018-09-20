/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import java.util.SortedSet;
import java.util.TreeSet;


/**
 * Speciale Gesorteerde Set implementatie zodat met JSON sets op de juiste manier kunnen worden ge-deserialiseeerd.
 *
 * @param <T> type van de 'model identificeerbare' elementen in deze set
 */
public class GesorteerdeSetOpDatabaseId<T extends ModelIdentificeerbaar> extends TreeSet<T> implements SortedSet<T> {

    /**
     * Constructor voor deze set, zet de comparator.
     */
    public GesorteerdeSetOpDatabaseId() {
        super(new IdComparator());
    }
}
