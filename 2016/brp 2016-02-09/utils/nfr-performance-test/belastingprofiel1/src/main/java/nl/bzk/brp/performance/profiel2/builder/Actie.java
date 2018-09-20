/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.performance.profiel2.builder;

import java.io.IOException;

/**
 */
public class Actie implements SqlAppender {

    long id = Globals.ACTIE_ID.incrementAndGet();

    private final AdministratieveHandeling handeling;

    public Actie(final AdministratieveHandeling handeling) {
        this.handeling = handeling;
    }

    @Override
    public void schrijf() throws IOException {
        Globals.WRITERS.get().actie.write(String
            .format("insert into kern.actie (id, srt, admhnd, partij, tsreg) values (%d, 1, %d, 1, date '2001-10-01');%n", id,
                handeling.id));
    }
}
