/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors.conversie;

import java.io.File;
import java.io.IOException;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface voor de het vullen van de database met geconverteerde Pls
 */
public interface ConversieDataVuller {

    @Transactional
    PersoonslijstPersisteerResultaat converteerInitieleVullingPL(File persoonFile)
        throws IOException, ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException;

    @Transactional
    PersoonslijstPersisteerResultaat converteerSyncPL(File persoonFile)
            throws IOException, ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException;

    /**
     * Converteert en persisteert de aangeleverde PL
     *
     * @param persoonFile een PL excel file
     * @return
     * @throws IOException
     * @throws ExcelAdapterException
     * @throws Lo3SyntaxException
     * @throws OngeldigePersoonslijstException
     */
    PersoonslijstPersisteerResultaat converteer(File persoonFile)
            throws IOException, ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException;
}
