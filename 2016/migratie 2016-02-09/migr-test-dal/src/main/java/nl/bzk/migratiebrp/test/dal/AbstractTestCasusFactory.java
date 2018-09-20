/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.dal;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import nl.bzk.migratiebrp.synchronisatie.dal.util.brpkern.DBUnitBrpUtil;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Test casus factory: basis.
 */
public abstract class AbstractTestCasusFactory implements TestCasusFactory {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String KOPPELTEKEN = "-";
    private String thema;
    private File themaFolder;
    private File outputFolder;
    private File expectedFolder;

    private GenericXmlApplicationContext context;

    /**
     * Default constructor.
     */
    protected AbstractTestCasusFactory() {
    }

    /**
     * Constructor waarbij de Spring application context wordt meegegeven.
     *
     * @param context
     *            de application context
     */
    protected AbstractTestCasusFactory(final GenericXmlApplicationContext context) {
        this.context = context;
    }

    @Override
    public final String getThema() {
        return thema;
    }

    @Override
    public final void setThema(final String thema) {
        this.thema = thema;
    }

    @Override
    public final File getOutputFolder() {
        return outputFolder;
    }

    @Override
    public final void setOutputFolder(final File outputFolder) {
        this.outputFolder = outputFolder;
    }

    @Override
    public final File getExpectedFolder() {
        return expectedFolder;
    }

    @Override
    public final void setExpectedFolder(final File expectedFolder) {
        this.expectedFolder = expectedFolder;
    }

    @Override
    public final void setThemaFolder(final File themaFolder) {
        this.themaFolder = themaFolder;
    }

    @Override
    public final File getThemaFolder() {
        return themaFolder;
    }

    /**
     * Maak de naam voor een test casus.
     *
     * @param name
     *            basis naam
     * @param i
     *            index
     * @return naam
     */
    protected final String maakNaam(final String name, final int i) {
        if (i == 0) {
            return name;
        }
        return name.substring(0, name.indexOf('.')) + KOPPELTEKEN + i;
    }

    /**
     * Maak de naam voor een test casus.
     *
     * @param name
     *            basis naam
     * @param identificatie
     *            index
     * @param gefixeerdeLengte
     *            vastgestelde lengte voor de naam
     * @return naam
     */
    protected final String maakNaamMetGefixeerdeIdentificatieLengte(final String name, final int identificatie, final int gefixeerdeLengte) {

        final String format = "%0" + gefixeerdeLengte + "d";

        return name.substring(0, name.indexOf('.')) + KOPPELTEKEN + String.format(format, identificatie);
    }

    /**
     * Geeft de array terug als {@link java.util.List} object.
     *
     * @param list
     *            de array
     * @param <T>
     *            type van de inhoud van de array
     * @return een {@link java.util.List} met de inhoud van de meegegeven array
     */
    protected final <T> List<T> asList(final T[] list) {
        if (list == null) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(list);
        }
    }

    /**
     * Reset de database voor de tests.
     *
     * @param log
     *            de log waarin gemeld kan worden wat er gebeurd tijdens het resetten van de database.
     */
    protected final void resetDB(final Logger log) {
        final Properties sysProps = System.getProperties();
        final Boolean noResetDB = Boolean.valueOf(sysProps.getProperty("noResetDB"));
        if (noResetDB) {
            LOG.info("Database wordt NIET volledig gereset");
        } else {
            LOG.info("Database wordt volledig gereset");
            context.getBean(DBUnitBrpUtil.class).resetDB(this.getClass(), log);
        }
    }

    /**
     * Geef de waarde van context.
     *
     * @return context
     */
    protected final GenericXmlApplicationContext getContext() {
        return context;
    }
}
