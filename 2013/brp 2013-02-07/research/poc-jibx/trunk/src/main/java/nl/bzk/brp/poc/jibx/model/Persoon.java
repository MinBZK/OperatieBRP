/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.jibx.model;

import java.util.*;

/**
 * Persoon model class.
 */
public class Persoon {
    
    private Long bsn;
    private SortedSet<SamengesteldeNaam> namen;

    
    public Persoon() {
        namen = new TreeSet<SamengesteldeNaam>(new Comparator<SamengesteldeNaam>() {
            @Override
            public int compare(SamengesteldeNaam samengesteldeNaam1, SamengesteldeNaam samengesteldeNaam2) {
                return samengesteldeNaam1.getAchterNaam().compareTo(samengesteldeNaam2.getAchterNaam());
            }
        });
    }
    
    public Persoon(final Long bsn, final SamengesteldeNaam naam) {
        this();
        this.bsn = bsn;
        namen.add(naam);
    }

    public static Set<SamengesteldeNaam> setFactory() {
        return new TreeSet<SamengesteldeNaam>(new Comparator<SamengesteldeNaam>() {
            @Override
            public int compare(SamengesteldeNaam samengesteldeNaam1, SamengesteldeNaam samengesteldeNaam2) {
                return samengesteldeNaam1.getAchterNaam().compareTo(samengesteldeNaam2.getAchterNaam());
            }
        });
    }

    public Long getBsn() {
        return bsn;
    }

    public void setBsn(Long bsn) {
        this.bsn = bsn;
    }

    public Set<SamengesteldeNaam> getNamen() {
        return namen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Persoon)) {
            return false;
        }

        Persoon persoon = (Persoon) o;

        if (bsn != null ? !bsn.equals(persoon.bsn) : persoon.bsn != null) {
            return false;
        }
        if (namen != null ? !namen.equals(persoon.namen) : persoon.namen != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = bsn != null ? bsn.hashCode() : 0;
        result = 31 * result + (namen != null ? namen.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Persoon{" +
                "bsn=" + bsn +
                ", namen=" + namen +
                '}';
    }

    private class AchternaamComperator implements Comparator<SamengesteldeNaam> {
        @Override
        public int compare(SamengesteldeNaam samengesteldeNaam, SamengesteldeNaam samengesteldeNaam1) {
            return samengesteldeNaam.getAchterNaam().compareTo(samengesteldeNaam1.getAchterNaam());
        }
    }
}
