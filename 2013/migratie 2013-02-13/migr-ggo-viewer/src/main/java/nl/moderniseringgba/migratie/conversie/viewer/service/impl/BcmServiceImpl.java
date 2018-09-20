/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import nl.gba.gbav.impl.checker.Checker;
import nl.gba.gbav.impl.util.configuration.ServiceLocatorSpringImpl;
import nl.gba.gbav.util.configuration.ServiceLocator;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Lg01Bericht;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutMelder;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutRegel;
import nl.moderniseringgba.migratie.conversie.viewer.service.BcmService;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Zorgt voor koppeling met BCM.
 */
@Component
public final class BcmServiceImpl implements BcmService {
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Properties BCM_CHECK_DESCRIPTIONS = new Properties();
    private static final String BCM_ERR_MSG = "Onverwacht BCM resultaat: ";

    @Inject
    private Checker checker;

    static {
        // Nodig voor de GBAV dependencies.
        if (!ServiceLocator.isInitialized()) {
            final String id = System.getProperty("gbav.deployment.id", "gbavContext");
            final String context =
                    System.getProperty("gbav.deployment.context", "classpath:gbavconfig/deploymentContext.xml");
            ServiceLocator.initialize(new ServiceLocatorSpringImpl(context, id));
        }
    }

    /**
     * Controleer de Lo3Persoonslijst door de BCM.
     * 
     * @param lo3Persoonslijst
     *            Lo3Persoonslijst
     * @param foutMelder
     *            Het object om verwerkingsfouten aan te melden.
     * @return bcmSignaleringen List<FoutRegel>
     */
    @Override
    public List<FoutRegel> controleerDoorBCM(final Lo3Persoonslijst lo3Persoonslijst, final FoutMelder foutMelder) {
        List<FoutRegel> bcmSignaleringen = null;
        try {
            if (useBCM()) {
                // lo3Persoonlijst naar lg01 body
                final String lg01Body = formatteerAlsLg01(lo3Persoonslijst);

                // Override system property
                setBCMDetailSystemProperty();

                // Roep BCM aan
                final Set<String> bcmResultaat = checker.checkOnePL(lg01Body);
                bcmSignaleringen = converteerBCMResultaat(bcmResultaat);
            } else {
                LOG.info("BCM uitgeschakeld");
            }
        } catch (final IOException ioe) {
            foutMelder.log(LogSeverity.ERROR, "BCM kan benodigde bestanden niet inlezen/vinden", ioe.getMessage());
            // CHECKSTYLE:OFF - Alle fouten afvangen en een nette specifieke melding voor op het scherm.
        } catch (final RuntimeException e) { // NOSONAR
            // CHECKSTYLE:ON
            foutMelder.log(LogSeverity.ERROR, "Fout bij het aanroepen van BCM", e);
        }
        return bcmSignaleringen;
    }

    /**
     * Controleert of BCM is ingeschakeld.
     * 
     * @return boolean bcmEnabled
     */
    boolean useBCM() {
        return Boolean.TRUE.toString().equalsIgnoreCase(
                ServiceLocator.getInstance().getSystemProperty("bcmEnabled", Boolean.FALSE.toString()));
    }

    /**
     * Overrule the system property to get a more detailed result.
     */
    void setBCMDetailSystemProperty() {
        final ServiceLocatorSpringImpl serviceLocatorImpl = (ServiceLocatorSpringImpl) ServiceLocator.getInstance();
        serviceLocatorImpl.setSystemProperty("showDetails", Boolean.TRUE.toString());
    }

    /**
     * Formateert de Lo3Persoonslijst naar een Lg01 string.
     * 
     * @param lo3Persoonslijst
     *            Lo3Persoonslijst
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
     * 
     * @param bcmResultaat
     *            Set<String>
     * @return bcmResultaatVoorViewer List<FoutRegel>
     */
    List<FoutRegel> converteerBCMResultaat(final Set<String> bcmResultaat) {
        final List<FoutRegel> bcmResultaatVoorViewer = new ArrayList<FoutRegel>();
        for (final String bcmResultaatRegel : bcmResultaat) {
            final Pattern p = Pattern.compile(".+[,]\\[.+[;].+[;].+\\][,].+");
            final Matcher m = p.matcher(bcmResultaatRegel);
            if (m.matches()) {
                final String[] bcmResultaatRegelArray = bcmResultaatRegel.split(",");

                final String bcmControleId = bcmResultaatRegelArray[0];
                final String checkDescription = getBcmControleOmschrijving(bcmControleId);
                final Lo3Herkomst herkomst = bepaalHerkomstInBcmResultaat(bcmResultaatRegelArray[1]);

                final FoutRegel bcmSignalering = new FoutRegel(herkomst, null, null, bcmControleId, checkDescription);
                bcmResultaatVoorViewer.add(bcmSignalering);
            } else {
                throw new IllegalArgumentException(BCM_ERR_MSG + bcmResultaatRegel);
            }
        }
        return bcmResultaatVoorViewer;
    }

    /**
     * Bepaalt de categorie/stapel/voorkomen uit de bronRegel uitgaande van formaat '[catNr;stapelNr;volgNr]'
     * 
     * @param bronRegel
     * @return lo3Herkomst
     */
    private Lo3Herkomst bepaalHerkomstInBcmResultaat(final String bronRegel) {
        try {
            final String strippedBronRegel = bronRegel.substring(1, bronRegel.length() - 1);
            final String[] bronRegelArray = strippedBronRegel.split(";");

            final int catNr = Integer.parseInt(bronRegelArray[0]);
            final int stapelNr = Integer.parseInt(bronRegelArray[1]);
            final int volgNr = Integer.parseInt(bronRegelArray[2]);

            return new Lo3Herkomst(Lo3CategorieEnum.valueOfCategorie(catNr), stapelNr, volgNr);
        } catch (final NumberFormatException nfe) {
            throw new IllegalArgumentException(BCM_ERR_MSG + bronRegel, nfe);
        }
    }

    /**
     * Geeft de omschrijving van de bcm controle terug.
     * 
     * @param bcmControleId
     *            String
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
     * 
     * @param filename
     *            pad plus bestandsnaam
     */
    private void laadBestand(final String filename) throws FileNotFoundException {
        final FileInputStream fis = new FileInputStream(filename);
        try {
            BCM_CHECK_DESCRIPTIONS.load(fis);
            LOG.info(BCM_CHECK_DESCRIPTIONS.size() + " BCM controle omschrijvingen geladen uit bestand '" + filename
                    + "'");
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
}
