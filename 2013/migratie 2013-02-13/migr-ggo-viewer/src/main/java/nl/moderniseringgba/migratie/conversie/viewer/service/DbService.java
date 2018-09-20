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
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;

/**
 * Verzorgt de databaseverbinding(en).
 */
public interface DbService {

    /**
     * Zoekt de laatste BerichtLog uit de BRP.
     * 
     * @param aNummer
     *            Long
     * @return lo3Persoonslijst
     */
    BerichtLog zoekBerichtLog(final Long aNummer);

    /**
     * Haalt het Lg01 bericht uit de BerichtLog.
     * 
     * @param berichtLog
     *            de BerichtLog
     * @return lo3Persoonslijst
     */
    Lo3Persoonslijst haalLo3PersoonslijstUitBerichtLog(final BerichtLog berichtLog);

    /**
     * Zoekt de (meest recente) berichtlog op op aNummer. Converteert de Set DB Logregels naar een List model-LogRegels
     * 
     * @param berichtLog
     *            de BerichtLog
     * @return de lijst met LogRegels, of null als niet gevonden
     */
    List<LogRegel> haalLogRegelsUitBerichtLog(final BerichtLog berichtLog);

    /**
     * Zoekt de BRP Persoonslijst op basis van ANummer.
     * 
     * @param aNummer
     *            Long
     * @return brpPersoonslijst
     */
    BrpPersoonslijst zoekBrpPersoonsLijst(final Long aNummer);
}
