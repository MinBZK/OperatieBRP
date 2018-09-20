/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle.runtime;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import nl.moderniseringgba.migratie.controle.rapport.ControleNiveauEnum;
import nl.moderniseringgba.migratie.controle.rapport.ControleTypeEnum;
import nl.moderniseringgba.migratie.controle.rapport.Opties;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Deze util class is voor het parsen van de meegegeven arguments van de main en om de juiste opties eruit te filteren.
 */
public final class OptionsUtils {

    private static final Logger LOG = LoggerFactory.getLogger();

    private OptionsUtils() {
        super();
    }

    /**
     * Parsed de meegeven arguments en bouwt een Opties object op.
     * 
     * @param args
     *            De argumenten die meegegeve werden aan de Main.
     * @return Opties met de opgegeven opties.
     * @throws ParseException
     *             Als het parsen van de options en arguments mislukt.
     * @throws IOException
     *             Als de verplichte config file niet bestaat.
     * @throws java.text.ParseException
     *             Als het parsen van een datum mislukt.
     */
    // CHECKSTYLE:OFF ik wil hogerop kunnen bepalen wat ik met de exceptions doe. Dus toch 3 ipv 2 teruggeven.
    public static Opties parseOpties(final String[] args) throws ParseException, IOException,
    // CHECKSTYLE:ON
            java.text.ParseException {
        final Options options = new Options();

        // config file
        final Option configOption = new Option("config", true, "configuratie (.properties)");
        configOption.setRequired(true);
        options.addOption(configOption);

        // type
        final Option typeOption = new Option("type", true, "Controletype");
        typeOption.setRequired(true);
        options.addOption(typeOption);

        // niveau
        final Option niveauOption = new Option("niveau", true, "Controleniveau");
        niveauOption.setRequired(true);
        options.addOption(niveauOption);

        // vanaf
        final Option vanafOption = new Option("vanaf", true, "Vanaf");
        vanafOption.setRequired(true);
        options.addOption(vanafOption);

        // tot
        final Option totOption = new Option("tot", true, "Tot");
        totOption.setRequired(true);
        options.addOption(totOption);

        // gemeente
        final Option gemeenteOption = new Option("gemeente", true, "Gemeentecode");
        gemeenteOption.setRequired(false);
        options.addOption(gemeenteOption);

        final CommandLineParser parser = new GnuParser();
        final CommandLine cmd = parser.parse(options, args, true);
        createConfigFile(configOption, cmd);

        // Opties samenstellen
        final Opties opties = new Opties();
        opties.setControleNiveau(ControleNiveauEnum.valueOf(cmd.getOptionValue(niveauOption.getOpt())));
        opties.setControleType(ControleTypeEnum.valueOf(cmd.getOptionValue(typeOption.getOpt())));
        if (cmd.hasOption(gemeenteOption.getOpt())) {
            opties.setGemeenteCode(cmd.getOptionValue(gemeenteOption.getOpt()));
        }
        final SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        opties.setTot(format.parse(cmd.getOptionValue(totOption.getOpt())));
        opties.setVanaf(format.parse(cmd.getOptionValue(vanafOption.getOpt())));
        return opties;
    }

    private static void createConfigFile(final Option configOption, final CommandLine cmd) throws IOException {
        final Properties config = new Properties();
        if (cmd.hasOption(configOption.getOpt())) {
            final String configFilename = cmd.getOptionValue(configOption.getOpt());
            LOG.info("Configuratie bestanden laden:{} ", configFilename);

            try {
                config.load(ClassLoader.getSystemResourceAsStream(configFilename));
                // CHECKSTYLE:OFF alle fouten afvangen en 1 fout over de config file gooien.
            } catch (final Exception e) {
                // CHECKSTYLE:ON
                throw new IOException("Configuratie file: '" + configFilename + "' bestaat niet.", e);
            }
        }
    }
}
