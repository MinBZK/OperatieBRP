/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle.runtime;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import nl.moderniseringgba.migratie.controle.ControleService;
import nl.moderniseringgba.migratie.controle.SelectieService;
import nl.moderniseringgba.migratie.controle.rapport.ControleRapport;
import nl.moderniseringgba.migratie.controle.rapport.Opties;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Basis runtime. Start de spring container; de spring container bevat componenten die een live thread opstarten (JMS
 * queue poller).
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static String springConfig = "classpath:controle-beans.xml";

    private Main() {
        throw new AssertionError("Niet instantieerbaar.");
    }

    /**
     * Run.
     * 
     * De 'config' systeem property is verplicht en moet verwijzen naar de config file.
     * 
     * @param args
     *            argumenten.
     * @throws Exception
     *             Exception als er iets misgaat.
     */
    public static void main(final String[] args) throws Exception {

        final String configFileProperty = System.getProperty("config");
        final boolean doesConfigFileExist = configFileProperty != null && new File(configFileProperty).exists();
        LOG.info("Using config file: " + configFileProperty + " Exists? " + doesConfigFileExist);
        if (!doesConfigFileExist) {
            throw new IllegalArgumentException("Config file kan niet worden gelezen.");
        }

        final Opties opties = OptionsUtils.parseOpties(args);

        LOG.info("Starting application context");
        final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(springConfig.split(","));

        // Selectie en controle
        final ControleService controleService = (ControleService) context.getBean("controleServiceImpl");
        final SelectieService selectieService = (SelectieService) context.getBean("selectieServiceImpl");

        // Selecteer PL'en
        final ControleRapport controleRapport = new ControleRapport();
        final Set<Long> anummers = selectieService.selecteerPLen(opties, controleRapport);

        // Controleer PL'en
        controleService.controleerPLen(new ArrayList<Long>(anummers), opties, controleRapport);

        // Output resultaten
        LOG.info(controleRapport.formatRapport());

        LOG.info("Controle klaar.");
    }

    /**
     * @param springConfig
     *            the springConfig to set
     */
    public static void setSpringConfig(final String springConfig) {
        Main.springConfig = springConfig;
    }

}
