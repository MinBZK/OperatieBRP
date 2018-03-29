/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;

/**
 * Util class voor sortering.
 */
public final class SortUtil {
    private SortUtil() {

    }

    /**
     * Vergelijkt twee objecten met elkaar en houdt er rekening mee dat het object null kan zijn. Als o1 null is en o2 niet, dan wordt deze als kleiner terug
     * gegeven. M.a.w. een gevul object wordt boven een null object gesorteerd.
     * @param o1 object 1, mag null zijn
     * @param o2 object 2, mag null zijn
     * @param <T> Object moet een {@link Comparable} zijn.
     * @return 0 als de objecten gelijk zijn, anders -1 indien o1 kleiner is dan o2 of o1 null is, of +1 als o1 groter is dan o2 of o2 null is.
     */
    public static <T extends Comparable<T>> int compareNulls(final T o1, final T o2) {
        if (o1 == null) {
            return o2 == null ? 0 : -1;
        } else {
            return o2 == null ? 1 : o1.compareTo(o2);
        }
    }

    /**
     * Vergelijkt 2 {@link BrpDatum} objecten met elkaar. Deze methode houdt er rekening mee als 1 van beide, of allebei de datums null zijn.
     * @param o1 een {@link BrpDatum}
     * @param o2 een andere {@link BrpDatum}
     * @return 0 als beide datums gelijk zijn (qua waarde of allebei null), of een waarde die aangeeft dat de o1 groter dan wel kleiner is dan o2.
     */
    public static int vergelijkDatums(final BrpDatum o1, final BrpDatum o2) {
        final int resultaat;

        if (o1 == null) {
            if (o2 == null) {
                resultaat = 0;
            } else {
                resultaat = 1;
            }
        } else {
            if (o2 == null) {
                resultaat = -1;
            } else {
                resultaat = o1.getWaarde().compareTo(o2.getWaarde());
            }
        }

        return resultaat;
    }
}
