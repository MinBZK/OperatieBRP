/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.performance.profiel2.builder;

import java.io.IOException;
import java.io.Writer;

/**
 */
public class Persoon implements SqlAppender {

    long id  = Globals.PERSOON_ID.incrementAndGet();
    long bsn = Globals.BSN.incrementAndGet();

    public AdministratieveHandeling handeling;

    public Persoon() {
        handeling = new AdministratieveHandeling(this);
        Globals.HANDELINGEN.set(handeling);
    }

    @Override
    public void schrijf() throws IOException {
        Writer writer = Globals.WRITERS.get().pers;
        writer.write(String.format(
            "insert into kern.pers (id, srt, admhnd, tslaatstewijz, sorteervolgorde, indonverwbijhvoorstelnieting, bsn, anr, "
                + "bijhpartij, bijhaard, naderebijhaard) values (%d, 1, %d, date '2001-10-01', 1, 'f', %d, %d, 1, 1, 1);%n", id,
            handeling.id, bsn, bsn));
    }

    @Override
    public String toString() {
        return "Persoon{" +
            "id=" + id +
            '}';
    }
}
