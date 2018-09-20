/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.nfr.loadgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Blobt alle te leveren handelingen.
 */
public class BlobifierMain {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String DBHOST = "dbhost";
    private static final String DBNAME = "dbname";
    private static final int TIEN = 10;

    /**
     * Main methode.
     *
     * @param args argumenten
     * @throws InterruptedException interrupted exception
     */
    public static void main(final String[] args) throws InterruptedException {

        final ApplicationContext applicationContext = maakContext();
        final LoadGeneratorService service = applicationContext.getBean(LoadGeneratorService.class);
        final List<Handeling> handelingen = service.geefTeVerwerkenHandelingen();
        LOGGER.info("Totaal onverwerkte handelingen: " + handelingen.size());

        service.maakBlob(handelingen.iterator().next().getPersoonId());

        final int aantalBlobThreads = Integer.parseInt(getPropertyNotNull("aantalBlobThreads", "8"));
        LOGGER.info("Aantal Blobifier Threads: " + aantalBlobThreads);

        final Queue<Handeling> handelingQueue = new ConcurrentLinkedDeque<>();

        final List<Blobifier> blobifiers = new ArrayList<>();
        final int partitie = handelingen.size() / aantalBlobThreads;
        for (int i = 0; i < handelingen.size(); i += partitie) {
            blobifiers.add(new Blobifier(service, handelingQueue, handelingen.subList(i,
                Math.min(i + partitie, handelingen.size()))));
        }

        final ExecutorService executors = Executors.newFixedThreadPool(aantalBlobThreads);
        for (final Blobifier blobifier : blobifiers) {
            executors.submit(blobifier);
        }
        executors.shutdown();

        while (!executors.awaitTermination(TIEN, TimeUnit.SECONDS)) {
            int totaalAantalGeblobd = 0;
            for (final Blobifier blobifier : blobifiers) {
                totaalAantalGeblobd += blobifier.aantalGeblobd;
            }
            LOGGER.info(String.format("Voortgang [%d/%d]", totaalAantalGeblobd, handelingen.size()));
        }
        LOGGER.info("Klaar!");
    }


    /**
     * Maak application context.
     *
     * @return application context
     */
    private static ApplicationContext maakContext() {

        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
        final PropertyPlaceholderConfigurer propConfig = new PropertyPlaceholderConfigurer();
        final Properties properties = new Properties();
        properties.put(DBHOST, getPropertyNotNull(DBHOST, "localhost"));
        properties.put(DBNAME, getPropertyNotNull(DBNAME, "nfr_profiel1"));
        propConfig.setProperties(properties);

        applicationContext.addBeanFactoryPostProcessor(propConfig);
        applicationContext.setConfigLocation("classpath:/spring-context.xml");
        applicationContext.refresh();

        return applicationContext;
    }

    /**
     * Geeft de property als deze niet null is.
     *
     * @param property     property
     * @param defaultValue default value
     * @return de property als deze niet null is, anders wordt er een IllegalStateException gegooid.
     */
    private static String getPropertyNotNull(final String property, final String defaultValue) {
        final String value = System.getProperty(property, defaultValue);
        if (StringUtils.isEmpty(value)) {
            throw new IllegalStateException("Property niet gevonden: " + property);
        }
        return value;
    }

    /**
     * Blobifier.
     */
    private static class Blobifier implements Runnable {

        private final LoadGeneratorService service;
        private final List<Handeling>      handelingen;
        private final Queue<Handeling>     handelingQueue;
        private       int                  aantalGeblobd;

        private Blobifier(final LoadGeneratorService service, final Queue<Handeling> handelingQueue, final List<Handeling> handelingen) {
            this.service = service;
            this.handelingen = handelingen;
            this.handelingQueue = handelingQueue;
        }

        @Override
        public void run() {
            LOGGER.info("Blobifier gestart met subset aan handelingen : " + handelingen.size());
            for (final Handeling handeling : handelingen) {
                service.maakBlob(handeling.getPersoonId());
                aantalGeblobd++;
                handelingQueue.add(handeling);
            }
        }
    }
}
