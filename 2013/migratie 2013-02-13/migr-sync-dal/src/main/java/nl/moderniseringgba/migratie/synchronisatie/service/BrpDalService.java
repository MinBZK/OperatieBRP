/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.synchronisatie.domein.blokkering.entity.Blokkering;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;

/**
 * Globale interface voor de Data Access Layer dat de database-entities afschermt voor de client-code. Hierdoor kan er
 * in de rest van de migratie-code gebruik worden gemaakt van de migratiemodellen.
 */
public interface BrpDalService {
    /**
     * Persisteer een persoonslijst in de BRP database.
     * 
     * TODO: documenteren hoe we omgaan met transacties, exceptions etc.
     * 
     * @param brpPersoonslijst
     *            De persoonslijst in het BRP-migratiemodelformaat.
     * @return de Persoon die is opgeslagen in de database
     */
    Persoon persisteerPersoonslijst(BrpPersoonslijst brpPersoonslijst);

    /**
     * Persisteer een persoonslijst in de BRP database waarbij sprake is van een a-nummer wijziging. Omdat de persoon
     * met het meegegeven a-nummer wordt vervangen mag het a-nummer van de nieuwe persoon niet bestaan en MOET het oude
     * a-nummer wel bestaan.
     * 
     * TODO: documenteren hoe we omgaan met transacties, exceptions etc.
     * 
     * @param brpPersoonslijst
     *            De persoonslijst in het BRP-migratiemodelformaat.
     * @param aNummerTeVervangenPersoon
     *            het a-nummer van de te vervangen persoon
     * @return de Persoon die is opgeslagen in de database
     */
    Persoon persisteerPersoonslijst(BrpPersoonslijst brpPersoonslijst, BigDecimal aNummerTeVervangenPersoon);

    /**
     * Vraag een persoonslijst op uit de BRP database.
     * 
     * TODO: documenteren hoe we omgaan met transacties, exceptions etc.
     * 
     * @param administratienummer
     *            het *actuele* administratienummer van de op te zoeken persoon
     * @return De persoonslijst in het BRP-migratiemodelformaat.
     * @throws IllegalStateException
     *             wanneer geen persoon gevonden is
     */
    BrpPersoonslijst bevraagPersoonslijst(long administratienummer);

    /**
     * Vraag een persoonslijst op uit de BRP database.
     * 
     * TODO: documenteren hoe we omgaan met transacties, exceptions etc.
     * 
     * @param administratienummer
     *            het *actuele* administratienummer van de op te zoeken persoon
     * @return De persoonslijst in het BRP-migratiemodelformaat of null als niet gevonden.
     */
    BrpPersoonslijst zoekPersoonOpAnummer(long administratienummer);

    /**
     * Vraag een persoonslijst op uit de BRP database.
     * 
     * TODO: documenteren hoe we omgaan met transacties, exceptions etc.
     * 
     * @param administratienummer
     *            het *historische* administratienummer van de op te zoeken persoon
     * @return De persoonslijst in het BRP-migratiemodelformaat of null als niet gevonden.
     */
    BrpPersoonslijst zoekPersoonOpHistorischAnummer(long administratienummer);

    /**
     * Vraag de laatst toegevoegde BerichtLog op voor het meegeven a-nummer.
     * 
     * TODO: documenteren hoe we omgaan met transacties, exceptions etc.
     * 
     * @param administratienummer
     *            het *actuele* administratienummer van de op te zoeken persoon
     * @return De meeste recente berichtLog of null als niet gevonden.
     */
    BerichtLog zoekBerichtLogOpAnummer(long administratienummer);

    /**
     * Zoekt de berichtLog anummers op voor de meegegeven criteria.
     * 
     * @param vanaf
     *            Datum tijd stempel ligt na vanaf.
     * @param tot
     *            Datum tijd stempel ligt voor tot.
     * @param gemeentecode
     *            De PL komt uit de meegegeven gemeentecode, of als de gemeentecode null is wordt deze param genegeerd.
     * @return Lijst met BerichtLogs.
     */
    List<Long> zoekBerichtLogAnrs(Date vanaf, Date tot, String gemeentecode);

    /**
     * Slaat een BerichtLog entiteit op in de BRP database. Als de BerichtLog entiteit is geassocieerd met een Persoon
     * dan dient deze als in de database te zijn opgeslagen.
     * 
     * @param berichtLog
     *            de berichtLog
     * @return het BerichtLog object dat is opslagen in de database
     */
    BerichtLog persisteerBerichtLog(BerichtLog berichtLog);

    /**
     * Slaat een Blokkering entiteit op in de BRP database.
     * 
     * @param blokkering
     *            de blokkering
     * @return het Blokkering object dat is opslagen in de database
     */
    Blokkering persisteerBlokkering(Blokkering blokkering);

    /**
     * Vraagt een Blokkering entiteit op uit de BRP database.
     * 
     * @param aNummer
     *            het aNummer van de PL die is geblokkeerd en waarvan we de blokkering willen opvragen.
     * @return het Blokkering object dat is opslagen in de database
     */
    Blokkering vraagOpBlokkering(String aNummer);

    /**
     * Verwijdert een Blokkering entiteit uit de BRP database.
     * 
     * @param blokkering
     *            de blokkering
     */
    void verwijderBlokkering(Blokkering blokkering);
}
