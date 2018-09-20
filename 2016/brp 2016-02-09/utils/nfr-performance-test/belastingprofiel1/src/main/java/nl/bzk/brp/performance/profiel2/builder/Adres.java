/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.performance.profiel2.builder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Adres implements SqlAppender {

    private static final Integer[] GEMEENTE_IDS;

    static {
        final URL url = Adres.class.getResource("/gemeenten.txt");
        final List<Integer> codeLijst = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                codeLijst.add(Integer.parseInt(line.split("\\|")[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Collections.shuffle(codeLijst);
        GEMEENTE_IDS = codeLijst.toArray(new Integer[codeLijst.size()]);
    }

    final Persoon         persoon;
    final PersoonHisAdres adressen[];

    public Adres(final Persoon persoon, int aantalHisAdressen) {
        this.persoon = persoon;

        adressen = new PersoonHisAdres[aantalHisAdressen];
        for (int i = 0; i < aantalHisAdressen; i++) {
            adressen[i] = new PersoonHisAdres();

            //bepaal handeling
            AdministratieveHandeling vorigeHandeling;
            if (i == 0) {
                vorigeHandeling = persoon.handeling;
            } else {
                vorigeHandeling = adressen[i - 1].handeling;
            }
            adressen[i].handeling = vorigeHandeling.maakNieuweHandeling();

            //bepaal gemeente
            int nieuweGemeente = 0;
            while ((i == 0 && nieuweGemeente == 0) || (i > 0 && (nieuweGemeente == 0 || nieuweGemeente == adressen[i - 1].gemeente))) {
                nieuweGemeente = GEMEENTE_IDS[(int) ((Math.random() * GEMEENTE_IDS.length))];
            }
            adressen[i].gemeente = nieuweGemeente;
        }
    }


    public void schrijf() throws IOException {
        //schrijf persadres
        final long persAdresId = Globals.ADRES_ID.incrementAndGet();
        Globals.WRITERS.get().persadres.write(String
            .format("insert into kern.persadres (id, pers, srt, rdnwijz, gem) values (%d, %d , 1, 3, %d);%n", persAdresId,
                persoon.id, adressen[adressen.length - 1].gemeente));

        //schrijf his_persadres

        for (final PersoonHisAdres adres : adressen) {
            long hisAdresId = Globals.HIS_ADRES_ID.incrementAndGet();
            //adres.handeling.schrijf();

            Globals.WRITERS.get().his_persadres.write(
                String.format("insert into kern.his_persadres (id, persadres, dataanvgel, tsreg, tsverval, actieinh, "
                        + "actieverval, srt, rdnwijz, aangadresh, dataanvadresh, gem, landgebied) values (%d, %d, '20011001', %s, %s, %d, %d, 1, 1, 3, 20041001, %d, 229);%n",
                    hisAdresId,
                    persAdresId,
                    adres.getTsReg(), adres.getTsVerval(), adres.getActieInhoud(), adres.getActieVerval(), adres.gemeente));

            //adres.handeling.schrijfAfgeleidAdministratief();

        }
    }


    private static class PersoonHisAdres {

        AdministratieveHandeling handeling;
        int                      gemeente;


        String dataanvgel;

        String getTsReg() {
            return handeling.getTsReg();
        }

        String getTsVerval() {
            return handeling.volgendeHandeling != null ? handeling.volgendeHandeling.getTsReg() : null;
        }

        Long getActieInhoud() {
            return handeling.actie.id;
        }

        Long getActieVerval() {
            return handeling.volgendeHandeling != null ? handeling.volgendeHandeling.actie.id : null;
        }

    }
}
