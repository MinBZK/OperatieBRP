/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime;

import java.util.Calendar;

import nl.bzk.migratiebrp.util.common.Version;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ISC Runtime.
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static String[] configuratieFiles = new String[] {"classpath:isc-runtime.xml" };
    private static ConfigurableApplicationContext context;

    private Main() {
        // Niet instantieerbaar
    }

    /**
     * Run ISC.
     *
     * @param args
     *            argumenten (ongebruikt)
     */
    public static void main(final String[] args) {
        final Version version = Version.readVersion("nl.bzk.migratiebrp.isc", "migr-isc-runtime");
        LOG.info(FunctioneleMelding.ISC_STARTEN_APPLICATIE, "Starten applicatie ({}) ...\nComponenten:\n{}", version.toString(), version.toDetailsString());
        context = new ClassPathXmlApplicationContext(configuratieFiles);
        context.registerShutdownHook();

        LOG.info(FunctioneleMelding.ISC_APPLICATIE_CORRECT_GESTART, "Applicatie ({}) gestart om {}", version.toString(), Calendar.getInstance().getTime());
    }

    /**
     * Geef de context.
     * 
     * @return de context
     */
    public static ConfigurableApplicationContext getContext() {
        return context;
    }
}
