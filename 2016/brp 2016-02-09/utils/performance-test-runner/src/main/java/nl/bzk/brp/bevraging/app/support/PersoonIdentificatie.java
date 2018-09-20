/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.app.support;

import java.util.Map;

/**
 * PersoonIdentificatie bevat de gebruikte identificerende elementen van een Persoon: id en BSN.
 */
public class PersoonIdentificatie {

    private final Integer id;
    private final Integer bsn;

    /**
     * Constructor.
     * @param id de persoon id
     * @param bsn de persoon BSN
     */
    public PersoonIdentificatie(final Integer id, final Integer bsn) {
        this.bsn = bsn;
        this.id = id;
    }

    /**
     * Constructor.
     * @param data map die id en bsn, keys bevat
     */
    public PersoonIdentificatie(final Map<String, Object> data) {
        this.id = (Integer) data.get("id");
        this.bsn = (Integer) data.get("bsn");
    }

    public Integer getId() {
        return id;
    }

    public Integer getBsn() {
        return bsn;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final PersoonIdentificatie that = (PersoonIdentificatie) o;

        if (!bsn.equals(that.bsn)) {
            return false;
        }
        if (!id.equals(that.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + bsn.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "" + getBsn();
    }
}
