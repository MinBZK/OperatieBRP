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

/**
 */
public class Afnemerindicatie implements SqlAppender {

    private static final Integer[] PARTIJ_IDS;

    static {
        final URL url = Adres.class.getResource("/partijen.txt");
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
        PARTIJ_IDS = codeLijst.toArray(new Integer[codeLijst.size()]);
    }

    private final Persoon     persoon;
    private final Indicatie[] indicaties;


    public Afnemerindicatie(final Persoon persoon, final int aantalTePlaatsenIndicaties) {
        this.persoon = persoon;

        indicaties = new Indicatie[aantalTePlaatsenIndicaties];
        for (int i = 0; i < indicaties.length; i++) {
            int afnemer = 0;
            while ((i == 0 && afnemer == 0) || (i > 0 && (afnemer == 0 || afnemer == indicaties[i - 1].partijId))) {
                afnemer = PARTIJ_IDS[(int) ((Math.random() * PARTIJ_IDS.length))];
            }
            indicaties[i] = new Indicatie();
            indicaties[i].partijId = afnemer;
            indicaties[i].abonnementId = (int) (Math.random() * 1000);
        }
    }

    @Override
    public void schrijf() throws IOException {

        for (Indicatie indicatie : indicaties) {

            Globals.WRITERS.get().persafnemerindicatie.write(String
                .format("insert into autaut.persafnemerindicatie (id, pers, afnemer, abonnement) values (%d, %d, %d, %d);%n", indicatie.afnemerindicatieId,
                    persoon.id, indicatie.partijId, indicatie.abonnementId));

            Globals.WRITERS.get().his_persafnemerindicatie.write(String
                .format("insert into autaut.his_persafnemerindicatie (id, persafnemerindicatie, tsreg) values (%d, %d, now());%n",
                    indicatie.hisAfnemerindicatieId, indicatie.afnemerindicatieId));
        }


    }


    static class Indicatie {
        final long afnemerindicatieId = Globals.AFNEMERINDICATIE_ID.incrementAndGet();
        final long hisAfnemerindicatieId = Globals.HIS_AFNEMERINDICATIE_ID.incrementAndGet();
        int partijId;
        int abonnementId;

    }
}
