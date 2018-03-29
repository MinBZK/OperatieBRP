/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoFoutRegel;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoLogType;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoStap;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.service.BcmService;
import nl.bzk.migratiebrp.ggo.viewer.util.NaamUtil;
import nl.gba.gbav.impl.checker.Checker;
import nl.gba.gbav.impl.util.configuration.ServiceLocatorSpringImpl;
import nl.gba.gbav.util.configuration.ServiceLocator;

import org.springframework.stereotype.Component;

/**
 * Zorgt voor koppeling met BCM.
 */
@Component
public final class BcmServiceImpl implements BcmService {
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Properties BCM_CHECK_DESCRIPTIONS = new Properties();
    private static final String BCM_ERR_MSG = "Onverwacht BCM resultaat: ";
    private static final int HISTORISCH = 50;

    @Inject
    private Checker checker;

    static {
        // Nodig voor de GBAV dependencies.
        if (!ServiceLocator.isInitialized()) {
            final String id = System.getProperty("gbav.deployment.id", "gbavContext");
            final String context = System.getProperty("gbav.deployment.context", "classpath:gbavconfig/deploymentContext.xml");
            ServiceLocator.initialize(new ServiceLocatorSpringImpl(context, id));
        }
    }

    /**
     * Controleer de Lo3Persoonslijst door de BCM.
     * @param lo3Persoonslijst Lo3Persoonslijst
     * @param foutMelder Het object om verwerkingsfouten aan te melden.
     * @return bcmSignaleringen Lijst met signaleringen.
     */
    @Override
    public List<GgoFoutRegel> controleerDoorBCM(final Lo3Persoonslijst lo3Persoonslijst, final FoutMelder foutMelder) {
        foutMelder.setHuidigeStap(GgoStap.BCM);

        List<GgoFoutRegel> bcmSignaleringen = null;
        try {
            if (useBCM()) {
                // lo3Persoonlijst naar lg01 body
                final String lg01Body = formatteerAlsLg01(lo3Persoonslijst);

                // Override system property
                setBCMDetailSystemProperty();
                setBCMTappSystemProperty();

                // Roep BCM aan
                final Set<String> bcmResultaat = checker.checkOnePL(lg01Body);
                bcmSignaleringen = converteerBCMResultaat(bcmResultaat);
            } else {
                LOG.info("BCM uitgeschakeld");
            }
        } catch (final IOException ioe) {
            foutMelder.log(LogSeverity.ERROR, "BCM kan benodigde bestanden niet inlezen/vinden", ioe.getMessage());
        } catch (final BCMResultaatException e) {
            foutMelder.log(LogSeverity.ERROR, "Fout bij het aanroepen van BCM", e);
        }
        return bcmSignaleringen;
    }

    /**
     * Controleert of BCM is ingeschakeld.
     * @return boolean bcmEnabled
     */
    boolean useBCM() {
        return Boolean.TRUE.toString().equalsIgnoreCase(ServiceLocator.getInstance().getSystemProperty("bcmEnabled", Boolean.FALSE.toString()));
    }

    /**
     * Overrule the system property to get a more detailed result.
     */
    void setBCMDetailSystemProperty() {
        final ServiceLocatorSpringImpl serviceLocatorImpl = (ServiceLocatorSpringImpl) ServiceLocator.getInstance();
        serviceLocatorImpl.setSystemProperty("showDetails", Boolean.TRUE.toString());
    }

    /**
     * Overrule the system property to read landelijke tabellen from sheet instead of gbav database.
     */
    void setBCMTappSystemProperty() {
        final ServiceLocatorSpringImpl serviceLocatorImpl = (ServiceLocatorSpringImpl) ServiceLocator.getInstance();
        serviceLocatorImpl.setSystemProperty("useTappDatasource", Boolean.FALSE.toString());
    }

    /**
     * Formateert de Lo3Persoonslijst naar een Lg01 string.
     * @param lo3Persoonslijst Lo3Persoonslijst
     * @return String Lg01 body
     */
    String formatteerAlsLg01(final Lo3Persoonslijst lo3Persoonslijst) {
        final Lg01Bericht lg01 = new Lg01Bericht();
        lg01.setLo3Persoonslijst(lo3Persoonslijst);
        return Lo3Inhoud.formatInhoud(lg01.formatInhoud());
    }

