/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import nl.bzk.brp.service.algemeen.StapException;

/**
 * Containerinterface voor specifieke stappen voor BRP onderhoud afnemerindiciatie.
 */
interface OnderhoudAfnemerindicatie {

    /**
     * Plaats een afnemerindicatie.
     */
    @FunctionalInterface
    interface PlaatsAfnemerIndicatieService {

        /**
         * Voer de stappen voor het plaatsen van een afnemenindicatie uit.
         * @param verzoekResultaat het plaatsen afnemerindicatie verzoek
         * @throws StapException bij een fout in de stap
         */
        void plaatsAfnemerindicatie(OnderhoudResultaat verzoekResultaat) throws StapException;
    }

    /**
     * Verwijder een afnemerindicatie.
     */
    interface VerwijderAfnemerIndicatieService {

        /**
         * @param verzoekResultaat het verwijderen afnemerindicatie verzoek
         * @throws StapException bij een fout in de stap
         */
        void verwijderAfnemerindicatie(OnderhoudResultaat verzoekResultaat) throws StapException;
    }


    /**
     * Overkoepelende service voor het plaatsen en verwijderen van afnemerindicatie.
     */
    interface AfnemerindicatieVerzoekService {
        /**
         * @param verzoek verzoek
         * @return resultaat
         */
        OnderhoudResultaat verwerkVerzoek(final AfnemerindicatieVerzoek verzoek);
    }
}
