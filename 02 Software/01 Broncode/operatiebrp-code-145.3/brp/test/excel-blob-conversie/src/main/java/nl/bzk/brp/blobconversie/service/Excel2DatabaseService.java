/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobconversie.service;

import java.io.IOException;

import org.springframework.core.io.Resource;

import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.TeLeverenAdministratieveHandelingenAanwezigException;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;

/**
 * Interface voor de het vullen van de database met geconverteerde persoonslijsten.
 */
public interface Excel2DatabaseService {


    /**
     * Converteer een PL file met daarin initiele vulling kolom (0) en 0 of * sync kolommen.
     *
     * @param persoonFile persoon file
     * @return the persoonslijst persisteer resultaat
     * @throws IOException iO exception
     * @throws ExcelAdapterException excel adapter exception
     * @throws Lo3SyntaxException lo 3 syntax exception
     * @throws OngeldigePersoonslijstException ongeldige persoonslijst exception
     * @throws TeLeverenAdministratieveHandelingenAanwezigException nog adm hnd te leveren
     */
    PersoonslijstPersisteerResultaat converteer(Resource persoonFile, Blob2FileService blob2FileService) throws IOException, ExcelAdapterException,
            Lo3SyntaxException, OngeldigePersoonslijstException, TeLeverenAdministratieveHandelingenAanwezigException;


    void insertPersoonCache(int persoonId, byte[] persoonBlob, byte[] afnemerindicatiesBlob);
}
