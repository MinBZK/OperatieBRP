/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.service.impl;

import java.util.List;

import javax.inject.Inject;

import nl.gba.gbav.impl.util.configuration.ServiceLocatorSpringImpl;
import nl.gba.gbav.util.configuration.ServiceLocator;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.proces.ConversieService;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3.Lo3PersoonslijstPrecondities;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutMelder;
import nl.moderniseringgba.migratie.conversie.viewer.service.ViewerService;
import nl.moderniseringgba.migratie.conversie.viewer.vergelijk.Lo3Vergelijker;
import nl.moderniseringgba.migratie.conversie.viewer.vergelijk.StapelVergelijking;

import org.springframework.stereotype.Component;

/**
 * Roept de voor de Viewer relevante delen van de omliggende code aan.
 */
@Component
public class ViewerServiceImpl implements ViewerService {

    @Inject
    private Lo3PersoonslijstPrecondities lo3PersoonslijstPrecondities;
    @Inject
    private ConversieService conversieService;

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
     * Verwerk precondities.
     * 
     * @param lo3Persoonslijst
     *            De Lo3 Persoonslijst waarop de precondities worden gecontroleerd.
     * @param foutMelder
     *            Het object om verwerkingsfouten aan te melden.
     * @return Een lijst met LogRegels, bevattende de overtredingen van precondities.
     */
    @Override
    public final List<LogRegel> verwerkPrecondities(
            final Lo3Persoonslijst lo3Persoonslijst,
            final FoutMelder foutMelder) {
        try {
            Logging.initContext();
            lo3PersoonslijstPrecondities.controleerPersoonslijst(lo3Persoonslijst);
            return Logging.getLogging().getRegels();
            // CHECKSTYLE:OFF - Alle fouten afvangen en een nette specifieke melding voor op het scherm.
        } catch (final RuntimeException e) { // NOSONAR
            // CHECKSTYLE:ON
            foutMelder.log(LogSeverity.ERROR, "Fout bij verwerken precondities", e);
            return null;
        }
    }

    /**
     * Converteer naar BRP.
     * 
     * @param lo3Persoonslijst
     *            De Lo3 Persoonslijst die geconverteerd moet worden.
     * @param foutMelder
     *            Het object om verwerkingsfouten aan te melden.
     * @return De bijbehorende BRP Persoonslijst
     */
    @Override
    public final BrpPersoonslijst converteerNaarBrp(
            final Lo3Persoonslijst lo3Persoonslijst,
            final FoutMelder foutMelder) {
        try {
            return conversieService.converteerLo3Persoonslijst(lo3Persoonslijst);
        } catch (final InputValidationException e) {
            foutMelder.log(LogSeverity.ERROR, "Validatiefout bij converteren naar BRP", e.getMessage());
            // CHECKSTYLE:OFF - Alle fouten afvangen en een nette specifieke melding voor op het scherm.
        } catch (final RuntimeException e) { // NOSONAR
            // CHECKSTYLE:ON
            foutMelder.log(LogSeverity.ERROR, "Fout bij converteren naar BRP", e);
        }
        return null;
    }

    /**
     * Converteer een naar BRP geconverteerde persoonslijst terug naar Lo3.
     * 
     * @param brpPersoonslijst
     *            De BRP Persoonslijst die geconverteerd moet worden.
     * @param foutMelder
     *            Het object om verwerkingsfouten aan te melden.
     * @return De bijbehorende terugconversie in de vorm van een Lo3 Persoonslijst.
     */
    @Override
    public final Lo3Persoonslijst
            converteerTerug(final BrpPersoonslijst brpPersoonslijst, final FoutMelder foutMelder) {
        try {
            return conversieService.converteerBrpPersoonslijst(brpPersoonslijst);
            // CHECKSTYLE:OFF - Alle fouten afvangen en een nette specifieke melding voor op het scherm.
        } catch (final RuntimeException e) { // NOSONAR
            // CHECKSTYLE:ON
            foutMelder.log(LogSeverity.ERROR, "Fout bij bij terugconverteren", e);
            return null;
        }
    }

    /**
     * Vergelijk Lo3 origineel met de teruggeconverteerde variant.
     * 
     * @param origineel
     *            Het origineel
     * @param teruggeconverteerd
     *            De teruggeconverteerde variant
     * @param foutMelder
     *            Het object om verwerkingsfouten aan te melden.
     * @return Een lijst StapelVergelijking'en, overigens inclusief 'IDENTICAL' regels.
     */
    @Override
    public final List<StapelVergelijking> vergelijkLo3OrigineelMetTerugconversie(
            final Lo3Persoonslijst origineel,
            final Lo3Persoonslijst teruggeconverteerd,
            final FoutMelder foutMelder) {
        try {
            final Lo3Vergelijker lo3Vergelijker = new Lo3Vergelijker();
            final List<StapelVergelijking> resultaatVergelijking =
                    lo3Vergelijker.vergelijk(origineel, teruggeconverteerd);
            return resultaatVergelijking;
            // CHECKSTYLE:OFF - Alle fouten afvangen en een nette specifieke melding voor op het scherm.
        } catch (final RuntimeException e) { // NOSONAR
            // CHECKSTYLE:ON
            foutMelder.log(LogSeverity.ERROR, "Fout bij het vergelijken", e);
            return null;
        }
    }
}
