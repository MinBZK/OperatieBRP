/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.service;

import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutMelder;
import nl.moderniseringgba.migratie.conversie.viewer.vergelijk.StapelVergelijking;

/**
 * Roept de voor de Viewer relevante delen van de omliggende code aan.
 */
public interface ViewerService {

    /**
     * Verwerk precondities.
     * 
     * @param lo3Persoonslijst
     *            De Lo3 Persoonslijst waarop de precondities worden gecontroleerd.
     * @param foutMelder
     *            Het object om verwerkingsfouten aan te melden.
     * @return Een lijst met LogRegels, bevattende de overtredingen van precondities.
     */
    List<LogRegel> verwerkPrecondities(final Lo3Persoonslijst lo3Persoonslijst, FoutMelder foutMelder);

    /**
     * Converteer naar BRP.
     * 
     * @param lo3Persoonslijst
     *            De Lo3 Persoonslijst die geconverteerd moet worden.
     * @param foutMelder
     *            Het object om verwerkingsfouten aan te melden.
     * @return De bijbehorende BRP Persoonslijst
     */
    BrpPersoonslijst converteerNaarBrp(Lo3Persoonslijst lo3Persoonslijst, FoutMelder foutMelder);

    /**
     * Converteer een naar BRP geconverteerde persoonslijst terug naar Lo3.
     * 
     * @param brpPersoonslijst
     *            De BRP Persoonslijst die geconverteerd moet worden.
     * @param foutMelder
     *            Het object om verwerkingsfouten aan te melden.
     * @return De bijbehorende terugconversie in de vorm van een Lo3 Persoonslijst.
     */
    Lo3Persoonslijst converteerTerug(final BrpPersoonslijst brpPersoonslijst, FoutMelder foutMelder);

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
    List<StapelVergelijking> vergelijkLo3OrigineelMetTerugconversie(
            Lo3Persoonslijst origineel,
            Lo3Persoonslijst teruggeconverteerd,
            FoutMelder foutMelder);

}
