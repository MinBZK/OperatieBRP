/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.logging;

import java.util.Date;
import java.util.Set;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;

/**
 * Interface voor de service die alles mbt logging afhandeld.
 */
public interface LoggingService {

    /**
     * Slaat een Lo3Bericht entiteit op in de BRP database. Als de Lo3Bericht entiteit is geassocieerd met een Persoon
     * dan dient deze als in de database te zijn opgeslagen.
     * @param bericht de Lo3Bericht
     * @return het Lo3Bericht object dat is opslagen in de database
     */
    Lo3Bericht persisteerLo3Bericht(Lo3Bericht bericht);

    /**
     * Zoekt de berichtLog administratienummers op voor de meegegeven criteria.<br/>
     * @param vanaf Datum tijd stempel ligt na vanaf.
     * @param tot Datum tijd stempel ligt voor tot.
     * @return Lijst met BerichtLogs.
     */
    Set<String> zoekBerichtLogAnrs(Date vanaf, Date tot);

    /**
     * Vraag het laatst toegevoegde Lo3Bericht met een persoonslijst op voor het meegeven administratienummer.
     * @param administratienummer het *actuele* administratienummer van de op te zoeken persoon
     * @return De meeste recente Lo3Bericht of null als niet gevonden.
     */
    Lo3Bericht zoekLo3PersoonslijstBerichtOpAnummer(String administratienummer);
}
