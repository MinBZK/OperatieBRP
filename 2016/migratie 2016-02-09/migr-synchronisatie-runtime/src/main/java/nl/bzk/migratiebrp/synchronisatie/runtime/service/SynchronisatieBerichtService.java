/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;

/**
 * Specificatie van de services die geboden worden door de SynchronisatieService.
 * 
 * @param <Verzoek>
 *            Verzoek bericht type dat wordt verwerkt.
 * @param <Antwoord>
 *            Antwoord bericht type dat wordt geproduceerd.
 */
public interface SynchronisatieBerichtService<Verzoek extends SyncBericht, Antwoord extends SyncBericht> {
    /**
     * Geeft de verwerkte verzoek classe.
     * 
     * @return De verwerkte verzoek classe.
     */
    Class<Verzoek> getVerzoekType();

    /**
     * Verwerk het bericht en produceer een antwoord.
     * 
     * @param verzoek
     *            Het verzoek bericht.
     * @return Het antwoord bericht.
     * @throws BerichtSyntaxException
     *             Als de syntax van het bericht niet goed is.
     * @throws OngeldigePersoonslijstException
     *             Als de persoonslijst ongeldig is.
     */
    Antwoord verwerkBericht(Verzoek verzoek) throws OngeldigePersoonslijstException, BerichtSyntaxException;

    /**
     * Geef de waarde van service naam.
     *
     * @return de naam van de specifieke SynchronisatieBerichtService.
     */
    String getServiceNaam();
}
