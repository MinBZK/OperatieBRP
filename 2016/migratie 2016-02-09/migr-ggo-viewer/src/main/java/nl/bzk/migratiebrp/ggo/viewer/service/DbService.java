/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoFoutRegel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;

/**
 * Verzorgt de databaseverbinding(en).
 */
public interface DbService {

    /**
     * Zoekt de laatste Lo3Bericht uit de BRP.
     *
     * @param aNummer
     *            Long
     * @return lo3Persoonslijst
     */
    Lo3Bericht zoekLo3Bericht(final Long aNummer);

    /**
     * Haalt het Lg01 bericht uit de Lo3Bericht.
     *
     * @param lo3Bericht
     *            de BerichtLog
     * @return lo3Persoonslijst
     */
    Lo3Persoonslijst haalLo3PersoonslijstUitLo3Bericht(final Lo3Bericht lo3Bericht);

    /**
     * Zoekt de (meest recente) Lo3Bericht op aNummer. Converteert de Set DB Logregels naar een lijst met GgoLogRegels
     *
     * @param lo3Bericht
     *            de Lo3Bericht
     * @return de lijst met GgoFoutRegels, of null als niet gevonden
     */
    List<GgoFoutRegel> haalLogRegelsUitLo3Bericht(final Lo3Bericht lo3Bericht);

    /**
     * Zoekt de BRP Persoonslijst op basis van ANummer.
     *
     * @param aNummer
     *            Long
     * @return brpPersoonslijst
     */
    BrpPersoonslijst zoekBrpPersoonsLijst(final Long aNummer);

    /**
     * Zoek entitymodel-persoon.
     *
     * @param administratienummer
     *            Het a-nummer
     * @return De persoon
     */
    Persoon zoekPersoon(final long administratienummer);

}
