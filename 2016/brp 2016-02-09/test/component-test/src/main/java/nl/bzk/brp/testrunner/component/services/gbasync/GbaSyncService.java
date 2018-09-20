/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.services.gbasync;

import java.io.File;
import java.io.IOException;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import org.springframework.core.io.Resource;

/**
 * Interface voor de het vullen van de database met geconverteerde persoonslijsten.
 */
public interface GbaSyncService {

    /**
     * Converteert een LO3 persoonslijst als initiele vulling naar BRP.
     *
     * @param persoonFile de LO3 persoonslijst
     * @return het resultaat van de conversie en initiele vulling van BRP.
     * @throws IOException                     als het bestand niet ingelezen kan worden
     * @throws ExcelAdapterException           als het bestand geen Excel-sheet is of als er een fout ontstaat bij het lezen van de Excel-sheet
     * @throws Lo3SyntaxException              Als de persoonlijst een syntax fout bevat
     * @throws OngeldigePersoonslijstException Als de persoonlijst niet voldoet aan de precondities
     */
    PersoonslijstPersisteerResultaat converteerInitieleVullingPL(final Resource persoonFile)
        throws IOException, ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException;

    /**
     * Converteert een LO3 persoonslijst als synchronisatie naar BRP.
     *
     * @param persoonFile de LO3 persoonslijst
     * @return het resultaat van de conversie en synchronisatie naar BRP.
     * @throws IOException                     als het bestand niet ingelezen kan worden
     * @throws ExcelAdapterException           als het bestand geen Excel-sheet is of als er een fout ontstaat bij het lezen van de Excel-sheet
     * @throws Lo3SyntaxException              Als de persoonlijst een syntax fout bevat
     * @throws OngeldigePersoonslijstException Als de persoonlijst niet voldoet aan de precondities
     */
    PersoonslijstPersisteerResultaat converteerSyncPL(final Resource persoonFile)
        throws IOException, ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException;

    /**
     * Converteert een meerdere versies van een LO3 persoonslijst naar BRP.
     *
     * @param persoonFile de LO3 persoonslijst
     * @return het resultaat van de conversies van BRP.
     * @throws IOException als het bestand niet ingelezen kan worden
     * @throws ExcelAdapterException als het bestand geen Excel-sheet is of als er een fout ontstaat bij het lezen van
     *             de Excel-sheet
     * @throws Lo3SyntaxException Als de persoonlijst een syntax fout bevat
     * @throws OngeldigePersoonslijstException Als de persoonlijst niet voldoet aan de precondities
     */
    PersoonslijstPersisteerResultaat converteer(final File persoonFile)
        throws IOException, ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException;

    /**
     * Geeft een persoonslijst terug welke in het BRP Migratiemodel is gegoten.
     *
     * @param anummer het anummer van de persoon die opgevraagd wordt
     * @return de BRP persoonslijst horende bij het anummer
     */
    BrpPersoonslijst terugMappenNaarConversieModel(final long anummer);
}
