/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;

/**
 * Service voor BrpPersoonslijst.
 */
public interface BrpPersoonslijstService {
    /**
     * Slaat een persoonslijst op in de BRP database.
     * @param brpPersoonslijst De persoonslijst in het BRP-migratiemodelformaat
     * @param lo3Bericht lo3 bericht waarop de persoonlijst is gebaseerd
     * @return een {@link nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat}
     */
    PersoonslijstPersisteerResultaat persisteerPersoonslijst(BrpPersoonslijst brpPersoonslijst, Lo3Bericht lo3Bericht);

    /**
     * Overschrijft een persoonslijst op in de BRP database.
     * @param brpPersoonslijst De persoonslijst in het BRP-migratiemodelformaat
     * @param teVervangenPersoonslijstId het id van de te vervangen persoonslijst
     * @param lo3Bericht lo3 bericht waarop de persoonslijst is gebaseerd
     * @return een {@link nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat}
     * @throws TeLeverenAdministratieveHandelingenAanwezigException als er nog te leveren administratieve handelingen aanwezig zijn voor de te vervangen
     * persoonslijst
     */
    PersoonslijstPersisteerResultaat persisteerPersoonslijst(BrpPersoonslijst brpPersoonslijst, Long teVervangenPersoonslijstId, Lo3Bericht lo3Bericht)
            throws TeLeverenAdministratieveHandelingenAanwezigException;

    /**
     * Vraag een persoonslijst op uit de BRP database voor het gegeven admnistratienummer, deze
     * persoon moet bestaan anders treedt er een fout op.
     * @param administratienummer het *actuele* administratienummer van de op te zoeken persoon
     * @return De persoonslijst in het BRP-migratiemodelformaat.
     * @throws IllegalStateException wanneer geen persoon gevonden is
     */
    BrpPersoonslijst bevraagPersoonslijst(String administratienummer);

    /**
     * Vraag een persoonslijst op uit de BRP database voor de meegegeven technische sleutel, deze
     * persoon moet bestaan anders treedt er een fout op.
     * @param techischesleutel de technische sleutel van de op te zoeken persoon
     * @return De persoonslijst in het BRP-migratiemodelformaat.
     * @throws IllegalStateException wanneer geen persoon gevonden is
     */
    BrpPersoonslijst bevraagPersoonslijstOpTechnischeSleutel(Long techischesleutel);

    /**
     * Vraag een persoonslijst op uit de BRP database voor de meegegeven technische sleutel, deze
     * persoon moet bestaan en niet foutief opgeschort zijn anders treedt er een fout op.
     * @param technischeSleutel de technische sleutel van de op te zoeken persoon
     * @return De persoonslijst in het BRP-migratiemodelformaat.
     * @throws IllegalStateException wanneer geen persoon gevonden is
     */
    BrpPersoonslijst bevraagPersoonslijstOpTechnischeSleutelFoutiefOpgeschortUitsluiten(Long technischeSleutel);

    /**
     * Zoek een persoonslijst op in de BRP database voor het meegegeven administratienummer.
     * @param administratienummer het *actuele* administratienummer van de op te zoeken persoon
     * @return De persoonslijst in het BRP-migratiemodelformaat of null als niet gevonden.
     */
    BrpPersoonslijst zoekPersoonOpAnummer(String administratienummer);
    /**
     * Zoek een persoonslijst op in de BRP database m.u.v. foutief opgeschorte (reden 'F' en 'W')
     * voor het meegegeven administratienummer.
     * @param administratienummer het *actuele* administratienummer van de op te zoeken persoon
     * @return De persoonslijst in het BRP-migratiemodelformaat of null als niet gevonden.
     */
    BrpPersoonslijst zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(String administratienummer);

    /**
     * @return syncparameters
     */
    SyncParameters getSyncParameters();

    /**
     * Zoek persoonslijsten op in de BRP database m.u.v. foutief opgeschorte (reden 'F' en 'W')
     * voor het meegegeven administratienummer.
     * @param administratienummer het *historische* administratienummer van de op te zoeken persoon
     * @return De persoonslijsten in het BRP-migratiemodelformaat (lege lijst als niets gevonden)
     */
    List<BrpPersoonslijst> zoekPersonenOpHistorischAnummerFoutiefOpgeschortUitsluiten(String administratienummer);

    /**
     * Zoek een persoonslijst op in de BRP database m.u.v. foutief opgeschorte (reden 'F' en 'W')
     * voor het meegegeven burgerservicenummer.
     * @param burgerservicenummer het *actuele* burgerservicenummer van de op te zoeken persoon
     * @return De persoonslijst in het BRP-migratiemodelformaat of null als niet gevonden.
     */
    BrpPersoonslijst zoekPersoonOpBsnFoutiefOpgeschortUitsluiten(String burgerservicenummer);

    /**
     * Zoek persoonslijsten op in de BRP database m.u.v. foutief opgeschorte (reden 'F' en 'W')
     * voor het meegegeven burgerservicenummer.
     * @param burgerservicenummer het *historische* burgerservicenummer van de op te zoeken persoon
     * @return De persoonslijsten in het BRP-migratiemodelformaat (lege lijst als niets gevonden)
     */
    List<BrpPersoonslijst> zoekPersoonOpHistorischBsnFoutiefOpgeschortUitsluiten(String burgerservicenummer);

    /**
     * Zoek persoonslijsten op in de BRP database m.u.v. foutief opgeschorte (reden 'F' en 'W')
     * voor het meegegeven volgende administratienummer.
     * @param administratienummer het *actuele* volgende administratienummer van de op te zoeken persoon
     * @return De persoonslijsten in het BRP-migratiemodelformaat (lege lijst als niets gevonden)
     */
    List<BrpPersoonslijst> zoekPersoonOpVolgendeAnummerFoutiefOpgeschortUitsluiten(String administratienummer);

    /**
     * Zoek persoonslijsten op in de BRP database m.u.v. foutief opgeschorte (reden 'F' en 'W')
     * voor het meegegeven volgende administratienummer.
     * @param administratienummer het *historische* volgende administratienummer van de op te zoeken persoon
     * @return De persoonslijsten in het BRP-migratiemodelformaat (lege lijst als niets gevonden)
     */
    List<BrpPersoonslijst> zoekPersoonOpHistorischVolgendeAnummerFoutiefOpgeschortUitsluiten(String administratienummer);

    /**
     * Zoek persoonslijsten op in de BRP database m.u.v. foutief opgeschorte (reden 'F' en 'W')
     * voor het meegegeven vorige administratienummer.
     * @param administratienummer het *actuele* vorige administratienummer van de op te zoeken persoon
     * @return De persoonslijsten in het BRP-migratiemodelformaat (lege lijst als niets gevonden)
     */
    List<BrpPersoonslijst> zoekPersoonOpVorigeAnummerFoutiefOpgeschortUitsluiten(String administratienummer);

    /**
     * Zoek persoonslijsten op in de BRP database m.u.v. foutief opgeschorte (reden 'F' en 'W')
     * voor het meegegeven vorige administratienummer.
     * @param administratienummer het *historische* vorige administratienummer van de op te zoeken persoon
     * @return De persoonslijsten in het BRP-migratiemodelformaat (lege lijst als niets gevonden)
     */
    List<BrpPersoonslijst> zoekPersoonOpHistorischVorigeAnummerFoutiefOpgeschortUitsluiten(String administratienummer);
}
