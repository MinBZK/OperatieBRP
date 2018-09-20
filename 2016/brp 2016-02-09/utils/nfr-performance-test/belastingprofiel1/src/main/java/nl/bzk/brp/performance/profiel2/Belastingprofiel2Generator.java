/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.performance.profiel2;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import nl.bzk.brp.performance.profiel2.builder.AdministratieveHandeling;
import nl.bzk.brp.performance.profiel2.builder.Adres;
import nl.bzk.brp.performance.profiel2.builder.Afnemerindicatie;
import nl.bzk.brp.performance.profiel2.builder.Betrokkenheid;
import nl.bzk.brp.performance.profiel2.builder.Globals;
import nl.bzk.brp.performance.profiel2.builder.Persoon;
import nl.bzk.brp.performance.profiel2.builder.Writers;

/**
 * truncate autaut.abonnement cascade;
 * <p/>
 * truncate kern.pers cascade; truncate kern.relatie cascade; delete from kern.admhnd; delete from kern.actie;
 */
public class Belastingprofiel2Generator {

    public static void main(String[] args) throws IOException, InterruptedException {
        new Belastingprofiel2Generator().start();
    }

    final static int aantalPersonen = (int) 1e3;
    final static int aantalThreads  = Runtime.getRuntime().availableProcessors();
    final static int aantalAdressen = 5;
    final static int aantalAfnemerindicaties = 10;

    private void start() throws IOException {

        long start = System.currentTimeMillis();
        final SqlMaker[] makers = maakSql();
        final long sqlGeneratieTijd = System.currentTimeMillis() - start;
        start = System.currentTimeMillis();
        joinSql(makers);
        final long sqlMergeTijd = System.currentTimeMillis() - start;
        System.out.println("SQL generatie tijd = " + sqlGeneratieTijd);
        System.out.println("SQL merge tijd = " + sqlMergeTijd);
    }

    private SqlMaker[] maakSql() throws IOException {
        final ExecutorService executorService = Executors.newFixedThreadPool(aantalThreads + 1);

        //reporter
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while (Globals.PERSOON_ID.get() < aantalPersonen) {
                    System.out.println("voortgang = " + Globals.PERSOON_ID.get());
                    //}
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });

        final SqlMaker[] threads = new SqlMaker[aantalThreads];
        for (int i = 0; i < aantalThreads; i++) {
            threads[i] = new SqlMaker();
            executorService.submit(threads[i]);
        }
        executorService.shutdown();
        try {
            while (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
//                System.out.println("nog niet klaar...");
            }
        } catch (InterruptedException e) {
            //
        }

        return threads;


    }

    private void joinSql(final SqlMaker[] threads) throws IOException {

        System.out.println("Start joinen SQL files...");


        final List<Writers> writersList = new LinkedList<>();
        for (SqlMaker maker : threads) {
            writersList.add(maker.writers);
        }
        Writers.mergeWriters(writersList.toArray(new Writers[writersList.size()]));
    }



    static class SqlMaker implements Runnable {

        final Writers writers;

        SqlMaker() throws IOException {
            writers = new Writers();

        }

        @Override
        public void run() {
            System.out.println("Start generator thread");
            Globals.WRITERS.set(writers);
            try {
                while (Globals.PERSOON_ID.get() < aantalPersonen) {
                    maakPersoon();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }

            try {
                writers.close();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            System.out.println("Stop generator thread");
        }

        private void maakPersoon() throws IOException {
            final Persoon persoon = new Persoon();
            final Adres adres = new Adres(persoon, aantalAdressen);
            final Betrokkenheid betrokkenheid = new Betrokkenheid(persoon);
            final Afnemerindicatie afnemerindicatie = new Afnemerindicatie(persoon, aantalAfnemerindicaties);

            //schrijf eerst alle handelingen
            AdministratieveHandeling handeling = persoon.handeling;
            while (handeling != null) {
                handeling.schrijf();
                handeling = handeling.volgendeHandeling;
            }

            persoon.schrijf();
            adres.schrijf();
            betrokkenheid.schrijf();
            afnemerindicatie.schrijf();

            //schrijf eerst alle afgeleid afministratief
            handeling = persoon.handeling;
            while (handeling != null) {
                handeling.schrijfAfgeleidAdministratief();
                handeling = handeling.volgendeHandeling;
            }
        }
    }
}
