/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.repository;

import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAfnemersindicatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAutorisatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingProtocollering;

/**
 * Init vulling log repository.
 */
public interface InitVullingLogRepository {

    /**
     * Maak een log regel.
     * @param log de InitVullingLog
     */
    void saveInitVullingLogPersoon(final InitVullingLog log);

    /**
     * Queried een InitVullingLog entiteit voor het meegegeven a-nummer. Deze methode geeft precies
     * 1 InitVullingLog als resultaat. Worden er geen of meerdere InitVullingLog entiteiten voor het
     * meegegeven a-nummer gevonden naar wordt er een runtime exception gegooid.
     * @param anummer het anummer
     * @return een InitVullingLog
     */
    InitVullingLog findInitVullingLogPersoon(final String anummer);

    /**
     * Zoek de init vulling regel obv administratienummer.
     * @param administratienummer administratienummer
     * @return regel, of null als niet gevonden
     */
    InitVullingAfnemersindicatie findInitVullingAfnemersindicatie(String administratienummer);

    /**
     * Sla de regel op.
     * @param initVullingAfnemersindicatie de indicatie
     */
    void saveInitVullingAfnemersindicatie(InitVullingAfnemersindicatie initVullingAfnemersindicatie);

    /**
     * Zoek de init vulling autorisatie obv autorisatie id.
     * @param autorisatieId autorisatie id
     * @return autorisatie, of null als niet gevonden
     */
    InitVullingAutorisatie findInitVullingAutorisatie(Long autorisatieId);

    /**
     * Sla de autorisatie op.
     * @param initVullingAutorisatie de autorisatie
     */
    void saveInitVullingAutorisatie(InitVullingAutorisatie initVullingAutorisatie);

    /**
     * Zoek de init vulling protocollering aan de hand van activiteit id.
     * @param activiteitId activiteit id
     * @return de gevonden protocollering data, of null als niet gevonden
     */
    InitVullingProtocollering findInitVullingProtocollering(long activiteitId);

    /**
     * Sla de protocollering data op.
     * @param initVullingProtocollering protocollering data
     */
    void saveInitVullingProtocollering(InitVullingProtocollering initVullingProtocollering);
}
