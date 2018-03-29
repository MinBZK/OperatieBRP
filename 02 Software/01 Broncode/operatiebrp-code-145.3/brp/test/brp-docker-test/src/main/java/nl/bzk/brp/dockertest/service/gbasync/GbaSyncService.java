/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service.gbasync;

import java.io.IOException;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.TeLeverenAdministratieveHandelingenAanwezigException;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import org.springframework.core.io.Resource;

/**
 * Interface voor de het vullen van de database met geconverteerde persoonslijsten.
 */
public interface GbaSyncService {

    /**
     * @param persoonFile persoonFile
     * @return
     * @throws IOException io fout
     * @throws ExcelAdapterException excel fout
     * @throws Lo3SyntaxException syntax fout
     * @throws OngeldigePersoonslijstException algemene fout
     * @throws TeLeverenAdministratieveHandelingenAanwezigException fout
     */
    PersoonslijstPersisteerResultaat converteer(Resource persoonFile) throws IOException,
            ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException, TeLeverenAdministratieveHandelingenAanwezigException;

    PersoonslijstPersisteerResultaat synchroniseer(Resource persoonFile) throws IOException,
            ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException, TeLeverenAdministratieveHandelingenAanwezigException;
}
