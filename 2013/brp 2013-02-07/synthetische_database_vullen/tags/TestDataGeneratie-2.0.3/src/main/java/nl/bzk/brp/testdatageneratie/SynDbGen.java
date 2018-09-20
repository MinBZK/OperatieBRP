/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import nl.bzk.brp.testdatageneratie.datagenerators.DocGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.OnderzoekGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.RelatieHistorieGenerator;
import org.apache.log4j.Logger;
import org.hibernate.Session;


public class SynDbGen {

    private static final String                    PROPERTIES_FILE = "syndbgen.properties";

    private static Logger                          log             = Logger.getLogger(SynDbGen.class);

    private static final Properties                properties      = new Properties();

    private static HibernateSessionFactoryProvider hibernateSessionFactoryProvider;

    private static ExecutorService                 executor;

    private static List<Callable<Boolean>>         taskList;

    public static void main(final String[] args) {
        try {
            doit();
        } catch (Exception e) {
            log.error("", e);
        } finally {
            try {
                if (executor != null) {
                    executor.shutdown();
                }
            } catch (RuntimeException e) {
                log.error("", e);
            } finally {
                try {
                    if (hibernateSessionFactoryProvider != null) {
                        hibernateSessionFactoryProvider.close();
                    }
                } catch (RuntimeException e) {
                    log.error("", e);
                }
            }
        }
    }

    private static void doit() throws Exception {
        properties.load(ClassLoader.getSystemResourceAsStream(PROPERTIES_FILE));
        final int personenPerThread = getProperty("personenPerThread");
        final int batchBlockSize = getProperty("batchBlockSize");
        final int[] ontbrekendePersoonIdBlokken = getIntArray("ontbrekendePersoonIdBlokken");
        final int rangeSize = getProperty("rangeSize");// 25000
        final int threadBlockSize = getProperty("threadBlockSize");// 10000000
        final int numberOfThreads = getProperty("numberOfThreads");
        final int aantalAfgebrokenThreads = getProperty("aantalAfgebrokenThreads");

        Huwelijksboot[] huwelijksboten = new Huwelijksboot[numberOfThreads];

        hibernateSessionFactoryProvider = new HibernateSessionFactoryProvider(properties);
        HibernateSessionFactoryProvider.setInstance(hibernateSessionFactoryProvider);

        executor = Executors.newFixedThreadPool(numberOfThreads + 2);
        taskList = new ArrayList<Callable<Boolean>>();

        truncateTables();

        BronnenRepo.initIfNeeded();
        MetaRepo.initIfNeeded();
        int aantalPersonen = personenPerThread * numberOfThreads;

        int actiesTotaal = 0;
        for (int i = 0; i < numberOfThreads; i++) {
            int actiesPerThread = personenPerThread;
            taskList.add(new ActieGenerator(actiesPerThread, batchBlockSize));
            actiesTotaal += actiesPerThread;
        }
        genereer(actiesTotaal);
        ActieIds actieIds = new ActieIds();

        int cumulatiefAantalThreads = aantalAfgebrokenThreads + numberOfThreads;
        for (int i = aantalAfgebrokenThreads, j = 0; i < cumulatiefAantalThreads; i++, j++) {
            huwelijksboten[j] = new Huwelijksboot(personenPerThread);
            taskList.add(new TestDataGenerator(personenPerThread, batchBlockSize, i, rangeSize, threadBlockSize,
                    huwelijksboten[j]));
        }
        genereer(aantalPersonen);

        PersoonIds persoonIds = new PersoonIds(batchBlockSize, ontbrekendePersoonIdBlokken);

        int hisRelatiesTotaal = 0;
        for (int i = 0; i < numberOfThreads; i++) {
            int hisRelatiesPerThread = personenPerThread / 10;
            taskList.add(new RelatieHistorieGenerator(persoonIds, hisRelatiesPerThread, batchBlockSize));
            hisRelatiesTotaal += hisRelatiesPerThread;
        }
        genereer(hisRelatiesTotaal);

        for (int i = 1; i <= numberOfThreads; i++) {
            taskList.add(new FamilieGenerator(persoonIds, i, numberOfThreads));
        }
        genereer(aantalPersonen);

        int huwelijkenTotaal = 0;
        for (int i = 0; i < numberOfThreads; i++) {
            taskList.add(new RelatieGenerator(persoonIds, huwelijksboten[i]));
            huwelijkenTotaal += huwelijksboten[i].getAantalHuwelijken();
        }
        genereer(huwelijkenTotaal);

        int docsTotaal = 0;
        for (int i = 0; i < numberOfThreads; i++) {
            int docsPerThread = personenPerThread * 2;
            taskList.add(new DocGenerator(docsPerThread, batchBlockSize, actieIds));
            docsTotaal += docsPerThread;
        }
        genereer(docsTotaal);

        int onderzoekenTotaal = 0;
        for (int i = 0; i < numberOfThreads; i++) {
            int onderzoekenPerThread = personenPerThread / 500;
            taskList.add(new OnderzoekGenerator(onderzoekenPerThread, batchBlockSize, persoonIds));
            onderzoekenTotaal += onderzoekenPerThread;
        }
        genereer(onderzoekenTotaal);

        log.info("---------------------- Einde --------------------------");
    }

    private static void truncateTables() {
        Session kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
        kernSession.beginTransaction();
        kernSession.createSQLQuery(
                "truncate kern.actie cascade;\n" +
                "truncate kern.pers cascade;\n" +
                "truncate kern.relatie cascade;").executeUpdate();
        kernSession.getTransaction().commit();
        kernSession.close();
        log.info("Tables truncated");
    }

    public static int getProperty(final String key) {
        String value = properties.getProperty(key);
        return Integer.parseInt(value);
    }

    private static int[] getIntArray(final String key) {
        String value = properties.getProperty(key);
        if (value == null || value.length() == 0) {
            return new int[0];
        }
        StringTokenizer tokenizer = new StringTokenizer(properties.getProperty(key), ",");
        int[] result = new int[tokenizer.countTokens()];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(tokenizer.nextToken().trim());
        }
        return result;
    }

    private static void genereer(final int aantalTeGenereren) throws InterruptedException {
        long now = System.currentTimeMillis();

        List<Future<Boolean>> futures = executor.invokeAll(taskList);
        log.info(aantalTeGenereren + " gegenereerd in " + (System.currentTimeMillis() - now) + " ms");
        for (int i = 0; i < futures.size(); i++) {
            try {
                log.info(i + ": " + futures.get(i).get());
            } catch (Throwable e) {
                log.error(i + ":", e);
                while (e.getCause() != null) {
                    e = e.getCause();
                }
                if (e instanceof SQLException) {
                    SQLException ex = (SQLException) e;
                    while (ex.getNextException() != null) {
                        ex = ex.getNextException();
                        log.error(i + ": Next Exception", ex);
                    }
                }
            }
        }

        taskList.clear();
    }
}
