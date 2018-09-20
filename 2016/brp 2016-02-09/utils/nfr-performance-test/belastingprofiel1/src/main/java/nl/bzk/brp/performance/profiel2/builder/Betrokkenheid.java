/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.performance.profiel2.builder;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 */
public class Betrokkenheid implements SqlAppender {


    final Persoon persoon;
    List<Relatie> relatieList = new LinkedList<>();

    public Betrokkenheid(final Persoon persoon) {
        this.persoon = persoon;

        final Queue<Persoon> queue = Globals.PERSONEN.get();
        while (queue.size() > 10) {
            queue.remove();
        }

        for (final Persoon anderePersoon : queue) {
            relatieList.add(new Relatie(persoon, anderePersoon));
        }
        queue.add(persoon);
    }

    @Override
    public void schrijf() throws IOException {
        for (Relatie relatie : relatieList) {
            relatie.schrijf();
        }
    }

    static class Relatie implements SqlAppender {

        long                     id                 = Globals.RELATIE_ID.incrementAndGet();
        long                     hisRelatieId       = Globals.HIS_RELATIE_ID.incrementAndGet();
        long                     betrokkenheidId1    = Globals.BETROKKENHEID_ID.incrementAndGet();
        long                     betrokkenheidId2    = Globals.BETROKKENHEID_ID.incrementAndGet();
        long                     hisBetrokkenheidId1 = Globals.HIS_BETROKKENHEID_ID.incrementAndGet();
        long                     hisBetrokkenheidId2 = Globals.HIS_BETROKKENHEID_ID.incrementAndGet();
        AdministratieveHandeling handeling          = Globals.HANDELINGEN.get().maakNieuweHandeling();

        final Persoon p1;
        final Persoon p2;

        Relatie(final Persoon p1, final Persoon p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public void schrijf() throws IOException {

            Globals.WRITERS.get().relatie.write(String.format("insert into kern.relatie (id, srt) values (%d, 3);%n", id));
            Globals.WRITERS.get().his_relatie.write(String.format("insert into kern.his_relatie (id, relatie, tsreg, tsverval, actieinh, actieverval) values (%d, %d, %s, %s, %d, %d);%n", hisRelatieId, id, handeling.getTsReg(), handeling.getTsVerval(), handeling.actie.id, handeling.getActieVerval()));

            Globals.WRITERS.get().betr.write(String.format("insert into kern.betr (id, relatie, rol, pers) values (%d, %d, 1, %d);%n", betrokkenheidId1, id, p1.id));
            Globals.WRITERS.get().betr.write(String.format("insert into kern.betr (id, relatie, rol, pers) values (%d, %d, 1, %d);%n", betrokkenheidId2, id, p2.id));

            Globals.WRITERS.get().his_betr.write(String.format("insert into kern.his_betr (id, betr, tsreg, tsverval, actieinh, actieverval) values (%d, %d, %s, %s, %d, %d);%n", hisBetrokkenheidId1, betrokkenheidId1, handeling.getTsReg(), handeling.getTsVerval(), handeling.getActieInhoud(), handeling.getActieVerval()));
            Globals.WRITERS.get().his_betr.write(String.format("insert into kern.his_betr (id, betr, tsreg, tsverval, actieinh, actieverval) values (%d, %d, %s, %s, %d, %d);%n", hisBetrokkenheidId2, betrokkenheidId2, handeling.getTsReg(), handeling.getTsVerval(), handeling.getActieInhoud(), handeling.getActieVerval()));


        }
    }
}
