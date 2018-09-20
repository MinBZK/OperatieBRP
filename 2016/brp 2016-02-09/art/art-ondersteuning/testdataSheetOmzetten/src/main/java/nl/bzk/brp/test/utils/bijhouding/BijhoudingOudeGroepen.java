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

public class BijhoudingOudeGroepen {

    private SortedMap<Integer, SortedSet<HisPersoonBijhoudingsaard>> persoonBijhoudingsaarden;
    private SortedMap<Integer, SortedSet<HisPersoonBijhoudingsgemeente>> persoonBijhoudingsgemeenten;
    private SortedMap<Integer, SortedSet<HisPersoonOpschorting>> persoonOpschortingen;

    public BijhoudingOudeGroepen() {
        persoonBijhoudingsaarden = new TreeMap<>();
        persoonBijhoudingsgemeenten = new TreeMap<>();
        persoonOpschortingen = new TreeMap<>();
    }

    public SortedSet<Integer> getPersoonIds() {
        SortedSet<Integer> persoonIds = new TreeSet<>();
        persoonIds.addAll(persoonBijhoudingsaarden.keySet());
        persoonIds.addAll(persoonBijhoudingsgemeenten.keySet());
        persoonIds.addAll(persoonOpschortingen.keySet());
        return persoonIds;
    }

    public SortedMap<Integer, SortedSet<HisPersoonBijhoudingsaard>> getPersoonBijhoudingsaarden() {
        return persoonBijhoudingsaarden;
    }

    public SortedMap<Integer, SortedSet<HisPersoonBijhoudingsgemeente>> getPersoonBijhoudingsgemeenten() {
        return persoonBijhoudingsgemeenten;
    }

    public SortedMap<Integer, SortedSet<HisPersoonOpschorting>> getPersoonOpschortingen() {
        return persoonOpschortingen;
    }

    public Set<HisPersoonRecord> getPersoonRecords(final int persoonId) {
        Set<HisPersoonRecord> persoonRecords = new HashSet<>();
        persoonRecords.addAll(getPersoonBijhoudingsaarden(persoonId));
        persoonRecords.addAll(getPersoonBijhoudingsgemeenten(persoonId));
        persoonRecords.addAll(getPersoonOpschortingen(persoonId));
        return persoonRecords;
    }

    public SortedSet<HisPersoonBijhoudingsaard> getPersoonBijhoudingsaarden(final int persoonId) {
        SortedSet<HisPersoonBijhoudingsaard> result = persoonBijhoudingsaarden.get(persoonId);
        if (result == null) {
            result = new TreeSet<>();
        }
        return result;
    }

    public SortedSet<HisPersoonBijhoudingsgemeente> getPersoonBijhoudingsgemeenten(final int persoonId) {
        SortedSet<HisPersoonBijhoudingsgemeente> result = persoonBijhoudingsgemeenten.get(persoonId);
        if (result == null) {
            result = new TreeSet<>();
        }
        return result;
    }

    public SortedSet<HisPersoonOpschorting> getPersoonOpschortingen(final int persoonId) {
        SortedSet<HisPersoonOpschorting> result = persoonOpschortingen.get(persoonId);
        if (result == null) {
            result = new TreeSet<>();
        }
        return result;
    }

    // Als er geen id gevonden is, is het een ongeldige rij.
    // Tevens: skip vervallen records, die worden handmatig verwerkt.
    private boolean recordVoldoet(final HisPersoonRecord hisPersoonRecord) {
        return hisPersoonRecord.getiD() != null && hisPersoonRecord.getiD() != 0
                && (hisPersoonRecord.getDatumTijdVerval() == null
                    || hisPersoonRecord.getDatumTijdVerval() == 0);
    }

    public void voegBijhoudingsaardToe(final HisPersoonBijhoudingsaard hisPersoonBijhoudingsaard) {
        if (recordVoldoet(hisPersoonBijhoudingsaard)) {
            Integer persoonId = hisPersoonBijhoudingsaard.getPersoon();
            SortedSet<HisPersoonBijhoudingsaard> bijhoudingsaarden = persoonBijhoudingsaarden.get(persoonId);
            if (bijhoudingsaarden == null) {
                bijhoudingsaarden = new TreeSet<>();
                persoonBijhoudingsaarden.put(persoonId, bijhoudingsaarden);
            }
            bijhoudingsaarden.add(hisPersoonBijhoudingsaard);
        }
    }

    public void voegBijhoudinggemeenteToe(final HisPersoonBijhoudingsgemeente hisPersoonBijhoudingsgemeente) {
        if (recordVoldoet(hisPersoonBijhoudingsgemeente)) {
            Integer persoonId = hisPersoonBijhoudingsgemeente.getPersoon();
            SortedSet<HisPersoonBijhoudingsgemeente> bijhoudingsgemeenten = persoonBijhoudingsgemeenten.get(persoonId);
            if (bijhoudingsgemeenten == null) {
                bijhoudingsgemeenten = new TreeSet<>();
                persoonBijhoudingsgemeenten.put(persoonId, bijhoudingsgemeenten);
            }
            bijhoudingsgemeenten.add(hisPersoonBijhoudingsgemeente);
        }
    }

    public void voegOpschortingToe(final HisPersoonOpschorting  hisPersoonOpschorting) {
        if (recordVoldoet(hisPersoonOpschorting)) {
            Integer persoonId = hisPersoonOpschorting.getPersoon();
            SortedSet<HisPersoonOpschorting> opschortingen = persoonOpschortingen.get(persoonId);
            if (opschortingen == null) {
                opschortingen = new TreeSet<>();
                persoonOpschortingen.put(persoonId, opschortingen);
            }
            opschortingen.add(hisPersoonOpschorting);
        }
    }

}
