/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.afnemersindicatie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.brp.util.BrpStapelSorter;

/**
 * Sorteren.
 */
public final class BrpAfnemersindicatiesSorter {

    private BrpAfnemersindicatiesSorter() {
    }

    /**
     * Sorteert de afnemersindiciaties.
     *
     * @param afnemersindicaties
     *            De te sorteren afnemerindicaties.
     * @return De gesorteerde afnemersindicaties.
     */
    public static BrpAfnemersindicaties sorteer(final BrpAfnemersindicaties afnemersindicaties) {
        if (afnemersindicaties == null) {
            return null;
        }

        return new BrpAfnemersindicaties(
            afnemersindicaties.getAdministratienummer(),
            BrpAfnemersindicatiesSorter.sorteerAfnemersindicaties(afnemersindicaties.getAfnemersindicaties()));
    }

    private static List<BrpAfnemersindicatie> sorteerAfnemersindicaties(final List<BrpAfnemersindicatie> afnemersindicaties) {
        if (afnemersindicaties == null) {
            return null;
        }
        final List<BrpAfnemersindicatie> result = new ArrayList<>();

        for (final BrpAfnemersindicatie afnemersindicatie : afnemersindicaties) {
            result.add(BrpAfnemersindicatiesSorter.sorteerAfnemersindicatie(afnemersindicatie));
        }

        Collections.sort(result, new BrpAfnemersindicatieSorter());
        return result;
    }

    private static BrpAfnemersindicatie sorteerAfnemersindicatie(final BrpAfnemersindicatie afnemersindicatie) {
        if (afnemersindicatie == null) {
            return null;
        }
        return new BrpAfnemersindicatie(
            afnemersindicatie.getPartijCode(),
            BrpStapelSorter.sorteerStapel(afnemersindicatie.getAfnemersindicatieStapel()),
            null // sorteerPartij(afnemersindicatie.getPartij())
        );
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Sorteer BrpAfnemersindicatie.
     */
    public static final class BrpAfnemersindicatieSorter implements Comparator<BrpAfnemersindicatie>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpAfnemersindicatie o1, final BrpAfnemersindicatie o2) {
            final Integer partijCode1 = o1.getPartijCode().getWaarde();
            final Integer partijCode2 = o2.getPartijCode().getWaarde();
            return partijCode1.compareTo(partijCode2);
        }
    }

}
