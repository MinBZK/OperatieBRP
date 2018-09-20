/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.service;

import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAfnemersindicatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAutorisatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;

/**
 * Service om logging te bewerken.
 */
public interface LoggingService {

    /**
     * Persisteert een {@link InitVullingLog} voor een persoonslijst.
     *
     * @param log
     *            De log die opgeslagen moet worden.
     */
    void persisteerInitVullingLog(final InitVullingLog log);

    /**
     * Persisteert een {@link InitVullingAutorisatie} voor een autorisatie.
     *
     * @param autorisatie
     *            De logs die opgeslagen moet worden.
     */
    void persisteerInitVullingAutorisatie(final InitVullingAutorisatie autorisatie);

    /**
     * Persisteert een {@link InitVullingAfnemersindicatie} voor een afnemerindicatie.
     *
     * @param afnemerindicatie
     *            De log die opgeslagen moet worden.
     */
    void persisteerInitVullingAfnemerindicatie(final InitVullingAfnemersindicatie afnemerindicatie);

    /**
     * Bepaalt de verschillen tussen het oorspronkelijke LO3-bericht en de terug geconverteerde BRP-PL en slaat deze
     * verschillen op bij de {@link InitVullingLog}.
     *
     * @param log
     *            {@link InitVullingLog} waar in de beide berichten staan.
     */
    void bepalenEnOpslaanVerschillen(final InitVullingLog log);

    /**
     * Zoek een {@link InitVullingLog} op basis van een anummer.
     *
     * @param anummer
     *            of the log to find.
     * @return {@link InitVullingLog} voor een persoonslijst.
     */
    InitVullingLog zoekInitVullingLog(final Long anummer);

    /**
     * Zoek een {@link InitVullingAutorisatie} op basis van een afnemercode.
     *
     * @param afnemerCode
     *            of the log to find.
     * @return {@link InitVullingAutorisatie} voor een autorisatie.
     */
    InitVullingAutorisatie zoekInitVullingAutorisatie(final Integer afnemerCode);

    /**
     * Zoek een {@link InitVullingAfnemersindicatie} op basis van een anummer.
     *
     * @param anummer
     *            of the log to find.
     * @return {@link InitVullingAfnemersindicatie} voor een afnemerindicatie.
     */
    InitVullingAfnemersindicatie zoekInitVullingAfnemerindicatie(final Long anummer);
}
