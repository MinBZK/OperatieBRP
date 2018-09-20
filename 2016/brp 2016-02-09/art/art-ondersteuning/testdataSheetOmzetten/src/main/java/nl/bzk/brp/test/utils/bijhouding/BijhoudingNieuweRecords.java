/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils.bijhouding;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class BijhoudingNieuweRecords {

    private SortedMap<Integer, SortedSet<HisPersoonBijhouding>> persoonBijhoudingen;

    public BijhoudingNieuweRecords() {
        persoonBijhoudingen = new TreeMap<>();
    }

    public SortedSet<Integer> getPersoonIds() {
        return new TreeSet<Integer>(this.persoonBijhoudingen.keySet());
    }

    public SortedMap<Integer, SortedSet<HisPersoonBijhouding>> getPersoonBijhouding() {
        return persoonBijhoudingen;
    }

    public Set<HisPersoonBijhouding> getAllePersoonBijhoudingen() {
        Set<HisPersoonBijhouding> alleBijhoudingen = new HashSet<>();
        for (Set<HisPersoonBijhouding> bijhouding : persoonBijhoudingen.values()) {
            alleBijhoudingen.addAll(bijhouding);
        }
        return alleBijhoudingen;
    }

    public SortedSet<HisPersoonBijhouding> getPersoonBijhoudingen(final int persoonId) {
        return persoonBijhoudingen.get(persoonId);
    }

    public void voegBijhoudingToe(final HisPersoonBijhouding hisPersoonBijhouding) {
        Integer persoonId = hisPersoonBijhouding.getPersoon();
        SortedSet<HisPersoonBijhouding> bijhoudingen = persoonBijhoudingen.get(persoonId);
        if (bijhoudingen == null) {
            bijhoudingen = new TreeSet<>();
            persoonBijhoudingen.put(persoonId, bijhoudingen);
        }
        bijhoudingen.add(hisPersoonBijhouding);
    }

}
