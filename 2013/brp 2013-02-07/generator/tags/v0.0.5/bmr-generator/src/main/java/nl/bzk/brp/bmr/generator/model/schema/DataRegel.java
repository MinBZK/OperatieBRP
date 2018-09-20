/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.schema;

import java.util.LinkedHashMap;
import java.util.Map;


public class DataRegel {

    private Tabel        tabel;

    private final Map<Kolom, CharSequence> waarden = new LinkedHashMap<Kolom, CharSequence>();


    public DataRegel(final Tabel tabel) {
        this.tabel = tabel;

    }

    public Tabel getTabel() {
        return tabel;
    }


    public void setTabel(final Tabel tabel) {
        this.tabel = tabel;
    }


    public Map<Kolom, CharSequence> getWaarden() {
        return waarden;
    }

    public void addWaarden(final Kolom kolom, final String waarde) {
        waarden.put(kolom, waarde);
    }
}