    /**
     * Converteert het resultaat van de BCM naar een formaat welke in de Viewer getoond kan worden. Verwacht formaat BCM
     * resultaat is 'L0001,[1;0;0],test(01.20.10;01.20.30)'.
     * @param bcmResultaat Set<String>
     * @return bcmResultaatVoorViewer List<FoutRegel>
     * @throws BCMResultaatException als een resultaat niet geconverteerd kan worden
     */
    List<GgoFoutRegel> converteerBCMResultaat(final Set<String> bcmResultaat) throws BCMResultaatException {
        final List<GgoFoutRegel> bcmResultaatVoorViewer = new ArrayList<>();
        for (final String bcmResultaatRegel : bcmResultaat) {
            final Pattern p = Pattern.compile(".+[,]\\[.+[;].+[;].+\\][,].+");
            final Matcher m = p.matcher(bcmResultaatRegel);
            if (m.matches()) {
                final String[] bcmResultaatRegelArray = bcmResultaatRegel.split(",");

                final String bcmControleId = bcmResultaatRegelArray[0];
                final String checkDescription = getBcmControleOmschrijving(bcmControleId);
                final GgoVoorkomen herkomst = bepaalHerkomstInBcmResultaat(bcmResultaatRegelArray[1]);

                final GgoFoutRegel bcmSignalering = new GgoFoutRegel(herkomst, LogSeverity.ERROR, GgoLogType.BCM, bcmControleId, checkDescription);
                bcmResultaatVoorViewer.add(bcmSignalering);
            } else {
                throw new BCMResultaatException(BCM_ERR_MSG + bcmResultaatRegel);
            }
        }
        return bcmResultaatVoorViewer;
    }

    /**
     * Bepaalt de categorie/stapel/voorkomen uit de bronRegel uitgaande van formaat '[catNr;stapelNr;volgNr]'
     * @param bronRegel de bronRegel
     * @return lo3Herkomst
     */
    private GgoVoorkomen bepaalHerkomstInBcmResultaat(final String bronRegel) throws BCMResultaatException {
        try {
            final String strippedBronRegel = bronRegel.substring(1, bronRegel.length() - 1);
            final String[] bronRegelArray = strippedBronRegel.split(";");

            final int catNr = Integer.parseInt(bronRegelArray[0]);
            final int stapelNr = Integer.parseInt(bronRegelArray[1]);
            final int volgNr = Integer.parseInt(bronRegelArray[2]);

            final GgoVoorkomen herkomst = new GgoVoorkomen();
            herkomst.setCategorieNr(catNr);
            herkomst.setStapelNr(stapelNr);
            herkomst.setVoorkomenNr(volgNr);
            herkomst.setLabel(NaamUtil.createLo3CategorieLabel(catNr));
            controleerCategorieHistorisch(herkomst);

            return herkomst;
        } catch (final NumberFormatException nfe) {
            throw new BCMResultaatException(BCM_ERR_MSG + bronRegel, nfe);
        }
    }

    private void controleerCategorieHistorisch(final GgoVoorkomen herkomst) {
        if (herkomst.getCategorieNr() < HISTORISCH && herkomst.getVoorkomenNr() > 0) {
            final int historischCatNr = herkomst.getCategorieNr() + HISTORISCH;
            herkomst.setCategorieNr(historischCatNr);
        }
    }

    /**
     * Geeft de omschrijving van de bcm controle terug.
     * @param bcmControleId String
     * @return bcmCheckDescription String
     */
    String getBcmControleOmschrijving(final String bcmControleId) {
        if (BCM_CHECK_DESCRIPTIONS.isEmpty()) {
            laadBcmControleOmschrijvingen();
        }
        return BCM_CHECK_DESCRIPTIONS.getProperty(bcmControleId, "Omschrijving onbekend");
    }

    /**
     * Laad de BCM omschrijvingen uit een property bestand.
     */
    private void laadBcmControleOmschrijvingen() {
        final String path = ServiceLocator.getInstance().getSystemProperty("bcmDescriptionsPath");
        final String filename = File.separator + "bcmCheckDescriptions.properties";
        try {
            laadBestand(path + filename);
        } catch (final FileNotFoundException fnfex) {
            LOG.warn("Kan bestand '" + filename + "' in map '" + path + "' niet vinden");
        }
    }

    /**
     * Laad de properties uit het opgegeven bestand.
     * @param filename pad plus bestandsnaam
     */
    private void laadBestand(final String filename) throws FileNotFoundException {
        final FileInputStream fis = new FileInputStream(filename);
        try {
            try (Reader reader = new InputStreamReader(fis, "UTF-8")) {
                BCM_CHECK_DESCRIPTIONS.load(reader);
                LOG.info(BCM_CHECK_DESCRIPTIONS.size() + " BCM controle omschrijvingen geladen uit bestand '" + filename + "'");
            }
        } catch (final IOException e) {
            LOG.warn("Kan BCM controle omschrijvingen uit bestand '" + filename + "' niet laden", e);
        } finally {
            try {
                fis.close();
            } catch (final IOException e) {
                LOG.warn("Kan FileInputStream van bestand '" + filename + "' niet sluiten", e);
            }
        }
    }

    /**
     * Exception class voor onverwerkbare BCM Resultaten.
     */
    static class BCMResultaatException extends Exception {
        /**
         * Standaard constructor.
         * @param message exception message
         */
        public BCMResultaatException(final String message) {
            super(message);
        }

        /**
         * Standaard constructor.
         * @param message exception message
         * @param cause cause
         */
        public BCMResultaatException(final String message, final Exception cause) {
            super(message, cause);
        }
    }
}
