/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.utils;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.testdatageneratie.datagenerators.AbstractBasicGenerator;

import org.apache.log4j.Logger;

/**
 * Performance logger.
 */
public final class PerformanceLogger implements Runnable {

    private static Logger log = Logger.getLogger(PerformanceLogger.class);
    private static final int SLEEP_TIME_SEC = 10;
    private static final int SLEEP_TIME_MILISEC = SLEEP_TIME_SEC * 1000;

    /**
     * Private constructor.
     */
    private PerformanceLogger() {
    }

    static {
        new Thread(new PerformanceLogger()).start();
    }

    private static volatile boolean quit = false;

    private static volatile List<AbstractBasicGenerator>         taskList = new ArrayList<AbstractBasicGenerator>();

    /**
     * Toevoegen of verwijderen van generator van takenlijst.
     *
     * @param generator generator
     * @param add toevoegen of verwijderen
     */
    private static void addRemove(final AbstractBasicGenerator generator, final boolean add) {
        List<AbstractBasicGenerator> myList = new ArrayList<AbstractBasicGenerator>(taskList);
        if (add) {
            if (!myList.contains(generator)) {
                taskList.add(generator);
            }
        } else {
            if (myList.contains(generator)) {
                taskList.remove(generator);
            }
        }

    }

    /**
     * Registreren/toevoegen van generator.
     *
     * @param generator the generator
     */
    public static void register(final AbstractBasicGenerator generator) {
        addRemove(generator, true);
    }

    /**
     * Deregistreren/verwijderen van generator.
     *
     * @param generator the generator
     */
    public static void unregister(final AbstractBasicGenerator generator) {
        addRemove(generator, false);
    }

    /**
     * Afsluiten logger.
     */
    public static void quitLogger() {
        quit = true;
    }

    @Override
    public void run() {

        while (!quit) {
            try {
                Thread.sleep(SLEEP_TIME_MILISEC);
                List<AbstractBasicGenerator> myList = new ArrayList<AbstractBasicGenerator>(taskList);
                for (AbstractBasicGenerator generator : myList) {
                    log.info(generator.getStatus(SLEEP_TIME_SEC));
                }
            } catch (Exception e) {
                log.error(e);
                quit = true;
            }
        }
    }
}
