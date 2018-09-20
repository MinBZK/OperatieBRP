/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.repository;

import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAfnemersindicatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAutorisatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;

/**
 * Init vulling log repository.
 */
public interface InitVullingLogRepository {

    /**
     * Maak een log regel.
     *
     * @param log
     *            de InitVullingLog
     */
    void saveInitVullingLogPersoon(final InitVullingLog log);

    /**
     * Queried een InitVullingLog entiteit voor het meegegeven a-nummer. Deze methode geeft precies 1 InitVullingLog als
     * resultaat. Worden er geen of meerdere InitVullingLog entiteiten voor het meegegeven a-nummer gevonden naar wordt
     * er een runtime exception gegooid.
     *
     * @param anummer
     *            het anummer
     * @return een InitVullingLog
     */
    InitVullingLog findInitVullingLogPersoon(final Long anummer);

    /**
     * Zoek de init vulling regel obv administratienummer.
     *
     * @param administratienummer
     *            administratienummer
     * @return regel, of null als niet gevonden
     */
    InitVullingAfnemersindicatie findInitVullingAfnemersindicatie(Long administratienummer);

    /**
     * Sla de regel op.
     *
     * @param initVullingAfnemersindicatie
     *            de indicatie
     */
    void saveInitVullingAfnemersindicatie(InitVullingAfnemersindicatie initVullingAfnemersindicatie);

    /**
     * Zoek de init vulling autorisatie obv afnemer code.
     *
     * @param afnemerCode
     *            afnemer code.
     * @return autorisatie, of null als niet gevonden
     */
    InitVullingAutorisatie findInitVullingAutorisatie(Integer afnemerCode);

    /**
     * Sla de autorisatie op.
     *
     * @param initVullingAutorisatie
     *            de autorisatie
     */
    void saveInitVullingAutorisatie(InitVullingAutorisatie initVullingAutorisatie);
}
