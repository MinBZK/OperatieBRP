/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal.leveringautorisatie;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 */
public final class ToegangBijhoudingautorisatieDsl extends AbstractToegangautorisatieDsl implements SQLWriter {

    static final         String        SECTIE = "Toegang Bijhouding autorisatie";
    private static final AtomicInteger ID_GEN = new AtomicInteger();

    private final int id = ID_GEN.incrementAndGet();
    private final List<BijhoudingautorisatieSoortAdministratieveHandelingDsl> administratieveHandelingSoorten = new ArrayList<>();

    public ToegangBijhoudingautorisatieDsl(final DslSectie sectie) {
        super(sectie);

    }

    @Override
    public void toSQL(final Writer writer) throws IOException {
        writer.write(
            String.format("insert into autaut.toegangbijhautorisatie (id, geautoriseerde, " +
                    "ondertekenaar, transporteur, datingang, dateinde, indblok) "
                    + "values (%d, (select id from kern.partijrol where partij = (select id from kern.partij where naam = %s) and rol=2), "
                    + "(select id from kern.partij where oin = %s), "
                    + "(select id from kern.partij where oin = %s), %d, %d, %s);%n",
                id, DslSectie.quote(getGeautoriseerde()), DslSectie.quote(getOndertekenaar()), DslSectie.quote(getTransporteur()), getDatingang(), getDateinde(),
                isIndblok()));
        for (BijhoudingautorisatieSoortAdministratieveHandelingDsl bijhoudingautorisatieSoortAdministratieveHandelingDsl : administratieveHandelingSoorten) {
            bijhoudingautorisatieSoortAdministratieveHandelingDsl.toSQL(writer);
        }
    }

    @Override
    public final int getId() {
        return id;
    }

    public static ToegangBijhoudingautorisatieDsl parse(final ListIterator<DslSectie> regelIterator) {
        final DslSectie sectie = regelIterator.next();
        sectie.assertMetNaam(SECTIE);
        final ToegangBijhoudingautorisatieDsl toegangBijhoudingautorisatieDsl = new ToegangBijhoudingautorisatieDsl(sectie);
        while (regelIterator.hasNext()) {
            final DslSectie next = regelIterator.next();
            next.assertMetNaam(BijhoudingautorisatieSoortAdministratieveHandelingDsl.SECTIE);
            regelIterator.previous();
            final BijhoudingautorisatieSoortAdministratieveHandelingDsl bijhoudingautorisatieSoortAdministratieveHandelingDsl
                = BijhoudingautorisatieSoortAdministratieveHandelingDsl.parse(toegangBijhoudingautorisatieDsl, regelIterator);
            toegangBijhoudingautorisatieDsl.administratieveHandelingSoorten.add(bijhoudingautorisatieSoortAdministratieveHandelingDsl);
        }
        return toegangBijhoudingautorisatieDsl;
    }

}
