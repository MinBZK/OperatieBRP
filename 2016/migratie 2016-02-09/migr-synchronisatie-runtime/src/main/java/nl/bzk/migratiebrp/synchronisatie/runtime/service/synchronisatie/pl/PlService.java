/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;

/**
 * Persoonslijst service. Facade voor alle bewerking op een persoonslijst (zoeken, opslaan, etc).
 */
public interface PlService {

    /**
     * Zoek een persoonslijst (ongeacht opschort reden) met het gegeven actuele a-nummer.
     *
     * @param anummer
     *            a-nummer
     * @return gevonden persoonslijsten, of lege lijst als niet gevonden.
     */
    List<BrpPersoonslijst> zoekPersoonslijstenOpActueelAnummer(long anummer);

    /**
     * Zoek een (niet opgeschort met reden 'F' of 'W') persoonslijst met het gegeven actuele a-nummer.
     *
     * @param anummer
     *            a-nummer
     * @return gevonden persoonslijst, of null als niet gevonden.
     * @throws IllegalStateException
     *             als meerdere niet foutief opgeschorte persoonslijsten worden gevonden met het gegevens actuele
     *             a-nummer
     */
    BrpPersoonslijst zoekNietFoutievePersoonslijstOpActueelAnummer(long anummer);

    /**
     * Zoek (opgeacht opschort reden) persoonslijsten met het gegeven historische a-nummer.
     *
     * @param anummer
     *            a-nummer
     * @return gevonden persoonslijsten, lege lijst als niets gevonden
     */
    List<BrpPersoonslijst> zoekPersoonslijstenOpHistorischAnummer(long anummer);

    /**
     * Sla een persoonslijst als nieuwe persoonslijst op.
     *
     * @param brpPersoonslijst
     *            persoonslijst
     * @param loggingBericht
     *            logging bericht (om te koppelen aan persoonslijst)
     * @return administratieve handeling id
     */
    List<Long> persisteerPersoonslijst(BrpPersoonslijst brpPersoonslijst, Lo3Bericht loggingBericht);

    /**
     * Sla een persoonslijst op als vervanging voor een bestaande persoonslijst.
     *
     * @param brpPersoonslijst
     *            persoonslijst
     * @param teVervangenAnummer
     *            a-nummer van de te vervangen persoonslijst
     * @param isANummerWijziging
     *            geeft aan of deze persoonslijst update als a-nummer wijziging moet worden behandeld
     * @param loggingBericht
     *            logging bericht (om te koppelen aan persoonslijst)
     * @return administratieve handeling ids
     */
    List<Long> persisteerPersoonslijst(BrpPersoonslijst brpPersoonslijst, long teVervangenAnummer, boolean isANummerWijziging, Lo3Bericht loggingBericht);

    /**
     * Sla een logging bericht op (zonder koppeling naar persoonslijst).
     *
     * @param bericht
     *            logging bericht
     */
    void persisteerLogging(Lo3Bericht bericht);

    /**
     * Converteer een kandidaat PL naar LO3 teletex string.
     *
     * @param persoonslijst
     *            persoonslijst
     * @return LO3 teletex string.
     */
    String converteerKandidaat(BrpPersoonslijst persoonslijst);

    /**
     * Converteer meerdere kandidaat PL-en naar LO3 teleex strings.
     *
     * @param persoonslijsten
     *            persoonlijsten
     * @return teletex strings
     */
    String[] converteerKandidaten(List<BrpPersoonslijst> persoonslijsten);

}
