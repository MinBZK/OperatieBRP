/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal.leveringautorisatie;

import java.io.IOException;
import java.io.Writer;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 */
public final class BijhoudingautorisatieSoortAdministratieveHandelingDsl implements SQLWriter {
    static final         String        SECTIE = "Bijhoudingsautorisatie Soort Administratieve Handeling";
    private static final AtomicInteger ID_GEN = new AtomicInteger();

    private final int id = ID_GEN.incrementAndGet();

    private final Integer                         srtadmhnd;
    private       ToegangBijhoudingautorisatieDsl toegangBijhoudingsautorisatieDsl;


    public BijhoudingautorisatieSoortAdministratieveHandelingDsl(final DslSectie sectie) {
        srtadmhnd = sectie.geefInteger("srtadmhnd");
    }

    @Override
    public void toSQL(final Writer writer) throws IOException {
        writer.write(
            String.format("insert into autaut.bijhautorisatiesrtadmhnd (id, toegangbijhautorisatie, srtadmhnd) "
                    + "values (%d, %d, %d);",
                id, toegangBijhoudingsautorisatieDsl.getId(), srtadmhnd));
    }

    /**
     * Parsed de bijhoudingsoortadministratievehandeling dsl
     * @param toegangBijhoudingautorisatieDsl de dsl
     * @param sectieIterator de secties
     * @return
     */
    public static BijhoudingautorisatieSoortAdministratieveHandelingDsl parse(final ToegangBijhoudingautorisatieDsl toegangBijhoudingautorisatieDsl,
        final ListIterator<DslSectie> sectieIterator)
    {
        final DslSectie sectie = sectieIterator.next();
        sectie.assertMetNaam(SECTIE);
        final BijhoudingautorisatieSoortAdministratieveHandelingDsl bijhoudingautorisatieSoortAdministratieveHandelingDsl
            = new BijhoudingautorisatieSoortAdministratieveHandelingDsl(sectie);
        bijhoudingautorisatieSoortAdministratieveHandelingDsl.toegangBijhoudingsautorisatieDsl = toegangBijhoudingautorisatieDsl;
        return bijhoudingautorisatieSoortAdministratieveHandelingDsl;
    }
}
